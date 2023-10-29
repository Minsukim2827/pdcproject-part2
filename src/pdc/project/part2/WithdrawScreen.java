package pdc.project.part2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
//import com.mycompany.comp603project.BankServiceCUI;
//import com.mycompany.comp603project.FileHandler;

public class WithdrawScreen extends JFrame {

    private DBManager dbManager;

//    public WithdrawScreen() {
//        super("Banking System - Withdraw Funds");
//        dbManager = new DBManager();
//        setSize(600, 400);
//        setLayout(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        setInsets(gbc, 10, 10, 10, 10);
//
//        JLabel customerWithdraw = new JLabel("How much would you like to withdraw?");
//        JTextField withdrawField = new JTextField(20);
//
//        JButton withdrawButton = new JButton("Withdraw");
//        JButton returnButton = new JButton("Return");
//
//        withdrawButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Get the customer ID from somewhere (you need to implement this)
//                int customerId = getCustomerId();
//
//                // Get the customer and the associated bank account
//                Customer customer = dbManager.getCustomerById(customerId);
//                BankAccount account = customer.getBankAccount();
//
//                // Process the withdrawal
//                double amount = Double.parseDouble(withdrawField.getText());
//                account.withdrawMoney(amount);
//
//                // Update the transaction history
//                Transaction transaction = new Transaction("Withdrawal", amount);
//                dbManager.addTransactionToDatabase(transaction);
//
//                // Update the bank account in the database
//                dbManager.updateBankAccountInDatabase(account);
//            }
//        });
//
//        returnButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                SwingUtilities.invokeLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        SplashScreen splashscreen = new SplashScreen();
//                        splashscreen.setLocation(getLocation());
//                        splashscreen.setVisible(true);
//                    }
//                });
//                dispose();
//            }
//        });
//
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        add(customerWithdraw, gbc);
//
//        gbc.gridx = 1;
//        add(withdrawField, gbc);
//
//        gbc.gridy = 1;
//        add(withdrawButton, gbc);
//        gbc.gridy = 2;
//        add(returnButton, gbc);
//    }
//
//    private void setInsets(GridBagConstraints gbc, int top, int left, int bottom, int right) {
//        gbc.insets = new Insets(top, left, bottom, right);
//    }
}

