package pdc.project.part2;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.*;

public class DisplayDetailsScreen extends JFrame {
    public DisplayDetailsScreen() {
        super("Banking System - Display Details");
        setTitle("Display Details");
        setSize(600, 400);
        GridBagConstraints gbc = new GridBagConstraints();
        setInsets(gbc, 10, 10, 10, 10);
    }
    
    private void setInsets(GridBagConstraints gbc, int top, int left, int bottom, int right) {
        gbc.insets = new Insets(top, left, bottom, right);
    }
}
