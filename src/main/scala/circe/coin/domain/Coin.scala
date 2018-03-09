package circe.coin.domain

import circe.coin.domain.CoinTypeDef.{CoinPrice, CoinSymbol}
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by phg on 3/8/18.
 **/

object CoinTypeDef {
  type CoinSymbol = String
  type CoinPrice = Double
}

trait CoinInfo {
  val id: String
  val name: String
  val symbol: CoinSymbol
  val priceUsd: CoinPrice
  val priceBtc: CoinPrice
  val percentChange1h: Double
  val percentChange24h: Double
  val percentChange7d: Double
}

case class SimpleCoinInfo(
  id: String,
  name: String,
  symbol: CoinSymbol,
  priceUsd: CoinPrice,
  priceBtc: CoinPrice,
  percentChange1h: Double,
  percentChange24h: Double,
  percentChange7d: Double
) extends CoinInfo

case class CoinHistogram(
  time: Long,
  min: Double,
  max: Double,
  avg: Double
)

case class CoinMarketCapInfo(
  id: String,
  name: String,
  symbol: CoinSymbol,
  rank: Int,
  priceUsd: Double,
  priceBtc: Double,
  @JsonProperty("24h_volume_usd") volumeUsd24h: Double,
  marketCapUsd: Double,
  availableSupply: Double,
  totalSupply: Double,
  maxSupply: Double,
  @JsonProperty("percent_change_1h") percentChange1h: Double,
  @JsonProperty("percent_change_24h") percentChange24h: Double,
  @JsonProperty("percent_change_7d") percentChange7d: Double,
  lastUpdate: Long
)