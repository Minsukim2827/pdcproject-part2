package pdc.project.part2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ChangePhoneNumScreen extends JFrame {
    private Customer customer;
    private JLabel resultMessage = new JLabel("");
    private JTextField phoneNumField = new JTextField(20);
    private JButton changePhoneNumber = new JButton("Change");
    private JButton returnButton = new JButton("Return");

    public ChangePhoneNumScreen(Customer customer) {
        super("Banking System - Change Phone Number");
        this.customer = customer;
        setupUI();
    }

    // Method to setup UI
    private void setupUI() {
        setSize(600, 400);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setInsets(gbc, 10, 10, 10, 10);

        JLabel custPhoneNum = new JLabel("Change Phone Number");

        setupChangePhoneNumberButton();
        setupReturnButton();

        addComponentsToLayout(gbc, custPhoneNum);
    }

    // Method to setup Change Phone Number Button
    private void setupChangePhoneNumberButton() {
        changePhoneNumber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeCustomerPhoneNumber();
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

    // Method to change customer phone number
    private void changeCustomerPhoneNumber() {
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
    private void addComponentsToLayout(GridBagConstraints gbc, JLabel custPhoneNum) {
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
