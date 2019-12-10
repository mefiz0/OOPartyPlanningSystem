/*
    Define a "Data Access Object" for the addons class
 */
package main.java.com.untitled.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import main.java.com.untitled.Addon;

public class AddonsDAO implements DAO{
    //variable declarations
    private String addonType;
    private int addonPrice;
    
    //constructor
    public AddonsDAO(Addon addons){
        this.addonType = addons.getAddonType();
        this.addonPrice = addons.getAddonPrice();
    }
    
    @Override
    public void insertIntoTable() throws SQLException {
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //define the sql statement
        String insertIntoAddonsTable = "INSERT INTO addons "
                                     + "(Type, Price) "
                                     + "VALUES "
                                     + "('" + this.addonType + "', "
                                     + this.addonPrice + ")";
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(insertIntoAddonsTable);
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
        String deleteFromAddonsTable = "DELETE FROM addons WHERE Type = '" + this.addonType + "'";
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(deleteFromAddonsTable);
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
        String updateAddonsTable = "UPDATE addons SET "
                            + "Price = " + this.addonPrice + " "
                            + "WHERE Type = '" + this.addonType + "'";
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(updateAddonsTable);
        //execute the statement
        ps.execute();
        
        //close the statement and the connection
        ps.close();
        connection.close();
    }
    
}
