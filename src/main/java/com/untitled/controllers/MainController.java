package main.java.com.untitled.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.com.untitled.models.UserAccessModel;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private BorderPane borderPane;
    
    @FXML
    private HBox topBar;
    
    @FXML
    private JFXButton navButton;
    
    @FXML
    private FontAwesomeIcon navIcon;
    
    @FXML
    private JFXDrawer navBar;

    @FXML
    private JFXButton homeButton;

    @FXML
    private JFXButton salesButton;
   
    @FXML
    private JFXButton salesHistoryButton;

    @FXML
    private JFXButton tasksButton;

    @FXML
    private JFXButton partiesButton;

    @FXML
    private JFXButton venuesButton;

    @FXML
    private JFXButton addonsButton;

    @FXML
    private JFXButton customersButton;

    @FXML
    private JFXButton userSettingsButton;
    
    @FXML
    private Label usernameView;

    @FXML
    private Label roleView;
    
    
    @FXML
    private JFXButton closeButton;

    @FXML
    private FontAwesomeIcon closeIcon;
    
    @FXML
    private JFXButton logOutButton;
    
    @FXML
    private VBox navButtons;
    
    //Hashmap to preload and store all javafx anchor panes to improve perfomance
    HashMap<String, AnchorPane> viewPanes = new HashMap<>();
    HashMap<String, Object> viewControllers = new HashMap<>();
    
    
    @FXML
    void initialize() {
        
        //initializeView();
        
        //if the nav button is clicked show the nav bar
        navButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{
            
            //if the nav bar is visible show the navbar otherwise hide it
            if(navBar.isVisible() == true){
                navBar.setVisible(false);
                navIcon.setFill(Color.BLACK);
                borderPane.setLeft(null);
                
            }else if(navBar.isVisible() == false){
                borderPane.setLeft(navBar);
                navBar.setVisible(true);
                navIcon.setFill(Color.web("#7b9bbd"));
            }
            
        }); //end customersButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{})
        
        /* home button is to be implemented later
        homeButton.setOnAction((event) -> {
            resetButtons();
            
            //load from hashmap
            HomeController homeController = (HomeController) viewControllers.get("Home");
            borderPane.setCenter(viewPanes.get("Home"));
            
            //disable the buttons
            homeButton.setDisable(true);
        });
        */
        salesButton.setOnAction((event) -> {
            resetButtons();
            
            //load from hashmap
            SaleController saleController = (SaleController) viewControllers.get("Sale");
            borderPane.setCenter(viewPanes.get("Sale"));
            
            //disable the buttons
            salesButton.setDisable(true);
        });
        
        tasksButton.setOnAction((event) -> {
            resetButtons();
            
            //load from hashmap
            TasksController tasksController = (TasksController) viewControllers.get("Tasks");
            borderPane.setCenter(viewPanes.get("Tasks"));
            tasksController.updateTableView();
            
            //disable the buttons
            tasksButton.setDisable(true);
        });
        
        partiesButton.setOnAction((event) -> {
            resetButtons();
            
            //load from hashmap
            PartiesController partiesController = (PartiesController) viewControllers.get("Parties");
            borderPane.setCenter(viewPanes.get("Parties"));
            
            //disable the buttons
            partiesButton.setDisable(true);
        });
        
        venuesButton.setOnAction((event) -> {
            resetButtons();
            
            //load from hashmap
            VenuesController venuesController = (VenuesController) viewControllers.get("Venues");
            borderPane.setCenter(viewPanes.get("Venues"));
            
            //disable the buttons
            venuesButton.setDisable(true);
        });
        
        addonsButton.setOnAction((event) -> {
            resetButtons();
            
            //load from hashmap
            AddonsController addonsController = (AddonsController) viewControllers.get("Addons");
            borderPane.setCenter(viewPanes.get("Addons"));
            
            //disable the buttons
            addonsButton.setDisable(true);
        });
        
        customersButton.setOnAction((event) -> {
            resetButtons();
            
            //load from hashmap
            CustomersController customersController = (CustomersController) viewControllers.get("Customers");
            borderPane.setCenter(viewPanes.get("Customers"));
            
            //disable the buttons
            customersButton.setDisable(true);
        });
        
        userSettingsButton.setOnAction((event) -> {
            resetButtons();
            
            //load from hashmap
            UserSettingsController userSettingsController = (UserSettingsController) viewControllers.get("Users");
            borderPane.setCenter(viewPanes.get("Users"));
            
            //disable the buttons
            userSettingsButton.setDisable(true);
        });
        
        //when the mouse moves into the closeButton
        closeButton.setOnMouseEntered((event) -> {
            closeIcon.setFill(Color.RED);
        });
        
        //when the mouse exits the closeButton
        closeButton.setOnMouseExited((event) -> {
            closeIcon.setFill(Color.BLACK);
        });
        
        //when the closeButton is pressed
        closeButton.setOnAction((event) -> {
            UserAccessModel userAccess = new UserAccessModel();
            try {
                userAccess.updateTable();
            } catch (SQLException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Stage currentStage = (Stage) closeButton.getScene().getWindow();
            currentStage.close();
        });
        
        //when the logOutButton is pressed
        logOutButton.setOnAction((event) -> {
            UserAccessModel userAccess = new UserAccessModel();
            try {
                userAccess.updateTable(); //insert the logged out record
                Stage currentStage = (Stage) closeButton.getScene().getWindow();
                currentStage.close(); //close the current stage
                
                //open the login page
                Parent root;
                try {
                    //get the main fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/com/untitled/view/Login.fxml")); 
                    root = loader.load(); //load  it
                    //set the scene
                    Scene scene = new Scene(root);
                    Stage loginStage = new Stage(); // <- new stage
                    //load the scene to the stage
                    loginStage.setScene(scene);
                    loginStage.initStyle(StageStyle.UNDECORATED); //stage style undecorated
                    loginStage.show();
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }   catch (SQLException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        
    }
    
    //set a disabled button to enabled
    private void resetButtons(){
        //enable the buttons if the button is disabled
        
        /*
        home button
        --to be implemented later (ver2.0)
        if(homeButton.isDisabled() == true){
            homeButton.setDisable(false);
        }
        */
        
        /*
        sales button
        */
        if(salesButton.isDisabled() == true){
            salesButton.setDisable(false);
        }
        
        /*
        tasks button
        */
        if(tasksButton.isDisabled() == true){
            tasksButton.setDisable(false);
        }
        
        /*
        parties button
        */
        if(partiesButton.isDisabled() == true){
            partiesButton.setDisable(false);
        }
        
        /*
        venues button
        */
        if(venuesButton.isDisabled() == true){
            venuesButton.setDisable(false);
        }
        
        /*
        addons button
        */
        if(addonsButton.isDisabled() == true){
            addonsButton.setDisable(false);
        }
        
        /*
        customers button
        */
        if(customersButton.isDisabled() == true){
            customersButton.setDisable(false);
        }
        
        /*
        user settings button
        */
        if(userSettingsButton.isDisabled() == true){
            userSettingsButton.setDisable(false);
        }
    }//end private void resetButtons(){}
    
    //reapply css for the topbar and the nav bar
    
    private void initializeView(String role){
        //create a new thread to load the scenes
        new Thread(() -> {
            try {
                //load the fxml scenes and store them in viewCache HashMap
                
                /*
                Home
                --to be implemented later (ver2.0)
                FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("/main/resources/com/untitled/view/Home.fxml"));
                AnchorPane homePane = homeLoader.load();
                HomeController homeController = homeLoader.getController();
                //add to the hashmaps
                viewPanes.put("Home", homePane);
                viewControllers.put("Home", homeController);
                */
                
                /*
                Sales
                */
                FXMLLoader saleLoader = new FXMLLoader(getClass().getResource("/main/resources/com/untitled/view/Sale.fxml"));
                AnchorPane salePane = saleLoader.load();
                SaleController saleController = saleLoader.getController();
                //add to the hashmaps
                viewPanes.put("Sale", salePane);
                viewControllers.put("Sale", saleController);
                
                /*
                Tasks
                */
                FXMLLoader tasksLoader = new FXMLLoader(getClass().getResource("/main/resources/com/untitled/view/Tasks.fxml"));
                AnchorPane tasksPane = tasksLoader.load();
                TasksController tasksController = tasksLoader.getController();
                //add to the hashmaps
                viewPanes.put("Tasks", tasksPane);
                viewControllers.put("Tasks", tasksController);
                
                /*
                Parties
                */
                FXMLLoader partiesLoader = new FXMLLoader(getClass().getResource("/main/resources/com/untitled/view/Parties.fxml"));
                AnchorPane partiesPane = partiesLoader.load();
                PartiesController partiesController = partiesLoader.getController();
                partiesController.setPermissions(role);
                //add to the hashmaps
                viewPanes.put("Parties", partiesPane);
                viewControllers.put("Parties", partiesController);
                
                /*
                    Venues
                */
                FXMLLoader venuesLoader = new FXMLLoader(getClass().getResource("/main/resources/com/untitled/view/Venues.fxml"));
                AnchorPane venuesPane = venuesLoader.load();
                VenuesController venuesController = venuesLoader.getController();
                //add to the hashmaps
                viewPanes.put("Venues", venuesPane);
                viewControllers.put("Venues", venuesController);
                
                /*
                    Add-ons and Catering
                */
                FXMLLoader addonsLoader = new FXMLLoader(getClass().getResource("/main/resources/com/untitled/view/Addons.fxml"));
                AnchorPane addonsPane = addonsLoader.load();
                AddonsController addonsController = addonsLoader.getController();
                //add to the hashmap
                viewPanes.put("Addons", addonsPane);
                viewControllers.put("Addons", addonsController);
                
                /*
                    Customers
                */
                FXMLLoader customersLoader = new FXMLLoader(getClass().getResource("/main/resources/com/untitled/view/Customers.fxml"));
                AnchorPane customersPane = customersLoader.load();
                CustomersController customersController = customersLoader.getController();
                //add to the hashmaps
                viewPanes.put("Customers", customersPane);
                viewControllers.put("Customers", customersController);
                
                /*
                    Users
                */
                FXMLLoader usersLoader = new FXMLLoader(getClass().getResource("/main/resources/com/untitled/view/UserSettings.fxml"));
                AnchorPane userSettingsPane = usersLoader.load();
                UserSettingsController userSettingsController = usersLoader.getController();
                //add to the hashmaps
                viewPanes.put("Users", userSettingsPane);
                viewControllers.put("Users", userSettingsController);
                
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }
    
    //set the username
    public void setUsername(String username){
        usernameView.setText(username);
    }
    
    //set the role and buttons based on role
    public void setRole(String role){
        roleView.setText(role);
        
        if(!role.equalsIgnoreCase("Administrator")){
            navButtons.getChildren().remove(userSettingsButton);
        }
        
        if(role.equals("Event Sales")){
            navButtons.getChildren().remove(tasksButton);
        }
        
        initializeView(role);
    }
}
