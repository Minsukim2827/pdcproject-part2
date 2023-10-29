package pdc.project.part2;

import java.util.HashMap;
import java.util.Scanner;
import java.io.*;

public class BankServiceCUI implements BankService{

    private HashMap<Integer, Customer> customers;
    private Scanner sc = new Scanner(System.in);
    private FileHandler fileHandler;

    public BankServiceCUI(HashMap<Integer, Customer> customers, FileHandler fileHandler) {

        this.customers = customers;
        this.fileHandler = fileHandler;

    }

    // Method for displaying accounts
    public void displayAccounts() {
        if (customers.isEmpty()) {
            System.out.println("No existing accounts.\n");
        } else {
            for (Customer customer : customers.values()) {
                System.out.println(customer.toString());
            }
        }
    }

    // Method for adding new accounts
    public void addNewAccount() {
        String name;
        do {
            System.out.print("Enter name: ");
            name = sc.nextLine();

            // Input validation for letters only
            if (name.matches(".*\\d.*") || name.matches(".*\\W.*")) {
                System.out.println("Invalid input. Name cannot contain numbers or symbols.");
            }
        } while (name.matches(".*\\d.*") || name.matches(".*\\W.*"));
        String address;
        do {
            System.out.print("Enter address: ");
            address = sc.nextLine();
// Input validation for numbers and letters only. No non-numerical values.
            if (!address.matches("[a-zA-Z0-9 ]+")) {
                System.out.println("Invalid input. Address cannot contain symbols.");
            }
        } while (!address.matches("[a-zA-Z0-9 ]+"));
        String phone;
        do {
            System.out.print("Enter phone number: ");
            phone = sc.nextLine();
// Input validation for numbers only.
            if (!phone.matches("[0-9]+")) {
                System.out.println("Invalid input. Phone number should contain only numbers.");
            }
        } while (!phone.matches("[0-9]+"));

        System.out.println("\nChoose account type: ");
        System.out.println("1. Student Account");
        System.out.println("2. Business Account");
        System.out.println("3. Savings Account ($500 Minimum Balance Requirement)");

        // Input validation for an integer value between 1-3
        int choice = -1;
        while (choice < 1 || choice > 3) {
            System.out.print("Enter your choice (1-3): ");
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine();
            } else {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
        }

        BankAccount account = null;
        double initialAmount;

        // Account creation upon users choice of 1-3
        switch (choice) {
            // Student Account created
            case 1:
                initialAmount = 0.0;
                account = new StudentAccount(initialAmount);
                break;
            // Business Account created
            case 2:
                initialAmount = 0.0;
                account = new BusinessAccount(initialAmount);
                break;
            // Savings Account created
            case 3:
                System.out.print("Enter initial amount: ");
                initialAmount = sc.nextDouble();
                sc.nextLine();
                account = new SavingsAccount("Savings Account", initialAmount, 0.01);
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        Customer newCustomer = new Customer(name, address, phone);
        newCustomer.setBankAccount(account);
        customers.put(newCustomer.getCustomerId(), newCustomer);
        System.out.println("Successfully created a " + account.getAccountType() + "!");
        System.out.println("Customer Id: " + newCustomer.getCustomerId() + "\n");
        fileHandler.saveCustomers(customers);
    }
// Method to delete account

public void deleteAccount() {
    int id;
    System.out.print("\nEnter customer ID to delete: ");

    // Input validation for an integer input
    while (!sc.hasNextInt()) {
        System.out.println("Invalid input. Please enter a number.");
        sc.next();
    }
    id = sc.nextInt();
    sc.nextLine();

    // If no user found for specified id
    if (customers.get(id) == null) {
        System.out.println("Customer not found.\n");
        return;
    }

    Customer customer = customers.get(id);
    if (customer == null) {
        System.out.println("Customer not found.\n");
        return;
    }

    System.out.print("Are you sure? y/n: ");
    String choice = sc.nextLine();
    // Account deletion
    if ("y".equalsIgnoreCase(choice)) {
        // Account deleted if account balance is 0, if account balance exists, doesn't delete the account
        if (customer.getBankAccount().getAccountBalance() == 0
                || ("Savings Account".equals(customer.getBankAccount().getAccountType())
                && customer.getBankAccount().getAccountBalance() == 500.0)) {
            customers.remove(id);
            System.out.println("Account deleted.");
        } else {
            System.out.println("Cannot delete account with existing balance.");
        }
    }
    fileHandler.saveCustomers(customers);
}


// Method to get find customer by ID

    public Customer getCustomerById(int id) {
        return customers.get(id);
    }

    public HashMap<Integer, Customer> getCustomers() {
        return this.customers;
    }
    
    // method for BankServiceCUI to control customers object and for LoginAccountCUI to gain specific access
    public void saveCurrentState() {
    fileHandler.saveCustomers(customers);
}
}
