// Wojciech Dominiak

enum BT[+A]:
  case Empty
  case Node(elem: A, left: BT[A], right: BT[A])

import BT.*

import scala.annotation.tailrec

val t = Node(1, Node(2, Empty, Node(3, Empty, Empty)), Empty)
val t2 = Node(10, Empty, Node(-2, Empty, Empty))
val t3 = Node(5, Empty, Empty)
val t4 = Empty
val t_str = Node("A", Node("b", Empty, Empty), Node("c", Empty, Empty))

// zadanie 1
val sumBT: BT[Int] => Int = bt =>
  bt match
    case Node(elem, tl, tr) => elem + sumBT(tl) + sumBT(tr)
    case Empty => 0

sumBT(t) == 6
sumBT(t2) == 8
sumBT(t3) == 5
sumBT(t4) == 0

// zadanie 2
def foldBT[A, B](f: A => (B, B) => B)(acc: B)(bt: BT[A]): B =
  bt match
    case Empty => acc
    case Node(elem, tl, tr) => f(elem)(foldBT(f)(acc)(tl), foldBT(f)(acc)(tr))

val add = (x: Int) => (y: Int, z: Int) => x + y + z
val concat = (x: String) => (y: String, z: String) => x + y + z
val mul = (x: Int) => (y: Int, z: Int) => x * y * z

foldBT(add)(0)(t) == 6
foldBT(mul)(1)(t) == 6
foldBT(add)(0)(t2) == 8
foldBT(mul)(1)(t2) == -20
foldBT(add)(0)(t3) == 5
foldBT(mul)(1)(t3) == 5
foldBT(add)(0)(t4) == 0
foldBT(mul)(1)(t4) == 1
foldBT(concat)("")(t_str) == "Abc"

// zadanie 3a
val sumBTfold: BT[Int] => Int = bt =>
  foldBT[Int, Int](v => (l, r) => v + l + r)(0)(bt)

sumBTfold(t) == 6
sumBTfold(t2) == 8
sumBTfold(t3) == 5
sumBTfold(t4) == 0

// zadanie 3b
def inorderBTfold[A](bt: BT[A]): List[A] =
  foldBT[A, List[A]](v => (l, r) => l ::: v :: r)(Nil)(bt)

inorderBTfold(t) == List(2, 3, 1)
inorderBTfold(t2) == List(10, -2)
inorderBTfold(t3) == List(5)
inorderBTfold(t4) == Nil
inorderBTfold(t_str) == List("b", "A", "c")

def preorderBTfold[A](bt: BT[A]): List[A] =
  foldBT[A, List[A]](v => (l, r) => v :: l ::: r)(Nil)(bt)

preorderBTfold(t) == List(1, 2, 3)
preorderBTfold(t2) == List(10, -2)
preorderBTfold(t3) == List(5)
preorderBTfold(t4) == Nil
preorderBTfold(t_str) == List("A", "b", "c")

def postorderBTfold[A](bt: BT[A]): List[A] =
  foldBT[A, List[A]](v => (l, r) => l ::: r ::: List(v))(Nil)(bt)

postorderBTfold(t) == List(3, 2, 1)
postorderBTfold(t2) == List(-2, 10)
postorderBTfold(t3) == List(5)
postorderBTfold(t4) == Nil
postorderBTfold(t_str) == List("b", "c", "A")

// zadanie 4
def mapBT[A, B](f: A => B)(bt: BT[A]): BT[B] =
  foldBT[A, BT[B]](v => (l, r) => Node(f(v), l, r))(Empty)(bt)

mapBT[Int, Int](v => 2 * v)(t) == Node(2,Node(4,Empty,Node(6,Empty,Empty)),Empty)
mapBT[Int, Int](v => -v)(t2) == Node(-10, Empty, Node(2, Empty, Empty))
mapBT[Int, Boolean](v => v % 2 == 0)(t3) == Node(false, Empty, Empty)
mapBT[Int, Int](v => v * 10)(t4) == Empty
mapBT[String, String](v => v.capitalize)(t_str) == Node("A", Node("B", Empty, Empty), Node("C", Empty, Empty))

// zadanie 5
case class Graph[A](succ: A => List[A])
val g = Graph((i: Int) =>
  i match
    case 0 => List(3)
    case 1 => List(0, 2, 4)
    case 2 => List(1)
    case 3 => List(5)
    case 4 => List(0, 2)
    case 5 => List(3)
    case n => throw new NoSuchElementException(s"Graph g: node $n doesn't exist")
)

def pathExists[A](g: Graph[A])(from: A, to: A): Boolean =
  @tailrec
  def helper(current: A)(visited: List[A])(queue: List[A]): Boolean =
    if current == to && visited != Nil then true
    else
      queue match
        case Nil => false
        case h :: t =>
          if visited contains current then helper(h)(visited)(t)
          else helper(h)(current :: visited)(t ::: (g succ current))
  helper(from)(Nil)(g succ from)

pathExists(g)(4, 1)
!pathExists(g)(0, 4)
!pathExists(g)(3, 0)
pathExists(g)(2,2)
!pathExists(g)(0,0)
!pathExists(g)(0, 7)
!pathExists(g)(7, 0)
