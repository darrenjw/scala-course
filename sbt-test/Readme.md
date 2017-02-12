# SBT test

This directory contains a Scala SBT project with numerous dependencies. Assuming that `sbt` is installed, you should be able to compile and run the project by typing `sbt run` from this directory. After downloading and caching any required libraries, it will compile the code (in `src/main`) and run it. If the program runs successfully, it will print the message "SBT IS INSTALLED AND WORKING" to the console.

For reference, the file [build.sbt](build.sbt) shows how to include a dependency on many of the libraries most commonly required for statistical computing applications.

For good measure, you might also want to run `sbt test` from this directory. This should compile and run a few tests (in `src/test`).



