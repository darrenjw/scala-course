import org.scalatest.FlatSpec

// Tests using the "FlatSpec" style...

class SetSpec extends FlatSpec {

  "An empty Set" should "have size 0" in {
    assert(Set.empty.size == 0)
  }

  it should "produce NoSuchElementException when head is invoked" in {
    assertThrows[NoSuchElementException] {
      Set.empty.head
    }
  }

  "A Gamma(3.0,4.0)" should "have mean 12.0" in {
    import breeze.stats.distributions.Gamma
    val g = Gamma(3.0,4.0)
    val m = g.mean
    assert(math.abs(m - 12.0) < 0.000001)
  }

}

// eof
