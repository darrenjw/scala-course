import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import org.scalacheck.Prop.forAll

class StringSpecification extends AnyFlatSpec with Matchers {

  "Concatenated strings" should "startwith first string" in {
    forAll { (a: String, b: String) =>
      (a+b).startsWith(a)
    } 
  }

  it should "concatenate" in {
    forAll { (a: String, b: String) =>
      (a+b).length >= a.length && (a+b).length >= b.length
    }
  }

  it should "substring" in {
    forAll { (a: String, b: String, c: String) =>
      (a+b+c).substring(a.length, a.length+b.length) == b
    }
  }

}

// eof

