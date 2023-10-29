package pdc.project.part2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
//import com.mycompany.comp603project.BankServiceCUI;
//import com.mycompany.comp603project.FileHandler;

public class WithdrawScreen extends JFrame {

    private BankServiceCUI bankService;
    private FileHandler fileHandler;

    public WithdrawScreen() {
        super("Banking System - Withdraw Funds");
        setSize(600, 400);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setInsets(gbc, 10, 10, 10, 10);

        JLabel customerWithdraw = new JLabel("How much would you like to withdraw?");
        JTextField withdrawField = new JTextField(20);

        JButton withdrawButton = new JButton("Withdraw");
        JButton returnButton = new JButton("Return");

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // implement withdraw function @minsu
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
        add(customerWithdraw, gbc);

        gbc.gridx = 1;
        add(withdrawField, gbc);

        gbc.gridy = 1;
        add(withdrawButton, gbc);
        gbc.gridy = 2;
        add(returnButton, gbc);
    }

    private void setInsets(GridBagConstraints gbc, int top, int left, int bottom, int right) {
        gbc.insets = new Insets(top, left, bottom, right);
    }
}

