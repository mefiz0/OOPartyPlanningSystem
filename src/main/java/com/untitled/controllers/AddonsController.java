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
import main.java.com.untitled.Services.Addon;
import main.java.com.untitled.models.AddonsModel;

public class AddonsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane addonsPane;

    @FXML
    private TableView addonsTable;
    
    @FXML
    private TableColumn<AddonsModel, Integer> rowNumColumn;

    @FXML
    private TableColumn<AddonsModel, String> typeColumn;

    @FXML
    private TableColumn<AddonsModel, Integer> priceColumn;

    @FXML
    private JFXTextField addType;

    @FXML
    private JFXTextField addPrice;

    @FXML
    private JFXButton addNewAddOnButton;

    @FXML
    private JFXButton modifyAddOnButton;

    @FXML
    private JFXTextField modifyAddonPrice;

    @FXML
    private JFXComboBox<String> modifyAddonSelect;

    @FXML
    private JFXButton removeAddonButton;

    @FXML
    private JFXComboBox<String> removeAddOnTypeSelect;

    @FXML
    void initialize() {
        //initialize the combobox and the tableview
        updateAddonsTable();
        updateTypeComboBoxes();
        
        /*
        Regex patterns to only allow numbers to be inputted in certain text fields
        */
        addPrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d{0,7}?")) {
                    Platform.runLater(() -> {
                        addPrice.setText("");
                    });
                }
            }
            
        });//end addPrice.textProperty().addListener(new ChangeListener<String>()
        
        modifyAddonPrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d{0,7}?")) {
                    Platform.runLater(() -> {
                        modifyAddonPrice.setText("");
                    });
                }
            }
            
        });//end modifyAddonPrice.textProperty().addListener(new ChangeListener<String>()
        
        /*
        End regex patterns
        */
        
        //add the data to the fields based on what is selected
        modifyAddonSelect.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            //create a new addons data access object
            AddonsModel addonsDAO = new AddonsModel();
            
            //get the price from the database
            String price = "0";
            try {
                price = Integer.toString(addonsDAO.getPriceBasedOnAddon(newValue));
            } catch (SQLException ex) {
                Logger.getLogger(AddonsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //update the price
            modifyAddonPrice.setText(price);
        });
        
        //when the addNewAddonButton is pressed
        addNewAddOnButton.setOnAction((event) -> {
            addAddOnToDatabase(); //add a new Addon to the database
        }); //end addNewAddOnButton.setOnAction((event) -> {})
        
        //when the modify addon button is pressed
        modifyAddOnButton.setOnAction((event) -> {
            modifyAddonInDatabase(); //modify the entry in the database
        });//end modifyAddOnButton.setOnAction((event) -> {})
        
        //when the removeAddonButton is pressed
        removeAddonButton.setOnAction((event) -> {
            removeAddonInDatabase(); //remove the addon from the database
        }); //end removeAddonInDatabase.setOnAction((event) -> {})
        
    }//end initialize()
    
     //update the addons table
    public void updateAddonsTable(){
        //create a list to hold the data in
        ObservableList<AddonsModel> addonsList = FXCollections.observableArrayList();
        
        //set the columns
        rowNumColumn.setCellValueFactory(cellData -> cellData.getValue().getRowNum().asObject());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().getAddonType());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().getAddonPrice().asObject());
        
        //create an addons data access object
        AddonsModel addonsDAO = new AddonsModel();
        
        try {
            addonsList = addonsDAO.getAddonsRecords();
        } catch (SQLException ex) {
            Logger.getLogger(AddonsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //set the the table to the list
        addonsTable.setItems(addonsList);
    }//end updateAddonsTable() {}
    
    //add data to the combo boxes
    public void updateTypeComboBoxes(){
        //create a new addons data access object
        AddonsModel addonsDAO = new AddonsModel();
        
        try {
            ObservableList<String> typeList = addonsDAO.getAllAddonsTypes();
            modifyAddonSelect.setItems(typeList);
            removeAddOnTypeSelect.setItems(typeList);
        } catch (SQLException ex) {
            Logger.getLogger(AddonsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//end updateTypeComboBoxes(){}
    
    //add a new addon to the database
    public void addAddOnToDatabase(){
        //get the input data
        String type = addType.getText();
        int price = Integer.parseInt(addPrice.getText());
        
        //pass the data to a new AddonsObject
        Addon addon = new Addon(type, price);
        
        //pass it to addons data access object
        AddonsModel addonsDAO = new AddonsModel(addon);
        try {
            //update the database
            addonsDAO.insertIntoTable();
        } catch (SQLException ex) {
            Logger.getLogger(AddonsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //update the table view and combo boxes
        updateAddonsTable();
        updateTypeComboBoxes();
        
        //reset the input fields
        addType.setText("");
        addPrice.setText("");
    }//end addAddonToDatabase()
    
    //modify an exisiting addon
    public void modifyAddonInDatabase(){
        //get the input data
        String type = modifyAddonSelect.getValue();
        int price = Integer.parseInt(modifyAddonPrice.getText());
        
        //pass the data to a new addon object
        Addon modifyAddon = new Addon(type, price);
        
        //create new addon data access object
        AddonsModel addonsDAO = new AddonsModel(modifyAddon);
        
        try {
            //update the database
            addonsDAO.updateTable();
        } catch (SQLException ex) {
            Logger.getLogger(AddonsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //update the table
        updateAddonsTable();
        
        //reset the input fields
        modifyAddonSelect.getSelectionModel().clearSelection();
        modifyAddonPrice.setText("");
    }//end modifyAddonInDatabase()
    
    //remove an addon from the database
    public void removeAddonInDatabase(){
        //get the input data
        String type = removeAddOnTypeSelect.getValue();
        
        //pass the data to a new addon object
        Addon removeAddon = new Addon(type);
        
        //create a new addons data acces object and remove from the database
        AddonsModel addonsDAO =  new AddonsModel(removeAddon);
        
        try {
            addonsDAO.deleteFromTable();
        } catch (SQLException ex) {
            Logger.getLogger(AddonsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //update the table and the combo boxes
        updateAddonsTable();
        updateTypeComboBoxes();
        
        //clear the selection
        removeAddOnTypeSelect.getSelectionModel().clearSelection();
    }//end removeAddonInDatabase()
}
