/*
    Defines a "Data Access Object" for the users class
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
import main.java.com.untitled.User;

public class UserDAO implements DAO{
    //variable declarations
    private IntegerProperty userID; //this is only used to generate the tableviews
    private StringProperty username;
    private StringProperty password;
    private StringProperty role;
    
    //getters and setters
    //userID
    public IntegerProperty getUserID() {
        return userID;
    }
    public void setUserID(int userID) {    
        this.userID = new SimpleIntegerProperty(userID);
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
    
    
    
    //constructor
    public UserDAO(User user){
        this.username = new SimpleStringProperty(user.getUsername());
        this.password = new SimpleStringProperty(user.getPassword());
        this.role = new SimpleStringProperty(user.getRole());
    }
    
    public UserDAO(){
        
    }

    @Override
    public void insertIntoTable() throws SQLException {
        //create the connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //define the sql statement
        String insertIntoUsersTable = "INSERT INTO users "
                                    + "(Username, Password, Role) "
                                    + "VALUES "
                                    + "('" + this.username.get() + "', "
                                    + "'" + this.password.get() + "', "
                                    + "'" + this.role.get() + "')";
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(insertIntoUsersTable);
        //execute the statement
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
        String deleteFromUsersTable = "DELETE FROM users WHERE Username = '" + this.username.get() + "'";
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(deleteFromUsersTable);
        //execute query
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
        String updateUsersTable = "UPDATE users SET "
                                + "Password = '" + this.password.get() + "', "
                                + "Role = '" + this.role.get() + "' "
                                + "WHERE Username = '" + this.username.get() + "'";
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(updateUsersTable);
        //execute the query
        ps.execute();
        
        //close the statement and the connection
        ps.close();
        connection.close();
    }
    
    //get the database records and store them in an observable list for tableview generation
    public ObservableList<UserDAO> getUserRecords() throws SQLException{
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //prepare the statment
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM users");
        
        ResultSet rs = ps.executeQuery(); //get the result set
        
        ObservableList<UserDAO> userList = getUserObjects(rs);  //get the user objects
        
        return userList;
        
    }//end public ObservableList<UserDAO> getUserRecords()
    
    //generate a observable list which is used to generate a table view 
    private ObservableList<UserDAO> getUserObjects(ResultSet resultSet) throws SQLException{
        
        ObservableList<UserDAO> userList = FXCollections.observableArrayList(); //create an observable array list
        
        while(resultSet.next()){
            UserDAO user = new UserDAO();
            user.setUserID(Integer.parseInt(resultSet.getString("UserID")));
            user.setUsername(resultSet.getString("Username"));
            System.out.println("Username = " + resultSet.getString("Username"));
            user.setRole(resultSet.getString("Role"));
                
            userList.add(user);
        }
        
        return userList;
    } //ObservableList<UserDAO> getUserObjects(ResultSet resultSet)
    
    //get a list of all users
    public ObservableList<String> getListOfAllUsers() throws SQLException {
        //create the conncetion object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement("SELECT Username FROM users");
        
        //get the result set
        ResultSet rs = ps.executeQuery();
        
        //add the results to an observable list
        ObservableList<String> usernames = FXCollections.observableArrayList();
        String username;
        
        while(rs.next()){
            //skip the admin as to not let the user modify it
            username = rs.getString("Username");
            
            if(username.equals("admin") == false){
                usernames.add(username);
            }
        }
        
        return usernames;
    }//end public ObservableList<String> getListOfAllUsers()
    
}
