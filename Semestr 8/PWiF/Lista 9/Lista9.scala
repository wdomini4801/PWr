// Wojciech Dominiak
import java.util.concurrent.Semaphore
import scala.annotation.tailrec
import scala.math.pow

// zadanie 1a

// Zakładając, że mamy tylko 1 procesor, działać jednocześnie może tylko 1 wątek. Przykładowo, wątek p wchodzi
// do pętli, pobierze wartość zmiennej counter (załóżmy, że wynosi 100) i zanim zwiększy jej wartość, zostanie wywłaszczony
// przez wątek q (p pamięta wartość zmiennej counter=100). Wątek q pobierze wartość zmiennej counter (dalej 100) i zwiększa
// wartość zmiennej counter. Wykona się to kilka razy (załóżmy 50) - zapisuje wartość zmiennej counter na 150.
// W tym momencie wątek q zostaje znów wywłaszczony przez wątek p, który zwiększa wartość zmiennej counter, ale tej
// zaczytanej w momencie przed wywłaszczeniem, czyli 100. I zwiększa wartość zmiennej counter o 1. Mimo, że wątek q
// zwiększył wartość zmiennej counter do 150, to teraz wątek p zapisze wartość 101. Z tego wynikają różne wartości
// licznika - wątki konkurują o dostęp do wspólnego zasobu (sekcji krytycznej) i w zależności od tego, który wątek w którym
// momencie się dostanie do zasobu, taka wartość licznika jest zapisywana (niedeterminizm).

object Zad1:
  var counter = 0 // counter variable

  def readWriteCounter(): Unit =
    counter += 1 // źródło problemów (sekcja krytyczna)

  def main(args: Array[String]): Unit =
    val p = new Thread(() => for _ <- 0 until 200000 do readWriteCounter())
    val q = new Thread(() => for _ <- 0 until 200000 do readWriteCounter())
    val startTime = System.nanoTime
    p.start; q.start
    p.join; q.join
    val estimatedTime = (System.nanoTime - startTime)/1000000
    println(s"Value of the counter = $counter")
    println(s"Estimated time = ${estimatedTime}ms, Available processors = ${Runtime.getRuntime.availableProcessors}")

// zadanie 1b
object Zad1Mon:
  var counter = 0 // counter variable

  def readWriteCounter(): Unit =
    this.synchronized { // każdy obiekt z synchronizowanymi metodami jest monitorem
      counter += 1
    }

  def main(args: Array[String]): Unit =
    val p = new Thread(() => for _ <- 0 until 200000 do readWriteCounter())
    val q = new Thread(() => for _ <- 0 until 200000 do readWriteCounter())
    val startTime = System.nanoTime
    p.start; q.start
    p.join; q.join
    val estimatedTime = (System.nanoTime - startTime) / 1000000
    println(s"Value of the counter = $counter")
    println(s"Estimated time = ${estimatedTime}ms, Available processors = ${Runtime.getRuntime.availableProcessors}")

// zadanie 1c
object Zad1Sem:
  var counter = 0 // counter variable
  val sem = new Semaphore(1) // maks. 1 wątek

  def readWriteCounter(): Unit =
    sem.acquire
    counter += 1
    sem.release

  def main(args: Array[String]): Unit =
    val p = new Thread(() => for _ <- 0 until 200000 do readWriteCounter())
    val q = new Thread(() => for _ <- 0 until 200000 do readWriteCounter())
    val startTime = System.nanoTime
    p.start; q.start
    p.join; q.join
    val estimatedTime = (System.nanoTime - startTime) / 1000000
    println(s"Value of the counter = $counter")
    println(s"Estimated time = ${estimatedTime}ms, Available processors = ${Runtime.getRuntime.availableProcessors}")

// zadanie 2
object Zad2:
  def parallel[A, B](block1: => A, block2: => B): (A, B) =
    var resultA: Option[A] = None // tak albo rzutowanie na null
    var resultB: Option[B] = None

    val threadA = new Thread(() => resultA = Some(block1))
    val threadB = new Thread(() => resultB = Some(block2))
    threadA.start; threadB.start
    threadA.join; threadB.join

    (resultA.get, resultB.get)

  def main(args: Array[String]): Unit =
    assert(parallel("a" + 1, "b" + 2) == ("a1", "b2"))
    assert(parallel(1*2*3*4*5, 10*20*30*40*50) == (120, 12000000))
    assert(parallel(1234567 % 9721 == 0, "tr" + "ue") == (true, "true"))
    assert(parallel(pow(30, 2), pow(2, 30)) == (900, 1073741824))
    println(parallel(Thread.currentThread.getName, Thread.currentThread.getName))

// zadanie 3
object Zad3:
  def periodically(duration: Long, times: Int)(block: => Unit): Unit =
    @tailrec
    def execute(times: Int): Unit =
      if times > 0 then
        Thread.sleep(duration)
        block
        execute(times - 1)

    val thread = new Thread(() => execute(times))
    thread.setDaemon(true)
    thread.start

  def main(args: Array[String]): Unit =
    periodically(1000, 5) {
      print("y ")
    }
    periodically(1000, 25) {
      print("x ")
    }
    Thread.sleep(10000)
    println("Done sleeping") // po 10 s zakończy się działanie głównego wątku, aktywne pozostaną tylko wątki demony, więc program zakończy działanie
