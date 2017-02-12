import org.scalatest.FunSuite

// Here using FunSuite style - but other possibilities...
// http://www.scalatest.org/user_guide/selecting_a_style

class SetSuite extends FunSuite {

  test("An empty Set should have size 0") {
    assert(Set.empty.size == 0)
  }

  test("A Gaussian sample of length 10 should have length 10") {
    import breeze.stats.distributions.Gaussian
    val x = Gaussian(2.0,4.0).sample(10)
    assert(x.length === 10)
  }

  test("Cats map merge") {
    import cats.instances.all._
    import cats.syntax.semigroup._
    val m1 = Map("a"->1,"b"->2)
    val m2 = Map("b"->2,"c"->1)
    val m3 = m1 |+| m2
    val m4 = Map("b" -> 4, "c" -> 1, "a" -> 1)
    assert(m3 === m4)
  }

}


// eof
