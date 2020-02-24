/*
smile.scala

Testing the use of Smile as a Scala library for data analysis

*/

object SmileApp {


  def main(args: Array[String]): Unit = {
    println("Hi")
    val url = "http://archive.ics.uci.edu/ml/machine-learning-databases/00243/yacht_hydrodynamics.data"
    val fileName = "yacht.csv"

    // download the file to disk if it hasn't been already
    val file = new java.io.File(fileName)
    if (!file.exists) {
      println("Downloading file...")
      val s = new java.io.PrintWriter(file)
      s.write("Resist,LongPos,PrisCoef,LDR,BDR,LBR,Froude\n")
      val data = scala.io.Source.fromURL(url).getLines
      data.foreach(l => s.write(l.trim.split(' ').filter(_ != "").mkString("",",","\n")))
      s.close
      println("File downloaded.")
    }

    // now read the data
    val df = smile.read.csv(fileName)
    println(df)
    println(df.summary)

    // simple OLS regression (normalising all variables...)
    import smile.data.formula._
    import scala.language.postfixOps

    val mod = smile.regression.ols("Resist" ~, df)
    println(mod)

    // try to figure out what's going on...
    println(buildFormula("Resist" ~).y(df).toDoubleArray)
    println(buildFormula("Resist" ~).matrix(df, true))
    println(buildFormula("Resist" ~).x(df))
    println(buildFormula("Resist" ~).x(df).summary)


    // cars df exercise
    val df2 = smile.read.csv("../C6-DataFrames/r/cars93.csv")
    println(df2.summary)
    val df3 = df2.filter{ _("EngineSize").asInstanceOf[Double] <= 4.0 }
    println(df3.summary)
    val w = df3.select("Weight")
    println(w)
    println(w(0))
    val wkg = w map {_(0).asInstanceOf[Int] * 0.453592}
    println(wkg)
    val wkgdf = smile.data.DataFrame.of(wkg.toArray.map(Array(_)),"WKG")
    println(wkgdf)
    println(wkgdf.summary)
    val adf = df3 merge wkgdf
    println(adf)
    println(adf.summary)
    smile.write.csv(adf,"cars-smile.csv")
    // read it back for good measure...
    val rdf = smile.read.csv("cars-smile.csv")
    println(rdf)
    println(rdf.summary)

  }

}

// eof

