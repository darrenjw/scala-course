
import java.util.concurrent.ThreadLocalRandom
import scala.math.exp
import scala.annotation.tailrec

val N = 1000000L
def rng = ThreadLocalRandom.current()

def mc(its: Long): Double = {
  @tailrec def sum(its: Long, acc: Double): Double = {
    if (its == 0) acc else {
      val u = rng.nextDouble()
      sum(its-1, acc + exp(-u*u))
    }
  }
  sum(its,0.0)/its
}

mc(N)
// res0: Double = 0.7469182341226777


def mcp(its: Long,np: Int = 4): Double =
  (1 to np).par.map(i => mc(its/np)).sum/np

mcp(N)
// res1: Double = 0.7468289488326496


def time[A](f: => A) = {
    val s = System.nanoTime
    val ret = f
    println("time: "+(System.nanoTime-s)/1e6+"ms")
    ret 
  }


val bigN = 100000000L
// bigN: Long = 100000000

time(mc(bigN))
// time: 6225.859951ms
// res2: Double = 0.7468159872240743
time(mcp(bigN))
// time: 2197.872294ms
// res3: Double = 0.7468246533834739


(1 to 12).foreach{i =>
  println("np = "+i)
  (1 to 3).foreach(j => time(mcp(bigN,i)))
}
// np = 1
// time: 6201.480532ms
// time: 6186.176627ms
// time: 6198.14735ms
// np = 2
// time: 3127.512337ms
// time: 3122.648652ms
// time: 3148.509354ms
// np = 3
// time: 2488.273962ms
// time: 2402.957878ms
// time: 2555.286948ms
// np = 4
// time: 2133.996ms
// time: 2238.847511ms
// time: 2177.260599ms
// np = 5
// time: 2867.889727ms
// time: 2890.128312ms
// time: 2784.020295ms
// np = 6
// time: 3358.373499ms
// time: 2600.759805ms
// time: 2559.704485ms
// np = 7
// time: 3248.162029ms
// time: 3359.006061ms
// time: 2882.463352ms
// np = 8
// time: 1847.027762ms
// time: 2545.40533ms
// time: 2556.063328ms
// np = 9
// time: 2344.998373ms
// time: 2253.718886ms
// time: 2260.407902ms
// np = 10
// time: 2158.32923ms
// time: 2125.176623ms
// time: 2049.69822ms
// np = 11
// time: 1945.826366ms
// time: 1945.175903ms
// time: 1952.519595ms
// np = 12
// time: 1822.598809ms
// time: 1827.48165ms
// time: 2722.349404ms


