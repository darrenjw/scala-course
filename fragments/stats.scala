
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
// QR(-0.44721359549  -0.10656672499  0.36158387049
// -0.44721359549  -0.6394003499   -0.620639682
// -0.44721359549  0.6926837124    -0.3519495634
// -0.44721359549  0.24865569166   0.0109426697
// -0.44721359549  -0.1953723291   0.6000627061   ,
// -2.23606797     -4.964070910    -4.427414595
// 0.0             1.12605506      0.9431155162
// 0.0             0.0             -1.3260969508  )
val q = QR.q
// q: breeze.linalg.DenseMatrix[Double] =
// -0.44721359549  -0.10656672499  0.36158387049
// -0.44721359549  -0.6394003499   -0.620639682
// -0.44721359549  0.6926837124    -0.35194956342
// -0.44721359549  0.24865569166   0.0109426697
// -0.44721359549  -0.1953723291   0.6000627061
val r = QR.r
// r: breeze.linalg.DenseMatrix[Double] =
// -2.2360679774  -4.96407091004  -4.42741459544
// 0.0            1.1260550608    0.943115516239
// 0.0            0.0             -1.326096950840
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
    1.0 - 0.5 * Beta.regularizedBeta(xt,0.5*df,0.5)
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
    "----------------------------------------------")
    (0 until pp).foreach(i => printf(
     "%8.4f\t%6.3f\t%6.3f\t%6.4f %s\t%s\n",
      coefficients(i),se(i),t(i),p(i),
      if (p(i) < 0.05) "*" else " ",
      names(i)))
    printf(
    "\nResidual standard error: %8.4f on %d degrees",
    rse,df)
    printf("of freedom\n")
    printf(
    "Multiple R-squared: %6.4f, ",rSquared)
    printf("Adjusted R-squared: %6.4f\n",
     adjRs)
    printf(
    "F-statistic: %6.4f on %d and %d DF, ",
     f,k,df)
    printf("p-value: %6.5f\n\n",
     pf)
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
// Estimate	S.E.	 t-stat	p-value	     Variable
// ---------------------------------------------------
//  -0.2804	0.418	-0.671	0.5711       Intercept
//   0.0536	0.225	 0.238	0.8337       Var1
//   0.9906	0.156	 6.365	0.0238 *     Var2
// Residual std error: 0.2064 on 2 degrees of freedom
// Multiple R-squared: 0.9696, Adj R-squared: 0.9391
// F-statistic: 31.867 on 2 and 2 DF, p-value: 0.0304


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
// Estimate	S.E.	 t-stat	p-value	    Variable
// ---------------------------------------------------
//   0.9859	0.031	31.845	0.0000 *    Intercept
//   1.4939	0.032	46.488	0.0000 *    Var1
//   1.9695	0.033	60.401	0.0000 *    Var2
// Residual std error: 0.978 on 997 degrees of freedom
// Multiple R-squared: 0.8535, Adj R-squared: 0.8532
// F-stat: 2903.097 on 2 and 997 DF, p-value: 0.0000


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


val url = "http://archive.ics.uci.edu/ml/"+
  "machine-learning-databases/00243/"+
  "yacht_hydrodynamics.data"
val fileName = "yacht.csv"
val file = new java.io.File(fileName)
if (!file.exists) {
      val s = new java.io.PrintWriter(file)
      val data = scala.io.Source.fromURL(url).getLines
      data.foreach(l => s.write(l.trim.split(' ').
        filter(_ != "").mkString("",",","\n")))
      s.close
}


val mat = csvread(new java.io.File(fileName))
// mat: breeze.linalg.DenseMatrix[Double] =
// -2.3  0.568  4.78  3.99  3.17  0.125  0.11
// -2.3  0.568  4.78  3.99  3.17  0.15   0.27
// -2.3  0.568  4.78  3.99  3.17  0.175  0.47
// -2.3  0.568  4.78  3.99  3.17  0.2    0.78
// -2.3  0.568  4.78  3.99  3.17  0.225  1.18
// ....
println("Dim: " + mat.rows + " " + mat.cols)
// Dim: 308 7
val y = mat(::, 6) // response is the final column
// y: DenseVector[Double]=DenseVector(0.11,0.27,...
val x = mat(::, 0 to 5)
// x: breeze.linalg.DenseMatrix[Double] =
// -2.3  0.568  4.78  3.99  3.17  0.125
// -2.3  0.568  4.78  3.99  3.17  0.15
// -2.3  0.568  4.78  3.99  3.17  0.175
// -2.3  0.568  4.78  3.99  3.17  0.2
// -2.3  0.568  4.78  3.99  3.17  0.225
// -2.3  0.568  4.78  3.99  3.17  0.25
// -2.3  0.568  4.78  3.99  3.17  0.275
// ...


