package lectures.part2oop

object CaseClasses extends App {

  /*
    equals, hashCode, toString
   */

  case class Person(name: String, age: Int)

  // 1. class parameters are fields
  val jim = new Person("Jim", 34)
  println(jim.name)

  // 2. sensible toString
  // println(instance) = println(instance.toString) // syntactic sugar
  println(jim)

  // 3. equals and hashCode implemented out of the box
  val jim2 = new Person("Jim", 34)
  println(jim == jim2)

  // 4. Case Classes have handy copy method
  val jim3 = jim.copy(age = 45)
  println(jim3)

  // 5. Case Classes have companion objects
  val thePerson = Person
  val mary = Person("Mary", 23) // here we delegate to apply()
  println(mary)

  // 6. Case Classes are serializable
  // Akka

  // 7. Case Classes have extractor patterns = CCs can be used in PATTERN MATCHING

  // Case object have the same properties as Case class except doesn't get companion object of course
  case object UnitedKingdom {
    def name(): String = "The UK of GB and NI"
  }
}
