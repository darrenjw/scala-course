
scala> import breeze.stats.distributions._
import breeze.stats.distributions._

scala> val poi = Poisson(3.0)
poi: breeze.stats.distributions.Poisson = Poisson(3.0)

scala> poi.draw
res19: Int = 2

scala> poi.draw
res20: Int = 3


scala> val x = poi.sample(10)
x: IndexedSeq[Int] = Vector(2, 3, 3, 4, 2, 2, 1, 2, 4, 2)

scala> x
res21: IndexedSeq[Int] = Vector(2, 3, 3, 4, 2, 2, 1, 2, 4, 2)

scala> x.sum
res22: Int = 25

scala> x.length
res23: Int = 10

scala> x.sum.toDouble/x.length
res24: Double = 2.5


scala> poi.probabilityOf(2)
res25: Double = 0.22404180765538775

scala> x map {x => poi.probabilityOf(x)}
res26: IndexedSeq[Double] = Vector(0.22404180765538775, 0.22404180765538775, 0.22404180765538775, 0.16803135574154085, 0.22404180765538775, 0.22404180765538775, 0.14936120510359185, 0.22404180765538775, 0.16803135574154085, 0.22404180765538775)

scala> x map {poi.probabilityOf(_)}
res27: IndexedSeq[Double] = Vector(0.22404180765538775, 0.22404180765538775, 0.22404180765538775, 0.16803135574154085, 0.22404180765538775, 0.22404180765538775, 0.14936120510359185, 0.22404180765538775, 0.16803135574154085, 0.22404180765538775)


scala> val gau=Gaussian(0.0,1.0)
gau: breeze.stats.distributions.Gaussian = Gaussian(0.0, 1.0)

scala> gau.draw
res28: Double = 1.606121255846881

scala> gau.draw
res29: Double = -0.1747896055492152

scala> val y=gau.sample(20)
y: IndexedSeq[Double] = Vector(-1.3758577012869702, -1.2148314970824652, -0.022501190144116855, 0.3244006323566883, 0.35978577573558407, 0.9651857500320781, -0.40834034207848985, 0.11583348205331555, -0.8797699986810634, -0.33609738668214695, 0.7043252811790879, -1.2045594639823656, 0.19442688045065826, -0.31442160076087067, 0.06313451540562891, -1.5304745838587115, -1.2372764884467027, 0.5875490994217284, -0.9385520597707431, -0.6647903243363228)

scala> y
res30: IndexedSeq[Double] = Vector(-1.3758577012869702, -1.2148314970824652, -0.022501190144116855, 0.3244006323566883, 0.35978577573558407, 0.9651857500320781, -0.40834034207848985, 0.11583348205331555, -0.8797699986810634, -0.33609738668214695, 0.7043252811790879, -1.2045594639823656, 0.19442688045065826, -0.31442160076087067, 0.06313451540562891, -1.5304745838587115, -1.2372764884467027, 0.5875490994217284, -0.9385520597707431, -0.6647903243363228)

scala> y.sum/y.length
res31: Double = -0.34064156102380994

scala> y map {gau.logPdf(_)}
res32: IndexedSeq[Double] = Vector(-1.8654307403000054, -1.6568463163564844, -0.9191916849836235, -0.9715564183413823, -0.9836614354155007, -1.3847302992371653, -1.0023094506890617, -0.9256472309869705, -1.3059361584943119, -0.975419259871957, -1.1669755840586733, -1.6444202843394145, -0.93783943912556, -0.9683690047171869, -0.9209315167224245, -2.090114759123421, -1.6843650876361744, -1.0915455053203147, -1.359378517654625, -1.1399116208702693)

scala> Gamma(2.0,3.0).sample(5)
res33: IndexedSeq[Double] = Vector(2.38436441278546, 2.125017198373521, 2.333118708811143, 5.880076392566909, 2.0901427084667503)


scala> import breeze.stats.DescriptiveStats._
import breeze.stats.DescriptiveStats._

scala> mean(y)
res34: Double = -0.34064156102380994

scala> variance(y)
res35: Double = 0.574257149387757

scala> meanAndVariance(y)
res36: (Double, Double) = (-0.34064156102380994,0.574257149387757)


scala> import breeze.linalg._
import breeze.linalg._

