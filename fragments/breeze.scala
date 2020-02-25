
import breeze.stats.distributions._
// import breeze.stats.distributions._
val poi = Poisson(3.0)
// poi: Poisson = Poisson(3.0)
poi.draw
// res0: Int = 7
poi.draw
// res1: Int = 1


val x = poi.sample(10)
// x: IndexedSeq[Int] = Vector(7,2,2,2,4,3,4,2,0,4)
x
// res2: IndexedSeq[Int] = Vector(7,2,2,2,4,3,4,2,0,4)
x.sum
// res3: Int = 30
x.length
// res4: Int = 10
x.sum.toDouble/x.length
// res5: Double = 3.0


poi.probabilityOf(2)
// res6: Double = 0.22404180765538775
x map {x => poi.probabilityOf(x)}
// res7: IndexedSeq[Double] = Vector(0.0216040314, ...
x map {poi.probabilityOf(_)}
// res8: IndexedSeq[Double] = Vector(0.0216040314, ...


val gau=Gaussian(0.0,1.0)
// gau: Gaussian = Gaussian(0.0, 1.0)
gau.draw
// res9: Double = -1.051465465460726
gau.draw
// res10: Double = 2.4371714130683357
val y=gau.sample(20)
// y: IndexedSeq[Double] = Vector(0.8392352891, ...
y
// res11: IndexedSeq[Double] = Vector(0.839235289, ...
y.sum/y.length
// res12: Double = -0.0678965896649274
y map {gau.logPdf(_)}
// res13: IndexedSeq[Double] = Vector(-1.27109646, ...

Gamma(2.0,3.0).sample(5)
// res14: IndexedSeq[Double] = Vector(2.904534697, ...


import breeze.stats._
// import breeze.stats._
mean(y)
// res15: Double = -0.06789658966492741
variance(y)
// res16: Double = 1.0934019878098706
meanAndVariance(y)
// res17: breeze.stats.MeanAndVariance =
// MeanAndVariance(-0.06789658966492741,
//  1.0934019878098706,20)


import breeze.linalg._
// import breeze.linalg._
val v=DenseVector(y.toArray)
// v: DenseVector[Double] = DenseVector(0.83923528,...
v(1) = 0
v
// res19: DenseVector[Double] = DenseVector(
//  0.839235289114,...
v(1 to 3) := 1.0
// res20: DenseVector[Double]=DenseVector(1.0,1.0,1.0)
v
// res21: DenseVector[Double] = DenseVector(0.8392352,
//  1.0, 1.0, 1.0, -1.9181255, 1.24039957, ...
v(1 to 3) := DenseVector(1.0,1.5,2.0)
// res22: DenseVector[Double]=DenseVector(1.0,1.5,2.0)
v
// res23: DenseVector[Double] = DenseVector(0.8392352,
// 1.0, 1.5, 2.0, -1.918125561681, 1.240399579996, ...
v >:> 0.0
// res24: BitVector=BitVector(0,1,2,3,5,9,10,12,17,19)
(v >:> 0.0).toArray
// res25: Array[Boolean]=Array(true,true,true,true,...


val m = new DenseMatrix(5,4,linspace(1.0,20.0,20).
  toArray)
// m: breeze.linalg.DenseMatrix[Double] =
// 1.0  6.0   11.0  16.0
// 2.0  7.0   12.0  17.0
// 3.0  8.0   13.0  18.0
// 4.0  9.0   14.0  19.0
// 5.0  10.0  15.0  20.0
m
// res26: breeze.linalg.DenseMatrix[Double] =
// 1.0  6.0   11.0  16.0
// 2.0  7.0   12.0  17.0
// 3.0  8.0   13.0  18.0
// 4.0  9.0   14.0  19.0
// 5.0  10.0  15.0  20.0
m.rows
// res27: Int = 5
m.cols
// res28: Int = 4
m(::,1)
// res29: DenseVector[Double] = DenseVector(
//    6.0,7.0,8.0,9.0,10.0)
m(1,::)
// res30: Transpose[DenseVector[Double]] =
//   Transpose(DenseVector(2.0, 7.0, 12.0, 17.0))
m(1,::) := linspace(1.0,2.0,4).t
// res31: Transpose[DenseVector[Double]] =
//   Transpose(DenseVector(1.0, 1.3333333333333333,
//     1.6666666666666665, 2.0))
m
// res32: breeze.linalg.DenseMatrix[Double] =
// 1.0  6.0                 11.0                16.0
// 1.0  1.3333333333333333  1.6666666666666665  2.0
// 3.0  8.0                 13.0                18.0
// 4.0  9.0                 14.0                19.0
// 5.0  10.0                15.0                20.0



