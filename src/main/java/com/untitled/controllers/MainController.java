package main.java.com.untitled.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private BorderPane borderPane;

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
    
    //Hashmap to preload and store all javafx anchor panes to improve perfomance
    HashMap<String, AnchorPane> viewPanes = new HashMap<>();
    HashMap<String, Object> viewControllers = new HashMap<>();
    
    
    @FXML
    void initialize() {
        
        initializeView();
        
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
            
        });//end navButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{})
        
        //load the home view into the center of the border pane
        homeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{
            try {
                resetButtons();

                FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("/main/resources/com/untitled/view/Home.fxml"));
                
                AnchorPane home = homeLoader.load(); //load the home fxml and set it to the anchor pane
                HomeController homeController = homeLoader.getController(); //set the controller
                
                borderPane.setCenter(home); //set it to center
                
                /*
                disable the button to prevent the user from clicking the button
                and having to perform the same operations again
                */
                homeButton.setDisable(true);
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }); //end homeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{})
        
        //load the sales view into the center of the border pane
//        salesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{
//            resetButtons();
//        });
        
        //load the parties view into the center of the border pane
        partiesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{
            resetButtons(); //reset the buttons
            //load the anchor pane and controller from the hashmaps
            PartiesController partiesController = (PartiesController) viewControllers.get("Parties");
            borderPane.setCenter(viewPanes.get("Parties")); //set it to center
            /*
            disable the button to prevent the user from clicking the button
            and having to perform the same operations again
            */
            partiesButton.setDisable(true);
        }); //end partiesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{})
        
        //load the parties view into the center of the border pane
        venuesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{
            resetButtons(); //reset the buttons
            //load the anchor pane and controller from the hashmaps
            VenuesController venuesController = (VenuesController) viewControllers.get("Venues");
            borderPane.setCenter(viewPanes.get("Venues")); //set it to center
            /*
            disable the button to prevent the user from clicking the button
            and having to perform the same operations again
            */
            venuesButton.setDisable(true);
        }); //end venuesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{})
        
        //load the customers view into the center of the pane
        customersButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{
            resetButtons(); //reset the buttons
            //load the anchor pane and controller from the hashmaps
            CustomersController customersController = (CustomersController) viewControllers.get("Customers");
            borderPane.setCenter(viewPanes.get("Parties")); //set it to center
            /*
            disable the button to prevent the user from clicking the button
            and having to perform the same operations again
            */
            customersButton.setDisable(true);
        }); //end customersButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{})
    }
    
    //set a disabled button to enabled
    private void resetButtons(){
        //enable the buttons if the button is disabled
        
        /*
        home button
        */
        if(homeButton.isDisabled() == true){
            homeButton.setDisable(false);
        }
        
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
    
    private void initializeView(){
        //create a new thread to load the scenes
        new Thread(() -> {
            try {
                //load the fxml scenes and store them in viewCache HashMap
                
                /*
                Home
                */
                
                
                /*
                Sales
                */
                
                /*
                Tasks
                */
                
                
                /*
                Parties
                */
                FXMLLoader partiesLoader = new FXMLLoader(getClass().getResource("/main/resources/com/untitled/view/Parties.fxml"));
                AnchorPane partiesPane = partiesLoader.load();
                PartiesController partiesController = partiesLoader.getController();
                //add the controllers into the hashmaps
                viewPanes.put("Parties", partiesPane);
                viewControllers.put("Parties", partiesController);
                
                /*
                    Venues
                */
                FXMLLoader venuesLoader = new FXMLLoader(getClass().getResource("/main/resources/com/untitled/view/Venues.fxml"));
                AnchorPane venuesPane = venuesLoader.load();
                VenuesController venuesController = venuesLoader.getController();
                //add the controllers into the hashmaps
                viewPanes.put("Venues", venuesPane);
                viewControllers.put("Venues", venuesController);
                
                /*
                    Add-ons and Catering
                */
                
                /*
                    Customers
                */
                FXMLLoader customersLoader = new FXMLLoader(getClass().getResource("/main/resources/com/untitled/view/Customers.fxml"));
                AnchorPane customersPane = customersLoader.load();
                CustomersController customersController = customersLoader.getController();
                //add the controllers into the hashmaps
                viewPanes.put("Customers", customersPane);
                viewControllers.put("Customers", customersController);
                
                /*
                    Users
                */
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }
}
