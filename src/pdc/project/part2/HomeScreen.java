package pdc.project.part2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeScreen extends JFrame {
    private Customer customer;
    private JPanel mainPanel;
    private DBManager dbManager;

    public HomeScreen(Customer customer) {
        super("Banking System - Home");
        this.customer = customer;
        this.dbManager = new DBManager();

        setupUI();
    }

    // Method to setup the UI
    private void setupUI() {
        setupWelcomePanel();
        setupMainPanel();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }

    // Method to setup the welcome panel
    private void setupWelcomePanel() {
        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel welcomeLabel = new JLabel("Welcome, " + customer.getCustomerName());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 17));
        welcomePanel.add(welcomeLabel);
        getContentPane().add(welcomePanel, BorderLayout.NORTH);
    }

    // Method to setup the main panel
    private void setupMainPanel() {
        mainPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addButton("Withdraw Funds", e -> openScreen(new WithdrawScreen(customer)));
        addButton("Deposit Funds", e -> openScreen(new DepositScreen(customer)));
        addButton("Change Address", e -> openScreen(new ChangeAddressScreen(customer)));
        addButton("Change Phone Number", e -> openScreen(new ChangePhoneNumScreen(customer)));
        addButton("Display Details", e -> openScreen(new DisplayDetailsScreen(customer)));
        addButton("Logout", this::logout);
    }

    // Method to add a button to the main panel
    private void addButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        mainPanel.add(button);
    }

    // Method to open a new screen
    private void openScreen(JFrame screen) {
        SwingUtilities.invokeLater(() -> {
            screen.setLocation(getLocation());
            screen.setVisible(true);
        });
        dispose();
    }

    // Method to handle logout
    private void logout(ActionEvent e) {
        dbManager.updateCustomerDetails(customer);
        openScreen(new SplashScreen());
    }
}
