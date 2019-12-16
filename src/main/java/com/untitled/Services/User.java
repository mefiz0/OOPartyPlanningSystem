/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.untitled.Services;

/**
 *
 * @author sath
 */
public class User {
    
    private String username;
    private String password;
    private String role;
    
    //getters and setters
    //user name
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    
    //password
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    //role
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    
    //end getters and setters
    
    //fully parametarized constructor
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    //if only the username is passed
    public User(String username){
        this.username = username;
        //set the others to null
        this.password = null;
        this.role = null;
    }
    
    //empty constructor
    public User(){
        //empty
    }
    
    
}
