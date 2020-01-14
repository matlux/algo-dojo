package net.matlux.scala.algo

import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}

class implicitTest extends FlatSpec with Matchers with BeforeAndAfterEach {



  override def beforeEach() {

  }

  override def afterEach() {

  }
  "type classes" should "work" in {
    sealed trait Animal
    final case class Dog(name: String) extends Animal
    final case class Cat(name: String) extends Animal
    final case class Bird(name: String) extends Animal


    trait BehavesLikeHuman[A] {
      def speak(a: A): Unit
    }

    object BehavesLikeHumanInstances {

      // only for `Dog`
      implicit val dogBehavesLikeHuman = new BehavesLikeHuman[Dog] {
        def speak(dog: Dog): Unit = {
          println(s"I'm a Dog, my name is ${dog.name}")
        }
      }

    }
    object BehavesLikeHumanSyntax {
      implicit class BehavesLikeHumanOps[A](value: A) {
        def speak(implicit behavesLikeHumanInstance: BehavesLikeHuman[A]): Unit = {
          behavesLikeHumanInstance.speak(value)
        }
      }
    }
    import BehavesLikeHumanInstances.dogBehavesLikeHuman
    import BehavesLikeHumanSyntax.BehavesLikeHumanOps

    val aDog = Dog("toby")

    //BehavesLikeHuman.speak(aDog)   //3a
    aDog.speak


  }
}
