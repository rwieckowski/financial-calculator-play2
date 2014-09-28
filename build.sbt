name := """financial-calculator-play2"""

version := "1.0-SNAPSHOT"

lazy val core = RootProject(uri("git://github.com/rwieckowski/financial-calculator.git"))

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .dependsOn(core)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws
)
