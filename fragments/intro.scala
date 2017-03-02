
import scala.math._
import breeze.stats.distributions.Uniform
import breeze.linalg._
import Scala.annotation.tailrec

// R-like functions for Uniform random numbers
def runif(l: Double,u: Double) = Uniform(l,u).sample
def runif(n: Int, l: Double, u: Double) = 
    DenseVector[Double](Uniform(l,u).sample(n).toArray)


val N=100000
def f(x: Double): Double = 
     math.exp(-x*x/2)/math.sqrt(2*Pi)

def mc1(its: Int): Int = {
  val x = runif(its,-5.0,5.0)
  val y = runif(its,0.0,0.5)
  val fx = x map {f(_)}
  sum((y :< fx) map {xi => if (xi == true) 1 else 0})
}
println(5.0*mc1(N)/N)


def mc2(its: Long): Long = {
  @tailrec def mc(its: Long,acc: Long): Long = {
    if (its == 0) acc else {
      val x = runif(-5.0,5.0)
      val y = runif(0.0,0.5)
      if (y < f(x)) mc(its-1,acc+1) else 
                                  mc(its-1,acc)
    }  
  }
  mc(its,0)
}
println(5.0*mc2(N)/N)


def mc3(its: Long): Long = {
  val NP = 8 // Max number of threads to use
  val N = its/NP // assuming NP|its
  (1 to NP).toList.par.map{x => mc2(N)}.sum
}
println(5.0*mc3(N)/N)


> run
[info] Running MonteCarlo 
Running with 10000000 iterations
Idiomatic vectorised solution
0.999661
time: 2957.277005ms
Fast efficient (serial) tail call
1.000262
time: 1395.82964ms
Parallelised version
1.000463
time: 337.361038ms
Done


class State(val x: Double,val y: Double)
 
def nextIter(s: State): State = {
     val newX=rngG.nextDouble(3.0,(s.y)*(s.y)+4.0)
     new State(newX, 
          rngN.nextDouble(1.0/(newX+1),1.0/sqrt(2*newX+2)))
}
 
def nextThinnedIter(s: State,left: Int): State = {
   if (left==0) s 
   else nextThinnedIter(nextIter(s),left-1)
}
 
def genIters(s: State,current: Int,stop: Int,thin: Int): State = {
     if (!(current>stop)) {
        println(current+" "+s.x+" "+s.y)
        genIters(nextThinnedIter(s,thin),current+1,stop,thin)
     }
     else s
}


/*
  multi-line
  comment
*/

object MyApp {

  def main(args: Array[String]): Unit =
    println("Hello, world!")
  
} // single line comment

