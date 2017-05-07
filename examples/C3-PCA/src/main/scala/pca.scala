/*
pca.scala

PCA for the dataset:

http://archive.ics.uci.edu/ml/datasets/Iris

from the Machine learning repository: 

http://archive.ics.uci.edu/ml/datasets.html

*/

import breeze.linalg._

object PCA {

  case class Pca(mat: DenseMatrix[Double]) {
    // first centre the matrix...
    import breeze.stats.mean
    val xBar = mean(mat(::,*)).t
    val x = mat(*,::) - xBar
    // then SVD...
    val SVD = svd.reduced(x)
    // contains U, S and Vt
  }


  // Main runner method
  def main(args: Array[String]): Unit = {

    val url = "http://archive.ics.uci.edu/ml/machine-learning-databases/iris/iris.data"
    val fileName = "iris.csv"
    val imap = Map(
      "Iris-setosa" -> 0,
      "Iris-versicolor" -> 1,
      "Iris-virginica" -> 2
    )

    // download the file to disk if it hasn't been already
    val file = new java.io.File(fileName)
    if (!file.exists) {
      val s = new java.io.PrintWriter(file)
      val data = scala.io.Source.fromURL(url).getLines
      data.foreach(l => s.write(l.trim.split(',').map(x=>imap.getOrElse(x,x)).mkString("",",","\n")))
      s.close
    }

    // read the file from disk
    val mat = csvread(new java.io.File(fileName))
    //println(mat)
    println("Dim: " + mat.rows + " " + mat.cols)
    val x = mat(::,0 to 3)
    println("Dim: " + x.rows + " " + x.cols)
    val clas = mat(::,4).toDenseVector
    val pca = Pca(x)

  } // main


}

// eof

