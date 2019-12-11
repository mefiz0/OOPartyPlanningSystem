/*
Defines a "data access object" for user accesses
*/
package main.java.com.untitled.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserAccessDAO implements DAO{
    
    //variable declarations
    private IntegerProperty IdOfUser; //this is only used to generate the tableviews
    private StringProperty username;
    private StringProperty role;
    private ObjectProperty<Timestamp> accessTime;
    private ObjectProperty<Timestamp> loggedOutTime;
    
    //getters and setters
    //userID
    public IntegerProperty getIDOfUser() {
        return IdOfUser;
    }
    public void setUserID(int userID) {    
        this.IdOfUser = new SimpleIntegerProperty(userID);
    }
    
    //username
    public StringProperty getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = new SimpleStringProperty(username);
    }
    
    //role
    public StringProperty getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = new SimpleStringProperty(role);
    }
    
    //access time
    public ObjectProperty<Timestamp> getAccessTime() {
        return accessTime;
    }
    public void setAccessTime(Timestamp accessTime) {
        this.accessTime = new SimpleObjectProperty<>(accessTime);
    }
    
    //logged out time
    public ObjectProperty<Timestamp> getLoggedOutTime() {
        return loggedOutTime;
    }
    public void setLoggedOutTime(Timestamp loggedOutTime) {
        this.loggedOutTime = new SimpleObjectProperty<>(loggedOutTime);
    }
    
    //end getters and setters
    
    //constructor
    public UserAccessDAO(){
        //empty
    }
    
    //insert a new record into the table
    @Override
    public void insertIntoTable() throws SQLException{
         //create a timestamp object
         Timestamp accessTime = new Timestamp(System.currentTimeMillis());
         //user id
         int userID = Integer.parseInt(this.getIDOfUser().toString());
         
         //create a connection object
         Connection connection = DriverManager.getConnection(JDBC_URL);
         
         //define the sql query
         String insertIntoAccessTable = "INSERT INTO access_history "
                                      + "(UserID, AccessTime) "
                                      + "VALUES "
                                      + "(" + userID + ", "
                                      + accessTime + ")";
         
         //prepare the statement
         PreparedStatement ps = connection.prepareStatement(insertIntoAccessTable);
         //execute the query
         ps.execute();
         
         //close the statement and the connection
         ps.close();
         connection.close();
                                      
    } 
    
    @Override
    public void deleteFromTable() throws SQLException {
        throw new UnsupportedOperationException("This feature is not avaliable!");
    }
    
    //update the logged out time of the table
    @Override
    public void updateTable() throws SQLException{
        //get the userid
        int userID = Integer.parseInt(this.getIDOfUser().toString());
        
        //get the current time
        Timestamp loggedOutTime = new Timestamp(System.currentTimeMillis());
        
        //create the connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //define the sql statement
        String updateAccessTable = "UPDATE access_history SET "
                                 + "LoggedOutTime = " + loggedOutTime
                                 + " WHERE UserID = " + userID;
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(updateAccessTable);
        //execute the query
        ps.execute();
        
        //close the statement and the connection
        ps.close();
        connection.close();
    }
    
    public ObservableList<UserAccessDAO> getAccessRecords() throws SQLException{
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //define the statement
        String selectUserAccessRecords = "SELECT access_history.UserID,"
                                       + "Users.Username, "
                                       + "Users.Role, "
                                       + "access_history.AccessTime, "
                                       + "access_history.LoggedOutTime"
                                       + " FROM access_history"
                                       + " LEFT JOIN users"
                                       + " ON access_history.UserID = users.UserID";
        //prepare the statment
        PreparedStatement ps = connection.prepareStatement(selectUserAccessRecords);
        
        ResultSet rs = ps.executeQuery(); //get the result set
        
        ObservableList<UserAccessDAO> accessList = getAccessObject(rs);  //get the user objects
        
        return accessList;
    }
    
    //get a list of user access data access objects
    private ObservableList<UserAccessDAO> getAccessObject(ResultSet resultSet) throws SQLException{
        ObservableList<UserAccessDAO> accessList = FXCollections.observableArrayList(); //create an observable array list
        
        while(resultSet.next()){
            UserAccessDAO userAccess = new UserAccessDAO();
            userAccess.setUserID(resultSet.getInt("UserID"));
            userAccess.setUsername(resultSet.getString("Username"));
            userAccess.setRole(resultSet.getString("Role"));
            userAccess.setAccessTime(resultSet.getTimestamp("AccessTime"));
            userAccess.setLoggedOutTime(resultSet.getTimestamp("LoggedOutTime"));
            
                
            accessList.add(userAccess);
        }
        
        return accessList;
    }
    
}
