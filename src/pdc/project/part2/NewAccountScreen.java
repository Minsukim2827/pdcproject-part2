package pdc.project.part2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
//import com.mycompany.comp603project.BankServiceCUI;
//import com.mycompany.comp603project.FileHandler;

public class NewAccountScreen extends JFrame {

    private BankServiceCUI bankService;
    private FileHandler fileHandler;

    public NewAccountScreen() {
        super("Banking System - New Account");
        setTitle("New Account Creation");
        setSize(600, 400);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setInsets(gbc, 10, 10, 10, 10);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);

        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField(20);

        JLabel phoneLabel = new JLabel("Phone Number:");
        JTextField phoneField = new JTextField(20);

        JLabel accountTypeLabel = new JLabel("Account Type:");
        String[] accountTypes = {"Student Account", "Business Account", "Savings Account"};
        JComboBox<String> accountTypeComboBox = new JComboBox<>(accountTypes);

        JButton createButton = new JButton("Create Account");
        JButton returnButton = new JButton("Return");

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String address = addressField.getText();
                String phone = phoneField.getText();
                String selectedAccountType = (String) accountTypeComboBox.getSelectedItem();

                String message = "Name: " + name + "\nAddress: " + address + "\nPhone: " + phone + "\nAccount Type: " + selectedAccountType + "\nCustomer ID: "; // Put in customer ID in here @minsu
                JOptionPane.showMessageDialog(NewAccountScreen.this, message);
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        SplashScreen splashscreen = new SplashScreen(bankService, fileHandler);
                        splashscreen.setLocation(getLocation());
                        splashscreen.setVisible(true);
                    }
                });
                dispose();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nameLabel, gbc);

        gbc.gridy = 1;
        add(addressLabel, gbc);

        gbc.gridy = 2;
        add(phoneLabel, gbc);

        gbc.gridy = 3;
        add(accountTypeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(nameField, gbc);

        gbc.gridy = 1;
        add(addressField, gbc);

        gbc.gridy = 2;
        add(phoneField, gbc);

        gbc.gridy = 3;
        add(accountTypeComboBox, gbc);

        gbc.gridy = 4;
        add(createButton, gbc);
        gbc.gridy = 5;
        add(returnButton, gbc);
    }

    private void setInsets(GridBagConstraints gbc, int top, int left, int bottom, int right) {
        gbc.insets = new Insets(top, left, bottom, right);
    }

}
