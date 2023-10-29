package pdc.project.part2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplashScreen extends JFrame {

    private BankServiceCUI bankService;
    private FileHandler fileHandler;

    public SplashScreen(BankServiceCUI bankService, FileHandler fileHandler) {
        this.bankService = bankService;
        this.fileHandler = fileHandler;
        setTitle("ASF Banking");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel menuPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel headingLabel = new JLabel("Welcome to ASF Banking");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        menuPanel.add(headingLabel, gbc);

        JButton seeAccountsButton = new JButton("See Current Accounts");
        JButton addAccountButton = new JButton("Add a New Account");
        JButton deleteAccountButton = new JButton("Delete an Account");
        JButton loginButton = new JButton("Log into an Account");
        JButton exitButton = new JButton("Exit");

        seeAccountsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        CurrentAccountScreen currentAccountScreen = new CurrentAccountScreen();
                        currentAccountScreen.setLocation(getLocation());
                        currentAccountScreen.setVisible(true);
                    }
                });
                dispose();
            }
        });

        addAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        NewAccountScreen newAccountScreen = new NewAccountScreen();
                        newAccountScreen.setLocation(getLocation());
                        newAccountScreen.setVisible(true);
                    }
                });
                dispose();
            }
        });

        deleteAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        DeleteAccountScreen deleteAccountScreen = new DeleteAccountScreen();
                        deleteAccountScreen.setLocation(getLocation());
                        deleteAccountScreen.setVisible(true);
                    }
                });
                dispose();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        LoginWindow loginWindow = new LoginWindow(bankService, fileHandler);
                        loginWindow.setVisible(true);
                    }
                });
                dispose();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        menuPanel.add(seeAccountsButton, gbc);
        gbc.gridx = 1;
        menuPanel.add(addAccountButton, gbc);

        gbc.gridy = 2;
        menuPanel.add(deleteAccountButton, gbc);
        gbc.gridx = 0;
        menuPanel.add(loginButton, gbc);

        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        exitPanel.add(exitButton);

        add(menuPanel, BorderLayout.CENTER);
        add(exitPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
    }

    private void openCurrentAccountScreen() {
        CurrentAccountScreen currentAccountScreen = new CurrentAccountScreen();
        currentAccountScreen.setVisible(true);
    }

    public void showSplashScreen() {
        setVisible(true);
    }
}
