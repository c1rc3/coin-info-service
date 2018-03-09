package circe.coin.module

import javax.inject.Singleton

import circe.coin.repository.{AsyncESClient, CoinInfoRepository, ESCoinInfoRepository}
import circe.coin.service.{CoinInfoService, ESCoinInfoService}
import circe.coin.util.ZConfig
import com.google.inject.Provides
import com.twitter.inject.TwitterModule

import scala.collection.JavaConversions._
/**
 * Created by SangDang on 9/16/16.
 */
object CoinInfoModule extends TwitterModule {

  override def configure: Unit = {
    bind[String].annotatedWithName("coin-info-mapping-file")
      .toInstance(ZConfig.getString("coin-info.es.mapping-file"))

    bind[String].annotatedWithName("market-cap-url")
      .toInstance(ZConfig.getString("coin-market-cap.url"))

    bind[Long].annotatedWithName("market-cap-crawler-period")
      .toInstance(ZConfig.getLong("coin-market-cap.period"))

    bind[CoinInfoRepository].to[ESCoinInfoRepository]
    bind[CoinInfoService].to[ESCoinInfoService]
  }

  @Provides
  @Singleton
  def providesCoinInfoESClient: AsyncESClient = {
    val config = ZConfig.getConf("coin-info")
    AsyncESClient(
      config.getStringList("es.servers").toList,
      config.getString("es.cluster"),
      config.getBoolean("es.transport-sniff"),
      config.getString("es.index-name")
    )
  }
}
