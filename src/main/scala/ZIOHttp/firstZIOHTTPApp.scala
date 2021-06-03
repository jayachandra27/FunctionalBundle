package ZIOHttp

import zio._
import zhttp.http._
import zhttp.service.Server

object firstZIOHTTPApp extends App {

  val app = Http.collect[Request] {
    case Method.GET -> Root / "text" => Response.text("First-Page")
  }

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    Server.start(8092, app).exitCode
}
