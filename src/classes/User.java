/*

 */
package classes;

import static database.UserSQL.*;
import java.sql.SQLException;

public class User {
    private  String username;
    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return username;
    }
    
    private String password;
    public void setPassword(String password){
        this.password = password;
    }
    
    private String role;
    public void setRole(String role){
        this.role = role;
    }
    public String getRole(){
        return role;
    }
    
    public User(){
        //empty
    }
    
    public User(String username, String password, String role){
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
    
    //get the userID
    public int getUserID() throws ClassNotFoundException, SQLException{
        
        return getUserIDSQL(this.username);
    }//end getUserID
    
    //authenticate the login
    public int authenticateLogin() throws SQLException, ClassNotFoundException{
        
        return authenticateLoginSQL(this.username, this.password);
    }//end authenticateLogin
    
    public void insertUserAccessRecord() throws ClassNotFoundException, SQLException{
        insertUserAccessRecordSQL(this.username);
    }//end insertUserAccessRecord
    
    //get the role of the currently accessed user
    public String getUserRole() throws ClassNotFoundException, SQLException{
        return getUserRoleSQL();
    }//end getUserRole
    
    //add a new user to the database
    public void insertNewUser() throws ClassNotFoundException, SQLException{
        insertNewUserSQL(this.username, this.password, this.role);
    }//end insertNewUser
    
    //remove a user from the database
    public void removeUser() throws ClassNotFoundException, SQLException{
        removeUserSQL(this.username);
    }//end remove user
    
    //update a users password and role
    public void updateUser() throws ClassNotFoundException, SQLException{
        updateUserSQL(this.username, this.password, this.role);
    }//end update user
    
    
}
