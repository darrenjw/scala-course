
val file = CsvFile("cars93.csv")
val df = CsvParser.parse(file).withColIndex(0)
println(df)
val df2 = df.rfilter(_("EngineSize").
             mapValues(CsvParser.parseDouble).at(0)<=4.0)
println(df2)
val wkg=df2.col("Weight").mapValues(CsvParser.parseDouble).
             mapValues(_*0.453592).setColIndex(Index("WeightKG"))
val df3=df2.joinPreserveColIx(wkg.mapValues(_.toString))
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
    val df=readCsv("Cars93",new FileReader("cars93.csv"),colTypes)
    println(df.length,df.columns.length)
    val df2=df.filter(row=>row.as[Double]("EngineSize")<=4.0).toDataTable
    println(df2.length,df2.columns.length)

    val oldCol=df2.columns("Weight").as[Int]
    val newCol=new DataColumn[Double]("WeightKG",oldCol.data.map{_.toDouble*0.453592})
    val df3=df2.columns.add(newCol).get
    println(df3.length,df3.columns.length)

    writeCsv(df3,new File("out.csv"))


val df=Csv.parseFile(new File("cars93.csv")).labeled.toFrame
println(""+df.rows+" "+df.cols)
val df2=df.filter(Cols("EngineSize").as[Double])( _ <= 4.0 )
println(""+df2.rows+" "+df2.cols)
val df3=df2.map(Cols("Weight").as[Int],"WeightKG")(r=>r.toDouble*0.453592)
println(""+df3.rows+" "+df3.cols)
println(df3.colIndex)
val csv = Csv.fromFrame(new CsvFormat(",", header = true))(df3)
new PrintWriter("out.csv") { write(csv.toString); close }


val df = sqlContext.read.format("com.databricks.spark.csv").
                         option("header", "true").
                         option("inferSchema","true").
                         load("cars93.csv")
val df2=df.filter("EngineSize <= 4.0")
val col=df2.col("Weight")*0.453592
val df3=df2.withColumn("WeightKG",col)
df3.write.format("com.databricks.spark.csv").
                         option("header","true").
                         save("out-csv")


import regression._
import scala.math.log
import org.saddle.io._
import FrameUtils._

val file = CsvFile("data/regression.csv")
val df = CsvParser.parse(file).withColIndex(0)
println(df)
framePlot(getCol("Age", df), getCol("OI", df))


scala>  val df = CsvParser.parse(file).withColIndex(0)
df: org.saddle.Frame[Int,String,String] =
[101 x 3]
         OI Age    Sex
       ---- --- ------
  1 ->    5  65 Female
  2 -> 3.75  40 Female
  3 ->  7.6  52 Female
  4 -> 2.45  45 Female
  5 ->  5.4  72 Female
...
 97 -> 8.89  57   Male
 98 -> 16.5  56   Male
 99 -> 4.65  53   Male
100 -> 13.5  56   Male
101 -> 16.1  66   Male

scala> 


val df2 = frameFilter(df, getCol("Age", df), _ > 0.0)
println(df2)
val oi = getCol("OI", df2)
val age = getCol("Age", df2)
val sex = getFactor("Sex", df2)
framePlot(age, oi, sex).saveas("data.png")

val y = oi.mapValues { log(_) }
val m = Lm(y, List(age, sex))
println(m)
m.plotResiduals.saveas("resid.png")

val summ = m.summary
println(summ)


scala> m.summary
res6: regression.LmSummary =
Residuals:
[5 x 1]
   Min -> -1.4005
    LQ -> -0.2918
Median ->  0.0308
    UQ ->  0.3211
   Max ->  0.8979
Coefficients:
[3 x 4]
                   OI     SE  t-val  p-val
               ------ ------ ------ ------
(Intercept) -> 0.8292 0.1777 4.6661 0.0000
        Age -> 0.0162 0.0035 4.6027 0.0000
    SexMale -> 0.3189 0.1157 2.7567 0.0070
Model statistics:
[6 x 1]
          RSS -> 20.0999
          RSE ->  0.4552
           df -> 97.0000
    R-squared ->  0.2621
Adjusted R-sq ->  0.2469
       F-stat -> 17.2265

scala> 

