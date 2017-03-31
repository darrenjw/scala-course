/*
gamma-test.scala

Test the gamma random number generator in Breeze

 */

object GammaTest {

  import math.{abs,sqrt}
  import breeze.stats.meanAndVariance
  import breeze.stats.distributions.Gamma

  def gammaTest(N: Int, a: Double, b: Double): Unit = {
    println(s"Testing Gamma($a,$b) with $N trials")
    val mean = a*b
    val variance = a*b*b
    val gammas = Gamma(a,b).sample(N)
    val stats = meanAndVariance(gammas)
    val xbar = stats.mean
    val s2 = stats.variance
    println(s"True mean: $mean  Sample mean: $xbar")
    val zscore = (xbar - mean)/sqrt(variance/N)
    println(s"z-score is $zscore")
    assert(abs(zscore) < 3.0)
    println(s"True variance: $variance Sample variance: $s2")    
    }

  def main(args: Array[String]): Unit = {
    println("Testing Breeze's Gamma generator")
    val N = 10000000
    gammaTest(N,2.0,3.0)
    gammaTest(N,1.0,2.0)
    gammaTest(N,5.0,1.0)
    gammaTest(N,1.0,5.0)
    gammaTest(N,0.5,3.0)
    gammaTest(N,0.2,1.0)
    gammaTest(N,0.2,4.0)
    println("Test complete")
  }

}

// eof

