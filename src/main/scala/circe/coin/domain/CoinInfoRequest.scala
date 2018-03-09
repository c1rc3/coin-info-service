package circe.coin.domain

import java.util.concurrent.TimeUnit

import circe.coin.domain.CoinTypeDef.CoinSymbol
import com.twitter.finatra.request.{QueryParam, RouteParam}

/**
 * Created by phg on 3/8/18.
 **/
case class CoinInfoRequest(@QueryParam ids: String) {
  def getIds: Array[CoinSymbol] = ids.trim.toLowerCase.split(",").map(_.trim)
}

case class CoinPriceDateHistogramRequest(
  @RouteParam id: String,
  @QueryParam metric: String = "price_usd",
  @QueryParam fromTime: Long = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1),  // default 1 days before
  @QueryParam toTime: Long = System.currentTimeMillis(),                                // default current millis
  @QueryParam interval: Long = TimeUnit.MINUTES.toMillis(30)                            // default 15 minutes
)