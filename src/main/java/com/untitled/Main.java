
package main.java.com.untitled;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.com.untitled.db.DatabaseConnection;

public class Main extends Application{
    
    public static void main(String [] args){
        Application.launch(args);
        
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
       
       DatabaseConnection.initDB();
       
       Parent root = FXMLLoader.load(getClass().getResource("/main/resources/com/untitled/view/Login.fxml"));
       Scene scene = new Scene(root);
       primaryStage.setScene(scene);
       primaryStage.setResizable(false);
       primaryStage.initStyle(StageStyle.UNDECORATED);
       primaryStage.show();
    }
    
}
