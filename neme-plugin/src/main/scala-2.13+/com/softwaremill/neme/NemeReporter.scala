package com.softwaremill.neme

import scala.reflect.internal.util.Position
import scala.tools.nsc.Settings
import scala.tools.nsc.reporters.FilteringReporter

class NemeReporter(original: FilteringReporter) extends FilteringReporter {

  override def doReport(pos: Position, msg: String, severity: Severity): Unit = {
    severity match {
      case INFO =>
        original.doReport(pos, msg, severity)
      case WARNING if msg.contains("match may not be exhaustive.") =>
        original.error(pos, msg)
      case WARNING =>
        original.warning(pos, msg)
      case ERROR => original.error(pos, msg)
    }
  }
  def exec(): Unit = {
    // do nothing
  }

  override def settings: Settings = original.settings
}
