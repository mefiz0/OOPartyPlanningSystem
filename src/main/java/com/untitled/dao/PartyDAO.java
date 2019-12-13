/*
    Defines a "data access object" for the add to parties class
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
import main.java.com.untitled.Party;
import static main.java.com.untitled.dao.DAO.JDBC_URL;

public class PartyDAO extends PartyBaseDAO{
    
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
    public PartyDAO(Party addParty){
        this.setType(addParty.getType());
        this.setPrice(addParty.getPrice());
        this.taskOne = new SimpleStringProperty(addParty.getTaskOne());
        this.taskTwo = new SimpleStringProperty(addParty.getTaskTwo());
        this.taskThree = new SimpleStringProperty(addParty.getTaskThree());
        this.taskFour = new SimpleStringProperty(addParty.getTaskFour());
    }
    
    public PartyDAO(){
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
                                         + "'" + this.taskOne.get() + ", "
                                         + "'" + this.taskTwo.get() + ", "
                                         + "'" + this.taskThree.get() + ", "
                                         + "'" + this.taskFour.get() +")";
        
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
                                  + "TaskOne = '" + this.taskOne.get() + ", "
                                  + "TaskTwo = '" + this.taskTwo.get() + ", "
                                  + "TaskThree = '" + this.taskThree.get() + ", "
                                  + "TaskFour = '" + this.taskFour.get() + " "
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
    public ObservableList<PartyDAO> getPartyRecords() throws SQLException{
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //prepare the statment
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM parties");
        
        ResultSet rs = ps.executeQuery(); //get the result set
        
        ObservableList<PartyDAO> partiesList = getPartyObjects(rs);  //get the user objects
        
        return partiesList;
    }
    
    //generate a observable list which is used to generate a table view
    private ObservableList<PartyDAO> getPartyObjects(ResultSet rs) throws SQLException{
        ObservableList<PartyDAO> partiesList = FXCollections.observableArrayList(); //create an observable array list
        
        int i = 1; //row number generations
        
        while(rs.next()){
            PartyDAO party = new PartyDAO();
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
        
        return partyTypes;
    }
}
