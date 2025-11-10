package lectures.part1basics

object Expressions extends App {

  val x = 1 + 2 // EXPRESSION
  println(x)

  println(2 + 3 * 4)
  // >>> (right shift with zero extension)

  println(1 == x)
  println(!(1 == x))

  var aVariable = 2
  aVariable += 3  // .... side effects (side effects are expressions returning Unit)
  println(aVariable)

  // Instruction (DO) vs Expressions (VALUE)

  // IF Expression
  val aCondition = true
  val aConditionedValue = if (aCondition) 5 else 3
  println(aConditionedValue)
  println(if (aCondition) 5 else 3)

  // !!!! NEVER CODE IMPERATIVE STYLE IN A FUNCTIONAL LANGUAGE LIKE SCALA !!!!
  // !!!! SO NEVER WRITE THIS AGAIN (LOOPS ARE IMPERATIVE CODE STYLES) !!!!
  var i = 0
  while (i < 10) {
    println(i)
    i += 1
  }

  // EVERYTHING in Scala is an Expression!
  val aWeirdValue: Unit = aVariable = 3 // Unit === void
  println(aWeirdValue) // outputs '()' because type of aWeirdValue is Unit

  // side effects: println(), whiles, reassigning

  // Code blocks
  val aCodeBlock = {
    val y = 2
    val z = y + 1

    if (z > 2) "hello" else "goodbye"
  }
}