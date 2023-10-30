package pdc.project.part2;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.sql.*;

public class NewAccountScreen extends JFrame {

    private DBManager dbManager;
    private JTextField nameField;
    private JTextField addressField;
    private JTextField phoneField;
    private JComboBox<String> accountTypeComboBox;

    public NewAccountScreen() {
        super("Banking System - New Account");
        dbManager = new DBManager();

        setupUI();
    }

    // Method to setup the UI
private void setupUI() {
    setTitle("New Account Creation");
    setSize(600, 400);
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    setInsets(gbc, 10, 10, 10, 10);

    // Apply gradient to the background
    JPanel gradientPanel = new JPanel() {
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
    gradientPanel.setLayout(new GridBagLayout());
    setContentPane(gradientPanel);

    setupInputFields(gbc);
    setupButtons(gbc);
}

    // Method to setup the input fields
    private void setupInputFields(GridBagConstraints gbc) {
        nameField = setupField("Name:", gbc, 0);
        addressField = setupField("Address:", gbc, 1);
        phoneField = setupField("Phone Number:", gbc, 2);

        JLabel accountTypeLabel = new JLabel("Account Type:");
        String[] accountTypes = {"Student Account", "Business Account", "Savings Account"};
        accountTypeComboBox = new JComboBox<>(accountTypes);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(accountTypeLabel, gbc);

        gbc.gridx = 1;
        add(accountTypeComboBox, gbc);
    }

    // Method to setup a field
    private JTextField setupField(String labelText, GridBagConstraints gbc, int gridy) {
        JLabel label = new JLabel(labelText);
        JTextField field = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = gridy;
        add(label, gbc);

        gbc.gridx = 1;
        add(field, gbc);

        return field;
    }

    // Method to setup the buttons
private void setupButtons(GridBagConstraints gbc) {
    JButton createButton = new JButton("Create Account");
    createButton.addActionListener(this::createAccount);
    createButton.setBackground(Color.GREEN); // Set button color to green

    JButton returnButton = new JButton("Return");
    returnButton.addActionListener(e -> openScreen(new SplashScreen()));
    returnButton.setBackground(Color.GREEN); // Set button color to green

    gbc.gridx = 1;
    gbc.gridy = 4;
    add(createButton, gbc);

    gbc.gridy = 5;
    add(returnButton, gbc);
}

    // Method to handle account creation
    protected void createAccount(ActionEvent e) {
        String name = nameField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        String selectedAccountType = (String) accountTypeComboBox.getSelectedItem();

        Customer newCustomer = new Customer(name, address, phone);
        BankAccount newAccount = createAccount(selectedAccountType);
        newCustomer.setBankAccount(newAccount);

        dbManager.insertCustomerAndAccount(newCustomer, newAccount);

        String message = "Name: " + name + "\nAddress: " + address + "\nPhone: " + phone + "\nAccount Type: " + selectedAccountType + "\nCustomer ID: " + newCustomer.getCustomerId(); // Put in customer ID in here @minsu
        JOptionPane.showMessageDialog(NewAccountScreen.this, message);
    }

    // Method to create a new account
    private BankAccount createAccount(String selectedAccountType) {
        switch (selectedAccountType) {
            case "Student Account":
                return new StudentAccount(0.0); // initial balance is 0.0
            case "Business Account":
                return new BusinessAccount(0.0); // initial balance is 0.0
            case "Savings Account":
                return new SavingsAccount(1000.0); // initial balance is 0.0
            default:
                throw new IllegalArgumentException("Invalid account type: " + selectedAccountType);
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

    private void setInsets(GridBagConstraints gbc, int top, int left, int bottom, int right) {
        gbc.insets = new Insets(top, left, bottom, right);
    }
}
