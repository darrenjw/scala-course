
import breeze.linalg._
// import breeze.linalg._

val y = DenseVector(1.0,2.0,3.0,2.0,1.0)
// y: DenseVector[Double] = DenseVector(1.0, 2.0,
//                   3.0, 2.0, 1.0)
val Xwoi = DenseMatrix((2.1,1.4),(1.5,2.2),
                (3.0,3.1),(2.5,2.2),(2.0,1.0))
// Xwoi: breeze.linalg.DenseMatrix[Double] =
// 2.1  1.4
// 1.5  2.2
// 3.0  3.1
// 2.5  2.2
// 2.0  1.0
val X = DenseMatrix.horzcat(DenseVector.ones[Double](
                    Xwoi.rows).toDenseMatrix.t, Xwoi)
// X: breeze.linalg.DenseMatrix[Double] =
// 1.0  2.1  1.4
// 1.0  1.5  2.2
// 1.0  3.0  3.1
// 1.0  2.5  2.2
// 1.0  2.0  1.0


import breeze.stats.regression._
val mod = leastSquares(X,y)
// mod: LeastSquaresRegressionResult = <function1>
mod.coefficients
// res3: DenseVector[Double] = DenseVector(
//   -0.2804082840767416, 0.05363661640849975,
//    0.9905732301261981)
mod.rSquared
// res4: Double = 0.08519073288426877


X \ y
// res5: DenseVector[Double] = DenseVector(
//   -0.2804082840767416, 0.05363661640849975,
//    0.9905732301261981)


import com.github.fommil.netlib.BLAS.{ getInstance =>
    blas }
def backSolve(A: DenseMatrix[Double],
  y: DenseVector[Double]): DenseVector[Double] = {
    val yc = y.copy
    blas.dtrsv("U", "N", "N", A.cols, A.toArray,
        A.rows, yc.data, 1)
    yc
}


val QR = qr.reduced(X)
// QR: breeze.linalg.qr.DenseQR =
// QR(-0.44721359549995787  -0.10656672499880869  0.36158387049672375
// -0.44721359549995787  -0.6394003499928489   -0.620639682977601
// -0.44721359549995787  0.6926837124922532    -0.3519495634209888
// -0.44721359549995787  0.24865569166388585   0.0109426697650324
// -0.44721359549995787  -0.1953723291644815   0.6000627061368334   ,
// -2.23606797749979  -4.964070910049532  -4.427414595449584
// 0.0                1.12605506082074    0.9431155162394527
// 0.0                0.0                 -1.3260969508404699  )
val q = QR.q
// q: breeze.linalg.DenseMatrix[Double] =
// -0.44721359549995787  -0.10656672499880869  0.36158387049672375
// -0.44721359549995787  -0.6394003499928489   -0.620639682977601
// -0.44721359549995787  0.6926837124922532    -0.3519495634209888
// -0.44721359549995787  0.24865569166388585   0.0109426697650324
// -0.44721359549995787  -0.1953723291644815   0.6000627061368334
val r = QR.r
// r: breeze.linalg.DenseMatrix[Double] =
// -2.23606797749979  -4.964070910049532  -4.427414595449584
// 0.0                1.12605506082074    0.9431155162394527
// 0.0                0.0                 -1.3260969508404699
backSolve(r, q.t * y)
// res6: DenseVector[Double] = DenseVector(
//   -0.2804082840767417, 0.053636616408499954,
//    0.9905732301261979)


