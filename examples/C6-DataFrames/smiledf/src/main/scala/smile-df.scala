/*
smile-df.scala

Testing the use of Smile DataFrames

*/

object SmileApp {


  def main(args: Array[String]): Unit = {
 
    val df2 = smile.read.csv("../r/cars93.csv")
    val df3 = df2.filter{ _("EngineSize").asInstanceOf[Double] <= 4.0 }
    val w = df3.select("Weight")
    val wkg = w map {_(0).asInstanceOf[Int] * 0.453592}
    val wkgdf = smile.data.DataFrame.of(wkg.toArray.map(Array(_)),"WKG")
    val adf = df3 merge wkgdf
    smile.write.csv(adf,"cars-smile.csv")

    // read it back for good measure...
    val rdf = smile.read.csv("cars-smile.csv")
    println(rdf)
    println(rdf.summary)

  }

}

// eof

