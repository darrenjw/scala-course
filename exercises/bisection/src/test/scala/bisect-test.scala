/*
bisect-test.scala

Tests for bisection exercise

 */

import org.scalatest._
import org.scalatest.junit._
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class MyTestSuite extends FunSuite {

  test("1+2=3") {
    assert(1 + 2 === 3)
  }

  val tol = 1.0e-8
  def approxEq(test: Double, should: Double): Boolean = {
    if (math.abs(test - should) < tol) true else {
      println("approxEq test failed: found " + test + " but expected " + should + " with tolerance " + tol)
      false
    }
  }

  test("1.0 approxEq 1.0") {
    assert(approxEq(1.0, 1.0))
  }

  import Bisect._

  test("findRoot(-10.0,10.0)(x => x+1.0) == -1.0") {
    assert(approxEq(findRoot(-10.0, 10.0)(x => x + 1.0), -1.0))
  }

  test("findRoot(-5.0, 10.0)(x => 2.0 - x) == 2.0") {
    assert(approxEq(findRoot(-5.0, 10.0)(x => 2.0 - x), 2.0))
  }

  test("findRoot(0.0, 5.0)(x => x - 1.0) == 1.0") {
    assert(approxEq(findRoot(0.0, 5.0)(x => x - 1.0), 1.0))

  }

  test("findRoot(0.0, 2.0)(x => (x + 1.0) * (x - 1.0)) == 1.0") {
    assert(approxEq(findRoot(0.0, 2.0)(x => (x + 1.0) * (x - 1.0)), 1.0))
  }

  test("findRoot(-2.0, 0.0)(x => (x + 1.0) * (x - 1.0)) == -1.0") {
    assert(approxEq(findRoot(-2.0, 0.0)(x => (x + 1.0) * (x - 1.0)), -1.0))
  }

  test("findRoot(0.0, 2.0)(x => x * x - 2.0) == math.sqrt(2.0)") {
    assert(approxEq(findRoot(0.0, 2.0)(x => x * x - 2.0), math.sqrt(2.0)))
  }

}

/* eof */