scala> val v=DenseVector(y.toArray)
v: breeze.linalg.DenseVector[Double] = DenseVector(-1.3758577012869702, -1.2148314970824652, -0.022501190144116855, 0.3244006323566883, 0.35978577573558407, 0.9651857500320781, -0.40834034207848985, 0.11583348205331555, -0.8797699986810634, -0.33609738668214695, 0.7043252811790879, -1.2045594639823656, 0.19442688045065826, -0.31442160076087067, 0.06313451540562891, -1.5304745838587115, -1.2372764884467027, 0.5875490994217284, -0.9385520597707431, -0.6647903243363228)

scala> v(1) = 0

scala> v
res38: breeze.linalg.DenseVector[Double] = DenseVector(-1.3758577012869702, 0.0, -0.022501190144116855, 0.3244006323566883, 0.35978577573558407, 0.9651857500320781, -0.40834034207848985, 0.11583348205331555, -0.8797699986810634, -0.33609738668214695, 0.7043252811790879, -1.2045594639823656, 0.19442688045065826, -0.31442160076087067, 0.06313451540562891, -1.5304745838587115, -1.2372764884467027, 0.5875490994217284, -0.9385520597707431, -0.6647903243363228)

scala> v(1 to 3) := 1.0
res39: breeze.linalg.DenseVector[Double] = DenseVector(1.0, 1.0, 1.0)

scala> v
res40: breeze.linalg.DenseVector[Double] = DenseVector(-1.3758577012869702, 1.0, 1.0, 1.0, 0.35978577573558407, 0.9651857500320781, -0.40834034207848985, 0.11583348205331555, -0.8797699986810634, -0.33609738668214695, 0.7043252811790879, -1.2045594639823656, 0.19442688045065826, -0.31442160076087067, 0.06313451540562891, -1.5304745838587115, -1.2372764884467027, 0.5875490994217284, -0.9385520597707431, -0.6647903243363228)

scala> v(1 to 3) := DenseVector(1.0,1.5,2.0)
res41: breeze.linalg.DenseVector[Double] = DenseVector(1.0, 1.5, 2.0)

scala> v
res42: breeze.linalg.DenseVector[Double] = DenseVector(-1.3758577012869702, 1.0, 1.5, 2.0, 0.35978577573558407, 0.9651857500320781, -0.40834034207848985, 0.11583348205331555, -0.8797699986810634, -0.33609738668214695, 0.7043252811790879, -1.2045594639823656, 0.19442688045065826, -0.31442160076087067, 0.06313451540562891, -1.5304745838587115, -1.2372764884467027, 0.5875490994217284, -0.9385520597707431, -0.6647903243363228)

scala> v :> 0.0
res43: breeze.linalg.BitVector = BitVector(1, 2, 3, 4, 5, 7, 10, 12, 14, 17)

scala> (v :> 0.0).toArray
res44: Array[Boolean] = Array(false, true, true, true, true, true, false, true, false, false, true, false, true, false, true, false, false, true, false, false)


scala> val m = new DenseMatrix(5,4,linspace(1.0,20.0,20).toArray)
m: breeze.linalg.DenseMatrix[Double] = 
1.0  6.0   11.0  16.0  
2.0  7.0   12.0  17.0  
3.0  8.0   13.0  18.0  
4.0  9.0   14.0  19.0  
5.0  10.0  15.0  20.0  

scala> m
res45: breeze.linalg.DenseMatrix[Double] = 
1.0  6.0   11.0  16.0  
2.0  7.0   12.0  17.0  
3.0  8.0   13.0  18.0  
4.0  9.0   14.0  19.0  
5.0  10.0  15.0  20.0  

scala> m.rows
res46: Int = 5

scala> m.cols
res47: Int = 4

scala> m(::,1)
res48: breeze.linalg.DenseVector[Double] = DenseVector(6.0, 7.0, 8.0, 9.0, 10.0)

scala> m(1,::)
res49: breeze.linalg.DenseMatrix[Double] = 2.0  7.0  12.0  17.0  

scala> m(1,::) := linspace(1.0,2.0,4)
res50: breeze.linalg.DenseMatrix[Double] = 1.0  1.3333333333333333  1.6666666666666665  2.0  

scala> m
res51: breeze.linalg.DenseMatrix[Double] = 
1.0  6.0                 11.0                16.0  
1.0  1.3333333333333333  1.6666666666666665  2.0   
3.0  8.0                 13.0                18.0  
4.0  9.0                 14.0                19.0  
5.0  10.0                15.0                20.0  

scala> 

scala> val n = m.t
n: breeze.linalg.DenseMatrix[Double] = 
1.0   1.0                 3.0   4.0   5.0   
6.0   1.3333333333333333  8.0   9.0   10.0  
11.0  1.6666666666666665  13.0  14.0  15.0  
16.0  2.0                 18.0  19.0  20.0  

