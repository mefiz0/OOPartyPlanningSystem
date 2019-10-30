/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import static userinterface.CenterUIOnScreen.CenterUIOnScreen;

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
                
                if((username != "") && (password != "")){
                    User userLogin = new User(username, password); //create user object
                    
                    try {
                        //if the user is in the database
                        if(userLogin.authenticateLogin() != 0){
                            userLogin.insertUserAccessRecord();  //update the database records
                            
                            //open the home view
                            
                        } else if(userLogin.authenticateLogin() == 0){ //user is not in the database
                            //display error message
                            Stage errorStage = new Stage();
                            
                            try {
                                //get the fxml
                                Parent root = FXMLLoader.load(getClass().getResource("/userinterface/FXML/LoginErrorFXML.fxml"));
                                
                                Scene scene = new Scene(root); //create a new scene
                                errorStage.setScene(scene); //load the scene into the stage
                                errorStage.setResizable(false); 
                                errorStage.show();
                                errorStage.centerOnScreen();
                                
                                CenterUIOnScreen(errorStage);
                                
                            } catch (IOException ex) {
                                Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                } else {
                    Stage errorStage = new Stage();

                    try {
                        //get the fxml
                        Parent root = FXMLLoader.load(getClass().getResource("/userinterface/FXML/LoginErrorFXML.fxml"));
                                
                        Scene scene = new Scene(root); //create a new scene
                        errorStage.setScene(scene); //load the scene into the stage
                        errorStage.setResizable(false); 
                        errorStage.show();
                        CenterUIOnScreen(errorStage);   
                    } catch (IOException ex) {
                        Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                                
                }
            }
        });
        
    }
}   


/*
Also implement a basic hash for password protection
*/