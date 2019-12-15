/*
    Defines a "Data Access Object" for the customers class
 */
package main.java.com.untitled.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.com.untitled.Customer;

public class CustomerModel implements Model{
    //variable declarations
    private StringProperty idNum;
    private StringProperty name;    
    private LongProperty bankAccountNum;    
    private IntegerProperty contactNum;    
    private StringProperty emailAddress;
    
    //getters and setters
    //idNumber
    public StringProperty getIdNum() {
        return idNum;
    }
    public void setIdNum(String idNum) {
        this.idNum = new SimpleStringProperty(idNum);
    }
    
    //name
    public StringProperty getName() {
        return name;
    }
    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    //bank account number
    public LongProperty getBankAccountNum() {
        return bankAccountNum;
    }
    public void setBankAccountNum(long bankAccountNum) {
        this.bankAccountNum = new SimpleLongProperty(bankAccountNum);
    }
    
    //contact number
    public IntegerProperty getContactNum() {
        return contactNum;
    }
    public void setContactNum(int contactNum) {
        this.contactNum = new SimpleIntegerProperty(contactNum);
    }
    
    //email address
    public StringProperty getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = new SimpleStringProperty(emailAddress);
    }
    
    //end getters and setters
    
    //constructor
    //fully parametrized constructor
    public CustomerModel(Customer customer){
        this.idNum = new SimpleStringProperty(customer.getIdNum());
        this.name = new SimpleStringProperty(customer.getName());
        this.bankAccountNum = new SimpleLongProperty(customer.getBankAccountNum());
        this.contactNum = new SimpleIntegerProperty(customer.getContactNum());
        this.emailAddress = new SimpleStringProperty(customer.getEmailAddress());
    }
    //empty constructor
    public CustomerModel(){
        //empty
    }
    
    @Override
    public void insertIntoTable() throws SQLException {
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //define the sql statement
        String insertIntoCustomersTable = "INSERT INTO customers "
                                        + "(ID, Name, BankAccountNumber, ContactNumber, Email) "
                                        + "VALUES "
                                        + "('" + this.idNum.get() + "', "
                                        + "'" + this.name.get() + "', "
                                        + this.bankAccountNum.get() + ", "
                                        + this.contactNum.get() + ", "
                                        + "'" + this.emailAddress.get() + "')";
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(insertIntoCustomersTable);
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
        String deleteFromCustomersTable = "DELETE FROM customers WHERE ID = '" + this.idNum.get() + "'";
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(deleteFromCustomersTable);
        //execute the statement
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
        String updateCustomersTable = "UPDATE customers SET "
                                    + "Name = '" + this.name.get() + "', "
                                    + "BankAccountNumber = " + this.bankAccountNum.get() + ", "
                                    + "ContactNumber = " + this.contactNum.get() + ", "
                                    + "Email = '" + this.emailAddress.get() + "'";
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(updateCustomersTable);
        //execute statement
        ps.execute();
        
        //close the statement and the connection
        ps.close();
        connection.close();
    }
    
    //get the database records and store them in an observable list for tableview generation
    public ObservableList<CustomerModel> getCustomerRecords() throws SQLException{
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //prepare the statment
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM customers");
        
        ResultSet rs = ps.executeQuery(); //get the result set
        
        ObservableList<CustomerModel> customersList = getCustomerObjects(rs);  //get the user objects
        
        return customersList;
    }
    
    //generate a observable list which is used to generate a table view
    private ObservableList<CustomerModel> getCustomerObjects(ResultSet rs) throws SQLException{
        ObservableList<CustomerModel> customersList = FXCollections.observableArrayList(); //create an observable array list
        
        while(rs.next()){
            CustomerModel customer = new CustomerModel();
            customer.setIdNum(rs.getString("ID"));
            customer.setName(rs.getString("Name"));
            customer.setBankAccountNum(rs.getLong("BankAccountNumber"));
            customer.setContactNum(rs.getInt("ContactNumber"));
            customer.setEmailAddress(rs.getString("Email"));
                
            customersList.add(customer);
        }
        
        return customersList;
    }
    
    //get a list of all customer IDs
     public ObservableList<String> getListOfAllCustomerIDs() throws SQLException {
        //create the conncetion object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement("SELECT ID FROM customers");
        
        //get the result set
        ResultSet rs = ps.executeQuery();
        
        //add the results to an observable list
        ObservableList<String> customerIDs = FXCollections.observableArrayList();
        String customerID;
        
        while(rs.next()){
            customerID = rs.getString("ID");
            customerIDs.add(customerID);
        }
        
        return customerIDs;
    }
     
    //get a hashmap of customer info based on the customerID selected
    public HashMap<String, String> getCustomerDataBasedOnID(String id) throws SQLException{
        //create the connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM customers WHERE ID = '" + id + "'");
        //get the result set
        ResultSet rs = ps.executeQuery();
        
        //define the hashmap to store customer data
        HashMap<String, String> customerData = new HashMap<>();
        
        while(rs.next()){
           customerData.put("name", rs.getString("Name"));
           customerData.put("bankAccountNum", rs.getString("BankAccountNumber"));
           customerData.put("contactNum", rs.getString("ContactNumber"));
           customerData.put("email", rs.getString("Email"));
        }
        
        return customerData;
    }
}
