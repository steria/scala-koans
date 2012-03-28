package org.scalakoans

import support.KoanSuite
import scala.util.matching._

class AboutRegularExpressions extends KoanSuite {

  koan ("regular expressions match substrings") {
    val expression:Regex = """wor""".r
    expression.findFirstIn("hello world").orNull should be (__)
  }

  koan ("regular expressions will match nothing if no match") {
    val expression = """wor""".r
    expression.findFirstIn("hello there").orNull should be (__)
  }

  koan ("regular expressions can be used in matchers") {
    def tryToMatch(str:String) = {
      val expression1 = "hello there".r
      val expression2 = "hello world".r
      
      str match {
        case expression1() => "there"
        case expression2() => "world"
        case _ =>             "no match"
      }
    }
    tryToMatch("hello world") should be(__)
    tryToMatch("hello " + __) should be("there")
    tryToMatch("hello " + __) should be("no match")
  }

  koan ("expressions can extract values") {
    val SumExpression = """the sum of (.) and (.)""".r
    val SumExpression(a, b) = "the sum of 1 and 2"
    a should be(__)
    b should be(__)
  }

  koan ("match zero or more characters") {
    val expression = """h(a*)llo there""".r
    "haaaaaaallo there" match {
      case expression(s) => s should be(__)
      case _ => fail()
    }
  }
  
  koan ("match zero characters") {
    val expression = """h(a*)llo""".r
    "hllo" match {
      case expression(s) => s should be("")
      case _ => fail()
    }
    meditate
    // what happens if you change val expression = """h(a+)llo""".r
  }
  
  koan ("doesn't match partial string") {
    val nonPartialExpression = """hello""".r
    val frontPartialExpression = """.*hello""".r
    val endPartialExpression = """hello.*""".r
    val fullPartialExpression = """.*hello.*""".r
    var result:String = "no match"
    "oh, hello there" match {
      case nonPartialExpression() => result = "non partial match"
      case frontPartialExpression() => result = "front partial match"
      case endPartialExpression() => result = "end partial match"
      case fullPartialExpression() => result = "full partial match"
    }
    result should be(__)
  }
  
  koan ("numeric match") {
    val numericMatch = """the answer is (\d+)""".r
    "the answer is 123" match {
       case numericMatch(number) => number should be(__)
       case _ => fail("should match")
    }
  }
  
  koan ("partial binding") {
    val numericMatch = """the (.*) is (\d+)""".r
    "the answer is 123" match {
       case numericMatch("answer", number) =>
          number should be(__)
       case numericMatch("question", number) =>
          fail
       case _ => fail("should match")
    }
  }
  
}
