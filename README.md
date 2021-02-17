## neme - not exhaustive match error
[![Build Status](https://travis-ci.org/softwaremill/neme-plugin.svg?branch=master)](https://travis-ci.org/softwaremill/neme-plugin)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.softwaremill.neme/neme-plugin_2.13/badge.svg)](https://search.maven.org/search?q=g:com.softwaremill.neme)

Scala compiler plugin for turning non exhaustive match warnings into errors

This plugin is heavily inspired by [silencer-plugin](https://github.com/ghik/silencer)

Published for Scala 2.11, 2.12 and 2.13.

### Deprecation notice

Since scala 2.13.2 there is a new flag - `-Wconf` which covers that functionality making this project obsolete.
Later, it was also backported into 2.12.13 (https://github.com/scala/scala/releases/tag/v2.12.13).

See https://github.com/scala/scala/pull/8373. The specific setting replicating the functionality of the plugin is:

```
scalacOptions += "-Wconf:cat=other-match-analysis:error"
```

### Usage

If you're using SBT, simply add these lines to your `build.sbt` to enable the plugin:

```scala
libraryDependencies ++= Seq(
  compilerPlugin("com.softwaremill.neme" %% "neme-plugin" % "0.0.5")
)
```

Or in `~/.sbt/1.0/*.sbt`:

```scala
addCompilerPlugin("com.softwaremill.neme" %% "neme-plugin" % "0.0.5")
```