// broadcasting (like "apply" in R)
sum(m(::,*)) // column sum
// res33: Transpose[DenseVector[Double]] =
//  Transpose(DenseVector(14.0, 34.33333333333333, ...
sum(m(*,::)) // row sum
// res34: DenseVector[Double] =
//  DenseVector(34.0, 6.0, 42.0, 46.0, 50.0)

val n = m.t
// n: breeze.linalg.DenseMatrix[Double] =
// 1.0   1.0                 3.0   4.0   5.0
// 6.0   1.3333333333333333  8.0   9.0   10.0
// 11.0  1.6666666666666665  13.0  14.0  15.0
// 16.0  2.0                 18.0  19.0  20.0
val o = m*n
// o: breeze.linalg.DenseMatrix[Double] =
// 414.0     59.3333333  482.0     516.0      550.0
// 59.3333   9.55555555  71.3333   77.33333   83.33333
// 482.0     71.3333333  566.0     608.0      650.0
// 516.0     77.3333333  608.0     654.0      700.0
// 550.0     83.3333333  650.0     700.0      750.0
val p = n*m
// p: breeze.linalg.DenseMatrix[Double] =
// 52.0          117.3333333  182.6666666  248.0
// 117.33333333  282.7777777  448.2222222  613.666666
// 182.66666666  448.2222222  713.7777777  979.333333
// 248.0         613.6666666  979.3333333  1345.0

DenseMatrix.eye[Double](3)
// res35: breeze.linalg.DenseMatrix[Double] =
// 1.0  0.0  0.0
// 0.0  1.0  0.0
// 0.0  0.0  1.0         


val N = 1000
// N: Int = 1000
val P = 2
// P: Int = 2
val XX = new DenseMatrix(N,P,gau.sample(P*N).toArray)
// XX: breeze.linalg.DenseMatrix[Double] =
// -0.7489949781984457    -0.5742924772515515
// -0.5240133331383998    -0.4361331555220949
// -0.5764525022050057    -1.2691562428327328
// 0.7326519916718431     0.19642905294418214
// -1.1493500841218598    -1.458947619159962
// 0.08783097116056983    0.3500859440...
val X = DenseMatrix.horzcat(
  DenseVector.ones[Double](N).toDenseMatrix.t,
  XX)
// X: breeze.linalg.DenseMatrix[Double] =
// 1.0  -0.7489949781984457    -0.5742924772515515
// 1.0  -0.5240133331383998    -0.4361331555220949
// 1.0  -0.5764525022050057    -1.2691562428327328
// 1.0  0.7326519916718431     0.19642905294418214
// 1.0  -1.1493500841218598    -1.458947619159962
// 1.0  2.044959173084444      1.7879360132192752...
val b0 = linspace(1.0,2.0,P+1)
// b0: DenseVector[Double] = DenseVector(1.0,1.5,2.0)
val y0 = X * b0
// y0: DenseVector[Double] = DenseVector(
// -1.2720774218007718, -0.6582863107517896, ...
val y = y0 + DenseVector(gau.sample(1000).toArray)
// y: DenseVector[Double] = DenseVector(
// -1.2085936819146115, 0.27627139418016755, ...
// now fit model
val b = X \ y  // linear solve
// b: DenseVector[Double] = DenseVector(
// 1.0242406534162087, 1.4241005745776305,
// 1.9714098738779253)


