package jmri.jmrit.beantable;

import apps.gui.GuiLafPreferencesManager;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import jmri.InstanceManager;
import jmri.Logix;
import jmri.jmrit.beantable.LogixTableAction;

import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JMenuBarOperator;
import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.netbeans.jemmy.operators.JMenuOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
* Tests for the LogixTableAction Class
* Re-created using JUnit4 with support for the new conditional editors
* @author Dave Sand Copyright (C) 2017
*/
public class LogixTableActionTest extends AbstractTableActionBase {

    @Test
    public void testCtor() {
        Assert.assertNotNull("LogixTableActionTest Constructor Return", new LogixTableAction());  // NOI18N
    }

    @Test
    public void testStringCtor() {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        Assert.assertNotNull("LogixTableActionTest Constructor Return", new LogixTableAction("test"));  // NOI18N
    }

    @Override
    public String getTableFrameName() {
        return Bundle.getMessage("TitleLogixTable");  // NOI18N
    }

    @Override
    @Test
    public void testGetClassDescription() {
         Assert.assertEquals("Logix Table Action class description", Bundle.getMessage("TitleLogixTable"), a.getClassDescription());  // NOI18N
    }

    /**
     * Check the return value of includeAddButton.
     * <p>
     * The table generated by this action includes an Add Button.
     */
    @Override
    @Test
    public void testIncludeAddButton() {
         Assert.assertTrue("Default include add button", a.includeAddButton());  // NOI18N
    }

    @Test
    public void testLogixBrowser() {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        LogixTableAction lgxTable = (LogixTableAction) a;

        lgxTable.browserPressed("IX101");  // NOI18N

        JFrame frame = JFrameOperator.waitJFrame(LogixTableAction.rbx.getString("BrowserTitle"), true, true);  // NOI18N
        Assert.assertNotNull(frame);
        frame.dispose();
    }
    
    @Test
    public void testWhereused() {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        LogixTableAction lgxTable = (LogixTableAction) a;

        lgxTable.makeWhereUsedWindow();

        JFrame frame = JFrameOperator.waitJFrame(LogixTableAction.rbx.getString("DisplayWhereUsed"), true, true);  // NOI18N
        Assert.assertNotNull(frame);
        frame.dispose();
    }

    @Test
    public void testListEditor() {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        LogixTableAction lgxTable = (LogixTableAction) a;

        lgxTable.editPressed("IX101");  // NOI18N

//         JFrameOperator frame = new JFrameOperator(LogixTableAction.rbx.getString("TitleEditLogix"), true, true);  // NOI18N
        JFrameOperator frame = new JFrameOperator(LogixTableAction.rbx.getString("TitleEditLogix"));  // NOI18N
        Assert.assertNotNull(frame);
        frame.dispose();
    }

    @Test
    public void testAddLogix() {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        LogixTableAction lgxTable = (LogixTableAction) a;

        lgxTable.actionPerformed(null); // show table
        JFrame lgxFrame = JFrameOperator.waitJFrame(Bundle.getMessage("TitleLogixTable"), true, true);  // NOI18N
        Assert.assertNotNull("Found Logix Frame", lgxFrame);  // NOI18N

        lgxTable.addPressed(null);
        JFrameOperator addFrame = new JFrameOperator(LogixTableAction.rbx.getString("TitleAddLogix"));  // NOI18N
        Assert.assertNotNull("Found Add Logix Frame", addFrame);  // NOI18N

        new JTextFieldOperator(addFrame, 0).setText("104");  // NOI18N
        new JTextFieldOperator(addFrame, 1).setText("Logix 104");  // NOI18N
        new JButtonOperator(addFrame, Bundle.getMessage("ButtonCreate")).push();  // NOI18N

        Logix chk104 = jmri.InstanceManager.getDefault(jmri.LogixManager.class).getLogix("Logix 104");  // NOI18N
        Assert.assertNotNull("Verify IX104 Added", chk104);  // NOI18N

        // Add creates an edit frame; find and dispose
        JFrame editFrame = JFrameOperator.waitJFrame(LogixTableAction.rbx.getString("TitleEditLogix"), true, true);  // NOI18N
        editFrame.dispose();

        lgxFrame.dispose();
    }

    @Test
    public void testDeleteLogix() {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        LogixTableAction lgxTable = (LogixTableAction) a;

        lgxTable.actionPerformed(null); // show table
        JFrame lgxFrame = JFrameOperator.waitJFrame(Bundle.getMessage("TitleLogixTable"), true, true);  // NOI18N
        Assert.assertNotNull("Found Logix Frame", lgxFrame);  // NOI18N

        // Delete IX102, respond No
        createModalDialogOperatorThread(LogixTableAction.rbx.getString("ConfirmTitle"), Bundle.getMessage("ButtonNo"));  // NOI18N
        lgxTable.deletePressed("IX102");  // NOI18N
        Logix chk102 = jmri.InstanceManager.getDefault(jmri.LogixManager.class).getBySystemName("IX102");  // NOI18N
        Assert.assertNotNull("Verify IX102 Not Deleted", chk102);  // NOI18N

        // Delete IX103, respond Yes
        createModalDialogOperatorThread(LogixTableAction.rbx.getString("ConfirmTitle"), Bundle.getMessage("ButtonYes"));  // NOI18N
        lgxTable.deletePressed("IX103");  // NOI18N
        Logix chk103 = jmri.InstanceManager.getDefault(jmri.LogixManager.class).getBySystemName("IX103");  // NOI18N
        Assert.assertNull("Verify IX103 Is Deleted", chk103);  // NOI18N

        lgxFrame.dispose();
    }

    void createModalDialogOperatorThread(String dialogTitle, String buttonText) {
        new Thread(() -> {
            // constructor for jdo will wait until the dialog is visible
            JDialogOperator jdo = new JDialogOperator(dialogTitle);
            JButtonOperator jbo = new JButtonOperator(jdo, buttonText);
            jbo.pushNoBlock();
        }).start();
    }

    @Before
    @Override
    public void setUp() {
        apps.tests.Log4JFixture.setUp();
        jmri.util.JUnitUtil.resetInstanceManager();
        jmri.util.JUnitUtil.initLogixManager();
        jmri.util.JUnitUtil.initDefaultUserMessagePreferences();

        Logix x1 = InstanceManager.getDefault(jmri.LogixManager.class).createNewLogix("IX101", "Logix 101");  // NOI18N
        Logix x2 = InstanceManager.getDefault(jmri.LogixManager.class).createNewLogix("IX102", "Logix 102");  // NOI18N
        Logix x3 = InstanceManager.getDefault(jmri.LogixManager.class).createNewLogix("IX103", "Logix 103");  // NOI18N

        a = new LogixTableAction();
    }

    @After
    @Override
    public void tearDown() {
        a = null;
        jmri.util.JUnitUtil.resetInstanceManager();
        apps.tests.Log4JFixture.tearDown();
    }
}
