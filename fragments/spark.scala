
sc.textFile("README.md").count


val rdd1 = sc.textFile("README.md")
rdd1
rdd1.count


sc.textFile("README.md").
  map(_.trim).
  flatMap(_.split(' ')).
  count


sc.textFile("README.md").
  map(_.toLowerCase).
  flatMap(_.toCharArray).
  map{(_,1)}.
  reduceByKey(_+_).
  collect


sc.textFile("/usr/share/dict/words").
  map(_.trim).
  map(_.toLowerCase).
  flatMap(_.toCharArray).
  filter(_ > '/').
  filter(_ < '}').
  map{(_,1)}.
  reduceByKey(_+_).
  collect


import breeze.stats.distributions._
val x = Gaussian(1.0,2.0).sample(10000)


val xRdd = sc.parallelize(x)


xRdd.mean
xRdd.sampleVariance


val xStats = xRdd.stats
xStats.mean
xStats.sampleVariance
xStats.sum


val x2 = Gaussian(0.0,1.0).sample(10000)
val xx = x zip x2
val lp = xx map {p => 2.0*p._1 + 1.0*p._2 + 1.5}
val eps = Gaussian(0.0,1.0).sample(10000)
val y = (lp zip eps) map (p => p._1 + p._2)
val yx = (y zip xx) map (p => (p._1,p._2._1,p._2._2))

val rddLR = sc.parallelize(yx)


val dfLR = rddLR.toDF("y","x1","x2")
dfLR.show
dfLR.show(5)


// Don't run unless you have an appropriate CSV file...
val df = spark.read.
  option("header","true").
  option("inferSchema","true").
  csv("myCsvFile.csv")


import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.ml.linalg._

val lm = new LinearRegression
lm.explainParams
lm.getStandardization
lm.setStandardization(false)
lm.getStandardization
lm.explainParams


// Transform data frame to required format
val dflr = (dfLR map {row => (row.getDouble(0), 
           Vectors.dense(row.getDouble(1),row.getDouble(2)))}).
           toDF("label","features")
dflr.show(5)


// Fit model
val fit = lm.fit(dflr)
fit.intercept
fit.coefficients


val summ = fit.summary
summ.r2
summ.rootMeanSquaredError
summ.coefficientStandardErrors
summ.pValues
summ.tValues
summ.predictions
summ.residuals

