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
        setTitle("Current Accounts");
        setSize(600, 400);
        setLayout(new BorderLayout());

        accountList = new JList<>();
        scrollPane = new JScrollPane(accountList);

        add(scrollPane, BorderLayout.CENTER);

        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SplashScreen splashScreen = new SplashScreen();
                splashScreen.setLocation(getLocation());
                splashScreen.setVisible(true);
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        loadAccounts();
    }

    private void loadAccounts() {
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