scala> n
res52: breeze.linalg.DenseMatrix[Double] = 
1.0   1.0                 3.0   4.0   5.0   
6.0   1.3333333333333333  8.0   9.0   10.0  
11.0  1.6666666666666665  13.0  14.0  15.0  
16.0  2.0                 18.0  19.0  20.0  

scala> val o = m*n
o: breeze.linalg.DenseMatrix[Double] = 
414.0              59.33333333333333  482.0              516.0              550.0              
59.33333333333333  9.555555555555555  71.33333333333333  77.33333333333333  83.33333333333333  
482.0              71.33333333333333  566.0              608.0              650.0              
516.0              77.33333333333333  608.0              654.0              700.0              
550.0              83.33333333333333  650.0              700.0              750.0              

scala> o
res53: breeze.linalg.DenseMatrix[Double] = 
414.0              59.33333333333333  482.0              516.0              550.0              
59.33333333333333  9.555555555555555  71.33333333333333  77.33333333333333  83.33333333333333  
482.0              71.33333333333333  566.0              608.0              650.0              
516.0              77.33333333333333  608.0              654.0              700.0              
550.0              83.33333333333333  650.0              700.0              750.0              

scala> val p = n*m
p: breeze.linalg.DenseMatrix[Double] = 
52.0                117.33333333333333  182.66666666666666  248.0              
117.33333333333333  282.77777777777777  448.22222222222223  613.6666666666667  
182.66666666666666  448.22222222222223  713.7777777777778   979.3333333333334  
248.0               613.6666666666667   979.3333333333334   1345.0             

scala> p
res54: breeze.linalg.DenseMatrix[Double] = 
52.0                117.33333333333333  182.66666666666666  248.0              
117.33333333333333  282.77777777777777  448.22222222222223  613.6666666666667  
182.66666666666666  448.22222222222223  713.7777777777778   979.3333333333334  
248.0               613.6666666666667   979.3333333333334   1345.0             


scala> val X = new DenseMatrix(1000,5,gau.sample(5000).toArray)
X: breeze.linalg.DenseMatrix[Double] = 
-0.40186606934180685  0.9847148198711287    ... (5 total)
-0.4760404521336951   -0.833737041320742    ...
-0.3315199616926892   -0.19460446824586297  ...
-0.14764615494496836  -0.17947658245206904  ...
-0.8357372755800905   -2.456222113596015    ...
-0.44458309216683184  1.848007773944826     ...
0.060314034896221065  0.5254462055311016    ...
0.8637867740789016    -0.9712570453363925   ...
0.11620167261655819   -1.2231380938032232   ...
-0.3335514290842617   -0.7487303696662753   ...
-0.5598937433421866   0.11083382409013512   ...
-1.7213395389510568   1.1717491221846357    ...
-1.078873342208984    0.9386859686451607    ...
-0.7793854546738327   -0.9829373863442161   ...
-1.054275201631216    0.10100826507456745   ...
-0.6947188686537832   1.215...
scala> val b0 = linspace(1.0,2.0,5)
b0: breeze.linalg.DenseVector[Double] = DenseVector(1.0, 1.25, 1.5, 1.75, 2.0)

