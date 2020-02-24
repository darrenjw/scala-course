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


  }

}

// eof

