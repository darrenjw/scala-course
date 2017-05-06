/*
datatable.scala

Test of "scala-datatable" and "scala-csv"

*/

import java.io.{File,FileReader}
import com.github.tototoshi.csv._
import com.github.martincooper.datatable._
import scala.annotation.tailrec
import scala.util.Try

object StringCol

object DatatableTest {

  def readCsv(name: String, file: FileReader, colTypes: Map[String,Object]): DataTable = {
    val reader=CSVReader.open(file)
    val all=reader.allWithHeaders()
    reader.close()
    val ks=colTypes.keys
    val colSet=ks map {key => (key,all map {row => row(key)}) }
    val dataCols=colSet map {pair => colTypes(pair._1) match { 
      case StringCol => new DataColumn[String](pair._1,pair._2)
      case Int       => new DataColumn[Int](pair._1,pair._2 map {x=>
                                        Try(x.toInt).toOption.getOrElse(-99)})
      case Double    => new DataColumn[Double](pair._1,pair._2 map {x=>
                                        Try(x.toDouble).toOption.getOrElse(-99.0)})
      } 
    }
    DataTable(name,dataCols).get
  }

  def writeCsv(df: DataTable,out: File): Unit = {
    val writer = CSVWriter.open(out)
    writer.writeRow(df.columns.map{_.name})
    df.foreach{r=>writer.writeRow(r.values)}
    writer.close()
  }


  def main(args: Array[String]) = {

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
    val df=readCsv("Cars93",new FileReader("../r/cars93.csv"),colTypes)
    println(df.length,df.columns.length)

    val df2=df.filter(row=>row.as[Double]("EngineSize")<=4.0).toDataTable
    println(df2.length,df2.columns.length)

    val oldCol=df2.columns("Weight").as[Int]
    val newCol=new DataColumn[Double]("WeightKG",oldCol.data.map{_.toDouble*0.453592})
    val df3=df2.columns.add(newCol).get
    println(df3.length,df3.columns.length)

    writeCsv(df3,new File("out.csv"))

    //println("Done")
  }



}




