package pdc.project.part2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import com.mycompany.comp603project.BankServiceCUI;
//import com.mycompany.comp603project.FileHandler;

public class HomeScreen extends JFrame {

    private BankServiceCUI bankService;

    public HomeScreen(String username) {
        super("Banking System - Home");

        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel welcomeLabel = new JLabel("Welcome, " + username);
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
                        WithdrawScreen withdrawScreen = new WithdrawScreen();
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
                        DepositScreen depositScreen = new DepositScreen();
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
                        ChangeAddressScreen changeAddressScreen = new ChangeAddressScreen();
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
                        ChangePhoneNumScreen changePhoneNumScreen = new ChangePhoneNumScreen();
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
                        DisplayDetailsScreen displayDetailsScreen = new DisplayDetailsScreen();
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
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        SplashScreen splashscreen = new SplashScreen(bankService);
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
