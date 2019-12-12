package main.java.com.untitled.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import main.java.com.untitled.Venues;
import main.java.com.untitled.dao.VenuesDAO;

public class VenuesController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane venuesPane;

    @FXML
    private TableView venuesTable;

    @FXML
    private TableColumn<VenuesDAO, Integer> rowNumColumn;

    @FXML
    private TableColumn<VenuesDAO, String> venueColumn;

    @FXML
    private TableColumn<VenuesDAO, String> roadColumn;

    @FXML
    private TableColumn<VenuesDAO, String> buildingColumn;

    @FXML
    private TableColumn<VenuesDAO, Integer> capacityColumn;

    @FXML
    private TableColumn<VenuesDAO, Integer> priceColumn;

    @FXML
    private JFXTextField addVenue;

    @FXML
    private JFXTextField addRoad;

    @FXML
    private JFXTextField addCapacity;

    @FXML
    private JFXTextField addBuilding;

    @FXML
    private JFXTextField addPrice;

    @FXML
    private JFXButton addVenueButton;

    @FXML
    private JFXButton modifyVenueButton;

    @FXML
    private JFXTextField modifyRoad;

    @FXML
    private JFXTextField modifyCapacity;

    @FXML
    private JFXTextField modifyBuilding;

    @FXML
    private JFXTextField modifyPrice;

    @FXML
    private JFXComboBox<String> modifyVenueSelect;

    @FXML
    private JFXComboBox<String> removeVenueSelect;

    @FXML
    private JFXButton removeVenuesButton;

    @FXML
    void initialize() {
        //update the table and combo boxes
        updateVenuesTable();
        updateVenuesComboBox();
        
        /*
        Regex patterns to only allow numbers to be inputted in certain text fields
        */
        addCapacity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d{0,7}?") && !newValue.contentEquals("")) {
                    Platform.runLater(() -> {
                        addCapacity.setText("");
                    });
                }
            }
            
        });//end addCapacity.textProperty().addListener(new ChangeListener<String>()
        
        addPrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d{0,7}?") && !newValue.contentEquals("")) {
                    Platform.runLater(() -> {
                        addPrice.setText("");
                    });
                }
            }
            
        });//end addPrice.textProperty().addListener(new ChangeListener<String>()
        
        modifyCapacity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d{0,7}?") && !newValue.contentEquals("")) {
                    Platform.runLater(() -> {
                        modifyCapacity.setText("");
                    });
                }
            }
            
        });//end modifyCapacity.textProperty().addListener(new ChangeListener<String>()
        
        modifyPrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d{0,7}?") && !newValue.contentEquals("")) {
                    Platform.runLater(() -> {
                        modifyPrice.setText("");
                    });
                }
            }
            
        });//end modifyPrice.textProperty().addListener(new ChangeListener<String>()
        
        /*
        End regex
        */
        
        //when the addVenueButton is pressed
        addVenueButton.setOnAction((event) -> {
            addVenueToDatabase(); //add a new venue to the database
        }); //end addVenueButton.setOnAction((event) -> {}
        
        //when the modifyVenueButton is pressed
        modifyVenueButton.setOnAction((event) -> {
            modifyVenueInDatabase(); //modify a venue in the database
        }); //end modifyVenueButton.setOnAction((event) -> {}
        
        //when removeVenuesButton is pressed
        removeVenuesButton.setOnAction((event) -> {
            deleteVenueFromDatabase(); //remove a venue from the database
        }); //end removeVenuesButton.setOnAction((event) -> {}
    }
    
    //update the table
    public void updateVenuesTable(){
        //create a new observable list
        ObservableList<VenuesDAO> venuesList = FXCollections.observableArrayList();
        
        //set the columns
        rowNumColumn.setCellValueFactory(cellData -> cellData.getValue().getRowNum().asObject());
        venueColumn.setCellValueFactory(cellData -> cellData.getValue().getVenueName());
        roadColumn.setCellValueFactory(cellData -> cellData.getValue().getRoad());
        buildingColumn.setCellValueFactory(cellData -> cellData.getValue().getBuilding());
        capacityColumn.setCellValueFactory(cellData -> cellData.getValue().getCapacity().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().getPrice().asObject());
        
        //create a new venues data access object
        VenuesDAO venuesDAO = new VenuesDAO();
        
        try {
            venuesList = venuesDAO.getVenuesRecords();
        } catch (SQLException ex) {
            Logger.getLogger(VenuesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        venuesTable.setItems(venuesList);
    }//end updateVenuesTable(){}
    
    //add data to the combo boxes
    public void updateVenuesComboBox(){
        //create a new data access object
        VenuesDAO venuesDAO = new VenuesDAO();
        
        try {
            ObservableList<String> venuesList = venuesDAO.getListOfAllVenues();
            modifyVenueSelect.setItems(venuesList);
            removeVenueSelect.setItems(venuesList);
        } catch (SQLException ex) {
            Logger.getLogger(VenuesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//end updateVenuesComboBox(){}
    
    //add a new venue to the database
    public void addVenueToDatabase(){
        //get the input data
        String venue = addVenue.getText();
        String road = addRoad.getText();
        String building = addBuilding.getText();
        int capacity = Integer.parseInt(addCapacity.getText());
        int price = Integer.parseInt(addPrice.getText());
        
        System.out.println(capacity);
        
        //pass the data to an venue object
        Venues addVenues = new Venues(venue, road, building, capacity, price);
        
        //create a new venues data access object
        VenuesDAO venuesDAO = new VenuesDAO(addVenues);
        
        try {
            venuesDAO.insertIntoTable();
        } catch (SQLException ex) {
            Logger.getLogger(VenuesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //update the table and combo boxes
        updateVenuesTable();
        updateVenuesComboBox();
        
        //reset the text fields
        addVenue.setText("");
        addRoad.setText("");
        addBuilding.setText("");
        addCapacity.setText("");
        addPrice.setText("");
    }//end addVenueToDatabase()
    
    //modify a record in the database
    public void modifyVenueInDatabase(){
        //get the input data
        String venue = modifyVenueSelect.getValue();
        String road = modifyRoad.getText();
        String building = modifyBuilding.getText();
        int capacity = Integer.parseInt(modifyCapacity.getText());
        System.out.println(capacity);
        int price = Integer.parseInt(modifyPrice.getText());
        
        //pass the data to an venue object
        Venues modifyVenues = new Venues(venue, road, building, capacity, price);
        
        //create a new venues data access object
        VenuesDAO venuesDAO = new VenuesDAO(modifyVenues);
        
        try {
            venuesDAO.updateTable();
        } catch (SQLException ex) {
            Logger.getLogger(VenuesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //update the table
        updateVenuesTable();
        
        //reset the text fields
        modifyRoad.setText("");
        modifyBuilding.setText("");
        modifyCapacity.setText("");
        modifyPrice.setText("");
    }//end modifyVenueInDatabase()
    
    //delete a record in the database
    public void deleteVenueFromDatabase(){
        //get the input
        String venue = removeVenueSelect.getValue();
        
        //create a new venue object
        Venues removeVenue = new Venues(venue);
        
        //create a new venues data access object
        VenuesDAO venuesDAO = new VenuesDAO(removeVenue);
        
        try {
            venuesDAO.deleteFromTable();
        } catch (SQLException ex) {
            Logger.getLogger(VenuesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //update the table and the combo boxes
        updateVenuesTable();
        updateVenuesComboBox();
    }//end deleteVenueFromDatabase()
}
