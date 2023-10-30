package pdc.project.part2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;
import java.time.format.DateTimeFormatter;
import java.time.format.*;
import java.text.SimpleDateFormat;

public class DBManager {

    private static final String URL = "jdbc:derby:BankDB;create=true";
    private static final String USER_NAME = "bank";
    private static final String PASSWORD = "bank";

    Connection con;

    public DBManager() {
        makeConnection();
        if (con != null) {
            populateUsedIds();
        } else {
            System.out.println("Failed to make database connection!");
        }
    }

    public static void main(String[] args) {
        DBManager playerDB = new DBManager();
        System.out.println(playerDB.getConnection());
    }

    public Connection getConnection() {
        try {
            if (this.con == null || !this.con.isValid(2)) {
                makeConnection();
            }
        } catch (SQLException ex) {
            System.out.println("Error while checking database connection: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return this.con;
    }

    public void makeConnection() {
        if (this.con == null) {
            try {
                con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            } catch (Exception ex) {
                System.out.println("Error while making database connection: " + ex.getMessage());
                throw new RuntimeException(ex);
            }
        }
    }

    public void closeConnection() {
        if (con != null) {
            try {
                con.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void insertCustomerAndAccount(Customer customer, BankAccount account) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            // insert the new customer into the CUSTOMER table
            String insertCustomerSql = "INSERT INTO CUSTOMER(CUSTOMER_ID, NAME, ADDRESS, PHONE_NUMBER) VALUES (?, ?, ?, ?)";
            try ( PreparedStatement pstmt = conn.prepareStatement(insertCustomerSql)) {
                pstmt.setInt(1, customer.getCustomerId());
                pstmt.setString(2, customer.getCustomerName());
                pstmt.setString(3, customer.getAddress());
                pstmt.setString(4, customer.getPhoneNumber());
                pstmt.executeUpdate();
            }

            // insert the new account into the ACCOUNT table
            String insertAccountSql = "INSERT INTO ACCOUNT(ACCOUNT_ID, CUSTOMER_ID, ACCOUNT_TYPE, BALANCE, INTEREST_RATE) VALUES (?, ?, ?, ?, ?)";
            try ( PreparedStatement pstmt = conn.prepareStatement(insertAccountSql)) {
                pstmt.setInt(1, account.getAccountId());
                pstmt.setInt(2, customer.getCustomerId());
                pstmt.setString(3, account.getAccountType());
                pstmt.setDouble(4, account.getAccountBalance());
                pstmt.setDouble(5, account.getInterestRate());
                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void populateUsedIds() {
        Connection conn = null;
        try {
            conn = getConnection();
            Statement statement = con.createStatement();

            // Populate Customer IDs
            ResultSet resultSet = statement.executeQuery("SELECT CUSTOMER_ID FROM CUSTOMER");
            while (resultSet.next()) {
                int id = resultSet.getInt("CUSTOMER_ID");
                Customer.addUsedId(id);
            }

            // Populate Account IDs
            resultSet = statement.executeQuery("SELECT ACCOUNT_ID FROM ACCOUNT");
            while (resultSet.next()) {
                int id = resultSet.getInt("ACCOUNT_ID");
                BankAccount.addUsedId(id); // Assuming you have this method in BankAccount class
            }

            // Populate Transaction IDs
            resultSet = statement.executeQuery("SELECT TRANSACTION_ID FROM BANK_TRANSACTION");
            while (resultSet.next()) {
                int id = resultSet.getInt("TRANSACTION_ID");
                Transaction.addUsedId(id); // Assuming you have this method in Transaction class
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer getCustomerById(int id) {
        Customer customer = null;
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM CUSTOMER WHERE CUSTOMER_ID = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("NAME");
                String address = resultSet.getString("ADDRESS");
                String phone = resultSet.getString("PHONE_NUMBER");
                customer = new Customer(name, id, address, phone);
                // Fetch associated bank account
                BankAccount bankAccount = getBankAccountByCustomerId(id);
                customer.setBankAccount(bankAccount);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public BankAccount getBankAccountByCustomerId(int customerId) {
        BankAccount bankAccount = null;
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM ACCOUNT WHERE CUSTOMER_ID = ?");
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int accountId = resultSet.getInt("ACCOUNT_ID");
                String accountType = resultSet.getString("ACCOUNT_TYPE");
                double balance = resultSet.getDouble("BALANCE");
                double interestRate = resultSet.getDouble("INTEREST_RATE");
                switch (accountType) {
                case "Savings Account":
                    bankAccount = new SavingsAccount(accountId, accountType, balance, interestRate);
                    break;
                case "Student Account":
                    bankAccount = new StudentAccount(accountId, accountType, balance, interestRate);
                    break;
                case "Business Account":
                    bankAccount = new BusinessAccount(accountId, accountType, balance, interestRate);
                    break;
                    default:
                        System.out.println("Unknown account type: " + accountType);
                        break;
                }
                // Fetch associated transactions
                Queue<Transaction> transactions = getTransactionsByAccountId(accountId);
                bankAccount.setTransactionHistory(transactions);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bankAccount;
    }

    public Queue<Transaction> getTransactionsByAccountId(int accountId) {
        Queue<Transaction> transactions = new LinkedList<>();
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM BANK_TRANSACTION WHERE ACCOUNT_ID = ? ORDER BY DATE DESC FETCH FIRST 5 ROWS ONLY");
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int transactionId = resultSet.getInt("TRANSACTION_ID");
            String transactionType = resultSet.getString("TRANSACTION_TYPE");
            double amount = resultSet.getDouble("AMOUNT");
            String date = resultSet.getString("DATE");
            Transaction transaction = new Transaction(transactionId, transactionType, amount, date);
            transactions.add(transaction);
        }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public ResultSet getAllAccounts() throws SQLException {
        Statement statement = con.createStatement();
        return statement.executeQuery("SELECT * FROM CUSTOMER");
    }

    public void deleteCustomer(int customerId) {
        try ( Connection conn = getConnection()) {
            // First, delete the associated records in the BANK_TRANSACTION table
            String deleteTransactionSql = "DELETE FROM BANK_TRANSACTION WHERE ACCOUNT_ID IN (SELECT ACCOUNT_ID FROM ACCOUNT WHERE CUSTOMER_ID = ?)";
            try ( PreparedStatement pstmt = conn.prepareStatement(deleteTransactionSql)) {
                pstmt.setInt(1, customerId);
                pstmt.executeUpdate();
            }

            // Then, delete the associated records in the ACCOUNT table
            String deleteAccountSql = "DELETE FROM ACCOUNT WHERE CUSTOMER_ID = ?";
            try ( PreparedStatement pstmt = conn.prepareStatement(deleteAccountSql)) {
                pstmt.setInt(1, customerId);
                pstmt.executeUpdate();
            }

            // Finally, delete the record in the CUSTOMER table
            String deleteCustomerSql = "DELETE FROM CUSTOMER WHERE CUSTOMER_ID = ?";
            try ( PreparedStatement pstmt = conn.prepareStatement(deleteCustomerSql)) {
                pstmt.setInt(1, customerId);
                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
public boolean updateCustomerAddress(int customerId, String newAddress) {
    Connection conn = null;
    try {
        conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        String updateAddressSql = "UPDATE CUSTOMER SET ADDRESS = ? WHERE CUSTOMER_ID = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement(updateAddressSql)) {
            pstmt.setString(1, newAddress);
            pstmt.setInt(2, customerId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if the update was successful, false otherwise
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    return false; // Return false if an exception was thrown
}

public boolean updateCustomerPhoneNumber(int customerId, String newPhoneNumber) {
    Connection conn = null;
    try {
        conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        String updatePhoneNumberSql = "UPDATE CUSTOMER SET PHONE_NUMBER = ? WHERE CUSTOMER_ID = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement(updatePhoneNumberSql)) {
            pstmt.setString(1, newPhoneNumber);
            pstmt.setInt(2, customerId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if the update was successful, false otherwise
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    return false; // Return false if an exception was thrown
}

    
    public void updateCustomerDetails(Customer customer) {
    Connection conn = null;
    try {
        conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        // update the customer in the CUSTOMER table
        String updateCustomerSql = "UPDATE CUSTOMER SET NAME = ?, ADDRESS = ?, PHONE_NUMBER = ? WHERE CUSTOMER_ID = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement(updateCustomerSql)) {
            pstmt.setString(1, customer.getCustomerName());
            pstmt.setString(2, customer.getAddress());
            pstmt.setString(3, customer.getPhoneNumber());
            pstmt.setInt(4, customer.getCustomerId());
            pstmt.executeUpdate();
        }

        // update the account in the ACCOUNT table
        BankAccount account = customer.getBankAccount();
        String updateAccountSql = "UPDATE ACCOUNT SET ACCOUNT_TYPE = ?, BALANCE = ?, INTEREST_RATE = ? WHERE ACCOUNT_ID = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement(updateAccountSql)) {
            pstmt.setString(1, account.getAccountType());
            pstmt.setDouble(2, account.getAccountBalance());
            pstmt.setDouble(3, account.getInterestRate());
            pstmt.setInt(4, account.getAccountId());
            pstmt.executeUpdate();
        }

        // update the transactions in the BANK_TRANSACTION table
        // assuming you have a method getTransactions() in BankAccount class that returns a list of transactions
// update the transactions in the BANK_TRANSACTION table
Queue<Transaction> transactions = account.getTransactionHistory();
for (Transaction transaction : transactions) {
    if (!transactionExists(transaction.getTransactionId())) {
        String updateTransactionSql = "UPDATE BANK_TRANSACTION SET TRANSACTION_TYPE = ?, AMOUNT = ?, DATE = ? WHERE TRANSACTION_ID = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement(updateTransactionSql)) {
            pstmt.setString(1, transaction.getTransactionType());
            pstmt.setDouble(2, transaction.getAmount());
            SimpleDateFormat fromUser = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                String reformattedStr = myFormat.format(fromUser.parse(transaction.getDate()));
                pstmt.setString(3, reformattedStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            pstmt.setInt(4, transaction.getTransactionId());
            pstmt.executeUpdate();
        }
    }
}

    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
public void updateAccountBalance(int accountId, double newBalance) {
    Connection conn = null;
    try {
        conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        String updateBalanceSql = "UPDATE ACCOUNT SET BALANCE = ? WHERE ACCOUNT_ID = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement(updateBalanceSql)) {
            pstmt.setDouble(1, newBalance);
            pstmt.setInt(2, accountId);
            pstmt.executeUpdate();
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

    public void insertTransaction(Transaction transaction, Customer customer) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            int accountId = customer.getBankAccount().getAccountId();
            String accountCheckSql = "SELECT * FROM ACCOUNT WHERE ACCOUNT_ID = ?";
            try (PreparedStatement pstmtCheck = conn.prepareStatement(accountCheckSql)) {
                pstmtCheck.setInt(1, accountId);
                ResultSet rs = pstmtCheck.executeQuery();
                if (!rs.next()) {
                    System.out.println("Account ID " + accountId + " does not exist in ACCOUNT table");
                    return;
                }
            }

            String insertTransactionSql = "INSERT INTO BANK_TRANSACTION (TRANSACTION_ID, ACCOUNT_ID, TRANSACTION_TYPE, AMOUNT, DATE) VALUES (?, ?, ?, ?, ?)";
            try ( PreparedStatement pstmt = conn.prepareStatement(insertTransactionSql)) {
                pstmt.setInt(1, transaction.getTransactionId());
                pstmt.setInt(2, accountId);
                pstmt.setString(3, transaction.getTransactionType());
                pstmt.setDouble(4, transaction.getAmount());
                String originalDate = transaction.getDate();  // assuming getDate() returns date in 'DD/MM/YYYY' format
                String convertedDate = convertDateFormat(originalDate);
                pstmt.setDate(5, java.sql.Date.valueOf(convertedDate));
                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


public String convertDateFormat(String originalDate) {
    DateTimeFormatter originalFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate date = LocalDate.parse(originalDate, originalFormat);
    DateTimeFormatter targetFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return date.format(targetFormat);
}



public boolean transactionExists(int transactionId) {
    Connection conn = null;
    boolean exists = false;
    try {
        conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        String checkTransactionSql = "SELECT COUNT(*) FROM BANK_TRANSACTION WHERE TRANSACTION_ID = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement(checkTransactionSql)) {
            pstmt.setInt(1, transactionId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    return exists;
}


}
