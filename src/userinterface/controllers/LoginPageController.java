/*

 */
package userinterface.controllers;

import classes.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LoginPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView logoView;

    @FXML
    private JFXTextField usernameInput;

    @FXML
    private JFXPasswordField passwordInput;

    @FXML
    private JFXButton logInButton;

    @FXML
    private Label partyPlannerLabel;

    @FXML
    void initialize() {
        logInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //get the inputs
                String username = usernameInput.getText();
                String password = passwordInput.getText();
                
                User userLogin = new User(username, password); //create user object
                    
                try {
                    //if the user is in the database
                    if(userLogin.authenticateLogin() != 0){
                        userLogin.insertUserAccessRecord();  //update the database records
                            
                            
                        //open the home view
                        Stage mainStage = new Stage(); //create a new stage for the main application
                        try {
                        
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/userinterface/FXML/Home.fxml"));    
                        Parent root = (Parent)loader.load(); //load the fxml
                        HomeController controller = loader.getController(); //get the controller
                        controller.setCurrentUsername(username); //set the username to the current user
                        
                        Scene homeScene = new Scene(root); //create a new scene
                        mainStage.setScene(homeScene); //set the scene to the mainStage   
                        logInButton.getScene().getWindow().hide(); //hide the current window
                       
                        mainStage.show(); //show the main Stage - scene home
                        
                        mainStage.setMaximized(true); //maximize the current stage
                                
                    
                                
                        } catch (IOException ex) {
                            Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                            
                    } else if(userLogin.authenticateLogin() == 0){ //user is not in the database
                        //display error message
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Log In Error!");
                        alert.setHeaderText(null);
                        alert.setContentText("Invalid Username/Password!");
                            
                        alert.showAndWait();
                    }
                        
                } catch (SQLException ex) {
                    Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
            }
        });
        
    }
}   


/*
Also implement a basic hash for password protection
*/