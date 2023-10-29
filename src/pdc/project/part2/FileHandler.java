package pdc.project.part2;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FileHandler {

    private static final String FILENAME = "Customers.txt";

    public static BufferedReader createBufferedReader(String filename) throws IOException {
        return new BufferedReader(new FileReader(filename));
    }

    public static String readLineFromBufferedReader(BufferedReader reader) throws IOException {
        return reader.readLine();
    }

    public static void closeBufferedReader(BufferedReader reader) throws IOException {
        reader.close();
    }

    public static PrintWriter createPrintWriter(String filename) throws IOException {
        return new PrintWriter(new BufferedWriter(new FileWriter(filename)));
    }

    public static void writeLineToPrintWriter(PrintWriter writer, String line) {
        writer.println(line);
    }

    public static void closePrintWriter(PrintWriter writer) {
        writer.close();
    }

    public static HashMap<Integer, Customer> loadCustomers() {
        HashMap<Integer, Customer> customers = new HashMap<>();
        try {
            BufferedReader reader = createBufferedReader(FILENAME);

            String line = readLineFromBufferedReader(reader);
            while (line != null) {
                
                String[] parts = line.split(",");
                String customerName = parts[0];
                int customerId = Integer.parseInt(parts[1]);
                String address = parts[2];
                String phoneNumber = parts[3];
            String accountType = parts[4];
            double accountBalance = Double.parseDouble(parts[5]);
            double interestRate = Double.parseDouble(parts[6]);

            BankAccount bankAccount;
            if (accountType.equals("Savings Account")) {
                bankAccount = new SavingsAccount(accountType, accountBalance, interestRate);
            } else if (accountType.equals("Student Account")) {
                bankAccount = new StudentAccount(accountType, accountBalance, interestRate);
            } else { // Business Account
                bankAccount = new BusinessAccount(accountType, accountBalance, interestRate);
            }
            if (parts.length > 7) {
                Queue<Transaction> transactions = new LinkedList<>();
                for (int i = 7; i < parts.length; i+=3) {
                    String transactionType = parts[i]; // transaction type
                    double amount = Double.parseDouble(parts[i+1]); // amount
                    String date = parts[i+2]; // date
                    transactions.add(new Transaction(transactionType, amount,date));
                }
                bankAccount.setTransactionHistory(transactions);
            }

                Customer customer = new Customer(customerName, customerId, address, phoneNumber);
                customer.setBankAccount(bankAccount);
                Customer.addUsedId(customerId);
                customers.put(customerId, customer);
            
            line = readLineFromBufferedReader(reader);
            
            
        } 
        reader.close();
    }catch (IOException e) {
            System.out.println("An error occurred while loading customers.");
            e.printStackTrace();
        }
        
        return customers;
    }

 public static void saveCustomers(HashMap<Integer, Customer> customers) {
        try {
            PrintWriter writer = createPrintWriter(FILENAME);
            for (Customer customer : customers.values()) {
                writeLineToPrintWriter(writer, customer.toStringFormatted());
            }
            closePrintWriter(writer);
        } catch (IOException e) {
            System.out.println("An error occurred while saving customers.");
            e.printStackTrace();
        }
    }
}
