package com.softwaremill.neme

import scala.reflect.internal.util.Position
import scala.tools.nsc.reporters.Reporter

class NemeReporter(original: Reporter) extends Reporter {

  def exec(): Unit = {
    updateCounts()
  }

  override def reset(): Unit = {
    super.reset()
    original.reset()
  }

  protected def info0(pos: Position, msg: String, severity: Severity, force: Boolean): Unit = {
    severity match {
      case INFO =>
        original.info(pos, msg, force)
      case WARNING if msg.contains("match may not be exhaustive.") =>
        original.error(pos, msg)
      case WARNING =>
        original.warning(pos, msg)
      case ERROR => original.error(pos, msg)
    }
    updateCounts()
  }

  private def updateCounts(): Unit = {
    INFO.count = original.INFO.count
    WARNING.count = original.WARNING.count
    ERROR.count = original.ERROR.count
  }

  private def originalSeverity(severity: Severity) = severity match {
    case INFO    => original.INFO
    case WARNING => original.WARNING
    case ERROR   => original.ERROR
  }

  override def hasErrors: Boolean =
    original.hasErrors || cancelled

  override def hasWarnings: Boolean =
    original.hasWarnings

  override def resetCount(severity: Severity): Unit = {
    super.resetCount(severity)
    original.resetCount(originalSeverity(severity))
  }

  override def flush(): Unit = {
    super.flush()
    original.flush()
  }
}
