// Wojciech Dominiak

class FullException(msg: String) extends Exception(msg)

abstract class MyQueue[E]:
  @throws[FullException]
  def enqueue(x: E): Unit
  def dequeue: Unit
  @throws[NoSuchElementException]
  def first: E
  def isEmpty: Boolean
  def isFull: Boolean
  override def toString: String

// zadanie 1a
import reflect.ClassTag
class QueueMut[E: ClassTag] private(val capacity: Int = 1000) extends MyQueue[E]:
  private var f, r: Int = 0
  private val array: Array[E] = new Array[E](capacity + 1)

  override def isEmpty: Boolean = f == r

  override def isFull: Boolean = (r + 1) % array.length == f

  override def enqueue(x: E): Unit =
    if isFull then throw new FullException("Full queue!")
    else
      array(r) = x
      r = (r + 1) % array.length

  override def dequeue: Unit =
    if !isEmpty then
      array(f) = null.asInstanceOf[E] // każe to wstawiać (żeby faktycznie usuwać element)
      f = (f + 1) % array.length

  override def first: E =
    if isEmpty then throw new NoSuchElementException("Empty queue!")
    else array(f)

  override def toString: String = s"${array.mkString("Array(", ", ", ")")}; f: $f; r: $r"

// zadanie 1b
object QueueMut:
  def apply[E: ClassTag](xs: E*): QueueMut[E] =
    xs.foldLeft(QueueMut.empty(xs.size))(
      (queue: QueueMut[E], x: E) => {
        queue.enqueue(x)
        queue
      }
    )
  def empty[E: ClassTag](capacity: Int = 1000): QueueMut[E] = new QueueMut[E](capacity)

object Lista8:
  def main(args: Array[String]): Unit =
    val queue1 = QueueMut(1, 2, 3, 4)
    println(queue1)
    println(queue1.isFull)
    println(queue1.first == 1)
    queue1.dequeue
    println(!queue1.isFull)
    println(queue1.first == 2)
    queue1.dequeue
    println(queue1)
    println(queue1.first == 3)
    queue1.enqueue(5)
    println(queue1.first == 3)
    println(queue1)
    queue1.enqueue(6) // sklejenie
    println(queue1)
    println(queue1.isFull)
    queue1.dequeue
    println(queue1)
    println(queue1.first == 4)

    println()

    val queue2 = QueueMut.empty[String](3)
    println(queue2)
    println(queue2.isEmpty)
    queue2.enqueue("A")
    println(queue2)
    queue2.enqueue("B")
    println(queue2)
    queue2.enqueue("C")
    println(queue2)
    println(queue2.isFull)
    queue2.dequeue
    println(queue2)
    queue2.enqueue("D")
    println(queue2)
//    queue2.enqueue("E") Full queue!

    println()

    val queue3 = QueueMut.empty[Boolean](3)
    println(queue3)
    queue3.enqueue(false)
    println(!queue3.first)
    println(queue3)