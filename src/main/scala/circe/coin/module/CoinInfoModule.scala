package circe.coin.module

import circe.coin.service.{CoinInfoService, ESCoinInfoService}
import com.twitter.inject.TwitterModule

/**
 * Created by SangDang on 9/16/16.
 */
object CoinInfoModule extends TwitterModule {
  override def configure: Unit = {
    bind[CoinInfoService].to[ESCoinInfoService]
  }
}