Lm(y,x,List("LongPos","PrisCoef","LDR","BDR","LBR",
  "Froude")).summary
// Estimate	 S.E.	 t-stat	p-value	    Variable
// --------------------------------------------------
//   0.1943	 0.338	 0.575	0.5655      LongPos
// -35.6159	16.005	-2.225	0.0268 *    PrisCoef
//  -4.1631	 7.779	-0.535	0.5929      LDR
//   1.3747	 3.297	 0.417	0.6770      BDR
//   3.3232	 8.911	 0.373	0.7095      LBR
// 121.4745	 5.054	24.034	0.0000 *    Froude
// Residual std error: 8.952 on 302 degrees of freedom
// Multiple R-squared: 0.6570, Adj R-squared: 0.6513
// F-statistic: 115.6 on 5 and 302 DF, p-value: 0.000


val X = DenseMatrix.horzcat(
   DenseVector.ones[Double](x.rows).toDenseMatrix.t,x)
// X: breeze.linalg.DenseMatrix[Double] =
// 1.0  -2.3  0.568  4.78  3.99  3.17  0.125
// 1.0  -2.3  0.568  4.78  3.99  3.17  0.15
// 1.0  -2.3  0.568  4.78  3.99  3.17  0.175
// 1.0  -2.3  0.568  4.78  3.99  3.17  0.2
// 1.0  -2.3  0.568  4.78  3.99  3.17  0.225
// ...
val mod = Lm(y,X,List("(Intercept)","LongPos",
  "PrisCoef","LDR","BDR","LBR","Froude"))
// mod: Lm =
// Lm(DenseVector(0.11, 0.27, 0.47, ...
mod.summary
// Estimate	 S.E.	 t-stat	p-value	   Variable
// ---------------------------------------------------
// -19.2367	27.113	-0.709	0.4786     (Intercept)
//   0.1938	 0.338	 0.573	0.5668     LongPos
//  -6.4194	44.159	-0.145	0.8845     PrisCoef
//   4.2330	14.165	 0.299	0.7653     LDR
//  -1.7657	 5.521	-0.320	0.7493     BDR
//  -4.5164	14.200	-0.318	0.7507     LBR
// 121.6676	 5.066	24.018	0.0000 *   Froude
// Residual std error: 8.959 on 301 degrees of freedom
// Multiple R-squared: 0.657, Adj R-squared: 0.6507
// F-statistic: 96.332 on 6 and 301 DF, p-value: 0.000


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
val x = DenseVector(Gaussian(1.0,3.0).sample(N).
  toArray)
// x: DenseVector[Double] = DenseVector(6.272108997,
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
// y: DenseVector[Double]=DenseVector(1.0,1.0,1.0,...


