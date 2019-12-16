/*
    Defines a "Data Access Object" for the venues class
 */
package main.java.com.untitled.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.com.untitled.Venues;
import static main.java.com.untitled.models.Model.JDBC_URL;

public class VenuesModel implements Model{
    
    //variable declarations
    private IntegerProperty rowNum; //only used for the row number in tableview
    private StringProperty venueName;
    private StringProperty road;
    private StringProperty Building;
    private IntegerProperty capacity;
    private IntegerProperty price;
    
    //getters and setters
    //row number
    public IntegerProperty getRowNum() {
        return rowNum;
    }
    public void setRowNum(int rowNum) {
        this.rowNum = new SimpleIntegerProperty(rowNum);
    }
    
    //venue name
    public StringProperty getVenueName() {
        return venueName;
    }
    public void setVenueName(String venueName) {
        this.venueName = new SimpleStringProperty(venueName);
    }
    
    //road
    public StringProperty getRoad() {
        return road;
    }
    public void setRoad(String road) {
        this.road = new SimpleStringProperty(road);
    }

    //building
    public StringProperty getBuilding() {
        return Building;
    }
    public void setBuilding(String Building) {
        this.Building = new SimpleStringProperty(Building);
    }

    //capacity
    public IntegerProperty getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = new SimpleIntegerProperty(capacity);
    }
    
    //price
    public IntegerProperty getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = new SimpleIntegerProperty(price);
    }
    
    //end getters and setters
    
    //constructor
    public VenuesModel(Venues venues){
        this.venueName = new SimpleStringProperty(venues.getVenueName());
        this.road = new SimpleStringProperty(venues.getRoad());
        this.Building = new SimpleStringProperty(venues.getBuilding());
        this.capacity = new SimpleIntegerProperty(venues.getCapacity());
        this.price = new SimpleIntegerProperty(venues.getPrice());
    }
    
    //empty constructor
    public VenuesModel(){
        //empty
    }

    @Override
    public void insertIntoTable() throws SQLException {
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //define the sql statement
        String insertIntoVenuesTable = "INSERT INTO venues "
                                     + "(Venue, Road, Building, Capacity, Price) "
                                     + "VALUES "
                                     + "('" + this.venueName.get() + "', "
                                     + "'" + this.road.get() + "', "
                                     + "'" + this.Building.get() + "', "
                                     + this.capacity.get() + ", "
                                     + this.price.get() + ")";
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(insertIntoVenuesTable);
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
        String deleteFromVenuesTable = "DELETE FROM venues WHERE Venue = '" + this.venueName.get() + "'";
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(deleteFromVenuesTable);
        //execute statement
        ps.execute();
        
        //close the statement and the conection
        ps.close();
        connection.close();
    }

    @Override
    public void updateTable() throws SQLException {
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //define the sql statement
        String updateVenuesTable = "UPDATE venues SET "
                                 + "Road = '" + this.road.get() + "', "
                                 + "Building = '" + this.Building.get() + "', "
                                 + "Capacity = " + this.capacity.get() + ", "
                                 + "Price = " + this.price.get() + " "
                                 + "WHERE Venue = '" + this.venueName.get() + "'";
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(updateVenuesTable);
        //execute statement
        ps.execute();
        
        //close the statement and the connection
        ps.close();
        connection.close();
    }
    
    //get the database records and store them in an observable list for tableview generation
    public ObservableList<VenuesModel> getVenuesRecords() throws SQLException{
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //prepare the statment
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM venues");
        
        ResultSet rs = ps.executeQuery(); //get the result set
        
        ObservableList<VenuesModel> venuesList = getVenuesObjects(rs);  //get the user objects
        
        //close the statement and the connection
        ps.close();
        connection.close();
        
        return venuesList;
        
    }//end ObservableList<VenuesDAO> getVenuesRecords()
    
    //generate a observable list which is used to generate a table view 
    private ObservableList<VenuesModel> getVenuesObjects(ResultSet resultSet) throws SQLException{
        
        ObservableList<VenuesModel> venuesList = FXCollections.observableArrayList(); //create an observable array list
        
        //row number
        int i = 1;
        while(resultSet.next()){
            VenuesModel venuesDAO = new VenuesModel();
            venuesDAO.setRowNum(i);
            venuesDAO.setVenueName(resultSet.getString("Venue"));
            venuesDAO.setRoad(resultSet.getString("Road"));
            venuesDAO.setBuilding(resultSet.getString("Building"));
            venuesDAO.setCapacity(resultSet.getInt("Capacity"));
            venuesDAO.setPrice(resultSet.getInt("Price"));
                
            venuesList.add(venuesDAO);
        }
        
        return venuesList;
    } //end ObservableList<VenuesDAO> getVenuesObjects(ResultSet resultSet)
    
    //get a list of all venues
    public ObservableList<String> getListOfAllVenues() throws SQLException {
        //create the conncetion object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement("SELECT Venue FROM venues");
        
        //get the result set
        ResultSet rs = ps.executeQuery();
        
        //add the results to an observable list
        ObservableList<String> venuesList = FXCollections.observableArrayList();
        
        while(rs.next()){
            //add to the list
            venuesList.add(rs.getString("Venue"));
        }
        
        //close the statement and the connection
        ps.close();
        connection.close();
        
        return venuesList;
    }//end ObservableList<String> getListOfAllVenues()
    
    //get a hashmap of venue info based on the customerID selected
    public HashMap<String, String> getVenueDataBasedOnVenue(String venue) throws SQLException{
        //create the connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM venues WHERE Venue = '" + venue + "'");
        //get the result set
        ResultSet rs = ps.executeQuery();
        
        //define the hashmap to store customer data
        HashMap<String, String> venueData = new HashMap<>();
        
        //get everything as a string
        while(rs.next()){
           venueData.put("road", rs.getString("Road"));
           venueData.put("building", rs.getString("Building"));
           venueData.put("capacity", rs.getString("Capacity"));
           venueData.put("price", rs.getString("Price"));
        }
        
        //close the statement and the connection
        ps.close();
        connection.close();
        
        return venueData;
    }
    
    //get the price of a selected venue
    public int getPriceOfVenue(String venue) throws SQLException{
        //create a new connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement("SELECT Price FROM venues WHERE Venue = '" + venue + "'");
        //create the result set
        ResultSet rs = ps.executeQuery();
        
        int price = 0;
        
        //get the results
        while(rs.next()){
        price = rs.getInt("Price");
        }
        
        //close the statement and the connection
        ps.close();
        connection.close();
        
        return price;
    }
}
