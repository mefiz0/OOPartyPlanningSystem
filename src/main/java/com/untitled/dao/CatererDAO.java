/*
    Defines a "Data Access Object" for the catering class
 */
package main.java.com.untitled.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import main.java.com.untitled.Caterer;

public class CatererDAO implements DAO{
    //variable declarations
    private String caterer;
    private int price;
    
    //constructor
    public CatererDAO(Caterer catering){
        this.caterer = catering.getCaterer();
        this.price = catering.getPrice();
    }

    @Override
    public void insertIntoTable() throws SQLException {
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //define the sql statement
        String insertIntoCaterersTable = "INSERT INTO caterers "
                                       + "(Caterer, Price) "
                                       + "VALUES "
                                       + "('" + this.caterer + "', "
                                       + this.price + ")";
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(insertIntoCaterersTable);
        //execute the statement
        ps.execute();
        
        //close the statement and the conneciton
        ps.close();
        connection.close();
        
    }

    @Override
    public void deleteFromTable() throws SQLException {
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //define the sql statement
        String deleteFromCaterersTable = "DELETE FROM caterers WHERE Caterer = '" + this.caterer + "'";
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(deleteFromCaterersTable);
        //execute statement
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
        String updateCaterersTable = "UPDATE caterers SET "
                            + "Price = " + this.price + " "
                            + "WHERE Caterer = '" + this.caterer + "'";
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(updateCaterersTable);
        //execute the statement
        ps.execute();
        
        //close the statement and the connection
        ps.close();
        connection.close();
    }
}
