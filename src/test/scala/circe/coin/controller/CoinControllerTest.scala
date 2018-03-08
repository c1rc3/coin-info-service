package circe.coin.controller

import circe.coin.Server
import circe.coin.domain.CoinTypeDef.CoinSymbol
import circe.coin.domain.{CoinHistogram, CoinInfo, SuccessCCPResponse}
import circe.coin.util.JsonUtil._
import com.twitter.finagle.http.Status
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest

/**
 * Created by phg on 3/8/18.
 **/
class CoinControllerTest extends FeatureTest {
  override protected def server = new EmbeddedHttpServer(twitterServer = new Server)

  "[HTTP] Coin Info" must {

    "Get coin info success" in {
      val res = server.httpGet("/coins?symbols=btc", andExpect = Status.Ok).contentString.asJsonObject[SuccessCCPResponse]
      assertResult(1)(res.code)
      assert(res.data.nonEmpty)

      val data = res.data.get.asInstanceOf[Map[CoinSymbol, CoinInfo]]
      assert(data.contains("btc"))
    }

    "Get coin price histogram" in {
      val res = server.httpGet("/coins/price/data-histogram?symbol=btc", andExpect = Status.Ok).contentString.asJsonObject[SuccessCCPResponse]
      assertResult(1)(res.code)
      assert(res.data.nonEmpty)

      val data = res.data.get.asInstanceOf[Seq[CoinHistogram]]
      data.foreach(h => {
        assert(h.time > 0, "time must > 0")
        assert(h.min > 0, "min must > 0")
        assert(h.max > 0, "max must > 0")
        assert(h.avg > 0, "avg must > 0")
      })
    }
  }
}
