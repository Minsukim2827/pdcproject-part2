package pdc.project.part2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
//import com.mycompany.comp603project.BankServiceCUI;
//import com.mycompany.comp603project.FileHandler;

public class ChangeAddressScreen extends JFrame {
    private Customer customer;


    public ChangeAddressScreen(Customer customer) {
        super("Banking System - Change Address");
        this.customer = customer;
        setSize(600, 400);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setInsets(gbc, 10, 10, 10, 10);

        JLabel customerAddress = new JLabel("Change Address");
        JTextField AddressField = new JTextField(20);

        JButton changeAddress = new JButton("Change");
        JButton returnButton = new JButton("Return");

changeAddress.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String newAddress = AddressField.getText();
        customer.setAddress(newAddress); // update the address in the Customer object

        // Update the address in the database
        DBManager dbManager = new DBManager();
        dbManager.updateCustomerAddress(customer.getCustomerId(), newAddress);
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
        add(customerAddress, gbc);

        gbc.gridx = 1;
        add(AddressField, gbc);

        gbc.gridy = 1;
        add(changeAddress, gbc);
        gbc.gridy = 2;
        add(returnButton, gbc);
    }

    private void setInsets(GridBagConstraints gbc, int top, int left, int bottom, int right) {
        gbc.insets = new Insets(top, left, bottom, right);
    }
}