import java.io.File
// import java.io.File
csvwrite(new File("x-matrix.csv"),X)
val X3 = csvread(new File("x-matrix.csv"))
// X3: breeze.linalg.DenseMatrix[Double] =
// 1.0  -0.7489949781984457    -0.5742924772515515
// 1.0  -0.5240133331383998    -0.4361331555220949
// 1.0  -0.5764525022050057    -1.2691562428327328
// 1.0  0.7326519916718431     0.19642905294418214
// 1.0  -1.1493500841218598    -1.458947619159962
// 1.0  2.044959173084444      1.787936013219275...


val e=eigSym(p)
// e: breeze.linalg.eigSym.DenseEigSym =
// EigSym(DenseVector(-1.589173864914946E-13, ...
e.eigenvalues
// res37: DenseVector[Double] = DenseVector(
// -1.589173864914946E-13, 1.2783114180198827E-13,
// 7.857939135975561, 2385.6976164195803)
e.eigenvectors
// res38: breeze.linalg.DenseMatrix[Double] =
// 0.2906711   -0.46423083  -0.8248907    -0.13984037
// -0.733578   0.4023209    -0.426695     -0.3434104
// 0.5951440   0.588050     -0.028500788  -0.546980
// -0.1522364  -0.5261407   0.36969416    -0.7505506


val s=svd(p) // full SVD
// s: breeze.linalg.svd.DenseSVD =
// SVD(-0.13984037783705727  -0.8248907010788752 ...
s.U
// res39: breeze.linalg.DenseMatrix[Double] =
// -0.13984037  -0.8248907    -0.007271090  -0.5476742
// -0.34341045  -0.4266957    0.4179071     0.7248128
// -0.5469805   -0.02850078   -0.8140009    0.19339719
// -0.7505506   0.3696941     0.4033649     -0.3705357
s.S
// res40: DenseVector[Double] = DenseVector(
//   2385.69761641958, 7.857939135975376,
//   3.5396639195325335E-14, 1.3843384666519122E-15)
s.Vt
// res41: breeze.linalg.DenseMatrix[Double] =
// -0.13984037  -0.3434104   -0.5469805    -0.7505506
// -0.8248907   -0.42669574  -0.028500788  0.3696941
// -0.3564872   0.7852597    -0.5010576    0.0722851
// 0.41583268   -0.28873364  -0.6700307    0.5429317


val ts=svd.reduced(m) // thin SVD
// ts: breeze.linalg.svd.DenseSVD =
// SVD(-0.41409513816680055   0.7907147500096334 ...
ts.U
// res42: breeze.linalg.DenseMatrix[Double] =
// -0.41409513   0.7907147    0.4178037    -0.1538134
// -0.061634609  -0.250403    0.1326486    -0.34803887
// -0.4870128    0.14117668   -0.6056510   0.5325848
// -0.523471     -0.18359234  -0.37148053  -0.6819416
// -0.5599305    -0.5083613   0.5504845    0.3263729
ts.S
// res43: DenseVector[Double] = DenseVector(
//  48.843603638752754, 2.8032015867531554,
//  1.1493769006712675E-15, 5.567163475013936E-16)
ts.Vt
// res44: breeze.linalg.DenseMatrix[Double] =
// -0.13984037  -0.3434104   -0.5469805    -0.7505506
// -0.8248907   -0.42669574  -0.028500788  0.3696941
// 0.4927694    -0.4787964   -0.5207153    0.5067423
// 0.23911985   -0.6861151   0.6548706     -0.20787539
ts.U * diag(ts.S) * ts.Vt
// res45: breeze.linalg.DenseMatrix[Double] =
// 1.0000000  6.000000   11.000000  16.000000
// 1.0000000  1.3333333  1.6666666  2.0
// 3.000000   8.000000   13.000000  18.00000
// 4.000000   9.000000   14.000000  19.00000
// 5.000000   10.000000  15.000000  20.00000


