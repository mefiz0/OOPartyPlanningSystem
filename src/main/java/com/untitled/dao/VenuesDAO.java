/*
    Defines a "Data Access Object" for the venues class
 */
package main.java.com.untitled.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import main.java.com.untitled.Venues;

public class VenuesDAO implements DAO{
    
    //variable declarations
    private String venueName;
    private String road;
    private String Building;
    private int capacity;
    private int price;
    
    //constructor
    public VenuesDAO(Venues venues){
        this.venueName = venues.getVenueName();
        this.road = venues.getRoad();
        this.Building = venues.getBuilding();
        this.capacity = venues.getCapacity();
        this.price = venues.getPrice();
    }

    @Override
    public void insertIntoTable() throws SQLException {
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //define the sql statement
        String insertIntoVenuesTable = "INSERT INTO venues "
                                     + "(Venue, Road, Building, Capacity, Price) "
                                     + "VALUES "
                                     + "('" + this.venueName + "', "
                                     + "'" + this.road + "', "
                                     + "'" + this.Building + "', "
                                     + this.capacity + ", "
                                     + this.price + ")";
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(insertIntoVenuesTable);
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
        String deleteFromVenuesTable = "DELETE FROM venues WHERE Venue = '" + this.venueName + "'";
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(deleteFromVenuesTable);
        //execute statement
        ps.execute();
        
        //close the statement and the conection
        ps.close();
        connection.close();
    }

    @Override
    public void updateTable() throws SQLException {
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //define the sql statement
        String updateVenuesTable = "UPDATE venues SET "
                                 + "Price = " + this.price + " "
                                 + "WHERE Venue = '" + this.venueName + "'";
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(updateVenuesTable);
        //execute statement
        ps.execute();
        
        //close the statement and the connection
        ps.close();
        connection.close();
    }
}
