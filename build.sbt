name := "neme"

val commonSettings = Seq(
  organization := "com.softwaremill",
  version := "1.3.5",
  scalaVersion := "2.12.7",
  crossScalaVersions := Seq("2.11.12", scalaVersion.value, "2.13.0-M5"),
)

val subprojectSettings = commonSettings ++ Seq(
  publishMavenStyle := true,
  publishTo := Some(
    if (isSnapshot.value)
      Opts.resolver.sonatypeSnapshots
    else
      Opts.resolver.sonatypeStaging
  ),
  pomIncludeRepository := { _ => false },
)

lazy val neme = (project in file(".")).aggregate(`neme-plugin`)
  .settings(commonSettings: _*)
  .settings(
    publishArtifact := false,
    PgpKeys.publishSigned := {}
  )

lazy val `neme-plugin` = project
  .settings(subprojectSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-compiler" % scalaVersion.value,
      "org.scalatest" %% "scalatest" % "3.0.6-SNAP3" % Test
    ),
    fork in Test := true,
    baseDirectory in Test := (baseDirectory in ThisBuild).value,
  )