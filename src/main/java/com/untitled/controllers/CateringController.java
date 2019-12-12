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
import main.java.com.untitled.Addon;
import main.java.com.untitled.Caterer;
import main.java.com.untitled.dao.AddonsDAO;
import main.java.com.untitled.dao.CatererDAO;

public class CateringController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane cateringPane;

    @FXML
    private TableView cateringTable;
    
    @FXML
    private TableColumn<CatererDAO, Integer> rowNumColumn;

    @FXML
    private TableColumn<CatererDAO, String> catererColumn;

    @FXML
    private TableColumn<CatererDAO, Integer> priceColumn;

    @FXML
    private JFXButton addCatererButton;

    @FXML
    private JFXTextField addCatererName;

    @FXML
    private JFXTextField addCatererPrice;

    @FXML
    private JFXButton modifyCatererButton;

    @FXML
    private JFXTextField modifyCatererPrice;

    @FXML
    private JFXComboBox<String> modifyCatererSelect;

    @FXML
    private JFXButton removeCatererButton;

    @FXML
    private JFXComboBox<String> removeCatererSelect;

    @FXML
    void initialize() {
        //initialize the combobox and the tableview
        updateCaterersTable();
        updateCatererComboBoxes();
        
        /*
        Regex patterns to only allow numbers to be inputted in certain text fields
        */
        addCatererPrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d{0,7}?")) {
                    Platform.runLater(() -> {
                        addCatererPrice.setText("");
                    });
                }
            }
            
        });//end addPrice.textProperty().addListener(new ChangeListener<String>()
        
        modifyCatererPrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d{0,7}?")) {
                    Platform.runLater(() -> {
                        modifyCatererPrice.setText("");
                    });
                }
            }
            
        });//end modifyAddonPrice.textProperty().addListener(new ChangeListener<String>()
        
        /*
        End regex patterns
        */
        
        //when the addCatererButton is pressed
        addCatererButton.setOnAction((event) -> {
            addCatererToDatabase(); //add a new caterer to the database
        }); //end addCatererButton.setOnAction((event) -> {})
        
        //when the modifyCatererButton is pressed
        modifyCatererButton.setOnAction((event) -> {
            modifyCatererInDatabase(); //modify the entry in the database
        });//end modifyCatererButton.setOnAction((event) -> {})
        
        //when the removeCatererButton is pressed
        removeCatererButton.setOnAction((event) -> {
            removeCatererInDatabase(); //remove a caterer from the database
        }); //end removeCatererButton.setOnAction((event) -> {})
        
    }//end initialize()
    
     //update the caterers table
    public void updateCaterersTable(){
        //create a list to hold the data in
        ObservableList<CatererDAO> caterersList = FXCollections.observableArrayList();
        
        //set the columns
        rowNumColumn.setCellValueFactory(cellData -> cellData.getValue().getRowNum().asObject());
        catererColumn.setCellValueFactory(cellData -> cellData.getValue().getCaterer());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().getPrice().asObject());
        
        //create an caterer data access object
        CatererDAO catererDAO = new CatererDAO();
        
        try {
            caterersList = catererDAO.getCaterersRecords();
        } catch (SQLException ex) {
            Logger.getLogger(AddonsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //set the the table to the list
        cateringTable.setItems(caterersList);
    }//end updateCaterersTable() {}
    
    //add data to the combo boxes
    public void updateCatererComboBoxes(){
        //create a new addons data access object
        CatererDAO catererDAO = new CatererDAO();
        
        try {
            ObservableList<String> catererList = catererDAO.getAllCaterers();
            modifyCatererSelect.setItems(catererList);
            removeCatererSelect.setItems(catererList);
        } catch (SQLException ex) {
            Logger.getLogger(AddonsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//end updateTypeComboBoxes(){}
    
    //add a new caterer to the database
    public void addCatererToDatabase(){
        //get the input data
        String catererName = addCatererName.getText();
        int price = Integer.parseInt(addCatererPrice.getText());
        
        //pass the data to a new Caterer object
        Caterer caterer = new Caterer(catererName, price);
        
        //pass it to addons data access object
        CatererDAO catererDAO = new CatererDAO(caterer);
        try {
            //update the database
            catererDAO.insertIntoTable();
        } catch (SQLException ex) {
            Logger.getLogger(AddonsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //update the table view and combo boxes
        updateCaterersTable();
        updateCatererComboBoxes();
        
        //reset the input fields
        addCatererName.setText("");
        addCatererPrice.setText("");
    }//end addCatererToDatabase()
    
    //modify an exisiting caterer
    public void modifyCatererInDatabase(){
        //get the input data
        String catererName = modifyCatererSelect.getValue();
        int price = Integer.parseInt(modifyCatererPrice.getText());
        
        //pass the data to a new caterer object
        Caterer modifyCaterer = new Caterer(catererName, price);
        
        //create new Caterer data access object
        CatererDAO catererDAO = new CatererDAO(modifyCaterer);
        
        try {
            //update the table
            catererDAO.updateTable();
        } catch (SQLException ex) {
            Logger.getLogger(CateringController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //update the table
        updateCaterersTable();
        
        //reset the input fields
        modifyCatererPrice.setText("");
    }//end modifyCatererInDatabase()()
    
    //remove an caterer from the database
    public void removeCatererInDatabase(){
        //get the input data
        String catererName = removeCatererSelect.getValue();
        
        //pass the data to a new caterer object
        Caterer removeCaterer = new Caterer(catererName);
        
        //create a new caterer data acces object and remove from the database
        CatererDAO catererDAO =  new CatererDAO(removeCaterer);
        
        try {
            catererDAO.deleteFromTable();
        } catch (SQLException ex) {
            Logger.getLogger(AddonsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //update the table and the combo boxes
        updateCaterersTable();
        updateCatererComboBoxes();
    }//end removeCatererInDatabase()
}
