package net.matlux.scala.algo

import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}

class RansomArticleTest extends FlatSpec with Matchers with BeforeAndAfterEach {



  override def beforeEach() {

  }

  override def afterEach() {

  }

  val FIXTURE_RANSOM = "We have Jeremy with us. We will release him if you can send us two thousand bitcoin"
  val FIXTURE_ARTICLE_TRUE = """Jeremy is a little boy would loves playing in the garden.
He gives us lots of joy and he plays with two thousand games in the which we bought with bitcoin.
                             Jeremy and his sister have a lot of release toys we cannot find in the us.
                             if We have you we can and will send him a letter."""
  val FIXTURE_ARTICLE_FALSE = """Jeremy is a little boy would loves playing in the garden."""

  "ransom" should "find words in article" in {

    assert(RansomArticle.matchesWith(FIXTURE_RANSOM,FIXTURE_ARTICLE_TRUE) === true)
  }

  "ransom" should "not find enough words in article" in {

    assert(RansomArticle.matchesWith(FIXTURE_RANSOM,FIXTURE_ARTICLE_FALSE) === false)
  }

}
