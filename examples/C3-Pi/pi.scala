/*
pi.scala
Simple (parallel) Monte Carlo estimate of Pi using rejection sampling on points randomly scattered on the unit square
*/

object Pi {

  def main(args: Array[String]): Unit = {
	val N = 10000000
	println("Estimating pi based on "+N+" draws")
	println("Creating random vector..")
	val z2 = (1 to N).par map (i => {
		val x = math.random
		val y = math.random
		x*x + y*y
	})
	println("Counting successes...")
	val c = z2 count (_ < 1)
	val mypi = 4.0*c/N
	println("Esimate of pi: "+mypi)
	}

}

// eof


