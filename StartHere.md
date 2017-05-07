# Start Here

## Main jump-off page for the Scala for Statistical Computing and Data Science Short Course

Course participants should bookmark this page: https://github.com/darrenjw/scala-course/blob/master/StartHere.md

* [Course outline](README.md) - front page of the repo, with brief summary overview
* [Setup instructions](Setup.md) - details of how to set up your laptop for programming in Scala. Please follow these instructions carefully *in advance of the start of the course*.

The information below is aimed primarily at course participants. In particular, it is assumed that people accessing this material are in possession of the **course notes**. The course notes (which are not yet freely available) will be given to participants at the start of the course. Others are welcome to browse the material in this repo, but should be aware that much of it will be difficult to decipher without the course notes.

* [Useful links](UsefulLinks.md) - selective and curated collection of some important additional on-line resources
* [app-template](app-template/) - Scala sbt "seed" project, for copying and editing to create a new Scala sbt project. Minimal dependencies in the sbt build file (just Breeze). Alternatively, use `sbt new` as described in the notes.
* [sbt-test](sbt-test/) - simple Scala sbt project with lots of dependencies. See the [build.sbt](sbt-test/build.sbt) for list of dependencies. Primarily for testing correct installation of sbt and caching of commonly required dependencies. Also useful for spinning up a REPL (`sbt console`) with lots of dependencies for interactive experiments. The [src/test](sbt-test/src/test/scala/) subdirectory tree contains some basic examples of how to write test code.

* [Fragments](fragments/Readme.md) - raw fragments of code from the course notes, auto-extracted by chapter
* [Examples](examples/) - complete runnable code examples, split corresponding to each chapter of the course notes
* [Exercises](exercises/Readme.md) - simple programming exercises, to be tackled following the presentation of each chapter of the notes.



#### eof


