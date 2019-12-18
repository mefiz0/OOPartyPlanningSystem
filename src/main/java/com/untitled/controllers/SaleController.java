package main.java.com.untitled.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import main.java.com.untitled.Services.Sale;
import main.java.com.untitled.models.AddonsModel;
import main.java.com.untitled.models.CatererModel;
import main.java.com.untitled.models.CustomerModel;
import main.java.com.untitled.models.PartyModel;
import main.java.com.untitled.models.PaymentsModel;
import main.java.com.untitled.models.SalesModel;
import main.java.com.untitled.models.SalesTableModel;
import main.java.com.untitled.models.VenuesModel;

public class SaleController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXComboBox<String> customerSaleSelect;

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
    private Label customerName;

    @FXML
    private TableView salesTable;

    @FXML
    private TableColumn<SalesTableModel, Integer> rowNumColumn;

    @FXML
    private TableColumn<SalesTableModel, String> customerIDColumn;

    @FXML
    private TableColumn<SalesTableModel, String> customerNameColumn;

    @FXML
    private TableColumn<SalesTableModel, String> partyTypeColumn;

    @FXML
    private TableColumn<SalesTableModel, Date> dueDateColumn;

    @FXML
    private TableColumn<SalesTableModel, Time> dueTimeColumn;

    @FXML
    private TableColumn<SalesTableModel, String> venueColumn;

    @FXML
    private TableColumn<SalesTableModel, String> catererColumn;

    @FXML
    private TableColumn<SalesTableModel, String> addonOneColumn;

    @FXML
    private TableColumn<SalesTableModel, String> addonTwoColumn;

    @FXML
    private TableColumn<SalesTableModel, String> addonThreeColumn;

    @FXML
    private TableColumn<SalesTableModel, BigDecimal> totalPriceColumn;

    @FXML
    private TableColumn<SalesTableModel, String> toBePaidColumn;

    @FXML
    private JFXButton makePaymentButton;

    @FXML
    private JFXComboBox<String> customerPaymentsSelect;

    @FXML
    private JFXComboBox<String> partyPaymentSelect;
    
    @FXML
    private JFXButton calculatePricesButton;
    
    @FXML
    private JFXTabPane salesTabPane;

    @FXML
    private Tab saleTab;
    
    @FXML
    private Tab historyTab;
    
    @FXML
    private AnchorPane historyPane;
    
    @FXML
    private GridPane dataAccessGrid;
    
    @FXML
    void initialize() {
        //update the table view
        updateTableView();
        
        //set these to not visible at start
        subTotal.setVisible(false);
        totalPrice.setVisible(false);
        customerName.setVisible(false);
        
        //when a customer id is selected print the customers name
        customerSaleSelect.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            //create a new customer dao
            CustomerModel customerDAO = new CustomerModel();
            try {
                //get the customer data hashmap
                HashMap data = customerDAO.getCustomerDataBasedOnID(newValue);
                customerName.setText((String) data.get("name")); //set the string
                customerName.setVisible(true); //and make it visible
            } catch (SQLException ex) {
                Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }); //end customerSaleSelect.getSelectionModel().selectedItemProperty().addListener
        
        //calculate the prices
        calculatePricesButton.setOnAction((event) -> {
            //get the input data
            Sale sale = new Sale();
            String customer = customerSaleSelect.getValue();
            sale.setType(partySaleSelect.getValue());
            sale.setDueDate(Date.valueOf(addDueDate.getValue()));
            sale.setDueTime(Time.valueOf(addDueTime.getValue()));
            sale.setVenue(addVenueSelect.getValue());
            sale.setAddonOne(addonOneSelect.getValue());
            sale.setAddonTwo(addonTwoSelect.getValue());
            sale.setAddonThree(addonThreeSelect.getValue());
            sale.setCaterer(catererSelect.getValue());
            
            try {
                //set the prices
                subTotal.setText(Integer.toString(sale.calculateSubtotal()));
                subTotal.setVisible(true);
                sale.calculateTotalPrice(sale.calculateSubtotal());
                totalPrice.setText(sale.getTotalPrice().toString());
                totalPrice.setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }); //end   calculatePricesButton.setOnAction((event) -> {
        
        //set the combo boxes to get the data on click
        customerSaleSelect.setOnShowing((event) -> {
            //create the data access objects
            CustomerModel customerModel = new CustomerModel();
            
            //get the data into the list
            try {
                //get the data
                ObservableList customerIDs = customerModel.getListOfAllCustomerIDs();
                customerSaleSelect.setItems(customerIDs);// set the data to the combo box
            } catch (SQLException ex) {
                Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });//end customerSaleSelect.setOnShowing
        
        //set the combo boxes to get the data on click
        partySaleSelect.setOnShowing((event) -> {
            //create the data access objects
            PartyModel partyModel = new PartyModel();
            
            //get the data into the list
            try {
                //get the data
                ObservableList partyTypes = partyModel.getListOfAllPartyTypes();
                partySaleSelect.setItems(partyTypes);// set the data to the combo box
            } catch (SQLException ex) {
                Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }); //end partySaleSelect.setOnShowing
        
        //set the combo boxes to get the data on click
        addVenueSelect.setOnShowing((event) -> {
            //create the data access objects
            VenuesModel venues = new VenuesModel();
            
            //get the data into the list
            try {
                //get the data
                ObservableList venuesList = venues.getListOfAllVenues();
                addVenueSelect.setItems(venuesList);// set the data to the combo box
            } catch (SQLException ex) {
                Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }); //addVenueSelect.setOnShowing
        
        //set the combo boxes to get the data on click
        addonOneSelect.setOnShowing((event) -> {
            //create the data access objects
            AddonsModel addonsModel = new AddonsModel();
            
            //get the data into the list
            try {
                //get the data
                ObservableList addonsList = addonsModel.getAllAddonsTypes();
                addonOneSelect.setItems(addonsList);// set the data to the combo box
            } catch (SQLException ex) {
                Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }); //addonOneSelect.setOnShowing
        
        //set the combo boxes to get the data on click
        addonTwoSelect.setOnShowing((event) -> {
            //create the data access objects
            AddonsModel addonsModel = new AddonsModel();
            
            //get the data into the list
            try {
                //get the data
                ObservableList addonsList = addonsModel.getAllAddonsTypes();
                addonTwoSelect.setItems(addonsList);// set the data to the combo box
            } catch (SQLException ex) {
                Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }); //addonTwoSelect.setOnShowing
        
        //set the combo boxes to get the data on click
        addonThreeSelect.setOnShowing((event) -> {
            //create the data access objects
            AddonsModel addonsModel = new AddonsModel();
            
            //get the data into the list
            try {
                //get the data
                ObservableList addonsList = addonsModel.getAllAddonsTypes();
                addonThreeSelect.setItems(addonsList);// set the data to the combo box
            } catch (SQLException ex) {
                Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }); //addonThreeSelect.setOnShowing
        
        catererSelect.setOnShowing((event) -> {
            //create the data access objects
            CatererModel caterer = new CatererModel();
            
            //get the data into the list
            try {
                //get the data
                ObservableList caterersList = caterer.getAllCaterers();
                catererSelect.setItems(caterersList);// set the data to the combo box
            } catch (SQLException ex) {
                Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }); //catererSelect.setOnShowing
        
        //payment combo box
        customerPaymentsSelect.setOnShowing((event) -> {
            //create the data access objects
            PaymentsModel payments = new PaymentsModel();
            
            //get the data into the list
            try {
                //get the data
                ObservableList customersList = payments.getCustomerIDs();
                customerPaymentsSelect.setItems(customersList);// set the data to the combo box
            } catch (SQLException ex) {
                Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }); //customerPaymentsSelect.setOnShowing
        
        //set the party items in the party payments
        customerPaymentsSelect.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
            //create a payment model
            PaymentsModel payments = new PaymentsModel();
            
            //get the data into the parties tab
            try {
                ObservableList partiesList  = payments.getPartyTypes(newValue);
                partyPaymentSelect.setItems(partiesList);
            } catch (SQLException ex) {
                Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }); //customerPaymentsSelect.getSelectionModel()
        
        //when the sale button is pressed
        saleButton.setOnAction((event) -> {
            makeSale(); //make a sale
        }); //end saleButton.setOnAction
        
        //when makePaymentButton is pressed
        makePaymentButton.setOnAction((event) -> {
            makePayment(); //make payment
        }); //makePaymentButton
    }
    
    //update the table view
    public void updateTableView(){
        //create a new salesTableModel
        SalesTableModel salesHistory = new SalesTableModel();
        
        //set the columns
        rowNumColumn.setCellValueFactory(cellData -> cellData.getValue().getRowNum().asObject());
        customerIDColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomerID());
        customerNameColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomerName());
        partyTypeColumn.setCellValueFactory(cellData -> cellData.getValue().getPartyType());
        dueDateColumn.setCellValueFactory(cellData -> cellData.getValue().getDueDate());
        dueTimeColumn.setCellValueFactory(cellData -> cellData.getValue().getDueTime());
        venueColumn.setCellValueFactory(cellData -> cellData.getValue().getVenue());
        catererColumn.setCellValueFactory(cellData -> cellData.getValue().getCaterer());
        addonOneColumn.setCellValueFactory(cellData -> cellData.getValue().getAddonOne());
        addonTwoColumn.setCellValueFactory(cellData -> cellData.getValue().getAddonTwo());
        addonThreeColumn.setCellValueFactory(cellData -> cellData.getValue().getAddonThree());
        totalPriceColumn.setCellValueFactory(cellData -> cellData.getValue().getTotalPrice());
        toBePaidColumn.setCellValueFactory(cellData -> cellData.getValue().getToBePaid());
        
        try {
            //get the sales history in an observable list
            ObservableList salesHistoryList = salesHistory.getSalesHistory();
            salesTable.setItems(salesHistoryList); //set the items in the table to this list
        } catch (SQLException ex) {
            Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//end updateTableView()
    
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
        
        System.out.println(dueDate);
        System.out.println(dueTime);
        
        //create a new sale object
        Sale sale = new Sale(venue, dueDate, dueTime, addonOne, addonTwo,
                     addonThree, caterer, amountPaid, total, party);
        
        //create a new Sale model object
        SalesModel salesModel = new SalesModel(sale);
        
        //set the customer id
        salesModel.setCustomerID(customer);
        
        try {
            //update the database
            salesModel.insertIntoTable();
        } catch (SQLException ex) {
            Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        updateTableView();
        
        //clear the inputs
        customerSaleSelect.getSelectionModel().clearSelection();
        partySaleSelect.getSelectionModel().clearSelection();
        addDueDate.getEditor().clear();
        addDueTime.getEditor().clear();
        addVenueSelect.getSelectionModel().clearSelection();
        addonOneSelect.getSelectionModel().clearSelection();
        addonTwoSelect.getSelectionModel().clearSelection();
        addonThreeSelect.getSelectionModel().clearSelection();
        saleAmountPaid.setText("");
        catererSelect.getSelectionModel().clearSelection();
        subTotal.setText("");
        totalPrice.setText("");
    }//end makeSale()
    
    //make a payment
    public void makePayment(){
        //get the input
        String customerID = customerPaymentsSelect.getValue();
        String partyType = partyPaymentSelect.getValue();
        
        //create a new payments model
        PaymentsModel payments = new PaymentsModel(customerID, partyType);
        
        try {
            //update the database()
            payments.updateTable();
        } catch (SQLException ex) {
            Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //update the table view
        updateTableView();
        
        //clear the data fields
        customerPaymentsSelect.getSelectionModel().clearSelection();
        partyPaymentSelect.getSelectionModel().clearSelection();
    }//end makePayment
    
    //set what is avaliable to the user depending on the user role
    public void setPermissions(String role){
        if(role.equals("Event Manager")){
            salesTabPane.getTabs().remove(saleTab);
            
            historyPane.getChildren().remove(makePaymentButton);
            historyPane.getChildren().remove(dataAccessGrid);
            
            AnchorPane.setBottomAnchor(salesTable, 0.0);
        }
    }
}
