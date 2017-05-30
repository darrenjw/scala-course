/*
mcmc-stream.scala


 */

import breeze.linalg._
import breeze.plot._
import breeze.stats.distributions._
import breeze.stats.meanAndVariance
import annotation.tailrec

object MCMC {

  def mcmcSummary(dv: DenseVector[Double]): Figure = {
    val len = dv.length
    val mav = meanAndVariance(dv)
    val mean = mav.mean
    val variance = mav.variance
    println(s"Iters=$len, Mean=$mean, variance=$variance")
    val f = Figure("MCMC Summary")
    f.height = 1000
    f.width = 1200
    val p0 = f.subplot(1, 2, 0)
    p0 += plot(linspace(1, len, len), dv)
    p0.xlabel = "Iteration"
    p0.ylabel = "Value"
    p0.title = "Trace plot"
    val p1 = f.subplot(1, 2, 1)
    p1 += hist(dv, 100)
    p1.xlabel = "Value"
    p1.title = "Marginal density"
    f
  }

  def time[A](f: => A) = {
    val s = System.nanoTime
    val ret = f
    println("time: " + (System.nanoTime - s) / 1e6 + "ms")
    ret
  }

  def metrop1(n: Int = 1000, eps: Double = 0.5): DenseVector[Double] = {
    val vec = DenseVector.fill(n)(0.0)
    var x = 0.0
    var oldll = Gaussian(0.0, 1.0).logPdf(x)
    vec(0) = x
    (1 until n).foreach { i =>
      val can = x + Uniform(-eps, eps).draw
      val loglik = Gaussian(0.0, 1.0).logPdf(can)
      val loga = loglik - oldll
      if (math.log(Uniform(0.0, 1.0).draw) < loga) {
        x = can
        oldll = loglik
      }
      vec(i) = x
    }
    vec
  }

  def metrop2(n: Int = 1000, eps: Double = 0.5): Unit = {
    var x = 0.0
    var oldll = Gaussian(0.0, 1.0).logPdf(x)
    (1 to n).foreach { i =>
      val can = x + Uniform(-eps, eps).draw
      val loglik = Gaussian(0.0, 1.0).logPdf(can)
      val loga = loglik - oldll
      if (math.log(Uniform(0.0, 1.0).draw) < loga) {
        x = can
        oldll = loglik
      }
      println(x)
    }
  }

  @tailrec
  def metrop3(n: Int = 1000, eps: Double = 0.5, x: Double = 0.0, oldll: Double = Double.MinValue): Unit = {
    if (n > 0) {
      println(x)
      val can = x + Uniform(-eps, eps).draw
      val loglik = Gaussian(0.0, 1.0).logPdf(can)
      val loga = loglik - oldll
      if (math.log(Uniform(0.0, 1.0).draw) < loga)
        metrop3(n - 1, eps, can, loglik)
      else
        metrop3(n - 1, eps, x, oldll)
    }
  }

  @tailrec
  def metrop4(n: Int = 1000, eps: Double = 0.5, x: Double = 0.0, oldll: Double = Double.MinValue, acc: List[Double] = Nil): DenseVector[Double] = {
    if (n == 0)
      DenseVector(acc.reverse.toArray)
    else {
      val can = x + Uniform(-eps, eps).draw
      val loglik = Gaussian(0.0, 1.0).logPdf(can)
      val loga = loglik - oldll
      if (math.log(Uniform(0.0, 1.0).draw) < loga)
        metrop4(n - 1, eps, can, loglik, can :: acc)
      else
        metrop4(n - 1, eps, x, oldll, x :: acc)
    }
  }

  def newState(x: Double, oldll: Double, eps: Double): (Double, Double) = {
    val can = x + Uniform(-eps, eps).draw
    val loglik = Gaussian(0.0, 1.0).logPdf(can)
    val loga = loglik - oldll
    if (math.log(Uniform(0.0, 1.0).draw) < loga) (can, loglik) else (x, oldll)
  }

