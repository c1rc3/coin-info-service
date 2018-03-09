package circe.coin.util

import com.twitter.util.NonFatal

/**
 * Created by plt on 12/27/17.
 **/
trait Cronning extends XLogging {
  var threads = Seq[Thread]()
  def run(period: Long)(fn: => Unit): Unit = {
    val thread = new Thread(new Runnable() {
      override def run() = while (true) try {
        fn
        Thread.sleep(period)
      } catch {
        case NonFatal(throwable) => exception(throwable, "Cronning.run")
      }
    })
    thread.start()
    threads = threads :+ thread
  }
}
