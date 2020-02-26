import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


// Example unit tests
class CatsSpec extends AnyFlatSpec with Matchers {

  import cats._
  import cats.implicits._

 "A List" should "combine" in {
   val l = List(1,2) |+| List(3,4)
   l should be (List(1,2,3,4))
  }

}




// eof


