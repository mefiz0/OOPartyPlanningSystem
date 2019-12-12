/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.untitled;

/**
 *
 * @author sath
 */
public class Customer {
    
    private String idNum;
    private String name;    
    private long bankAccountNum;    
    private int contactNum;    
    private String emailAddress;
    
    //getters and setters
    //identity Number
    public String getIdNum() {
        return idNum;
    }
    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }
    
    //name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //bank account number
    public long getBankAccountNum() {
        return bankAccountNum;
    }
    public void setBankAccountNum(long bankAccountNum) {
        this.bankAccountNum = bankAccountNum;
    }
    
    //contact number
    public int getContactNum() {
        return contactNum;
    }
    public void setContactNum(int contactNum) {
        this.contactNum = contactNum;
    }
    
    //email address
    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    //end getters and setters
    
    //constructors
    //fully parametraized
    public Customer(String idNum, String name, long bankAccountNum, int contactNum, String emailAddress) {
        this.idNum = idNum;
        this.name = name;
        this.bankAccountNum = bankAccountNum;
        this.contactNum = contactNum;
        this.emailAddress = emailAddress;
    }
    
    //only id
    public Customer(String idNum){
        this.idNum = idNum;
        this.name = null;
        this.bankAccountNum = 0;
        this.contactNum = 0;
        this.emailAddress = null;
    }
    
    //empty
    public Customer(){
        //empty
    }
    
    
}
