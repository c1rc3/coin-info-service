package circe.coin.repository

import java.io.File

import circe.coin.domain.{CoinHistogram, CoinInfo, CoinMarketCapInfo, SimpleCoinInfo}
import circe.coin.repository.ESClient._
import circe.coin.util.Jsoning
import com.google.inject.Inject
import com.google.inject.name.Named
import com.twitter.util.Future
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.aggregations.AggregationBuilders
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram
import org.elasticsearch.search.aggregations.metrics.stats.Stats

import scala.io.Source
import scala.collection.JavaConversions._

/**
 * Created by phg on 3/9/18.
 **/
trait CoinInfoRepository {
  def updateCoinInfos(coinMarketCapInfo: Array[CoinMarketCapInfo]): Future[Boolean]

  def addCoinInfoHistory(coinMarketCapInfo: Array[CoinMarketCapInfo]): Future[Boolean]

  def mget(ids: Array[String]): Future[Array[CoinInfo]]

  def getHistogram(symbol: String, metric: String, from: Long, to: Long, interval: Long): Future[Seq[CoinHistogram]]
}

case class ESCoinInfoRepository @Inject()(es: AsyncESClient, @Named("coin-info-mapping-file") mappingFile: String) extends CoinInfoRepository with Jsoning {

  protected def updateMapping(types: String, jsonFile: String): Unit = es.Admin.updateMapping(types, Source.fromFile(jsonFile).mkString)

  protected def createIfNotExists(setting: String, mappings: Seq[(String, String)] = Seq()): Unit = {
    es.Admin.initIndex(setting, mappings)
  }

  protected def initIndexFromJsonFile(file: String): Unit = {
    val json = readFile(new File(file))
    val settings = json.path("settings").asText("")
    val mappings = json.path("mappings").fields().toSeq.map(f => {
      (f.getKey, f.getValue.toJsonString)
    })
    es.Admin.initIndex(settings)
    mappings.foreach(f => {
      es.Admin.updateMapping(f._1, f._2)
    })
  }

  initIndexFromJsonFile(mappingFile)

  override def updateCoinInfos(coinMarketCapInfo: Array[CoinMarketCapInfo]) = {
    es.mupsert("coin", coinMarketCapInfo.map(f => (f.id, f.toJsonString))).map(_ => true)
  }

  override def addCoinInfoHistory(coinMarketCapInfo: Array[CoinMarketCapInfo]) = {
    val data = coinMarketCapInfo.map(info => {
      val id = s"${info.id}-${info.lastUpdate}"
      (id, info.toJsonString)
    })
    es.mupsert("history", data).map(_ => true)
  }

  override def mget(ids: Array[String]) = es.mget("coin", ids).map(res => {
    res.getResponses.map(item => item.getResponse.getSourceAsString.asJsonObject[SimpleCoinInfo])
  })

  override def getHistogram(symbol: String, metric: String, from: Long, to: Long, interval: Long) = {
    es.prepareSearch.setTypes("history")
      .setSize(0)
      .setQuery(
        QueryBuilders.boolQuery()
          .must(QueryBuilders.termQuery("symbol", symbol))
          .must(QueryBuilders.rangeQuery("last_update").gte(from).lte(to))
      )
      .addAggregation(
        AggregationBuilders.dateHistogram("dh-timestamp").field("last_update")
          .interval(interval)
          .subAggregation(AggregationBuilders.stats("stats").field(metric))
      ).execAsync
      .map(res => res.getAggregations.get[Histogram]("dh-timestamp").getBuckets.map(bucket => {
        val time = bucket.getKeyAsString.toLong
        val stats = bucket.getAggregations.get[Stats]("stats")
        CoinHistogram(time, stats.getMin, stats.getMax, stats.getAvg)
      }))
  }
}