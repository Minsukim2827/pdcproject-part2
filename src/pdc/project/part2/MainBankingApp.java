package pdc.project.part2;

import java.util.HashMap;
import javax.swing.*;

public class MainBankingApp {
    public static void main(String[] args) {
        DBManager dbManager = new DBManager();
        FileHandler fileHandler = new FileHandler();
        HashMap<Integer, Customer> customers = fileHandler.loadCustomers();
        BankServiceCUI bankService = new BankServiceCUI(customers, fileHandler);
        printExistingCustomerIDs(bankService);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SplashScreen splashScreen = new SplashScreen(bankService);
                splashScreen.showSplashScreen();
            }
        });

        dbManager.closeConnection();
    }

    private static void printExistingCustomerIDs(BankServiceCUI bankService) {
        HashMap<Integer, Customer> customers = bankService.getCustomers();
        System.out.println("Existing Customer IDs:");
        for (int customerID : customers.keySet()) {
            System.out.println(customerID);
        }
        System.out.println("-----------------------");
    }
}
