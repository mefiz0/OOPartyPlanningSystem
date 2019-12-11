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
import main.java.com.untitled.Customer;
import main.java.com.untitled.dao.CustomerDAO;

public class CustomersController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane customersPane;

    @FXML
    private TableView customersTable;

    @FXML
    private TableColumn<CustomerDAO, String> customerIDColumn;

    @FXML
    private TableColumn<CustomerDAO, String> customerNameColumn;

    @FXML
    private TableColumn<CustomerDAO, Integer> customerBankColumn;

    @FXML
    private TableColumn<CustomerDAO, Integer> customerContactNumColumn;

    @FXML
    private TableColumn<CustomerDAO, String> customerEmailIDColumn;

    @FXML
    private JFXTextField addCustomerID;

    @FXML
    private JFXTextField addCustomerContactNum;

    @FXML
    private JFXTextField addCustomerBankNum;

    @FXML
    private JFXTextField addCustomerName;

    @FXML
    private JFXTextField addCustomerEmail;

    @FXML
    private JFXButton addCustomerButton;

    @FXML
    private JFXButton modifyCustomerButton;

    @FXML
    private JFXTextField modifyCustomerContactNum;

    @FXML
    private JFXTextField modifyCustomerBankAccount;

    @FXML
    private JFXTextField modifyCustomerName;

    @FXML
    private JFXTextField modifyCustomerEmail;

    @FXML
    private JFXComboBox<String> modifyCustomerIDSelect;

    @FXML
    private JFXButton removeCustomer;

    @FXML
    private JFXComboBox<String> removeCustomerIDSelect;

    @FXML
    void initialize() {
        
    }
    
    //update the customers table
    public void updateTableView(){
        //create the list to hold the data in
        ObservableList<CustomerDAO> customersList = FXCollections.observableArrayList();
        
        //set the columns
        customerIDColumn.setCellValueFactory(cellData -> cellData.getValue().getIdNum());
        customerNameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
        customerBankColumn.setCellValueFactory(cellData -> cellData.getValue().getBankAccountNum().asObject());
        customerContactNumColumn.setCellValueFactory(cellData -> cellData.getValue().getContactNum().asObject());
        customerEmailIDColumn.setCellValueFactory(cellData -> cellData.getValue().getEmailAddress());
        
        //create a new database access object
        CustomerDAO customers = new CustomerDAO();
        
        try {
            //get the data from the database
            customersList = customers.getCustomerRecords();
        } catch (SQLException ex) {
            Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        customersTable.setItems(customersList);
    }//end updateTableView(){}
    
    //add data to the id combo boxes
    public void updateIDComboBoxes(){
        //create a new customerDAO
        CustomerDAO customerDAO = new CustomerDAO();
        
        try {
            ObservableList customerNames = customerDAO.getListOfAllCustomerIDs();
            modifyCustomerIDSelect.setItems(customerNames);
            removeCustomerIDSelect.setItems(customerNames);
        } catch (SQLException ex) {
            Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//end updateIDComboBoxes(){}
    
    //add the new customer to the database
    public void addCustomerToDatabase(){
        //get the data
        String customerID  = addCustomerID.getText();
        String customerName = addCustomerName.getText();
        int customerBankNum = Integer.parseInt(addCustomerBankNum.getText());
        int customerContactNumber = Integer.parseInt(addCustomerContactNum.getText());
        String customerEmail = addCustomerEmail.getText();
        
        //insert to a customer object
        Customer customer = new Customer(customerID, customerName, customerBankNum,
                                         customerContactNumber, customerEmail);
        //create a new customer data access object
        CustomerDAO customerDAO = new CustomerDAO(customer);
        //insert the data into the table
        try {
            customerDAO.insertIntoTable();
        } catch (SQLException ex) {
            Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//end addCustomerToDatabase()
}
