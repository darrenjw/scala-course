/*
monte-carlo.scala
Integration via rejection sampling
Integrate the standard normal PDF from -5 to 5 to get an estimate close to 1...
Simulate points uniformly over a bounding box and look at fraction of points
falling under the PDF
*/

import scala.math._
import breeze.stats.distributions.Uniform
import breeze.linalg._
import scala.annotation.tailrec

object MonteCarlo {

  def f(x: Double): Double = math.exp(-x * x / 2) / math.sqrt(2 * Pi)

  // Idiomatic Breeze solution
  def mc1(its: Int): Int = {
    val x = runif(its, -5.0, 5.0)
    val y = runif(its, 0.0, 0.5)
    val fx = x map { f(_) }
    sum((y <:< fx) map { xi => if (xi == true) 1 else 0 })
  }

  // Fast, memory-efficient tail call
  def mc2(its: Long): Long = {
    @tailrec def mc(its: Long, acc: Long): Long = {
      if (its == 0) acc else {
        val x = runif(-5.0, 5.0)
        val y = runif(0.0, 0.5)
        if (y < f(x)) mc(its - 1, acc + 1) else mc(its - 1, acc)
      }
    }
    mc(its, 0)
  }

  // Parallel version
  def mc3(its: Long,NP: Int = 8): Long = {
    val N = its / NP // assuming NP | its
    (1 to NP).par.map { x => mc2(N) }.sum
  }

  // R-like functions for Uniform random numbers
  def runif(n: Int, l: Double, u: Double) = DenseVector[Double](Uniform(l, u).sample(n).toArray)
  def runif(l: Double, u: Double) = Uniform(l, u).draw

  // Function for timing
  def time[A](f: => A) = {
    val s = System.nanoTime
    val ret = f
    println("time: " + (System.nanoTime - s) / 1e6 + "ms")
    ret
  }

  // Main method for running the code
  def main(args: Array[String]) = {
    val N = 10000000 // 10^7 is as big as mc1() can really cope with
    println("Running with " + N + " iterations")
    println("Idiomatic vectorised solution")
    time { println(5.0 * mc1(N) / N) }
    println("Fast efficient (serial) tail call")
    time { println(5.0 * mc2(N) / N) }
    println("Parallelised version")
    time { println(5.0 * mc3(N) / N) }
    println("Vary size of parallel collection")
      (1 to 12).foreach{ i =>
        println("NP = "+i)
        time(mc3(N,i))
      }
    println("Done")
  }



}

