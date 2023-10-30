package pdc.project.part2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
//import com.mycompany.comp603project.BankServiceCUI;
//import com.mycompany.comp603project.FileHandler;

public class WithdrawScreen extends JFrame {
    private Customer customer;
    private DBManager dbManager;

    public WithdrawScreen(Customer customer) {
        super("Banking System - Withdraw Funds");
        this.customer = customer;
        dbManager = new DBManager();
        setSize(600, 400);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setInsets(gbc, 10, 10, 10, 10);

        JLabel customerWithdraw = new JLabel("How much would you like to withdraw?");
        JTextField withdrawField = new JTextField(20);

        JButton withdrawButton = new JButton("Withdraw");
        JButton returnButton = new JButton("Return");

withdrawButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
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
});



        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(customerWithdraw, gbc);

        gbc.gridx = 1;
        add(withdrawField, gbc);

        gbc.gridy = 1;
        add(withdrawButton, gbc);
        gbc.gridy = 2;
        add(returnButton, gbc);
    }

    private void setInsets(GridBagConstraints gbc, int top, int left, int bottom, int right) {
        gbc.insets = new Insets(top, left, bottom, right);
    }
}

