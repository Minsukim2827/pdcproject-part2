package pdc.project.part2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;

public class DBManager
{
    private static final String URL = "jdbc:derby:BankDB;create=true"; 
    private static final String USER_NAME = "bank";
    private static final String PASSWORD= "bank";

    Connection con;
    
public DBManager() {
    makeConnection();
    if (con != null) {
        populateUsedIds();
    } else {
        System.out.println("Failed to make database connection!");
    }
}

    public static void main(String[] args)
    {
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

    public void closeConnection()
    {
        if(con != null)
        {
          try
          {
            con.close();
          }
          catch(Exception ex)
          {
            System.out.println(ex.getMessage());
          }
        }
    }
    
            public void insertCustomerAndAccount(Customer customer, BankAccount account) {
        try (Connection conn = getConnection()) {
            // insert the new customer into the CUSTOMER table
            String insertCustomerSql = "INSERT INTO CUSTOMER(CUSTOMER_ID, NAME, ADDRESS, PHONE_NUMBER) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertCustomerSql)) {
                pstmt.setInt(1, customer.getCustomerId());
                pstmt.setString(2, customer.getCustomerName());
                pstmt.setString(3, customer.getAddress());
                pstmt.setString(4, customer.getPhoneNumber());
                pstmt.executeUpdate();
            }

            // insert the new account into the ACCOUNT table
            String insertAccountSql = "INSERT INTO ACCOUNT(ACCOUNT_ID, CUSTOMER_ID, ACCOUNT_TYPE, BALANCE, INTEREST_RATE) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertAccountSql)) {
                pstmt.setInt(1, account.getAccountId());
                pstmt.setInt(2, customer.getCustomerId());
                pstmt.setString(3, account.getAccountType());
                pstmt.setDouble(4, account.getAccountBalance());
                pstmt.setDouble(5, account.getInterestRate());
                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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
            // Assuming you have other fields like address, email in your Customer table
            String address = resultSet.getString("ADDRESS");
            String phone = resultSet.getString("PHONE_NUMBER");
            customer = new Customer(name,id, address, phone);
        }

        resultSet.close();
        statement.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return customer;
}
            
            
            public ResultSet getAllAccounts() throws SQLException {
    Statement statement = con.createStatement();
    return statement.executeQuery("SELECT * FROM CUSTOMER");
}

public void deleteCustomer(int customerId) {
    try (Connection conn = getConnection()) {
        // First, delete the associated records in the BANK_TRANSACTION table
        String deleteTransactionSql = "DELETE FROM BANK_TRANSACTION WHERE ACCOUNT_ID IN (SELECT ACCOUNT_ID FROM ACCOUNT WHERE CUSTOMER_ID = ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(deleteTransactionSql)) {
            pstmt.setInt(1, customerId);
            pstmt.executeUpdate();
        }

        // Then, delete the associated records in the ACCOUNT table
        String deleteAccountSql = "DELETE FROM ACCOUNT WHERE CUSTOMER_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(deleteAccountSql)) {
            pstmt.setInt(1, customerId);
            pstmt.executeUpdate();
        }

        // Finally, delete the record in the CUSTOMER table
        String deleteCustomerSql = "DELETE FROM CUSTOMER WHERE CUSTOMER_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(deleteCustomerSql)) {
            pstmt.setInt(1, customerId);
            pstmt.executeUpdate();
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

}






