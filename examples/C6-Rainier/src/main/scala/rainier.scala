/*
rainier.scala

Simple Rainier logistic regression example

*/

object RainierLogRegApp {

  import com.stripe.rainier.core._
  import com.stripe.rainier.compute._
  import com.stripe.rainier.sampler._
  import com.stripe.rainier.notebook._
  import com.cibo.evilplot._
  import com.cibo.evilplot.plot._

  def main(args: Array[String]): Unit = {

    // first simulate some data from a logistic regression model
    implicit val rng = ScalaRNG(3)
    val N = 1000
    val beta0 = 0.1
    val beta1 = 0.3
    val x = (1 to N) map { _ =>
      3.0 * rng.standardNormal
    }
    val theta = x map { xi =>
      beta0 + beta1 * xi
    }
    def expit(x: Double): Double = 1.0 / (1.0 + math.exp(-x))
    val p = theta map expit
    val yb = p map (pi => (rng.standardUniform < pi))
    val y = yb map (b => if (b) 1L else 0L)
    println(y.take(10))
    println(x.take(10))

    // now build Rainier model
    val b0 = Normal(0, 5).latent
    val b1 = Normal(0, 5).latent
    val model = Model.observe(y, Vec.from(x).map{xi => 
      val theta = b0 + b1*xi
      val p  = 1.0 / (1.0 + (-theta).exp)
      Bernoulli(p)
    })

    // now sample from the model
    val sampler = EHMC(warmupIterations = 2000, iterations = 1000)
    println("Sampling...\nthis can take a while...")
    val bt = model.sample(sampler)
    println("Finished sampling.")
    val b0t = bt.predict(b0)
    println(b0t.sum/b0t.length)
    show("b0", density(b0t)) // only works in Jupyter and mdoc
    val b1t = bt.predict(b1)
    println(b1t.sum/b1t.length)
    show("b1", density(b1t)) // only works in Jupyter and mdoc
    displayPlot(density(b0t).render()) // hack for app/repl
    displayPlot(density(b1t).render()) // hack for app/repl

  }

}

// eof