case class Lm(y: DenseVector[Double],
  X: DenseMatrix[Double],names: List[String]) {
  require(y.size == X.rows)
  require(names.length == X.cols)
  require(X.rows >= X.cols)
  val QR = qr.reduced(X)
  val q = QR.q
  val r = QR.r
  val qty = q.t * y
  val coefficients = backSolve(r,qty)
  import breeze.stats._
  import org.apache.commons.math3.special.Beta
  def tCDF(t: Double, df: Double): Double = {
    val xt = df / (t * t + df)
    1.0 - 0.5 * Beta.regularizedBeta(xt, 0.5 * df, 0.5)
  }
  def fCDF(x: Double, d1: Double, d2: Double) = {
    val xt = x * d1 / (x * d1 + d2)
    Beta.regularizedBeta(xt, 0.5 * d1, 0.5 * d2)
  }
  lazy val fitted = q * qty
  lazy val residuals = y - fitted
  lazy val n = X.rows
  lazy val pp = X.cols
  lazy val df = n - pp
  lazy val rss = sum(residuals ^:^ 2.0)
  lazy val rse = math.sqrt(rss / df)
  lazy val ri = inv(r)
  lazy val xtxi = ri * (ri.t)
  lazy val se = breeze.numerics.sqrt(diag(xtxi))*rse
  lazy val t = coefficients / se
  lazy val p = t.map {1.0 - tCDF(_, df)}.map {_ * 2}
  lazy val ybar = mean(y)
  lazy val ymyb = y - ybar
  lazy val ssy = sum(ymyb ^:^ 2.0)
  lazy val rSquared = (ssy - rss) / ssy
  lazy val adjRs = 1.0 - ((n-1.0)/(n-pp))*(1-rSquared)
  lazy val k = pp-1
  lazy val f = (ssy - rss) / k / (rss/df)
  lazy val pf = 1.0 - fCDF(f,k,df)
  def summary: Unit = {
    println(
    "Estimate\t S.E.\t t-stat\tp-value\t\tVariable")
    println(
    "---------------------------------------------------------")
    (0 until pp).foreach(i => printf(
     "%8.4f\t%6.3f\t%6.3f\t%6.4f %s\t%s\n",
      coefficients(i),se(i),t(i),p(i),
      if (p(i) < 0.05) "*" else " ",
      names(i)))
    printf(
     "\nResidual standard error: %8.4f on %d degrees of freedom\n",
    rse,df)
    printf(
     "Multiple R-squared: %6.4f, Adjusted R-squared: %6.4f\n",
     rSquared,adjRs)
    printf(
     "F-statistic: %6.4f on %d and %d DF, p-value: %6.5f\n\n",
     f,k,df,pf)
  }
}


val mod2 = Lm(y, X, List("Intercept","Var1","Var2"))
// mod2: Lm =
// Lm(DenseVector(1.0, 2.0, 3.0, 2.0, 1.0),
// 1.0  2.1  1.4
// 1.0  1.5  2.2
// 1.0  3.0  3.1
// 1.0  2.5  2.2
// 1.0  2.0  1.0  ,List(Intercept, Var1, Var2))


mod2.coefficients
// res7: DenseVector[Double] = DenseVector(
//  -0.2804082840767417, 0.053636616408499954,
//   0.9905732301261979)
mod2.p
// res8: DenseVector[Double] = DenseVector(
//  0.5711265415017199, 0.8337153027851611,
//  0.02380731603365338)
mod2.rse
// res9: Double = 0.20638644926965116
mod2.rSquared
// res10: Double = 0.9695747382556184
mod2.adjRs
// res11: Double = 0.9391494765112367
mod2.f
// res12: Double = 31.86742472099393
mod2.pf
// res13: Double = 0.03042526174438165


mod2.summary
// Estimate	 S.E.	 t-stat	p-value		Variable
// ---------------------------------------------------------
//  -0.2804	 0.418	-0.671	0.5711  	Intercept
//   0.0536	 0.225	 0.238	0.8337  	Var1
//   0.9906	 0.156	 6.365	0.0238 *	Var2
// Residual standard error:   0.2064 on 2 degrees of freedom
// Multiple R-squared: 0.9696, Adjusted R-squared: 0.9391
// F-statistic: 31.8674 on 2 and 2 DF, p-value: 0.03043


// Synthetic data...
val N = 1000
// N: Int = 1000
val P = 2
// P: Int = 2
val gau=breeze.stats.distributions.Gaussian(0.0,1.0)
// gau: Gaussian = Gaussian(0.0, 1.0)
val XX = new DenseMatrix(N,P,gau.sample(P*N).toArray)
// XX: breeze.linalg.DenseMatrix[Double] =
// 0.11002285761491036   -0.20149313279570508
// 0.6185281490520133    -0.037185723563396854
// 1.7071005345717225    1.2413540328321024
// -0.2220569665834186   0.4686688219889745
// ...
val X = DenseMatrix.horzcat(
   DenseVector.ones[Double](N).toDenseMatrix.t,XX)
