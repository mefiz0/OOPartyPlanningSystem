
package main.java.com.untitled;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{
    
    public static void main(String [] args){
        Application.launch(args);
        
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
       Parent root = FXMLLoader.load(getClass().getResource("/main/resources/com/untitled/view/Main.fxml"));
       Scene scene = new Scene(root);
       primaryStage.setScene(scene);
       primaryStage.setMaximized(true);
       primaryStage.setResizable(false);
       primaryStage.show();
    }
    
}
