package circe.coin.util

import circe.coin.util.JsonUtil._
import com.twitter.inject.Logging

import scalaj.http.Http

/**
 * Created by phg on 11/16/17.
 **/
trait XLogging extends Logging {

  val slackAPI: String = ZConfig.getString("bug-report.slack.api")
  val username: String = ZConfig.getString("bug-report.slack.username")
  val service: String = ZConfig.getString("bug-report.service-name")
  val defaultChannel: String = ZConfig.getString("bug-report.default-channel")

  override protected def error(msg: => Any): Unit = {
    println(msg)
    super.error(msg)
  }

  def log(msg: Any): Unit = info(msg)

  def sendSlack(msg: String, channel: String = defaultChannel, from: String = ""): Unit = {
    Http(slackAPI).postData(
      Map(
        "username" -> username,
        "channel" -> channel,
        "text" -> s"${if (from.nonEmpty) s"`$from`\n" else ""}$msg"
      ).toJsonString
    ).asString
  }

  def exception(e: Throwable, from: String = ""): Unit = {
    val msg = if (from.nonEmpty) s"[$service]:$from: `${e.getLocalizedMessage}`" else s"[$service]:`${e.getLocalizedMessage}`"
    e.printStackTrace()
    sendSlack(s"`[exception]` $msg")
  }
}
