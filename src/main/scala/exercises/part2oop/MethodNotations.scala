package exercises.part2oop

import scala.language.postfixOps

object MethodNotations extends App {

  val mary = new Person("Mary", "Inception")
  println((mary + "the rockstar")())
  println((+mary).age)
  println(mary learnsScala)
  println(mary(10))
}

class Person(val name: String, favoriteMovie: String, val age: Int = 0) {
  def +(nickname: String): Person = new Person(s"$name ($nickname)", favoriteMovie)
  def apply(): String = s"Hi, my name is $name and I like $favoriteMovie"
  def unary_+ : Person = new Person(name, favoriteMovie, age + 1)
  def learns(thing: String): String = s"$name is learning $thing"
  def learnsScala = this learns "Scala"
  def apply(n: Int): String = s"$name watched $favoriteMovie $n times"
}
