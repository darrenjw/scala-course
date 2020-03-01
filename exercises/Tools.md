# Tools

## Practical exercises

Again, choose selectively from these exercises according to interests and your previous selections.

### 1. ScalaDoc

* Go back to your linear regression example from the Chapter 6 exercises, and add ScalaDoc documentation to the `backSolve` method and `Lm` case class. Generate HTML documentation and check it with your web browser.

### 2. Testing

* Continuing with the same example, add some ScalaTest unit tests. For testing `backsolve`, just add a couple of tests using some simple 2x2 examples picked by hand.
* For testing `Lm`, start by testing it with two or three points on a known straight line.
* Try adding some property-based tests to your code, using ScalaCheck.

### 3. Interfacing with R

* One way we could check that our logistic regression code is working as it should would be to read in or simulate a fairly small dataset and fit it with our code, then send the dataset to R and re-fit it with the `glm` function in R. Then bring the fitted coefficients back to Scala for comparison. Take a look at the tests for the [scala-glm](https://github.com/darrenjw/scala-glm/) library, which uses exactly this strategy.
* If you are using simulated data, you could easily loop this to check for agreement on a range of small simulated datasets (ideally using ScalaCheck).

### 4. EvilPlot

[EvilPlot](https://cibotech.github.io/evilplot/) is a nice library for generating high-quality plots and charts using Scala. I have an [example project](../examples/C7-EvilPlot/) which shows how to use it to generate a range of plots and charts, based mainly on examples from the EvilPlot documentation.

* Run the example project, and inspect the code to see how it works
* Read through some of the EvilPlot documentation
* Produce some nice charts and plots for one or more of the examples you have previously considered, such as a regression model, but previously charted using breeze-viz.

### 5. Mdoc

[Mdoc](https://scalameta.org/mdoc/) is a great framework for documenting libraries and workflows using executable Scala code blocks within Markdown documents. A couple of the examples we have already seen had some mdoc documentation associated with them.

* The [Smile example](../examples/C6-Smile/) has an mdoc document in `docs`, and the `mdoc` sbt task compiles this, and puts generated Markdown in `target/mdoc`. Make sure you know how it works.
* The [Rainier example](../example/C6-Rainier/) has an mdoc document as well. Note that Rainier has built-in support for generating EvilPlot figures, and hooks for including these in mdoc documents and Jupyter notebooks. The mdoc document associated with this example illustrates how to use this functionality to embed Rainier EvilPlot figures into a mdoc document. Study it to see how it works. Note that Rainier acheives this by making use of mdoc PostModifier hooks - you can read more about those [here](https://scalameta.org/mdoc/docs/modifiers.html).
* Add some Mdoc tutorial documentation to one of the examples you have developed during this course, in order to document your workflow.




#### eof
