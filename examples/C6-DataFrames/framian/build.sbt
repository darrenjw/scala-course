name := "framian-test"

version := "0.1"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

libraryDependencies  ++= Seq(
            "org.scalacheck" %% "scalacheck" % "1.11.4" % "test",
            "org.scalatest" %% "scalatest" % "2.1.7" % "test",
            "com.pellucid" %% "framian" % "0.3.3"
)

resolvers ++= Seq(
            "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
            "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/",
            "Pellucid Bintray" at "http://dl.bintray.com/pellucid/maven"
)

scalaVersion := "2.11.2"




