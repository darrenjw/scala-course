# Logistic regression

We will walk through a logistic regression example in Rainier. First some imports.

```scala mdoc
import com.stripe.rainier.core._
import com.stripe.rainier.compute._
import com.stripe.rainier.sampler._
import com.stripe.rainier.notebook._
import com.cibo.evilplot._
import com.cibo.evilplot.plot._
```

Now simulate some synthetic data from a logistic regression model that we can used to test our inference algorithm.
```scala mdoc
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
```
Now we have some data, we can build a Rainier model.
```scala mdoc
val b0 = Normal(0, 5).latent
val b1 = Normal(0, 5).latent
val model = Model.observe(y, Vec.from(x).map{xi => 
  val theta = b0 + b1*xi
  val p  = 1.0 / (1.0 + (-theta).exp)
  Bernoulli(p)
})
```
This completes specification of the Bayesian model. We now need to sample from the implied posterior distribution.
```scala mdoc
val sampler = EHMC(warmupIterations = 2000, iterations = 1000)
println("Sampling...\nthis can take a while...")
val bt = model.sample(sampler)
println("Finished sampling.")
val b0t = bt.predict(b0)
println(b0t.sum/b0t.length)
```
We can plot the marginal posteriors using `show`, which works in both mdoc and Jupyter notebooks, but doesn't currently work from the Scala REPL.
```scala mdoc:image:b0.png
show("b0", density(b0t)) // only works in Jupyter and mdoc
```

```scala mdoc
val b1t = bt.predict(b1)
println(b1t.sum/b1t.length)
```

```scala mdoc:image:b1.png
show("b1", density(b1t)) // only works in Jupyter and mdoc
```
So we see that mdoc documents provide a nice way to document Rainier modelling workflows, similar to the way people often document R workflows using R Markdown.
