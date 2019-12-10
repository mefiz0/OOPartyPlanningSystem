/*
    Defines a "Data Access Object" for the users class
 */
package main.java.com.untitled.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.com.untitled.User;

public class UserDAO implements DAO{
    //variable declarations
    private StringProperty username;
    private StringProperty password;
    private StringProperty role;

    public StringProperty getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = new SimpleStringProperty(username);
    }

    public StringProperty getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = new SimpleStringProperty(password);
    }

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
                                    + "'" + this.role.get() + "'";
        
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
    
    public ObservableList<UserDAO> getAllDatabaseRecords() throws SQLException{
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM users");
        
        ResultSet rs = ps.executeQuery(); //get the result set
        
        ObservableList<UserDAO> userList = getUserObjects(rs);
        
        return userList;
        
    }
    
    //generate a observable list which is used to generate a table view
    private ObservableList<UserDAO> getUserObjects(ResultSet resultSet) throws SQLException{
        
        ObservableList<UserDAO> userList = FXCollections.observableArrayList(); //create an observable array list
        
        while(resultSet.next()){
            UserDAO user = new UserDAO();
            user.setUsername(resultSet.getString("Username"));
            System.out.println("Username = " + resultSet.getString("Username"));
            user.setPassword(resultSet.getString("Password"));
            user.setRole(resultSet.getString("Role"));
                
            userList.add(user);
        }
        
        return userList;
    } 
    
}
