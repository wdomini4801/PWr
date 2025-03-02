import scala.annotation.tailrec
// Wojciech Dominiak

// zadanie 1a
@tailrec
def existsA[A](xs: List[A])(p: A => Boolean): Boolean =
  xs match
    case Nil => false
    case h :: t => p(h) || existsA(t)(p)

existsA(List(5, 1, 2, 3))(_ == 2)
!existsA(List(1, 2, 3))(_ > 3)
existsA(List(-1, -2, -3))(_ < -1)
existsA(List("ala", "ma", "kota"))(_ == "ala")
!existsA(Nil)(_ == 0)

// zadanie 1b
def existsB[A](xs: List[A])(p: A => Boolean): Boolean =
  xs.foldLeft(false)((result, current) => result || p(current))

existsB(List(5, 1, 2, 3))(_ == 2)
!existsB(List(1, 2, 3))(_ > 3)
existsB(List(-1, -2, -3))(_ < -1)
existsB(List("ala", "ma", "kota"))(_ == "ala")
!existsB(Nil)(_ == 0)

// zadanie 1c
def existsC[A](xs: List[A])(p: A => Boolean): Boolean =
  xs.foldRight(false)((current, result) => result || p(current))

existsC(List(5, 1, 2, 3))(_ == 2)
!existsC(List(1, 2, 3))(_ > 3)
existsC(List(-1, -2, -3))(_ < -1)
existsC(List("ala", "ma", "kota"))(_ == "ala")
!existsC(Nil)(_ == 0)

// zadanie 2
val filterR: [A] => List[A] => (A => Boolean) => List[A] =
  [A] => (xs: List[A]) => (p: A => Boolean) =>
    xs.foldRight[List[A]](Nil)((current, result) =>
      if p(current) then current :: result else result)

filterR(List(2,7,1,3,7,8,4,1,6,9))(_ > 3) == List(7, 7, 8, 4, 6, 9)
filterR(List(2, 1, 3, 7, 4, 3, 2, 1))(_ <= 1) == List(1, 1)
filterR(List(1, 2, 3, 4, 5))(_ % 2 == 0) == List(2, 4)
filterR(List(1, 2, 3, 4, 5))(_ > 0) == List(1, 2, 3, 4, 5)
filterR(List("a", "b", "c", "d", "e"))(_ == "z") == Nil
filterR(List("a", "a", "a", "b", "a"))(_ != "a") == List("b")
filterR(Nil)(_ == 0) == Nil

// zadanie 3a
val remove1A: [A] => List[A] => (A => Boolean) => List[A] =
  [A] => (xs: List[A]) => (p: A => Boolean) =>
    xs match
      case Nil => Nil
      case h :: t => if p(h) then t else h :: remove1A(t)(p)

remove1A(List(1,2,3,2,5))(_ == 2) == List(1, 3, 2, 5)
remove1A(List(1, 2, 3, 4, 5))(_ % 2 == 0) == List(1, 3, 4, 5)
remove1A(List(-1, -2, -3, -4, -5))(_ > 0) == List(-1, -2, -3, -4, -5)
remove1A(List(1, 2, 3, 4, 5))(_ < 2) == List(2, 3, 4, 5)
remove1A(List("a", "b", "c", "d"))(_ == "d") == List("a", "b", "c")
remove1A(Nil)(_ != 5) == Nil

// zadanie 3b
val remove1B: [A] => List[A] => (A => Boolean) => List[A] =
  [A] => (xs: List[A]) => (p: A => Boolean) =>
    @tailrec
    def removeHelper(xs: List[A], acc: List[A]): List[A] =
      xs match
        case Nil => acc.reverse
        case h :: t => if p(h) then t.reverse_:::(acc) else removeHelper(t, h::acc)
    removeHelper(xs, Nil)

remove1B(List(1,2,3,2,5))(_ == 2) == List(1, 3, 2, 5)
remove1B(List(1, 2, 3, 4, 5))(_ % 2 == 0) == List(1, 3, 4, 5)
remove1B(List(-1, -2, -3, -4, -5))(_ > 0) == List(-1, -2, -3, -4, -5)
remove1B(List(1, 2, 3, 4, 5))(_ < 2) == List(2, 3, 4, 5)
remove1B(List("a", "b", "c", "d"))(_ == "d") == List("a", "b", "c")
remove1B(Nil)(_ != 5) == Nil

// zadanie 4
def splitAt[A](xs: List[A])(n: Int): (List[A], List[A]) =
  @tailrec
  def splitHelper(count: Int, acc: List[A], xs: List[A]): (List[A], List[A]) =
    xs match
      case Nil => (acc.reverse, xs)
      case h :: t =>
        if count < n then splitHelper(count+1, h::acc, t) else (acc.reverse, xs)
  splitHelper(0, Nil, xs)

splitAt(List('a','b','c','d','e'))(2) == (List('a', 'b'), List('c', 'd', 'e'))
splitAt(List(-1, -2, -3, -4, -5))(1) == (List(-1), List(-2, -3, -4, -5))
splitAt(List(1, 2, 3, 4, 5))(6) == (List(1, 2, 3, 4, 5), Nil)
splitAt(List(1, 2, 3, 4, 5))(0) == (Nil, List(1, 2, 3, 4, 5))
splitAt(List(1, 2, 3, 4, 5))(-1) == (Nil, List(1, 2, 3, 4, 5))
splitAt(Nil)(3) == (Nil, Nil)