import breeze.linalg._
import breeze.stats._

case class Pca(mat: DenseMatrix[Double]) {
    val xBar = mean(mat(::,*)).t
    val x = mat(*,::) - xBar
    val SVD = svd.reduced(x)
    val loadings = SVD.Vt.t
    val sdev = SVD.S / math.sqrt(x.rows - 1)
    lazy val scores = x * loadings
  }


val X = DenseMatrix((1.0,1.5),(1.5,2.0),(2.0,1.5))
val pca = Pca(X)
pca.sdev
pca.loadings
pca.scores


val q=qr.reduced(m) // thin QR
// q: breeze.linalg.qr.DenseQR =
// QR(-0.13867504905630734  0.881744764716757 ...
q.q
// res46: breeze.linalg.DenseMatrix[Double] =
// -0.13867504  0.881744      0.1402689    0.42793087
// -0.1386750   -0.21741651   0.9622045    0.08741261
// -0.41602514  0.2898886     0.07426633   -0.7332302
// -0.5547001   -0.006039347  -0.06813863  -0.18698794
// -0.6933752   -0.30196738   -0.2105435   0.48645980
q.r
// res47: breeze.linalg.DenseMatrix[Double] =
// -7.211102  -16.271205  -25.33130      -34.39141
// 0.0        4.245661    8.49132        12.736984
// 0.0        0.0         -9.930136E-16  -3.574849E-15
// 0.0        0.0         0.0            1.7455587E-15


cholesky(DenseMatrix((3.0,1.0),(1.0,2.0)))
// res48: breeze.linalg.DenseMatrix[Double] =
// 1.7320508075688772  0.0
// 0.5773502691896258  1.2909944487358056


import breeze.numerics.constants._
// import breeze.numerics.constants._
Pi
// res49: Double = 3.141592653589793
E
// res50: Double = 2.718281828459045
eulerMascheroni
// res51: Double = 0.5772156649015329


import breeze.numerics._
// import breeze.numerics._
erf(2.0) // error function
// res52: Double = 0.9953222650189527
erfinv(erf(2.0))
// res53: Double = 1.999999999999999
sigmoid(1.0) // expit/logistic
// res54: Double = 0.7310585786300049
lgamma(4.0) // log gamma function
// res55: Double = 1.791759469228055
exp(lgamma(4.0))
// res56: Double = 6.0
gammp(2.0,1.0) // incomplete gamma
// res57: Double = 0.2642411176571152
digamma(2.0)
// res58: Double = 0.4227843322079321
lbeta(1.0,2.0) // log beta function
// res59: Double = -0.6931471805599453
Bessel.i0(1.0) // Bessel functions
// res60: Double = 1.2660658777520086
Bessel.i1(1.0)
// res61: Double = 0.5651591039924851
// UFuncs
erf(DenseVector(1.0,2.0,3.0))
// res62: DenseVector[Double] = DenseVector(
//  0.8427007929497151, 0.9953222650189527,
//  0.9999779095030014)


// https://github.com/scalanlp/breeze/wiki/Integration
import breeze.integrate._
// import breeze.integrate._
trapezoid(x => sin(x), 0, Pi, 10)
// res63: Double = 1.9796508112164832
simpson(x => sin(x), 0, Pi, 100)
// res64: Double = 2.0000000007042207


val ode=new HighamHall54Integrator(0.001, 0.1)
// min and max time steps
// ode: HighamHall54Integrator =
//   HighamHall54Integrator@116e4be2
ode.integrate((x,t) => x, DenseVector(1.0),
  linspace(0,1,5).toArray)
// res65: Array[DenseVector[Double]] = Array(
//   DenseVector(1.0),
//   DenseVector(1.2840254150249555),
//   DenseVector(1.6487212664298634),
//   DenseVector(2.1170000083877234),
//   DenseVector(2.718281814377245))  