scala> val y0 = X * b0
y0: breeze.linalg.DenseVector[Double] = DenseVector(0.08200546839589107, -0.5992571365601228, -5.646398002309553, -7.346136663325798, -8.486423788193362, 1.451119214541837, -0.25792385841948406, 2.324936340609002, -1.2285599639827862, -4.030261316643863, -4.1732627416377674, -0.5077151099958077, -0.2087263741903591, 0.46678616461409383, 2.0244342278575975, 1.775756468177401, -4.799821190728213, -1.8518388060564481, 1.5892306875621767, -1.6528539564387008, 1.4064864330994125, -0.8734630221484178, -7.75470002781836, -0.2893619536998493, -5.972958583649336, -4.952666733286302, 0.5431255990489059, -2.477076684976403, -0.6473617571867107, -0.509338416957489, -1.5415350935719594, -0.47068802465681125, 2.546118380362026, -7.940401988804477, -1.037049442788122, -1.564016663370888, -3.3147087994...
scala> val y = y0 + DenseVector(gau.sample(1000).toArray)
y: breeze.linalg.DenseVector[Double] = DenseVector(-0.572127338358624, -0.16481167194161406, -4.213873268823003, -10.142015065601388, -7.893898543052863, 1.7881055848475076, -0.26987820512025357, 3.3289433195054148, -2.514141419925489, -4.643625974157769, -3.8061000214061886, 0.6462624993109218, 0.23603338389134149, 1.0211137806779267, 2.0061727641393317, 0.022624943149799348, -5.429601401989341, -1.836181225242386, 1.0265599173053048, -0.1673732536615371, 0.8418249443853956, -1.1547110533101967, -8.392100167478764, -1.1586377992526877, -6.400362975646245, -5.487018086963841, 0.3038055584347069, -1.2247410435868684, -0.06476921390724344, -1.5039074374120407, -1.0189111630970076, 1.307339668865724, 2.048320821568789, -8.769328824477714, -0.9104251029228555, -1.3533910178496698, -2.178788...
scala> val b = X \ y  // defaults to a QR-solve of the least squares problem
b: breeze.linalg.DenseVector[Double] = DenseVector(0.9952708232116663, 1.2344546192238952, 1.5543512339052412, 1.744091673457169, 1.9874158953720507)


scala> def mean(arr: Array[Int]): Double = {
     |   arr.sum.toDouble/arr.length
     | }
mean: (arr: Array[Int])Double

scala> mean(Array(3,1,4,5))
res55: Double = 3.25


object BreezeGibbs {

  import breeze.stats.distributions._
  import scala.math.sqrt

  class State(val x: Double, val y: Double)

  def nextIter(s: State): State = {
    val newX = Gamma(3.0, 1.0 / ((s.y) * (s.y) + 4.0)).draw()
    new State(newX, Gaussian(1.0 / (newX + 1), 1.0 / sqrt(2 * newX + 2)).draw())
  }

  def nextThinnedIter(s: State, left: Int): State = {
    if (left == 0) s
    else nextThinnedIter(nextIter(s), left - 1)
  }

  def genIters(s: State, current: Int, stop: Int, thin: Int): State = {
    if (!(current > stop)) {
      println(current + " " + s.x + " " + s.y)
      genIters(nextThinnedIter(s, thin), current + 1, stop, thin)
    } else s
  }

  def main(args: Array[String]) {
    println("Iter x y")
    genIters(new State(0.0, 0.0), 1, 50000, 1000)
  }

}


// start with non-Breeze stuff

val a = 5
a
a = 6
a

var b = 7
b
b = 8
b

val c = List(3,4,5,6)
c(1)
c.sum
c.length
c.product
c.foldLeft(0)((x,y) => x+y)
c.foldLeft(0)(_+_)
c.foldLeft(1)(_*_)

val d = Vector(2,3,4,5,6,7,8,9)
d
d.slice(3,6)
val e = d.updated(3,0)
d
e

val f=(1 to 10).toList
f
f.map(x => x*x)
f map {x => x*x}
f filter {_ > 4}

// introduce breeze through random distributions
// https://github.com/scalanlp/breeze/wiki/Quickstart

import breeze.stats.distributions._
val poi = Poisson(3.0)
poi.draw
poi.draw
val x = poi.sample(10)
x
x.sum
x.length
x.sum.toDouble/x.length
poi.probabilityOf(2)
x map {x => poi.probabilityOf(x)}
x map {poi.probabilityOf(_)}

val gau=Gaussian(0.0,1.0)
gau.draw
gau.draw
val y=gau.sample(20)
y
y.sum/y.length
y map {gau.logPdf(_)}

Gamma(2.0,3.0).sample(5)

import breeze.stats.DescriptiveStats._
mean(y)
variance(y)
meanAndVariance(y)


// move on to linear algebra
// https://github.com/scalanlp/breeze/wiki/Breeze-Linear-Algebra

import breeze.linalg._
val v=DenseVector(y.toArray)
v(1) = 0
v
v(1 to 3) := 1.0
v
v(1 to 3) := DenseVector(1.0,1.5,2.0)
v
v :> 0.0
(v :> 0.0).toArray

val m = new DenseMatrix(5,4,linspace(1.0,20.0,20).toArray)
m
m.rows
m.cols
m(::,1)
m(1,::)
m(1,::) := linspace(1.0,2.0,4)
m

val n = m.t
n
val o = m*n
o
val p = n*m
p

// regression and QR solution

val X = new DenseMatrix(1000,5,gau.sample(5000).toArray)
val b0 = linspace(1.0,2.0,5)
val y0 = X * b0
val y = y0 + DenseVector(gau.sample(1000).toArray)
val b = X \ y  // defaults to a QR-solve of the least squares problem

// a simple function example

def mean(arr: Array[Int]): Double = {
  arr.sum.toDouble/arr.length
}

mean(Array(3,1,4,5))

