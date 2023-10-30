package pdc.project.part2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class CurrentAccountScreen extends JFrame {
    private JList<String> accountList;
    private JScrollPane scrollPane;
    private DBManager dbManager;
    private JButton backButton;

    public CurrentAccountScreen() {
        super("Banking System - Current Account");
        dbManager = new DBManager();
        setupUI();
        loadAccounts();
    }

    // Method to setup UI
    private void setupUI() {
        setTitle("Current Accounts");
        setSize(600, 400);
        setLayout(new BorderLayout());

        accountList = new JList<>();
        scrollPane = new JScrollPane(accountList);

        add(scrollPane, BorderLayout.CENTER);

        setupBackButton();

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Method to setup Back Button
    private void setupBackButton() {
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToSplashScreen();
            }
        });
    }

    // Method to return to splash screen
    private void returnToSplashScreen() {
        SplashScreen splashScreen = new SplashScreen();
        splashScreen.setLocation(getLocation());
        splashScreen.setVisible(true);
        dispose();
    }

    // Method to load accounts
    protected void loadAccounts() {
        Vector<String> accounts = new Vector<>();
        try {
            ResultSet rs = dbManager.getAllAccounts();
            while (rs.next()) {
                String accountInfo = "ID: " + rs.getInt("CUSTOMER_ID") + ", Name: " + rs.getString("NAME") + ", Address: " + rs.getString("ADDRESS") + ", Phone: " + rs.getString("PHONE_NUMBER");
                accounts.add(accountInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        accountList.setListData(accounts);
    }
}
