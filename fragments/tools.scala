
set scalaVersion := "2.12.10"


set libraryDependencies+="org.scalanlp"%%"breeze"%"1.0"
set libraryDependencies+="org.scalanlp"%%"breeze-natives"%"1.0"


object Metropolis {

  import breeze.stats.distributions._

  def kernel(x: Double): Rand[Double] = for {
    innov <- Uniform(-0.5, 0.5)
    can = x + innov
    oldll = Gaussian(0.0, 1.0).logPdf(x)
    loglik = Gaussian(0.0, 1.0).logPdf(can)
    loga = loglik - oldll
    u <- Uniform(0.0, 1.0)
  } yield if (math.log(u) < loga) can else x

  val chain = Stream.iterate(0.0)(kernel(_).draw)

  def main(args: Array[String]): Unit = {
    val n = if (args.size == 0) 10 else args(0).toInt
    chain.take(n).toArray.foreach(println)
  }

}


addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.10")


set scalaVersion := "2.12.1"
set libraryDependencies+="org.ddahl"%%"rscala"%"2.0.1"
console


val R = org.ddahl.rscala.RClient()
// R: org.ddahl.rscala.RClient = RClient@9fc5dc1


org.ddahl.rscala.RClient.defaultRCmd
// res0: String = R


val d0 = R.evalD0("rnorm(1)")
// d0: Double = 0.945922465932532


val d1 = R.evalD1("rnorm(5)")
// d1: Array[Double] = Array(-0.8272179841496433, ...


val d2 = R.evalD2("matrix(rnorm(6),nrow=2)")
// d2: Array[Array[Double]] = Array(Array(
//      -0.7545734628207127, ...



R.vec = (1 to 10).toArray // send data to R
// R.vec: (Any, String) = ([I@1e009fac,Array[Int])
R.evalI1("vec")
// res1: Array[Int] = Array(1,2,3,4,5,6,7,8,9,10)


R eval """
vec2 = rep(vec,3)
vec3 = vec2 + 1
mat1 = matrix(vec3,ncol=5)
"""


R.getI2("mat1") // get data back from R
// res3: Array[Array[Int]] = Array(Array(2, 8, 4, ...


import breeze.stats.distributions._
import breeze.linalg._
import org.ddahl.rscala.RClient
val x = Uniform(50,60).sample(1000)
// x: IndexedSeq[Double] = Vector(50.54008541753607, ...
val eta = x map (xi => (xi * 0.1) - 3)
// eta: IndexedSeq[Double] = Vector(2.054008541753607, ...
val mu = eta map math.exp
// mu: IndexedSeq[Double] = Vector(7.799101554600703, ...
val y = mu map (Poisson(_).draw)
// y: IndexedSeq[Int] = Vector(8, 15, 12, ...


val R = RClient() // initialise an R interpreter
// R: RClient = RClient@45768f22
R.x=x.toArray // send x to R
// R.x: (Any, String) = ([D@6c7e65fb,Array[Double])
R.y=y.toArray // send y to R
// R.y: (Any, String) = ([I@65a9a726,Array[Int])
R.eval("mod = glm(y~x,family=poisson())") // fit 
// pull the fitted coefficents back into scala
DenseVector[Double](R.evalD1("mod$coefficients"))
// res6: breeze.linalg.DenseVector[Double] =
//   DenseVector(-2.98680078148213,
//      0.09959046899061315)


require(1 == 1) // satisfied
// require(1 == 2) // throws exception
assert (1 == 1) // satisfied
// assert (1 == 2) // throws exception


def sqrt(x: Double): Double = {
  require(x >= 0.0) // pre-condition
  val ans = math.sqrt(x)
  assert(math.abs(x-ans*ans) < 0.00001) // post-condition
  ans
}

sqrt(2.0) // works as expected
// sqrt(-2.0) // throws exception


scalacOptions += "-Xdisable-assertions"


import org.scalatest.FlatSpec

class SetSpec extends FlatSpec {

  "An empty Set" should "have size 0" in {
    assert(Set.empty.size == 0)
  }

  it should "throw an exception with head" in {
    assertThrows[NoSuchElementException] {
      Set.empty.head
    }
  }

}


  "A Gamma(3.0,4.0)" should "have mean 12.0" in {
    import breeze.stats.distributions.Gamma
    val g = Gamma(3.0,4.0)
    val m = g.mean
    assert(math.abs(m - 12.0) < 0.000001)
  }


/**
  *  Take every th value from the stream s of type T
  * 
  *  @param s A Stream to be thinned
  *  @param th Thinning interval
  * 
  *  @return The thinned stream, with values of
  *  the same type as the input stream
  */
def thinStream[T](s: Stream[T],th: Int): Stream[T] = {
  val ss = s.drop(th-1)
  if (ss.isEmpty) Stream.empty else
    ss.head #:: thinStream(ss.tail, th)
}

