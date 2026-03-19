package exercises.part3fp

import exercises.part2oop.MyGenericListExercise.{listOfIntegers, listOfStrings}
import exercises.part2oop.{GenericCons, GenericEmpty, MyGenericList}
import exercises.part2oop.MyGenericListExpandedExercise.{GenericCons, GenericEmpty, MyGenericList, MyPredicate, MyTransformer, listOfIntegers}
import exercises.part2oop.MyGenericListExpandedWithCaseClassesExercise.{GenericCons, GenericEmpty, MyGenericList, listOfIntegers}

/*
    1.  a function which takes 2 strings and concatenates them
    2.  transform the MyPredicate and MyTransformer into function types
    3.  define a function which takes an int and returns another function which takes an int and returns an int
        - what's the type of this function
        - how to do it
   */

object WhatsAFunctionExercise extends App {

  // 1)
  def concatenator: (String, String) => String = new Function2[String, String, String] {
    override def apply(a: String, b: String): String = a + b
  }

  println(concatenator("Hello ", "Scala"))

  // 2)
  abstract class MyGenericList[+A] {
    def head: A
    def tail: MyGenericList[A]
    def isEmpty: Boolean
    def add[B >: A](element: B): MyGenericList[B]
    def printElements: String
    override def toString: String = s"[$printElements]"

    // HIGHER-ORDER FUNCTIONS
    def map[B](transformer: A => B): MyGenericList[B]
    def flatMap[B](transformer: A => MyGenericList[B]): MyGenericList[B]
    def filter(predicate: A => Boolean): MyGenericList[A]
    // concatenation
    def ++[B >: A](list: MyGenericList[B]): MyGenericList[B]
  }

  case object GenericEmpty extends MyGenericList[Nothing] {
    def head: Nothing = throw new NoSuchElementException
    def tail: MyGenericList[Nothing] = throw new NoSuchElementException
    def isEmpty: Boolean = true
    def add[B >: Nothing](element: B): MyGenericList[B] = new GenericCons(element, GenericEmpty)
    def printElements: String = ""

    def map[B](transformer: Nothing => B): MyGenericList[B] = GenericEmpty
    def flatMap[B](transformer: Nothing => MyGenericList[B]): MyGenericList[B] = GenericEmpty
    def filter(predicate: Nothing => Boolean): MyGenericList[Nothing] = GenericEmpty
    def ++[B >: Nothing](list: MyGenericList[B]): MyGenericList[B] = list
  }

  case class GenericCons[+A](h: A, t: MyGenericList[A]) extends MyGenericList[A] {
    def head: A = h
    def tail: MyGenericList[A] = t
    def isEmpty: Boolean = false
    def add[B >: A](element: B): MyGenericList[B] = new GenericCons(element, this)
    def printElements: String =
      if (t.isEmpty) "" + h
      else s"$h ${t.printElements}"

    def filter(predicate: A => Boolean): MyGenericList[A] =
      if (predicate(h)) new GenericCons(h, t.filter(predicate))
      else t.filter(predicate)

    def map[B](transformer: A => B): MyGenericList[B] =
      new GenericCons(transformer(h), t.map(transformer))

    /*
        [1,2] ++ [3,4,5]
        = new Cons(1, [2] ++ [3,4,5])
        = new Cons(1, new Cons(2, Empty ++ [3,4,5]))
        = new Cons(1, new Cons(2, new Cons(3, new Cons(4, new Cons(5)))))
    */
    def ++[B >: A](list: MyGenericList[B]): MyGenericList[B] = new GenericCons(h, t ++ list)

    def flatMap[B](transformer: A => MyGenericList[B]): MyGenericList[B] =
      transformer(h) ++ t.flatMap(transformer)
  }

  val listOfIntegers: MyGenericList[Int] = new GenericCons[Int](1, new GenericCons[Int](2, new GenericCons[Int](3, GenericEmpty)))
  val cloneListOfIntegers: MyGenericList[Int] = new GenericCons[Int](1, new GenericCons[Int](2, new GenericCons[Int](3, GenericEmpty)))
  val anotherListOfIntegers: MyGenericList[Int] = new GenericCons[Int](4, new GenericCons[Int](5, GenericEmpty))
  val listOfStrings: MyGenericList[String] = new GenericCons[String]("Hello", new GenericCons[String]("Scala", GenericEmpty))

  println(listOfIntegers.toString)
  println(listOfStrings.toString)

  println(listOfIntegers.map(new Function1[Int, Int] {
    override def apply(elem: Int): Int = elem * 2
  }).toString)

  println(listOfIntegers.filter(new Function1[Int, Boolean] {
    override def apply(elem: Int): Boolean = elem % 2 == 0
  }).toString)

  println((listOfIntegers ++ anotherListOfIntegers).toString)
  println(listOfIntegers.flatMap(new Function1[Int, MyGenericList[Int]] {
    override def apply(elem: Int): MyGenericList[Int] = new GenericCons[Int](elem, new GenericCons[Int](elem + 1, GenericEmpty))
  }).toString)

  println(listOfIntegers == cloneListOfIntegers)

  // 3)
  // Function1[Int, Function1[Int, Int]]
  val superAdder: Function1[Int, Function1[Int, Int]] = new Function1[Int, Function1[Int, Int]] {
    override def apply(x: Int): Function1[Int, Int] = new Function1[Int, Int] {
      override def apply(y: Int): Int = x + y
    }
  }

  val adder3 = superAdder(3)
  println(adder3(4)) // print 7
  println(superAdder(3)(4)) // the same. This is called curried function
}
