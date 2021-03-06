package jmri.jmrix.ieee802154.xbee.configurexml;

import jmri.util.JUnitUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * XBeeLightManagerXmlTest.java
 *
 * Test for the XBeeLightManagerXml class
 *
 * @author   Paul Bender  Copyright (C) 2016
 */
public class XBeeLightManagerXmlTest {

    @Test
    public void testCtor(){
      Assert.assertNotNull("XBeeLightManagerXml constructor",new XBeeLightManagerXml());
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

