package pdc.project.part2;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

public class DisplayDetailsScreen extends JFrame {
    private Customer customer;
    private JTextArea detailsArea = new JTextArea();
    private JButton backButton = new JButton("Back");
    private JPanel mainPanel;

    public DisplayDetailsScreen(Customer customer) {
        super("Banking System - Display Details");
        this.customer = customer;
        setupUI();
    }

    // Method to setup UI
private void setupUI() {
    setTitle("Display Details");
    setSize(600, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBackground(Color.WHITE); // Set main panel background to white

    detailsArea.setEditable(false);
    detailsArea.setText(getDetails());
    detailsArea.setOpaque(false); // Make JTextArea transparent

    // Wrap the JTextArea in a JScrollPane
    JScrollPane scrollPane = new JScrollPane(detailsArea);
    scrollPane.setOpaque(false); // Make JScrollPane transparent
    scrollPane.getViewport().setOpaque(false); // Make JScrollPane's viewport transparent

    setupBackButton();

    // Create a new JPanel for the button and set its background to dark grey
    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(Color.DARK_GRAY);
    buttonPanel.add(backButton);

    mainPanel.add(scrollPane, BorderLayout.CENTER);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    add(mainPanel);
}

// Method to setup Back Button
private void setupBackButton() {
    backButton.setPreferredSize(new Dimension(100, 20)); // Set button size
    backButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            returnToHomeScreen();
        }
    });
    backButton.setBackground(Color.GREEN); // Set button color to green
}


    // Method to return to home screen
    private void returnToHomeScreen() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                HomeScreen homeScreen = new HomeScreen(customer);
                homeScreen.setLocation(getLocation());
                homeScreen.setVisible(true);
            }
        });
        dispose();
    }

    // Method to get details
    protected String getDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Customer Details:\n");
        details.append("Name: " + customer.getCustomerName() + "\n");
        details.append("Address: " + customer.getAddress() + "\n");
        details.append("Phone Number: " + customer.getPhoneNumber() + "\n\n");

        BankAccount account = customer.getBankAccount();
        details.append("Bank Account Details:\n");
        details.append("Account Type: " + account.getAccountType() + "\n");
        details.append("Balance: " + String.format("%.2f",account.getAccountBalance()) + "\n");
        details.append("Interest Rate: " + account.getInterestRate() + "\n\n");

        details.append("Transaction History:\n");
        for (Transaction transaction : account.getTransactionHistory()) {
            details.append("Transaction Type: " + transaction.getTransactionType() + "\n");
            details.append("Amount: " + transaction.getAmount() + "\n");
            details.append("Date: " + transaction.getDate() + "\n\n");
        }

        return details.toString();
    }

    private void setInsets(GridBagConstraints gbc, int top, int left, int bottom, int right) {
        gbc.insets = new Insets(top, left, bottom, right);
    }
}
