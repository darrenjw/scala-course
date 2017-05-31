
// Spark context available as 'sc' (master = local[4]).
// Spark session available as 'spark'.
// Welcome to
//       ____              __
//      / __/__  ___ _____/ /__
//     _\ \/ _ \/ _ `/ __/  '_/
//    /___/ .__/\_,_/_/ /_/\_\   version 2.1.0
//       /_/
//          
// Using Scala version 2.11.8 
// Type in expressions to have them evaluated.
// Type :help for more information.

sc.textFile("README.md").count
// res0: Long = 104


val rdd1 = sc.textFile("README.md")
// rdd1: RDD[String] = README.md MapPartitionsRDD[3]
rdd1
// res1: RDD[String] = README.md MapPartitionsRDD[3]
rdd1.count
// res2: Long = 104


sc.textFile("README.md").
  map(_.trim).
  flatMap(_.split(' ')).
  count
// res3: Long = 535


sc.textFile("README.md").
  map(_.toLowerCase).
  flatMap(_.toCharArray).
  map{(_,1)}.
  reduceByKey(_+_).
  collect
// res4: Array[(Char, Int)] = Array((d,97), (z,3),
//  ( ,1), (",12), (`,6), (p,144), (x,13), (t,233),
// (.,61), (0,13), (b,44), (h,116), ( ,464), ...


sc.textFile("/usr/share/dict/words").
  map(_.trim).
  map(_.toLowerCase).
  flatMap(_.toCharArray).
  filter(_ > '/').
  filter(_ < '}').
  map{(_,1)}.
  reduceByKey(_+_).
  sortBy(__._2,false).
  collect
// res5: Array[(Char, Int)] = Array((s,90113),
//  (e,88833), (i,66986), (a,64439),
//  (r,57347), (n,57144), ...


def ++(other: RDD[T]): RDD[T]
def cartesian[U](other: RDD[U]): RDD[(T, U)]
def distinct: RDD[T]
def filter(f: (T) => Boolean): RDD[T]
def flatMap[U](f: (T) => TraversableOnce[U]): RDD[U]
def map[U](f: (T) => U): RDD[U]
def persist: RDD[T]
def sample(withReplacement: Boolean,
  fraction: Double): RDD[T]
def sortBy[K](f: (T) => K,
  ascending: Boolean = true): RDD[T]
def zip[U](other: RDD[U]): RDD[(T, U)]


def aggregate[U](zeroValue: U)(seqOp: (U, T) => U,
  combOp: (U, U) => U): U
def collect: Array[T]
def count: Long
def fold(zeroValue: T)(op: (T, T) => T): T
def foreach(f: (T) => Unit): Unit
def reduce(f: (T, T) => T): T
def take(num: Int): Array[T]


def aggregateByKey[U](zeroValue: U)(seqOp: (U, V) => U,
  combOp: (U, U) => U): RDD[(K, U)]
def groupByKey(): RDD[(K, Iterable[V])]
def join[W](other: RDD[(K, W)]): RDD[(K, (V, W))]
def keys: RDD[K]
def mapValues[U](f: (V) => U): RDD[(K, U)]
def reduceByKey(func: (V, V) => V): RDD[(K, V)]
def values: RDD[V]


def countByKey: Map[K, Long]


def toDF(colNames: String*): DataFrame
def col(colName: String): Column
def drop(col: Column): DataFrame
def select(cols: Column*): DataFrame
def show(numRows: Int): Unit
def withColumn(colName: String, col: Column): DataFrame
def withColumnRenamed(existingName: String,
  newName: String): DataFrame


import breeze.stats.distributions._
// import breeze.stats.distributions._
val x = Gaussian(1.0,2.0).sample(10000)
// x: IndexedSeq[Double] = Vector(0.1784867328179982,
// 5.270572111051524, -1.5529505975275635,
// 0.11506296796076387, -0.29372435649818396, ...


val xRdd = sc.parallelize(x)
// xRdd: RDD[Double] = ParallelCollectionRDD[23]


