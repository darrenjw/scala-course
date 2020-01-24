name := "app-template"

version := "0.1"

scalacOptions ++= Seq(
  "-unchecked", "-deprecation", "-feature"
)

libraryDependencies  ++= Seq(
 "org.scalacheck" %% "scalacheck" % "1.13.4" % "test",
 "org.scalatest" %% "scalatest" % "3.0.1" % "test",
 "org.scalanlp" %% "breeze" % "1.0",
 "org.scalanlp" %% "breeze-natives" % "1.0"
)

scalaVersion := "2.12.10"


