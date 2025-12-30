package exercises.part2oop

object CounterExercise extends App {

  /*
    Counter class
      - receives an int value
      - method current count
      - method to increment/decrement => new Counter
      - overload inc/dec to receive an amount
   */

  val counter = new Counter()
  counter.print()
  counter.inc.print()
  counter.inc.inc.inc.print()
  counter.inc(10).print()
}

class Counter(val count: Int = 0) {

//    def currentCount: Int = count
  def inc: Counter = {
    println("incrementing")
    new Counter(count + 1) // IMMUTABILITY
  }

  def dec: Counter = {
    println("decrementing")
    new Counter(count - 1)
  }

  /*def inc(n: Int): Counter = new Counter(count + n)
  def dec(n: Int): Counter = new Counter(count - n)*/

  def inc(n: Int): Counter = {
    if (n <= 0) this
    else inc.inc(n-1)
  }

  def dec(n: Int): Counter = {
    if (n <= 0) this
    else dec.dec(n-1)
  }

  def print(): Unit = println(count)
}
