# Collections

## Exercise: Wrapping a root-finder in an Option

### Part A

Copy your previous `findRoot` function from the [previous exercise](../bisection/Readme.md), and add a new function `findRootOpt` which wraps it, so that instead of returning a `Double` it returns `Option[Double]`. The new signature is:

```scala
findRootOpt(low: Double, high: Double)(f: Double => Double): Option[Double]
```

Add checks that `low < high` and that the sign of `f(low)` is different from the sign of `f(high)` and return `None` if either check fails. Otherwise your function should behave as previously, returning the root in a `Some`.

All of the previous test case translate obviously as follows:

```scala
findRootOpt(-10.0,10.0)(x => x+1.0) == Some(-1.0)

findRootOpt(-5.0,10.0)(x => 2.0-x) == Some(2.0)

findRootOpt(0.0,5.0)(x => x-1.0) == Some(1.0)

findRootOpt(0.0,2.0)(x => (x+1.0)*(x-1.0)) == Some(1.0)

findRootOpt(-2.0,0.0)(x => (x+1.0)*(x-1.0)) == Some(-1.0)

findRootOpt(0.0,2.0)(x => x*x-2.0) == Some(math.sqrt(2.0))
```

In addition, we can add some new test cases which test the inital assumptions:

```scala
findRootOpt(2.0,0.0)(x => x-1.0) == None

findRootOpt(-1.0,-3.0)(x => x+2.0) == None

findRootOpt(0.0,2.0)(x => x+1.0) == None

findRootOpt(0.0,2.0)(x => x-5.0) == None

```

Again, these test cases are all included in the associated Scala template in this directory, and can be run with the `~testOnly PartA` task in `sbt`.


### Part B (if time permits)

The quadratic curve `y = a*x*x` for any fixed `a > 0` intersects the unit circle `x*x + y*y = 1` exactly once for `0 <= x <= 1`. Our task is to use our function `findRootOpt` to find this `x`.

Using just a tiny bit of maths, we can write the solution to this problem as the solution to the triangular system:

```scala
y - a*(1-y*y) = 0

x*x + y*y -1 = 0

```

The left hand side of first equation will clearly be negative at `y=0` and positive at `y=1`. Then for `0 <= y <= 1`, the left hand side of the second equation will be negative at `x=0` and positive at `x=1`.

Write a function, `solveQuad`, which accepts a value `a`, and uses a for-expression with `findRootOpt` to obtain the solution for `x`. It should have signature:

```scala
solveQuad(a: Double): Option[Double]
```

We can test this function by picking an `a`, solving for `x`, computing `y = a*x*x`, then checking whether `x*x + y*y = 1`. Some example tests are included in the Scala template in this directory.

You can run all tests for Part A and Part B with the `~test` task in `sbt`, or just the specific tests for Part B with `~testOnly PartB`.


