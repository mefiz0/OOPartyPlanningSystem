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
import main.java.com.untitled.Services.Sale;

public class SalesModel implements Model{
    
    //define the variables
    private StringProperty customerName; //used for unique identifier generation
    private StringProperty customerID;
    private StringProperty partyType;
    private StringProperty venue;
    private ObjectProperty<Date> date;
    private ObjectProperty<Time> time;
    private StringProperty addonOne;
    private StringProperty addonTwo;
    private StringProperty addonThree;
    private StringProperty caterer;
    private ObjectProperty<BigDecimal> totalPrice;
    private ObjectProperty<BigDecimal> amountPaid;

    //getters and setters
    //customer name
    public StringProperty getCustomerName() {    
        return customerName;
    }
    public void setCustomerName(String customerName) {    
        this.customerName = new SimpleStringProperty(customerName);
    }

    //customer property
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
    
    //venue
    public StringProperty getVenue() {
        return venue;
    }
    public void setVenue(String venue) {
        this.venue = new SimpleStringProperty(venue);
    }
    
    //date
    public ObjectProperty<Date> getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = new SimpleObjectProperty(date);
    }
    
    //time
    public ObjectProperty<Time> getTime() {
        return time;
    }
    public void setTime(Time time) {
        this.time = new SimpleObjectProperty(time);
    }
    
    //addon one
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
        this.addonTwo = new SimpleStringProperty(addonTwo);
    }

    //addon three
    public StringProperty getAddonThree() {
        return addonThree;
    }
    public void setAddonThree(String addonThree) {
        this.addonThree = new SimpleStringProperty(addonThree);
    }
    
    //caterer
    public StringProperty getCaterer() {
        return caterer;
    }
    public void setCaterer(String caterer) {
        this.caterer = new SimpleStringProperty(caterer);
    }
    
    //total price
    public ObjectProperty<BigDecimal> getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = new SimpleObjectProperty(totalPrice);
    }
    
    //amount paid
    public ObjectProperty<BigDecimal> getAmountPaid() {
        return amountPaid;
    }
    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = new SimpleObjectProperty(amountPaid);
    }
    
    //constructors

    public SalesModel(Sale sale) {
        this.partyType = new SimpleStringProperty(sale.getType());
        this.venue = new SimpleStringProperty(sale.getVenue());
        this.date = new SimpleObjectProperty(sale.getDueDate());
        this.time = new SimpleObjectProperty(sale.getDueTime());
        this.addonOne = new SimpleStringProperty(sale.getAddonOne());
        this.addonTwo = new SimpleStringProperty(sale.getAddonTwo());
        this.addonThree = new SimpleStringProperty(sale.getAddonThree());
        this.caterer = new SimpleStringProperty(sale.getCaterer());
        this.totalPrice = new SimpleObjectProperty(sale.getTotalPrice());
        this.amountPaid = new SimpleObjectProperty(sale.getAmountPaid());
    }
    
    public SalesModel(){
        //empty
    }
    
    @Override
    public void insertIntoTable() throws SQLException {
        //create a new connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //generate the unique identifier
        String identifier = this.customerID.get() + "''s " + this.partyType.get() + " party";
        
        //define the sql string
        String insertIntoSalesTable = "INSERT INTO sold "
                                    + "(Identifier, PartyType, Venue, Caterer, AddonOne, AddOnTwo, AddonThree, DueDate, DueTime, TotalPrice, AmountPaid) "
                                    + "VALUES "
                                    + "('" + identifier + "', " 
                                    + this.partyType.get() + "', "
                                    + "'" + this.venue.get() + "', "
                                    + "'" + this.caterer.get() + "', "
                                    + "'" + this.addonOne.get() + "', "
                                    + "'" + this.addonTwo.get() + "', "
                                    + "'" + this.addonThree.get() + "', "
                                    + "'" + this.date.get() + "', "
                                    + "'" + this.time.get() + "', "
                                    + this.totalPrice.get() + ", "
                                    + this.amountPaid.get() + ")";
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(insertIntoSalesTable);
        ps.execute();
        
        //insert into the purchases table
        
        //1 - first we get the id from the sold table
        ps = connection.prepareStatement("SELECT MAX(SoldID) FROM sold");
        ResultSet rs = ps.executeQuery();
        
        int soldID = 0;
        while(rs.next()){
            soldID = rs.getInt(1);
        }
        
        //2 - we get the customer id from the customers table
        ps = connection.prepareStatement("SELECT CustomerID FROM customers WHERE ID = '" + this.customerID.get() + "'");
        rs = ps.executeQuery();
        
        int customerID = 0;
        while(rs.next()){
            customerID = rs.getInt("CustomerID");
            System.out.println(customerID);
        }
        
        //3 - get the tasks id
        //create a task model
        TasksModel taskModel = new TasksModel();
        //set the identifier and the party type
        taskModel.setIdentifier(identifier);
        taskModel.setPartyType(this.partyType.get());
        
        //insert a new record to the tasks table
        taskModel.insertIntoTable();
        
        //get the task id
        int taskId = taskModel.getTaskID();
        
        //4 - add to the purchases table
        String toBePaid = null;
        
        //compare if the amount paid and the total price is the same
        BigDecimal totalPrice = this.totalPrice.get();
        BigDecimal amountPaid = this.amountPaid.get();
        
        //if there is not an amount to paid
        if(totalPrice.equals(amountPaid)){
            toBePaid = "No"; //set no 
        }else{
            toBePaid = "Yes"; //set yes
            System.out.println(toBePaid);
        }
        
        //4 - insert record into purchases
        ps = connection.prepareStatement("INSERT INTO purchases (CustomerID, SoldID, TaskID, ToBePaid) VALUES("
                                        + customerID + ", " + soldID + ", "  + taskId + ", '" + toBePaid + "')");
        ps.execute();
        
        //close the statement and the conenction
        ps.close();
        connection.close();
    }
    
    @Override
    public void deleteFromTable() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateTable() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
