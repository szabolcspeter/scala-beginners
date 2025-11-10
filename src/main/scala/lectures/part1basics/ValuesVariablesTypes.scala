package lectures.part1basics

object ValuesVariablesTypes extends App {

  // val x: Int = 42

  // COMPILER CAN INFER TYPES
  val x = 42
  println(x)

  // VALS ARE IMMUTABLE
  // x = 2

  val aString: String = "hello"
  val anotherString = "goodbye"
  val aBoolean: Boolean = false
  val aChar: Char = 'a'
  val anInt: Int = x
  val aShort: Short = 4613
  val aLong: Long = 5273985273895237L
  val aFloat: Float = 2.0f
  val aDouble: Double = 3.14

  // VARIABLES
  var aVariable: Int = 4
  aVariable = 5 // side effects

  // functional programming involves working less with variables, prefer vals over vars

}