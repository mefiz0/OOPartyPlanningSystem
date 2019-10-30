
package oopartyplanningsystem;

import database.DatabaseConnection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import static userinterface.CenterUIOnScreen.CenterUIOnScreen;

public class OOPartyPlanningSystem extends Application{

    public static void main(String[] args) {
        //start the application
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //create a connection object
        DatabaseConnection connection = new DatabaseConnection();
        try {
            connection.initDB(); //initialize the database if database does not exist
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OOPartyPlanningSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Parent root = FXMLLoader.load(getClass().getResource("/userinterface/FXML/LoginPageFXML.fxml")); //get the loginpage fxml

        Scene scene = new Scene(root); //create a new scene
        primaryStage.setScene(scene); //load the scene into the stage
        primaryStage.setResizable(false); 
        primaryStage.show();
        
        CenterUIOnScreen(primaryStage);
    }
    
}
