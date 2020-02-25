name := "sbt-test"

version := "0.1"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature","-language:implicitConversions")

libraryDependencies  ++= Seq(
            "org.scalatest" %% "scalatest" % "3.1.1" % "test",
	    "org.scalacheck" %% "scalacheck" % "1.14.1" % "test",
            "org.scalanlp" %% "breeze" % "1.0",
            "org.scalanlp" %% "breeze-natives" % "1.0",
            "org.scalanlp" %% "breeze-viz" % "1.0",
            "org.typelevel" %% "cats-core" % "1.0.0"
)

resolvers ++= Seq(
            "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
            "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
)


addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)


scalaVersion := "2.12.10"

