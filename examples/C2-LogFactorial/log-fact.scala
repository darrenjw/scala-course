/*
log-fact.scala
Program to compute the log-factorial function
*/

object LogFact {

  import annotation.tailrec
  import math.log

  @tailrec
  def logfact(n: Int, acc: Double = 0.0): Double =
    if (n == 1) acc else
      logfact(n-1, acc + log(n))

  def main(args: Array[String]): Unit = {
    val n = if (args.length == 1) args(0).toInt else 5
    val lfn = logfact(n)
    println(s"logfact($n) = $lfn")
  }

}

// eof

