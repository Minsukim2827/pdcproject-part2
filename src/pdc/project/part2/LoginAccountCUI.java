package pdc.project.part2;

import java.util.Scanner;

public class LoginAccountCUI implements LoginAccount {

    private Customer customer;
    private BankServiceCUI bankService;
    Scanner scanner = new Scanner(System.in);
    private FileHandler fileHandler;

    public LoginAccountCUI(Customer customer, BankServiceCUI bankService, FileHandler fileHandler) {
        this.customer = customer;
        this.fileHandler = fileHandler;
        this.bankService = bankService;

    }

    // UI for login
    public void loginDisplay() {
        while (true) {
            System.out.println("\nWelcome back, " + customer.getCustomerName() + "!");
            System.out.println("--------------------------");
            System.out.println(" [1] Withdraw Funds");
            System.out.println(" [2] Deposit Funds");
            System.out.println(" [3] Change Address");
            System.out.println(" [4] Change Phone Number");
            System.out.println(" [5] Display details");
            System.out.println(" [6] Logout");
            System.out.println("\nChoose an option: ");

            // Input validation
            int option;
            while (true) {
                if (scanner.hasNextInt()) {
                    option = scanner.nextInt();
                    scanner.nextLine();
                    if (option >= 1 && option <= 6) {
                        break;
                    } else {
                        System.out.println("Invalid input. Please choose a valid option.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine();
                }
            }

            switch (option) {
                case 1:
                    withdrawFunds();
                    break;
                case 2:
                    depositFunds();
                    break;
                case 3:
                    changeAddress();
                    break;
                case 4:
                    changePhoneNum();
                    break;
                case 5:
                    System.out.println(customer.toString() + "\n");
                    break;
                case 6:
                    System.out.println("Logging out...\n");
                    return;
                default:
                    System.out.println("Invalid input.");
            }
        }
    }

    // Method for withdrawing funds
    public void withdrawFunds() {
        while (true) {
            System.out.println("Enter withdrawal amount: ");
            // Input validation
            if (scanner.hasNextDouble()) {
                double withdrawAmount = scanner.nextDouble();
                scanner.nextLine();

                BankAccount account = customer.getBankAccount();
                account.withdrawMoney(withdrawAmount);
                break;
            } else {
                System.out.println("Invalid input. Please enter a numerical value.");
                scanner.nextLine();
            }
        }
        bankService.saveCurrentState();
    }

    // Method for depositing funds
    public void depositFunds() {
        while (true) {
            System.out.print("Enter deposit amount: ");

            // Input validation
            if (scanner.hasNextDouble()) {
                double depositAmount = scanner.nextDouble();
                scanner.nextLine();

                BankAccount account = customer.getBankAccount();
                account.depositMoney(depositAmount);
                break;
            } else {
                System.out.println("Invalid input. Please enter a numerical value.");
                scanner.nextLine();
            }
        }
        bankService.saveCurrentState();
    }

    // Method updating address
    public void changeAddress() {
        System.out.print("Enter the new address: ");
        String newAddress = scanner.nextLine();

        // to check if the weird edge case in case you only type empty spaces
        if (newAddress.trim().isEmpty()) {
            System.out.println("Invalid input. Address cannot be only spaces.");
        }
        // Check if the address contains only alphanumeric characters (no symbols)
        if (!newAddress.matches("[a-zA-Z0-9 ]+")) {
            System.out.println("Invalid input. Address cannot contain symbols.");
        } else {
            customer.setAddress(newAddress);
            System.out.println("Address has been updated successfully.");
        }
        bankService.saveCurrentState();
    }

    // Method updating phone number
    public void changePhoneNum() {
        System.out.print("Enter the new phone number: ");
        String newPhoneNum = scanner.nextLine();

        // Input validation for only numbers
        if (!newPhoneNum.matches("[0-9]+")) {
            System.out.println("Invalid input. Phone number must be numeric.");
        } else {
            customer.setPhoneNumber(newPhoneNum);
            System.out.println("Phone number has been updated successfully.");
        }
        bankService.saveCurrentState();
    }

}