// X: breeze.linalg.DenseMatrix[Double] =
// 1.0  0.11002285761491036   -0.20149313279570508
// 1.0  0.6185281490520133    -0.037185723563396854
// 1.0  1.7071005345717225    1.2413540328321024
// 1.0  -0.2220569665834186   0.4686688219889745
// 1.0  ...
val b0 = linspace(1.0,2.0,P+1)
// b0: DenseVector[Double] = DenseVector(1.0,1.5,2.0)
val y0 = X * b0
// y0: DenseVector[Double] = DenseVector(...
val y = y0 + DenseVector(gau.sample(1000).toArray)
// y: DenseVector[Double] = DenseVector(...


val b = X \ y 
// b: DenseVector[Double] = DenseVector(
//   0.985909926499153, 1.4939286812873727,
//   1.9694751417388345)


val mod3 = Lm(y,X, List("Intercept","Var1","Var2"))
// mod3: Lm =
// Lm(DenseVector(0.2863458414136328, ...
mod3.summary
// Estimate	 S.E.	 t-stat	p-value		Variable
// ---------------------------------------------------------
//   0.9859	 0.031	31.845	0.0000 *	Intercept
//   1.4939	 0.032	46.488	0.0000 *	Var1
//   1.9695	 0.033	60.401	0.0000 *	Var2
// Residual standard error:   0.9778 on 997 degrees of freedom
// Multiple R-squared: 0.8535, Adjusted R-squared: 0.8532
// F-statistic: 2903.0978 on 2 and 997 DF, p-value: 0.00000


import breeze.plot._
// import breeze.plot._
val fig = Figure("Regression diagnostics")
// fig: Figure = Figure@37569f24
fig.width = 1000
// fig.width: Int = 1000
fig.height = 800
// fig.height: Int = 800
val p = fig.subplot(1,1,0)
// p: Plot = Plot@298c0508
p += plot(mod3.fitted,mod3.residuals,'.')
// res16: Plot = Plot@298c0508
p.xlabel = "Fitted values"
// p.xlabel: String = Fitted values
p.ylabel = "Residuals"
// p.ylabel: String = Residuals
p.title = "Residuals against fitted values"
// p.title: String = Residuals against fitted values


import breeze.linalg._
// import breeze.linalg._
import breeze.stats.distributions.{Gaussian,Binomial}
// import distributions.{Gaussian, Binomial}
val N = 2000
// N: Int = 2000
val beta = DenseVector(0.1,0.3)
// beta: DenseVector[Double] = DenseVector(0.1, 0.3)
val ones = DenseVector.ones[Double](N)
// ones: DenseVector[Double] = DenseVector(1.0, ...
val x = DenseVector(Gaussian(1.0,3.0).sample(N).toArray)
// x: DenseVector[Double] = DenseVector(6.27210899796815,
//  3.0135444386214765, 4.373649007468049, ...
val X = DenseMatrix.vertcat(ones.toDenseMatrix,
  x.toDenseMatrix).t
// X: breeze.linalg.DenseMatrix[Double] =
// 1.0  6.27210899796815
// 1.0  3.0135444386214765
// 1.0  4.373649007468049
// 1.0  -0.34689004119207434
// ...
val theta = X * beta
// theta: DenseVector[Double] = DenseVector(
// 1.981632699390445,
// 1.004063331586443, 1.4120947022404147, ...
def expit(x: Double): Double = 1.0/(1.0+math.exp(-x))
// expit: (x: Double)Double
val p = theta map expit
// p: breeze.linalg.DenseVector[Double] = DenseVector(
//  0.8788551012256975, 0.7318567276541773, ...
val y = p map (pi => new Binomial(1,pi).draw) map (
   _.toDouble)
// y: DenseVector[Double] = DenseVector(1.0, 1.0, 1.0, ...


@annotation.tailrec
def IRLS(
  b: Double => Double,
  bp: Double => Double,
  bpp: Double => Double,
  y: DenseVector[Double],
  X: DenseMatrix[Double],
  bhat0: DenseVector[Double] = DenseVector.
    zeros[Double](X.cols),
  its: Int = 30,
  tol: Double = 0.0000001
): DenseVector[Double] = if (its == 0) {
  println("WARNING: IRLS did not converge")
  bhat0
} else {
  val eta = X * bhat0
  val W = diag(eta map bpp) 
  val z = y - (eta map bp)
  val bhat = bhat0 + (X.t * W * X) \ (X.t * z) 
  if (norm(bhat-bhat0) < tol) bhat else
    IRLS(b,bp,bpp,y,X,bhat,its-1,tol)
}


