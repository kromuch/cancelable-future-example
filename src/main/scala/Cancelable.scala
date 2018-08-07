import java.util.concurrent.FutureTask

import scala.concurrent.{ExecutionContext, Future}

class Cancelable[T](todo: => T)(implicit executionContext: ExecutionContext) {

  private val jf: FutureTask[T] = new FutureTask[T](
    () => todo
  )

  executionContext.execute(jf)

  val future: Future[T] = Future {
    jf.get
  }

  def cancel(): Unit = jf.cancel(true)

}

object Cancelable {
  def apply[T](todo: => T)(implicit executionContext: ExecutionContext): Cancelable[T] =
    new Cancelable[T](todo)
}