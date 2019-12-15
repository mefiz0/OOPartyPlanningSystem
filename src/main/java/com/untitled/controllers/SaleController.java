package main.java.com.untitled.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import main.java.com.untitled.Sale;
import main.java.com.untitled.dao.AddonsDAO;
import main.java.com.untitled.dao.CustomerDAO;
import main.java.com.untitled.dao.PartyDAO;
import main.java.com.untitled.dao.SalesDAO;
import main.java.com.untitled.dao.VenuesDAO;

public class SaleController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXComboBox<String> customerSaleSelect;
    
    @FXML
    private Label customerName;

    @FXML
    private JFXComboBox<String> partySaleSelect;

    @FXML
    private JFXDatePicker addDueDate;

    @FXML
    private JFXTimePicker addDueTime;

    @FXML
    private JFXComboBox<String> addVenueSelect;

    @FXML
    private JFXComboBox<String> addonOneSelect;

    @FXML
    private JFXComboBox<String> addonTwoSelect;

    @FXML
    private JFXComboBox<String> addonThreeSelect;

    @FXML
    private JFXTextField saleAmountPaid;

    @FXML
    private JFXComboBox<String> catererSelect;

    @FXML
    private JFXButton saleButton;

    @FXML
    private Label subTotal;

    @FXML
    private Label totalPrice;

    @FXML
    private TableView<?> paymentsTable;

    @FXML
    private JFXButton makePaymentButton;

    @FXML
    private JFXTextField amountToBePaid;

    @FXML
    private JFXComboBox<?> customerPaymentsSelect;

    @FXML
    private JFXComboBox<?> partyPaymentSelect;

    @FXML
    void initialize() {
        
        customerSaleSelect.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            //create a new customer dao
            CustomerDAO customerDAO = new CustomerDAO();
            try {
                //get the customer data hashmap
                HashMap data = customerDAO.getCustomerDataBasedOnID(newValue);
                customerName.setText((String) data.get("name")); //set the string
                customerName.setVisible(true); //and make it visible
            } catch (SQLException ex) {
                Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }
    
    //set the combo boxes
    public void updateSalesBoxes(){
        //create the data access objects
        CustomerDAO customerDAO = new CustomerDAO();
        PartyDAO partyDAO = new PartyDAO();
        VenuesDAO venuesDAO = new VenuesDAO();
        AddonsDAO addonsDAO = new AddonsDAO();
        
        try {
            //add all the data to a list
            ObservableList customerIDs = customerDAO.getListOfAllCustomerIDs();
            ObservableList partyTypes = partyDAO.getListOfAllPartyTypes();
            ObservableList venues = venuesDAO.getListOfAllVenues();
            ObservableList addons = addonsDAO.getAllAddonsTypes();
            
            //set the combo boxes
            customerSaleSelect.setItems(customerIDs);
            partySaleSelect.setItems(partyTypes);
            addVenueSelect.setItems(venues);
            addonOneSelect.setItems(addons);
            addonTwoSelect.setItems(addons);
            addonThreeSelect.setItems(addons);
        } catch (SQLException ex) {
            Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//end updateSalesBoxes
    
    //make a sale
    public void makeSale(){
        //get the input data
        String customer = customerSaleSelect.getValue();
        String party = partySaleSelect.getValue();
        Date dueDate = Date.valueOf(addDueDate.getValue());
        Time dueTime = Time.valueOf(addDueTime.getValue());
        String venue = addVenueSelect.getValue();
        String addonOne = addonOneSelect.getValue();
        String addonTwo = addonTwoSelect.getValue();
        String addonThree = addonThreeSelect.getValue();
        BigDecimal amountPaid = new BigDecimal(saleAmountPaid.getText());
        String caterer = catererSelect.getValue();
        BigDecimal total = new BigDecimal(totalPrice.getText());
        
        //create a new sale object
        Sale sale = new Sale(venue, dueDate, dueTime, addonOne, addonTwo,
                     addonThree, caterer, amountPaid, total, party);
        
        //create a new Sale DAO object
        SalesDAO salesDAO = new SalesDAO(sale);
        
        try {
            //update the database
            salesDAO.insertIntoTable();
        } catch (SQLException ex) {
            Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
