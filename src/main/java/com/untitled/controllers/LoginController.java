package main.java.com.untitled.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.com.untitled.models.UserAccessModel;
import main.java.com.untitled.models.UserModel;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField usernameInput;

    @FXML
    private JFXPasswordField passwordInput;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton closeButton;
    
    @FXML
    private FontAwesomeIcon closeIcon;
    
    @FXML
    void initialize() {
        //when the mouse moves into the closeButton
        closeButton.setOnMouseEntered((event) -> {
            closeIcon.setFill(Color.web("#4099ff"));
        });
        
        //when the mouse exits the closeButton
        closeButton.setOnMouseExited((event) -> {
            closeIcon.setFill(Color.WHITE);
        });
        
        
        //when the login button is pressed
        loginButton.setOnAction((event) ->{
            authenticateAndLogin(); //authenticate the user and log in
        });
        
        //when the closeButton is pressed
        closeButton.setOnAction((event) -> {
            Stage currentStage = (Stage) closeButton.getScene().getWindow();
            currentStage.close();
        });
        
    }
    
    //authenticate the user and login
    public void authenticateAndLogin(){
       //get the input
            String username = usernameInput.getText();
            String password = passwordInput.getText();

            //create a new usermodel
            UserModel userLogin = new UserModel(username, password);

            int authenticationStatus = 0;

            try {
                authenticationStatus = userLogin.userLogin();
                System.out.println(authenticationStatus);
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }

            //if the user is successfully logged in
            if(authenticationStatus > 0){
                //create a user access model
                UserAccessModel userAccess = new UserAccessModel();

                //set the user id
                userAccess.setUserID(authenticationStatus);

                //update the access table
                try {
                    userAccess.insertIntoTable();
                } catch (SQLException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }

                //close the current stage
                Stage currentStage = (Stage) loginButton.getScene().getWindow();
                currentStage.close();
                
                //open the main stage
                Parent root;
                try {
                    //get the main fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/com/untitled/view/Main.fxml")); 
                    root = loader.load(); //load  it
                    //get the controller and set the view username and role
                    MainController controller = loader.getController();
                    controller.setUsername(username.toUpperCase());
                    controller.setRole(userLogin.getUserRole(username));
                    //set the scene
                    Scene scene = new Scene(root);
                    Stage mainStage = new Stage(); // <- new stage
                    //load the scene to the stage
                    mainStage.setScene(scene);
                    mainStage.initStyle(StageStyle.UNDECORATED); //stage style undecorated
                    mainStage.setMaximized(true);
                    mainStage.show();
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } else if(authenticationStatus == 0){
                usernameInput.setText("");
                passwordInput.setText("");
                
                Alert loginFailed = new Alert(AlertType.ERROR);
                loginFailed.setHeaderText("Login Failed!");
                loginFailed.setContentText("Please check username and/or password.");
                loginFailed.showAndWait();
            }
    }
}
