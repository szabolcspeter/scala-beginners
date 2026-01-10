package exercises.part2oop

import scala.language.postfixOps

object MethodNotationsExercise extends App {

  /*
      1.  Overload the + operator
          mary + "the rockstar" => new person "Mary (the rockstar)"

      2.  Add an age to the Person class
          Add a unary + operator => new person with the age + 1
          +mary => mary with the age incrementer

      3.  Add a "learns" method in the Person class => "Mary learns Scala"
          Add a learnsScala method, calls learns method with "Scala".
          Use it in postfix notation.

      4.  Overload the apply method
          mary.apply(2) => "Mary watched Inception 2 times"
     */

  class Person(val name: String, favoriteMovie: String, val age: Int = 0) {
    def +(nickname: String): Person = new Person(s"$name ($nickname)", favoriteMovie)
    def apply(): String = s"Hi, my name is $name and I like $favoriteMovie"
    def unary_+ : Person = new Person(name, favoriteMovie, age + 1)
    def learns(thing: String): String = s"$name is learning $thing"
    def learnsScala: String = this learns "Scala"
    def apply(n: Int): String = s"$name watched $favoriteMovie $n times"
  }

  // 1
  val mary = new Person("Mary", "Inception")
  println(mary + "the Rockstar")
  println( (mary + "the Rockstar")() )

  // 2
  println((+mary).age)

  // 3
  println(mary learns "C++")
  println(mary learnsScala)

  // 4
  println(mary(10))
}
