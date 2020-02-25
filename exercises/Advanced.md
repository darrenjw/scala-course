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

### 4. Monix

### 5. Frameless

### 6. Probabilistic programming with Figaro

* [Figaro](https://github.com/p2t2/figaro) is a library for probabilistic programming in Scala. Use the remaining time to read through the [Quick start guide](https://github.com/p2t2/figaro/raw/master/doc/Figaro%20Quick%20Start%20Guide.pdf) and then skim the [Tutorial](https://www.cra.com/sites/default/files/pdf/Figaro_Tutorial.pdf). Try to build and run the example from the quick start guide, noting that the examples can be found [here](https://github.com/p2t2/figaro/tree/master/FigaroExamples/src/main/scala/com/cra/figaro/example).
* Note that from a clean SBT session (say, run from an empty/temp directory), a REPL with a Figaro dependency can be started with:
```scala
set libraryDependencies += "com.cra.figaro" %% "figaro" % "5.0.0.0"
set scalaVersion := "2.12.10"
console
```

### 7. Scala.js


### 8. Scala-native



#### eof

