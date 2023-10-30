//package com.mycompany.comp603project;
//
//import java.awt.GridBagConstraints;
//import java.awt.Insets;
//import javax.swing.*;
//
//public class ChangePhoneNumScreen extends JFrame {
//    public ChangePhoneNumScreen() {
//        super("Banking System - Change Phone Number");
//        setTitle("Change Phone Number");
//        setSize(600, 400);
//        GridBagConstraints gbc = new GridBagConstraints();
//        setInsets(gbc, 10, 10, 10, 10);
//    }
//    
//    private void setInsets(GridBagConstraints gbc, int top, int left, int bottom, int right) {
//        gbc.insets = new Insets(top, left, bottom, right);
//    }
//}


package pdc.project.part2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
//import com.mycompany.comp603project.BankServiceCUI;
//import com.mycompany.comp603project.FileHandler;

public class ChangePhoneNumScreen extends JFrame {
    private Customer customer;
    private JLabel resultMessage = new JLabel("");

    public ChangePhoneNumScreen(Customer customer) {
        
        super("Banking System - Change Phone Number");
        this.customer = customer;
        setSize(600, 400);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setInsets(gbc, 10, 10, 10, 10);

        JLabel custPhoneNum = new JLabel("Change Phone Number");
        JTextField phoneNumField = new JTextField(20);

        JButton changePhoneNumber = new JButton("Change");
        JButton returnButton = new JButton("Return");

changePhoneNumber.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String newPhoneNumber = phoneNumField.getText();
        customer.setPhoneNumber(newPhoneNumber); // update the phone number in the Customer object

        // Update the phone number in the database
        DBManager dbManager = new DBManager();
        boolean isUpdateSuccessful = dbManager.updateCustomerPhoneNumber(customer.getCustomerId(), newPhoneNumber);
                
                if (isUpdateSuccessful) {
                    resultMessage.setText("Phone number successfully changed");
                } else {
                    resultMessage.setText("Failed to change phone number");
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
        add(custPhoneNum, gbc);

        gbc.gridx = 1;
        add(phoneNumField, gbc);

        gbc.gridy = 1;
        add(changePhoneNumber, gbc);
        gbc.gridy = 2;
        add(returnButton, gbc);
        gbc.gridy = 3;
        add(resultMessage, gbc); 
    }

    private void setInsets(GridBagConstraints gbc, int top, int left, int bottom, int right) {
        gbc.insets = new Insets(top, left, bottom, right);
    }
}



