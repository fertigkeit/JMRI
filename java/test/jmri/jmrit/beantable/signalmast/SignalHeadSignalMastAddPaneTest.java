package jmri.jmrit.beantable.signalmast;

import jmri.*;
import jmri.implementation.*;
import jmri.util.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Bob Jacobsen Copyright 2018
 */
public class SignalHeadSignalMastAddPaneTest extends AbstractSignalMastAddPaneTestBase {

    /** {@inheritDoc} */
    @Override
    protected SignalMastAddPane getOTT() { return new SignalHeadSignalMastAddPane(); }    
    
    @Test
    public void testSetMast() {
        InstanceManager.getDefault(jmri.SignalHeadManager.class).register(
                new DefaultSignalHead("IH1") {
                    @Override
                    protected void updateOutput() {
                    }
                }
        );
        SignalHeadSignalMast s1 = new SignalHeadSignalMast("IF$shsm:basic:one-searchlight:IH1", "user name");
        MatrixSignalMast m1 = new MatrixSignalMast("IF$xsm:basic:one-low($0001)-3t", "user");

        SignalHeadSignalMastAddPane vp = new SignalHeadSignalMastAddPane();
        
        Assert.assertTrue(vp.canHandleMast(s1));
        Assert.assertFalse(vp.canHandleMast(m1));
        
        vp.setMast(null);
        
        vp.setAspectNames(s1.getAppearanceMap(), InstanceManager.getDefault(jmri.SignalSystemManager.class).getSystem("basic"));
        vp.setMast(s1);
        
        vp.setAspectNames(m1.getAppearanceMap(), InstanceManager.getDefault(jmri.SignalSystemManager.class).getSystem("basic"));
        vp.setMast(m1);

        JUnitAppender.assertErrorMessage("mast was wrong type: IF$xsm:basic:one-low($0001)-3t jmri.implementation.MatrixSignalMast");
    }

    @Before
    @Override
    public void setUp() {
        JUnitUtil.setUp();
        JUnitUtil.initDefaultUserMessagePreferences();
        JUnitUtil.initInternalSignalHeadManager();
    }

    @After
    @Override
    public void tearDown() {
        JUnitUtil.tearDown();
    }
}
