package circe.coin.controller.http

import circe.coin.domain.CCPResponse._
import circe.coin.domain.{CoinInfoRequest, CoinPriceDateHistogramRequest}
import circe.coin.service.CoinInfoService
import com.google.inject.Inject
import com.twitter.finatra.http.Controller

/**
 * Created by phg on 3/8/18.
 **/
class CoinController @Inject()(coinInfoService: CoinInfoService) extends Controller {

  get("/coins") {
    req: CoinInfoRequest => {
      coinInfoService.getCoinBySymbols(req.getSymbols).toCCPSuccessResponse
    }
  }

  get("/coins/price/date-histogram") {
    req: CoinPriceDateHistogramRequest => {
      coinInfoService.getCoinPriceDateHistogram(req.symbol, req.fromTime, req.toTime, req.interval).toCCPSuccessResponse
    }
  }
}
