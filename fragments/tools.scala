
set scalaVersion := "2.12.1"


set libraryDependencies += "org.scalanlp" %% "breeze" % "0.13"
set libraryDependencies += "org.scalanlp" %% "breeze-natives" % "0.13"


set scalaVersion := "2.12.1"
set libraryDependencies += "org.ddahl" %% "rscala" % "2.0.1"
console


val R = org.ddahl.rscala.RClient()
// R: org.ddahl.rscala.RClient = RClient@9fc5dc1


org.ddahl.rscala.RClient.defaultRCmd
// res0: String = R


val d0 = R.evalD0("rnorm(1)")
// d0: Double = 0.945922465932532


val d1 = R.evalD1("rnorm(5)")
// d1: Array[Double] = Array(-0.8272179841496433, ...


val d2 = R.evalD2("matrix(rnorm(6),nrow=2)")
// d2: Array[Array[Double]] = Array(Array(
//      -0.7545734628207127, ...



R.vec = (1 to 10).toArray // send data to R
// R.vec: (Any, String) = ([I@1e009fac,Array[Int])
R.evalI1("vec")
// res1: Array[Int] = Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

R eval """
vec2 = rep(vec,3)
vec3 = vec2 + 1
mat1 = matrix(vec3,ncol=5)
"""

R.getI2("mat1") // get data back from R
// res3: Array[Array[Int]] = Array(Array(2, 8, 4, 10, 6), Array(3, 9, 5, 11, 7), Array(4, 10, 6, 2, 8), Array(5, 11, 7, 3, 9), Array(6, 2, 8, 4, 10), Array(7, 3, 9, 5, 11))


// Now a Breeze example
import breeze.stats.distributions._
// import breeze.stats.distributions._
import breeze.linalg._
// import breeze.linalg._
import org.ddahl.rscala.RClient
// import org.ddahl.rscala.RClient

// first simulate some data consistent with a Poisson regression model
val x = Uniform(50,60).sample(1000)
// x: IndexedSeq[Double] = Vector(50.54008541753607, 58.188108835261396, 54.33242435629781, 51.46348609867576, 50.329732407391816, 57.18504356490772, 59.682922563618135, 56.87836753345429, 53.421238386295165, 53.428836398290905, 54.29402789914159, 50.598748884993604, 55.02120860114839, 52.70682898511676, 51.85513346620335, 50.302855339853664, 54.40994681847843, 58.542422798176716, 52.87918059806756, 54.63899295241052, 59.30451217987658, 55.35646487722065, 58.30483611114004, 55.03048048780772, 50.652666136757, 51.74295010756255, 53.59612021970451, 50.11797791909953, 56.32411823260278, 59.62241663190592, 51.9742481602477, 54.05919860732068, 51.870403096992334, 54.659312378134736, 53.00732412255694, 50.3798058614912, 52.47038780603685, 58.14087996647727, 53.40165121498069, 50.887047715266604,...
val eta = x map (xi => (xi * 0.1) - 3)
// eta: IndexedSeq[Double] = Vector(2.054008541753607, 2.8188108835261403, 2.433242435629781, 2.1463486098675766, 2.0329732407391816, 2.7185043564907723, 2.9682922563618135, 2.6878367533454295, 2.342123838629517, 2.3428836398290906, 2.4294027899141595, 2.059874888499361, 2.5021208601148395, 2.2706828985116765, 2.1855133466203354, 2.030285533985367, 2.440994681847844, 2.8542422798176723, 2.2879180598067563, 2.4638992952410526, 2.9304512179876587, 2.5356464877220652, 2.830483611114005, 2.503048048780773, 2.0652666136757, 2.1742950107562553, 2.3596120219704515, 2.0117977919099532, 2.632411823260278, 2.962241663190593, 2.1974248160247702, 2.4059198607320686, 2.187040309699234, 2.465931237813474, 2.3007324122556945, 2.0379805861491205, 2.2470387806036847, 2.8140879966477277, 2.340165121498069, ...
val mu = eta map math.exp
// mu: IndexedSeq[Double] = Vector(7.799101554600703, 16.75691289914264, 11.39577229873184, 8.553568889801756, 7.636758558785743, 15.157634864865841, 19.45866080117558, 14.699842116390732, 10.403308063822767, 10.411215513433632, 11.352100466285021, 7.844988250664992, 12.208358744342917, 9.686013119992657, 8.895213709412294, 7.616260749619395, 11.4844584457108, 17.361277232346517, 9.854400037999696, 11.750541154609142, 18.736082647157005, 12.624589852740455, 16.953657819646907, 12.21968344545628, 7.887400506191024, 8.795981863227361, 10.586843192966962, 7.476746907027427, 13.907271366653774, 19.341279831364478, 9.001802328638897, 11.088625583585781, 8.908806747718144, 11.774441853630695, 9.981490344152338, 7.675094346867109, 9.459682126068136, 16.677958488225038, 10.382950869595458, 8.07445...
val y = mu map (Poisson(_).draw)
// y: IndexedSeq[Int] = Vector(8, 15, 12, 15, 9, 17, 22, 16, 6, 8, 15, 6, 8, 8, 12, 2, 9, 23, 10, 9, 14, 6, 25, 7, 10, 5, 12, 8, 16, 18, 8, 14, 6, 19, 15, 1, 13, 7, 7, 8, 6, 13, 8, 7, 13, 11, 17, 17, 8, 18, 7, 12, 22, 19, 16, 13, 6, 23, 16, 9, 6, 5, 11, 15, 19, 17, 7, 14, 12, 10, 13, 16, 12, 5, 14, 16, 8, 12, 10, 22, 8, 16, 12, 5, 13, 12, 1, 16, 10, 11, 9, 8, 8, 12, 13, 9, 15, 7, 14, 4, 11, 12, 12, 10, 16, 9, 7, 4, 12, 17, 10, 9, 21, 12, 9, 11, 8, 25, 17, 11, 6, 6, 9, 11, 12, 24, 9, 16, 18, 8, 11, 5, 17, 13, 7, 5, 17, 3, 10, 9, 22, 6, 11, 16, 22, 19, 14, 8, 8, 11, 16, 7, 4, 13, 10, 20, 6, 8, 10, 10, 17, 24, 10, 23, 3, 18, 16, 13, 20, 21, 9, 15, 11, 20, 21, 5, 4, 2, 13, 12, 8, 9, 25, 11, 16, 7, 13, 11, 12, 19, 11, 17, 9, 17, 8, 11, 8, 14, 8, 6, 21, 8, 10, 8, 17, 14, 7, 18, 5, 16, 14, 9, 9, ...
    
// call to R to fit the Poission regression model
val R = RClient() // initialise an R interpreter
// R: org.ddahl.rscala.RClient = org.ddahl.rscala.RClient@45768f22
R.x=x.toArray // send x to R
// R.x: (Any, String) = ([D@6c7e65fb,Array[Double])
R.y=y.toArray // send y to R
// R.y: (Any, String) = ([I@65a9a726,Array[Int])
R.eval("mod = glm(y~x,family=poisson())") // fit the model in R
// pull the fitted coefficents back into scala
DenseVector[Double](R.evalD1("mod$coefficients"))
// res6: breeze.linalg.DenseVector[Double] = DenseVector(-2.98680078148213, 0.09959046899061315)


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

