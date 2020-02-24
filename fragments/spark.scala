
// Spark context available as 'sc' (master = local[4]).
// Spark session available as 'spark'.
// Welcome to
//       ____              __
//      / __/__  ___ _____/ /__
//     _\ \/ _ \/ _ `/ __/  '_/
//    /___/ .__/\_,_/_/ /_/\_\   version 2.4.5
//       /_/
//          
// Using Scala version 2.11.12 
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
// x: IndexedSeq[Double] = Vector(3.0223249923826834,
// 2.077810061808082, 4.8705145017976825,
// -1.2269630846849795, ...


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
// x2: IndexedSeq[Double] = Vector(-1.3299709525667858,
// 0.8900485622851986, -0.82913582956473,
// 0.4170472792720592, -0.3296838173775767, ...
val xx = x zip x2
// xx: IndexedSeq[(Double, Double)] = Vector(
// (3.0223249923826834,-1.3299709525667858),
// (2.077810061808082,0.8900485622851986),
val lp = xx map {p => 2.0*p._1 + 1.0*p._2 + 1.5}
// lp: IndexedSeq[Double] = Vector(6.214679032198581,
// 6.545668685901363, 10.411893174030634, ...
val eps = Gaussian(0.0,1.0).sample(10000)
// eps: IndexedSeq[Double] = Vector(-0.1250737756498898,
// -0.698695043218904, 0.177484931860096,//
// 0.19496475364956445, ...
val y = (lp zip eps) map (p => p._1 + p._2)
// y: IndexedSeq[Double] = Vector(6.089605256548691,
// 5.8469736426824594, 10.58937810589073,
// -0.34191413644833557, 5.595445603872117, ...
val yx = (y zip xx) map (p => (p._1,p._2._1,p._2._2))
// yx: IndexedSeq[(Double, Double, Double)] = Vector(
// (6.0896052565486,3.02232499238268,-1.32997095256678),
// (5.84697364268245,2.0778100618080,0.89004856228519)
// ...
val rddLR = sc.parallelize(yx)
// rddLR: RDD[(Double, Double, Double)] =
//   ParallelCollectionRDD[27]


val dfLR = rddLR.toDF("y","x1","x2")
// dfLR: org.apache.spark.sql.DataFrame =
//  [y: double, x1: double ... 1 more field]
dfLR.show
// +----------------+-----------------+-----------------+
// |               y|               x1|               x2|
// +----------------+-----------------+-----------------+
// |   6.089605256548| 3.0223249923826| -1.3299709525667|
// |  5.8469736426824|  2.077810061808|  0.8900485622851|
// |   10.58937810589| 4.8705145017976|   -0.82913582956|
// |-0.34191413644833|-1.2269630846849|  0.4170472792720|
// |   5.595445603872| 1.4802998580155| -0.3296838173775|
// |   9.427736572221| 2.8857451823545|  1.8388636773477|
// |  2.8162102399840| 0.9805440701579| -0.2883825935757|
// |   2.309534124364| 1.0778717202118| -0.8100879058278|
// |  2.5747161714542|0.09520008719238|  0.5642713548100|
// | -3.4422857154246|-2.9331703116468| 0.39422544033998|
// |   4.724868996350| 0.8501392094618| -0.4654355297426|
// |  -5.275810527950|-3.2006600305149| 0.00759830412394|
// |  2.3275257922512| 0.5023605802277| 0.06622565408926|
// |   6.954030184869| 3.2302471178699|  -0.476446601461|
// |  7.9350616586311| 3.0156885607537|  1.4311847283099|
// |  -4.338442740026|-3.1755115071872|  1.8055806581474|
// |  2.3330918772673| 0.6716231640061|  0.8335826994900|
// |  -1.514930229964|-1.7340336593492|  1.4466744503838|
// |  2.4294247417548| 1.5919623686044| -0.9701544260678|
// |   9.437005019053|  2.521041551181|  1.4196909330122|
// +-----------------+----------------+-----------------+
// only showing top 20 rows
dfLR.show(5)
// +----------------+-----------------+----------------+
// |               y|               x1|              x2|
// +----------------+-----------------+----------------+
// |   6.089605256548| 3.0223249923826|-1.3299709525667|
// |  5.8469736426824|  2.077810061808| 0.8900485622851|
// |   10.58937810589| 4.8705145017976|  -0.82913582956|
// |-0.34191413644833|-1.2269630846849| 0.4170472792720|
// |   5.595445603872| 1.4802998580155|-0.3296838173775|
// +-----------------+----------------+----------------+
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
// aggregationDepth: suggested depth for treeAggregate
//   (>= 2) (default: 2)
// elasticNetParam: the ElasticNet mixing parameter,
//   in range [0, 1].
//   For alpha = 0, the penalty is an L2 penalty.
//   For alpha = 1, it is an L1 penalty (default: 0.0)
// featuresCol: features column name (default: features)
// fitIntercept: whether to fit an intercept term
//   (default: true)
// labelCol: label column name (default: label)
// maxIter: maximum number of iterations (>= 0)
//   (default: 100)
// predictionCol: prediction column name
//   (default: prediction)
// regParam: regularization parameter (>= 0)
//   (default: 0.0)
// solver: the solver algorithm for optimization.
//   If this is not set or empty, default value is 'auto'
//    (default: auto)
// standardization: whether to standardize the
//   training features ...


