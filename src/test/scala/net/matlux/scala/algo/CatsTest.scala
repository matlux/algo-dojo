package net.matlux.scala.algo

import cats.kernel.Semigroup
import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
class CatsTest  extends FlatSpec  with BeforeAndAfterEach {



  override def beforeEach() {

  }

  override def afterEach() {

  }

  "Eq" should "work" in {

    import cats._, cats.data._, cats.implicits._
    import cats.syntax.eq._
    //1 === 1
  }

  "Order" should "work" in {

    import cats._, cats.data._, cats.implicits._
    1 > 2.0
    3.show
    "hello".show
  }

  "Functors" should "work" in {

    import cats._, cats.data._, cats.implicits._
    Functor[List].map(List(1, 2, 3)) {
      _ + 1
    }
    (List(1, 2, 3): List[Int]).map {
      _ + 1
    }

    //Functor[Either[String, Int]].map(Right(1)) { _ + 1 }

    (Right(1): Either[String, Int]) map {
      _ + 1
    }
    (Left("boom!"): Either[String, Int]) map {
      _ + 1
    }

    //val h = ((x: Int) => x + 1) map {_ * 7}

    //h(3)

    implicit val optionFunctor: Functor[Option] = new Functor[Option] {
      def map[A, B](fa: Option[A])(f: A => B) = fa map f
    }

    implicit val listFunctor: Functor[List] = new Functor[List] {
      def map[A, B](fa: List[A])(f: A => B) = fa map f
    }

//    implicit def function1Functor[In]: Functor[Function1[In, ?]] =
//      new Functor[Function1[In, ?]] {
//        def map[A, B](fa: In => A)(f: A => B): Function1[In, B] = fa andThen f
//      }
    implicit def function1Functor[In]: Functor[({type F[A] = Function1[In,A]})#F] =
      new Functor[({type F[A] = Function1[In,A]})#F] {
        def map[A, B](fa: In => A)(f: A => B): Function1[In, B] = fa andThen f
      }

    // LIFT

    val lenOption: Option[String] => Option[Int] = Functor[Option].lift(_.length)
    lenOption(Some("abcd"))

    // FPRODUCT

    val source = List("Cats", "is", "awesome")
    val product = Functor[List].fproduct(source)(_.length).toMap

    product.get("Cats").getOrElse(0)

    Functor[List].fproduct(source)(e => 1).toMap

    // COMPOSE

    val listOpt = Functor[List] compose Functor[Option]
    listOpt.map(List(Some(1), None, Some(3)))(_ + 1)

    //equivalent to this in pure Scala
    List(Some(1), None, Some(3)).map(_.map(_ + 1))

    // Apply

    implicit val optionApply: Apply[Option] = new Apply[Option] {
      def ap[A, B](f: Option[A => B])(fa: Option[A]): Option[B] =
        fa.flatMap(a => f.map(ff => ff(a)))

      def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa map f

      override def product[A, B](fa: Option[A], fb: Option[B]): Option[(A, B)] =
        fa.flatMap(a => fb.map(b => (a, b)))
    }

    implicit val listApply: Apply[List] = new Apply[List] {
      def ap[A, B](f: List[A => B])(fa: List[A]): List[B] =
        fa.flatMap(a => f.map(ff => ff(a)))

      def map[A, B](fa: List[A])(f: A => B): List[B] = fa map f

      override def product[A, B](fa: List[A], fb: List[B]): List[(A, B)] =
        fa.zip(fb)
    }

    val intToString: Int ⇒ String = _.toString
    val double: Int ⇒ Int = _ * 2
    val addTwo: Int ⇒ Int = _ + 2

    Apply[Option].map(Some(1))(intToString)
    Apply[Option].map(Some(1))(double)
    Apply[Option].map(None)(addTwo)

    Functor[Option].map(Some(1))(intToString)
    Functor[Option].map(Some(1))(double)
    Functor[Option].map(None)(addTwo)

    val listOpt2 = Apply[List] compose Apply[Option]
    val plusOne = (x: Int) ⇒ x + 1
    listOpt2.ap(List(Some(plusOne)))(List(Some(1), None, Some(3)))
    Apply[Option].ap(Some(intToString))(Some(1))

    val addArity2 = (a: Int, b: Int) ⇒ a + b
    Apply[Option].ap2(Some(addArity2))(Some(1), Some(2))
    Apply[Option].ap2(Some(addArity2))(Some(1), None)

    val addArity3 = (a: Int, b: Int, c: Int) ⇒ a + b + c
    Apply[Option].ap3(Some(addArity3))(Some(1), Some(2), Some(3))

    Apply[Option].map2(Some(1), Some(2))(addArity2)
    Apply[Option].map3(Some(1), Some(2), Some(3))(addArity3)

    Apply[Option].tuple2(Some(1), Some(2))

    // Apply Builder syntax

    val option2 = Option(1) |@| Option(2)
    val option3 = option2 |@| Option.empty[Int]

    option2 map addArity2
    option3 map addArity3

    option2 apWith Some(addArity2)
    option3 apWith Some(addArity3)

    option2.tupled
    option3.tupled

    // Applicative

    Applicative[Option].pure(1)
    Applicative[List].pure(1)

    (Applicative[List] compose Applicative[Option]).pure(1)

    //APPLICATIVE FUNCTORS & MONADS
    Monad[Option].pure(1)
    Applicative[Option].pure(1)

    //MONAD

    Option(Option(1)).flatten
    Option(None).flatten
    List(List(1), List(2, 3)).flatten

    case class OptionT[F[_], A](value: F[Option[A]])

//    implicit def optionMonad(implicit app: Monad[Option]) =
//      new Monad[Option] {
//        // Define flatMap using Option's flatten method
//        override def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] =
//          app.map(fa)(f).flatten
//        // Reuse this definition from Applicative.
//        override def pure[A](a: A): Option[A] = app.pure(a)
//        override def tailRecM[A, B](a: A)(f: A => OptionT[F, Either[A, B]]): OptionT[F, B] =
//          defaultTailRecM(a)(f)
//      }

  }

  "Effects IO" should "work" in {

    import cats.effect.IO
    import fs2.Stream


    val ioa = IO { println("hey!") }

    val program: IO[Unit] =
      for {
        _ <- ioa
        _ <- ioa
      } yield ()

    program.unsafeRunSync()
    //=> hey!
    //=> hey!
    ()

    // doesnt work with future
    import scala.concurrent.ExecutionContext.Implicits.global
    val ioa2 = Future { println("hey!") }

    val program2: Future[Unit] =
      for {
        _ <- ioa2
        _ <- ioa2
      } yield ()

//    program2.unsafeRunSync()
    //=> hey!
    //=> hey!


    val eff2 = IO.pure(25).flatMap(n => IO(println(s"Number is: $n")))
        val program3: IO[Unit] =
          for {
            _ <- eff2
            _ <- eff2
          } yield ()
    program3.unsafeRunSync()
//    val eff3 = IO.pure(25).flatMap{n => IO{println(s"Number is: $n");n} }
//    val program4: IO[Int] =
//      for {
//        _ <- eff3
//        _ <- eff3
//      } yield ()
//    program4.unsafeRunSync()

    //Synchronous Effects — IO.apply

    def putStrlLn(value: String) = IO(println(value))
    val readLn = IO(scala.io.StdIn.readLine)
    val program0: IO[Unit] = for {
      _ <- putStrlLn("What's your name?")
      n <- readLn
      _ <- putStrlLn(s"Hello, $n!")
    } yield ()
    program0.unsafeRunSync()

    val input = IO(Option("helloworld"))
    val program11: IO[Option[String]] = for {
      in <- input
    } yield (in)
    val result = program11.unsafeRunSync()

    IO.fromFuture(IO {
      Future(println("I come from the Future!"))
    })
    val f = Future.successful("I come from the Future!")

    IO.fromFuture(IO.pure(f))
    val input22 = IO.pure(Future("helloworld"))


//    val input2 = IO.fromFuture(IO(Future(Option("helloworld"))))
    val input2 = IO.fromFuture(IO(Future(Option("helloworld"))))
    val program12: IO[Option[String]] = for {
      in <- input2
    } yield (in)
    val res : Option[String] = program12.unsafeRunSync()

    res match {
      case Some(value) => value
      case None => "error"
    }

    import cats.data.OptionT
//    import cats.std.future._
    val program13 = for {
        in    <- OptionT(input2)
      } yield in
    //val res = program13.unsafeRunSync()

    val never: IO[Nothing] = IO.async(_ => ())

    //Stream

    import cats._, cats.data._, cats.implicits._
    val value: Stream[IO, Int] = Stream.eval(IO {
      println("BEING RUN!!"); 1 + 1
    })
    val eff = value

    //eff.run

    //val err = Stream.raiseError[IO,Exception](new Exception("oh noes!"))
    val count = new java.util.concurrent.atomic.AtomicLong(0)

    val acquire : IO[Unit] = IO {
      println("incremented: " + count.incrementAndGet); ()
    }
    val release = IO { println("decremented: " + count.decrementAndGet); () }
    Stream.bracket(acquire)(_ => release).flatMap(_ => Stream(1,2,3) ++ err).compile.drain.unsafeRunSync()

    // IO
    trait Connection {
      def readBytes(onSuccess: Array[Byte] => Unit, onFailure: Throwable => Unit): Unit

      // or perhaps
      def readBytesE(onComplete: Either[Throwable,Array[Byte]] => Unit): Unit =
        readBytes(bs => onComplete(Right(bs)), e => onComplete(Left(e)))

      override def toString = "<connection>"
    }
    val c = new Connection {
      def readBytes(onSuccess: Array[Byte] => Unit, onFailure: Throwable => Unit): Unit = {
        Thread.sleep(200)
        onSuccess(Array(0,1,2))
      }
    }

    c.readBytes(input => println(input.toList), err => println(err))
    // c: Connection = <connection>

    val bytes = cats.effect.Async[IO].async[Array[Byte]] { (cb: Either[Throwable,Array[Byte]] => Unit) =>
      c.readBytesE(cb)
    }
    // bytes: cats.effect.IO[Array[Byte]] = IO$1064869761
    import cats.Monad
    import cats.effect.Sync
    Stream.eval(bytes).map(_.toList).compile.toVector.unsafeRunSync()


  }

  "Scala with Cats" should "work" in {
    import cats.instances.string._ // for Monoid
    import cats.syntax.semigroup._ // for |+|
    import cats.kernel.Monoid
    import cats.effect.IO
    import fs2.Stream
    val intResult = 1 |+| 2 |+| Monoid[Int].empty

    trait Monad2[F[_]] {
      def pure[A](a: A): F[A]
      def flatMap[A, B](value: F[A])(func: A => F[B]): F[B]
      def map[A, B](value: F[A])(func: A => B): F[B] = flatMap(value)(a => pure(func(a)) )
    }

    import cats.Monad

    val fm = Monad[Future]

    val future = fm.flatMap(fm.pure(1))(x => fm.pure(x + 2))
    Await.result(future, 1.second)


  }

  "Semi groups" should "work" in {
    import cats.implicits._
    Semigroup[Int].combine(1, 2)
    Semigroup[List[Int]].combine(List(1, 2, 3), List(4, 5, 6))
    Semigroup[Option[Int]].combine(Option(1), Option(2))
    Semigroup[Option[Int]].combine(Option(1), None)
  }


  "Call-by-value" should "work" in {
    def time() = {
      println("1 Entered time() ...")
      System.nanoTime
    }

    // `t` is now defined as a by-name parameter
    def exec(t: Long) = {
      println("2 Entered exec, calling t ...")
      println("3 t = " + t)
      println("4 Calling t again ...")
      t
    }

    println(exec(time()))

  }

  "Difference between Function0 vs by-name parameters" should "work sometimes" in {
    // https://stackoverflow.com/questions/46185458/scala-function0-vs-by-name-parameters
    def value: Int = ???
    def method(): Int = ???

    def f1(f: () => Int) = ???
    def f2(f: => Int) = ???

    //f1(value)  // fails
    f1(method) // works
    f2(value)  // works
    f2(method) // works with a warning "empty-paren method accessed as parameterless"

    def f(): Int = ???
    val a = f               // no function context; a is a string
    val b: () => Int = f    // b is a function Unit => Int
    val c = f2 _            // c is a function Unit => Int
  }

  "Call-by-name" should "work" in {
    def time() = {
      println("2 Entered time() ...")
      System.nanoTime
    }

    // `t` is now defined as a by-name parameter
    def exec(t: => Long) = {
      println("1 Entered exec, calling t ...")
      println("3 t = " + t)
      println("4 Calling t again ...")
      t
    }

    println(exec(time()))

  }

  "Call-by-name2" should "work" in {

    ((n : Long) => (n2 : Long)  => n + n2 : Long)(1)(2)

    def time() = {
      println("2 Entered time() ...")
//      val f : (Long => Long => Long ) =
        n : Long => n2 : Long => n + n2

    }

    // `t` is now defined as a by-name parameter
    def exec(t: => Long => Long => Long ) = {
      println("1 Entered exec, calling t ...")
      println("3 t = " + t)
      println("4 Calling t again ...")
      t(5)(5)
    }

    println(exec(time()))

  }


  "Scratch Pad" should "work" in {
    //import cats._, cats.data._, cats.implicits._





    assert(42 === 42)

  }

}
