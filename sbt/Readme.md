# sbt installation and testing

`sbt` is the simple/scala build tool. It is the standard build tool for Scala. Other than a recent Java installation, `sbt` is all you need for building Scala projects.

Some useful links relating to `sbt` are given below:

* [sbt](http://www.scala-sbt.org/)
  * [Documentation](http://www.scala-sbt.org/documentation.html)
    * [Installing](http://www.scala-sbt.org/1.3.3/docs/Setup.html)
    * [Getting started guide](http://www.scala-sbt.org/1.3.3/docs/Getting-Started.html)

Please follow the relevant instructions for installing `sbt` on your OS.

If at all possible, please install `sbt` on your system in advance of the start of the course, and test that it works by typing `sbt run` from the `sbt-test` directory of this code repository. You will need an Internet connection the first time that you run this, and it will take some time to run while it downloads Scala, the Scala compiler, the Scala standard library and all of the libraries that we will be using in the course. If the test runs correctly, it should finish by printing the message "SBT IS INSTALLED AND WORKING" to the console. Once these libraries are downloaded and cached on your system, subsequent builds should be much faster, and should not require an Internet connection.

Once you have `sbt` installed and working, see the [Readme](../sbt-test/Readme.md) in the `sbt-test` directory for further information.