@annotation.tailrec
def IRLS(
  b: Double => Double,
  bp: Double => Double,
  bpp: Double => Double,
  y: DenseVector[Double],
  X: DenseMatrix[Double],
  bhat0: DenseVector[Double],
  its: Int,
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
  its: Int = 30
): DenseVector[Double] = {
  def b(x: Double): Double = math.log(1.0+math.exp(x))
  def bp(x: Double): Double = expit(x)
  def bpp(x: Double): Double = {
    val e = math.exp(-x)
      e/((1.0+e)*(1.0+e))
  }
  val bhat0 = DenseVector.zeros[Double](X.cols)
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
// ones: DenseVector[Double] = DenseVector(1.0,1.0,...
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
  its: Int = 30
): DenseVector[Double] = {
  val bhat0 = DenseVector.zeros[Double](X.cols)
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
// fig: Figure = breeze.plot.Figure@18a68150
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


val df2 = smile.read.csv("../r/cars93.csv")
val df3 = df2.filter{ _("EngineSize").asInstanceOf[Double] <= 4.0 }
val w = df3.select("Weight")
val wkg = w map {_(0).asInstanceOf[Int] * 0.453592}
val wkgdf = smile.data.DataFrame.of(wkg.toArray.map(Array(_)),"WKG")
val adf = df3 merge wkgdf
smile.write.csv(adf,"cars-smile.csv")


val df = spark.read.
         option("header", "true").
         option("inferSchema","true").
         csv("../r/cars93.csv")
val df2=df.filter("EngineSize <= 4.0")
val col=df2.col("Weight")*0.453592
val df3=df2.withColumn("WeightKG",col)
df3.write.format("com.databricks.spark.csv").
                         option("header","true").
                         save("out-csv")


"com.github.haifengl" %% "smile-scala" % "2.2.1"


val url = "http://archive.ics.uci.edu/ml/machine-learning-databases/00243/yacht_hydrodynamics.data"
val fileName = "yacht.csv"
// download the file to disk if it hasn't been already
val file = new java.io.File(fileName)
if (!file.exists) {
  val s = new java.io.PrintWriter(file)
  s.write("LongPos,PrisCoef,LDR,BDR,LBR,Froude,Resist\n")
  val data = scala.io.Source.fromURL(url).getLines
  data.foreach(l => s.write(l.trim.split(' ').filter(_ != "").mkString("",",","\n")))
  s.close
}
// now read the file from disk
val df = smile.read.csv(fileName)


df
// [LongPos: double, PrisCoef: double, LDR: double, BDR: double, LBR: double, Froude: double, Resist: double]
// +-------+--------+----+----+----+------+------+
// |LongPos|PrisCoef| LDR| BDR| LBR|Froude|Resist|
// +-------+--------+----+----+----+------+------+
// |   -2.3|   0.568|4.78|3.99|3.17| 0.125|  0.11|
// |   -2.3|   0.568|4.78|3.99|3.17|  0.15|  0.27|
// |   -2.3|   0.568|4.78|3.99|3.17| 0.175|  0.47|
// |   -2.3|   0.568|4.78|3.99|3.17|   0.2|  0.78|
// |   -2.3|   0.568|4.78|3.99|3.17| 0.225|  1.18|
// |   -2.3|   0.568|4.78|3.99|3.17|  0.25|  1.82|
// |   -2.3|   0.568|4.78|3.99|3.17| 0.275|  2.61|
// |   -2.3|   0.568|4.78|3.99|3.17|   0.3|  3.76|
// |   -2.3|   0.568|4.78|3.99|3.17| 0.325|  4.99|
// |   -2.3|   0.568|4.78|3.99|3.17|  0.35|  7.16|
// +-------+--------+----+----+----+------+------+
// 298 more rows...


df.summary
// [column: String, count: long, min: double, avg: double, max: double]
// +--------+-----+-----+---------+-----+
// |  column|count|  min|      avg|  max|
// +--------+-----+-----+---------+-----+
// | LongPos|  308|   -5|-2.381818|    0|
// |PrisCoef|  308| 0.53| 0.564136|  0.6|
// |     LDR|  308| 4.34| 4.788636| 5.14|
// |     BDR|  308| 2.81| 3.936818| 5.35|
// |     LBR|  308| 2.73| 3.206818| 3.64|
// |  Froude|  308|0.125|   0.2875| 0.45|
// |  Resist|  308| 0.01|10.495357|62.42|
// +--------+-----+-----+---------+-----+


import smile.data.formula._
import scala.language.postfixOps
smile.regression.ols("Resist" ~, df)
// Linear Model:
// 
// Residuals:
//        Min        1Q	  Median        3Q       Max
//   -11.7700   -7.5578	 -1.8198    6.1620   31.5715
// 
// Coefficients:
//             Estimate Std. Error  t value  Pr(>|t|)
// Intercept   -19.2367    27.1133  -0.7095    0.4786 
// LongPos       0.1938     0.3381   0.5734    0.5668 
// PrisCoef     -6.4194    44.1590  -0.1454    0.8845 
// LDR           4.2330    14.1651   0.2988    0.7653 
// BDR          -1.7657     5.5212  -0.3198    0.7493 
// LBR          -4.5164    14.2000  -0.3181    0.7507 
// Froude      121.6676     5.0658  24.0175    0.0000 ***
// ------------------------------------------------------
// Significance codes: 0 '***' 0.001 '**' 0.01 '*' 0.05 '.' 0.1 ' ' 1
// 
// Residual standard error: 8.9596 on 301 degrees of freedom
// Multiple R-squared: 0.6576, Adjusted R-squared: 0.6507
// F-statistic: 96.3327 on 6 and 301 DF, p-value: 4.526e-67


smile.regression.ols("Resist" ~ "Froude" + "LongPos", df)


libraryDependencies += "com.stripe" %% "rainier-core" % "0.3.0"


import com.stripe.rainier.core._
import com.stripe.rainier.compute._
import com.stripe.rainier.sampler._
val n = 1000
val mu = 3.0
val sig = 5.0
implicit val rng = ScalaRNG(3)
val x = Vector.fill(n)(mu + sig*rng.standardNormal) 


val m = Normal(0,100).latent
val s = Gamma(1,10).latent
val model = Model.observe(x, Normal(m,s))


val sampler = EHMC(warmupIterations = 5000, iterations = 5000)
val out = model.sample(sampler)
val mut = out.predict(m)
val sigt = out.predict(s)
mut.sum/mut.length
// res4: Double = 2.976841199039055
sigt.sum/sigt.length
// res5: Double = 4.9790361575463615