import breeze.optimize._
// import breeze.optimize._
def f(x: DenseVector[Double]) = (x(0)-5.0)*(x(0)-5.0)
// f: (x: DenseVector[Double])Double
val ag = new ApproximateGradientFunction(f)
// ag: ApproximateGradientFunction[Int,
// DenseVector[Double]] = <function1>
val opt = new LBFGS[DenseVector[Double]]()
// opt: LBFGS[DenseVector[Double]] = LBFGS@69398816
opt.minimize(ag,DenseVector(0.0))
// res66: DenseVector[Double] = DenseVector(4.999995)


def eg(x: DenseVector[Double]) =
  DenseVector(2.0*x(0) - 10.0)
// eg: (x: DenseVector[Double])DenseVector[Double]
val df = new DiffFunction[DenseVector[Double]] {
  def calculate(x: DenseVector[Double]) = (f(x),eg(x))
}
// df: DiffFunction[DenseVector[Double]] = <function1>
opt.minimize(df,DenseVector(0.0))
// res67: DenseVector[Double] = DenseVector(5.0)  


  "org.scalanlp" %% "breeze-viz" % "1.0"


import breeze.plot._
// import breeze.plot._
val fig = Figure("My Figure")
// fig: Figure = Figure@2e23d4da
fig.width=1000
// fig.width: Int = 1000
fig.height=800
// fig.height: Int = 800
val p1 = fig.subplot(0)
// p1: Plot = breeze.plot.Plot@593c763a
p1 += hist(y,50)
// res68: Plot = Plot@593c763a
p1.xlim = (-10,15)
// p1.xlim: (Double, Double) = (-10.0,15.0)
p1.xlabel = "y"
// p1.xlabel: String = y
p1.title = "Distribution of observed response"
// p1.title: String = Distribution of observed response
fig.refresh
fig.saveas("hist.pdf") // or "eps", or "png" for bitmap


val p2=fig.subplot(1,2,1) // rows, cols, subplot index
// p2: breeze.plot.Plot = breeze.plot.Plot@6f6f7d5f


p2 += plot(y0,y,'+',colorcode="red")
// res71: Plot = Plot@6f6f7d5f
val xvals=linspace(min(y0),max(y0),2)
// xvals: DenseVector[Double] = DenseVector(
//   -6.869237935312322, 9.103663726097341)
p2 += plot(xvals,xvals,colorcode="[40,40,200]")
// res72: Plot = Plot@6f6f7d5f
p2.xlabel = "y0"
// p2.xlabel: String = y0
p2.ylabel = "y"
// p2.ylabel: String = y
p2.title = "Observed against true response"
// p2.title: String = Observed against true response


val fig2 = Figure("More plots")
// fig2: Figure = Figure@246f133f
fig2.width=800
// fig2.width: Int = 800
fig2.height=600
// fig2.height: Int = 600
val p3 = fig2.subplot(1,2,0)
// p3: Plot = Plot@7f2d3ec2
p3 += image(X)
// res73: Plot = Plot@7f2d3ec2
p3.xlabel = "p"
// p3.xlabel: String = p
p3.ylabel = "N"
// p3.ylabel: String = N
p3.title = "Covariate matrix"
// p3.title: String = Covariate matrix

val p4 = fig2.subplot(1,2,1)
// p4: breeze.plot.Plot = breeze.plot.Plot@7d3d1f9e
val xs = linspace(0,4,200)
// xs: DenseVector[Double] = DenseVector(0.0,
//  0.020100502512562814, 0.04020100502512563, ...
p4 += plot(xs,xs map (Bessel.i0(_:Double)),name="i0")
// res74: Plot = Plot@7d3d1f9e
p4 += plot(xs,xs map (Bessel.i1(_:Double)),name="i1")
// res75: Plot = Plot@7d3d1f9e
p4.legend = true
// p4.legend: Boolean = true
p4.xlabel = "x"
// p4.xlabel: String = x
p4.ylabel = "i(x)"
// p4.ylabel: String = i(x)
p4.title = "Bessel functions"
// p4.title: String = Bessel functions  

