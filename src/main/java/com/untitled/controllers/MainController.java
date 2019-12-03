package main.java.com.untitled.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
                
            }else if(navBar.isVisible() == false){
                navBar.setVisible(true);
                navIcon.setStyle("-fx-fill: #5a7ca3;");
            }
            
        });//end navButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{})
        
        
        homeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{
            resetButtons();
            homeButton.setStyle("-fx-background-color: #5a7ca3;");
        });
        
        salesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{
            resetButtons();
            salesButton.setStyle("-fx-background-color: #5a7ca3;");
        });
    }
    
    //this resets the colors of the buttons when another button is pressed
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
