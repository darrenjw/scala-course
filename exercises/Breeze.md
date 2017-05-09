# Breeze

## Practical exercises

#### Useful links:

* [Breeze](https://github.com/scalanlp/breeze/)
  * [Wiki](https://github.com/scalanlp/breeze/wiki)
  * [API Docs](http://www.scalanlp.org/api/breeze/)

### 1. Review the on-line documentation

Begin by reading through the [quickstart guide](https://github.com/scalanlp/breeze/wiki/Quickstart) and then read through the [linear algebra cheat sheet](https://github.com/scalanlp/breeze/wiki/Linear-Algebra-Cheat-Sheet). Then quickly check a few other pages on the [Breeze wiki](https://github.com/scalanlp/breeze/wiki). Finally, have a quick look at the [API docs](http://www.scalanlp.org/api/breeze/) - for example, search the docs for `Gamma` and see how Breeze parameterises the gamma distribution. Note that the docs are often very terse, so sometimes there's no alternative than to browse the [source code](https://github.com/scalanlp/breeze/tree/master/math/src/main/scala/breeze). Also, the [test code](https://github.com/scalanlp/breeze/tree/master/math/src/test/scala/breeze) can sometimes be useful for figuring out how to use a Breeze function.

### 2. Multivariate normal

* Write a function with type signature
```scala
rmvn(n: Int, mean: DenseVector[Double], cov: DenseMatrix[Double]): DenseMatrix[Double]
```
which returns a matrix with `n` rows, each row representing an iid draw from a multivariate normal with the given mean and variance matrix. Note that this can be accomplished by *post*-multiplying a matrix of iid *N(0,1)* random quantities by the *upper* Cholesky factor of the variance matrix, and then adding the mean to each row of the result (don't use the built-in Breeze function for simulating multivariate Gaussians unless you're stuck).
* How can you test your code to ensure that you have implemented it correctly? See the [gamma testing](../examples/C4-GammaTest/src/main/scala/gamma-test.scala) example for clues. Also, `breeze.stats.covmat` may be of use.

### 3. Scatter-plot

Write a function with type signature
```scala
pairs(mat: DenseMatrix[Double]): Figure
```
which produces a scatterplot matrix similar to that produced by the `pairs()` function in R. eg. for a matrix with `k` columns, the function should plot a `k * k` array of scatter plots showing each variable against each other. Test your code on some simulated data generated using your `rmvn` function.


#### eof