def logReg(
  y: DenseVector[Double],
  X: DenseMatrix[Double],
  bhat0: DenseVector[Double] =
    DenseVector.zeros[Double](X.cols),
  its: Int = 30
): DenseVector[Double] = {
  def b(x: Double): Double = math.log(1.0+math.exp(x))
  def bp(x: Double): Double = expit(x)
  def bpp(x: Double): Double = {
    val e = math.exp(-x)
      e/((1.0+e)*(1.0+e))
  }
  IRLS(b,bp,bpp,y,X,bhat0,its)
}


val betahat = logReg(y,X)
// betahat: DenseVector[Double] = DenseVector(
//   0.0563791369276622, 0.31606883046872236)


import breeze.plot._
// import breeze.plot._
val fig = Figure("Logistic regression")
// fig: Figure = breeze.plot.Figure@11befd2e
fig.width = 1000
// fig.width: Int = 1000
fig.height = 800
// fig.height: Int = 800
val p = fig.subplot(1,1,0)
// p: breeze.plot.Plot = breeze.plot.Plot@62b33d65
p += plot(x,y,'+')
// res0: breeze.plot.Plot = breeze.plot.Plot@62b33d65
p += plot(x,x map (xi =>
  expit(betahat(0)+betahat(1)*xi)),
  '.',colorcode="red")
// res1: breeze.plot.Plot = breeze.plot.Plot@62b33d65
p.xlabel = "x"
// p.xlabel: String = x
p.ylabel = "y"
// p.ylabel: String = y
p.title = "Logistic regression"
// p.title: String = Logistic regression


import breeze.linalg._
// import breeze.linalg._
import breeze.numerics._
// import breeze.numerics._
import breeze.stats.distributions.{Gaussian,Poisson}
// import distributions.{Gaussian, Poisson}
val N = 2000
// N: Int = 2000
val beta = DenseVector(-3.0,0.1)
// beta: DenseVector[Double] = DenseVector(-3.0, 0.1)
val ones = DenseVector.ones[Double](N)
// ones: DenseVector[Double] = DenseVector(1.0, 1.0, ...
val x = DenseVector(Gaussian(50.0,10.0).sample(N).
   toArray)
// x: DenseVector[Double] = DenseVector(
//  54.811589088666324,
//  35.54528051285478, 59.11931256262003, ...
val X = DenseMatrix.vertcat(ones.toDenseMatrix,
          x.toDenseMatrix).t
// X: breeze.linalg.DenseMatrix[Double] =
// 1.0  54.811589088666324
// 1.0  35.54528051285478
// 1.0  59.11931256262003
// 1.0  54.17607633281474
// ...
val theta = X * beta
// theta: DenseVector[Double] = DenseVector(
//  2.4811589088666324,
//  0.5545280512854784, 2.9119312562620037, ...
val mu = exp(theta)
// mu: DenseVector[Double] = DenseVector(
//  11.955111277135977,
//   1.7411190719311496, 18.392284504390723, ...
val y = mu map (mui => new Poisson(mui).draw) map
  (_.toDouble)
// y: DenseVector[Double] = DenseVector(6.0, 2.0, ...


def poiReg(
  y: DenseVector[Double],
  X: DenseMatrix[Double],
  bhat0: DenseVector[Double] = DenseVector.
    zeros[Double](X.cols),
  its: Int = 30
): DenseVector[Double] = {
  IRLS(math.exp,math.exp,math.exp,y,X,bhat0,its)
}


poiReg(y,X)
// WARNING: IRLS did not converge
// res0: DenseVector[Double] = DenseVector(
//  -77.71670795773245, 1.1994502808957552)


val betahat = poiReg(y,X,its=100)
// betahat: DenseVector[Double] = DenseVector(
//  -3.0118026291813274, 0.10020295518543317)


