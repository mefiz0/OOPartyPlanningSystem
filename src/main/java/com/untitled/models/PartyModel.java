/*
    Defines a "data access object" for the add to parties class
 */
package main.java.com.untitled.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.com.untitled.Party;
import static main.java.com.untitled.models.Model.JDBC_URL;

public class PartyModel extends PartyBaseModel{
    
    //variable declarations
    private IntegerProperty rowNum; //user to generate the row number in table view
    private StringProperty taskOne;
    private StringProperty taskTwo;
    private StringProperty taskThree;
    private StringProperty taskFour;
    
    //getters and setters
    //row num
    public IntegerProperty getRowNum() {
        return rowNum;
    }
    public void setRowNum(int rowNum) {
        this.rowNum = new SimpleIntegerProperty(rowNum);
    }
    
    //task one
    public StringProperty getTaskOne() {
        return taskOne;
    }
    public void setTaskOne(String taskOne) {
        this.taskOne = new SimpleStringProperty(taskOne);
    }
    
    //task two
    public StringProperty getTaskTwo() {
        return taskTwo;
    }
    public void setTaskTwo(String taskTwo) {
        this.taskTwo = new SimpleStringProperty(taskTwo);
    }
    
    //task three
    public StringProperty getTaskThree() {
        return taskThree;
    }
    public void setTaskThree(String taskThree) {
        this.taskThree = new SimpleStringProperty(taskThree);
    }

    //task four
    public StringProperty getTaskFour() {
        return taskFour;
    }
    public void setTaskFour(String taskFour) {
        this.taskFour = new SimpleStringProperty(taskFour);
    }
    
    //constructor
    public PartyModel(Party addParty){
        this.setType(addParty.getType());
        this.setPrice(addParty.getPrice());
        this.taskOne = new SimpleStringProperty(addParty.getTaskOne());
        this.taskTwo = new SimpleStringProperty(addParty.getTaskTwo());
        this.taskThree = new SimpleStringProperty(addParty.getTaskThree());
        this.taskFour = new SimpleStringProperty(addParty.getTaskFour());
    }
    
    public PartyModel(){
        //empty
    }
    
    @Override
    public void insertIntoTable() throws SQLException {
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //define the sql statement
        String insertIntoPartiesTable = "INSERT INTO parties "
                                         + "(Type, Price, TaskOne, TaskTwo, TaskThree, TaskFour) "
                                         + "VALUES "
                                         + "('" + this.getType().get() + "', "
                                         + this.getPrice().get() +", "
                                         + "'" + this.taskOne.get() + "', "
                                         + "'" + this.taskTwo.get() + "', "
                                         + "'" + this.taskThree.get() + "', "
                                         + "'" + this.taskFour.get() +"')";
        
       //prepare the sql statement
       PreparedStatement ps = connection.prepareStatement(insertIntoPartiesTable);
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
        String deleteFromPartiesTable = "DELETE FROM parties WHERE Type = '" + this.getType().get() + "'";
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(deleteFromPartiesTable);
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
        String updatePartiesTable = "UPDATE parties SET "
                                  + "Price = " + this.getPrice().get() + ", "
                                  + "TaskOne = '" + this.taskOne.get() + "', "
                                  + "TaskTwo = '" + this.taskTwo.get() + "', "
                                  + "TaskThree = '" + this.taskThree.get() + "', "
                                  + "TaskFour = '" + this.taskFour.get() + "' "
                                  + "WHERE Type = '" + this.getType().get() + "'";
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(updatePartiesTable);
        //execute statement
        ps.execute();
        
        //close the statement and the connection
        ps.close();
        connection.close();
    }
    
    //get the database records and store them in an observable list for tableview generation
    public ObservableList<PartyModel> getPartyRecords() throws SQLException{
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //prepare the statment
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM parties");
        
        ResultSet rs = ps.executeQuery(); //get the result set
        
        ObservableList<PartyModel> partiesList = getPartyObjects(rs);  //get the user objects
        
        //close the statement and the connection
        ps.close();
        connection.close();
        
        return partiesList;
    }
    
    //generate a observable list which is used to generate a table view
    private ObservableList<PartyModel> getPartyObjects(ResultSet rs) throws SQLException{
        ObservableList<PartyModel> partiesList = FXCollections.observableArrayList(); //create an observable array list
        
        int i = 1; //row number generations
        
        while(rs.next()){
            PartyModel party = new PartyModel();
            party.setRowNum(i);
            party.setType(rs.getString("Type"));
            party.setPrice(rs.getInt("Price"));
            party.setTaskOne(rs.getString("TaskOne"));
            party.setTaskTwo(rs.getString("TaskTwo"));
            party.setTaskThree(rs.getString("TaskThree"));
            party.setTaskFour(rs.getString("TaskFour"));
                
            i++; //increment row number
            partiesList.add(party);
        }
        
        //close the statement and the connection
        
        return partiesList;
    }
    
    //get a list of all party types
     public ObservableList<String> getListOfAllPartyTypes() throws SQLException {
        //create the conncetion object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement("SELECT Type FROM parties");
        
        //get the result set
        ResultSet rs = ps.executeQuery();
        
        //add the results to an observable list
        ObservableList<String> partyTypes = FXCollections.observableArrayList();
        
        while(rs.next()){;
            partyTypes.add(rs.getString("Type"));
        }
        
        //close the statement and the connection
        ps.close();
        connection.close();
        
        return partyTypes;
    }//end getListOfAllParty Types
     
     //get a hashmap of party info based on the customerID selected
    public HashMap<String, String> getPartyDataBasedOnType(String type) throws SQLException{
        //create the connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM parties WHERE Type = '" + type + "'");
        //get the result set
        ResultSet rs = ps.executeQuery();
        
        //define the hashmap to store customer data
        HashMap<String, String> partyData = new HashMap<>();
        
        while(rs.next()){
           partyData.put("price", rs.getString("Price"));
           partyData.put("taskOne", rs.getString("TaskOne"));
           partyData.put("taskTwo", rs.getString("TaskTwo"));
           partyData.put("taskThree", rs.getString("TaskThree"));
           partyData.put("taskFour", rs.getString("TaskFour"));
        }
        
       //close the statement and the connection
        ps.close();
        connection.close();
        
        return partyData;
    }
    
    //get the price of a selected party
    public int getPriceOfPartyBasedOnType(String type) throws SQLException{
        //create a new connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement("SELECT Price FROM parties WHERE Type = '" + type + "'");
        //create the result set
        ResultSet rs = ps.executeQuery();
        
        //get the results
        int price = rs.getInt("Price");
        
        //close the statement and the connection
        ps.close();
        connection.close();
        
        return price;
    }
}
