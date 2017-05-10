# Monte Carlo methods

## Practical exercises

### 1. Pure Monte Carlo

A mixture random variable is constructed as a `Binomial` random quantity with sample size taken from a `Poisson` distribution with mean 20 and success probability drawn independently from a `Beta(4,4)` distribution.

* Monadically, or otherwise, construct a function for drawing samples from this random variable. Note that fresh `Poisson` and `Beta` draws are required for each `Binomial` draw.
* Take 10,000 draws and plot the distribution.
* Average the draws to get an empirical estimate of the mean of this random variable.

### 2. Bayesian inference for a normal random sample

Consider a vector of iid sample observations `x` from a Gaussian distribution with unknown mean and variance. We can define a log-likelihood function with
```scala
import breeze.stats.distributions.Gaussian
import scala.collection.GenSeq
def ll(x: GenSeq[Double])(mean: Double,stdev: Double): Double =
  x map (Gaussian(mean,stdev).logPdf(_)) reduce (_+_)
```

* Assuming a flat prior the log-posterior is the log-likelihood. In this case, use the `MarkovChain.metropolisHastings` function in Breeze to sample from the posterior distribution by using the log-posterior as the log-target. For a proposal kernel, use a bivariate normal distribution, constructed using the `MultivariateGaussian` distribution in Breeze. Centre the proposal on the current value, and use a proposal variance matrix which is a scaled version of the 2x2 identity matrix. Manually tune the scaling factor to get reasonable mixing.
* Test your implementation on simulated data by conditioning on a large `x` sampled with a mean and variance you know. Check that the posterior mean and standard deviation are close to the true values.
* I deliberately parameterised the log likelihood with a `GenSeq`. Run your MCMC algorithm in parallel by passing in `x.par` instead of `x`. Time the runs to see what speed-up (if any) you get. You will probably only get significant speed-up for large `x` (for me, the parallel version is significantly quicker for a sample size of 10k).




#### eof

