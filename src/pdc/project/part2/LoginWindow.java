package pdc.project.part2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder; 

public class LoginWindow extends JFrame {

    private JTextField customerID;
     private DBManager dbManager;

    public LoginWindow() {
        super("Banking System - Login");
        
        dbManager = new DBManager();

        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        loginPanel.setBorder(BorderFactory.createEmptyBorder(-20, 0, 0, 0));

        JLabel headingLabel = new JLabel("ASF BANKING");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        loginPanel.add(headingLabel, gbc);
        gbc.gridwidth = 1;

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JLabel customerIDLabel = new JLabel("Customer ID:");
        customerID = new JTextField(15); 
        inputPanel.add(customerIDLabel);
        inputPanel.add(customerID);

        JButton backButton = new JButton("Back");
backButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SplashScreen splashScreen = new SplashScreen();
                splashScreen.setLocation(getLocation());
                splashScreen.setVisible(true);
            }
        });
        dispose();
    }
});

gbc.gridx = 0;
gbc.gridy = 2;
gbc.gridwidth = 2;
gbc.fill = GridBagConstraints.CENTER;
loginPanel.add(backButton, gbc);

        
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String customerIDText = customerID.getText();

                try {
                    int customerID = Integer.parseInt(customerIDText);
                    Customer customer = dbManager.getCustomerById(customerID);// get customer from database;
                    if (customer != null) {
                        HomeScreen homeScreen = new HomeScreen(customer);
                        homeScreen.setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(LoginWindow.this, "Invalid customer ID.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(LoginWindow.this, "Invalid customer ID format.");
                }
            }
        });
        inputPanel.add(loginButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        loginPanel.add(inputPanel, gbc);

        add(loginPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); 
    }
}



