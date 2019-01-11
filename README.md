## neme - not exhaustive match error

Scala compiler plugin for turning non exhaustive match warnings into errors

This plugin is heavily inspired by [silencer-plugin](https://github.com/ghik/silencer)

### Usage

If you're using SBT, simply add these lines to your `build.sbt` to enable the plugin:

```scala
libraryDependencies ++= Seq(
  compilerPlugin("com.softwaremill.neme" %% "neme-plugin" % "0.0.1")
)
```
