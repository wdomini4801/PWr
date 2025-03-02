import java.util.concurrent.Semaphore
import scala.util.Random

object ProblemOfPhilosophers:
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
