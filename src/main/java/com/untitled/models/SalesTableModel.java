/*
This class contains methods to setup the sales history table
*/
package main.java.com.untitled.models;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SalesTableModel {
    //define the database url
    private final String JDBC_URL = "jdbc:derby:Database;create=true";
    
    //define the variables
    private IntegerProperty rowNum;
    private StringProperty customerID;
    private StringProperty customerName;
    private StringProperty partyType;
    private ObjectProperty<Date> dueDate;
    private ObjectProperty<Time> dueTime;
    private StringProperty venue;
    private StringProperty caterer;
    private StringProperty addonOne;
    private StringProperty addonTwo;
    private StringProperty addonThree;
    private ObjectProperty<BigDecimal> totalPrice;
    private StringProperty toBePaid;
    
    //getters and setters
    //row number
    public IntegerProperty getRowNum() {
        return rowNum;
    }
    public void setRowNum(int rowNum) {
        this.rowNum = new SimpleIntegerProperty(rowNum);
    }
    
    //customerID
    public StringProperty getCustomerID() {
        return customerID;
    }
    public void setCustomerID(String customerID) {
        this.customerID = new SimpleStringProperty(customerID);
    }
    
    //customer name
    public StringProperty getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = new SimpleStringProperty(customerName);
    }
    
    //party type
    public StringProperty getPartyType() {
        return partyType;
    }
    public void setPartyType(String partyType) {
        this.partyType = new SimpleStringProperty(partyType);
    }
    
    //due date
    public ObjectProperty<Date> getDueDate() {
        return dueDate;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = new SimpleObjectProperty(dueDate);
    }
    
    //due time
    public ObjectProperty<Time> getDueTime() {
        return dueTime;
    }
    public void setDueTime(Time dueTime) {
        this.dueTime = new SimpleObjectProperty(dueTime);
    }
    
    //venue
    public StringProperty getVenue() {
        return venue;
    }
    public void setVenue(String venue) {
        this.venue = new SimpleStringProperty(venue);
    }

    public StringProperty getCaterer() {
        return caterer;
    }

    public void setCaterer(String caterer) {
        this.caterer = new SimpleStringProperty(caterer);
    }
    
    //add on one
    public StringProperty getAddonOne() {
        return addonOne;
    }
    public void setAddonOne(String addonOne) {
        this.addonOne = new SimpleStringProperty(addonOne);
    }
    
    //addon two
    public StringProperty getAddonTwo() {
        return addonTwo;
    }
    public void setAddonTwo(String addonTwo) {
        this.addonTwo = new SimpleStringProperty(addonTwo);;
    }
    
    //addon three
    public StringProperty getAddonThree() {
        return addonThree;
    }
    public void setAddonThree(String addonThree) {
        this.addonThree = new SimpleStringProperty(addonThree);
    }
    
    //total Price
    public ObjectProperty<BigDecimal> getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = new SimpleObjectProperty(totalPrice);
    }
    
    //to be paid
    public StringProperty getToBePaid() {
        return toBePaid;
    }
    public void setToBePaid(String toBePaid) {
        this.toBePaid = new SimpleStringProperty(toBePaid);
    }
    
    //constructor

    public SalesTableModel() {
        //empty
    }
    
    //get an observable list to generate the table view
    public ObservableList<SalesTableModel> getSalesHistory() throws SQLException{
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //define the statement
        String selectSalesHistory = "SELECT purchases.PurchaseID, "
                                    + "customers.ID, "
                                    + "customers.Name, "
                                    + "sold.PartyType, "
                                    + "sold.Venue, "
                                    + "sold.Caterer, "
                                    + "sold.AddonOne, "
                                    + "sold.AddonTwo, "
                                    + "sold.AddonThree, "
                                    + "sold.DueDate, "
                                    + "sold.DueTime, "
                                    + "sold.TotalPrice, "
                                    + "purchases.ToBePaid"
                                    + " FROM purchases"
                                    + " LEFT JOIN customers"
                                    + " ON purchases.CustomerID = customers.CustomerID"
                                    + " LEFT JOIN sold"
                                    + " ON purchases.SoldID = sold.SoldID";
                                    
        //prepare the statment
        PreparedStatement ps = connection.prepareStatement(selectSalesHistory);
        
        ResultSet rs = ps.executeQuery(); //get the result set
        
        ObservableList<SalesTableModel> saleHisttoryList = getSalesHistoryObjects(rs);  //get the user objects
        
        //close the statement and the connection
        ps.close();
        connection.close();
        
        return saleHisttoryList;
    }//end ObservableList<CatererDAO> getCaterersRecords()
    
    //get the salestable objects from the database
    private ObservableList<SalesTableModel> getSalesHistoryObjects(ResultSet rs) throws SQLException{
        ObservableList<SalesTableModel> saleHisttoryList = FXCollections.observableArrayList();

        while(rs.next()){
            //create a new saleTablemodel
            SalesTableModel saleTable = new SalesTableModel();
            
            //set the variables
            saleTable.setRowNum(rs.getInt("PurchaseID"));
            saleTable.setCustomerID(rs.getString("ID"));
            saleTable.setCustomerName(rs.getString("Name" ));
            saleTable.setPartyType(rs.getString("PartyType"));
            saleTable.setVenue(rs.getString("Venue"));
            saleTable.setCaterer(rs.getString("Caterer"));
            saleTable.setAddonOne(rs.getString("AddonOne"));
            saleTable.setAddonTwo(rs.getString("AddonTwo"));
            saleTable.setAddonThree(rs.getString("AddonThree"));
            saleTable.setDueDate(rs.getDate("DueDate"));
            saleTable.setDueTime(rs.getTime("DueTime"));
            saleTable.setTotalPrice(rs.getBigDecimal("TotalPrice"));
            saleTable.setToBePaid(rs.getString("ToBePaid"));
            
            saleHisttoryList.add(saleTable);
        }
        
        return saleHisttoryList;
        
    }//end ObservableList<CatererDAO> getCatererObjects(ResultSet rs)
    
}
