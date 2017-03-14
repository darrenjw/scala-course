
val l1 = List(6,7,8,9,10)
// l1: List[Int] = List(6, 7, 8, 9, 10)
l1.head
// res0: Int = 6
l1.tail
// res1: List[Int] = List(7, 8, 9, 10)
l1(0)
// res2: Int = 6
l1(2)
// res3: Int = 8
l1(2) = 22
// <console>:9: error: value update is not a member of List[Int]
//               l1(2) = 22
//               ^
l1
// res5: List[Int] = List(6, 7, 8, 9, 10)
val l2: List[Double] = List(1,2,3)
// l2: List[Double] = List(1.0, 2.0, 3.0)


import scala.collection.mutable
// import scala.collection.mutable
val l3 = mutable.MutableList(5,6,7,8,9)
// l3: MutableList[Int] = MutableList(5, 6, 7, 8, 9)
l3.head
// res6: Int = 5
l3.tail
// res7: MutableList[Int] = MutableList(6, 7, 8, 9)
l3(2)
// res8: Int = 7
l3(2) = 22
l3
// res10: MutableList[Int] = MutableList(5, 6, 22, 8, 9)


val list1 = List(3,4,5,6)
// list1: List[Int] = List(3, 4, 5, 6)
val list2 = 2 :: list1
// list2: List[Int] = List(2, 3, 4, 5, 6)
val list3 = list1 ++ list2
// list3: List[Int] = List(3, 4, 5, 6, 2, 3, 4, 5, 6)
list3.take(3)
// res0: List[Int] = List(3, 4, 5)
list3.drop(2)
// res1: List[Int] = List(5, 6, 2, 3, 4, 5, 6)
list3.filter(_<5)
// res2: List[Int] = List(3, 4, 2, 3, 4)
val list4 = list1 map (_*2)
// list4: List[Int] = List(6, 8, 10, 12)
list4.length
// res3: Int = 4
list4.sum
// res4: Int = 36
list4.reverse
// res5: List[Int] = List(12, 10, 8, 6)
list1.foldLeft(0)(_+_)
// res6: Int = 18
list1.scanLeft(0)(_+_)
// res7: List[Int] = List(0, 3, 7, 12, 18)
list1.reduce(_+_)
// res8: Int = 18
list1.sortWith(_>_)
// res9: List[Int] = List(6, 5, 4, 3)
list1.foreach{e => println(e)}
// 3
// 4
// 5
// 6


val arr1 = Array(2,3,4,5)
// arr1: Array[Int] = Array(2, 3, 4, 5)
arr1(1)=6
arr1
// res12: Array[Int] = Array(2, 6, 4, 5)
val arr2 = arr1 map(_+1)
// arr2: Array[Int] = Array(3, 7, 5, 6)
arr1
// res13: Array[Int] = Array(2, 6, 4, 5)
arr1.reduce(_+_)
// res14: Int = 17
arr1 ++ arr2
// res15: Array[Int] = Array(2, 6, 4, 5, 3, 7, 5, 6)


val vec1 = Vector(6,5,4,3)
// vec1: Vector[Int] = Vector(6, 5, 4, 3)
vec1(2)
// res16: Int = 4
vec1.toList
// res17: List[Int] = List(6, 5, 4, 3)
vec1 map (_*0.5)
// res18: Vector[Double] = Vector(3.0, 2.5, 2.0, 1.5)
val vec2 = vec1.updated(2,10)
// vec2: Vector[Int] = Vector(6, 5, 10, 3)
vec2.length
// res19: Int = 4
vec1
// res20: Vector[Int] = Vector(6, 5, 4, 3)
vec2
// res21: Vector[Int] = Vector(6, 5, 10, 3)
vec1 ++ vec2
// res22: Vector[Int] = Vector(6, 5, 4, 3, 6, 5, 10, 3)
vec1 :+ 22
// res23: Vector[Int] = Vector(6, 5, 4, 3, 22)
33 +: vec1
// res24: Vector[Int] = Vector(33, 6, 5, 4, 3)
vec1.reduce(_+_)
// res25: Int = 18
Vector.fill(5)(0)
// res26: Vector[Int] = Vector(0, 0, 0, 0, 0)
Vector.fill(5)(math.random)
// res27: Vector[Double] = Vector(0.35160713387930087, 0.0892297132191533, 0.9061352705408103, 0.7020295276855067, 0.09434089580898397)  


val map1 = Map("a"->10,"b"->20,"c"->30)
// map1: Map[String,Int] = Map(a -> 10, b -> 20, c -> 30)
map1("b")
// res28: Int = 20
val map2 = map1.updated("d",40)
// map2: Map[String,Int] = Map(a -> 10, b -> 20, c -> 30, d -> 40)
map1
// res29: Map[String,Int] = Map(a -> 10, b -> 20, c -> 30)
map2
// res30: Map[String,Int] = Map(a -> 10, b -> 20, c -> 30, d -> 40)
map2.updated("d",50)
// res31: Map[String,Int] = Map(a -> 10, b -> 20, c -> 30, d -> 50)
map2.keys
// res32: Iterable[String] = Set(a, b, c, d)
map2.values
// res33: Iterable[Int] = MapLike(10, 20, 30, 40)
map2 mapValues (_*2)
// res34: Map[String,Int] = Map(a -> 20, b -> 40, c -> 60, d -> 80)
val mapK = List(3,4,5,6)
// mapK: List[Int] = List(3, 4, 5, 6)
val mapV = List(0.3,0.4,0.5,0.6)
// mapV: List[Double] = List(0.3, 0.4, 0.5, 0.6)
val pairs = mapK zip mapV
// pairs: List[(Int, Double)] = List((3,0.3), (4,0.4), (5,0.5), (6,0.6))
val map3 = pairs.toMap
// map3: Map[Int,Double] = Map(3 -> 0.3, 4 -> 0.4, 5 -> 0.5, 6 -> 0.6)  


