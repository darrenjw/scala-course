/*
pfilter.scala

Top level code for pfilter blog post

 */

package pfilter

object PFilter {

  import scala.language.higherKinds
  import scala.collection.parallel.immutable.ParVector
  import scala.collection.GenTraversable

  // Hardcode LogLik type
  type LogLik = Double
  // Use blank typeclasses for State, Observation, and Parameter
  trait State[T]
  trait Observation[T]
  trait Parameter[T]

  // My generic collection typeclass
  trait GenericColl[C[_]] {
    def map[A, B](ca: C[A])(f: A => B): C[B]
    def reduce[A](ca: C[A])(f: (A, A) => A): A
    def flatMap[A, B, D[B] <: GenTraversable[B]](ca: C[A])(f: A => D[B]): C[B]
    def zip[A, B](ca: C[A])(cb: C[B]): C[(A, B)]
    def length[A](ca: C[A]): Int
  }
  // Syntax for the typeclass
  implicit class GenericCollSyntax[A, C[A]](value: C[A]) {
    def map[B](f: A => B)(implicit inst: GenericColl[C]): C[B] = inst.map(value)(f)
    def reduce(f: (A, A) => A)(implicit inst: GenericColl[C]): A = inst.reduce(value)(f)
    def flatMap[B, D[B] <: GenTraversable[B]](f: A => D[B])(implicit inst: GenericColl[C]): C[B] = inst.flatMap(value)(f)
    def zip[B](cb: C[B])(implicit inst: GenericColl[C]): C[(A, B)] = inst.zip(value)(cb)
    def length(implicit inst: GenericColl[C]): Int = inst.length(value)
  }

  // Implementation for Vector
  implicit val vGC: GenericColl[Vector] = new GenericColl[Vector] {
    def map[A, B](ca: Vector[A])(f: A => B): Vector[B] = ca map f
    def reduce[A](ca: Vector[A])(f: (A, A) => A): A = ca reduce f
    def flatMap[A, B, D[B] <: GenTraversable[B]](ca: Vector[A])(f: A => D[B]): Vector[B] = ca flatMap f
    def zip[A, B](ca: Vector[A])(cb: Vector[B]): Vector[(A, B)] = ca zip cb
    def length[A](ca: Vector[A]) = ca.length
  }

  // Implementation for ParVector
  implicit val pvGC: GenericColl[ParVector] = new GenericColl[ParVector] {
    def map[A, B](ca: ParVector[A])(f: A => B): ParVector[B] = ca map f
    def reduce[A](ca: ParVector[A])(f: (A, A) => A): A = ca reduce f
    def flatMap[A, B, D[B] <: GenTraversable[B]](ca: ParVector[A])(f: A => D[B]): ParVector[B] = ca flatMap f
    def zip[A, B](ca: ParVector[A])(cb: ParVector[B]): ParVector[(A, B)] = ca zip cb
    def length[A](ca: ParVector[A]) = ca.length
  }

  // TODO: Implementation for Spark RDDs

  // Single step of a bootstrap particle filter
  def update[S: State, O: Observation, C[_]: GenericColl](
    dataLik: (S, O) => LogLik, stepFun: S => S
  )(x: C[S], o: O): (LogLik, C[S]) = {
    import breeze.stats.distributions.Poisson
    val xp = x map (stepFun(_))
    val lw = xp map (dataLik(_, o))
    val max = lw reduce (math.max(_, _))
    val rw = lw map (lwi => math.exp(lwi - max))
    val srw = rw reduce (_ + _)
    val l = rw.length
    val z = rw zip xp
    val rx = z flatMap (p => Vector.fill(Poisson(p._1 * l / srw).draw)(p._2))
    (max + math.log(srw / l), rx)
  }

  // Run a bootstrap particle filter over a collection of observations
  def pFilter[S: State, O: Observation, C[_]: GenericColl, D[O] <: GenTraversable[O]](
    x0: C[S], data: D[O], dataLik: (S, O) => LogLik, stepFun: S => S
  ): (LogLik, C[S]) = {
    val updater = update[S, O, C](dataLik, stepFun) _
    data.foldLeft((0.0, x0))((prev, o) => {
      val next = updater(prev._2, o)
      (prev._1 + next._1, next._2)
    })
  }

  // Marginal log likelihood estimation
  def pfMll[S: State, P: Parameter, O: Observation, C[_]: GenericColl, D[O] <: GenTraversable[O]](
    simX0: P => C[S], stepFun: P => S => S, dataLik: P => (S, O) => LogLik, data: D[O]
  ): (P => LogLik) = (th: P) => pFilter(simX0(th), data, dataLik(th), stepFun(th))._1

  // Main method
  def main(args: Array[String]): Unit = {
    println("Hi")
    import Examples._
    arTest
    println("Bye")
  }

}

object Examples {

  import PFilter._

  // Simple test for an AR(1) model
  def arTest: Unit = {
    import breeze.linalg._
    import breeze.stats.distributions._
    println("AR(1) test start")
    // simulate some data from an AR(1) model with noise
    val inNoise = Gaussian(0.0, 1.0).sample(99)
    val state = DenseVector(inNoise.scanLeft(0.0)((s, i) => 0.8 * s + i).toArray)
    val noise = DenseVector(Gaussian(0.0, 2.0).sample(100).toArray)
    val data = (state + noise).toArray.toList
    import breeze.plot._
    val f = Figure()
    val p0 = f.subplot(0)
    val idx = linspace(1, 100, 100)
    p0 += plot(idx, state)
    p0 += plot(idx, data, '.')
    p0.xlabel = "Time"
    p0.ylabel = "Value"
    // now try to recover autoregression coefficient
    implicit val dState = new State[Double] {}
    implicit val dObs = new Observation[Double] {}
    implicit val dPar = new Parameter[Double] {}
    val mll = pfMll(
      (th: Double) => Gaussian(0.0, 10.0).sample(10000).toVector.par,
      (th: Double) => (s: Double) => Gaussian(th * s, 1.0).draw,
      (th: Double) => (s: Double, o: Double) => Gaussian(s, 2.0).logPdf(o),
      data
    )
    val x = linspace(0.0, 0.99, 100)
    val y = x map (mll(_))
    //println(y)
    val p1 = f.subplot(2, 1, 1)
    p1 += plot(x, y)
    p1.xlabel = "theta"
    p1.ylabel = "mll"
    f.saveas("plot.png")
    println("AR(1) test finish")
  }

}

// eof