xRdd.mean
// res6: Double = 0.979624756565341
xRdd.sampleVariance
// res7: Double = 4.068248733184799


val xStats = xRdd.stats
// xStats: StatCounter = (count: 10000,
//  mean: 0.979625, stdev: 2.016889,
//  max: 10.478370, min: -8.809859)
xStats.mean
// res8: Double = 0.979624756565341
xStats.sampleVariance
// res9: Double = 4.068248733184799
xStats.sum
// res10: Double = 9796.24756565341


val x2 = Gaussian(0.0,1.0).sample(10000)
// x2: IndexedSeq[Double] = Vector(0.5814691557760885,
// 1.1171111465724972, 0.29188249592720783, ...
val xx = x zip x2
// xx: IndexedSeq[(Double, Double)] = Vector(
// (0.1784867328179982,0.5814691557760885),
// (5.270572111051524,1.1171111465724972), ...
val lp = xx map {p => 2.0*p._1 + 1.0*p._2 + 1.5}
// lp: IndexedSeq[Double] = Vector(2.4384426214120847,
//  13.158255368675546, -1.314018699127919, ...
val eps = Gaussian(0.0,1.0).sample(10000)
// eps: IndexedSeq[Double] = Vector(1.427893572186197,
// -0.6504024834850954, -0.30314296354173065, ...
val y = (lp zip eps) map (p => p._1 + p._2)
// y: IndexedSeq[Double] = Vector(3.866336193598282,
//  12.50785288519045, -1.6171616626696497, ...
val yx = (y zip xx) map (p => (p._1,p._2._1,p._2._2))
// yx: IndexedSeq[(Double, Double, Double)] = Vector(
// (3.866336193598282,0.1784867328179982,0.5814691557760885),
// (12.50785288519045,5.270572111051524,1.1171111465724972),
// ...

val rddLR = sc.parallelize(yx)
// rddLR: RDD[(Double, Double, Double)] =
//   ParallelCollectionRDD[27]


val dfLR = rddLR.toDF("y","x1","x2")
// dfLR: org.apache.spark.sql.DataFrame = [y: double, x1: double ... 1 more field]
dfLR.show
// +-------------------+--------------------+--------------------+
// |                  y|                  x1|                  x2|
// +-------------------+--------------------+--------------------+
// |  3.866336193598282|  0.1784867328179982|  0.5814691557760885|
// |  12.50785288519045|   5.270572111051524|  1.1171111465724972|
// |-1.6171616626696497| -1.5529505975275635| 0.29188249592720783|
// | 0.8683451871377511| 0.11506296796076387|  -0.297053228847389|
// | 1.3320680756656988|-0.29372435649818396|-0.03601773761744...|
// |  8.408493708252392|    1.88109002482217|  1.1950301713953415|
// | 1.5672517002311308|  0.7128598020565082|-0.48665514357360196|
// |  3.168738194841887|  2.0340739790649733| -0.6601131149055477|
// |  8.039748451446279|  3.8653674162715967| -1.2316169577326943|
// | 10.629872301438459|   4.019661153356504|  0.7687922240854776|
// |  2.962051294451586|  1.1752057841668553| -0.6818677255596642|
// | 1.2833251872218785| -1.5065655454119269|  0.7481935090342691|
// | 2.4091370399456338| 0.32161213623980145| -0.6009336402945296|
// |-3.0076474881260586| -1.7990530790353638|-0.32140307509169086|
// |   8.80509036070586|   4.577527777964653|-0.46644732455301874|
// |  0.705669160914749|-0.24374019169110217|   0.359536828750355|
// |  6.357141836034998|  2.3064957474701653|  0.3832815882167742|
// |-0.7111075496051031| -1.5606909465026222|  0.2622222327532171|
// | 4.9583467481047245|  1.8421235473520943| 0.12990787453918962|
// | 5.3331956954235284|  2.1442041883454985|-6.86911622420159...|
// +-------------------+--------------------+--------------------+
// only showing top 20 rows
dfLR.show(5)
// +-------------------+--------------------+--------------------+
// |                  y|                  x1|                  x2|
// +-------------------+--------------------+--------------------+
// |  3.866336193598282|  0.1784867328179982|  0.5814691557760885|
// |  12.50785288519045|   5.270572111051524|  1.1171111465724972|
// |-1.6171616626696497| -1.5529505975275635| 0.29188249592720783|
// | 0.8683451871377511| 0.11506296796076387|  -0.297053228847389|
// | 1.3320680756656988|-0.29372435649818396|-0.03601773761744...|
// +-------------------+--------------------+--------------------+
// only showing top 5 rows


