/*
ex2-test.scala

Tests for Exercise 2

 */

import org.scalatest._
import org.scalatest.Matchers._

class PartA extends FlatSpec {

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

  "1.0 " should "approxEq 1.0" in {
    assert(approxEq(1.0, 1.0))
  }

  import OptionBisect._

  "findRootOpt(-10.0,10.0)(x => x+1.0)" should "= Some(-1.0)" in {
    assert(approxEq(findRootOpt(-10.0, 10.0)(x => x + 1.0).getOrElse(0.0), -1.0))
  }

  "findRootOpt(-5.0, 10.0)(x => 2.0 - x)" should "= Some(2.0)" in {
    assert(approxEq(findRootOpt(-5.0, 10.0)(x => 2.0 - x).getOrElse(0.0), 2.0))
  }

  "findRootOpt(0.0, 5.0)(x => x - 1.0)" should "= Some(1.0)" in {
    assert(approxEq(findRootOpt(0.0, 5.0)(x => x - 1.0).getOrElse(0.0), 1.0))
  }

  "findRootOpt(0.0, 2.0)(x => (x + 1.0) * (x - 1.0))" should "= Some(1.0)" in {
    assert(approxEq(findRootOpt(0.0, 2.0)(x => (x + 1.0) * (x - 1.0)).getOrElse(0.0), 1.0))
  }

  "findRootOpt(-2.0, 0.0)(x => (x + 1.0) * (x - 1.0))" should "= Some(-1.0)" in {
    assert(approxEq(findRootOpt(-2.0, 0.0)(x => (x + 1.0) * (x - 1.0)).getOrElse(0.0), -1.0))
  }

  "findRootOpt(0.0, 2.0)(x => x * x - 2.0)" should "= Some(math.sqrt(2.0))" in {
    assert(approxEq(findRootOpt(0.0, 2.0)(x => x * x - 2.0).getOrElse(0.0), math.sqrt(2.0)))
  }

  "findRootOpt(2.0,0.0)(x => x-1.0)" should "= None" in {
    assert(findRootOpt(2.0, 0.0)(x => x - 1.0) == None)
  }

  "findRootOpt(-1.0,-3.0)(x => x+2.0)" should "= None" in {
    assert(findRootOpt(-1.0, -3.0)(x => x + 2.0) == None)
  }

  "findRootOpt(0.0,2.0)(x => x+1.0)" should "= None" in {
    assert(findRootOpt(0.0, 2.0)(x => x + 1.0) == None)
  }

  "findRootOpt(0.0,2.0)(x => x-5.0)" should "= None" in {
    assert(findRootOpt(0.0, 2.0)(x => x - 5.0) == None)
  }

}

class PartB extends FlatSpec {

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

  "1.0 " should "approxEq 1.0" in {
    assert(approxEq(1.0, 1.0))
  }

  import OptionBisect._

  def testX(a: Double, x: Double): Boolean = {
    val y = a * x * x
    approxEq(x * x + y * y, 1.0)
  }

  "solveQuad(0.1)" should "work" in {
    assert(testX(0.1, solveQuad(0.1).getOrElse(0.0)))
  }

  "solveQuad(1.0)" should "work" in {
    assert(testX(1.0, solveQuad(1.0).getOrElse(0.0)))
  }

  "solveQuad(10.0)" should "work" in {
    assert(testX(10.0, solveQuad(10.0).getOrElse(0.0)))
  }

  "solveQuad(0.01)" should "work" in {
    assert(testX(0.01, solveQuad(0.01).getOrElse(0.0)))
  }



}

/* eof */

