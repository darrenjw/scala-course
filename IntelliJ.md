# IntelliJ installation and setup

IntelliJ now seems to be the most popular IDE for Scala.

## Installation

* To get started with IntelliJ, Scala and SBT, follow the official Scala [getting started guide](http://docs.scala-lang.org/getting-started.html)
  - [Get started with IntelliJ and Scala](https://www.scala-lang.org/documentation/getting-started-intellij-track/getting-started-with-scala-in-intellij.html)
  - [Get started with IntelliJ and Sbt](https://www.scala-lang.org/documentation/getting-started-intellij-track/building-a-scala-project-with-intellij-and-sbt.html)
  - [Using ScalaTest with IntelliJ](http://docs.scala-lang.org/getting-started-intellij-track/testing-scala-in-intellij-with-scalatest.html)

## Tips

* Always import SBT project into IntelliJ as SBT projects - IntelliJ will then examine the SBT build file to figure out all appropriate dependencies
  - do **Import Project** and select the `build.sbt` file within the project directory
  - the default import options are mostly fine, though you probably want to build with the *SBT shell*
* IntelliJ can get confused if you try and import two different SBT projects with the same name
  - So, if you copy the `app-template` directory, you should edit the project name in `build.sbt` *before* trying to import it into IntelliJ
  - **Note** that you may prefer to use `sbt new darrenjw/breeze.g8` to directly create an app template with an *appropriate name* and then import that



