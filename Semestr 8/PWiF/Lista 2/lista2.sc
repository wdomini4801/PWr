import scala.annotation.tailrec
// Wojciech Dominiak

// zadanie 1
def take[A](n: Int, xs: List[A]): List[A] =
  xs match
    case Nil => Nil
    case h :: t => if n <= 0 then Nil else h :: take(n-1, t)

take(2, List(1,2,3,5,6)) == List(1,2)
take(-2, List(1,2,3,5,6)) == Nil
take(8, List(1,2,3,5,6)) == List(1,2,3,5,6)
take(0, List(1,2,3)) == Nil
take(5, Nil) == Nil
take(-5, Nil) == Nil

// zadanie 2
@tailrec
def drop[A](n: Int, xs: List[A]): List[A] =
  xs match
    case Nil => xs
    case _ :: t => if n <= 0 then xs else drop(n-1, t)

drop(2, List(1,2,3,5,6)) == List(3,5,6)
drop(-2, List(1,2,3,5,6)) == List(1,2,3,5,6)
drop(8, List(1,2,3,5,6)) == Nil
drop(0, List(1,2,3)) == List(1,2,3)
drop(3, List(1,2,3)) == Nil
drop(5, Nil) == Nil
drop(-5, Nil) == Nil

// zadanie 3
def reverse[A](xs: List[A]): List[A] =
  @tailrec
  def reverseHelper[A](xs: List[A], acc: List[A]): List[A] =
    xs match
      case Nil => acc
      case h :: t => reverseHelper(t, h :: acc)
  reverseHelper(xs, Nil)

reverse(List("Ala", "ma", "kota")) == List("kota", "ma", "Ala")
reverse(List(-1, -2, -3, -4, -5)) == List(-5, -4, -3, -2, -1)
reverse(List(0)) == List(0)
reverse(Nil) == Nil

// zadanie 4
val replicate: List[Int] => List[Int] = xs =>
  def replicateHelper(elem: Int, rep: Int, tail: List[Int]): List[Int] =
    if rep > 0 then elem :: replicateHelper(elem, rep-1, tail)
    else replicate(tail)
  xs match
    case Nil => Nil
    case h :: t => replicateHelper(h, h, t)

replicate(List(1,0,4,-2,3)) == List(1, 4, 4, 4, 4, 3, 3, 3)
replicate(List(1, 2, 3)) == List(1, 2, 2, 3, 3, 3)
replicate(List(5)) == List(5, 5, 5, 5, 5)
replicate(List(3, 2)) == List(3, 3, 3, 2, 2)
replicate(List(0)) == Nil
replicate(List(-1)) == Nil
replicate(List(-1, -2, -3)) == Nil
replicate(List(-1, -2, 3)) == List(3, 3, 3)

// zadanie 5
val root3 = (a: Double) =>
  @tailrec
  def root3Helper(x: Double): Double =
    if math.abs(x * x * x - a) <= 1.0e-15 * math.abs(a) then x
    else root3Helper(x + (a / (x * x) - x) / 3)
  root3Helper(if a > 1 then a/3 else a)

math.abs(root3(-8.0) + 2.0) <= 1.0e-70
math.abs(root3(8.0) - 2.0) <= 1.0e-70
math.abs(root3(0) - 0) <= 1.0e-70
math.abs(root3(1.0) - 1.0) <= 1.0e-70
math.abs(root3(27.0) - 3.0) <= 1.0e-70
math.abs(root3(-27.0) + 3.0) <= 1.0e-70
math.abs(root3(-1000.0) + .0) <= 1.0e-70
