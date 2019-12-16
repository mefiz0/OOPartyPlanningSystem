/*
Data access object for payments
 */
package main.java.com.untitled.models;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static main.java.com.untitled.models.Model.JDBC_URL;

public class PaymentsModel implements Model{
    //variable declarations
    private IntegerProperty rowNum; //used for row number in table view
    private StringProperty customerID;
    private StringProperty partyType;
    
    //getters and setters
    //row number
    public IntegerProperty getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = new SimpleIntegerProperty(rowNum);
    }
    
    //customer name
    public StringProperty getCustomerID() {
        return customerID;
    }
    public void setCustomerID(String customerID) {
        this.customerID = new SimpleStringProperty(customerID);
    }
    
    //party type
    public StringProperty getPartyType() {
        return partyType;
    }
    public void setPartyType(String partyType) {
        this.partyType = new SimpleStringProperty(partyType);
    }
    
    //constructors
    public PaymentsModel(StringProperty customerName, StringProperty partyType) {
        this.customerID = customerID;
        this.partyType = partyType;
    }
    
    public PaymentsModel(){
        //empty
    }

    @Override
    public void insertIntoTable() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void deleteFromTable() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
        
    }

    @Override
    public void updateTable() throws SQLException {
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //1 - get the customer id
        PreparedStatement ps = connection.prepareStatement("SELECT CustomerID FROM customers WHERE ID = '" + this.customerID.get() + "'");
        ResultSet rs = ps.executeQuery();
        int customerID = rs.getInt("ID");
        
        ps.close();
        connection.close();
        
        //2 - update the table
        ps = connection.prepareStatement("UPDATE payments SET ToBePaid = 'NO' WHERE CustomerID = " + customerID);
        ps.execute();
        connection.close();
    }//end update table

    //get the database records and store them in an observable list for tableview generation
    public ObservableList<PaymentsModel> getPaymentsRecords() throws SQLException{
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //define the statement
        String sqlStatement = "SELECT purchases.PurchaseID, "
                            + "customers.ID, "
                            + "sold.PartyType "
                            + "FROM purchases "
                            + "LEFT JOIN customers "
                            + "ON purchases.CustomerID = customers.CustomerID "
                            + "LEFT JOIN sold "
                            + "ON purchases.SoldID = sold.SoldID "
                            + "WHERE ToBePaid = 'YES'"; 
        
        //prepare the statment
        PreparedStatement ps = connection.prepareStatement(sqlStatement);
        
        ResultSet rs = ps.executeQuery(); //get the result set
        
        ObservableList<PaymentsModel> paymentsList = getPaymentsObjects(rs);  //get the user objects
        
        //close the statement and the connection
        ps.close();
        connection.close();
        
        return paymentsList;
    }//end ObservableList<PaymentsModel> getPaymentsRecords()
    
    //generate a observable list which is used to generate a table view
    private ObservableList<PaymentsModel> getPaymentsObjects(ResultSet rs) throws SQLException{
        ObservableList<PaymentsModel> paymentsList = FXCollections.observableArrayList(); //create an observable array list
        
        while(rs.next()){
            PaymentsModel payments = new PaymentsModel();
            
            payments.setRowNum(rs.getInt("PurchaseID"));
            payments.setCustomerID(rs.getString("ID"));
            payments.setPartyType(rs.getString("PartyType"));
            
            paymentsList.add(payments);
            
        }
        
        //close the statement and the connection
        
        return paymentsList;
    }//end ObservableList<PaymentsModel> getPaymentsObjects(ResultSet rs)
    
    //get the customer ids for combo boxes
    public ObservableList<String> getCustomerIDs() throws SQLException{
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //create the statement
        PreparedStatement ps = connection.prepareStatement("SELECT customers.ID FROM "
                                                          + "purchases "
                                                          + "LEFT JOIN "
                                                          + "purchases.CustomerID = customers.CustomerID "
                                                          + "WHERE purchases.ToBePaid = 'YES'");
        //get the result set
        ResultSet rs = ps.executeQuery();
        
        ObservableList customerIDs = FXCollections.observableArrayList();
        
        while(rs.next()){
            customerIDs.add(rs.getString("ID"));
        }
        
        return customerIDs;
    }//end ObservableList<String> getCustomerIDs()
    
    //get the party types for combo boxes
    public ObservableList<String> getPartyTypes(String customerID) throws SQLException{
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //create the statement
        PreparedStatement ps = connection.prepareStatement("SELECT sold.PartyType FROM "
                                                          + "purchases "
                                                          + "LEFT JOIN "
                                                          + "purchases.SoldID = sold.SoldID "
                                                          + "WHERE purchases.ToBePaid = 'YES' "
                                                          + "AND customers.ID = '" + customerID + "'");
        //get the result set
        ResultSet rs = ps.executeQuery();
        
        ObservableList partyTypes = FXCollections.observableArrayList();
        
        while(rs.next()){
            partyTypes.add(rs.getString("PartyType"));
        }
        
        return partyTypes;
    }//end ObservableList<String> getCustomerIDs()
}
