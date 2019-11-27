/*
    This class facilitates ui scene switching
 */
package userinterface.controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class UIManager {
    private Scene scene;
    
    public UIManager(Scene scene){
        this.scene = scene;
    }
    
    //switches the scene to the homeview
    private void showHomeView(String username){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userinterface/FXML/Home.fxml")); //get the fxml
            scene.setRoot((Parent)loader.load()); //load it to the scene
            HomeController controller = loader.<HomeController>getController(); //get the controller
            controller.initHome(this, username); //show the username in the home tab
        } catch (IOException ex) {
            Logger.getLogger(UIManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //switches the scene to the parties tab
    private void showPartiesView(String username){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userinterface/FXML/Parties.fxml")); //get the fxml
            scene.setRoot((Parent)loader.load()); //load it to the scene
            PartiesController controller = loader.<PartiesController>getController(); //get the controller
            //controller.initParties(this, username); //show the username in the parties tab
        } catch (IOException ex) {
            Logger.getLogger(UIManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        //switches the scene to the sales tab
    private void showSalesView(String username){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userinterface/FXML/Sales.fxml")); //get the fxml
            scene.setRoot((Parent)loader.load()); //load it to the scene
            SalesController controller = loader.<SalesController>getController(); //get the controller
            //controller.initSales(this, username); //show the username in the sales tab
        } catch (IOException ex) {
            Logger.getLogger(UIManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
            //switches the scene to the Addons tab
    private void showAddonsView(String username){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userinterface/FXML/Addons.fxml")); //get the fxml
            scene.setRoot((Parent)loader.load()); //load it to the scene
            AddonsController controller = loader.<AddonsController>getController(); //get the controller
            //controller.initAddons(this, username); //show the username in the sales tab
        } catch (IOException ex) {
            Logger.getLogger(UIManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
                //switches the scene to the Customers tab
    private void showCustomersView(String username){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userinterface/FXML/Customers.fxml")); //get the fxml
            scene.setRoot((Parent)loader.load()); //load it to the scene
            CustomersController controller = loader.<CustomersController>getController(); //get the controller
            //controller.initCustomers(this, username); //show the username in the sales tab
        } catch (IOException ex) {
            Logger.getLogger(UIManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
                //switches the scene to the Users tab
    private void showUsersView(String username){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userinterface/FXML/Users.fxml")); //get the fxml
            scene.setRoot((Parent)loader.load()); //load it to the scene
            UsersController controller = loader.<UsersController>getController(); //get the controller
            //controller.initUsers(this, username); //show the username in the sales tab
        } catch (IOException ex) {
            Logger.getLogger(UIManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
                //switches the scene to the Venues tab
    private void showVenuesView(String username){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userinterface/FXML/Venues.fxml")); //get the fxml
            scene.setRoot((Parent)loader.load()); //load it to the scene
            VenuesController controller = loader.<VenuesController>getController(); //get the controller
            //controller.initVenues(this, username); //show the username in the sales tab
        } catch (IOException ex) {
            Logger.getLogger(UIManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