// Don't run unless you have an appropriate CSV file...
val df = spark.read.
  option("header","true").
  option("inferSchema","true").
  csv("myCsvFile.csv")


import org.apache.spark.ml.regression.LinearRegression
// import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.ml.linalg._
// import org.apache.spark.ml.linalg._

val lm = new LinearRegression
// lm: LinearRegression = linReg_830e2ec56b44
lm.getStandardization
// res14: Boolean = true
lm.setStandardization(false)
// res15: lm.type = linReg_830e2ec56b44
lm.getStandardization
// res16: Boolean = false
lm.explainParams
// res17: String =
// aggregationDepth: suggested depth for treeAggregate (>= 2) (default: 2)
// elasticNetParam: the ElasticNet mixing parameter, in range [0, 1].
//   For alpha = 0, the penalty is an L2 penalty.
//   For alpha = 1, it is an L1 penalty (default: 0.0)
// featuresCol: features column name (default: features)
// fitIntercept: whether to fit an intercept term (default: true)
// labelCol: label column name (default: label)
// maxIter: maximum number of iterations (>= 0) (default: 100)
// predictionCol: prediction column name (default: prediction)
// regParam: regularization parameter (>= 0) (default: 0.0)
// solver: the solver algorithm for optimization.
//   If this is not set or empty, default value is 'auto' (default: auto)
// standardization: whether to standardize the training features ...


val dflr = (dfLR map {row => (row.getDouble(0), 
  Vectors.dense(row.getDouble(1),
  row.getDouble(2)))}).toDF("label","features")
// dflr: DataFrame = [label: double, features: vector]
dflr.show(5)
// +-------------------+--------------------+
// |              label|            features|
// +-------------------+--------------------+
// |  3.866336193598282|[0.17848673281799...|
// |  12.50785288519045|[5.27057211105152...|
// |-1.6171616626696497|[-1.5529505975275...|
// | 0.8683451871377511|[0.11506296796076...|
// | 1.3320680756656988|[-0.2937243564981...|
// +-------------------+--------------------+
// only showing top 5 rows


val fit = lm.fit(dflr)
// fit: LinearRegressionModel = linReg_830e2ec56b44
fit.intercept
// res19: Double = 1.4799669653918834
fit.coefficients
// res20: Vector = [2.004976921569354,1.0004609409395846]


val summ = fit.summary
// summ: LinearRegressionTrainingSummary =
//   LinearRegressionTrainingSummary@35e2c4b
summ.r2
// res21: Double = 0.9451196184400352
summ.rootMeanSquaredError
// res22: Double = 1.0061563554694304
summ.coefficientStandardErrors
// res23: Array[Double] = Array(0.004989649710643566,
//  0.010061968865893115, 0.011187314859319352)
summ.pValues
// res24: Array[Double] = Array(0.0, 0.0, 0.0)
summ.tValues
// res25: Array[Double] = Array(401.8271898511191,
//  99.42993804431556, 132.28973922719524)
summ.predictions
// res26: DataFrame = [label: double, features: vector
summ.residuals
// res27: DataFrame = [residuals: double]


val p = lp map (x => 1.0/(1.0+math.exp(-x)))
// p: IndexedSeq[Double] = Vector(0.9994499079755785,
// 0.4937931999875772, 0.999720196336271, ...
val yl = p map (pi => new Binomial(1,pi).draw) map
  (_.toDouble)
