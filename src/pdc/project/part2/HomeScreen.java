package pdc.project.part2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import com.mycompany.comp603project.BankServiceCUI;
//import com.mycompany.comp603project.FileHandler;

public class HomeScreen extends JFrame {
    Customer customer;

    public HomeScreen(Customer customer) {
        super("Banking System - Home");
        this.customer = customer;

        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel welcomeLabel = new JLabel("Welcome, " + customer.getCustomerName());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 17));
        welcomePanel.add(welcomeLabel);

        JPanel mainPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton withdrawButton = new JButton("Withdraw Funds");
        JButton depositButton = new JButton("Deposit Funds");
        JButton changeAddressButton = new JButton("Change Address");
        JButton changePhoneNumberButton = new JButton("Change Phone Number");
        JButton displayDetailsButton = new JButton("Display Details");
        JButton logoutButton = new JButton("Logout");

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        WithdrawScreen withdrawScreen = new WithdrawScreen(customer);
                        withdrawScreen.setLocation(getLocation());
                        withdrawScreen.setVisible(true);
                    }
                });
                dispose();
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        DepositScreen depositScreen = new DepositScreen(customer);
                        depositScreen.setLocation(getLocation());
                        depositScreen.setVisible(true);
                    }
                });
                dispose();
            }
        });

        changeAddressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ChangeAddressScreen changeAddressScreen = new ChangeAddressScreen(customer);
                        changeAddressScreen.setLocation(getLocation());
                        changeAddressScreen.setVisible(true);
                    }
                });
                dispose();
            }
        });

        changePhoneNumberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ChangePhoneNumScreen changePhoneNumScreen = new ChangePhoneNumScreen(customer);
                        changePhoneNumScreen.setLocation(getLocation());
                        changePhoneNumScreen.setVisible(true);
                    }
                });
                dispose();
            }
        });

displayDetailsButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DisplayDetailsScreen displayDetailsScreen = new DisplayDetailsScreen(customer);
                displayDetailsScreen.setLocation(getLocation());
                displayDetailsScreen.setVisible(true);
            }
        });
        dispose();
    }
});


logoutButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Update the customer's details to the database
        DBManager dbManager = new DBManager();
        dbManager.updateCustomerDetails(customer);

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

        mainPanel.add(withdrawButton);
        mainPanel.add(depositButton);
        mainPanel.add(changeAddressButton);
        mainPanel.add(changePhoneNumberButton);
        mainPanel.add(displayDetailsButton);
        mainPanel.add(logoutButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(welcomePanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }
}
