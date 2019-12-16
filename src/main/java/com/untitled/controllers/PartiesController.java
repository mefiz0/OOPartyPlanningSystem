package main.java.com.untitled.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
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
import main.java.com.untitled.Services.Party;
import main.java.com.untitled.models.CustomerModel;
import main.java.com.untitled.models.PartyModel;

public class PartiesController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane partiesPane;

    @FXML
    private TableView partiesTable;

    @FXML
    private TableColumn<PartyModel, Integer> rowNumColumn;

    @FXML
    private TableColumn<PartyModel, String> typeColumn;

    @FXML
    private TableColumn<PartyModel, Integer> priceColumn;

    @FXML
    private TableColumn<PartyModel, String> taskOneColumn;

    @FXML
    private TableColumn<PartyModel, String> taskTwoColumn;

    @FXML
    private TableColumn<PartyModel, String> taskThreeColumn;

    @FXML
    private TableColumn<PartyModel, String> taskFourColumn;

    @FXML
    private JFXTextField addType;

    @FXML
    private JFXTextField addBasePrice;

    @FXML
    private JFXTextField addTaskThree;

    @FXML
    private JFXTextField addTaskFour;

    @FXML
    private JFXTextField addTaskOne;

    @FXML
    private JFXTextField addTaskTwo;

    @FXML
    private JFXButton addPartyButton;

    @FXML
    private JFXTextField modifyBasePrice;

    @FXML
    private JFXTextField modifyTaskThree;

    @FXML
    private JFXTextField modifyTaskFour;

    @FXML
    private JFXTextField modifyTaskOne;

    @FXML
    private JFXTextField modifyTaskTwo;

    @FXML
    private JFXComboBox<String> modifyTypeSelect;
    
    @FXML
    private JFXButton modifyPartyButton;

    @FXML
    private JFXButton removePartyButton;

    @FXML
    private JFXComboBox<String> removePartySelect;
    
    @FXML
    private JFXTabPane dataAccessPane;

    @FXML
    void initialize() {
        //update the table view and the combo box
        updateTableView();
        updateTypeComboBoxes();
        
        /*
        Regex patterns for setting certain text fields to numbers only
        */
        addBasePrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d{0,7}?")) {
                    Platform.runLater(() -> {
                        addBasePrice.setText("");
                    });
                }
            }
            
        });//end addBasePrice.textProperty().addListener;
        
        modifyBasePrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d{0,7}?")) {
                    Platform.runLater(() -> {
                        modifyBasePrice.setText("");
                    });
                }
            }
            
        });//end modifyBasePrice.textProperty().addListener;
        
        /*
        End regex patterns
        */
        
         /*
        When a value is selected from the type drop down box, set the text fields to the data of that type
        */
        modifyTypeSelect.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            //create a new Customer Data access object
            PartyModel partyDAO = new PartyModel();
            
            try {
                //store the data in a hashmap
                HashMap<String, String> data = partyDAO.getPartyDataBasedOnType(newValue);
                
                //update the text fields
                modifyBasePrice.setText(data.get("price"));
                modifyTaskOne.setText(data.get("taskOne"));
                modifyTaskTwo.setText(data.get("taskTwo"));
                modifyTaskThree.setText(data.get("taskThree"));
                modifyTaskFour.setText(data.get("taskFour"));
                
            } catch (SQLException ex) {
                Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }); //end modifyTypeSelect.getSelectionModel().selectedItemProperty().addListener
        
        //when the addPartyButton is pressed
        addPartyButton.setOnAction((event) -> {
            addPartyToTheDatabase(); //add a new party to the database
        }); //end addPartyButton.setOnAction((event) -> {}
        
        //when the modifyPartyButton is pressed
        modifyPartyButton.setOnAction((event) -> {
            modifyPartyInDatabase(); //modify a party in the database
        }); //end modifyPartyButton.setOnAction((event) -> {}
        
        //when the removePartyButton is pressed
        removePartyButton.setOnAction((event) -> {
            deletePartyInDatabase(); //remove a party in the database
        });//end removePartyButton.setOnAction((event) -> {}
    }
    
    //update the parties table view
    public void updateTableView(){
        //create the list to hold the data in
        ObservableList<PartyModel> partiesList = FXCollections.observableArrayList();
        
        //set the columns
        rowNumColumn.setCellValueFactory(cellData -> cellData.getValue().getRowNum().asObject());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().getType());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().getPrice().asObject());
        taskOneColumn.setCellValueFactory(cellData -> cellData.getValue().getTaskOne());
        taskTwoColumn.setCellValueFactory(cellData -> cellData.getValue().getTaskTwo());
        taskThreeColumn.setCellValueFactory(cellData -> cellData.getValue().getTaskThree());
        taskFourColumn.setCellValueFactory(cellData -> cellData.getValue().getTaskFour());
        
        //create a new database access object
        PartyModel partiesDAO = new PartyModel();
        
        try {
            //get the data from the database
            partiesList = partiesDAO.getPartyRecords();
        } catch (SQLException ex) {
            Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        partiesTable.setItems(partiesList);
    }//end updateTableView(){}
    
    //add data to the type combo boxes
    public void updateTypeComboBoxes(){
        //create a new customerDAO
        PartyModel partyDAO = new PartyModel();
        
        try {
            ObservableList partyTypes = partyDAO.getListOfAllPartyTypes();
            modifyTypeSelect.setItems(partyTypes);
            removePartySelect.setItems(partyTypes);
        } catch (SQLException ex) {
            Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//end updateIDComboBoxes(){}
    
    //add a new party to the database
    public void addPartyToTheDatabase(){
        //get the input
        String type = addType.getText();
        int basePrice = Integer.parseInt(addBasePrice.getText());
        String taskOne = addTaskOne.getText();
        String taskTwo = addTaskTwo.getText();
        String taskThree = addTaskThree.getText();
        String taskFour = addTaskFour.getText();
        
        //create a new party object
        Party addParty =  new Party(type, basePrice, taskOne, taskTwo, taskThree, taskFour);
        
        //create a new party dao
        PartyModel partyDAO = new PartyModel(addParty);
        
        try {
            //update the database
            partyDAO.insertIntoTable();
        } catch (SQLException ex) {
            Logger.getLogger(PartiesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //update the table view and  the combo boxes
        updateTableView();
        updateTypeComboBoxes();
        
        //reset the text fields
        addType.setText("");
        addBasePrice.setText("");
        addTaskOne.setText("");
        addTaskTwo.setText("");
        addTaskThree.setText("");
        addTaskFour.setText("");
    }//end addPartyToTheDatabase()
    
    //modify a record in the database
    public void modifyPartyInDatabase(){
        //get the input
        String type = modifyTypeSelect.getValue();
        int basePrice = Integer.parseInt(modifyBasePrice.getText());
        String taskOne = modifyTaskOne.getText();
        String taskTwo = modifyTaskTwo.getText();
        String taskThree = modifyTaskThree.getText();
        String taskFour = modifyTaskFour.getText();
        
        //create a new party object
        Party party = new Party(type, basePrice, taskOne, taskTwo, taskThree, taskFour);
        
        //create a new party dao
        PartyModel partyDAO = new PartyModel(party);
        
        try {
            //update the database
            partyDAO.updateTable();
        } catch (SQLException ex) {
            Logger.getLogger(PartiesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //update the tableview
        updateTableView();
        
        //reset the text fields
        modifyTypeSelect.getSelectionModel().clearSelection();
        modifyBasePrice.setText("");
        modifyTaskOne.setText("");
        modifyTaskTwo.setText("");
        modifyTaskThree.setText("");
        modifyTaskFour.setText("");
        
    }//end modifyPartyInDatabase()
    
    //delete a record in the database
    public void deletePartyInDatabase(){
        //get the input
        String type = removePartySelect.getValue();
        
        //create a new party object
        Party removeParty = new Party(type);
        
        //create a new party dao
        PartyModel partyDAO = new PartyModel(removeParty);
        
        try {
            //delete from the database
            partyDAO.deleteFromTable();
        } catch (SQLException ex) {
            Logger.getLogger(PartiesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //update the table view and the combo boxes
        updateTableView();
        updateTypeComboBoxes();
        
        //reset the combo box selection
        removePartySelect.getSelectionModel().clearSelection();
    }
    
    public void setPermissions(String role){
        if(role.equals("Event Sales")){
            partiesPane.getChildren().remove(dataAccessPane);
        }
    }
}
