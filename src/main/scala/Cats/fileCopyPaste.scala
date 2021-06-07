package Cats

import cats.effect.{ExitCode, IO, IOApp, Resource}

import java.io._

object fileCopyPaste extends IOApp{

  def inputStream(f: File): Resource[IO, FileInputStream] =
    Resource.make {
      IO.blocking(new FileInputStream(f))
    } { inStream =>
      IO.blocking(inStream.close()).handleErrorWith(_ => IO.unit)
    }

  def outputStream(f: File): Resource[IO, FileOutputStream] =
    Resource.make {
      IO.blocking(new FileOutputStream(f))
    } { outStream =>
      IO.blocking(outStream.close()).handleErrorWith(_ => IO.unit)
    }

  def inputOutputStream(in: File, out: File): Resource[IO, (InputStream, OutputStream)] =
      for {
        inStream <- inputStream(in)
        outStream <- outputStream(out)
    } yield (inStream, outStream)

  def transmit(origin: InputStream, destination: OutputStream, buffer: Array[Byte], acc: Long): IO[Long] =
    for {
      amount <- IO.blocking(origin.read(buffer, 0, buffer.length))
      count <- if (amount > -1) IO.blocking(destination.write(buffer, 0, amount)) >> transmit(origin, destination, buffer, acc + amount)
      else IO.pure(acc)
    } yield count

  def transfer(origin: InputStream, destination: OutputStream): IO[Long] =
    transmit(origin, destination, new Array[Byte](1024 * 10), 0L)

  def copy(origin: File, destination: File) : IO[Long] =
    inputOutputStream(origin, destination).use { case (in, out) =>
      transfer(in, out)
    }

  override def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- if (args.length < 2) IO.raiseError(new IllegalArgumentException("Need origin and Destination"))
      else IO.unit
      orig = new File(args.head)
      dest = new File(args(1))
      count <- copy(orig, dest)
      _ <- IO.println(s"$count bytes copied from ${orig.getPath} to ${dest.getPath}")
    } yield ExitCode.Success
}
