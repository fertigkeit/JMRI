package jmri.jmrix.loconet.hexfile.configurexml;

import jmri.util.JUnitUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Paul Bender Copyright (C) 2017
 */
public class LnSensorManagerXmlTest {

    @Test
    public void testCTor() {
        LnSensorManagerXml t = new LnSensorManagerXml();
        Assert.assertNotNull("exists",t);
    }

    @Before
    public void setUp() {
        JUnitUtil.setUp();
    }

    @After
    public void tearDown() {
        JUnitUtil.tearDown();
    }

    // private final static Logger log = LoggerFactory.getLogger(LnSensorManagerXmlTest.class);

}
