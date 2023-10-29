package pdc.project.part2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
//import com.mycompany.comp603project.BankServiceCUI;
//import com.mycompany.comp603project.FileHandler;

public class DeleteAccountScreen extends JFrame {


    public DeleteAccountScreen() {
        super("Banking System - Delete Account");
        setSize(600, 400);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setInsets(gbc, 10, 10, 10, 10);

        JLabel customerIdLabel = new JLabel("Customer ID:");
        JTextField customerIdField = new JTextField(20);

        JButton deleteButton = new JButton("Delete Account");
        JButton returnButton = new JButton("Return");

deleteButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        int customerId = Integer.parseInt(customerIdField.getText());
        DBManager dbManager = new DBManager();
        Customer customer = dbManager.getCustomerById(customerId);
        if (customer == null) {
            JOptionPane.showMessageDialog(DeleteAccountScreen.this, "No account found for Customer ID: " + customerId);
            dbManager.closeConnection(); // Close connection here if customer is null
            return;
        }

        dbManager.deleteCustomer(customerId);
        JOptionPane.showMessageDialog(DeleteAccountScreen.this, "Account deleted for Customer ID: " + customerId);
        dbManager.closeConnection(); // Close connection here after all operations
    }
});

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        SplashScreen splashscreen = new SplashScreen();
                        splashscreen.setLocation(getLocation());
                        splashscreen.setVisible(true);
                    }
                });
                dispose();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(customerIdLabel, gbc);

        gbc.gridx = 1;
        add(customerIdField, gbc);

        gbc.gridy = 1;
        add(deleteButton, gbc);
        gbc.gridy = 2;
        add(returnButton, gbc);
    }

    private void setInsets(GridBagConstraints gbc, int top, int left, int bottom, int right) {
        gbc.insets = new Insets(top, left, bottom, right);
    }
}
