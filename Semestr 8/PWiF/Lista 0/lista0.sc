// Wojciech Dominiak

//  zadanie 1
def last[A](xs: List[A]): A =
  if xs == Nil then
    throw new NoSuchElementException("last of empty list")
  else if xs.tail == Nil then xs.head
  else last(xs.tail)

last(List(1,9,5,6,3)) == 3
last(List("Ala", "ma", "kota")) == "kota"
last(List(5)) == 5
last(Nil)  // =>> wyjątek  java.util.NoSuchElementException: last of empty list