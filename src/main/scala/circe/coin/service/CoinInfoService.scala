package circe.coin.service


import circe.coin.domain.CoinTypeDef.CoinSymbol
import circe.coin.domain.{CoinHistogram, CoinInfo}
import circe.coin.repository.CoinInfoRepository
import com.google.inject.Inject
import com.twitter.util.Future

/**
 * Created by phg on 3/8/18.
 **/
trait CoinInfoService {

  def mget(coinIds: Array[String]): Future[Map[String, CoinInfo]]

  def coinHistogram(symbol: CoinSymbol, metric: String, from: Long, to: Long, interval: Long): Future[Seq[CoinHistogram]]
}

case class ESCoinInfoService @Inject()(coinInfoRepository: CoinInfoRepository) extends CoinInfoService {

  override def mget(coinIds: Array[String]) = coinInfoRepository.mget(coinIds).map(coinInfos =>
    coinInfos.map(f => (f.id, f)).toMap
  )

  override def coinHistogram(symbol: CoinSymbol, metric: String, from: Long, to: Long, interval: Long) =
    coinInfoRepository.getHistogram(symbol.toUpperCase, metric, from, to, interval)
}