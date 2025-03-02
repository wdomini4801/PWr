// Wojciech Dominiak

import scala.concurrent.{Future, ExecutionContext, Await, Promise}
import scala.util.{Failure, Success}
import concurrent.duration.DurationInt
import ExecutionContext.Implicits.global
import scala.io.Source

// zadanie 1
object Zad1:
  // zadanie 1a
  def pairFutZip[A, B](fut1: Future[A], fut2: Future[B]): Future[(A, B)] = fut1.zip(fut2) // albo fut1 zip fut2

  // zadanie 1b
  def pairFut[A, B](fut1: Future[A], fut2: Future[B]): Future[(A, B)] =
    for
      x <- fut1
      y <- fut2
    yield (x, y)

  def main(args: Array[String]): Unit =
    val futureInt = Future{Thread.sleep(3000); 30+20}
    val futureString = Future{Thread.sleep(5000); "This will be " + "future!"}
    val futureBoolean = Future{Thread.sleep(5000); 5000 % 2000 == 0}
    val futureException = Future{Thread.sleep(5000); 5000 / 0}

    val result1 = pairFutZip(futureInt, futureBoolean)
    val result2 = pairFutZip(futureString, futureException)

    println(result1) // not completed
    println(Await.result(result1, 5.seconds)) // result

    println(result2) // future with failure

    val result3 = pairFut(futureInt, futureBoolean)
    val result4 = pairFut(futureString, futureException)

    println(result3) // not completed
    println(Await.result(result3, 5.seconds)) // result

    println(result4) // future with failure

// zadanie 2
object Zad2:
  extension[T](self: Future[T])
    // zadanie 2a
    def existsProm(p: T => Boolean): Future[Boolean] =
      val promise = Promise[Boolean]()
      self onComplete {
        case Success(value) => promise.success(p(value))
        case Failure(_) => promise.success(false)
      }
      promise.future

    // zadanie 2b
    def exists(p: T => Boolean): Future[Boolean] =
      self.map(p).recover { case _ => false } // recover zwraca future z wynikiem, jeśli obliczenia się zakończą pomyślnie, jeśli nie, to mapuje wynik do Throwable

  def main(args: Array[String]): Unit =
    val fut1 = Future{0}
    val fut2 = Future{50}
    val fut3 = Future{-5}
    val fut4 = Future{"Hello!"}
    val fut5 = Future{10 / 0}

    println(Await.result(fut1.existsProm(x => x > 0), 1.seconds)) // false
    println(Await.result(fut2.existsProm(x => x % 5 == 0), 1.seconds))
    println(Await.result(fut3.existsProm(x => x < 0), 1.seconds))
    println(Await.result(fut4.existsProm(x => x.endsWith("!")), 1.seconds))
    println(Await.result(fut5.existsProm(x => x > 1), 1.seconds)) // false

    println()

    println(Await.result(fut1.exists(x => x > 0), 1.seconds)) // false
    println(Await.result(fut2.exists(x => x % 5 == 0), 1.seconds))
    println(Await.result(fut3.exists(x => x < 0), 1.seconds))
    println(Await.result(fut4.exists(x => x.endsWith("!")), 1.seconds))
    println(Await.result(fut5.exists(x => x > 1), 1.seconds)) // false

// zadanie 3
object Zad3:
  def main(args: Array[String]): Unit =
    //ścieżka do folderu, pobierana z wiersza poleceń, np. "C:/lista11/pliki/" lub "C:\\lista11\\pliki\\"
    val path = args(0)
    val promiseOfFinalResult = Promise[Seq[(String, Int)]]() //Promesa jest wyłącznie z powodów dydaktycznych

    //  Tu oblicz  promiseOfFinalResult
    promiseOfFinalResult.completeWith(scanFiles(path).flatMap(processFiles))

    promiseOfFinalResult.future onComplete {
      case Success(result) => result foreach println
      case Failure(t) => t.printStackTrace
    }
    Thread.sleep(5000)

  // Oblicza liczbę słów w każdym pliku z sekwencji wejściowej
  private def processFiles(fileNames: Seq[String]): Future[Seq[(String, Int)]] =
    Future.sequence(fileNames.map(fileName => processFile(fileName))).map(tuples => tuples.sortBy(_._2))

  // Oblicza liczbę słów w podanym pliku i zwraca parę: (nazwa pliku, liczba słów)
  private def processFile(fileName: String): Future[(String, Int)] =
    Future {
      val source = Source.fromFile(fileName)
      try
        (fileName, source.getLines.foldLeft(0)((acc, line) => acc + line.split(' ').length))
      finally source.close()
    }

  // Zwraca sekwencję nazw plików z folderu docRoot
  private def scanFiles(docRoot: String): Future[Seq[String]] =
    Future {
      new java.io.File(docRoot).list.toIndexedSeq.map(docRoot + _)
    }

