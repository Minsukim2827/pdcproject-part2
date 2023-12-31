package pdc.project.part2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.*;


public class SplashScreen extends JFrame {

    public SplashScreen() {
        setTitle("ASF Banking");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setupUI();
    }

// Method to setup the UI
private void setupUI() {
    JPanel menuPanel = new JPanel(new GridBagLayout()) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            Color color1 = Color.DARK_GRAY;
            Color color2 = Color.ORANGE;
            int w = getWidth();
            int h = getHeight();
            GradientPaint gp = new GradientPaint(
                0, 0, color1, w, h, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
        }
    };
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);

    setupHeadingLabel(menuPanel, gbc);
    setupButtons(menuPanel, gbc);
    setupExitButton();

    setLocationRelativeTo(null);
}


    // Method to setup the heading label
private void setupHeadingLabel(JPanel menuPanel, GridBagConstraints gbc) {
    JLabel headingLabel = new JLabel("Welcome to ASF Banking");
    headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
    headingLabel.setBorder(BorderFactory.createCompoundBorder(new DropShadowBorder(), BorderFactory.createEmptyBorder(30, 0, 0, 0)));
    headingLabel.setForeground(Color.WHITE); // Set title color to white

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.fill = GridBagConstraints.CENTER;
    menuPanel.add(headingLabel, gbc);
}

    // Method to setup the buttons
    private void setupButtons(JPanel menuPanel, GridBagConstraints gbc) {
        addButton(menuPanel, "See Current Accounts", e -> openScreen(new CurrentAccountScreen()), gbc, 1, 0);
        addButton(menuPanel, "Add a New Account", e -> openScreen(new NewAccountScreen()), gbc, 1, 1);
        addButton(menuPanel, "Delete an Account", e -> openScreen(new DeleteAccountScreen()), gbc, 2, 0);
        addButton(menuPanel, "Log into an Account", e -> openScreen(new LoginWindow()), gbc, 2, 1);

        add(menuPanel, BorderLayout.CENTER);
    }

    // Method to add a button to the panel
private void addButton(JPanel panel, String text, ActionListener action, GridBagConstraints gbc, int gridy, int gridx) {
    JButton button = new JButton(text);
    button.addActionListener(action);
    button.setBackground(Color.GREEN); // Set button color to green
    button.setBorder(new DropShadowBorder());

    gbc.gridwidth = 1;
    gbc.gridy = gridy;
    gbc.gridx = gridx;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    panel.add(button, gbc);
}

    // Method to setup the exit button
    private void setupExitButton() {
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        exitButton.setBackground(Color.GREEN); // Set button color to green

        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        exitPanel.add(exitButton);
        exitPanel.setBackground(Color.DARK_GRAY); // Set background color to dark gray

        add(exitPanel, BorderLayout.SOUTH);
    }

    // Method to open a new screen
    private void openScreen(JFrame screen) {
        SwingUtilities.invokeLater(() -> {
            screen.setLocation(getLocation());
            screen.setVisible(true);
        });
        dispose();
    }
}
