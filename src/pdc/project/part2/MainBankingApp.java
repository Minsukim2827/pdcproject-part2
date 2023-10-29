package pdc.project.part2;

import java.util.HashMap;
import javax.swing.*;

public class MainBankingApp {
    public static void main(String[] args) {
        DBManager dbManager = new DBManager();
        dbManager.populateUsedIds();
       // BankServiceCUI bankService = new BankServiceCUI(customers);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SplashScreen splashScreen = new SplashScreen();
                splashScreen.showSplashScreen();
            }
        });

        dbManager.closeConnection();
    }


}
