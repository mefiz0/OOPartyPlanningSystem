/*
    Defines a "Data Access Object" for the customers class
 */
package main.java.com.untitled.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import main.java.com.untitled.Customer;

public class CustomerDAO implements DAO{
    //variable declarations
    private String idNum;
    private String name;    
    private int bankAccountNum;    
    private int contactNum;    
    private String emailAddress;
    
    //constructor
    public CustomerDAO(Customer customer){
        this.idNum = customer.getIdNum();
        this.name = customer.getName();
        this.bankAccountNum = customer.getBankAccountNum();
        this.contactNum = customer.getContactNum();
        this.emailAddress = customer.getEmailAddress();
    }

    @Override
    public void insertIntoTable() throws SQLException {
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //define the sql statement
        String insertIntoCustomersTable = "INSERT INTO customers "
                                        + "(ID, Name, BankAccountNumber, ContactNumber, Email) "
                                        + "VALUES "
                                        + "('" + this.idNum + "', "
                                        + "'" + this.name + "', "
                                        + this.bankAccountNum + ", "
                                        + this.contactNum + ", "
                                        + "'" + this.emailAddress + "')";
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(insertIntoCustomersTable);
        //execute statement
        ps.execute();
        
        //close the statement and the connection
        ps.close();
        connection.close();
    }

    @Override
    public void deleteFromTable() throws SQLException {
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //define the sql statement
        String deleteFromCustomersTable = "DELETE FROM customers WHERE ID = '" + this.idNum + "'";
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(deleteFromCustomersTable);
        //execute the statement
        ps.execute();
        
        //close the statement and the connection
        ps.close();
        connection.close();
    }

    @Override
    public void updateTable() throws SQLException {
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //define the sql statement
        String updateCustomersTable = "UPDATE customers SET "
                                    + "Name = '" + this.name + "', "
                                    + "BankAccountNumber = " + this.bankAccountNum + ", "
                                    + "ContactNumber = " + this.contactNum + ", "
                                    + "Email = '" + this.emailAddress + "'";
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(updateCustomersTable);
        //execute statement
        ps.execute();
        
        //close the statement and the connection
        ps.close();
        connection.close();
    }
    
    
}