val stream1 = Stream(1,2,3)
// stream1: Stream[Int] = Stream(1, ?)
val stream2 = 0 #:: stream1
// stream2: Stream[Int] = Stream(0, ?)
stream2.toList
// res35: List[Int] = List(0, 1, 2, 3)
stream2.foldLeft(0)(_+_)
// res36: Int = 6
def fibFrom(a: Int,b: Int): Stream[Int] =
  a #:: fibFrom(b,a+b)
// fibFrom: (a: Int, b: Int)Stream[Int]
val fib = fibFrom(1,1)
// fib: Stream[Int] = Stream(1, ?)
fib.take(8).toList
// res37: List[Int] = List(1, 1, 2, 3, 5, 8, 13, 21)
val naturals = Stream.iterate(1)(_+1)
// naturals: Stream[Int] = Stream(1, ?)
naturals.take(5).toList
// res38: List[Int] = List(1, 2, 3, 4, 5)
val evens = naturals map (_*2)
// evens: Stream[Int] = Stream(2, ?)
evens.take(6).toList
// res39: List[Int] = List(2, 4, 6, 8, 10, 12)
val triangular = naturals.scanLeft(0)(_+_).drop(1)
// triangular: Stream[Int] = Stream(1, ?)
triangular.take(8).toList
// res40: List[Int] = List(1, 3, 6, 10, 15, 21, 28, 36)  


1 to 5
// res41: Range.Inclusive = Range(1, 2, 3, 4, 5)
0 to 5
// res42: Range.Inclusive = Range(0, 1, 2, 3, 4, 5)
0 until 5
// res43: Range = Range(0, 1, 2, 3, 4)
0 to 10 by 2
// res44: Range = Range(0, 2, 4, 6, 8, 10)
val range = 0 to 10 by 2
// range: Range = Range(0, 2, 4, 6, 8, 10)
range.toList
// res45: List[Int] = List(0, 2, 4, 6, 8, 10)
(0 to 5) map (_*2)
// res46: IndexedSeq[Int] = Vector(0, 2, 4, 6, 8, 10)
(0 to 5).sum
// res47: Int = 15  


def meanList(ld: List[Double]): Double = ld.sum/ld.length
// meanList: (ld: List[Double])Double
meanList(List(1,2,3,4))
// res0: Double = 2.5


def mean(sq: Seq[Double]): Double = sq.sum/sq.length
// mean: (sq: Seq[Double])Double
mean(List(2,3,4))
// res1: Double = 3.0
mean(Vector(1,2,3))
// res2: Double = 2.0


def repD(ld: List[Double], n: Int): List[Double] =
  if (n <= 1) ld else ld ++ repD(ld,n-1)
// repD: (ld: List[Double], n: Int)List[Double]
repD(List(1,2,3),3)
// res3: List[Double] = List(1.0, 2.0, 3.0, 1.0, 2.0, 3.0, 1.0, 2.0, 3.0)


def rep[T](l: List[T], n: Int): List[T] =
  if (n <=1) l else l ++ rep(l,n-1)
// rep: [T](l: List[T], n: Int)List[T]
rep(List(1,2,3),3)
// res4: List[Int] = List(1, 2, 3, 1, 2, 3, 1, 2, 3)
rep(List(1.0,2.0),3)
// res5: List[Double] = List(1.0, 2.0, 1.0, 2.0, 1.0, 2.0)
rep(List("a","b","c"),2)
// res6: List[String] = List(a, b, c, a, b, c)


def ssd(sq: Seq[Double]): Double = (sq map (x => x*x)).sum
// ssd: (sq: Seq[Double])Double
ssd(List(2,3,4))
// res7: Double = 29.0


def ssg[T](sq: Seq[T]): T = (sq map (x => x*x)).sum
// <console>:7: error: value * is not a member of type parameter T
//        def ssg[T](sq: Seq[T]): T = (sq map (x => x*x)).sum


def ss[T](sq: Seq[T])(implicit num: Numeric[T]): T = {
  import num._
  (sq map (x => x*x)).sum
  }
// ss: [T](sq: Seq[T])(implicit num: Numeric[T])T
ss(List(2,3,4))
// res8: Int = 29
ss(Vector(1.0,2.0,3.0))
// res9: Double = 14.0


def ssg[T: Numeric](sq: Seq[T]): T = {
  val num = implicitly[Numeric[T]]
  import num._
  (sq map (x => x*x)).sum
  }
// ssg: [T](sq: Seq[T])(implicit evidence$1: Numeric[T])T
ssg(List(2,3,4))
// res10: Int = 29
ssg(Vector(1.0,2.0,3.0))
// res11: Double = 14.0


import spire.math._
import spire.implicits._
def ss[T: Numeric](sq: Seq[T]): T =
  (sq map (x=>x*x)).reduce(_+_)
ss(List(1,2,3))
// res0: Int = 14
ss(Vector(2.0,3.0,4.0))
// res1: Double = 29.0


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

