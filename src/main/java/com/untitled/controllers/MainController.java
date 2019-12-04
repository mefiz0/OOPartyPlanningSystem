package main.java.com.untitled.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

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
    private JFXButton PartiesButton;

    @FXML
    private JFXButton venuesButton;

    @FXML
    private JFXButton addonsButton;

    @FXML
    private JFXButton customersButton;

    @FXML
    private JFXButton userSettingsButton;
    
    @FXML
    void initialize() {
        //if the nav button is clicked show the nav bar
        navButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{
            
            //if the nav bar is visible show the navbar otherwise hide it
            if(navBar.isVisible() == true){
                navBar.setVisible(false);
                navIcon.setStyle("-fx-fill: #000000;");
                borderPane.setLeft(null);
                
            }else if(navBar.isVisible() == false){
                borderPane.setLeft(navBar);
                navBar.setVisible(true);
                navIcon.setStyle("-fx-fill: #5a7ca3;");
            }
            
        });//end navButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{})
        
        //load the home view into the center of the border pane
        homeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{
            try {
                resetButtons();
                homeButton.setStyle("-fx-background-color: #5a7ca3;");
                
                FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("/main/resources/com/untitled/view/Home.fxml"));
                
                AnchorPane home = homeLoader.load(); //load the home fxml and set it to the anchor pane
                HomeController homeController = homeLoader.getController(); //set the controller
                
                borderPane.setCenter(home); //set it to center
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        //load the sales view into the center of the border pane
        salesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{
            resetButtons();
            salesButton.setStyle("-fx-background-color: #5a7ca3;");
        });
    }
    
    //resets the colors of the buttons when another button is pressed
    private void resetButtons(){
        homeButton.setStyle("-fx-background-color: #2b3b4e;");
        salesButton.setStyle("-fx-background-color: #2b3b4e;");
        tasksButton.setStyle("-fx-background-color: #2b3b4e;");
        PartiesButton.setStyle("-fx-background-color: #2b3b4e;");
        venuesButton.setStyle("-fx-background-color: #2b3b4e;");
        addonsButton.setStyle("-fx-background-color: #2b3b4e;");
        customersButton.setStyle("-fx-background-color: #2b3b4e;");
        userSettingsButton.setStyle("-fx-background-color: #2b3b4e;");
    }
}
