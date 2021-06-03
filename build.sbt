name := "myLearningBundle"

version := "0.1"

scalaVersion := "2.13.3"

val AkkaVersion = "2.6.14"
val AkkaHttpVersion = "10.2.4"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "io.d11" %% "zhttp" % "1.0.0.0-RC16",
  "dev.zio" %% "zio" % "1.0.8",
  "org.typelevel" %% "cats-core" % "2.6.1",
  "org.typelevel" %% "cats-effect" % "3.1.1",
  "org.typelevel" %% "discipline-scalatest" % "2.1.5" % Test,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.akka" %% "akka-slf4j" % AkkaVersion
)