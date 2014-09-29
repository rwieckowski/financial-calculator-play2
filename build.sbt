name := """financial-calculator-play2"""

version := "1.0-SNAPSHOT"

lazy val core = RootProject(uri("git://github.com/rwieckowski/financial-calculator.git"))

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .dependsOn(core)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.3.0-1",
  "org.webjars" % "bootstrap" % "3.2.0",
  jdbc,
  anorm,
  cache,
  ws
)