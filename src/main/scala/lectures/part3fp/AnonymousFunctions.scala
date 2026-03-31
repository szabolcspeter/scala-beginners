package lectures.part3fp

object AnonymousFunctions extends App {

  // anonymous function (LAMBDA)
  val doubler = (x: Int) => x * 2

  // multiple params in a lambda
  val adder = (a: Int, b: Int) => a + b

  // no params
  val justDoSomething = () => 3

  // careful
  println(justDoSomething) // function itself
  println(justDoSomething()) // call

  // curly braces with lambdas
  val stringToInt = { (str: String) =>
    str.toInt
  }

  // MORE syntactic sugar
  val niceIncrementer: Int => Int = _ + 1 // equivalent to x => x + 1
  val niceAdder: (Int, Int) => Int = _ + _ // equivalent to (a, b) => a + b
}
