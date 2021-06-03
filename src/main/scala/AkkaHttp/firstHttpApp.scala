package AkkaHttp

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model._

import scala.io.StdIn

object firstHttpApp extends App {

  implicit val system = ActorSystem(Behaviors.empty, "First-HTTP-Test")
  implicit val executionContext = system.executionContext

  val route = path("page1") {
    get {
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<p>Test Page 1</p>"))
    }
  }

  val bindingFuture = Http().newServerAt("localhost",8081).bind(route)

  println("new Server at https://localhost:8081/\nPress Enter to Quit")
  StdIn.readLine()

  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}
