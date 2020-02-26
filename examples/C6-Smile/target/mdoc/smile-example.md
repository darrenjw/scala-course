# Smile example

## Some mdoc documentation

This is some documentation prepared using `mdoc`. The original file is in `docs`, but the `sbt` task `mdoc` will typecheck and execute the code blocks, and put the compiled markdown document in `target/mdoc`.

We begin by reading the data (we assume that the file "yacht.csv" already exists).

```scala
val df = smile.read.csv("yacht.csv")
// df: smile.data.DataFrame = [LongPos: double, PrisCoef: double, LDR: double, BDR: double, LBR: double, Froude: double, Resist: double]
// +-------+--------+----+----+----+------+------+
// |LongPos|PrisCoef| LDR| BDR| LBR|Froude|Resist|
// +-------+--------+----+----+----+------+------+
// |   -2.3|   0.568|4.78|3.99|3.17| 0.125|  0.11|
// |   -2.3|   0.568|4.78|3.99|3.17|  0.15|  0.27|
// |   -2.3|   0.568|4.78|3.99|3.17| 0.175|  0.47|
// |   -2.3|   0.568|4.78|3.99|3.17|   0.2|  0.78|
// |   -2.3|   0.568|4.78|3.99|3.17| 0.225|  1.18|
// |   -2.3|   0.568|4.78|3.99|3.17|  0.25|  1.82|
// |   -2.3|   0.568|4.78|3.99|3.17| 0.275|  2.61|
// |   -2.3|   0.568|4.78|3.99|3.17|   0.3|  3.76|
// |   -2.3|   0.568|4.78|3.99|3.17| 0.325|  4.99|
// |   -2.3|   0.568|4.78|3.99|3.17|  0.35|  7.16|
// +-------+--------+----+----+----+------+------+
// 298 more rows...
// 
df
// res0: smile.data.DataFrame = [LongPos: double, PrisCoef: double, LDR: double, BDR: double, LBR: double, Froude: double, Resist: double]
// +-------+--------+----+----+----+------+------+
// |LongPos|PrisCoef| LDR| BDR| LBR|Froude|Resist|
// +-------+--------+----+----+----+------+------+
// |   -2.3|   0.568|4.78|3.99|3.17| 0.125|  0.11|
// |   -2.3|   0.568|4.78|3.99|3.17|  0.15|  0.27|
// |   -2.3|   0.568|4.78|3.99|3.17| 0.175|  0.47|
// |   -2.3|   0.568|4.78|3.99|3.17|   0.2|  0.78|
// |   -2.3|   0.568|4.78|3.99|3.17| 0.225|  1.18|
// |   -2.3|   0.568|4.78|3.99|3.17|  0.25|  1.82|
// |   -2.3|   0.568|4.78|3.99|3.17| 0.275|  2.61|
// |   -2.3|   0.568|4.78|3.99|3.17|   0.3|  3.76|
// |   -2.3|   0.568|4.78|3.99|3.17| 0.325|  4.99|
// |   -2.3|   0.568|4.78|3.99|3.17|  0.35|  7.16|
// +-------+--------+----+----+----+------+------+
// 298 more rows...
//
```

We can get a quick summary of the data as follows.

```scala
df.summary
// res1: smile.data.DataFrame = [column: String, count: long, min: double, avg: double, max: double]
// +--------+-----+-----+---------+-----+
// |  column|count|  min|      avg|  max|
// +--------+-----+-----+---------+-----+
// | LongPos|  308|   -5|-2.381818|    0|
// |PrisCoef|  308| 0.53| 0.564136|  0.6|
// |     LDR|  308| 4.34| 4.788636| 5.14|
// |     BDR|  308| 2.81| 3.936818| 5.35|
// |     LBR|  308| 2.73| 3.206818| 3.64|
// |  Froude|  308|0.125|   0.2875| 0.45|
// |  Resist|  308| 0.01|10.495357|62.42|
// +--------+-----+-----+---------+-----+
//
```

We can now carry out OLS regression after a couple of imports

