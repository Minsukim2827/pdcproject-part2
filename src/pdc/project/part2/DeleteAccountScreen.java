package pdc.project.part2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class DeleteAccountScreen extends JFrame {
    private JTextField customerIdField = new JTextField(20);
    private JButton deleteButton = new JButton("Delete Account");
    private JButton returnButton = new JButton("Return");

    public DeleteAccountScreen() {
        super("Banking System - Delete Account");
        setupUI();
    }

    // Method to setup UI
// Method to setup UI
private void setupUI() {
    setSize(600, 400);
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    setInsets(gbc, 10, 10, 10, 10);

    JLabel customerIdLabel = new JLabel("Customer ID:");

    setupDeleteButton();
    setupReturnButton();

    // Apply gradient to the background
    JPanel gradientPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            int w = getWidth();
            int h = getHeight();
            GradientPaint gp = new GradientPaint(0, 0, Color.DARK_GRAY, w, h, Color.ORANGE);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
        }
    };
    gradientPanel.setLayout(new GridBagLayout());
    setContentPane(gradientPanel);

    addComponentsToLayout(gbc, customerIdLabel);
}

// Method to setup Delete Button
private void setupDeleteButton() {
    deleteButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            deleteCustomerAccount();
        }
    });
    deleteButton.setBackground(Color.GREEN); // Set button color to green
}

// Method to setup Return Button
private void setupReturnButton() {
    returnButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            returnToSplashScreen();
        }
    });
    returnButton.setBackground(Color.GREEN); // Set button color to green
}

    // Method to delete customer account
    private void deleteCustomerAccount() {
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

    // Method to return to splash screen
    private void returnToSplashScreen() {
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

    // Method to add components to layout
    private void addComponentsToLayout(GridBagConstraints gbc, JLabel customerIdLabel) {
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
