/*
ex2-test.scala

Tests for Exercise 2

 */

import org.scalatest._
import org.scalatest.junit._
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class PartA extends FunSuite {

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

  import OptionBisect._

  test("findRootOpt(-10.0,10.0)(x => x+1.0) == Some(-1.0)") {
    assert(approxEq(findRootOpt(-10.0, 10.0)(x => x + 1.0).getOrElse(0.0), -1.0))
  }

  test("findRootOpt(-5.0, 10.0)(x => 2.0 - x) == Some(2.0)") {
    assert(approxEq(findRootOpt(-5.0, 10.0)(x => 2.0 - x).getOrElse(0.0), 2.0))
  }

  test("findRootOpt(0.0, 5.0)(x => x - 1.0) == Some(1.0)") {
    assert(approxEq(findRootOpt(0.0, 5.0)(x => x - 1.0).getOrElse(0.0), 1.0))

  }

  test("findRootOpt(0.0, 2.0)(x => (x + 1.0) * (x - 1.0)) == Some(1.0)") {
    assert(approxEq(findRootOpt(0.0, 2.0)(x => (x + 1.0) * (x - 1.0)).getOrElse(0.0), 1.0))
  }

  test("findRootOpt(-2.0, 0.0)(x => (x + 1.0) * (x - 1.0)) == Some(-1.0)") {
    assert(approxEq(findRootOpt(-2.0, 0.0)(x => (x + 1.0) * (x - 1.0)).getOrElse(0.0), -1.0))
  }

  test("findRootOpt(0.0, 2.0)(x => x * x - 2.0) == Some(math.sqrt(2.0))") {
    assert(approxEq(findRootOpt(0.0, 2.0)(x => x * x - 2.0).getOrElse(0.0), math.sqrt(2.0)))
  }

  test("findRootOpt(2.0,0.0)(x => x-1.0) == None") {
    assert(findRootOpt(2.0, 0.0)(x => x - 1.0) == None)
  }

  test("findRootOpt(-1.0,-3.0)(x => x+2.0) == None") {
    assert(findRootOpt(-1.0, -3.0)(x => x + 2.0) == None)
  }

  test("findRootOpt(0.0,2.0)(x => x+1.0) == None") {
    assert(findRootOpt(0.0, 2.0)(x => x + 1.0) == None)
  }

  test("findRootOpt(0.0,2.0)(x => x-5.0) == None") {
    assert(findRootOpt(0.0, 2.0)(x => x - 5.0) == None)
  }

}

@RunWith(classOf[JUnitRunner])
class PartB extends FunSuite {

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

  import OptionBisect._

  def testX(a: Double, x: Double): Boolean = {
    val y = a * x * x
    approxEq(x * x + y * y, 1.0)
  }

  test("solveQuad(0.1)") {
    assert(testX(0.1, solveQuad(0.1).getOrElse(0.0)))
  }

  test("solveQuad(1.0)") {
    assert(testX(1.0, solveQuad(1.0).getOrElse(0.0)))
  }

  test("solveQuad(10.0)") {
    assert(testX(10.0, solveQuad(10.0).getOrElse(0.0)))
  }

  test("solveQuad(0.01)") {
    assert(testX(0.01, solveQuad(0.01).getOrElse(0.0)))
  }



}

/* eof */

