
val x = (0 to 4).toList
// x: List[Int] = List(0, 1, 2, 3, 4)
val x2 = x map { x => x * 3 }
// x2: List[Int] = List(0, 3, 6, 9, 12)
val x3 = x map { _ * 3 }
// x3: List[Int] = List(0, 3, 6, 9, 12)
val x4 = x map { _ * 0.1 }
// x4: List[Double] = List(0.0, 0.1, 0.2, 0.30000000000000004, 0.4)


val xv = x.toVector
// xv: Vector[Int] = Vector(0, 1, 2, 3, 4)
val xv2 = xv map { _ * 0.2 }
// xv2: scala.collection.immutable.Vector[Double] = Vector(0.0, 0.2, 0.4, 0.6000000000000001, 0.8)
val xv3 = for (xi <- xv) yield (xi * 0.2)
// xv3: scala.collection.immutable.Vector[Double] = Vector(0.0, 0.2, 0.4, 0.6000000000000001, 0.8)


trait F[T] {
  def map(f: T => S): F[S]
}


val x5 = x map { x => List(x - 0.1, x + 0.1) }
// x5: List[List[Double]] = List(List(-0.1, 0.1), List(0.9, 1.1), List(1.9, 2.1), List(2.9, 3.1), List(3.9, 4.1))


val x6 = x flatMap { x => List(x - 0.1, x + 0.1) }
// x6: List[Double] = List(-0.1, 0.1, 0.9, 1.1, 1.9, 2.1, 2.9, 3.1, 3.9, 4.1)


val y = (0 to 12 by 2).toList
// y: List[Int] = List(0, 2, 4, 6, 8, 10, 12)
val xy = x flatMap { xi => y map { yi => xi * yi } }
// xy: List[Int] = List(0, 0, 0, 0, 0, 0, 0, 0, 2, 4, 6, 8, 10, 12, 0, 4, 8, 12, 16, 20, 24, 0, 6, 12, 18, 24, 30, 36, 0, 8, 16, 24, 32, 40, 48)


val xy2 = for {
  xi <- x
  yi <- y
} yield (xi * yi)
// xy2: List[Int] = List(0, 0, 0, 0, 0, 0, 0, 0, 2, 4, 6, 8, 10, 12, 0, 4, 8, 12, 16, 20, 24, 0, 6, 12, 18, 24, 30, 36, 0, 8, 16, 24, 32, 40, 48)


trait M[T] {
  def map(f: T => S): M[S]
  def flatMap(f: T => M[S]): M[S]
}


val three = Option(3)
// three: Option[Int] = Some(3)
val twelve = three map (_ * 4)
// twelve: Option[Int] = Some(12)


val four = Option(4)
// four: Option[Int] = Some(4)
val twelveB = three map (i => four map (i * _))
// twelveB: Option[Option[Int]] = Some(Some(12))


val twelveC = three flatMap (i => four map (i * _))
// twelveC: Option[Int] = Some(12)
val twelveD = for {
  i <- three
  j <- four
} yield (i * j)
// twelveD: Option[Int] = Some(12)


val oops: Option[Int] = None
// oops: Option[Int] = None
val oopsB = for {
  i <- three
  j <- oops
} yield (i * j)
// oopsB: Option[Int] = None
val oopsC = for {
  i <- oops
  j <- four
} yield (i * j)
// oopsC: Option[Int] = None


val nan = Double.NaN
3.0 * 4.0
// res0: Double = 12.0
3.0 * nan
// res1: Double = NaN
nan * 4.0
// res2: Double = NaN


val nanB = 0.0 / 0.0
// nanB: Double = NaN


val nanC=0/0
// This raises a runtime exception!


import breeze.linalg._
def safeChol(m: DenseMatrix[Double]): Option[DenseMatrix[Double]] = scala.util.Try(cholesky(m)).toOption
val m = DenseMatrix((2.0, 1.0), (1.0, 3.0))
val c = safeChol(m)
// c: Option[breeze.linalg.DenseMatrix[Double]] =
// Some(1.4142135623730951  0.0
// 0.7071067811865475  1.5811388300841898  )

val m2 = DenseMatrix((1.0, 2.0), (2.0, 3.0))
val c2 = safeChol(m2)
// c2: Option[breeze.linalg.DenseMatrix[Double]] = None


import com.github.fommil.netlib.BLAS.{getInstance => blas}
def dangerousForwardSolve(A: DenseMatrix[Double], y: DenseVector[Double]): DenseVector[Double] = {
  val yc = y.copy
  blas.dtrsv("L", "N", "N", A.cols, A.toArray, A.rows, yc.data, 1)
  yc
}
def safeForwardSolve(A: DenseMatrix[Double], y: DenseVector[Double]): Option[DenseVector[Double]] = scala.util.Try(dangerousForwardSolve(A, y)).toOption


def safeStd(A: DenseMatrix[Double], y: DenseVector[Double]): Option[DenseVector[Double]] = for {
  L <- safeChol(A)
  z <- safeForwardSolve(L, y)
} yield z

safeStd(m,DenseVector(1.0,2.0))
// res15: Option[breeze.linalg.DenseVector[Double]] = Some(DenseVector(0.7071067811865475, 0.9486832980505138))


import scala.concurrent.duration._
import scala.concurrent.{Future,ExecutionContext,Await}
import ExecutionContext.Implicits.global
val f1=Future{
  Thread.sleep(10000)
  1 }
val f2=Future{
  Thread.sleep(10000)
  2 }
val f3=for {
  v1 <- f1
  v2 <- f2
  } yield (v1+v2)
println(Await.result(f3,30.second))

