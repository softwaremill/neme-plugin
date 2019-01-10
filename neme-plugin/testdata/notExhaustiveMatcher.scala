package testdata

sealed trait Person

case object PersonOne extends Person

case object PersonTwo extends Person

class TestClass {
  def method1(p: Person) = {
    p match {
      case PersonTwo => ()
    }
  }
}
