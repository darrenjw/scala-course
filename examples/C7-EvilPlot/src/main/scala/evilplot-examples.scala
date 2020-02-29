/*
evilplot-examples.scala

EvilPlot examples

*/

object EvilPlotExamples {

  import scala.util.Random

  import com.cibo.evilplot._
  import com.cibo.evilplot.plot._
  import com.cibo.evilplot.numeric._
  import com.cibo.evilplot.plot.renderers.PointRenderer

  def scatterExample() = {
    import com.cibo.evilplot.plot.aesthetics.DefaultTheme._
    val points = Seq.fill(150) {
      Point(Random.nextDouble(), Random.nextDouble())
    } :+ Point(0.0, 0.0)
    val years = Seq.fill(150)(Random.nextDouble()) :+ 1.0
    val yearMap = (points zip years).toMap.withDefaultValue(0.0)
    ScatterPlot(
      points,
      //pointRenderer = Some(PointRenderer.depthColor(p => yearMap(p), 0.0, 1.0, None, None))
      pointRenderer = Some(PointRenderer.depthColor((p: Point) => p.x, 0.0, 500.0, None, None))
)
      .standard()
      .xLabel("x")
      .yLabel("y")
      .trend(1, 0)
      .rightLegend()
      .render()
  }

  def scatterHist() = {
    import com.cibo.evilplot.plot.aesthetics.DefaultTheme._
    import com.cibo.evilplot.colors.RGB
    import com.cibo.evilplot.geometry.Extent
    import com.cibo.evilplot.geometry.LineStyle.DashDot

    val allYears = (2007 to 2013).toVector
    val data = Seq.fill(150)(Point(Random.nextDouble(), Random.nextDouble()))
    val years = Seq.fill(150)(allYears(Random.nextInt(allYears.length)))
    val yearMap = (data zip years).toMap

    val xhist = Histogram(data.map(_.x), bins = 50)
    val yhist = Histogram(data.map(_.y), bins = 40)
    ScatterPlot(
      data = data,
      //pointRenderer = Some(PointRenderer.colorByCategory(data, p => yearMap(p)))
    ).topPlot(xhist)
      .rightPlot(yhist)
      .standard()
      .title("Measured vs Actual")
      .xLabel("measured")
      .yLabel("actual")
      .trend(1, 0, color = RGB(45, 45, 45), lineStyle = DashDot)
      .overlayLegend(x = 0.95, y = 0.8)
      .render(Extent(600, 400))
  }

  def functionPlot() = {
    import com.cibo.evilplot.plot.aesthetics.DefaultTheme._
    import com.cibo.evilplot.colors.HTMLNamedColors
    import com.cibo.evilplot.numeric.Bounds
    Overlay(
      FunctionPlot.series(x => x * x, "y = x^2",
        HTMLNamedColors.dodgerBlue, xbounds = Some(Bounds(-1, 1))),
      FunctionPlot.series(x => math.pow(x, 3), "y = x^3",
        HTMLNamedColors.crimson, xbounds = Some(Bounds(-1, 1))),
      FunctionPlot.series(x => math.pow(x, 4), "y = x^4",
        HTMLNamedColors.green, xbounds = Some(Bounds(-1, 1)))
    ).title("A bunch of polynomials.")
      .overlayLegend()
      .standard()
      .render()
  }

