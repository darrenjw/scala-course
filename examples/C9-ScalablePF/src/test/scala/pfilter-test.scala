/*
pfilter-test.scala

Test code for pfilter

 */

package pfilter

import org.scalatest._
import org.scalatest.junit._
import org.junit.runner.RunWith

import scala.language.higherKinds
import PFilter._

@RunWith(classOf[JUnitRunner])
class MyTestSuite extends FunSuite {

  test("1+2=3") {
    assert(1 + 2 === 3)
  }

  // test generic functions to check that the typeclass works as intended
  def doubleIt[C[_]: GenericColl](ca: C[Int]): C[Int] = ca map (_ * 2)
  def addThem[C[_]: GenericColl](ca: C[Int]): Int = ca reduce (_ + _)
  def repeatThem[C[_]: GenericColl](ca: C[Int]): C[Int] = ca flatMap (x => List(x, x, x))
  def zipThem[C[_]: GenericColl](ci: C[Int], cd: C[Double]): C[(Int, Double)] = ci zip cd
  def getLength[C[_]: GenericColl](ci: C[Int]): Int = ci.length

  test("Vector in generic function including map") {
    val v = Vector(5, 10, 15, 20)
    val v2 = v map (_ * 2)
    val v3 = doubleIt(v)
    assert(v2 === v3)
  }

  test("Vector in generic function including flatMap") {
    val v = Vector(5, 10, 15)
    val v2 = v flatMap (x => Array(x, x, x))
    //println(v2)
    val v3 = repeatThem(v)
    assert(v2 === v3)
  }

  test("Vector in generic function including reduce") {
    val v = Vector(5, 10, 15)
    val s = addThem(v)
    assert(s === 30)
  }

  test("Vector in generic zipping function") {
    val v1 = Vector(1, 2, 3)
    val v2 = Vector(2.0, 4.0, 6.0)
    val v3 = v1 zip v2
    val v4 = zipThem(v1, v2)
    assert(v4 === v3)
  }

  test("Vector in generic length function") {
    val v1 = Vector(1, 2, 3, 4)
    val l = getLength(v1)
    assert(l === 4)
  }

  test("ParVector in generic function including map") {
    val v = Vector(5, 10, 15, 30).par
    val v2 = v map (_ * 2)
    //println(v2)
    val v3 = doubleIt(v)
    assert(v2 === v3)
  }

  test("ParVector in generic function including flatMap") {
    val v = Vector(5, 10, 15, 10).par
    val v2 = v flatMap (x => Vector(x, x, x))
    //println(v2)
    val v3 = repeatThem(v)
    assert(v2 === v3)
  }

  test("ParVector in generic function including reduce") {
    val v = Vector(5, 10, 15).par
    val s = addThem(v)
    assert(s === 30)
  }

  test("ParVector in generic zipping function") {
    val v1 = Vector(1, 2, 3).par
    val v2 = Vector(2.0, 4.0, 6.0).par
    val v3 = v1 zip v2
    //println(v3)
    val v4 = zipThem(v1, v2)
    assert(v4 === v3)
  }

  test("ParVector in generic length function") {
    val v1 = Vector(1, 2, 3, 4).par
    val l = getLength(v1)
    assert(l === 4)
  }

  test("Vector update test") {
    import breeze.stats.distributions.Gaussian
    implicit val dState = new State[Double] {}
    implicit val dObs = new Observation[Double] {}
    val p1 = Gaussian(0.0, 10.0).sample(100000).toVector
    val p2 = update((s: Double, o: Double) => Gaussian(s, 2.0).logPdf(o), (s: Double) => Gaussian(s, 1.0).draw)(p1, 5.0)
    assert(p2._2.length > 90000)
  }

  test("ParVector update test") {
    import breeze.stats.distributions.Gaussian
    implicit val dState = new State[Double] {}
    implicit val dObs = new Observation[Double] {}
    val p1 = Gaussian(0.0, 10.0).sample(100000).toVector.par
    val p2 = update((s: Double, o: Double) => Gaussian(s, 2.0).logPdf(o), (s: Double) => Gaussian(s, 1.0).draw)(p1, 5.0)
    assert(p2._2.length > 90000)
  }

  test("Vector pFilter test") {
    import breeze.stats.distributions.Gaussian
    implicit val dState = new State[Double] {}
    implicit val dObs = new Observation[Double] {}
    val p1 = Gaussian(0.0, 10.0).sample(100000).toVector
    val pn = pFilter(p1, List(2.0, 2.0, 3.0, 4.0), (s: Double, o: Double) => Gaussian(s, 2.0).logPdf(o), (s: Double) => Gaussian(s, 1.0).draw)
    assert(pn._2.length > 90000)
  }

  test("ParVector pFilter test") {
    import breeze.stats.distributions.Gaussian
    implicit val dState = new State[Double] {}
    implicit val dObs = new Observation[Double] {}
    val p1 = Gaussian(0.0, 10.0).sample(100000).toVector.par
    val pn = pFilter(p1, List(2.0, 2.0, 3.0, 4.0), (s: Double, o: Double) => Gaussian(s, 2.0).logPdf(o), (s: Double) => Gaussian(s, 1.0).draw)
    assert(pn._2.length > 90000)
  }

  test("Vector pfMll test") {
    import breeze.stats.distributions.Gaussian
    implicit val dState = new State[Double] {}
    implicit val dObs = new Observation[Double] {}
    implicit val dPar = new Parameter[Double] {}
    val mll = pfMll(
      (th: Double) => Gaussian(0.0, 10.0).sample(100000).toVector,
      (th: Double) => (s: Double) => Gaussian(s, 1.0).draw,
      (th: Double) => (s: Double, o: Double) => Gaussian(s, 2.0).logPdf(o),
      List(2.0, 2.0, 3.0, 4.0)
    )
    val ll1 = mll(1.0)
    val ll2 = mll(2.0)
    assert(math.abs(ll1 - ll2) < 0.1)
  }

  test("ParVector pfMll test") {
    import breeze.stats.distributions.Gaussian
    implicit val dState = new State[Double] {}
    implicit val dObs = new Observation[Double] {}
    implicit val dPar = new Parameter[Double] {}
    val mll = pfMll(
      (th: Double) => Gaussian(0.0, 10.0).sample(100000).toVector.par,
      (th: Double) => (s: Double) => Gaussian(s, 1.0).draw,
      (th: Double) => (s: Double, o: Double) => Gaussian(s, 2.0).logPdf(o),
      List(2.0, 2.0, 3.0, 4.0)
    )
    val ll1 = mll(1.0)
    val ll2 = mll(2.0)
    assert(math.abs(ll1 - ll2) < 0.1)
  }

}

// eof
