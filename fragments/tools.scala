
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


set scalaVersion := "2.12.10"
set libraryDependencies+="org.ddahl"%%"rscala"%"3.2.18"
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



R.eval("vec = %-", (1 to 10).toArray) // send data to R
R.evalI1("vec")
// res9: Array[Int] = Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)


R eval """
vec2 = rep(vec,3)
vec3 = vec2 + 1
mat1 = matrix(vec3,ncol=5)
"""


R.evalI2("mat1") // get data back from R
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
// R: RClient = RClient@661e0a99
R.eval("x = %-", x.toArray) // send x to R
R.eval("y = %-", y.toArray) // send y to R
R.eval("mod = glm(y~x,family=poisson())") // fit in R
// pull the fitted coefficents back into scala
DenseVector[Double](R.evalD1("mod$coefficients"))
// res9: DenseVector[Double] = DenseVector(
//    -2.93361267743947, 0.09875286320703261)


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


class SetSpec extends AnyFlatSpec {

  "An empty Set" should "have size 0" in {
    assert(Set.empty.size == 0)
  }

  it should "produce NoSuchElementException when head is invoked" in {
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


"org.scalacheck" %% "scalacheck" % "1.14.1" % "test"


import org.scalatest.matchers.should.Matchers

import org.scalacheck._
import org.scalacheck.Prop.{forAll, propBoolean}

class StringSpec extends Properties("String") with Matchers {

  property("startwith first string") =
    forAll { (a: String, b: String) =>
      (a+b).startsWith(a)
    } 

  property("concatenate length") =
    forAll { (a: String, b: String) =>
      (a+b).length == a.length + b.length
    }

  property("substring") =
    forAll { (a: String, b: String, c: String) =>
      (a+b+c).substring(a.length, a.length+b.length) == b
    }

}


class SqrtSpecification extends Properties("Sqrt") with Matchers {

  property("math.sqrt should square to give original") =
    forAll { a: Double =>
      (a >= 0.0) ==> {
        val s = math.sqrt(a)
        val tol = 1e-8 * a
        s*s === a +- tol
      }
    }

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


val x = 3 + 2
// x: Int = 5


addSbtPlugin("org.scalameta" % "sbt-mdoc" % "1.3.6")


resolvers += Resolver.bintrayRepo("cibotech", "public")
libraryDependencies += "com.cibo" %% "evilplot" % "0.6.3"
libraryDependencies += "com.cibo" %% "evilplot-repl" % "0.6.3"


import scala.util.Random
import com.cibo.evilplot._
import com.cibo.evilplot.plot._
import com.cibo.evilplot.numeric._
import com.cibo.evilplot.plot.renderers.PointRenderer
import com.cibo.evilplot.plot.aesthetics.DefaultTheme._

val points = Seq.fill(150) {
  Point(Random.nextDouble(), Random.nextDouble())
} :+ Point(0.0, 0.0)
val years = Seq.fill(150)(Random.nextDouble()) :+ 1.0
val yearMap = (points zip years).toMap.withDefaultValue(0.0)
val plot = ScatterPlot(
  points,
  pointRenderer = Some(PointRenderer.depthColor((p: Point) =>
    p.x, 0.0, 500.0, None, None))
  ).standard()
    .xLabel("x")
    .yLabel("y")
    .trend(1, 0)
    .rightLegend()
    .render()
displayPlot(plot)


val im = plot.asBufferedImage

