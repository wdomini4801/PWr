// Wojciech Dominiak

// zadanie 1

class MyQueue[+T] private(private val out: List[T], private val reversed: List[T]):
  def this() = this(Nil, Nil)

  def isEmpty: Boolean = out == Nil

  def first: T =
    out match
      case Nil => throw new NoSuchElementException("Queue is empty")
      case h :: _ => h

  def firstOption: Option[T] =
    out match
      case Nil => None
      case h :: _ => Some(h)

  def enqueue[S >: T](elem: S): MyQueue[S] =
    if isEmpty then new MyQueue[S](List(elem), Nil)
    else new MyQueue[S](out, elem :: reversed)

  def dequeue: MyQueue[T] =
    out match
      case Nil => this
      case _ :: Nil => new MyQueue[T](reversed.reverse, Nil)
      case _ :: t => new MyQueue[T](t, reversed)

object MyQueue:
  def apply[T](xs: T*) = new MyQueue(xs.toList, Nil)
  def empty = new MyQueue(Nil, Nil)

// zadanie 2
enum BT[+A]:
  case Empty
  case Node(elem: A, left: BT[A], right: BT[A])
import BT.*

object Lista7:
  def main (args: Array[String]): Unit =
    // proste testy dla kolejki
    val q1 = new MyQueue
    println(q1.isEmpty)
    val q2 = MyQueue()
    println(q2.isEmpty)
    val q3 = MyQueue.empty
    println(q3.isEmpty)
    val q4 = MyQueue('a', 'b', 'c')
    println(!q4.isEmpty)
    println(q4.firstOption.contains('a'))
    println(q4.dequeue.first == 'b')
    println()

    val q_test = q1.enqueue(1.2).enqueue(2).enqueue(3)
    println(q_test.first == 1.2)
    println(q_test.dequeue.first == 2)
    println(q_test.dequeue.dequeue.first == 3)
    println(q_test.dequeue.dequeue.dequeue.firstOption.isEmpty)
    println()

    // testy dla drzewa
    val t = Node(1, Node(2, Empty, Node(3, Empty, Empty)), Empty)
    val tt = Node(1, Node(2, Node(4, Empty, Empty), Empty), Node(3, Node(5, Empty, Node(6, Empty, Empty)), Empty))

    println(breadthBT(Empty) == Nil)
    println(breadthBT(t) == List(1, 2, 3))
    println(breadthBT(tt) == List(1, 2, 3, 4, 5, 6))

    def breadthBT[A](tree: BT[A]) =
      def breadthBTHelper(queue: MyQueue[BT[A]]): List[A] =
        queue.firstOption match
          case None => Nil
          case Some(Empty) => breadthBTHelper(queue.dequeue)
          case Some(Node(elem, tl, tr)) => elem :: breadthBTHelper(queue.dequeue.enqueue(tl).enqueue(tr))
      breadthBTHelper(MyQueue(tree))
