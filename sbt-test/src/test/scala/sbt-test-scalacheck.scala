import org.scalatest.matchers.should.Matchers

import org.scalacheck._
import org.scalacheck.Prop.{forAll,propBoolean}

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

class GammaSpec extends Properties("Gamma") with Matchers {

  import breeze.stats.distributions.Gamma

  val tol = 1e-8
  val big = 1e100

  property("mean") =
    forAll { (a: Double, b: Double) =>
      ((a > tol) && (a < big) && (b > tol) && (b < big)) ==> {
        Gamma(a,b).mean === a*b +- tol
      }
    }

}

class StringSpecification extends Properties("String") with Matchers {

  property("startwith first string") =
    forAll { (a: String, b: String) =>
      (a+b).startsWith(a)
    } 

  property("concatenate bound") =
    forAll { (a: String, b: String) =>
      (a+b).length >= a.length && (a+b).length >= b.length
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

// eof

