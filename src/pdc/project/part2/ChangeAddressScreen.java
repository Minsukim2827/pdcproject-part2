package pdc.project.part2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
//import com.mycompany.comp603project.BankServiceCUI;
//import com.mycompany.comp603project.FileHandler;

public class ChangeAddressScreen extends JFrame {

    private BankServiceCUI bankService;

    public ChangeAddressScreen() {
        super("Banking System - Change Address");
        setSize(600, 400);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setInsets(gbc, 10, 10, 10, 10);

        JLabel customerAddress = new JLabel("Change Address");
        JTextField AddressField = new JTextField(20);

        JButton changeAddress = new JButton("Change");
        JButton returnButton = new JButton("Return");

        changeAddress.addActionListener(new ActionListener() {
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
                        SplashScreen splashscreen = new SplashScreen(bankService);
                        splashscreen.setLocation(getLocation());
                        splashscreen.setVisible(true);
                    }
                });
                dispose();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(customerAddress, gbc);

        gbc.gridx = 1;
        add(AddressField, gbc);

        gbc.gridy = 1;
        add(changeAddress, gbc);
        gbc.gridy = 2;
        add(returnButton, gbc);
    }

    private void setInsets(GridBagConstraints gbc, int top, int left, int bottom, int right) {
        gbc.insets = new Insets(top, left, bottom, right);
    }
}



