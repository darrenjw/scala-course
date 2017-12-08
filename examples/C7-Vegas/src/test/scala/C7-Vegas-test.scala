import org.scalatest.FlatSpec

class SetSpec extends FlatSpec {

 "A Poisson(10.0)" should "have mean 10.0" in {
    import breeze.stats.distributions.Poisson
    val p = Poisson(10.0)
    val m = p.mean
    assert(math.abs(m - 10.0) < 0.000001)
  }

}

