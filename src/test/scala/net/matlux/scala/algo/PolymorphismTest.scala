package net.matlux.scala.algo

import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}

class PolymorphismTest extends FlatSpec with Matchers with BeforeAndAfterEach {



  override def beforeEach() {

  }

  override def afterEach() {

  }
  "Ad-hoc polymorphism" should "work" in {
    trait CanPlus[A] {
      def plus(a1: A, a2: A): A
    }

    def plus[A: CanPlus](a1: A, a2: A): A = implicitly[CanPlus[A]].plus(a1, a2)
  }

  "sum function" should "work" in {
    def sum(xs: List[Int]): Int = xs.foldLeft(0) { _ + _ }

    sum(List(1, 2, 3, 4))
  }

  "Monoid" should "work" in {
    trait Monoid[A] {
      def mappend(a1: A, a2: A): A
      def mzero: A
    }
    //object Monoid {
      implicit val IntMonoid: Monoid[Int] = new Monoid[Int] {
        def mappend(a: Int, b: Int): Int = a + b
        def mzero: Int = 0
      }
      implicit val StringMonoid: Monoid[String] = new Monoid[String] {
        def mappend(a: String, b: String): String = a + b
        def mzero: String = ""
      }
    //}
    def sum[A: Monoid](xs: List[A]): A = {
      val m = implicitly[Monoid[A]]
      xs.foldLeft(m.mzero)(m.mappend)
    }

    sum(List(1,2,3))

    sum(List("a", "b", "c"))
  }

}
