package pdc.project.part2;

import java.util.*;

public class Customer {

    // static variable to generate unique incremented ID
    private static int increment = 0;
    // declare variables and constants
    private String customerName;
    private int customerId;
    private String address;
    private String phoneNumber;
    private BankAccount bankAccount;
    private static HashSet<Integer> usedIDs = new HashSet<>();

    // create customer constructor
    public Customer(String customerName, String address, String phoneNumber) {
        this.customerName = customerName;

        // increment and have a unique id for each new customer
        while (usedIDs.contains(increment)) {
            increment++;
        }
        this.customerId = increment;
        usedIDs.add(this.customerId);

        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // Overloaded constructor to handle customerId
    public Customer(String customerName, int customerId, String address, String phoneNumber) {
        this.customerName = customerName;
        this.customerId = customerId;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public static void addUsedId(int id) {
        usedIDs.add(id);
    }

    public static Customer fromString(String input) {
        String[] lines = input.split("\n");
        String customerName = lines[0].split(": ")[1];
        int customerId = Integer.parseInt(lines[1].split(": ")[1]);
        String address = lines[2].split(": ")[1];
        String phoneNumber = lines[3].split(": ")[1];

        return new Customer(customerName, customerId, address, phoneNumber);
    }

    public String toStringFormatted() {
        String format = String.format("%s,%d,%s,%s,%s", customerName, customerId, address, phoneNumber, bankAccount.toStringFormatted());
        if (format.endsWith(",")) {
            format = format.substring(0, format.length() - 1);
        }

        return format;

    }

    //to String method to print out customer details
    @Override
    public String toString() {
        String bankAccountInfo = (bankAccount != null) ? bankAccount.toString() : "No Bank Account";
        return String.format("-Customer name: %s\n-Customer ID: %d\n-Address: %s\n-Phone Number: %s\n-Bank Account: \n%s\n", customerName, customerId, address, phoneNumber, bankAccountInfo);

    }

    public void setBankAccount(BankAccount account) {
        this.bankAccount = account;
    }

    public BankAccount getBankAccount() {
        return this.bankAccount;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
        public static boolean customerExists(int customerIdToCheck) {
        // Check if the customerIdToCheck exists in the usedIDs HashSet
        return usedIDs.contains(customerIdToCheck);
    }
}
