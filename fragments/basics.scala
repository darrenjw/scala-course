
Welcome to Scala 2.12.1 (OpenJDK 64-Bit Server VM,
    Java 1.8.0_121).
Type in expressions for evaluation. Or try :help.

scala> val a = 5
a: Int = 5

scala> a
res0: Int = 5


scala> a = 6
:8: error: reassignment to val
       a = 6
         ^
scala> a
res1: Int = 5


scala> var b = 7
b: Int = 7

scala> b
res2: Int = 7

scala> b = 8
b: Int = 8

scala> b
res3: Int = 8


scala> val c = List(3,4,5,6)
c: List[Int] = List(3, 4, 5, 6)

scala> c(1)
res4: Int = 4

scala> c.sum
res5: Int = 18

scala> c.length
res6: Int = 4

scala> c.product
res7: Int = 360


scala> c.foldLeft(0)((x,y) => x+y)
res8: Int = 18


scala> c.foldLeft(0)(_+_)
res9: Int = 18

scala> c.foldLeft(1)(_*_)
res10: Int = 360


scala> c.reduce(_*_)
res11: Int = 360


scala> val d = Vector(2,3,4,5,6,7,8,9)
d: Vector[Int] = Vector(2, 3, 4, 5, 6, 7, 8, 9)

scala> d
res11: Vector[Int] = Vector(2, 3, 4, 5, 6, 7, 8, 9)

scala> d.slice(3,6)
res12: Vector[Int] = Vector(5, 6, 7)

scala> val e = d.updated(3,0)
e: Vector[Int] = Vector(2, 3, 4, 0, 6, 7, 8, 9)

scala> d
res13: Vector[Int] = Vector(2, 3, 4, 5, 6, 7, 8, 9)

scala> e
res14: Vector[Int] = Vector(2, 3, 4, 0, 6, 7, 8, 9)


scala> val f=(1 to 10).toList
f: List[Int] = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

scala> f
res15: List[Int] = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

scala> f.map(x => x*x)
res16: List[Int] = List(1, 4, 9, 16, 25, 36, 49, 64,
  81, 100)

scala> f map {x => x*x}
res17: List[Int] = List(1, 4, 9, 16, 25, 36, 49, 64,
  81, 100)

scala> f filter {_ > 4}
res18: List[Int] = List(5, 6, 7, 8, 9, 10)


math.log(2.0)
// res0: Double = 0.6931471805599453
math.sin(1.0)
// res1: Double = 0.8414709848078965
log(2.0)
// <console>:8: error: not found: value log
//               log(2.0)
//               ^
import math.log
// import math.log
log(2.0)
// res3: Double = 0.6931471805599453
import math._
// import math._
sin(Pi/2)
// res4: Double = 1.0
exp(log(sin(Pi/2)))
// res5: Double = 1.0
sin(asin(0.1))
// res6: Double = 0.1
atan(1)*4
// res7: Double = 3.141592653589793
log(sqrt(exp(1)))
// res8: Double = 0.5
abs(min(-1,2))
// res9: Int = 1
pow(2,8)
// res10: Double = 256.0
random
// res11: Double = 0.0954535018607291
random
// res12: Double = 0.5669552981874513
random
// res13: Double = 0.9598287994663521
floor(random*3)
// res14: Double = 2.0
floor(random*3)
// res15: Double = 1.0
floor(random*3)
// res16: Double = 1.0
floor(random*3)
// res17: Double = 1.0


val a1 = 1
// a1: Int = 1
val a2: Int = 1
// a2: Int = 1
val l1 = List(1, 2, 3)
// l1: List[Int] = List(1, 2, 3)
val l2: List[Int] = List(2, 3, 4)
// l2: List[Int] = List(2, 3, 4)


val a3: Double = 1
// a3: Double = 1.0
val a4: Int = 1.0
// <console>:7: error: type mismatch;
//  found   : Double(1.0)
//  required: Int
//        val a4: Int = 1.0
//                      ^


def fact1(n: Int): Int = (1 to n).product
// fact1: (n: Int)Int
fact1(5)
// res0: Int = 120


def fact2(n: Int): Int = {
  var acc = 1
  var i = 2
  while (i <= n) {
    acc *= i
    i += 1
    }
  acc
  }
// fact2: (n: Int)Int
fact2(5)
// res1: Int = 120


def fact3(n: Int): Int = {
  if (n == 1) 1 else
    n * fact3(n-1)
  }
// fact3: (n: Int)Int
fact3(5)
// res2: Int = 120


@annotation.tailrec
def fact4(n: Int, acc: Int = 1): Int = {
  if (n == 1) acc else
    fact4(n-1, acc*n)
  }
// fact4: (n: Int, acc: Int)Int
fact4(5)
// res3: Int = 120


math.log(fact4(5))
// res4: Double = 4.787491742782046

