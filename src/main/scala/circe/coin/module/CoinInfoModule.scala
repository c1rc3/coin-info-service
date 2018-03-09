package circe.coin.module

import circe.coin.repository.AsyncESClient
import circe.coin.service.{CoinInfoService, ESCoinInfoService}
import circe.coin.util.ZConfig
import com.twitter.inject.TwitterModule
import scala.collection.JavaConversions._
/**
 * Created by SangDang on 9/16/16.
 */
object CoinInfoModule extends TwitterModule {
  override def configure: Unit = {
    bind[CoinInfoService].to[ESCoinInfoService]
  }

  def providesCoinInfoESClient: AsyncESClient = {
    val config = ZConfig.getConf("football")
    AsyncESClient(
      config.getStringList("es.servers").toList,
      config.getString("es.cluster"),
      config.getBoolean("es.transport-sniff"),
      config.getString("es.index-name")
    )
  }
}
