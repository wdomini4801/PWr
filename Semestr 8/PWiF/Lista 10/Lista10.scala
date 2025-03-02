// Wojciech Dominiak
import java.util.concurrent.{ArrayBlockingQueue, Semaphore}
import scala.concurrent.ExecutionContext
import scala.util.Random

def log(msg: String): Unit = println(s"${Thread.currentThread.getName}: $msg")

// zadanie 1a
object Zad1a:
  class Producer(name: String, buf: ArrayBlockingQueue[Int]) extends Thread(name):
    override def run: Unit =
      for i <- 1 to 10 do
        println(s"$getName producing $i"); buf.put(i)

  class Consumer(name: String, buf: ArrayBlockingQueue[Int]) extends Thread(name):
    override def run: Unit =
      for i <- 1 to 10 do println(s"$getName consumed ${buf.take}")

  def main(args: Array[String]): Unit =
    val buf: ArrayBlockingQueue[Int] = new ArrayBlockingQueue[Int](5)
    new Producer("Producer", buf).start
    new Consumer("Consumer", buf).start

// zadanie 1b
object Zad1b:
  class Producer(name: String, buf: ArrayBlockingQueue[Int]) extends Thread(name):
    override def run: Unit =
      for i <- 1 to 10 do
        log(s"producing $i"); buf.put(i)

  class Consumer(name: String, buf: ArrayBlockingQueue[Int]) extends Thread(name):
    override def run: Unit =
      for _ <- 1 to 10 do log(s"consumed ${buf.take}")

  def main(args: Array[String]): Unit =
    val buf: ArrayBlockingQueue[Int] = new ArrayBlockingQueue[Int](5)

    val AMOUNT_OF_PRODUCERS = 2
    val AMOUNT_OF_CONSUMERS = 3

    for i <- 1 to AMOUNT_OF_PRODUCERS do new Producer(s"Producer $i", buf).start
    for i <- 1 to AMOUNT_OF_CONSUMERS do new Consumer(s"Consumer $i", buf).start

  // Program się nie kończy, ponieważ producenty wyprodukują 20 "produktów" (każdy po 10), zaś konsumenty będą chciały
  // skonsumować 30 "produktów" (w każdym pętla for od 1 do 10), co spowoduje, że konsumenty po opróżnieniu kolejki
  // z "produktami" będą czekały na kolejne "produkty", bo każdy z nich chce skonsumować po 10. Taka sytuacja nigdy nie nastąpi
  // - każdy producent wyprodukował już swoją ilość produktów (10), więc konsumenty będą czekały w nieskończoność
  // - program się nie zakończy.

// zadanie 1c
object Zad1c:
  def main(args: Array[String]): Unit =
    val buf: ArrayBlockingQueue[Int] = new ArrayBlockingQueue[Int](5)

    val AMOUNT_OF_PRODUCERS = 2
    val AMOUNT_OF_CONSUMERS = 3

    for i <- 1 to AMOUNT_OF_PRODUCERS do ExecutionContext.global.execute(
      () => for j <- 1 to 10 do
        log(s"Producer $i producing $j"); buf.put(j)
      )
    for i <- 1 to AMOUNT_OF_CONSUMERS do ExecutionContext.global.execute(
      () => for _ <- 1 to 10 do log(s"Consumer $i consumed ${buf.take}"))

    Thread.sleep(3000)

  // Program się kończy, ponieważ egzekutor, wykorzystany w zadaniu, tworzy wątek demona oraz wątki robocze. Zakończenie
  // wątku demona kończy również działanie wszystkich wątków roboczych. W zadaniu wątek demona kończy się, gdy kończy się
  // działanie metody main(), zatem po zakończeniu metody zakończy się działanie wszystkich innych wątków, czyli całego
  // programu. Nie zdąży się nawet wydrukować stan poszczególnych producentów i konsumentów. Aby temu zapobiec, można wykorzystać
  // metodę sleep(), która opóźni zakończenie aplikacji, tak żeby zdążył się wydrukować stan producentów i konsumentów.

// zadanie 2
object Zad2:
  class Stick:
    private val semaphore = new Semaphore(1)
    def take: Unit = semaphore.acquire
    def place: Unit = semaphore.release

  class Philosopher(name: String, leftStick: Stick, rightStick: Stick, doorman: Semaphore, meditatingTime: Int, eatingTime: Int) extends Thread(name):
    private val random = new Random

    private def eat: Unit =
      doorman.acquire
      leftStick.take
      rightStick.take
      log("started eating")
      val startTime = System.nanoTime
      Thread.sleep(random.nextInt(eatingTime))
      leftStick.place
      rightStick.place
      val time = (System.nanoTime - startTime) / 1000000
      log(s"finished eating, time: ${time} ms")
      doorman.release

    private def meditate: Unit =
      log("started meditating")
      val startTime = System.nanoTime
      Thread.sleep(random.nextInt(meditatingTime))
      val time = (System.nanoTime - startTime) / 1000000
      log(s"finished meditating, time: ${time} ms")

    override def run(): Unit =
      while true do
        meditate
        eat

  class Feast(amount_of_philosophers: Int, longest_meditating_time: Int, longest_eating_time: Int):
    private val sticks = new Array[Stick](amount_of_philosophers)
    private val philosophers = new Array[Philosopher](amount_of_philosophers)
    private val doorman = new Semaphore(amount_of_philosophers - 1, true)

    def start: Unit =
      for i <- 0 until amount_of_philosophers do
        sticks(i) = new Stick()

      for i <- 0 until amount_of_philosophers do
        philosophers(i) = new Philosopher(
          s"Philosopher ${i + 1}", sticks(i), sticks((i + 1) % amount_of_philosophers), doorman, longest_meditating_time, longest_eating_time)
        philosophers(i).start

  def main(args: Array[String]): Unit =
    val feast = Feast(5, 20000, 15000)
    feast.start
