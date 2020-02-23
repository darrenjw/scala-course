/*
metropolis.scala

Example of a project which can be built as an assembly JAR, allowing stand-alone deployment and calling from R

*/

object Metropolis {

  import breeze.stats.distributions._

  def kernel(x: Double): Rand[Double] = for {
    innov <- Uniform(-0.5, 0.5)
    can = x + innov
    oldll = Gaussian(0.0, 1.0).logPdf(x)
    loglik = Gaussian(0.0, 1.0).logPdf(can)
    loga = loglik - oldll
    u <- Uniform(0.0, 1.0)
  } yield if (math.log(u) < loga) can else x

  val chain = Stream.iterate(0.0)(kernel(_).draw)

  def main(args: Array[String]): Unit = {
    val n = if (args.size == 0) 10 else args(0).toInt
    chain.take(n).toArray.foreach(println)
  }

}

// eof


