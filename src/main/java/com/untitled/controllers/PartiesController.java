package main.java.com.untitled.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import main.java.com.untitled.dao.CustomerDAO;
import main.java.com.untitled.dao.PartyDAO;

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
    private TableColumn<PartyDAO, Integer> rowNumColumn;

    @FXML
    private TableColumn<PartyDAO, String> typeColumn;

    @FXML
    private TableColumn<PartyDAO, Integer> priceColumn;

    @FXML
    private TableColumn<PartyDAO, String> taskOneColumn;

    @FXML
    private TableColumn<PartyDAO, String> taskTwoColumn;

    @FXML
    private TableColumn<PartyDAO, String> taskThreeColumn;

    @FXML
    private TableColumn<PartyDAO, String> taskFourColumn;

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
    private JFXButton removeParty;

    @FXML
    private JFXComboBox<String> removePartySelect;

    @FXML
    void initialize() {
        
    }
    
    //update the parties table view
    public void updateTableView(){
        //create the list to hold the data in
        ObservableList<PartyDAO> partiesList = FXCollections.observableArrayList();
        
        //set the columns
        rowNumColumn.setCellValueFactory(cellData -> cellData.getValue().getRowNum().asObject());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().getType());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().getPrice().asObject());
        taskOneColumn.setCellValueFactory(cellData -> cellData.getValue().getTaskOne());
        taskTwoColumn.setCellValueFactory(cellData -> cellData.getValue().getTaskTwo());
        taskThreeColumn.setCellValueFactory(cellData -> cellData.getValue().getTaskThree());
        taskFourColumn.setCellValueFactory(cellData -> cellData.getValue().getTaskFour());
        
        //create a new database access object
        PartyDAO partiesDAO = new PartyDAO();
        
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
        PartyDAO partyDAO = new PartyDAO();
        
        try {
            ObservableList partyTypes = partyDAO.getListOfAllPartyTypes();
            modifyTypeSelect.setItems(partyTypes);
            removePartySelect.setItems(partyTypes);
        } catch (SQLException ex) {
            Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//end updateIDComboBoxes(){}
}
