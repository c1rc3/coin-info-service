package circe.coin


import circe.coin.controller.http.CoinController
import circe.coin.module.CoinInfoModule
import circe.coin.util.ZConfig
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.CommonFilters
import com.twitter.finatra.http.routing.HttpRouter

/**
 * Created by SangDang on 9/8/
 **/
object MainApp extends Server

class Server extends HttpServer {

  override protected def defaultFinatraHttpPort: String = ZConfig.getString("server.http.port", ":8080")

  override protected def disableAdminHttpServer: Boolean = ZConfig.getBoolean("server.admin.disable", default = true)

  override val modules = Seq(CoinInfoModule)

  override protected def configureHttp(router: HttpRouter): Unit = {
    router.filter[CommonFilters]
      .add[CoinController]
  }

  override def afterPostWarmup(): Unit = {
    super.afterPostWarmup()
    println("=====> Ready")
  }
}
