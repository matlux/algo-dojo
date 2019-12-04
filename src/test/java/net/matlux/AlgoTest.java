package net.matlux;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class AlgoTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown()  {
    }



    @Test
    public void testTest() {

        assertThat(42,is(42));
    }
}
