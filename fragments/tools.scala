
package gibbs

object Gibbs {

    import scala.annotation.tailrec
    import scala.math.sqrt
    import breeze.stats.distributions.{Gamma,Gaussian}

    case class State(x: Double, y: Double) {
      override def toString: String = x.toString + " , " + y + "\n"
    }

    def nextIter(s: State): State = {
      val newX = Gamma(3.0, 1.0/((s.y)*(s.y)+4.0)).draw
      State(newX, Gaussian(1.0/(newX+1), 1.0/sqrt(2*newX+2)).draw)
    }

    @tailrec def nextThinnedIter(s: State,left: Int): State =
      if (left==0) s else nextThinnedIter(nextIter(s),left-1)

    def genIters(s: State, stop: Int, thin: Int): List[State] = {
      @tailrec def go(s: State, left: Int, acc: List[State]): List[State] =
        if (left>0)
          go(nextThinnedIter(s,thin), left-1, s::acc)
          else acc
      go(s,stop,Nil).reverse
    }

    def main(args: Array[String]) = {
      if (args.length != 3) {
        println("Usage: sbt \"run   \"")
        sys.exit(1)
      } else {
        val outF=args(0)
        val iters=args(1).toInt
        val thin=args(2).toInt
        val out = genIters(State(0.0,0.0),iters,thin)
        val s = new java.io.FileWriter(outF)
        s.write("x , y\n")
        out map { it => s.write(it.toString) }
        s.close
      }
    }

}


name := "gibbs"

version := "0.1"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

libraryDependencies  ++= Seq(
            "org.scalanlp" %% "breeze" % "0.10",
            "org.scalanlp" %% "breeze-natives" % "0.10"
)

resolvers ++= Seq(
            "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
            "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
)

scalaVersion := "2.11.6"


addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.13.0")


sbt.version=0.13.7


unmanagedJars in Compile += file("/home/ndjw1/R/x86_64-pc-linux-gnu-library/3.2/rscala/java/rscala_2.11-1.0.6.jar")


name := "rscala test"

version := "0.1"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

libraryDependencies  ++= Seq(
            "org.scalanlp" %% "breeze" % "0.10",
            "org.scalanlp" %% "breeze-natives" % "0.10"
)

resolvers ++= Seq(
            "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
            "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
)

unmanagedJars in Compile += file("/home/ndjw1/R/x86_64-pc-linux-gnu-library/3.2/rscala/java/rscala_2.11-1.0.6.jar")

scalaVersion := "2.11.6"


import org.ddahl.rscala.callback._
import breeze.stats.distributions._
import breeze.linalg._

object ScalaToRTest {

  def main(args: Array[String]) = {

    // first simulate some data consistent with a Poisson regression model
    val x = Uniform(50,60).sample(1000)
    val eta = x map { xi => (xi * 0.1) - 3 }
    val mu = eta map { math.exp(_) }
    val y = mu map { Poisson(_).draw }
    
    // call to R to fit the Poission regression model
    val R = RClient() // initialise an R interpreter
    R.x=x.toArray // send x to R
    R.y=y.toArray // send y to R
    R.eval("mod <- glm(y~x,family=poisson())") // fit the model in R
    // pull the fitted coefficents back into scala
    val beta = DenseVector[Double](R.evalD1("mod$coefficients"))

    // print the fitted coefficents
    println(beta)

  }

}

