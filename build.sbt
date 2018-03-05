name := "Assignment-Spark-4"

version := "0.1"

scalaVersion := "2.11.11"

libraryDependencies += "org.twitter4j" % "twitter4j-stream" % "4.0.4"

libraryDependencies += "log4j" % "log4j" % "1.2.17"

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.2.0"

libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.2.1" % "provided"

libraryDependencies += "org.apache.spark" %% "spark-streaming-twitter" % "1.6.3"
