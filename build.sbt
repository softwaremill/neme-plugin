import com.softwaremill.PublishTravis.publishTravisSettings

val v2_11 = "2.11.12"
val v2_12 = "2.12.9"
val v2_13 = "2.13.1"

val commonSettings = commonSmlBuildSettings ++ ossPublishSettings ++ Seq(
  organization := "com.softwaremill.neme",
  scalafmtOnCompile := true,
  scalaVersion := v2_12,
  crossScalaVersions := Seq(v2_13, v2_12, v2_11)
)

lazy val neme = (project in file("."))
  .settings(commonSettings: _*)
  .settings(publishArtifact := false, name := "neme")
  .settings(publishTravisSettings)
  .aggregate(`neme-plugin`)

lazy val `neme-plugin` = project
  .settings(commonSettings: _*)
  .settings(
    name := "neme-plugin",
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-compiler" % scalaVersion.value,
      "org.scalatest" %% "scalatest" % "3.0.9" % Test
    ),
    fork in Test := true,
    baseDirectory in Test := (baseDirectory in ThisBuild).value,
    unmanagedSourceDirectories in Compile += {
      // sourceDirectory returns a platform-scoped directory, e.g. /.jvm
      val sourceDir = (baseDirectory in Compile).value / "src" / "main"
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, n)) if n >= 13 => sourceDir / "scala-2.13+"
        case _                       => sourceDir / "scala-2.13-"
      }
    }
  )
