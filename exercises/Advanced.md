# Advanced topics

## Practical exercises

Start off with exercise 1 (Cats), then pick and choose according to your interests.

### 1. Playing with Cats

* [Cats](http://typelevel.org/cats/) is one of many useful libraries that we haven't had time to explore properly in this short course. [Scala exercises](https://www.scala-exercises.org/) has some [Cats exercises](https://www.scala-exercises.org/cats) which are worth working through to learn a little about how it works.

### 2. Simulacrum for typeclass programming

* [Simulacrum](https://github.com/typelevel/simulacrum) is another useful library for FP in Scala. Read about how it works and then re-do the `CsvRow` and `Thinnable` typeclass examples from the notes using Simulacrum. Note how much cleaner they are. Note that Cats has a dependence on Simulacrum, so if you have a project or REPL with a Cats dependency you *may* not need to add an additional dependence on Simulacrum. However, you *do* need to enable the "macro paradise" compiler plugin, by adding the line
```scala
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
```
to your `build.sbt` file. The `sbt-test` example project is set up to allow experimenting with both Simulacrum and Cats from the REPL.

### 3. Monocle

[Monocle](https://julien-truffaut.github.io/Monocle/) is an *optics* library for Scala, intended to make it easier to work with immutable data structures based on (nested) algebraic data types (ADTs).

* Work through the Getting started guide to get a bit of a feel for the problem that the library solves. If it seems interesting, continue to work through the rest of the documentation.

### 4. Monix

[Monix](https://monix.io/) is a library for asyncronous and concurrent programming in Scala, including stream-based functional reactive programming (FRP), using a datatype known as `Observable`. It is one of many options for working with (real time) data streams in Scala. It also contains `Task`, which is a much better version of Scala's `Future` monad.

* Start working through the documentation for [Observable](https://monix.io/docs/3x/reactive/observable.html), and then investigate further if the library seems interesting.

### 5. Frameless

[Frameless](https://typelevel.org/frameless/) is a library which provides a safter, more idiomatic, Scala interface to Spark. If you intend to work a lot with Spark, it is worth trying to understand this library and the potential benefits it can bring.
* Start by reading through the Introduction, then continue with the library documentation, learning first about `TypedDataset`.

### 6. Probabilistic programming with Figaro

* [Figaro](https://github.com/p2t2/figaro) is a library for probabilistic programming in Scala. Use the remaining time to read through the [Quick start guide](https://github.com/p2t2/figaro/raw/master/doc/Figaro%20Quick%20Start%20Guide.pdf) and then skim the [Tutorial](https://www.cra.com/sites/default/files/pdf/Figaro_Tutorial.pdf). Try to build and run the example from the quick start guide, noting that the examples can be found [here](https://github.com/p2t2/figaro/tree/master/FigaroExamples/src/main/scala/com/cra/figaro/example).
* Note that from a clean SBT session (say, run from an empty/temp directory), a REPL with a Figaro dependency can be started with:
```scala
set libraryDependencies += "com.cra.figaro" %% "figaro" % "5.0.0.0"
set scalaVersion := "2.12.10"
console
```

### 7. Scala.js

[Scala.js](https://www.scala-js.org/) is a framework for compiling Scala code to Javascript for client-side execution in web applications. If you do any front-end work, Scala.ja is one of the nicest ways to develop web applications. Many of the libraries we have considered, including EvilPlot, Cats, Simulacrum, Monocle, and Monix, are available for Scala.js as well as the usual JVM version of Scala. This makes it very easy to develop web applications which share code between the front and the back-end. Some of the web sites we have used in this course, such as [scala-fiddle](https://scalafiddle.io/) and [scala-exercises](https://www.scala-exercises.org/) are powered by Scala.js. It is particularly useful for developing web-based interactive dashboards for data science applications.
* Try one of the [tutorials](https://www.scala-js.org/doc/tutorial/) to get started

### 8. Scala-native

[Scala-native](https://github.com/scala-native/scala-native) is a framework for compiling Scala to native code, rather than JVM bytecode. This is useful for systems programming, for interfacing with C libraries, and for developing lightweight command-line tools. Although many people imagine that Scala code compiled to native code will execute much faster than JVM bytecode, this is typically not the case, and is certainly not the main intended use of Scala-native. See the [documentation](https://www.scala-native.org/) for further details.


#### eof

