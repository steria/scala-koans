package org.scalakoans.support
import org.scalatest.events.Event
import org.scalatest.Stopper

object Master extends Stopper {
  var studentNeedsToMeditate = false

  override def apply() = studentNeedsToMeditate

  type HasTestNameAndSuiteName = {
    val suiteName: String
    val testName: String
  }

  type HasNameAndMessage = {
    val suiteName: String
    val testName: String
    val message: String
  }


  def studentFailed(event: Event): String = {
    studentNeedsToMeditate = true
    return event match {
      case e: org.scalatest.events.TestPending => meditationMessage(e)
      case e: HasNameAndMessage => meditationMessageWithMessage(e)
      case e: HasTestNameAndSuiteName => meditationMessage(e)
      case _ => ""
    }
  }

  private def meditationMessageWithMessage(event: HasNameAndMessage) = {
    "Please meditate on koan \"%s\" of suite \"%s\": %s" format
      (event.testName, event.suiteName, event.message)
  }

  private def meditationMessage(event: HasTestNameAndSuiteName) = {
    "Please meditate on koan \"%s\" of suite \"%s\"" format (event.testName, event.suiteName)
  }

}

