# Spark

## Practical exercises

### 1. Review some Spark documentation

* The official [Spark Documentation](http://spark.apache.org/docs/2.1.0/) is pretty good. Read through the [Quick start guide](http://spark.apache.org/docs/2.1.0/quick-start.html), then quickly skim the [Programming guide](http://spark.apache.org/docs/2.1.0/programming-guide.html), then the [ML guide](http://spark.apache.org/docs/2.1.0/ml-guide.html), especially the section on [Classification and regression](http://spark.apache.org/docs/2.1.0/ml-classification-regression.html). Briefly familiarise yourself with the [API docs](http://spark.apache.org/docs/2.1.0/api/scala/index.html#org.apache.spark.package).

### 2. Logistic regression for the SpamBase dataset

* This exercise will be concerned with analysis of the old SpamBase dataset. After skimming the documentation:
  * ftp://ftp.ics.uci.edu/pub/machine-learning-databases/spambase/

download the dataset:

* ftp://ftp.ics.uci.edu/pub/machine-learning-databases/spambase/spambase.data

to your machine and move it somewhere sensible for subsequent analysis. It actually isn't very big, so don't worry about size/memory issues.
* The data is a simple CSV file, so can be parsed easily with Spark's built-in CSV parser. Write a Spark shell script to read the data and fit a simple logistic regression model for the final column (Spam or not) given the other variables.
* Use Lasso regression to shrink out some of the variables. Choose your Lasso regularisation parameter by cross-validation. How many of the 57 predictor variables drop out of the regression in this case?

#### eof
