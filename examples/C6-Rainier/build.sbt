// build.sbt

name := "rainier"

version := "0.1-SNAPSHOT"

scalacOptions ++= Seq(
  "-unchecked", "-deprecation", "-feature", "-language:higherKinds",
  "-language:implicitConversions", "-Ypartial-unification"
)

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full)
addCompilerPlugin("org.scalamacros" %% "paradise" % "2.1.1" cross CrossVersion.full)

libraryDependencies  ++= Seq(
  "org.scalatest" %% "scalatest" % "3.1.1" % "test",
  "org.scalactic" %% "scalactic" % "3.0.8",
  "org.typelevel" %% "cats-core" % "2.0.0",
  "org.typelevel" %% "simulacrum" % "1.0.0",
  "com.cibo" %% "evilplot" % "0.6.3", // 0.7.0
  "com.cibo" %% "evilplot-repl" % "0.6.3", // 0.7.0
  "com.stripe" %% "rainier-core" % "0.3.0",
  "com.stripe" %% "rainier-notebook" % "0.3.0"

)

resolvers += Resolver.bintrayRepo("cibotech", "public") // for EvilPlot

resolvers ++= Seq(
  "Sonatype Snapshots" at
    "https://oss.sonatype.org/content/repositories/snapshots/",
  "Sonatype Releases" at
    "https://oss.sonatype.org/content/repositories/releases/",
 "jitpack" at "https://jitpack.io" // for Jupiter/notebook

)

scalaVersion := "2.12.10"


// eof

