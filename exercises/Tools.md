# Tools

## Practical exercises

### 1. ScalaDoc

* Go back to your linear regression example from the Chapter 6 exercises, and add ScalaDoc documentation to the `backSolve` method and `Lm` case class. Generate HTML documentation and check it with your web browser.

### 2. Testing

* Continuing with the same example, add some ScalaTest unit tests. For testing `backsolve`, just add a couple of tests using some simple 2x2 examples picked by hand.
* For testing `Lm`, start by testing it with two or three points on a known straight line.

### 3. Interfacing with R

* One way we could check that our logistic regression code is working as it should would be to read in or simulate a fairly small dataset and fit it with our code, then send the dataset to R and re-fit it with the `glm` function in R. Then bring the fitted coefficients back to Scala for comparison. If you are using simulated data, you could easily loop this to check for agreement on a range of small simulated datasets.

### 4. Vegas: a visualisation library

* We saw earlier that Breeze contains some basic data visualisaton functionality which is often sufficient for diagnostic purposes. However, there are a number of other visualisation libraries available for Scala. Have a quick look at [Vegas](https://github.com/vegas-viz/Vegas) now.
* From an new clean SBT session (say, run from an empty/temp directory), a dependence on Vegas session can be started with:
```scala
set libraryDependencies += "org.vegas-viz" %% "vegas" % "0.3.11"
set scalaVersion := "2.11.8"
console
```
Note that Vegas is not yet available for Scala 2.12.
* An example plot can be created with:
```scala
import vegas._
import vegas.render.WindowRenderer._

val plot = Vegas("Country Pop").
  withData(
    Seq(
      Map("country" -> "USA", "population" -> 314),
      Map("country" -> "UK", "population" -> 64),
      Map("country" -> "DK", "population" -> 80)
    )
  ).
  encodeX("country", Nom).
  encodeY("population", Quant).
  mark(Bar)

plot.show
```
This plot is rendered as a window in your OS window manager, but Vegas can render in other ways, for example, as HTML to display in a browser, and it is also designed to run within "notebook" environments such as [Jupyter](https://github.com/jupyter-scala/jupyter-scala/blob/master/README.md) and [Spark Notebook](https://github.com/spark-notebook/spark-notebook/blob/master/README.md).
* Try creating some more Vegas plots by copy-and-pasting code from the [example Jupyter notebook](http://nbviewer.jupyter.org/github/aishfenton/Vegas/blob/master/notebooks/jupyter_example.ipynb).
* Note that the simple example that we started with is available as a standalone app in the examples directory.
* Later, you might want to watch [this talk](https://www.youtube.com/watch?v=R29K9mpaLqA), which gives a good introduction to and overview of Vegas. You can skip the first 3 minutes without loss.


#### eof
