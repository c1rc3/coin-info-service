package circe.coin.controller.http.exception

import javax.inject.Inject

import circe.coin.domain.{CCPResponse, FailureCCPResponse, StandardResponse}
import circe.coin.util.XLogging
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.exceptions.ExceptionMapper
import com.twitter.finatra.http.response.ResponseBuilder

/**
 * Created by plt on 11/16/17.
 **/
class CommonExceptionMapper @Inject()(response: ResponseBuilder) extends ExceptionMapper[Exception] with XLogging {
  override def toResponse(request: Request, throwable: Exception): Response = {
    throwable match {
      case s@_ =>
        throwable.printStackTrace()
        sendSlack(s.getLocalizedMessage, "bug", "CommonExceptionMapper - unknown error")
        response.ok(StandardResponse(code = -1, msg = s.getLocalizedMessage))
    }
  }
}