import breeze.plot._
// import breeze.plot._
val fig = Figure("Poisson regression")
// fig: breeze.plot.Figure = breeze.plot.Figure@18a68150
fig.width = 1000
// fig.width: Int = 1000
fig.height = 800
// fig.height: Int = 800
val p = fig.subplot(1,1,0)
// p: breeze.plot.Plot = breeze.plot.Plot@7bdf24a7
p += plot(x,y,'+')
// res1: breeze.plot.Plot = breeze.plot.Plot@7bdf24a7
p += plot(x,x map (xi => math.exp(
  betahat(0)+betahat(1)*xi)),
  '.',colorcode="red")
// res2: breeze.plot.Plot = breeze.plot.Plot@7bdf24a7
p.xlabel = "x"
// p.xlabel: String = x
p.ylabel = "y"
// p.ylabel: String = y
p.title = "Poisson regression"
// p.title: String = Poisson regression


val file = CsvFile("cars93.csv")
val df = CsvParser.parse(file).withColIndex(0)
println(df)
val df2 = df.rfilter(_("EngineSize").
  mapValues(CsvParser.parseDouble).
  at(0)<=4.0)
println(df2)
val wkg=df2.col("Weight").
  mapValues(CsvParser.parseDouble).
  mapValues(_*0.453592).setColIndex(Index("WeightKG"))
val df3=df2.
  joinPreserveColIx(wkg.mapValues(_.toString))
println(df3)
df3.writeCsvFile("saddle-out.csv")


    val colTypes=Map("DriveTrain" -> StringCol, 
                     "Min.Price" -> Double, 
                     "Cylinders" -> Int, 
                     "Horsepower" -> Int, 
                     "Length" -> Int, 
                     "Make" -> StringCol, 
                     "Passengers" -> Int, 
                     "Width" -> Int, 
                     "Fuel.tank.capacity" -> Double, 
                     "Origin" -> StringCol, 
                     "Wheelbase" -> Int, 
                     "Price" -> Double, 
                     "Luggage.room" -> Double, 
                     "Weight" -> Int, 
                     "Model" -> StringCol, 
                     "Max.Price" -> Double, 
                     "Manufacturer" -> StringCol, 
                     "EngineSize" -> Double, 
                     "AirBags" -> StringCol, 
                     "Man.trans.avail" -> StringCol, 
                     "Rear.seat.room" -> Double, 
                     "RPM" -> Int, 
                     "Turn.circle" -> Double, 
                     "MPG.highway" -> Int, 
                     "MPG.city" -> Int, 
                     "Rev.per.mile" -> Int, 
                     "Type" -> StringCol)
    val df=readCsv("Cars93",new FileReader(
      "cars93.csv"),colTypes)
    println(df.length,df.columns.length)
    val df2=df.
      filter(row=>row.as[Double]("EngineSize")<=4.0).
      toDataTable
    println(df2.length,df2.columns.length)

    val oldCol=df2.columns("Weight").as[Int]
    val newCol=new DataColumn[Double]("WeightKG",
      oldCol.data.map{_.toDouble*0.453592})
    val df3=df2.columns.add(newCol).get
    println(df3.length,df3.columns.length)

    writeCsv(df3,new File("out.csv"))


val df=Csv.parseFile(new File("cars93.csv")).
  labeled.toFrame
println(""+df.rows+" "+df.cols)
val df2=df.
  filter(Cols("EngineSize").as[Double])( _ <= 4.0 )
println(""+df2.rows+" "+df2.cols)
val df3=df2.
  map(Cols("Weight").as[Int],"WeightKG")(r =>
    r.toDouble*0.453592)
println(""+df3.rows+" "+df3.cols)
println(df3.colIndex)
val csv = Csv.
  fromFrame(new CsvFormat(",", header = true))(df3)
new PrintWriter("out.csv") {
  write(csv.toString); close
}


val df = sqlContext.read.
  format("com.databricks.spark.csv").
  option("header", "true").
  option("inferSchema","true").
  load("cars93.csv")
val df2=df.filter("EngineSize <= 4.0")
val col=df2.col("Weight")*0.453592
val df3=df2.withColumn("WeightKG",col)
df3.write.format("com.databricks.spark.csv").
                         option("header","true").
                         save("out-csv")

