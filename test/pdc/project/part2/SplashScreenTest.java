package pdc.project.part2;

import org.junit.Test;
import org.junit.Before;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class SplashScreenTest {
    private SplashScreen splashScreen;
    private JPanel panel;

    @Before
    public void setUp() {
        splashScreen = new SplashScreen();
        panel = new JPanel();
    }

    @Test
    public void testAddButton() throws Exception {
        Method addButton = SplashScreen.class.getDeclaredMethod("addButton", JPanel.class, String.class, ActionListener.class, GridBagConstraints.class, int.class, int.class);
        addButton.setAccessible(true);

        int initialComponentCount = panel.getComponentCount();
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        };
        addButton.invoke(splashScreen, panel, "Test", actionListener, new GridBagConstraints(), 0, 0);
        int finalComponentCount = panel.getComponentCount();

        //check that a component was added to the panel
        assertEquals(initialComponentCount + 1, finalComponentCount);

        //check that the added component is a JButton
        assertTrue(panel.getComponent(initialComponentCount) instanceof JButton);

        //check that the button has the correct text
        JButton button = (JButton) panel.getComponent(initialComponentCount);
        assertEquals("Test", button.getText());
    }
}