  @tailrec
  def metrop5(n: Int = 1000, eps: Double = 0.5, x: Double = 0.0, oldll: Double = Double.MinValue): Unit = {
    if (n > 0) {
      println(x)
      val ns = newState(x, oldll, eps)
      metrop5(n - 1, eps, ns._1, ns._2)
    }
  }

  @tailrec
  def metrop5b(n: Int = 1000, eps: Double = 0.5, x: Double = 0.0, oldll: Double = Double.MinValue): Unit = {
    if (n > 0) {
      println(x)
      val (nx, ll) = newState(x, oldll, eps)
      metrop5b(n - 1, eps, nx, ll)
    }
  }

  @tailrec
  def metrop6(n: Int = 1000, eps: Double = 0.5, x: Double = 0.0, oldll: Double = Double.MinValue, acc: List[Double] = Nil): DenseVector[Double] = {
    if (n == 0) DenseVector(acc.reverse.toArray) else {
      val (nx, ll) = newState(x, oldll, eps)
      metrop6(n - 1, eps, nx, ll, nx :: acc)
    }
  }

  def nextState(eps: Double)(state: (Double, Double)): (Double, Double) = {
    val (x, oldll) = state
    val can = x + Uniform(-eps, eps).draw
    val loglik = Gaussian(0.0, 1.0).logPdf(can)
    val loga = loglik - oldll
    if (math.log(Uniform(0.0, 1.0).draw) < loga) (can, loglik) else (x, oldll)
  }

  def metrop7(eps: Double = 0.5, x: Double = 0.0, oldll: Double = Double.MinValue): Stream[Double] =
    Stream.iterate((x, oldll))(nextState(eps)) map (_._1)

  def thin[T](s: Stream[T], th: Int): Stream[T] = {
    val ss = s.drop(th - 1)
    if (ss.isEmpty) Stream.empty else
      ss.head #:: thin(ss.tail, th)
  }

  def kernel(x: Double): Rand[Double] = for {
    innov <- Uniform(-0.5, 0.5)
    can = x + innov
    oldll = Gaussian(0.0, 1.0).logPdf(x)
    loglik = Gaussian(0.0, 1.0).logPdf(can)
    loga = loglik - oldll
    u <- Uniform(0.0, 1.0)
  } yield if (math.log(u) < loga) can else x

  def main(arg: Array[String]): Unit = {
    println("Hi")
    metrop1(10).foreach(println)
    metrop2(10)
    metrop3(10)
    metrop4(10).foreach(println)
    metrop5(10)
    metrop6(10).foreach(println)
    metrop7().take(10).foreach(println)
    MarkovChain(0.0)(kernel).steps.take(10).foreach(println)
    MarkovChain.metropolisHastings(0.0, (x: Double) => Uniform(x - 0.5, x + 0.5))(x => Gaussian(0.0, 1.0).logPdf(x)).steps.take(10).toArray.foreach(println)
    // plot output to check it looks OK
    mcmcSummary(DenseVector(MarkovChain.metropolisHastings(0.0,(x: Double)=>Uniform(x-0.5,x+0.5))(x=>Gaussian(0.0,1.0).logPdf(x)).steps.take(100000).toArray))

    // timings...
    val N=1000000
    println("metrop1:")
    time(metrop1(N))
    println("metrop4:")
    time(metrop4(N))
    println("metrop6:")
    time(metrop6(N))
    println("metrop7:")
    time(metrop7().take(N).toArray)
    println("MarkovChain with custom kernel")
    time(MarkovChain(0.0)(kernel).steps.take(N).toArray)
    println("MarkovChain.metropolisHastings:")
    time(MarkovChain.metropolisHastings(0.0, (x: Double) => Uniform(x - 0.5, x + 0.5))(x => Gaussian(0.0, 1.0).logPdf(x)).steps.take(N).toArray)
    println("Bye")
  }

}

// eof

