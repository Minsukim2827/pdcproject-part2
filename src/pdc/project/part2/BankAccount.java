package pdc.project.part2;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.*;

public abstract class BankAccount {

    private String accountType;
    private double accountBalance;
    private Queue<Transaction> transactionHistory;
    private double interestRate;
    private double currentYearlyInterest;
    private static int increment = 0;
    private static HashSet<Integer> usedIDs = new HashSet<>();
    private int accountId;
    Scanner scanner = new Scanner(System.in);

    // constructors------------
    public BankAccount(String accountType, double accountBalance, double interestRate) {
        this.accountType = accountType;
        this.accountBalance = accountBalance;
        this.transactionHistory = new LinkedList<>();
        this.interestRate = interestRate;
        this.currentYearlyInterest = 0.0;

        while (usedIDs.contains(increment)) {
            increment++;
        }
        this.accountId = increment;
        usedIDs.add(this.accountId);
    }
        public BankAccount(int accountId, String accountType, double accountBalance, double interestRate) {
        this.accountType = accountType;
        this.accountBalance = accountBalance;
        this.transactionHistory = new LinkedList<>();
        this.interestRate = interestRate;
        this.currentYearlyInterest = 0.0;

        this.accountId = accountId;
        usedIDs.add(this.accountId);
    }
//----------------------
        
    // each account type has different interest rates
    public abstract void calculateYearlyInterest();

    //method for depositing money into the account
    public void depositMoney(double amount) {
        // Input validation
        while (true) {
            if (amount > 0) {
                accountBalance += amount;
                Transaction depositTransaction = new Transaction("Deposit", amount);
                addTransaction(depositTransaction);
                System.out.println("Deposit successful. Remaining balance: $" + this.accountBalance);
                break;
            } else {
                System.out.println("Invalid deposit amount. Please enter a positive amount:");
                amount = scanner.nextDouble();
            }
        }
        calculateYearlyInterest();
    }

    //method for withdrawing money from the account
    public void withdrawMoney(double amount) {
        while (true) {
            if (amount > 0) {
                if (accountBalance >= amount) {
                    accountBalance -= amount;
                    Transaction withdrawTransaction = new Transaction("Withdrawal", amount);
                    addTransaction(withdrawTransaction);
                    System.out.println("Withdrawal successful. Remaining balance: $" + this.accountBalance);
                    break;
                } else {
                    System.out.println("Insufficient balance");
                    break;
                }
            } else {
                System.out.println("Invalid withdrawal amount. Please enter a positive amount:");
                amount = scanner.nextDouble();
            }
        }
        calculateYearlyInterest();
    }
//adds a transaction to the transaction history
    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
        if (transactionHistory.size() > 5) {
            transactionHistory.poll();
        }
    }
    // adds a used account id to the set
    public static void addUsedId(int id) {
    usedIDs.add(id);
}
//checks if set contains account id
    public static boolean accountExists(int accountIdToCheck) {
        return usedIDs.contains(accountIdToCheck);
    }

    // Getters and setters
    public String getAccountType() {
        return accountType;
    }

    public int getAccountId() {
        return accountId;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getCurrentYearlyInterest() {
        return currentYearlyInterest;
    }

    public void setYearlyInterest(double yearlyInterest) {
        this.currentYearlyInterest = yearlyInterest;
    }

    public void setTransactionHistory(Queue<Transaction> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }
    public Queue<Transaction> getTransactionHistory(){
        return transactionHistory;
    }

    //for parsing to text file
    public String toStringFormatted() {
        StringBuilder sb = new StringBuilder(String.format("%s,%f,%f,", accountType, accountBalance, interestRate));

        for (Transaction transaction : transactionHistory) {
            sb.append(transaction.toStringFormatted());

        }

        return sb.toString();
    }

// to string method
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Bank Account\n");
        sb.append("Account type: ").append(accountType).append("\n");
        sb.append("- Account Balance: $").append(String.format("%.2f", accountBalance)).append("\n");
        sb.append("- Interest Rate: ").append(String.format("%.2f", interestRate)).append("\n");
        sb.append("- Current yearly Interest: ").append(String.format("%.2f", currentYearlyInterest)).append("\n");
        sb.append("- Transaction History:\n");
        sb.append("\nTransaction/s\n-------------------------------\n");

        for (Transaction transaction : transactionHistory) {
            sb.append(transaction.toString()).append("\n");
        }

        return sb.toString();
    }

    public void setCurrentYearlyInterest(double currentYearlyInterest) {
        this.currentYearlyInterest = currentYearlyInterest;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