def metrop1(n: Int = 1000, eps: Double = 0.5):
  DenseVector[Double] = {
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


def metrop2(n: Int = 1000, eps: Double = 0.5): Unit =
{
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
def metrop3(n: Int = 1000, eps: Double = 0.5,
  x: Double = 0.0, oldll: Double = Double.MinValue):
  Unit = {
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
def metrop4(n: Int = 1000, eps: Double = 0.5,
  x: Double = 0.0, oldll: Double = Double.MinValue,
  acc: List[Double] = Nil): DenseVector[Double] = {
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


def newState(x: Double, oldll: Double, eps: Double):
  (Double, Double) = {
    val can = x + Uniform(-eps, eps).draw
    val loglik = Gaussian(0.0, 1.0).logPdf(can)
    val loga = loglik - oldll
    if (math.log(Uniform(0.0, 1.0).draw) < loga)
      (can, loglik) else (x, oldll)
}


  @tailrec
  def metrop5(n: Int = 1000, eps: Double = 0.5,
  x: Double = 0.0,
  oldll: Double = Double.MinValue): Unit = {
    if (n > 0) {
      println(x)
      val ns = newState(x, oldll, eps)
      metrop5(n - 1, eps, ns._1, ns._2)
    }
  }


  @tailrec
  def metrop5b(n: Int = 1000, eps: Double = 0.5,
  x: Double = 0.0,
  oldll: Double = Double.MinValue): Unit = {
    if (n > 0) {
      println(x)
      val (nx, ll) = newState(x, oldll, eps)
      metrop5b(n - 1, eps, nx, ll)
    }
  }


 @tailrec
 def metrop6(n: Int = 1000, eps: Double = 0.5,
  x: Double = 0.0, oldll: Double = Double.MinValue,
  acc: List[Double] = Nil): DenseVector[Double] = {
   if (n == 0) DenseVector(acc.reverse.toArray) else {
     val (nx, ll) = newState(x, oldll, eps)
     metrop6(n - 1, eps, nx, ll, nx :: acc)
   }
 }


def nextState(eps: Double)(state: (Double, Double)):
  (Double, Double) = {
    val (x, oldll) = state
    val can = x + Uniform(-eps, eps).draw
    val loglik = Gaussian(0.0, 1.0).logPdf(can)
    val loga = loglik - oldll
    if (math.log(Uniform(0.0, 1.0).draw) < loga)
      (can, loglik) else (x, oldll)
  }


def metrop7(eps: Double = 0.5, x: Double = 0.0,
 oldll: Double = Double.MinValue): Stream[Double] =
  Stream.iterate((x,oldll))(nextState(eps)) map (_._1)


def kernel(x: Double): Rand[Double] = for {
    innov <- Uniform(-0.5, 0.5)
    can = x + innov
    oldll = Gaussian(0.0, 1.0).logPdf(x)
    loglik = Gaussian(0.0, 1.0).logPdf(can)
    loga = loglik - oldll
    u <- Uniform(0.0, 1.0)
} yield if (math.log(u) < loga) can else x


val ms = Stream.iterate(0.0)(kernel(_).draw)
ms.
drop(1000).
take(10000).
foreach(println)


case class State(x: Double, y: Double)
// defined class State


val s = State(1.0,2.0)
// s: State = State(1.0,2.0)
s.x
// res0: Double = 1.0
s.y
// res1: Double = 2.0
s.copy()
// res2: State = State(1.0,2.0)
s.copy(y=3)
// res3: State = State(1.0,3.0)


import breeze.stats.distributions._
// import breeze.stats.distributions._

def nextState(state: State): State = {
 val sy = state.y
 val x = Gamma(3.0,1.0/(sy*sy+4)).draw
 val y = Gaussian(1.0/(x+1),1.0/math.sqrt(2*x+2)).draw
 State(x,y)
}


val gs = Stream.iterate(State(1.0,1.0))(nextState)
// gs: scala.collection.immutable.Stream[State] =
//  Stream(State(1.0,1.0), ?)
val output = gs.drop(1000).take(100000).toArray
// output: Array[State] = Array(
//  State(0.20703194113971382,0.874650780098001),
//  State(0.5813103371812548,0.4780234809903935), ...


import breeze.linalg._
val xv = DenseVector(output map (_.x))
val yv = DenseVector(output map (_.y))

import breeze.plot._
val fig = Figure("Bivariate Gibbs sampler")
fig.subplot(2,2,0)+=hist(xv,50)
fig.subplot(2,2,1)+=hist(yv,50)
fig.subplot(2,2,2)+=plot(xv,yv,'.')


def thin[T](s: Stream[T], th: Int): Stream[T] = {
    val ss = s.drop(th - 1)
    if (ss.isEmpty) Stream.empty else
      ss.head #:: thin(ss.tail, th)
  }


thin(gs.drop(1000),10).take(10000).toArray


// gs.drop(1000).thin(10).take(10000)


def kernel(state: State): Rand[State] = for {
  x <- Gamma(3.0,1.0/(state.y*state.y+4))
  y <- Gaussian(1.0/(x+1),1.0/math.sqrt(2*x+2))
  ns = State(x,y)
} yield ns

val out3 = Stream.iterate(State(1.0,1.0))(kernel(_).draw).
  drop(1000).
  take(10000).
  toArray


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

