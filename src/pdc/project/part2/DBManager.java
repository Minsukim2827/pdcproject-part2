package pdc.project.part2;

import java.sql.Connection;
import java.sql.DriverManager;

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
}