def lfact(n: Int): Double = {
  if (n == 1) 0.0 else
    math.log(n) + lfact(n-1)
  }
// lfact: (n: Int)Double
lfact(5)
// res5: Double = 4.787491742782046
// lfact(10000) // will cause a stack overflow


@annotation.tailrec
def lfacttr(n: Int, acc: Double = 0.0): Double = {
  if (n == 1) acc else
    lfacttr(n-1, acc + math.log(n))
  }
// lfacttr: (n: Int, acc: Double)Double
lfacttr(5)
// res6: Double = 4.787491742782046
lfacttr(10000)
// res7: Double = 82108.92783681446


@annotation.tailrec
def factbi(n: BigInt, acc: BigInt = 1): BigInt = {
  if (n == 1) acc else
    factbi(n-1, acc*n)
  }
// factbi: (n: BigInt, acc: BigInt)BigInt
factbi(5)
// res8: BigInt = 120
factbi(10000)
// res9: BigInt = 2846259680917054518906413212119...


/*
log-fact.scala
Program to compute the log-factorial function
*/

object LogFact {

  import annotation.tailrec
  import math.log

  @tailrec
  def logfact(n: Int, acc: Double = 0.0): Double =
    if (n == 1) acc else
      logfact(n-1, acc + log(n))

  def main(args: Array[String]): Unit = {
    val n = if (args.length == 1) args(0).toInt else 5
    val lfn = logfact(n)
    println(s"logfact($n) = $lfn")
  }

}

// eof


val l1 = List(1,2,3)


val l2 = 4 :: l1
// List(4, 1, 2, 3)


val l3 = l2 map { x => x*x }
// List(16, 1, 4, 9)


val l4 = l2.map(x => x*x)


import breeze.plot._
def plotFun(fun: Double => Double, xmin: Double =
               -3.0, xmax: Double = 3.0): Figure = {
  val f = Figure()
  val p = f.subplot(0)
  import breeze.linalg._
  val x = linspace(xmin, xmax)
  p += plot(x, x map fun)
  p.xlabel = "x"
  p.ylabel = "f(x)"
  f
}


plotFun(x => x*x)


def myQuad1(x: Double): Double = x*x - 2*x + 1
plotFun(myQuad1)
def myQuad2(x: Double): Double = x*x - 3*x - 1
plotFun(myQuad2)


val myQuad3: (Double => Double) = x => -x*x + 2
plotFun(myQuad3)


def quadratic(a: Double, b: Double, c: Double,
                             x: Double): Double = 
  a*x*x + b*x + c


plotFun(x => quadratic(3,2,1,x))


def quadFun(a: Double, b: Double, c: Double):
    Double => Double = x => quadratic(a,b,c,x)
val myQuad4 = quadFun(2,1,3)
plotFun(myQuad4)
plotFun(quadFun(1,2,3))


val quadFunF: (Double,Double,Double) => Double =>
     Double = (a,b,c) => x => quadratic(a,b,c,x)
val myQuad5 = quadFunF(-1,1,2)
plotFun(myQuad5)
plotFun(quadFunF(1,-2,3))


val myQuad6 = quadratic(1,2,3,_: Double)
plotFun(myQuad6)


def quad(a: Double, b: Double, c: Double)(x: Double):
      Double = a*x*x + b*x + c
plotFun(quad(1,2,-3))
val myQuad7 = quad(1,0,1) _
plotFun(myQuad7)


def quadCurried = (quadratic _).curried
plotFun(quadCurried(1)(2)(3))


val quadraticF: (Double,Double,Double,Double) => Double =
                     (a,b,c,x) => a*x*x + b*x + c
def quadCurried2 = quadraticF.curried
plotFun(quadCurried2(-1)(2)(3))


val aLongString = (1 to 10000).map(_.toString).
                               reduce(_+_)
// aLongString: String = 1234567891011121314151617...

val stringLength: String => Int = s => s.length
// stringLength: String => Int = <function1>

stringLength(aLongString)
// res0: Int = 38894


def convertToK: Int => Double = i => i.toDouble/1024
// convertToK: Int => Double

def stringLengthInK1(s: String): Double = {
  val l = stringLength(s)
  val lk = convertToK(l)
  lk
}
// stringLengthInK1: (s: String)Double

stringLengthInK1(aLongString)
// res1: Double = 37.982421875


val stringLengthInK2: String => Double =
                      s => convertToK(stringLength(s))
// stringLengthInK2: String => Double = <function1>

stringLengthInK2(aLongString)
// res2: Double = 37.982421875


val stringLengthInK3: String => Double =
             s => (convertToK compose stringLength)(s)
// stringLengthInK3: String => Double = <function1>

stringLengthInK3(aLongString)
// res3: Double = 37.982421875


val stringLengthInK4: String => Double =
                      convertToK compose stringLength
// stringLengthInK4: String => Double = <function1>

stringLengthInK4(aLongString)
// res4: Double = 37.982421875

