package org.scalakoans

import support.KoanSuite
import org.scalatra._
import org.scalatra.test.scalatest._
import java.net.{URL, HttpURLConnection, InetAddress, InetSocketAddress, Proxy}
import java.io.{ InputStream, OutputStream }

class AboutWeb extends ScalatraSuite with KoanSuite {

  koan("handle a basic request") {
    addServlet(new ScalatraServlet {
      get("/") {
        "Hello, world!"
      }
      get("/some_route") {
        "Another url"
      }
    }, "/*")
    get("/") {
      body should equal(__)
    }
    get("/" + __) {
      body should equal("Another url")
    }
  }

  koan("use the tester object") {
    addServlet(new ScalatraServlet {
      get("/") {
        "Hello"
      }
    }, "/*")
    // send a raw HTTP request using Jettys ServletTester (the 'tester' object)
    val response = tester.getResponses("GET / HTTP/1.0\r\n\r\n");
    response should startWith("HTTP/" + __ + " OK")
    response should endWith("" + __)
  }

  koan("handle request parameters") {
    addServlet(new ScalatraServlet {
      get("/question") {
        params.getOrElse("q", "no param")
      }
    }, "/*")

    get("/question") {
      body should equal(__)
    }

    get("/question" + __) {
      body should equal("Hello World!")
    }
  }

  koan("parse request parameters") {
    addServlet(new ScalatraServlet {
      get("/users/:username") {
        "username=" + params("username")
      }
    }, "/*")

    get("/users/test") {
      body should equal (__)
      status should equal(__)
    }
  }

  koan("set and get session variables") {
    addServlet(new ScalatraServlet {
      post("/session_variables") {
        session("name") = params("name")
        session.getOrElse("name", "error!")
      }

      get("/session_variables") {
        session.getOrElse("name", "error!")
      }
    }, "/*")

    session {
      get("/session_variables") { body should equal(__) }
      post("/session_variables", "name" -> "scala koans") {}
      get("/session_variables") { body should equal(__) }
    }
    meditate
    // what happens if you remove the session {} block?
  }

  koan("can you set and get cookies?") {
    addServlet(new ScalatraServlet with CookieSupport {
        get("/") {
          if (params.contains("client_name")) {
            cookies += ("client_name", params("client_name"))
          }
          cookies.get("client_name").getOrElse("no cookie for you")
        }
    }, "/*")
    session {
      get("/") { body should equal(__) }
      get("/", "client_name" -> "jimbo") {}
      get("/") { body should equal(__) }
    }
  }

  koan("get response headers") {
    addServlet(new ScalatraServlet {
      get("/set_response_headers") {
        response.setHeader("Hello", "World")
      }
    }, "/*")
    get("/set_response_headers") {
      header("Hello") should equal (__)
    }
  }

  koan("not-found matchers") {
    addServlet(new ScalatraServlet {
      notFound {
        requestPath match {
          case s if s.equals("/secret/foobar") => "foo-bar"
          case _ => {
            status(404)
            "page not found"
          }
        }
      }
    }, "/*")

    get("/foobar") {
      status should equal(__)
      body should equal (__)
    }

    get("/secret/foobar") {
      body should equal (__)
    }
  }

  koan("handle HTTP methods") {
    addServlet(new ScalatraServlet {
      put("/http_method") {
        response.setContentType("text/html; charset=UTF-8")
        response.getWriter.print(request.getMethod)
      }

      delete("/http_method") {
        response.getWriter.print(request.getMethod)
      }
    }, "/*")
    
    put("/http_method") {
      body should equal (__)
    }

    delete("/http_method") {
      body should equal (__)
    }
  }
}
