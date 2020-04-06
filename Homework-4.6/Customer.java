package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Customer {

    private static final String DB_URL = "localhost:3306";
    private static final String FULL_DB_URL = "jdbc:mysql://" + DB_URL + "/eshop_final?zeroDateTimeBehavior=CONVERT_TO_NULL&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWD = "root";
    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;

    private int id;
    private String first_name;
    private String last_name;
    private String email;

    // o default constructor σετάρει πάντα την σύνδεση, έτσι
    //με το που δημιουργείται ένα αντικείμενο μπορούν κατευθείαν
    //να χρησιμοποιηθούν sql εντολές
    public Customer() {
        getConnection();
        setStatement();
    }

    //ο constructor καλεί τον default
    public Customer(int id, String first_name, String last_name, String email) {
        this();
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;

    }

    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection(FULL_DB_URL, DB_USER, DB_PASSWD);
            return connection;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Statement getStatement() {
        return statement;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static void setStatement() {
        try {
            statement = connection.createStatement();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

//create    
public void createCustomersTable() {
              
        String sql = "CREATE TABLE IF NOT EXISTS `customers` ("
                + "`id` INT NOT NULL AUTO_INCREMENT,"
                + "`first_name` VARCHAR(50) NOT NULL,"
                + "`last_name` VARCHAR(50) NOT NULL,"
                + "`email` VARCHAR(50) NOT NULL,"
                + "PRIMARY KEY(`id`));";
        Statement st = getStatement();        
        try {
            connection.setCatalog("eshop_final");
            st.executeUpdate(sql);           
        } catch (SQLException ex) {
            ex.printStackTrace();
            
        }
    }

//select from table
    public ResultSet selectFromCustomer() {
        try {
            String querry = "SELECT * FROM `customers`;";
            ResultSet rs = getStatement().executeQuery(querry);
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ResultSet selectFromCustomer(String fieldToBeReturned) {
        try {
            String querry = "SELECT " + fieldToBeReturned + " FROM `customers`;";
            ResultSet rs = getStatement().executeQuery(querry);
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ResultSet selectFromCustomer(String conditionField, String conditionValue) {
        try {
            setStatement();
            String querry = "SELECT * FROM `customers` WHERE `" + conditionField + "` = '" + conditionValue + "';";
            ResultSet rs = getStatement().executeQuery(querry);
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ResultSet selectFromCustomer(String fieldToBeReturned, String conditionField, String conditionValue) {
        try {
            setStatement();
            String querry = "SELECT `" + fieldToBeReturned + "` FROM `customers` WHERE `" + conditionField + "` = '" + conditionValue + "';";
            ResultSet rs = getStatement().executeQuery(querry);
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    //insert
    
    public int insertIntoCustomers(Customer customer) {
        int result = 0;
        String customerData = "'" + customer.getFirst_name() + "', '" 
                + customer.getLast_name() + "', '" + customer.getEmail() + "'";
        String sql = "INSERT INTO `customers`(`first_name`, `last_name`, `email`) "
                + "VALUES(" + customerData +");";
        
        Statement st = getStatement();
        try {
            result = st.executeUpdate(sql);
            System.out.println(result  + " record(s) inserted.");
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return result;
        }
    }
    
    //update
    public int updateCustomers(String fieldToBeUpdated, String valueToBeInserted, String conditionField, String conditionValue) {
        int result = 0;        
        String sql = "UPDATE `customers` SET `" + fieldToBeUpdated + "` = '" + valueToBeInserted + "' WHERE `" + conditionField + "` = '" + conditionValue + "';";    
        System.out.println(sql);
        Statement st = getStatement();
        try {
            result = st.executeUpdate(sql);
            System.out.println(result  + " record(s) updated.");
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return result;
        }
    }

    //delete
    public int deleteFromCustomers(String conditionField, String conditionValue) {
        int result = 0;        
        String sql = "DELETE FROM `customers` WHERE `" + conditionField + "` = '" + conditionValue + "';";
        Statement st = getStatement();        
        try {
            result = st.executeUpdate(sql);
            System.out.println(result  + " record(s) deleted.");
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return result;
        }
    }

    //print
    public void printCustomerResultsAllFields(ResultSet rs) {
        try {
            while (rs.next()) {
                System.out.println("id: " + rs.getString(1)
                        + ", first_name: "
                        + rs.getString(2)
                        + ", last_name: "
                        + rs.getString(3)
                        + ", email: "
                        + rs.getString(4));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void printCustomerResultsSpecificFields(ResultSet rs, int numberOfFieldsToPrint) {        
        try {
            switch (numberOfFieldsToPrint) {
                case 1:
                    while (rs.next()) {
                        System.out.println(rs.getString(1));
                    }
                case 2:
                    while (rs.next()) {
                        System.out.println(rs.getString(1) + ", " + rs.getString(2));
                    }
                case 3:
                    while (rs.next()) {
                        System.out.println(rs.getString(1) + ", " + rs.getString(2) + ", " + rs.getString(3));
                    }
                case 4:
                    while (rs.next()) {
                        System.out.println(rs.getString(1) + ", " + rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(4));
                    }
            }
        } catch (SQLException ex) {
            System.out.println("Something went wrong...");
            ex.printStackTrace();
        }
    }

}
