package net.matlux

import org.scalatest.{FlatSpec, Matchers}

class FlatSpecTest extends FlatSpec with Matchers {
  "42" should "equal to 42" in {
/*
Yanove
Alex


*/
    assert(42 === 42)
  }
}
