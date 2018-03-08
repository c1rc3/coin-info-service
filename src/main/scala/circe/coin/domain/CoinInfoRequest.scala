package circe.coin.domain

import java.util.concurrent.TimeUnit

import circe.coin.domain.CoinTypeDef.CoinSymbol
import com.twitter.finatra.request.QueryParam

/**
 * Created by phg on 3/8/18.
 **/
case class CoinInfoRequest(@QueryParam symbols: String) {

  def getSymbols: Array[CoinSymbol] = symbols.trim.toLowerCase.split(",")
}

case class CoinPriceDateHistogramRequest(
  @QueryParam symbol: String,
  @QueryParam fromTime: Long = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1),  // default 1 days before
  @QueryParam toTime: Long = System.currentTimeMillis(),                                // default current millis
  @QueryParam interval: Long = TimeUnit.MINUTES.toMillis(15)                            // default 15 minutes
)