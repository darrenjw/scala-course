# Tools

## Practical exercises

### 1. ScalaDoc

* Go back to your linear regression example from the Chapter 6 exercises, and add ScalaDoc documentation to the `backSolve` method and `Lm` case class. Generate HTML documentation and check it with your web browser.

### 2. Testing

* Continuing with the same example, add some ScalaTest unit tests. For testing `backsolve`, just add a couple of tests using some simple 2x2 examples picked by hand.
* For testing `Lm`, start by testing it with two or three points on a known straight line.

### 3. Interfacing with R

* One way we could check that our logistic regression code is working as it should would be to read in or simulate a fairly small dataset and fit it with our code, then send the dataset to R and re-fit it with the `glm` function in R. Then bring the fitted coefficients back to Scala for comparison. If you are using simulated data, you could easily loop this to check for agreement on a range of small simulated datasets.




#### eof
