package exercises.part3fp

import exercises.part3fp.AnonymousFunctionsExercise.MyGenericList

/*
    1.  Expand MyList
        - foreach method A => Unit
          [1,2,3].foreach(x => println(x))

        - sort function ((A, A) => Int) => MyList
          [1,2,3].sort((x, y) => y - x) => [3,2,1]

        - zipWith (list, (A, A) => B) => MyList[B]
          [1,2,3].zipWith([4,5,6], x * y) => [1 * 4, 2 * 5, 3 * 6] = [4,10,18]

        - fold(start)(function) => a value
          [1,2,3].fold(0)(x + y) = 6

    2.  toCurry(f: (Int, Int) => Int) => (Int => Int => Int)
        fromCurry(f: (Int => Int => Int)) => (Int, Int) => Int

    3.  compose(f,g) => x => f(g(x))
        andThen(f,g) => x => g(f(x))
   */

object HOFsCurriesExercise extends App {

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

    // hofs
    def foreach(f: A => Unit): Unit
    def sort(compare: (A, A) => Int): MyGenericList[A]
    def zipWith[B, C](list: MyGenericList[B], zip: (A, B) => C): MyGenericList[C]
    def fold[B](start: B)(operator: (B, A) => B): B
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

    // hofs
    def foreach(f: Nothing => Unit): Unit = ()
    def sort(compare: (Nothing, Nothing) => Int) = GenericEmpty
    def zipWith[B, C](list: MyGenericList[B], zip: (Nothing, B) => C): MyGenericList[C] =
      if (!list.isEmpty) throw new RuntimeException("Lists do not have the same length")
      else GenericEmpty
    def fold[B](start: B)(operator: (B, Nothing) => B): B = start
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

      // hofs
    def foreach(f: A => Unit): Unit = {
      f(h)
      t.foreach(f)
    }

    def sort(compare: (A, A) => Int): MyGenericList[A] = {

      def insert(x: A, sortedList: MyGenericList[A]): MyGenericList[A] = {
        if (sortedList.isEmpty) new GenericCons[A](x, GenericEmpty)
        else if (compare(x, sortedList.head) <= 0) new GenericCons[A](x, sortedList)
        else new GenericCons[A](sortedList.head, insert(x, sortedList.tail))
      }

      val sortedTail = t.sort(compare)
      insert(h, sortedTail)
    }

    def zipWith[B, C](list: MyGenericList[B], zip: (A, B) => C): MyGenericList[C] =
      if (list.isEmpty) throw new RuntimeException("Lists do not have the same length")
      else new GenericCons[C](zip(h, list.head), t.zipWith(list.tail, zip))


    /*
        [1,2,3].fold(0)(+) =
        = [2,3].fold(1)(+) =
        = [3].fold(3)(+) =
        = [].fold(6)(+)
        = 6
       */
    def fold[B](start: B)(operator: (B, A) => B): B = {
      /*val newStart = operator(start, h)
      t.fold(newStart)(operator)*/

      t.fold(operator(start, h))(operator)
    }
  }

  val listOfIntegers: MyGenericList[Int] = new GenericCons[Int](1, new GenericCons[Int](2, new GenericCons[Int](3, GenericEmpty)))
  val cloneListOfIntegers: MyGenericList[Int] = new GenericCons[Int](1, new GenericCons[Int](2, new GenericCons[Int](3, GenericEmpty)))
  val anotherListOfIntegers: MyGenericList[Int] = new GenericCons[Int](4, new GenericCons[Int](5, GenericEmpty))
  val listOfStrings: MyGenericList[String] = new GenericCons[String]("Hello", new GenericCons[String]("Scala", GenericEmpty))

  // listOfIntegers.foreach(x => println(x))
  listOfIntegers.foreach(println) // this is sorter form
  println(listOfIntegers.sort((x, y) => y - x))
  println(anotherListOfIntegers.zipWith[String, String](listOfStrings, _ + "-" + _))
  println(listOfIntegers.fold(0)(_ + _))

  // 2)
  def toCurry(f: (Int, Int) => Int): (Int => Int => Int) =
    x => y => f(x, y)

  def fromCurry(f: (Int => Int => Int)): (Int, Int) => Int =
    (x, y) => f(x)(y)

  def superAdder2: (Int => Int => Int) = toCurry(_ + _)
  def add4 = superAdder2(4)
  println(add4(17)) // 21

  val superAdder: Int => (Int => Int) = (x: Int) => (y: Int) => x + y
  val simpleAdder = fromCurry(superAdder)
  println(simpleAdder(4, 17)) // 21

  // 3)
  // FunctionX

  def compose[A, B, T](f: A => B, g: T => A): T => B =
    x => f(g(x))

  def andThen[A, B, C](f: A => B, g: B => C): A => C =
    x => g(f(x))

  val add2 = (x: Int) => x + 2
  val times3 = (x: Int) => x * 3
  val composed = compose(add2, times3)
  val ordered = andThen(add2, times3)
  println(composed(4)) // 14
  println(ordered(4)) // 18
}
