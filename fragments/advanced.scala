
trait GenericColl[C[_]] {
  def map[A, B](ca: C[A])(f: A => B): C[B]
  def reduce[A](ca: C[A])(f: (A, A) => A): A
  def flatMap[A, B, D[B] <: GenTraversable[B]](ca: C[A])(f: A => D[B]): C[B]
  def zip[A, B](ca: C[A])(cb: C[B]): C[(A, B)]
  def length[A](ca: C[A]): Int
}


def update[S: State, O: Observation, C[_]: GenericColl](
  dataLik: (S, O) => LogLik, stepFun: S => S
)(x: C[S], o: O): (LogLik, C[S]) = {
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


def pFilter[S: State, O: Observation, C[_]: GenericColl, D[O] <: GenTraversable[O]](
  x0: C[S], data: D[O], dataLik: (S, O) => LogLik, stepFun: S => S
): (LogLik, C[S]) = {
  val updater = update[S, O, C](dataLik, stepFun) _
  data.foldLeft((0.0, x0))((prev, o) => {
    val next = updater(prev._2, o)
    (prev._1 + next._1, next._2)
  })
}


def pfMll[S: State, P: Parameter, O: Observation, 
            C[_]: GenericColl, D[O] <: GenTraversable[O]](
  simX0: P => C[S], stepFun: P => S => S, 
  dataLik: P => (S, O) => LogLik, data: D[O]
): (P => LogLik) = (th: P) => 
       pFilter(simX0(th), data, dataLik(th), stepFun(th))._1


val inNoise = Gaussian(0.0, 1.0).sample(99)
val state = DenseVector(inNoise.scanLeft(0.0)((s, i) => 0.8 * s + i).toArray)
val noise = DenseVector(Gaussian(0.0, 2.0).sample(100).toArray)
val data = (state + noise).toArray.toList


val mll = pfMll(
  (th: Double) => Gaussian(0.0, 10.0).sample(10000).toVector.par,
  (th: Double) => (s: Double) => Gaussian(th * s, 1.0).draw,
  (th: Double) => (s: Double, o: Double) => Gaussian(s, 2.0).logPdf(o),
  data
)

