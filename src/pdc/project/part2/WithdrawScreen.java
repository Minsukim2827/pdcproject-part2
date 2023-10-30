package pdc.project.part2;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class WithdrawScreen extends JFrame {
    private Customer customer;
    private DBManager dbManager;
    private JTextField withdrawField;
    private JPanel mainPanel;

    public WithdrawScreen(Customer customer) {
        super("Banking System - Withdraw Funds");
        this.customer = customer;
        dbManager = new DBManager();

        setupUI();
    }

    // Method to setup the UI
    private void setupUI() {
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new GridBagLayout()) {
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
        setInsets(gbc, 10, 10, 10, 10);

        setupWithdrawField(gbc);
        setupButtons(gbc);

        add(mainPanel);
    }

    // Method to setup the withdraw field
    private void setupWithdrawField(GridBagConstraints gbc) {
        JLabel customerWithdraw = new JLabel("How much would you like to withdraw?");
        withdrawField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(customerWithdraw, gbc);

        gbc.gridx = 1;
        mainPanel.add(withdrawField, gbc);
    }

    // Method to setup the buttons
    private void setupButtons(GridBagConstraints gbc) {
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(this::withdraw);
        withdrawButton.setBackground(Color.GREEN); // Set button color to green

        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(e -> openScreen(new HomeScreen(customer)));
        returnButton.setBackground(Color.GREEN); // Set button color to green

        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(withdrawButton, gbc);

        gbc.gridy = 2;
        mainPanel.add(returnButton, gbc);
    }

    // Method to handle withdrawal
    private void withdraw(ActionEvent e) {
        String withdrawText = withdrawField.getText();
        try {
            double withdrawAmount = Double.parseDouble(withdrawText);
            BankAccount account = customer.getBankAccount(); // assuming that the Customer class has a method to get the account

            // Withdraw money from the account
            account.withdrawMoney(withdrawAmount);

            // Update the balance in the database
            dbManager.updateAccountBalance(account.getAccountId(), account.getAccountBalance());

            // Create a new Transaction object and add it to the transaction history
            Transaction transaction = new Transaction("Withdrawal", withdrawAmount);
            account.addTransaction(transaction); // assuming that the Account class has a method to add a transaction

            // Insert a new row in the BANK_TRANSACTION table in the database
            dbManager.insertTransaction(transaction, customer);

            JOptionPane.showMessageDialog(WithdrawScreen.this, "Withdrawal successful.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(WithdrawScreen.this, "Invalid withdrawal amount format.");
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
