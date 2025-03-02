import scala.annotation.tailrec
// Wojciech Dominiak

// zadanie 1

var count = 0
while (count < 5) {
  println(count)
  count += 1
}

@tailrec
def whileLoop(cond: => Boolean)(exp: => Unit): Unit =
  if cond then
    exp
    whileLoop(cond)(exp)

count = 0
whileLoop (count < 5) {
  println(count)
  count += 1
}

whileLoop(count >= 0) {
  println(count)
  count -= 1
}

whileLoop(count < 0) {
  count += 1
}

count == 0

// zadanie 2
def lrepeat[A](k: Int)(xsl: LazyList[A]): LazyList[A] =
  def lrepeatHelper(reps: Int)(remaining: LazyList[A]): LazyList[A] =
    remaining match
      case LazyList() => LazyList()
      case h #:: t =>
        if reps == 0 then lrepeatHelper(k)(t)
        else h #:: lrepeatHelper(reps - 1)(remaining)
  lrepeatHelper(k)(xsl)

lrepeat(3)(LazyList('a','b','c','d')).toList == List('a', 'a', 'a', 'b', 'b', 'b', 'c', 'c', 'c', 'd', 'd', 'd')
lrepeat(3)(LazyList.from(1)).take(15).toList == List(1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5)
lrepeat(3)(LazyList()).take(15).toList == List()
lrepeat(1)(LazyList.from(5)).take(5).toList == List(5, 6, 7, 8, 9)
lrepeat(5)(LazyList(0)).toList == List(0, 0, 0, 0, 0)
lrepeat(0)(LazyList(true, true, false, false)).toList == Nil

// zadanie 3
enum BT[+A]:
  case Empty
  case Node(elem: A, left: BT[A], right: BT[A])

import BT.*
val tt = Node(1,Node(2,Node(4,Empty,Empty),Empty),Node(3,Node(5,Empty,Node(6,Empty,Empty)),Empty))
val tt2 = Node("A",Node("B",Empty,Empty),Node("C",Node("D",Empty,Empty), Empty))
val tt3 = Node(true, Empty, Empty)

enum lBT[+A]:
  case LEmpty
  case LNode(elem: A, left: () => lBT[A], right: () => lBT[A])

import lBT.*

// zadanie 3a
def lBreadth[A](ltree: lBT[A]): LazyList[A] =
  def lBreadthHelper(queue: List[lBT[A]]): LazyList[A] =
    queue match
      case Nil => LazyList()
      case LEmpty :: t => lBreadthHelper(t)
      case LNode(elem, tl, tr) :: t => elem #:: lBreadthHelper(t ::: List(tl(), tr()))
  lBreadthHelper(List(ltree))

// zadanie 3b
def foldBT[A, B](f: A => (B, B) => B)(acc: B)(bt: BT[A]): B =
  bt match
    case Empty => acc
    case Node(elem, tl, tr) => f(elem)(foldBT(f)(acc)(tl), foldBT(f)(acc)(tr))

def BT2lBT[A](bt: BT[A]): lBT[A] =
  foldBT[A, lBT[A]](v => (l, r) => LNode(v, () => l, () => r))(LEmpty)(bt)

lBreadth(BT2lBT(tt)).force == LazyList(1, 2, 3, 4, 5, 6)
lBreadth(BT2lBT(tt2)).toList == List("A", "B", "C", "D")
lBreadth(BT2lBT(tt3)).force == LazyList(true)
lBreadth(LEmpty) == LazyList()

// zadanie 3c
def lTree(n: Int): lBT[Int] =
  LNode(n, () => lTree(2*n), () => lTree(2*n + 1))

lBreadth(lTree(1)).take(20).toList == List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)
lBreadth(LEmpty).take(20).toList == List()
lBreadth(lTree(2)).take(10).force == LazyList(2, 4, 5, 8, 9, 10, 11, 16, 17, 18)
lBreadth(lTree(0)).take(10).toList == List(0, 0, 1, 0, 1, 2, 3, 0, 1, 2)