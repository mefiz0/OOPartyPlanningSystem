/*
    Defines a "Data Access Object" for the catering class
 */
package main.java.com.untitled.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.com.untitled.Caterer;
import static main.java.com.untitled.dao.DAO.JDBC_URL;

public class CatererDAO implements DAO{
    //variable declarations
    private IntegerProperty rowNum; //only used for the row number generation in table view
    private StringProperty caterer;
    private IntegerProperty price;
    
    //getters and setters
    //row number
    public IntegerProperty getRowNum() {
        return rowNum;
    }
    public void setRowNum(int rowNum) {
        this.rowNum = new SimpleIntegerProperty(rowNum);
    }
    
    //caterer
    public StringProperty getCaterer() {
        return caterer;
    }
    public void setCaterer(String caterer) {
        this.caterer = new SimpleStringProperty(caterer);
    }

    //price
    public IntegerProperty getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = new SimpleIntegerProperty(price);
    }
    
    //end getters and setters
    
    //constructor
    public CatererDAO(Caterer catering){
        this.caterer = new SimpleStringProperty(catering.getCaterer());
        this.price = new SimpleIntegerProperty(catering.getPrice());
    }
    //empty constructor
    public CatererDAO(){
        //empty
    }

    @Override
    public void insertIntoTable() throws SQLException {
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //define the sql statement
        String insertIntoCaterersTable = "INSERT INTO caterers "
                                       + "(Caterer, Price) "
                                       + "VALUES "
                                       + "('" + this.caterer.get() + "', "
                                       + this.price.get() + ")";
        
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
        String deleteFromCaterersTable = "DELETE FROM caterers WHERE Caterer = '" + this.caterer.get() + "'";
        
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
                            + "Price = " + this.price.get() + " "
                            + "WHERE Caterer = '" + this.caterer.get() + "'";
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(updateCaterersTable);
        //execute the statement
        ps.execute();
        
        //close the statement and the connection
        ps.close();
        connection.close();
    }
    
    //get the data for table view generation
    public ObservableList<CatererDAO> getCaterersRecords() throws SQLException{
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //define the statement
        String selectAddonsRecords = "SELECT * FROM caterers";
        //prepare the statment
        PreparedStatement ps = connection.prepareStatement(selectAddonsRecords);
        
        ResultSet rs = ps.executeQuery(); //get the result set
        
        ObservableList<CatererDAO> caterersList = getCatererObjects(rs);  //get the user objects
        
        return caterersList;
    }//end ObservableList<CatererDAO> getCaterersRecords()
    
    //get the add on objects from the database
    private ObservableList<CatererDAO> getCatererObjects(ResultSet rs) throws SQLException{
        ObservableList<CatererDAO> caterersList = FXCollections.observableArrayList();
        
        int i = 1; //this is a counter to set the rows in the table view
        while(rs.next()){
            CatererDAO catererDAO = new CatererDAO();
            catererDAO.setRowNum(i);
            catererDAO.setCaterer(rs.getString("Caterer"));
            catererDAO.setPrice(rs.getInt("Price"));
            
            caterersList.add(catererDAO);
            i++;
        }
        
        return caterersList;
        
    }//end ObservableList<CatererDAO> getCatererObjects(ResultSet rs)
    
    //get a list of all the caterers
    public ObservableList<String> getAllCaterers() throws SQLException{
        //create the conncetion object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement("SELECT Caterer FROM caterers");
        
        //get the result set
        ResultSet rs = ps.executeQuery();
        
        //add the results to an observable list
        ObservableList<String> caterersList = FXCollections.observableArrayList();
        
        while(rs.next()){
            caterersList.add(rs.getString("Caterer"));
        }
        
        return caterersList;
    }//end getAllCaterers()
}
