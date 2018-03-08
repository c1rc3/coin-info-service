package circe.coin.domain

import circe.coin.domain.CoinTypeDef.{CoinPrice, CoinSymbol}

/**
 * Created by phg on 3/8/18.
 **/

object CoinTypeDef {
  type CoinSymbol = String
  type CoinPrice = Double
}

trait CoinInfo {
  val id: String
  val symbol: CoinSymbol
  val name: String
  val priceInUSD: CoinPrice
  val priceInBTC: CoinPrice
  val percentChangedHour: Double
  val percentChangedDay: Double
  val percentChangedWeek: Double
}

case class SimpleCoinInfo(
  id: String,
  symbol: CoinSymbol,
  name: String,
  priceInUSD: CoinPrice,
  priceInBTC: CoinPrice,
  percentChangedHour: Double,
  percentChangedDay: Double,
  percentChangedWeek: Double
) extends CoinInfo

case class CoinHistogram(
  time: Long,
  min: Double,
  max: Double,
  avg: Double
)