# Monte Carlo methods

## Practical exercises

### 1. Pure Monte Carlo

A random variable 

### 2. Bayesian inference for a normal random sample

Consider a vector of iid sample observations `x` from a Gaussian distribution with unknown mean and variance. We can define a log-likelihood function with
```scala
import breeze.stats.distributions.Gaussian
import scala.collection.GenSeq
def ll(x: GenSeq[Double])(mean: Double,stdev: Double): Double =
  x map (Gaussian(mean,stdev).logPdf(_)) reduce (_+_)
```

* Assuming a flat prior the log-posterior is the log-likelihood. In this case, use the `MarkovChain.metropolisHastings` function in Breeze to sample from the posterior distribution by using the log-posterior as the log-target. For a proposal kernel, use a standard bivariate normal, constructed using the `MultivariateGaussian` distribution in Breeze.
* Test your implementation on simulated data by conditioning on a large `x` sampled with a mean and variance you know. Check that the posterior mean and standard deviation are close to the true values.
* I deliberately parameterised the log likelihood with a `GenSeq`. Run your MCMC algorithm in parallel by passing in `x.par` instead of `x`. Time the runs to see what speed-up (if any) you get. You will probably only get significant speed-up for large `x`.




#### eof

