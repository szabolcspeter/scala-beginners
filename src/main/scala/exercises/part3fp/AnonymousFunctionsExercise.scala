package exercises.part3fp

import exercises.part3fp.WhatsAFunctionExercise.superAdder

/*
    1.  MyList: replace all FunctionX calls with lambdas
    2.  Rewrite the "special" adder as an anonymous function
   */

object AnonymousFunctionsExercise extends App {

  // 1)
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

  println(listOfIntegers.map(elem => elem * 2).toString)
  println(listOfIntegers.map(_ * 2).toString)

  println(listOfIntegers.filter(elem => elem % 2 == 0).toString)
  println(listOfIntegers.filter(_ % 2 == 0).toString)

  println((listOfIntegers ++ anotherListOfIntegers).toString)
  println(listOfIntegers.flatMap(elem => new GenericCons[Int](elem, new GenericCons[Int](elem + 1, GenericEmpty))).toString)

  println(listOfIntegers == cloneListOfIntegers)

  // 2)
  val superAdder = (x: Int) => (y: Int) => x + y

  val adder3 = superAdder(3)
  println(adder3(4)) // prints 7
  println(superAdder(3)(4)) // the same. This is called curried function
}
