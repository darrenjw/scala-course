/*
spark.scala

Build with: 
sbt package

Then submit with:
spark-submit --class "SparkApp" \
--master local[4] \
target/scala-2.11/spark-template_2.11-0.1.jar

*/

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object SparkApp {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().
      setAppName("Spark Application")
    val sc = new SparkContext(conf)

    sc.textFile("/usr/share/dict/words").
      map(_.trim).
      map(_.toLowerCase).
      flatMap(_.toCharArray).
      filter(_ > '/').
      filter(_ < '}').
      map{(_,1)}.
      reduceByKey(_+_).
      sortBy(_._2,false).
      collect.
      foreach(println)

  }

}

// eof


