package exercises.part2oop

object MyGenericListExpandedExercise extends App {

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

  object GenericEmpty extends MyGenericList[Nothing] {
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

  class GenericCons[+A](h: A, t: MyGenericList[A]) extends MyGenericList[A] {
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
  println(listOfIntegers.toString)

  /*
      [1,2,3].map(n * 2)
        = new Cons(2, [2,3].map(n * 2))
        = new Cons(2, new Cons(4, [3].map(n * 2)))
        = new Cons(2, new Cons(4, new Cons(6, Empty.map(n * 2))))
        = new Cons(2, new Cons(4, new Cons(6, Empty))))
  */
  println(listOfIntegers.map(new MyTransformer[Int, Int] {
    override def transform(elem: Int): Int = elem * 2
  }).toString)

  /*
      [1,2,3].filter(n % 2 == 0) =
        [2,3].filter(n % 2 == 0) =
        = new Cons(2, [3].filter(n % 2 == 0))
        = new Cons(2, Empty.filter(n % 2 == 0))
        = new Cons(2, Empty)
  */
  println(listOfIntegers.filter(new MyPredicate[Int] {
    override def test(elem: Int): Boolean = elem % 2 == 0
  }).toString)

  val anotherListOfIntegers: MyGenericList[Int] = new GenericCons[Int](4, new GenericCons[Int](5, GenericEmpty))
  println((listOfIntegers ++ anotherListOfIntegers).toString)

  /*
      [1,2].flatMap(n => [n, n+1])
      = [1,2] ++ [2].flatMap(n => [n, n+1])
      = [1,2] ++ [2,3] ++ Empty.flatMap(n => [n, n+1])
      = [1,2] ++ [2,3] ++ Empty
      = [1,2,2,3]
  */
  println(listOfIntegers.flatMap(new MyTransformer[Int, MyGenericList[Int]] {
    override def transform(elem: Int): MyGenericList[Int] = new GenericCons[Int](elem, new GenericCons[Int](elem + 1, GenericEmpty))
  }).toString)

}
