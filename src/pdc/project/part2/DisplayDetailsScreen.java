package pdc.project.part2;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class DisplayDetailsScreen extends JFrame {
    private Customer customer;
    private JTextArea detailsArea = new JTextArea();
    private JButton backButton = new JButton("Back");

    public DisplayDetailsScreen(Customer customer) {
        super("Banking System - Display Details");
        this.customer = customer;
        setupUI();
    }

    // Method to setup UI
    private void setupUI() {
        setTitle("Display Details");
        setSize(600, 400);
        GridBagConstraints gbc = new GridBagConstraints();
        setInsets(gbc, 10, 10, 10, 10);

        detailsArea.setEditable(false);
        detailsArea.setText(getDetails());

        // Wrap the JTextArea in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(detailsArea);
        add(scrollPane);

        setupBackButton();

        add(backButton, BorderLayout.SOUTH);
    }

    // Method to setup Back Button
    private void setupBackButton() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToHomeScreen();
            }
        });
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
    private String getDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Customer Details:\n");
        details.append("Name: " + customer.getCustomerName() + "\n");
        details.append("Address: " + customer.getAddress() + "\n");
        details.append("Phone Number: " + customer.getPhoneNumber() + "\n\n");

        BankAccount account = customer.getBankAccount();
        details.append("Bank Account Details:\n");
        details.append("Account Type: " + account.getAccountType() + "\n");
        details.append("Balance: " + account.getAccountBalance() + "\n");
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
