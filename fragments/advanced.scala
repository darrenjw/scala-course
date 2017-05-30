
Vector(1,2,3).sum
// res0: Int = 6
List(1.0,5.0).sum
// res1: Double = 6.0


Vector(1,2,3).mean
// <console>:8: error: value mean is not a member of Vector[Int]
//               Vector(1,2,3).mean
//                             ^


object Meanable {
  def mean[T: Numeric](it: Iterable[T]): Double =
    it.map(implicitly[Numeric[T]].toDouble(_)).
      sum / it.size
}


object Meanable {
  def mean[T](it: Iterable[T])(
    implicit num: Numeric[T]): Double =
    it.map(num.toDouble(_)).sum / it.size
}


import Meanable._
// import Meanable._
mean(Vector(1,2,3))
// res3: Double = 2.0
mean(List(1.0,5.0))
// res4: Double = 3.0


implicit class MeanableInstance[T: Numeric](
    it: Iterable[T]) {
  def mean[T] = Meanable.mean(it)
}


Vector(1,2,3).mean
// res5: Double = 2.0
List(1.0,3.0,5.0,7.0).mean
// res6: Double = 4.0


trait CsvRow[T] {
 def toCsv(row: T): String
}


implicit class CsvRowSyntax[T](row: T) {
  def toCsv(implicit inst: CsvRow[T]) = inst.toCsv(row)
}


def printRows[T: CsvRow](it: Iterable[T]): Unit =
  it.foreach(row => println(row.toCsv))


case class MyState(x: Int, y: Double)


implicit val myStateCsvRow = new CsvRow[MyState] {
  def toCsv(row: MyState) = row.x.toString+","+row.y
}


MyState(1,2.0).toCsv
// res7: String = 1,2.0
printRows(List(MyState(1,2.0),MyState(2,3.0)))
// 1,2.0
// 2,3.0


implicit val vectorDoubleCsvRow =
    new CsvRow[Vector[Double]] {
  def toCsv(row: Vector[Double]) = row.mkString(",")
}
// vectorDoubleCsvRow: CsvRow[Vector[Double]] =
//   $anon$1@4604e051

Vector(1.0,2.0,3.0).toCsv
// res9: String = 1.0,2.0,3.0
printRows(List(Vector(1.0,2.0),Vector(4.0,5.0),
  Vector(3.0,3.0)))
// 1.0,2.0
// 4.0,5.0
// 3.0,3.0


import scala.language.higherKinds
trait Thinnable[F[_]] {
  def thin[T](f: F[T], th: Int): F[T]
}


implicit class ThinnableSyntax[T,F[T]](value: F[T]) {
  def thin(th: Int)(implicit inst: Thinnable[F]): F[T] =
    inst.thin(value,th)
}


implicit val streamThinnable: Thinnable[Stream] =
    new Thinnable[Stream] {
  def thin[T](s: Stream[T],th: Int): Stream[T] = {
  val ss = s.drop(th-1)
  if (ss.isEmpty) Stream.empty else
    ss.head #:: thin(ss.tail, th)
  }
}


Stream.iterate(0)(_ + 1).
  drop(10).
  thin(2).
  take(5).
  toArray
// res11: Array[Int] = Array(11, 13, 15, 17, 19)


trait GenericColl[C[_]] {
  def map[A, B](ca: C[A])(f: A => B): C[B]
  def reduce[A](ca: C[A])(f: (A, A) => A): A
  def flatMap[A, B, D[B] <: GenTraversable[B]](
    ca: C[A])(f: A => D[B]): C[B]
  def zip[A, B](ca: C[A])(cb: C[B]): C[(A, B)]
  def length[A](ca: C[A]): Int
}


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
    val rx = z flatMap { case (rwi, xpi) => 
      Vector.fill(Poisson(rwi * l / srw).draw)(xpi) }
    (max + math.log(srw / l), rx)
}


def pFilter[S: State, O: Observation,
  C[_]: GenericColl, D[O] <: GenTraversable[O]](
  x0: C[S], data: D[O],
  dataLik: (S, O) => LogLik, stepFun: S => S
  ): (LogLik, C[S]) = {
    val updater = update[S, O, C](dataLik, stepFun) _
    data.foldLeft((0.0, x0))((prev, o) => {
      val (oll, ox) = prev
      val (ll, x) = updater(ox, o)
      (oll + ll, x)
    })
}


def pfMll[S: State, P: Parameter, O: Observation, 
    C[_]: GenericColl, D[O] <: GenTraversable[O]](
  simX0: P => C[S], stepFun: P => S => S, 
  dataLik: P => (S, O) => LogLik, data: D[O]
): (P => LogLik) = (th: P) => 
    pFilter(simX0(th), data, dataLik(th), stepFun(th))._1


val inNoise = Gaussian(0.0, 1.0).sample(99)
val state = DenseVector(inNoise.scanLeft(0.0)(
  (s, i) => 0.8 * s + i).toArray)
val noise = DenseVector(
  Gaussian(0.0, 2.0).sample(100).toArray)
val data = (state + noise).toArray.toList


val mll = pfMll(
  (th: Double) => Gaussian(0.0, 10.0).
    sample(10000).toVector.par,
  (th: Double) => (s: Double) =>
    Gaussian(th * s, 1.0).draw,
  (th: Double) => (s: Double, o: Double) =>
    Gaussian(s, 2.0).logPdf(o),
  data
)


libraryDependencies += "org.typelevel" %% "cats" % "0.9.0"


import cats.Monoid
// import cats.Monoid
import cats.syntax.semigroup._
// import cats.syntax.semigroup._
import cats.instances.all._
// import cats.instances.all._


1 |+| 3
// res0: Int = 4
1.0 |+| 2.0
// res1: Double = 3.0
"Hi" |+| "There"
// res2: String = HiThere
List(1,2,3) |+| List(4,5)
// res3: List[Int] = List(1, 2, 3, 4, 5)


val m1 = Map("a" -> 2, "b" -> 3)
// m1: Map[String,Int] = Map(a -> 2, b -> 3)
val m2 = Map("b" -> 4, "c" -> 5)
// m2: Map[String,Int] = Map(b -> 4, c -> 5)
m1 |+| m2
// res3: Map[String,Int] = Map(b -> 7, c -> 5, a -> 2)


scala.io.Source.
  fromFile("/usr/share/dict/words").
  getLines.
  map(_.trim).
  map(_.toLowerCase).
  flatMap(_.toCharArray).
  filter(_ > '/').
  filter(_ < '}').
  map(ch => Map(ch -> 1)).
  reduce(_ |+| _)
// res4: Map[Char,Int] = Map(e -> 88833, s -> 90113,
//  x -> 2124, n -> 57144, j -> 1948, y -> 12652,
// t -> 53006, u -> 26118, f -> 10675, a -> 64439, ...

