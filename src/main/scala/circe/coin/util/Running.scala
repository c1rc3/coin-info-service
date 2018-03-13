package circe.coin.util

import java.util.{Timer, TimerTask}

import com.twitter.inject.Logging

/**
 * Created by phg on 12/6/17.
 **/

trait Running extends Logging {

  private val globalTimer = new Timer()

  def run(delay: Long, period: Long)(func: => Unit): Unit = {
    globalTimer.schedule(new TimerTask {
      override def run(): Unit = try {
        func
      } catch {
        case throwable: Throwable => error("Running.run", throwable)
      }
    }, delay, period)
  }

  def runWithNewTimer(delay: Long, period: Long)(func: => Unit): Timer = {
    val timer = new Timer()
    timer.schedule(new TimerTask {
      override def run(): Unit = func
    }, delay, period)
    timer
  }
}
