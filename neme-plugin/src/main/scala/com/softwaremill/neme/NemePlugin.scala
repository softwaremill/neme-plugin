package com.softwaremill.neme

import scala.tools.nsc.plugins.{Plugin, PluginComponent}
import scala.tools.nsc.{Global, Phase}

class NemePlugin(val global: Global) extends Plugin {
  plugin =>

  import global._

  val name = "neme"
  val description = "Scala compiler plugin for warning suppression"
  val components: List[PluginComponent] = List(component)

  private lazy val reporter =
    new NemeReporter(global.reporter)

  override def processOptions(options: List[String], error: String => Unit): Unit = {
    global.reporter = reporter
  }

  private object component extends PluginComponent {
    val global: plugin.global.type = plugin.global
    val runsAfter = List("typer")
    override val runsBefore = List("patmat")
    val phaseName = "neme"

    def newPhase(prev: Phase): StdPhase = new StdPhase(prev) {
      def apply(unit: CompilationUnit): Unit = applySuppressions(unit)
    }

    def applySuppressions(unit: CompilationUnit): Unit = {
      plugin.reporter.exec()
    }
  }

}
