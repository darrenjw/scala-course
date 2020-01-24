# SBT test

This directory contains a Scala SBT project with numerous dependencies. Assuming that `sbt` is installed, you should be able to compile and run the project by typing `sbt run` from this directory. After downloading and caching any required libraries, it will compile the code (in `src/main`) and run it. If the program runs successfully, it will print the message "SBT IS INSTALLED AND WORKING" to the console.

For reference, the file [build.sbt](build.sbt) shows how to include a dependency on many of the libraries most commonly required for statistical computing applications.

For good measure, you might also want to run `sbt test` from this directory. This should compile and run a few tests (in `src/test`). Note that there are some simple example tests using both ScalaTest (in different styles) and ScalaCheck (for property-based testing) in this directory, so these provide useful tempates for test code.

This directory is also useful for starting a REPL including commonly used dependencies. Just running `sbt console` from this directory will give a Scala console including dependencies on libraries such as Breeze, Breeze-viz and Cats, which can be very useful for interactive experiments.

For further information about sbt, read through the [sbt getting started guide](http://www.scala-sbt.org/1.3.3/docs/Getting-Started.html).

#### eof