val dflr = (dfLR map {row => (row.getDouble(0), 
  Vectors.dense(row.getDouble(1),
  row.getDouble(2)))}).toDF("label","features")
// dflr: DataFrame = [label: double, features: vector]
dflr.show(5)
// +-------------------+--------------------+
// |              label|            features|
// +-------------------+--------------------+
// |   6.089605256548691|[3.02232499238268...|
// |  5.8469736426824594|[2.07781006180808...|
// |   10.58937810589073|[4.87051450179768...|
// |-0.34191413644833557|[-1.2269630846849...|
// |   5.595445603872117|[1.48029985801559...|
// +-------------------+--------------------+
// only showing top 5 rows


import org.apache.spark.ml.feature.RFormula
// import org.apache.spark.ml.feature.RFormula
val dflr2 = new RFormula().
  setFormula("y ~ x1 + x2").
  fit(dfLR).transform(dfLR).
  select("label","features")
// dflr2: org.apache.spark.sql.DataFrame =
//  [label: double, features: vector]
dflr2 show 5
// +--------------------+--------------------+
// |               label|            features|
// +--------------------+--------------------+
// |   6.089605256548691|[3.02232499238268...|
// |  5.8469736426824594|[2.07781006180808...|
// |   10.58937810589073|[4.87051450179768...|
// |-0.34191413644833557|[-1.2269630846849...|
// |   5.595445603872117|[1.48029985801559...|
// +--------------------+--------------------+
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
// aggregationDepth: suggested depth for treeAggregate
//  (>= 2) (default: 2)
// elasticNetParam: the ElasticNet mixing parameter,
//   in range [0, 1].
//  For alpha = 0, the penalty is an L2 penalty.
//  For alpha = 1, it is an L1 penalty (default: 0.0)
// family: The name of family which is a description of
//  the label distribution to be used in the model.
//  Supported options: auto, binomial, multinomial.
//   (default: auto)
// featuresCol: features column name (default: features)
// fitIntercept: whether to fit an intercept term
//  (default: true)
// labelCol: label column name (default: label)
// maxIter: maximum number of iterations (>= 0)
//  (default: 100)
// predictionCol: prediction column name
//  (default: prediction)
// probabilityCol: Column name for predicted class
//  conditional probabilities.

val dflogr = new RFormula().
  setFormula("y ~ x1 + x2").
  fit(dfLogR).transform(dfLogR).
  select("label","features")
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
// 	logreg_09792f5b38dd-regParam: 8.05825473249957E-6
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
// estimator: estimator for selection
//  (current: logreg_09792f5b38dd)
// estimatorParamMaps: param maps for the estimator
//  (current: [ParamMap;@40b2122b)
// evaluator: evaluator used to select hyper-parameters
//  that maximize the validated metric
//    (current: binEval_4fe7ca428ee0)
// numFolds: number of folds for cross validation (>= 2)
//  (default: 3, current: 8)
// seed: random seed (default: -1191137437)
cvMod.bestModel.explainParams
// res35: String =
// aggregationDepth: suggested depth for treeAggregate
//  (>= 2) (default: 2)
// elasticNetParam: the ElasticNet mixing parameter, in
//  range [0, 1]. For alpha = 0, the penalty is an L2
//  penalty. For alpha = 1, it is an L1 penalty
//   (default: 0.0)
// family: The name of family which is a description of
//  the label distribution to be used in the model.
//  Supported options: auto, binomial, multinomial.
//   (default: auto)
// featuresCol: features column name (default: features)
// fitIntercept: whether to fit an intercept term
//  (default: true)
// labelCol: label column name (default: label)
// maxIter: maximum number of iterations (>= 0)
//  (default: 100)
// predictionCol: prediction column name
//  (default: prediction)
// probabilityCol: Column name for predicted class
//  conditional probabilities. 
cvMod.bestModel.extractParamMap
// res36: org.apache.spark.ml.param.ParamMap =
// {
//   logreg_09792f5b38dd-aggregationDepth: 2,
//   logreg_09792f5b38dd-elasticNetParam: 0.0,
//   logreg_09792f5b38dd-family: auto,
//   logreg_09792f5b38dd-featuresCol: features,
//   logreg_09792f5b38dd-fitIntercept: true,
//   logreg_09792f5b38dd-labelCol: label,
//   logreg_09792f5b38dd-maxIter: 100,
//   logreg_09792f5b38dd-predictionCol: prediction,
//   logreg_09792f5b38dd-probabilityCol: probability,
//   logreg_09792f5b38dd-rawPredictionCol: rawPrediction,
//   logreg_09792f5b38dd-regParam: 2.737241171E-4,
//   logreg_09792f5b38dd-standardization: false,
//   logreg_09792f5b38dd-threshold: 0.5,
//   logreg_09792f5b38dd-tol: 1.0E-6
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
  "org.apache.spark" %% "spark-core" % "2.4.5" % Provided,
  "org.apache.spark" %% "spark-sql" % "2.4.5" % Provided
)

scalaVersion := "2.11.12"


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

