/*
regression.scala

Linear regression for the dataset:

http://archive.ics.uci.edu/ml/datasets/Yacht+Hydrodynamics

from the Machine learning repository: 

http://archive.ics.uci.edu/ml/datasets.html

*/

import breeze.linalg._
import com.github.fommil.netlib.BLAS.{ getInstance => blas }

object Regression {

  def backSolve(A: DenseMatrix[Double],
    y: DenseVector[Double]): DenseVector[Double] = {
    val yc = y.copy
    blas.dtrsv("U", "N", "N", A.cols, A.toArray,
      A.rows, yc.data, 1)
    yc
  }

  case class Lm(y: DenseVector[Double],
    X: DenseMatrix[Double], names: List[String]) {
    require(y.size == X.rows)
    require(names.length == X.cols)
    require(X.rows >= X.cols)
    val QR = qr.reduced(X)
    val q = QR.q
    val r = QR.r
    val qty = q.t * y
    val coefficients = backSolve(r, qty)
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
    lazy val se = breeze.numerics.sqrt(diag(xtxi)) * rse
    lazy val t = coefficients / se
    lazy val p = t.map { 1.0 - tCDF(_, df) }.map { _ * 2 }
    lazy val ybar = mean(y)
    lazy val ymyb = y - ybar
    lazy val ssy = sum(ymyb ^:^ 2.0)
    lazy val rSquared = (ssy - rss) / ssy
    lazy val adjRs = 1.0 - ((n - 1.0) / (n - pp)) * (1 - rSquared)
    lazy val k = pp - 1
    lazy val f = (ssy - rss) / k / (rss / df)
    lazy val pf = 1.0 - fCDF(f, k, df)
    def summary: Unit = {
      println(
        "Estimate\t S.E.\t t-stat\tp-value\t\tVariable")
      println(
        "---------------------------------------------------------")
      (0 until pp).foreach(i => printf(
        "%8.4f\t%6.3f\t%6.3f\t%6.4f %s\t%s\n",
        coefficients(i), se(i), t(i), p(i),
        if (p(i) < 0.05) "*" else " ",
        names(i)))
      printf(
        "\nResidual standard error: %8.4f on %d degrees of freedom\n",
        rse, df)
      printf(
        "Multiple R-squared: %6.4f, Adjusted R-squared: %6.4f\n",
        rSquared, adjRs)
      printf(
        "F-statistic: %6.4f on %d and %d DF, p-value: %6.5f\n\n",
        f, k, df, pf)
    }
  }

  // Main runner method
  def main(args: Array[String]): Unit = {

    val url = "http://archive.ics.uci.edu/ml/machine-learning-databases/00243/yacht_hydrodynamics.data"
    val fileName = "yacht.dat"

    // download the file to disk if it hasn't been already
    if (!(new java.io.File(fileName).exists)) {
      val s = new java.io.PrintWriter(new java.io.File(fileName))
      val data = scala.io.Source.fromURL(url).getLines.toList
      data.foreach(l => s.write(l.trim.split(' ').filter(_ != "").mkString(",") + "\n"))
      s.close
    }

    // read the file from disk
    val mat = csvread(new java.io.File(fileName))
    println("Dim: " + mat.rows + " " + mat.cols)
    val y = mat(::, 6) // response is the final column
    val x = mat(::, 0 to 5)
    // first fit without an intercept
    Lm(y,x,List("LongPos","PrisCoef","LDR","BDR","LBR","Froude")).summary
    // add an intercept and re-fit
    val X = DenseMatrix.horzcat(
      DenseVector.ones[Double](x.rows).toDenseMatrix.t,x)
    val mod = Lm(y,X,List("(Intercept)","LongPos","PrisCoef","LDR","BDR","LBR","Froude"))
    mod.summary

  } // main


}

// eof

