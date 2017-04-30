/*
metropolis.scala

Example of a project which can be built as an assembly JAR, allowing stand-alone deployment and calling from R

*/

object Metropolis {

  import breeze.stats.distributions._

  val chain = MarkovChain.
    metropolisHastings(0.0, 
      (x: Double) => Uniform(x-0.5, x+0.5))(x => 
        Gaussian(0.0, 1.0).logPdf(x)).steps

  def main(args: Array[String]): Unit = {
    val n = if (args.size == 0) 10 else args(0).toInt
    chain.take(n).toArray.foreach(println)
  }

}

// eof


