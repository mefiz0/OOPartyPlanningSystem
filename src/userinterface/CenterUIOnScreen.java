/*
    This method is only used to center the user interface on the screen
 */
package userinterface;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class CenterUIOnScreen {
    
    public static void CenterUIOnScreen(Stage stage){
        
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2 ); //center x value is half of screen width - width of stage
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2); //center y value is half of screen height - height of stage
    }
}
