package net.matlux.java.algo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RansomArticleTest {

    String FIXTURE_RANSOM = "We have Jeremy with us. We will release him if you can send us two thousand bitcoin";
    String FIXTURE_ARTICLE_TRUE = "Jeremy is a little boy would loves playing in the garden.\n" +
        "He gives us lots of joy and he plays with two thousand games in the which we bought with bitcoin.\n" +
        "Jeremy and his sister have a lot of release toys we cannot find in the us.\n" +
        "if We have you we can and will send him a letter.";
    String FIXTURE_ARTICLE_FALSE = "Jeremy is a little boy would loves playing in the garden.";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void matchesWithTrue() throws Exception {
        RansomArticle ransomArticle = new RansomArticle();
        assertTrue(ransomArticle.matchesWith(FIXTURE_RANSOM,FIXTURE_ARTICLE_TRUE));
        //assertTrue(ransomArticle.matchesWith(FIXTURE_RANSOM,FIXTURE_ARTICLE_TRUE));
    }
    @Test
    public void matchesWithFalse() throws Exception {
        RansomArticle ransomArticle = new RansomArticle();
        assertFalse(ransomArticle.matchesWith(FIXTURE_RANSOM,FIXTURE_ARTICLE_FALSE));
    }

}