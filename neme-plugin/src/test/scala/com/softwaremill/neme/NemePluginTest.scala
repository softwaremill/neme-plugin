package com.softwaremill.neme

import org.scalatest.FunSuite

import scala.io.Source
import scala.reflect.io.VirtualDirectory
import scala.tools.nsc.plugins.Plugin
import scala.tools.nsc.reporters.ConsoleReporter
import scala.tools.nsc.{Global, Settings}

class NemePluginTest extends FunSuite {
  suite =>

  val testdata = "neme-plugin/testdata/"
  val settings = new Settings

  Option(getClass.getResourceAsStream("/embeddedcp")) match {
    case Some(is) =>
      Source.fromInputStream(is).getLines().foreach(settings.classpath.append)
    case None =>
      settings.usejavacp.value = true
  }

  // avoid saving classfiles to disk
  val outDir = new VirtualDirectory("(memory)", None)
  settings.outputDirs.setSingleOutput(outDir)
  val reporter = new ConsoleReporter(settings)

  val global: Global = new Global(settings, reporter) {
    override protected def loadRoughPluginsList(): List[Plugin] =
      new NemePlugin(this) :: super.loadRoughPluginsList()
  }

  def compile(filenames: String*): Unit = {
    val run = new global.Run
    run.compile(filenames.toList.map(testdata + _))
  }

  def testFile(filename: String, expectedErrors: Int): Unit = {
    compile(filename)
    assert(reporter.errorCount == expectedErrors)
  }

  test("not exhaustive matcher") {
    testFile("notExhaustiveMatcher.scala", 1)
  }
}
