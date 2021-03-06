package jmri.jmrix.mrc.configurexml;

import jmri.util.JUnitUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * MrcTurnoutManagerXmlTest.java
 *
 * Test for the MrcTurnoutManagerXml class
 *
 * @author   Paul Bender  Copyright (C) 2016
 */
public class MrcTurnoutManagerXmlTest {

    @Test
    public void testCtor(){
      Assert.assertNotNull("MrcTurnoutManagerXml constructor",new MrcTurnoutManagerXml());
    }

    @Before
    public void setUp() {
        JUnitUtil.setUp();
    }

    @After
    public void tearDown() {
        JUnitUtil.tearDown();
    }

}

