name := "neme"

val commonSettings = commonSmlBuildSettings ++ ossPublishSettings ++ Seq(
  organization := "com.softwaremill.neme",
  scalaVersion := "2.12.9",
  crossScalaVersions := Seq("2.13.0", scalaVersion.value),
)

lazy val neme = (project in file(".")).aggregate(`neme-plugin`)
  .settings(commonSettings: _*)
  .settings(
    publishArtifact := false
  )

lazy val `neme-plugin` = project
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-compiler" % scalaVersion.value,
      "org.scalatest" %% "scalatest" % "3.0.8" % Test
    ),
    fork in Test := true,
    baseDirectory in Test := (baseDirectory in ThisBuild).value,
  )