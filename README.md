# Scala for Statistical Computing and Data Science Short Course

## 16th-18th May 2017, [Senate House](https://goo.gl/maps/YVaUVoVw8C82), London

**NOTE: I am running this course in association with [Jumping Rivers Ltd.](http://www.jumpingrivers.com/). See their website for futher details on [course dates](http://www.jumpingrivers.com/#dates), [cost](http://www.jumpingrivers.com/#cost), [registration](http://www.jumpingrivers.com/#registration) and [course descriptions](http://www.jumpingrivers.com/#description). The description for this course is repeated below.**

This GitHub repository will be used to share public materials relating to the course. Course participants who are familiar with Git should clone this repo and pull changes in advance of the course. In particular, instructions for downloading and installing the necessary software and setting up your laptop ready to start the course will be shared here at least two weeks before the first day of the course.

## Outline course description

This course is aimed at statisticians and data scientists already familiar with a dynamic programming language (such as R, Python or Octave) who would like to learn how to use [Scala](http://www.scala-lang.org/). Scala is a free modern, powerful, strongly-typed, functional programming language, well-suited to statistical computing and data science applications. In particular, it is fast and efficient, runs on the Java virtual machine (JVM), and is designed to easily exploit modern multi-core and distributed computing architectures.

The course will begin with an introduction to the Scala language and basic concepts of [functional programming](https://en.wikipedia.org/wiki/Functional_programming) (FP), as well as essential Scala tools such as [SBT](http://www.scala-sbt.org/) for managing builds and library dependencies. The course will continue with an overview of the [Scala collections library](http://docs.scala-lang.org/overviews/collections/overview.html), including [parallel collections](http://docs.scala-lang.org/overviews/parallel-collections/overview.html), and we will see how parallel collections enable trivial parallelisation of many statistical computing algorithms on multi-core hardware. We will next survey the wider Scala library ecosystem, paying particular attention to [Breeze](https://github.com/scalanlp/breeze), the Scala library for scientific computing and numerical linear algebra. We will see how to exploit non-uniform random number generation and matrix computations in Breeze for statistical applications. Both maximum-likelihood and simulation-based Bayesian statistical inference algorithms will be considered. Much of the final day will be dedicated to understanding [Apache Spark](http://spark.apache.org/), the distributed Big Data analytics platform for Scala. We will understand how Spark relates to the parallel collections we have already examined, and see how it can be used not only for the processing of very large data sets, but also for the parallel and distributed analysis of large or otherwise computationally-intensive models. As time permits, we will discuss more [advanced FP concepts](https://github.com/typelevel/cats/blob/master/README.md), such as typeclasses, higher-kinded types, monoids, functors, monads, applicatives, streams and streaming data, and see how these enable the development of flexible, scalable, generic code in strongly-typed functional languages.

#### Prerequisite

The course assumes a basic familiarity with essential concepts in statistical computing, as well as some basic programming experience. It is assumed that participants will be familiar with writing their own functions in a language such as R, including essential control structures such as "for-loops" and "if-statements". The course is not suitable for people completely new to programming. However, no prior knowledge of Scala or functional programming is assumed. All participants will be expected to bring their own (multi-core) laptop and to have a recent version of Java pre-installed. Other set-up instructions will be provided in advance to registered participants.

#### Course structure

The course will be delivered through a combination of lectures, live demos and hands-on practical sessions. For the practical sessions, participants will be expected to actively engage with the material, run demos, follow examples, and write code to solve simple problems.

#### Presenters

The course will be delivered by [Prof Darren Wilkinson](https://www.staff.ncl.ac.uk/d.j.wilkinson/) (Newcastle University, U.K.). Prof Wilkinson is co-Director of Newcastle's [EPSRC Centre for Doctoral Training in Cloud Computing for Big Data](http://www.bigdata-cdt.ac.uk/). He is a well-known expert in computational Bayesian statistics and a leading proponent of the use of strongly-typed FP languages (such as Scala) for scalable statistical computing.


