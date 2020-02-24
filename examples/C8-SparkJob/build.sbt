name := "spark-template"

version := "0.1"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

libraryDependencies  ++= Seq(
            "org.scalatest" %% "scalatest" % "3.0.8" % "test",
            "org.apache.spark" %% "spark-core" % "2.4.5" % Provided,
            "org.apache.spark" %% "spark-sql" % "2.4.5" % Provided,
            "org.apache.spark" %% "spark-mllib" % "2.4.5" % Provided
)

resolvers ++= Seq(
            "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
            "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
)

scalaVersion := "2.11.12"

