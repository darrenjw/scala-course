
import java.util.concurrent.ThreadLocalRandom
import scala.math.exp
import scala.annotation.tailrec

object MonteCarlo {

  @tailrec
  def sum(its: Long,acc: Double): Double = {
    if (its==0) 
      (acc)
    else {
      val u=ThreadLocalRandom.current().nextDouble()
      sum(its-1,acc+exp(-u*u))
    }
  }

  def main(args: Array[String]) = {
    println("Hello")
    val iters=1000000000
    val result=sum(iters,0.0)
    println(result/iters)
    println("Goodbye")
  }

}


import java.util.concurrent.ThreadLocalRandom
import scala.math.exp
import scala.annotation.tailrec

object MonteCarlo {

  @tailrec
  def sum(its: Long,acc: Double): Double = {
    if (its==0) 
      (acc)
    else {
      val u=ThreadLocalRandom.current().nextDouble()
      sum(its-1,acc+exp(-u*u))
    }
  }

  def main(args: Array[String]) = {
    println("Hello")
    val N=4
    val iters=1000000000
    val its=iters/N
    val sums=(1 to N).toList map {x => sum(its,0.0)}
    val result=sums.reduce(_+_)
    println(result/iters)
    println("Goodbye")
  }

}


import java.util.concurrent.ThreadLocalRandom
import scala.math.exp
import scala.annotation.tailrec

object MonteCarlo {

  @tailrec
  def sum(its: Long,acc: Double): Double = {
    if (its==0) 
      (acc)
    else {
      val u=ThreadLocalRandom.current().nextDouble()
      sum(its-1,acc+exp(-u*u))
    }
  }

  def main(args: Array[String]) = {
    println("Hello")
    val N=4
    val iters=1000000000
    val its=iters/N
    val sums=(1 to N).toList.par map {x => sum(its,0.0)}
    val result=sums.reduce(_+_)
    println(result/iters)
    println("Goodbye")
  }

}


import java.util.concurrent.ThreadLocalRandom
import scala.math.exp
import scala.annotation.tailrec

object MonteCarlo {

  @tailrec
  def sum(its: Long,acc: Double): Double = {
    if (its==0) 
      (acc)
    else {
      val u=ThreadLocalRandom.current().nextDouble()
      sum(its-1,acc+exp(-u*u))
    }
  }

  def main(args: Array[String]) = {
    println("Hello")
    val N=args(0).toInt
    val iters=1000000000
    val its=iters/N
    val sums=(1 to N).toList.par map {x => sum(its,0.0)}
    val result=sums.reduce(_+_)
    println(result/iters)
    println("Goodbye")
  }

}


 N     T1     T2     T3
 1   57.67  57.62  57.83
 2   32.20  33.24  32.76
 3   26.63  26.60  26.63
 4   20.99  20.92  20.75
 5   20.13  18.70  18.76
 6   16.57  16.52  16.59
 7   15.72  14.92  15.27
 8   13.56  13.51  13.32
 9   18.30  18.13  18.12
10   17.25  17.33  17.22
11   17.04  16.99  17.09
12   15.95  15.85  15.91

16   16.62  16.68  16.74
32   15.41  15.54  15.42
64   15.03  15.03  15.28


import java.util.concurrent.ThreadLocalRandom
import scala.math.exp

object MonteCarlo {

  def main(args: Array[String]) = {
    println("Hello")
    val iters=1000000000
    val sums=(1 to iters).toList map {x => ThreadLocalRandom.current().nextDouble()} map {x => exp(-x*x)}
    val result=sums.reduce(_+_)
    println(result/iters)
    println("Goodbye")
  }

}


import java.util.concurrent.ThreadLocalRandom
import scala.math.exp

object MonteCarlo {

  def main(args: Array[String]) = {
    println("Hello")
    val iters=1000000000
    val sums=(1 to iters).toList.par map {x => ThreadLocalRandom.current().nextDouble()} map {x => exp(-x*x)}
    val result=sums.reduce(_+_)
    println(result/iters)
    println("Goodbye")
  }

}


object GibbsSc {
 
    import cern.jet.random.tdouble.engine.DoubleMersenneTwister
    import cern.jet.random.tdouble.Normal
    import cern.jet.random.tdouble.Gamma
    import Math.sqrt
    import java.util.Date
 
    def main(args: Array[String]) {
        val N=50000
        val thin=1000
        val rngEngine=new DoubleMersenneTwister(new Date)
        val rngN=new Normal(0.0,1.0,rngEngine)
        val rngG=new Gamma(1.0,1.0,rngEngine)
        var x=0.0
        var y=0.0
        println("Iter x y")
        for (i <- 0 until N) {
            for (j <- 0 until thin) {
                x=rngG.nextDouble(3.0,y*y+4)
                y=rngN.nextDouble(1.0/(x+1),1.0/sqrt(2*x+2))
            }
            println(i+" "+x+" "+y)
        }
    }
 
}


object FunGibbs {
 
    import cern.jet.random.tdouble.engine.DoubleMersenneTwister
    import cern.jet.random.tdouble.Normal
    import cern.jet.random.tdouble.Gamma
    import java.util.Date
    import scala.math.sqrt

    val rngEngine=new DoubleMersenneTwister(new Date)
    val rngN=new Normal(0.0,1.0,rngEngine)
    val rngG=new Gamma(1.0,1.0,rngEngine)

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

    def main(args: Array[String]) {
        println("Iter x y")
        genIters(new State(0.0,0.0),1,50000,1000)
     }

}


(th: P) => {
  val x0 = simx0(n, t0, th).par
  @tailrec def pf(ll: LogLik, x: ParVector[S], t: Time, 
             deltas: List[Time], obs: List[O]): LogLik =
    obs match {
      case Nil => ll
      case head :: tail => {
        val xp = if (deltas.head == 0) x else 
               (x map { stepFun(_, t, deltas.head, th) })
        val w = xp map { dataLik(_, head, th) }
        val rows = sample(n, DenseVector(w.toArray)).par
        val xpp = rows map { xp(_) }
        pf(ll + math.log(mean(w)), xpp, t + deltas.head, 
                                        deltas.tail, tail)
      }
    }
  pf(0, x0, t0, deltas, obs)
}

