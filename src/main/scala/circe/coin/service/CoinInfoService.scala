package circe.coin.service


import circe.coin.domain.{CoinHistogram, CoinInfo, SimpleCoinInfo}
import circe.coin.domain.CoinTypeDef.CoinSymbol
import com.twitter.util.Future

/**
 * Created by phg on 3/8/18.
 **/
trait CoinInfoService {

  def getCoinBySymbols(symbols: Array[CoinSymbol]): Future[Map[CoinSymbol, CoinInfo]]

  def getCoinPriceDateHistogram(symbol: String, from: Long, to: Long, interval: Long): Future[Seq[CoinHistogram]]
}


case class ESCoinInfoService() extends CoinInfoService {

  override def getCoinBySymbols(symbols: Array[CoinSymbol]) = Future(
    symbols.map(s => (s, SimpleCoinInfo(
      id = "bitcoin",
      symbol = s,
      name = "Bitcoin",
      priceInUSD = 9845.123,
      priceInBTC = 1.00,
      percentChangedHour = 0.05,
      percentChangedDay = 0.08,
      percentChangedWeek = 0.10
    ))).toMap
  )

  override def getCoinPriceDateHistogram(symbol: String, from: Long, to: Long, interval: Long) = Future(
    Seq(
      CoinHistogram(
        time = 1520497939878L,
        min = 123.123,
        max = 432.234,
        avg = 123.234
      )
    )
  )
}