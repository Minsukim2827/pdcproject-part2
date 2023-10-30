package pdc.project.part2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.time.LocalDate;
import java.util.Date;

public class DepositScreen extends JFrame {
    private Customer customer;
    private JTextField depositField = new JTextField(20);
    private JButton depositButton = new JButton("Deposit");
    private JButton returnButton = new JButton("Return");

    public DepositScreen(Customer customer) {
        super("Banking System - Deposit Funds");
        this.customer = customer;
        setupUI();
    }

    // Method to setup UI
    private void setupUI() {
        setSize(600, 400);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setInsets(gbc, 10, 10, 10, 10);

        JLabel customerDeposit = new JLabel("How much would you like to deposit?");

        setupDepositButton();
        setupReturnButton();

        addComponentsToLayout(gbc, customerDeposit);
    }

    // Method to setup Deposit Button
    private void setupDepositButton() {
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                depositFunds();
            }
        });
    }

    // Method to setup Return Button
    private void setupReturnButton() {
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToHomeScreen();
            }
        });
    }

    // Method to deposit funds
    private void depositFunds() {
        String depositText = depositField.getText();
        try {
            double depositAmount = Double.parseDouble(depositText);
            if (depositAmount > 0) {
                // Update the balance in the Account object
                BankAccount account = customer.getBankAccount(); // assuming that the Customer class has a method to get the account
                account.depositMoney(depositAmount); // assuming that the Account class has a deposit method

                // Update the balance in the database
                DBManager dbManager = new DBManager();
                dbManager.updateAccountBalance(account.getAccountId(), account.getAccountBalance());

                // Create a new Transaction object and add it to the transaction history
                Transaction transaction = new Transaction("Deposit", depositAmount);
                account.addTransaction(transaction); // assuming that the Account class has a method to add a transaction

                // Insert a new row in the BANK_TRANSACTION table in the database
                dbManager.insertTransaction(transaction, customer);

                JOptionPane.showMessageDialog(DepositScreen.this, "Deposit successful.");
            } else {
                JOptionPane.showMessageDialog(DepositScreen.this, "Invalid deposit amount.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(DepositScreen.this, "Invalid deposit amount format.");
        }
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

    // Method to add components to layout
    private void addComponentsToLayout(GridBagConstraints gbc, JLabel customerDeposit) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(customerDeposit, gbc);

        gbc.gridx = 1;
        add(depositField, gbc);

        gbc.gridy = 1;
        add(depositButton, gbc);
        gbc.gridy = 2;
        add(returnButton, gbc);
    }

    private void setInsets(GridBagConstraints gbc, int top, int left, int bottom, int right) {
        gbc.insets = new Insets(top, left, bottom, right);
    }
}
