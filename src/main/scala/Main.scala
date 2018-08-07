import scala.concurrent._
import ExecutionContext.Implicits.global

object Main extends App {

  def recursiveEcho(i: Int, step: Int): Unit = {
    println(i)
    Thread.sleep(1000)
    recursiveEcho(i + step, step)
  }

  val f = Cancelable(recursiveEcho(0, 1))

  Thread.sleep(5000)

  Future.successful(println("Start of killing")).andThen {case _ =>
      f.cancel()
  }

  Thread.sleep(10000)

}
