# FP Basics

## Exercise: Interval bisection

Implement a function to find the (approximate) *root* of a simple function of type `Double => Double` using interval bisection - that is, find the input value `x` which makes the output of the function equal zero.

Your function should have the type signature:

```scala
findRoot(low: Double, high: Double)(f: Double => Double): Double
```

You may *assume* that the sign of `f(low)` is different to the sign of `f(high)` - do not test or check for this in your code (yet) - we will come back to this later. Similarly, just *assume* that `low < high`.

The function should be recursive, evaluating the function at the given end points and mid-point, and then calling itself on a smaller interval where the function changes sign.

You will obviously need some kind of termination criterion for the recursion.

Just find a single root. *Do not* worry about identifying or tracking multiple roots.

Some test cases are given below. Note that since your method is approximate, you can only expect approximate equality.

```scala
findRoot(-10.0,10.0)(x => x+1.0) == -1.0

findRoot(-5.0,10.0)(x => 2.0-x) == 2.0

findRoot(0.0,5.0)(x => x-1.0) == 1.0

findRoot(0.0,2.0)(x => (x+1.0)*(x-1.0)) == 1.0

findRoot(-2.0,0.0)(x => (x+1.0)*(x-1.0)) == -1.0

findRoot(0.0,2.0)(x => x*x-2.0) == math.sqrt(2.0)
```

This directory contains a template for a Scala implementation, including the above test cases. Run the `~test` task from `sbt` to check your implementation.

**Optional:** experts may want to consider using continuation passing to avoid repeated evaluation of the function at the same values.


