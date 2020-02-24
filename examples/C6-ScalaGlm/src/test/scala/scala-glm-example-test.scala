import org.scalatest.FlatSpec

class SetSpec extends FlatSpec {

  import scalaglm.Utils.backSolve
  import breeze.linalg._

  "backSolve" should "invert correctly" in {
    val A = DenseMatrix((4,1),(0,2)) map (_.toDouble)
    val x = DenseVector(3.0,-2.0)
    val y = A * x
    val xx = backSolve(A,y)
    assert (norm(x-xx) < 0.00001)
  }

}

