/*
spark.scala

code for a "spark-shell" session

spark-shell --master local[4]

*/

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


// eof


