package lectures.part2oop

object AbstractDataTypes extends App {

  // abstract
  abstract class Animal {
    val creatureType: String = "wild"
    def eat(): Unit
  }

  class Dog extends Animal {
    override val creatureType: String = "Canine"
    def eat(): Unit = println("crunch crunch")
  }

  // traits (ultimate abstract data types in Scala)
  trait ColdBlooded

  trait Carnivore {
    def eat(animal: Animal): Unit
    val preferredMeal: String = "fresh meat"
  }

  class Crocodile extends Animal with Carnivore with ColdBlooded {
    override val creatureType: String = "croc"
    def eat(): Unit = println("nomnomnom")
    def eat(animal: Animal): Unit = println(s"I'm a croc and I am eating ${animal.creatureType}")
  }

  val dog = new Dog
  val croc = new Crocodile
  croc.eat(dog)

  // traits vs abstract classes
  // 1) In Scala 2 traits do not have constructor parameters  (in Scala 3 this is possible)
  // 2) multiple traits may be inherited by the same class
  // 3) traits = behaviour (describe what they do), abstract class = "thing"

}