// yl: IndexedSeq[Double] = Vector(1.0, 1.0, 1.0, ...
val yxl = (yl zip xx) map (p => (p._1,p._2._1,p._2._2))
// yxl: IndexedSeq[(Double, Double, Double)] = Vector(
//  (1.0,3.1381503063526694,-0.27142587948210634),
//  (1.0,0.2902050889768183,-2.1052386533907854), ...

val rddLogR = sc.parallelize(yxl)
// rddLogR: RDD[(Double, Double, Double)] =
//   ParallelCollectionRDD[59]
val dfLogR = rddLogR.toDF("y","x1","x2").persist
// dfLogR: Dataset[org.apache.spark.sql.Row] =
//   [y: double, x1: double ... 1 more field]
dfLogR.show(5)
// +---+------------------+--------------------+
// |  y|                x1|                  x2|
// +---+------------------+--------------------+
// |1.0|3.1381503063526694|-0.27142587948210634|
// |1.0|0.2902050889768183| -2.1052386533907854|
// |1.0|3.2133859767485955|  0.2543706054055738|
// |1.0|0.8247934159943499| -1.0013955800392003|
// |1.0| 3.274443477406557| -1.6514890824757613|
// +---+------------------+--------------------+
// only showing top 5 rows


import org.apache.spark.ml.classification._
// import org.apache.spark.ml.classification._
val lr = new LogisticRegression
// lr: LogisticRegression = logreg_09792f5b38dd
lr.setStandardization(false)
// res29: lr.type = logreg_09792f5b38dd
lr.explainParams
// res30: String =
// aggregationDepth: suggested depth for treeAggregate (>= 2) (default: 2)
// elasticNetParam: the ElasticNet mixing parameter, in range [0, 1].
//  For alpha = 0, the penalty is an L2 penalty.
//  For alpha = 1, it is an L1 penalty (default: 0.0)
// family: The name of family which is a description of the
//  label distribution to be used in the model.
//  Supported options: auto, binomial, multinomial. (default: auto)
// featuresCol: features column name (default: features)
// fitIntercept: whether to fit an intercept term (default: true)
// labelCol: label column name (default: label)
// maxIter: maximum number of iterations (>= 0) (default: 100)
// predictionCol: prediction column name (default: prediction)
// probabilityCol: Column name for predicted class conditional probabilities.

val dflogr = (dfLogR map {row => (row.getDouble(0), 
  Vectors.dense(row.getDouble(1),
  row.getDouble(2)))}).toDF("label","features")
// dflogr: DataFrame = [label: double, features: vector]
dflogr.show(5)
// +-----+--------------------+
// |label|            features|
// +-----+--------------------+
// |  1.0|[3.13815030635266...|
// |  1.0|[0.29020508897681...|
// |  1.0|[3.21338597674859...|
// |  1.0|[0.82479341599434...|
// |  1.0|[3.27444347740655...|
// +-----+--------------------+
// only showing top 5 rows

val logrfit = lr.fit(dflogr)
// logrfit: LogisticRegressionModel = logreg_09792f5b38dd
logrfit.intercept
// res32: Double = 1.482650505103195
logrfit.coefficients
// res33: org.apache.spark.ml.linalg.Vector =
//   [2.003478489528172,0.9368494184176968]


import breeze.linalg.linspace
// import breeze.linalg.linspace
val lambdas = linspace(-12,4,60).
  toArray.
  map{math.exp(_)}
// lambdas: Array[Double] = Array(6.14421235332821E-6,
//  8.058254732499574E-6, 1.0568558767126194E-5, ...
import org.apache.spark.ml.tuning._
// import org.apache.spark.ml.tuning._
import org.apache.spark.ml.evaluation._
// import org.apache.spark.ml.evaluation._
val paramGrid = new ParamGridBuilder().
  addGrid(lr.regParam,lambdas).
  build()
