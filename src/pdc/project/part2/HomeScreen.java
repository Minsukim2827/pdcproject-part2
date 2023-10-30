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
    JPanel contentPane = new JPanel(new BorderLayout()) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            Color color1 = Color.DARK_GRAY;
            Color color2 = Color.ORANGE;
            int w = getWidth();
            int h = getHeight();
            GradientPaint gp = new GradientPaint(
                    0, 0, color1, w, h, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
        }
    };

    setContentPane(contentPane);

    setupWelcomePanel();
    setupMainPanel();

    getContentPane().add(mainPanel, BorderLayout.CENTER);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(600, 400);
    setLocationRelativeTo(null);
    setVisible(true);
}




    // Method to setup the welcome panel
private void setupWelcomePanel() {
    JPanel welcomePanel = new JPanel(new BorderLayout());
    welcomePanel.setBackground(Color.LIGHT_GRAY);
    JLabel welcomeLabel = new JLabel("Welcome, " + customer.getCustomerName());
    welcomeLabel.setFont(new Font("Arial", Font.BOLD, 17));
    welcomePanel.add(welcomeLabel, BorderLayout.WEST);

    JLabel balanceLabel = new JLabel("Current Balance: " + "$" + String.format("%.2f",customer.getBankAccount().getAccountBalance()) );
    balanceLabel.setFont(new Font("Arial", Font.BOLD, 17));
    welcomePanel.add(balanceLabel, BorderLayout.EAST);

    getContentPane().add(welcomePanel, BorderLayout.NORTH);
}

    // Method to setup the main panel
private void setupMainPanel() {
    mainPanel = new JPanel(new GridBagLayout()) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            Color color1 = Color.DARK_GRAY;
            Color color2 = Color.ORANGE;
            int w = getWidth();
            int h = getHeight();
            GradientPaint gp = new GradientPaint(
                    0, 0, color1, w, h, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
        }
    };
    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.VERTICAL;
    constraints.weightx = 0.5;
    constraints.weighty = 0.5;

    addButton("Withdraw Funds", e -> openScreen(new WithdrawScreen(customer)), constraints, Color.GREEN);
    addButton("Deposit Funds", e -> openScreen(new DepositScreen(customer)), constraints, Color.GREEN);
    addButton("Change Address", e -> openScreen(new ChangeAddressScreen(customer)), constraints, Color.GREEN);
    addButton("Change Phone Number", e -> openScreen(new ChangePhoneNumScreen(customer)), constraints, Color.GREEN);
    addButton("Display Details", e -> openScreen(new DisplayDetailsScreen(customer)), constraints, Color.GREEN);
    addButton("Logout", this::logout, constraints, Color.GREEN);
}



    // Method to add a button to the main panel
private int currentGridY = 0;  // Add this field to keep track of the current gridy value

    private void addButton(String text, ActionListener action, GridBagConstraints constraints, Color color) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        button.setBackground(color); // Set button color
        button.setPreferredSize(new Dimension(200, 30));  // Set the preferred size here

        constraints.gridx = 0;
        constraints.gridy = currentGridY++;  // Increment gridy each time a button is added
        constraints.insets = new Insets(10,0,10,0);

        mainPanel.add(button, constraints);
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
