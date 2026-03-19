package lectures.part2oop

import playground.{Cinderella => Princess, PrinceCharming}

import java.util.Date
import java.sql.{Date => Sqldate} // alias

object PackagingAndImports extends App {

  val writer = new exercises.part2oop.Writer("Daniel", "RockTheJVM", 2018) // fully qualified name
  val princess = new Princess
  // packages are in hierarchy
  // matching folder structure.

  // package object
  sayHello()
  println(SPEED_OF_LIGHT)

  // imports
  val prince = new PrinceCharming

  val date = new Date
  // 1. use FQ names
  val sqlDate = new java.sql.Date(2026, 3, 19)
  // 2. use aliasing (see import at line 3)

  // default imports
  // java.lang - String, Object, Exception
  // scala - Int, Nothing, Function
  // scala.Predef - println, ???
}
