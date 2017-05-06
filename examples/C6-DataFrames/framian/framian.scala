/*
framian.scala

Test of "framian"

*/

import java.io.{File,PrintWriter}
import framian.{Index,Cols}
import framian.csv.{Csv,CsvFormat}

object FramianTest {

  def main(args: Array[String]) = {
    println("Hello")
    val df=Csv.parseFile(new File("../r/cars93.csv")).labeled.toFrame
    println(""+df.rows+" "+df.cols)
    val df2=df.filter(Cols("EngineSize").as[Double])( _ <= 4.0 )
    println(""+df2.rows+" "+df2.cols)
    val df3=df2.map(Cols("Weight").as[Int],"WeightKG")(r=>r.toDouble*0.453592)
    println(""+df3.rows+" "+df3.cols)
    println(df3.colIndex)
    val csv = Csv.fromFrame(new CsvFormat(",", header = true))(df3)
    new PrintWriter("out.csv") { write(csv.toString); close }
    println("Done")
  }

}