```scala
import smile.data.formula._
import scala.language.postfixOps
val mod = smile.regression.ols("Resist" ~, df)
// mod: smile.regression.LinearModel = Linear Model:
// 
// Residuals:
// 	       Min	        1Q	    Median	        3Q	       Max
// 	  -11.7700	   -7.5578	   -1.8198	    6.1620	   31.5715
// 
// Coefficients:
//                   Estimate Std. Error    t value   Pr(>|t|)
// Intercept         -19.2367    27.1133    -0.7095     0.4786 
// LongPos             0.1938     0.3381     0.5734     0.5668 
// PrisCoef           -6.4194    44.1590    -0.1454     0.8845 
// LDR                 4.2330    14.1651     0.2988     0.7653 
// BDR                -1.7657     5.5212    -0.3198     0.7493 
// LBR                -4.5164    14.2000    -0.3181     0.7507 
// Froude            121.6676     5.0658    24.0175     0.0000 ***
// ---------------------------------------------------------------------
// Significance codes:  0 '***' 0.001 '**' 0.01 '*' 0.05 '.' 0.1 ' ' 1
// 
// Residual standard error: 8.9596 on 301 degrees of freedom
// Multiple R-squared: 0.6576,    Adjusted R-squared: 0.6507
// F-statistic: 96.3327 on 6 and 301 DF,  p-value: 4.526e-67
// 
mod
// res2: smile.regression.LinearModel = Linear Model:
// 
// Residuals:
// 	       Min	        1Q	    Median	        3Q	       Max
// 	  -11.7700	   -7.5578	   -1.8198	    6.1620	   31.5715
// 
// Coefficients:
//                   Estimate Std. Error    t value   Pr(>|t|)
// Intercept         -19.2367    27.1133    -0.7095     0.4786 
// LongPos             0.1938     0.3381     0.5734     0.5668 
// PrisCoef           -6.4194    44.1590    -0.1454     0.8845 
// LDR                 4.2330    14.1651     0.2988     0.7653 
// BDR                -1.7657     5.5212    -0.3198     0.7493 
// LBR                -4.5164    14.2000    -0.3181     0.7507 
// Froude            121.6676     5.0658    24.0175     0.0000 ***
// ---------------------------------------------------------------------
// Significance codes:  0 '***' 0.001 '**' 0.01 '*' 0.05 '.' 0.1 ' ' 1
// 
// Residual standard error: 8.9596 on 301 degrees of freedom
// Multiple R-squared: 0.6576,    Adjusted R-squared: 0.6507
// F-statistic: 96.3327 on 6 and 301 DF,  p-value: 4.526e-67
//
```

If we don't want to regress on everything, we can just choose what we'd like to regress on.

```scala
smile.regression.ols("Resist" ~ "Froude", df)
// res3: smile.regression.LinearModel = Linear Model:
// 
// Residuals:
// 	       Min	        1Q	    Median	        3Q	       Max
// 	  -11.2396	   -7.6662	   -1.7111	    6.4039	   32.1537
// 
// Coefficients:
//                   Estimate Std. Error    t value   Pr(>|t|)
// Intercept         -24.4841     1.5336   -15.9654     0.0000 ***
// Froude            121.6676     5.0339    24.1698     0.0000 ***
// ---------------------------------------------------------------------
// Significance codes:  0 '***' 0.001 '**' 0.01 '*' 0.05 '.' 0.1 ' ' 1
// 
// Residual standard error: 8.9031 on 306 degrees of freedom
// Multiple R-squared: 0.6562,    Adjusted R-squared: 0.6551
// F-statistic: 584.1803 on 1 and 306 DF,  p-value: 6.233e-73
// 
smile.regression.ols("Resist" ~ "Froude" + "LongPos", df)
// res4: smile.regression.LinearModel = Linear Model:
// 
// Residuals:
// 	       Min	        1Q	    Median	        3Q	       Max
// 	  -11.2361	   -7.4169	   -1.7970	    6.3781	   32.1378
// 
// Coefficients:
//                   Estimate Std. Error    t value   Pr(>|t|)
// Intercept         -24.0234     1.7315   -13.8743     0.0000 ***
// Froude            121.6676     5.0394    24.1434     0.0000 ***
// LongPos             0.1934     0.3362     0.5754     0.5655 
// ---------------------------------------------------------------------
// Significance codes:  0 '***' 0.001 '**' 0.01 '*' 0.05 '.' 0.1 ' ' 1
// 
// Residual standard error: 8.9129 on 305 degrees of freedom
// Multiple R-squared: 0.6566,    Adjusted R-squared: 0.6544
// F-statistic: 291.6172 on 2 and 305 DF,  p-value: 1.604e-71
//
```

### Summary

This brief document has illustrated how easy and convenient it is to produce executable documentation and reports for Scala.

