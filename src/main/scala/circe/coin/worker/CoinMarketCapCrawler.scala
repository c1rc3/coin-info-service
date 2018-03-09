package circe.coin.worker

import circe.coin.domain.CoinMarketCapInfo
import circe.coin.repository.CoinInfoRepository
import circe.coin.util.JsonUtil._
import circe.coin.util.Running
import com.google.inject.Inject
import com.google.inject.name.Named

import scalaj.http.{Http, HttpResponse}

/**
 * Created by phg on 3/9/18.
 **/
case class CoinMarketCapCrawler @Inject()(
  @Named("market-cap-url") marketCapUrl: String,
  coinInfoRepository: CoinInfoRepository,
  @Named("market-cap-crawler-period") period: Long
) extends Running {

  private def retry(f: => HttpResponse[String]): HttpResponse[String] = {
    var res = f
    var counter = 0
    while (res.isError && counter < 10) {
      Thread.sleep(3000)
      counter = counter + 1
      res = f
    }
    res
  }

  private def fetchNSave(): Unit = {
    val res = retry(Http(s"$marketCapUrl?start=0&limit=1000").asString)
    if (res.isSuccess) {
      val arr = res.body.asJsonNode.asArrayOfNode.map(node => CoinMarketCapInfo(
        id = node.path("id").asText,
        name = node.path("name").asText,
        symbol = node.path("symbol").asText.toUpperCase,
        rank = node.path("rank").asInt,
        priceUsd = node.path("price_usd").asDouble,
        priceBtc = node.path("price_btc").asDouble,
        marketCapUsd = node.path("market_cap_usd").asDouble,
        availableSupply = node.path("available_supply").asDouble,
        totalSupply = node.path("total_supply").asDouble,
        maxSupply = node.path("max_supply").asDouble,
        volumeUsd24h = node.path("24h_volume_usd").asDouble,
        percentChange1h = node.path("percent_change_1h").asDouble,
        percentChange24h = node.path("percent_change_24h").asDouble,
        percentChange7d = node.path("percent_change_7d").asDouble,
        lastUpdate = node.path("last_updated").asLong() * 1000L)
      )
      coinInfoRepository.updateCoinInfos(arr)
      coinInfoRepository.addCoinInfoHistory(arr)
    }
  }

  run(1000, period) {
    info("[Run] CoinMarketCapCrawler!")
    fetchNSave()
  }
}
