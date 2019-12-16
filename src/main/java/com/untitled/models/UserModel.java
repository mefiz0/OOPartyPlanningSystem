/*
    Defines a "Data Access Object" for the users class
 */
package main.java.com.untitled.models;

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
import main.java.com.untitled.Services.User;

public class UserModel implements Model{
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
    public UserModel(User user){
        this.username = new SimpleStringProperty(user.getUsername());
        this.password = new SimpleStringProperty(user.getPassword());
        this.role = new SimpleStringProperty(user.getRole());
    }
    
    public UserModel(String username, String password){
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
    }
    
    public UserModel(){
        
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
    
     //connects to the db to cross check username and password
    /*
    Checks if there is a user ID with the username and password given
    if there is the user id is returned to the loginstatuscheck so it will be >1
    however if there isnt the loginstatuscheck will be at 0.
    so the user can be authenticated based on that
    */
    public int userLogin() throws SQLException{
        
        int loginStatusCheck = 0; //this stores the values of the login status. true if login is a success
       
        Connection connection = DriverManager.getConnection("jdbc:derby:Database;create=true");
        
        //define the statement
        String loginDetails = "SELECT UserID FROM users WHERE Username='" + username.get()
                + "' AND Password='" + password.get() + "'";
        
        PreparedStatement ps = connection.prepareStatement(loginDetails);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            loginStatusCheck = rs.getInt("UserID");
        }//end while
        
        
       return loginStatusCheck;
    }//end int userLogin()
    
    //get the database records and store them in an observable list for tableview generation
    public ObservableList<UserModel> getUserRecords() throws SQLException{
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //prepare the statment
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM users");
        
        ResultSet rs = ps.executeQuery(); //get the result set
        
        ObservableList<UserModel> userList = getUserObjects(rs);  //get the user objects
        
        return userList;
        
    }//end public ObservableList<UserDAO> getUserRecords()
    
    //generate a observable list which is used to generate a table view 
    private ObservableList<UserModel> getUserObjects(ResultSet resultSet) throws SQLException{
        
        ObservableList<UserModel> userList = FXCollections.observableArrayList(); //create an observable array list
        
        while(resultSet.next()){
            UserModel user = new UserModel();
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
    
    //get user role
    public String getUserRole(String username) throws SQLException{
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement("SELECT Role FROM users WHERE Username = '" + username + "'");
        
        //get the result set
        ResultSet rs = ps.executeQuery();
        
        String userRole = null;
        
        //get the role
        while(rs.next()){
            userRole = rs.getString("Role");
        }
        
        return userRole;
    }
}
