package exercises.part2oop

object MyGenericListExpandedWithCaseClassesExercise extends App {

  /*
      Expand MyGenericList - use case classes and case objects
  */

  abstract class MyGenericList[+A] {
    def head: A
    def tail: MyGenericList[A]
    def isEmpty: Boolean
    def add[B >: A](element: B): MyGenericList[B]
    def printElements: String
    override def toString: String = s"[$printElements]"
    def map[B](transformer: MyTransformer[A, B]): MyGenericList[B]
    def flatMap[B](transformer: MyTransformer[A, MyGenericList[B]]): MyGenericList[B]
    def filter(predicate: MyPredicate[A]): MyGenericList[A]

    // concatenation
    def ++[B >: A](list: MyGenericList[B]): MyGenericList[B]
  }

  case object GenericEmpty extends MyGenericList[Nothing] {
    def head: Nothing = throw new NoSuchElementException
    def tail: MyGenericList[Nothing] = throw new NoSuchElementException
    def isEmpty: Boolean = true
    def add[B >: Nothing](element: B): MyGenericList[B] = new GenericCons(element, GenericEmpty)
    def printElements: String = ""
    def map[B](transformer: MyTransformer[Nothing, B]): MyGenericList[B] = GenericEmpty
    def flatMap[B](transformer: MyTransformer[Nothing, MyGenericList[B]]): MyGenericList[B] = GenericEmpty
    def filter(predicate: MyPredicate[Nothing]): MyGenericList[Nothing] = GenericEmpty
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

    def filter(predicate: MyPredicate[A]): MyGenericList[A] =
      if (predicate.test(h)) new GenericCons(h, t.filter(predicate))
      else t.filter(predicate)

    def map[B](transformer: MyTransformer[A, B]): MyGenericList[B] =
      new GenericCons(transformer.transform(h), t.map(transformer))

    /*
        [1,2] ++ [3,4,5]
        = new Cons(1, [2] ++ [3,4,5])
        = new Cons(1, new Cons(2, Empty ++ [3,4,5]))
        = new Cons(1, new Cons(2, new Cons(3, new Cons(4, new Cons(5)))))
    */
    def ++[B >: A](list: MyGenericList[B]): MyGenericList[B] = new GenericCons(h, t ++ list)
    def flatMap[B](transformer: MyTransformer[A, MyGenericList[B]]): MyGenericList[B] =
      transformer.transform(h) ++ t.flatMap(transformer)
  }

  trait MyPredicate[-T] {
    def test(elem: T): Boolean
  }

  trait MyTransformer[-A, B] {
    def transform(elem: A): B
  }

  val listOfIntegers: MyGenericList[Int] = new GenericCons[Int](1, new GenericCons[Int](2, new GenericCons[Int](3, GenericEmpty)))
  val cloneListOfIntegers: MyGenericList[Int] = new GenericCons[Int](1, new GenericCons[Int](2, new GenericCons[Int](3, GenericEmpty)))

  println(listOfIntegers == cloneListOfIntegers)
}
