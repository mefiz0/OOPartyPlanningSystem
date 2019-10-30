/*

 */
package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UserSQL {
    
    //get the userID
    public static int getUserIDSQL(String username) throws ClassNotFoundException, SQLException{
        
        String selectUserIDSQL = "SELECT UserID FROM users WHERE Username = '" + username +"'";
        int userID = 0;
        
        //create connection object
        DatabaseConnection connection = new DatabaseConnection();
        PreparedStatement statement = connection.DatabaseConnection().prepareStatement(selectUserIDSQL);
        
        //get the userID
        ResultSet rs = statement.executeQuery();
        
        if(rs.next()){
            userID = Integer.parseInt(rs.getString("UserID"));
        }//end if
        
        rs.close();
        statement.close();
        connection.DatabaseConnection().close();
        
        return userID;
    }//end getUserID
    
    //insert a new user to the access records list
    public static void insertUserAccessRecordSQL(String username) throws ClassNotFoundException, SQLException{
        
        //get the userID
        int userID = getUserIDSQL(username);

        Timestamp accessTime = new Timestamp(System.currentTimeMillis()); //get the current time
            
        String insetIntoUserAccessHistory = "INSERT INTO access_history (UserID, AccessTime) VALUES("
                                          + userID + ", '" + accessTime + "')";
            
        //create connection object
        DatabaseConnection connection = new DatabaseConnection();
        PreparedStatement statement = connection.DatabaseConnection().prepareStatement(insetIntoUserAccessHistory);
        statement.execute();

            
        statement.close();
        connection.DatabaseConnection().close();

    }//end insertUserAccessRecord
    
    //get the role of the current user
    public static String getUserRoleSQL() throws ClassNotFoundException, SQLException{
        
        String role = null;
        
        //first we get the latest entered id into the access_history table
        String getMaxFromAccessHistory = "SELECT MAX(AccessID) FROM access_history";
        int userID = 0;
        int accessID = 0;
        
        //create the connection object
        DatabaseConnection connection = new DatabaseConnection();
        PreparedStatement statement;
        statement = connection.DatabaseConnection().prepareStatement(getMaxFromAccessHistory);
        
        //get the result set
        ResultSet rs = statement.executeQuery();
        if(rs.next()){
            accessID = rs.getInt(1);
        }//end if(rs.next)
        System.out.println(accessID);
        rs.close();
        statement.close();
        connection.DatabaseConnection().close();
        
        //then we get the userID
        String getUserID = "SELECT UserID FROM access_history WHERE AccessID = " + accessID ;
        statement = connection.DatabaseConnection().prepareStatement(getUserID);
        rs = statement.executeQuery();
        
        if(rs.next()){
            userID = Integer.parseInt(rs.getString("UserID"));
        }//end if(rs.next())
        System.out.println(userID);
        rs.close();
        statement.close();
        connection.DatabaseConnection().close();
        
        if(userID != 0){
            //get the user role
            String getUserRole = "SELECT Role FROM users WHERE UserID = " + userID;
            
            statement = connection.DatabaseConnection().prepareStatement(getUserRole);
            rs = statement.executeQuery();
            
            if(rs.next()){
                role = rs.getString("Role");
            }//end if(rs.next())
            statement.close();
            rs.close();
            connection.DatabaseConnection().close();
        }//end if(userID != 0)
        
        return role;
    }//end getRole
    
    //insert a new user to the users table
    public static void insertNewUserSQL(String username, String password, String role) throws ClassNotFoundException, SQLException{
        String newUserSql = "INSERT INTO users (Username, Password, Role) VALUES("
                          + "'" + username + "', "
                          + "'" + password + "', "
                          + "'" + role + "')";
        
        //create connection object
        DatabaseConnection connection = new DatabaseConnection();
        PreparedStatement statement = connection.DatabaseConnection().prepareStatement(newUserSql);
        statement.execute();
        statement.close();
        connection.DatabaseConnection().close();
    }//end insert newUser
    
    //remove a user from the users table
    public static void removeUserSQL(String username) throws ClassNotFoundException, SQLException{
        String deleteUserSQL = "DELETE FROM users WHERE Username = '" + username + "'";
        
        //create connection object
        DatabaseConnection connection = new DatabaseConnection();
        PreparedStatement statement = connection.DatabaseConnection().prepareStatement(deleteUserSQL);
        statement.execute();
        statement.close();
        connection.DatabaseConnection().close();
    }//end removeUser
    
    //update the password and role of a user
    public static void updateUserSQL(String username, String password, String role) throws ClassNotFoundException, SQLException{
        String updateUserSQL = "UPDATE users SET Password = '" + password + "', Role = '" + role + "' "
                             + "WHERE Username = '" + username + "'";
        
        //create connection object
        DatabaseConnection connection = new DatabaseConnection();
        PreparedStatement statement = connection.DatabaseConnection().prepareStatement(updateUserSQL);
        statement.execute();
        statement.close();
        connection.DatabaseConnection().close();
    }//end updateUserSQL
    
    //authenticate user login
    public static int authenticateLoginSQL(String username, String password) throws SQLException, ClassNotFoundException{
        
        int loginStatusCheck = 0; //this stores the values of the login status. true if login is a success
        
        String loginAuthentication = "SELECT UserID FROM users WHERE Username='" + username 
                + "' AND Password='" + password + "'";
        DatabaseConnection connection = new DatabaseConnection();
        PreparedStatement statement = connection.DatabaseConnection().prepareStatement(loginAuthentication);
        ResultSet rs = statement.executeQuery();
        if(rs.next()){
            loginStatusCheck = rs.getInt("UserID");
        }//end while
        
        rs.close();
        statement.close();
        connection.DatabaseConnection().close();
        
       return loginStatusCheck;
    }//end authenticateLoginSQL
}
