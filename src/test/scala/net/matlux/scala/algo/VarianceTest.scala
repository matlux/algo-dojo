package net.matlux.scala.algo

import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}

class VarianceTest extends FlatSpec with Matchers with BeforeAndAfterEach {



  override def beforeEach() {

  }

  override def afterEach() {

  }
  "upper bound" should "work" in {

    /*
    *
    *     Here <: Dog is the upper bound of the type parameter S.
    *         S <: T means S is a subtype of T, and
    *         S >: T means S is a supertype of T, of T is a subtype of S.
    * */




    class Dog
    class Puppy extends Dog

    def assertSomethingAboutDog[S <: Dog](r: S): S = ???
  }

  "lower bound" should "work" in {

  }


  "Covariance" should "work" in {

    case class AnimalContainer[+T](val animal:T)

    class Dog
    class Puppy extends Dog

    class DogCarer(val dog:AnimalContainer[Dog])


    val puppy = new Puppy
    val dog = new Dog

    val puppyContainer:AnimalContainer[Puppy] = AnimalContainer[Puppy](animal = puppy)
    val dogContainer:AnimalContainer[Dog] = AnimalContainer[Dog](animal = dog)

    val dogCarer = new DogCarer(dogContainer)
    val puppyCarer = new DogCarer(puppyContainer)

    println("Done.")

  }

  "Covariance2" should "work" in {
    class Dog
    class Puppy extends Dog



    val puppy = new Puppy
    val dog = new Dog

    val strings: List[Dog] = List(puppy,dog)
    //

  }

  "Contravariance" should "work" in {

    abstract class Type [-T]{
      def typeName : Unit
    }

    class SuperType extends Type[AnyVal]{
      override def typeName: Unit = {
        println("SuperType")
      }
    }
    class SubType extends Type[Int]{
      override def typeName: Unit = {
        println("SubType")
      }
    }

    class TypeCarer{
      def display(t: Type[Int]){
        t.typeName
      }
    }


    val superType = new SuperType
    val subType = new SubType

    val typeCarer = new TypeCarer

    typeCarer.display(subType)
    typeCarer.display(superType)
  }

  "Contravariance2" should "work" in {

    trait Animal {
      def toCat() : Puppy = {
        new Puppy
      }
      def name() : String = ""
    }

    class Dog extends Animal {
      override def name(): String = ???
    }
    class Puppy extends Dog {
      override def name(): String = "Garfield"
    }

    class Cat extends Animal
    class Garfield extends Cat

    abstract class Printer[-A] {
      def print(value: A): Unit
    }
    class AnimalPrinter extends Printer[Animal] {
      def print(animal: Animal): Unit =
        println("The animal's name is: " + animal.name)
    }

    class CatPrinter extends Printer[Cat] {
      def print(cat: Cat): Unit =
        println("The cat's name is: " + cat.name)
    }

  }

  "Contravariance3" should "also work" in {

    trait MatFunction1[-T,+U] {
      def apply(x: T): U
    }

    class Animal {
      def toCat() : Cat = {
        new Cat
      }
    }

    class Dog extends Animal {
      override def toCat(): Cat = new Cat
    }
    class Puppy extends Dog {
      override def toCat(): Cat = new Garfield
    }

    class Cat extends Animal
    class Garfield extends Cat

//    case class Container[+T,-U,+V](val things:T*) {
//
//      def map[S](f : U => V):Container[V,V,S] = new Container(things.toList.map(e => f(e) ) :_*)
//      def assertSomethingAboutDog[S <: Dog](r: S): S = ???
//    }
//    def map[S](f : U => V):Container[V,V,S] = new Container(things.toList.map(e => f(e) ) :_*)
//
//    val puppy = new Puppy
//    val dog = new Dog
//    val f : (Animal) => Puppy = x => x.toCat()
////    val f = new ((Animal) => Puppy) {
////      def apply(x: Animal): Puppy = {
////        x.subClassOf()
////      }
////    }
//
////    val dogs: List[Dog] = List(puppy,dog)
////    val convertedDogs: List[Dog] = dogs.map(f)
//
//    val dogs: Container[Dog] = Container(puppy,dog)
//    val convertedDogs: Container[Dog] = map(dogs, f)

  }
}
