package pdc.project.part2;

import java.util.*;

public class BankApp {
    private BankServiceCUI bankService;
    private FileHandler fileHandler;

    public BankApp() {
        HashMap<Integer, Customer> customers = FileHandler.loadCustomers();
        this.fileHandler = new FileHandler();
        this.bankService = new BankServiceCUI(customers, fileHandler);
        Scanner sc = new Scanner(System.in);
        int option;

        //while loop for the main CUI of our program
        do {
            System.out.println("--------------------------");
            System.out.println("Welcome to ASF Banking!");
            System.out.println("--------------------------");
            System.out.println("1. See current accounts");
            System.out.println("2. Add a new account");
            System.out.println("3. Delete an account");
            System.out.println("4. Log into an account");
            System.out.println("5. Exit");
            System.out.print("\nChoose an option: ");

            // Input validation
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.next();
            }
            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    bankService.displayAccounts();
                    break;
                case 2:
                    bankService.addNewAccount();
                    break;
                case 3:
                    bankService.deleteAccount();
                    break;
                case 4:
                    int id;
                    do {
                        System.out.print("Enter customer ID to login: ");
                        // Input validation
                        while (!sc.hasNextInt()) {
                            System.out.println("Invalid input. Please enter a number:");
                            sc.next();
                        }
                        id = sc.nextInt();
                        sc.nextLine();

                        // If customer not found, return back to main menu
                        if (bankService.getCustomerById(id) == null) {
                            System.out.println("Customer not found.\n");
                            break;
                        }
                    } while (bankService.getCustomerById(id) == null);

                    // If customer found, display the login display.
                    if (bankService.getCustomerById(id) != null) {
                        LoginAccountCUI loginAccount = new LoginAccountCUI(bankService.getCustomerById(id), bankService, fileHandler);
                        loginAccount.loginDisplay();
                    }
                    break;
                case 5:
                    System.out.println("Exiting...");
                    FileHandler.saveCustomers(bankService.getCustomers());
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        } while (option != 5);
    }
}
