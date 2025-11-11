package lectures.part1basics

object Functions extends App {

  def aFunction(a: String, b: Int): String = {
    a + " " + b
  }

  println(aFunction("hello", 3))

  def aParameterlessFunction(): Int = 42
  println(aParameterlessFunction())

  // WHEN YOU NEED LOOPS, USE RECURSION
  def aRepeatedFunction(aString: String, n: Int): String = {
    if (n == 1) aString
    else aString + aRepeatedFunction(aString, n-1)
  }

  println(aRepeatedFunction("hello", 3))

  def aFunctionWithSideEffects(aString: String): Unit = println(aString)

  def aBigFunction(n: Int): Int = {
    def aSmallerFunction(a: Int, b: Int): Int = a + b

    aSmallerFunction(n, n-1)
  }

  // Exercise 1
  def aGreetingFunction(name: String, age: Int): String = "Hi, my name is " + name + " and I am " + age + " years old."

  println(aGreetingFunction("Steve", 21))

  // Exercise 2
  def aFactorialFunction(n: Int): Int = {
    if (n <= 0) 1
    else n * aFactorialFunction(n - 1)
  }

  println(aFactorialFunction(5))

  // Exercise 3
  def aFibonacciFunction(n: Int): Int =
    if (n <= 2) 1
    else aFibonacciFunction(n-1) + aFibonacciFunction(n-2)

  // 1 1 2 3 5 8 13 21
  println(aFibonacciFunction(8))

  // Exercise 4
  def isPrime(n: Int): Boolean = {
    def isPrimeUntil(t: Int): Boolean =
      if (t <= 1) true
      else n % t != 0 && isPrimeUntil(t-1)

    isPrimeUntil(n / 2)
  }

  println(isPrime(37))
  println(isPrime(2003))
  println(isPrime(37 * 17))

}