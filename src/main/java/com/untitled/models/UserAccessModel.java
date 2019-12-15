/*
Defines a "data access object" for user accesses
*/
package main.java.com.untitled.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserAccessModel extends UserModel{
    
    //variable declarations
    private ObjectProperty<Timestamp> accessTime;
    private ObjectProperty<Timestamp> loggedOutTime;
    
    //getters and setters
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
    public UserAccessModel(){
        //empty
    }
    
    //insert a new record into the table
    @Override
    public void insertIntoTable() throws SQLException{
         //create a timestamp object
         Timestamp accessTime = new Timestamp(System.currentTimeMillis());
         System.out.println(accessTime);
         //user id
         int userID = this.getUserID().get();
         System.out.println(userID);
         
         //create a connection object
         Connection connection = DriverManager.getConnection(JDBC_URL);
         
         //define the sql query
         String insertIntoAccessTable = "INSERT INTO access_history "
                                      + "(UserID, AccessTime) "
                                      + "VALUES "
                                      + "(" + userID + ", "
                                      + "'" + accessTime + "')";
         
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
        //get the current time
        Timestamp loggedOutTime = new Timestamp(System.currentTimeMillis());
        
        //create the connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //define the sql statement
        String updateAccessTable = "UPDATE access_history SET "
                                 + "LoggedOutTime = '" + loggedOutTime + "'"
                                 + " WHERE AccessID = (SELECT MAX(AccessID) FROM access_history)";
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(updateAccessTable);
        //execute the query
        ps.execute();
        
        //close the statement and the connection
        ps.close();
        connection.close();
    }
    
    public ObservableList<UserAccessModel> getAccessRecords() throws SQLException{
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
        
        ObservableList<UserAccessModel> accessList = getAccessObject(rs);  //get the user objects
        
        return accessList;
    }
    
    //get a list of user access data access objects
    private ObservableList<UserAccessModel> getAccessObject(ResultSet resultSet) throws SQLException{
        ObservableList<UserAccessModel> accessList = FXCollections.observableArrayList(); //create an observable array list
        
        while(resultSet.next()){
            UserAccessModel userAccess = new UserAccessModel();
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
