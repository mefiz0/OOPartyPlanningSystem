package userinterface.controllers;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class AddonsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton logOutButton;

    @FXML
    private Label currentUserName;

    @FXML
    private JFXButton goToHomeButton;

    @FXML
    private JFXButton goToSalesButton;

    @FXML
    private JFXButton goToPartiesButton;

    @FXML
    private JFXButton goToVenuesButton;

    @FXML
    private JFXButton goToAddOnsButton;

    @FXML
    private JFXButton goToCustomersButton;

    @FXML
    private Label settingsLabel;

    @FXML
    private JFXButton goToUsersButton;

    @FXML
    private TableView<?> addOnsTableView;

    @FXML
    private JFXButton addAddonsButton;

    @FXML
    private JFXButton modifyAddonsButton;

    @FXML
    private JFXButton removeAddonsButton;

    @FXML
    private TableView<?> cateringTableView;

    @FXML
    private JFXButton addCateringOptionButton;

    @FXML
    private JFXButton modifyCateringOptionButton;

    @FXML
    private JFXButton removeCateringOptionButton;

    @FXML
    void initialize() {
        
    }
    
    public void initAddons(String currentUsername){
        currentUserName.setText(currentUsername);
    }
}