// paramGrid: Array[org.apache.spark.ml.param.ParamMap] =
// Array({
// 	logreg_09792f5b38dd-regParam: 6.14421235332821E-6
// }, {
// 	logreg_09792f5b38dd-regParam: 8.058254732499574E-6
//  ...
val cv = new CrossValidator().
  setEstimator(lr).
  setEvaluator(new BinaryClassificationEvaluator).
  setEstimatorParamMaps(paramGrid).
  setNumFolds(8)
// cv: CrossValidator = cv_6d06cc600072
val cvMod = cv.fit(dflogr)
// cvMod: CrossValidatorModel = cv_6d06cc600072
cvMod.explainParams
// res34: String =
// estimator: estimator for selection (current: logreg_09792f5b38dd)
// estimatorParamMaps: param maps for the estimator (current: [Lorg.apache.spark.ml.param.ParamMap;@40b2122b)
// evaluator: evaluator used to select hyper-parameters that maximize the validated metric (current: binEval_4fe7ca428ee0)
// numFolds: number of folds for cross validation (>= 2) (default: 3, current: 8)
// seed: random seed (default: -1191137437)
cvMod.bestModel.explainParams
// res35: String =
// aggregationDepth: suggested depth for treeAggregate (>= 2) (default: 2)
// elasticNetParam: the ElasticNet mixing parameter, in range [0, 1]. For alpha = 0, the penalty is an L2 penalty. For alpha = 1, it is an L1 penalty (default: 0.0)
// family: The name of family which is a description of the label distribution to be used in the model. Supported options: auto, binomial, multinomial. (default: auto)
// featuresCol: features column name (default: features)
// fitIntercept: whether to fit an intercept term (default: true)
// labelCol: label column name (default: label)
// maxIter: maximum number of iterations (>= 0) (default: 100)
// predictionCol: prediction column name (default: prediction)
// probabilityCol: Column name for predicted class conditional probabilities. Note: Not all models outp...
cvMod.bestModel.extractParamMap
// res36: org.apache.spark.ml.param.ParamMap =
// {
// 	logreg_09792f5b38dd-aggregationDepth: 2,
// 	logreg_09792f5b38dd-elasticNetParam: 0.0,
// 	logreg_09792f5b38dd-family: auto,
// 	logreg_09792f5b38dd-featuresCol: features,
// 	logreg_09792f5b38dd-fitIntercept: true,
// 	logreg_09792f5b38dd-labelCol: label,
// 	logreg_09792f5b38dd-maxIter: 100,
// 	logreg_09792f5b38dd-predictionCol: prediction,
// 	logreg_09792f5b38dd-probabilityCol: probability,
// 	logreg_09792f5b38dd-rawPredictionCol: rawPrediction,
// 	logreg_09792f5b38dd-regParam: 2.737241171E-4,
// 	logreg_09792f5b38dd-standardization: false,
// 	logreg_09792f5b38dd-threshold: 0.5,
// 	logreg_09792f5b38dd-tol: 1.0E-6
// }
val lambda = cvMod.bestModel.
  extractParamMap.
  getOrElse(cvMod.bestModel.
    getParam("regParam"),0.0).
  asInstanceOf[Double]
// lambda: Double = 2.737241171E-4


name := "spark-template"

version := "0.1"

libraryDependencies  ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.1.0" % Provided,
  "org.apache.spark" %% "spark-sql" % "2.1.0" % Provided
)

scalaVersion := "2.11.8"


import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object SparkApp {

  def main(args: Array[String]): Unit = {

    val spark = new SparkConf().
      setAppName("Spark Application")
    val sc = new SparkContext(spark)

    sc.textFile("/usr/share/dict/words").
      map(_.trim).
      map(_.toLowerCase).
      flatMap(_.toCharArray).
      filter(_ > '/').
      filter(_ < '}').
      map{(_,1)}.
      reduceByKey(_+_).
      sortBy(_._2,false).
      collect.
      foreach(println)

  }

}

