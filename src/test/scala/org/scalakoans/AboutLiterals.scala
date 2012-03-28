package org.scalakoans

import support.KoanSuite

class AboutLiterals extends KoanSuite {
  koan("integer literals") {
    val i = 1

    i.isInstanceOf[Int] should __
  }

  koan("double literals") {
    val f = 2.0

    f.isInstanceOf[Double] should be(__)
  }

  koan("string literals") {
    val s = "abcd"

    s.isInstanceOf[___] should be(true)

    s.toUpperCase() should __
    s.length() should __

    s.isInstanceOf[java.lang.String] should __

    val str = "%s - %d".format("abc", 123)
    str should __
  }

  koan("multiline strings") {
    meditate
    val str = """hello
    \ world
    """
    
    str should equal("hello\n\\ world\n")
  }
}
