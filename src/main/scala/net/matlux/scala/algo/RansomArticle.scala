package net.matlux.scala.algo

object RansomArticle {
//  val ransom = FIXTURE_RANSOM
//  val article = FIXTURE_ARTICLE_TRUE
  // val lines = FIXTURE_RANSOM

  def matchesWith(ransom: String, article: String) = {


    val ransomWordCount = countWords(ransom)



    val articleWordCount = countWords(article)

    val stringToBoolean = ransomWordCount.
      map { case (w, count) => (w, articleWordCount.getOrElse(w, 0) < count) }

    stringToBoolean.filter{case (w, b) => b}.size == 0



  }

  def countWords(lines : String) = {
    lines.split(" |\\.|\\n").
      filterNot(_ == "").
      map(w => (w.toLowerCase, 1)).
      groupBy(_._1).
      mapValues(l => l.map(p => p._2).sum)
  }

  def countWords2(lines : String) = {
    lines.split(" |\\.|\\n").
      filterNot(_ == "").
      map(w => (w.toLowerCase, 1)).
      foldLeft(Map.empty[String,Int]){case (m, (k , v)) =>
        m + (k -> (m.getOrElse(k,0) + v))}
  }

}
