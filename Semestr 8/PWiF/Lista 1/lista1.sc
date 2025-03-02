// Wojciech Dominiak

//  zadanie 1
val suma: List[Double] => Double = xs =>
  if xs == Nil then 0
  else xs.head + suma(xs.tail)

suma(Nil) == 0.0
suma(List(-1, 2, 3)) == 4.0
suma(List(5.6)) == 5.6
suma(List(1.2, 3.4, -1.1)) == 3.5


// zadanie 2
def ends[A](xs: List[A]): (A, A) =
  if xs == Nil then throw new NoSuchElementException("empty list")
  else if xs.tail == Nil then (xs.head, xs.head)
  else if xs.tail.tail == Nil then (xs.head, xs.tail.head)
  else ends(xs.head :: xs.tail.tail)

ends(List(1, 3, 5, 6, 9)) == (1,9)
ends(List("Ala", "ma", "kota")) == ("Ala", "kota")
ends(List(1)) == (1,1)
ends(List(true, false, true)) == (true, true)
ends(Nil) // wyjątek NoSuchElementException: empty list

// zadanie 3
val posortowana: List[Int] => Boolean = xs =>
  if xs == Nil || xs.tail == Nil then true
  else xs.head <= xs.tail.head && posortowana(xs.tail)

posortowana(List(1,3,3,5,6,7)) == true
posortowana(List(-1, 0, 1, 2, 3)) == true
posortowana(List(1, 1, 1, 1)) == true
posortowana(List(1, 2, -3)) == false
posortowana(List(0)) == true
posortowana(List()) == true
posortowana(List(1, 2, 3, 4, 1)) == false
posortowana(List(5, 4, 3, 2, 1)) == false


// zadanie 4
val glue: (List[String], String) => String = (xs, sep) =>
  if xs == Nil then ""
  else if xs.tail == Nil then xs.head
  else s"${xs.head}${sep}${glue(xs.tail, sep)}"

glue(List("To", "jest", "napis"), "-") == "To-jest-napis"
glue(Nil, "-") == ""
glue(List("raz", "dwa", "trzy"), "/") == "raz/dwa/trzy"
glue(List("nie", "spodzianka"), "") == "niespodzianka"
glue(List(), "") == ""
glue(List("Ala"), ";") == "Ala"
