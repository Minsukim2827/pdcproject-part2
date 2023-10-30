package pdc.project.part2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.border.EmptyBorder;

public class LoginWindow extends JFrame {

    private JTextField customerID;
    private DBManager dbManager;

    public LoginWindow() {
        super("Banking System - Login");

        dbManager = new DBManager();

        setupUI();
    }

    // Method to setup the UI
private void setupUI() {
    JPanel loginPanel = new JPanel(new GridBagLayout()) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            int w = getWidth();
            int h = getHeight();
            GradientPaint gp = new GradientPaint(0, 0, Color.DARK_GRAY, w, h, Color.ORANGE);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
        }
    };
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);

    loginPanel.setBorder(BorderFactory.createEmptyBorder(-20, 0, 0, 0));

    setupHeadingLabel(loginPanel, gbc);
    setupInputPanel(loginPanel, gbc);
    setupBackButton(loginPanel, gbc);

    setContentPane(loginPanel);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(600, 400);
    setLocationRelativeTo(null);
}

    // Method to setup the heading label
    private void setupHeadingLabel(JPanel loginPanel, GridBagConstraints gbc) {
        JLabel headingLabel = new JLabel("ASF BANKING");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        loginPanel.add(headingLabel, gbc);
        gbc.gridwidth = 1;
    }

    // Method to setup the input panel
private void setupInputPanel(JPanel loginPanel, GridBagConstraints gbc) {
    JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
    JLabel customerIDLabel = new JLabel("Customer ID:");
    customerID = new JTextField(15);
    inputPanel.add(customerIDLabel);
    inputPanel.add(customerID);

    JButton loginButton = new JButton("Login");
    loginButton.addActionListener(this::login);
    loginButton.setBackground(Color.GREEN); // Set button color to green
    inputPanel.add(loginButton);

    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    loginPanel.add(inputPanel, gbc);
}

    // Method to setup the back button
private void setupBackButton(JPanel loginPanel, GridBagConstraints gbc) {
    JButton backButton = new JButton("Back");
    backButton.addActionListener(e -> openScreen(new SplashScreen()));
    backButton.setBackground(Color.GREEN); // Set button color to green

    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    gbc.fill = GridBagConstraints.CENTER;
    loginPanel.add(backButton, gbc);
}

    // Method to handle login
    private void login(ActionEvent e) {
        String customerIDText = customerID.getText();

        try {
            int customerID = Integer.parseInt(customerIDText);
            Customer customer = dbManager.getCustomerById(customerID); // get customer from database;
            if (customer != null) {
                openScreen(new HomeScreen(customer));
            } else {
                JOptionPane.showMessageDialog(LoginWindow.this, "Invalid customer ID.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(LoginWindow.this, "Invalid customer ID format.");
        }
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
