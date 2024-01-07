package exercises.part2oop

abstract class MyGenericList[+A] {

  def head: A
  def tail: MyGenericList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyGenericList[B]
  def printElements: String
  // polymorphic call
  override def toString: String = "[" + printElements + "]"

}

object GenericEmpty extends MyGenericList[Nothing] {

  def head: Nothing = throw new NoSuchElementException
  def tail: MyGenericList[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): MyGenericList[B] = new GenericCons(element, GenericEmpty)
  def printElements: String = ""
}

class GenericCons[+A](h: A, t: MyGenericList[A]) extends MyGenericList[A] {

  def head: A = h
  def tail: MyGenericList[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyGenericList[B] = new GenericCons(element, this)
  def printElements: String =
    if (t.isEmpty) "" + h
    else s"$h ${t.printElements}"

}

// test
object GenericListTest extends App {

  val listOfIntegers: MyGenericList[Int] = new GenericCons(1, new GenericCons(2, new GenericCons(3, GenericEmpty)))
  val listOfStrings: MyGenericList[String] = new GenericCons("Hello", new GenericCons("Scala", GenericEmpty))
  println(listOfIntegers.toString)
  println(listOfStrings.toString)
}
