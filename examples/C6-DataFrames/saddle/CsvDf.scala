
object CsvDf {

  def main(args: Array[String]): Unit = {

    import org.saddle.Index
    import org.saddle.io._

    val file = CsvFile("../r/cars93.csv")
    val df = CsvParser.parse(file).withColIndex(0)
    println(df)
    val df2 = df.rfilter(_("EngineSize").mapValues(CsvParser.parseDouble).at(0)<=4.0)
    println(df2)
    val wkg=df2.col("Weight").mapValues(CsvParser.parseDouble).mapValues(_*0.453592).setColIndex(Index("WeightKG"))
    val df3=df2.joinPreserveColIx(wkg.mapValues(_.toString))
    println(df3)

    import CsvImplicits._
    import scala.language.reflectiveCalls
    df3.writeCsvFile("saddle-out.csv")

  }

}