  def barChart() = {
    import com.cibo.evilplot.colors.RGB
    import com.cibo.evilplot.geometry.{Align, Drawable, Extent, Rect, Text}
    import com.cibo.evilplot.plot._
    import com.cibo.evilplot.plot.aesthetics
    import com.cibo.evilplot.plot.aesthetics.DefaultTheme.{DefaultFonts}
    import com.cibo.evilplot.plot.renderers.BarRenderer
    implicit val theme = aesthetics.DefaultTheme.DefaultTheme.copy(
      fonts = DefaultFonts.
        copy(tickLabelSize = 14, legendLabelSize = 14, fontFace = "'Lato', sans-serif")
    )
    val percentChange = Seq[Double](-10, 5, 12, 68, -22)
    val labels = Seq("one", "two", "three", "four", "five")
    val labeledByColor = new BarRenderer {
      val positive = RGB(241, 121, 6)
      val negative = RGB(226, 56, 140)
      def render(plot: Plot, extent: Extent, category: Bar): Drawable = {
        val rect = Rect(extent)
        val value = category.values.head
        val color = if (value >= 0) positive else negative
        Align.center(rect filled color, Text(s"$value%", size = 20)
          .filled(theme.colors.label)
        ).group
      }
    }
    BarChart
      .custom(percentChange.map(Bar.apply), spacing = Some(20),
        barRenderer = Some(labeledByColor)
      )
      .standard(xLabels = labels)
      .hline(0)
      .render()
  }

  def clusteredBar() = {
    import com.cibo.evilplot.plot.aesthetics.DefaultTheme._
    val data = Seq[Seq[Double]](
      Seq(1, 2, 3),
      Seq(4, 5, 6),
      Seq(3, 4, 1),
      Seq(2, 3, 4)
    )
    BarChart
      .clustered(
        data,
        labels = Seq("one", "two", "three")
      )
      .title("Clustered Bar Chart Demo")
      .xAxis(Seq("a", "b", "c", "d"))
      .yAxis()
      .frame()
      .bottomLegend()
      .render()
  }

  def boxPlot() = {
    import com.cibo.evilplot.plot.aesthetics.DefaultTheme._
    val data = Seq.fill(10)(Seq.fill(Random.nextInt(30))(Random.nextDouble()))
    BoxPlot(data)
      .standard(xLabels = (1 to 10).map(_.toString))
      .render()
  }

  def pairsPlot() = {
    import com.cibo.evilplot.plot.aesthetics.DefaultTheme._
    val labels = Vector("a", "b", "c", "d")
    val data = for (i <- 1 to 4) yield {
      (labels(i - 1), Seq.fill(10) { Random.nextDouble() * 10 })
    }
    val plots = for ((xlabel, xdata) <- data) yield {
      for ((ylabel, ydata) <- data) yield {
        val points = (xdata, ydata).zipped.map { (a, b) => Point(a, b) }
        if (ylabel == xlabel) {
          Histogram(xdata, bins = 4)
        } else {
          ScatterPlot(points)
        }
      }
    }
    Facets(plots)
      .standard()
      .title("Pairs Plot with Histograms")
      .topLabels(data.map { _._1 })
      .rightLabels(data.map { _._1 })
      .render()
  }

  def contourPlot() = {
    import com.cibo.evilplot.plot.aesthetics.DefaultTheme._
    val data = Seq.fill(100) {
      Point(Random.nextDouble() * 20, Random.nextDouble() * 20)
      }
      ContourPlot(data)
        .standard()
        .xbounds(0, 20)
        .ybounds(0, 20)
        .render()
    }

  def heatMap() = {
    import com.cibo.evilplot.plot.aesthetics.DefaultTheme._
    val x = 100 ; val y = 50
    //val x = 500 ; val y = 500
    val data = Vector.fill(y)(Vector.fill(x)(Random.nextDouble()))
    Heatmap(data,256)
      .standard()
      .render()
  }


  // TODO:
  // Custom point rendering - broken??


  def main(args: Array[String]): Unit = {
    import com.cibo.evilplot.plot.aesthetics.DefaultTheme._
    displayPlot(scatterExample())
    //displayPlot(scatterHist())
    //displayPlot(functionPlot())
    //displayPlot(barChart())
    //displayPlot(clusteredBar())
    //displayPlot(boxPlot())
    //displayPlot(pairsPlot())
    displayPlot(heatMap())
    //displayPlot(contourPlot())
    val bitmap = contourPlot().asBufferedImage
    javax.imageio.ImageIO.write(bitmap, "png", new java.io.File("image.png"))
  }


}

// eof
