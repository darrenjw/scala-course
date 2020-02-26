# Smile example

## Some mdoc documentation

This is some documentation prepared using `mdoc`. The original file is in `docs`, but the `sbt` task `mdoc` will typecheck and execute the code blocks, and put the compiled markdown document in `target/mdoc`.

We begin by reading the data (we assume that the file "yacht.csv" already exists).
```scala mdoc
val df = smile.read.csv("yacht.csv")
df
```
We can get a quick summary of the data as follows.
```scala mdoc
df.summary
```
We can now carry out OLS regression after a couple of imports
```scala mdoc
import smile.data.formula._
import scala.language.postfixOps
val mod = smile.regression.ols("Resist" ~, df)
mod
```
If we don't want to regress on everything, we can just choose what we'd like to regress on.
```scala mdoc
smile.regression.ols("Resist" ~ "Froude", df)
smile.regression.ols("Resist" ~ "Froude" + "LongPos", df)
```

### Summary

This brief document has illustrated how easy and convenient it is to produce executable documentation and reports for Scala.

