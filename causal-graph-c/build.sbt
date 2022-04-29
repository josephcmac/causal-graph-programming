scalaVersion := "2.13.8"
name := "causal-graph-c"
organization := "Caballero Software Inc."
version := "1.0"
libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-core" % "3.2.1",
      "org.apache.spark" %% "spark-sql" % "3.2.1",
      "commons-io" % "commons-io" % "2.6",
      "org.scalatest" %% "scalatest" % "3.2.9" % Test
)