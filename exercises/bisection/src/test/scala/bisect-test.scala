/*
bisect-test.scala

Tests for bisection exercise

 */

import org.scalatest._
import org.scalatest.Matchers._

class MyTestSuite extends FlatSpec {

  "1+2" should "=3" in {
    assert(1 + 2 === 3)
  }

  val tol = 1.0e-8

  def approxEq(test: Double, should: Double): Boolean = {
    if (math.abs(test - should) < tol) true else {
      println("approxEq test failed: found " + test + " but expected " + should + " with tolerance " + tol)
      false
    }
  }

  "1.0" should "approxEq 1.0" in {
    assert(approxEq(1.0, 1.0))
  }

  import Bisect._

  "findRoot(-10.0,10.0)(x => x+1.0)" should "=-1.0" in {
    assert(approxEq(findRoot(-10.0, 10.0)(x => x + 1.0), -1.0))
  }

  "findRoot(-5.0, 10.0)(x => 2.0 - x)" should "=2.0" in {
    assert(approxEq(findRoot(-5.0, 10.0)(x => 2.0 - x), 2.0))
  }

  "findRoot(0.0, 5.0)(x => x - 1.0)" should "= 1.0" in {
    assert(approxEq(findRoot(0.0, 5.0)(x => x - 1.0), 1.0))

  }

  "findRoot(0.0, 2.0)(x => (x + 1.0) * (x - 1.0))" should "= 1.0" in {
    assert(approxEq(findRoot(0.0, 2.0)(x => (x + 1.0) * (x - 1.0)), 1.0))
  }

  "findRoot(-2.0, 0.0)(x => (x + 1.0) * (x - 1.0))" should "= -1.0" in {
    assert(approxEq(findRoot(-2.0, 0.0)(x => (x + 1.0) * (x - 1.0)), -1.0))
  }

  "findRoot(0.0, 2.0)(x => x * x - 2.0)" should "= math.sqrt(2.0)" in {
    assert(approxEq(findRoot(0.0, 2.0)(x => x * x - 2.0), math.sqrt(2.0)))
  }

}

/* eof */

