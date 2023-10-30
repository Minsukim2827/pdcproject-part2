package pdc.project.part2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ChangeAddressScreen extends JFrame {
    private Customer customer;
    private JLabel resultMessage = new JLabel("");
    private JTextField AddressField = new JTextField(20);
    private JButton changeAddress = new JButton("Change");
    private JButton returnButton = new JButton("Return");

    public ChangeAddressScreen(Customer customer) {
        super("Banking System - Change Address");
        this.customer = customer;
        setupUI();
    }

    // Method to setup UI
    private void setupUI() {
        setSize(600, 400);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setInsets(gbc, 10, 10, 10, 10);

        JLabel customerAddress = new JLabel("Change Address");

        setupChangeAddressButton();
        setupReturnButton();

        addComponentsToLayout(gbc, customerAddress);
    }

    // Method to setup Change Address Button
    private void setupChangeAddressButton() {
        changeAddress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeCustomerAddress();
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

    // Method to change customer address
    private void changeCustomerAddress() {
        String newAddress = AddressField.getText();
        customer.setAddress(newAddress); // update the address in the Customer object

        // Update the address in the database
        DBManager dbManager = new DBManager();
        boolean isUpdateSuccessful = dbManager.updateCustomerAddress(customer.getCustomerId(), newAddress);

        if (isUpdateSuccessful) {
            resultMessage.setText("Address successfully changed");
        } else {
            resultMessage.setText("Failed to change address");
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
    private void addComponentsToLayout(GridBagConstraints gbc, JLabel customerAddress) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(customerAddress, gbc);

        gbc.gridx = 1;
        add(AddressField, gbc);

        gbc.gridy = 1;
        add(changeAddress, gbc);
        gbc.gridy = 2;
        add(returnButton, gbc);
        gbc.gridy = 3;
        add(resultMessage, gbc);
    }

    private void setInsets(GridBagConstraints gbc, int top, int left, int bottom, int right) {
        gbc.insets = new Insets(top, left, bottom, right);
    }
}
