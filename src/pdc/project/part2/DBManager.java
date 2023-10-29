package pdc.project.part2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBManager
{
    private static final String URL = "jdbc:derby:BankDB;create=true"; 
    private static final String USER_NAME = "bank";
    private static final String PASSWORD= "bank";

    Connection con;
    
    public DBManager()
    {
        makeConnection();
    }

    public static void main(String[] args)
    {
        DBManager playerDB = new DBManager();
        System.out.println(playerDB.getConnection());
    }

    public Connection getConnection()
    {
        return this.con;
    }

    public void makeConnection()
    {
        if(this.con == null)
        {
            try
            {
                con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            }
            catch(Exception ex)
            {
                System.out.println(ex.getMessage());
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
}


