package main.java.com.untitled.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import main.java.com.untitled.User;
import main.java.com.untitled.models.UserAccessModel;
import main.java.com.untitled.models.UserModel;

public class UserSettingsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView usersTable;
    
    @FXML
    private TableColumn<UserModel, Integer> userIDColumn;
    
    @FXML
    private TableColumn<UserModel, String> usernameColumn;

    @FXML
    private TableColumn<UserModel, String> roleColumn;

    @FXML
    private JFXTextField addUsernameInput;

    @FXML
    private JFXPasswordField addPasswordInput;

    @FXML
    private JFXComboBox<String> addRoleInput;

    @FXML
    private JFXButton addUserButton;

    @FXML
    private JFXButton modifyUserButton;

    @FXML
    private JFXPasswordField modifyPasswordInput;

    @FXML
    private JFXComboBox<String> roleModifyComboBox;

    @FXML
    private JFXComboBox<String> usernameModifyComboBox;

    @FXML
    private JFXButton removeUserButton;

    @FXML
    private JFXComboBox<String> userRemoveComboBox;

    @FXML
    private TableView accessHistoryTable;
    
    @FXML
    private TableColumn<UserAccessModel, Integer> userIDAccessColumn;

    @FXML
    private TableColumn<UserAccessModel, String> usernameAccessColumn;

    @FXML
    private TableColumn<UserAccessModel, String> userRoleAccessColumn;

    @FXML
    private TableColumn<UserAccessModel, Timestamp> userAccessTimeColumn;

    @FXML
    private TableColumn<UserAccessModel, Timestamp> userLoggedOutTimeColumn;
    
    //list to hold the user information
    private ObservableList<UserModel> userList = FXCollections.observableArrayList();
    
    @FXML
    void initialize() {
        //initialize all the tables and the combo boxes
        updateUserTableView();
        updateRoleComboBoxes();
        updateUsernameComboBoxes();
        updateAccessTableView();
        
        //when the addUserButton is pressed
        addUserButton.setOnAction((event) -> {
            addUserToDatabase(); //add a user to the database
        });//end addUserButton.setOnAction((event) -> {})
        
        //when the modifyUserButton is pressed
        modifyUserButton.setOnAction((event) -> {
            modifyUserInDatabase(); //modify a user in the database
        }); //end modifyUserButton.setOnAction((event) -> {})
        
        //when the removeUserButton is pressed
        removeUserButton.setOnAction((event) -> {
            removeUserInDatabase(); //remove a user from the database
        }); //end removeUserButton.setOnAction((event) -> {})
        
        
    }
    
    //update the users table view
    public void updateUserTableView() {
        try {
            //set the columns
            userIDColumn.setCellValueFactory(cellData -> cellData.getValue().getUserID().asObject());
            usernameColumn.setCellValueFactory(cellData -> cellData.getValue().getUsername());
            roleColumn.setCellValueFactory(cellData -> cellData.getValue().getRole());
            
            //create a new database access object
            UserModel users = new UserModel();
            
            //get the data from the database
            userList = users.getUserRecords();
            
            //add the items to the users table
            usersTable.setItems(userList);
        } catch (SQLException ex) {
            Logger.getLogger(UserSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//end updateTableView(){}
    
     //update the access table view
    public void updateAccessTableView() {
        try {
            ObservableList<UserAccessModel> accessList = FXCollections.observableArrayList();
            
            //set the columns
            userIDAccessColumn.setCellValueFactory(cellValue -> cellValue.getValue().getUserID().asObject());
            usernameAccessColumn.setCellValueFactory(cellData -> cellData.getValue().getUsername());
            userRoleAccessColumn.setCellValueFactory(cellData -> cellData.getValue().getRole());
            userAccessTimeColumn.setCellValueFactory(cellData -> cellData.getValue().getAccessTime());
            userLoggedOutTimeColumn.setCellValueFactory(cellData -> cellData.getValue().getLoggedOutTime());
            
            //create a new database access object
            UserAccessModel usersAccess = new UserAccessModel();
            
            //get the data from the database
            accessList = usersAccess.getAccessRecords();
            
            //add the items to the users table
            accessHistoryTable.setItems(accessList);
        } catch (SQLException ex) {
            Logger.getLogger(UserSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//end updateTableView(){}
    
    //add roles to role comboboxes
    public void updateRoleComboBoxes() {
        //create an observable list
        ObservableList comboBoxData = FXCollections.observableArrayList(
                "Administrator",
                "Event Sales",
                "Event Manager"
        );
        
        addRoleInput.setItems(comboBoxData);
        roleModifyComboBox.setItems(comboBoxData);
    }// end updateRoleComboBox(){}
    
    //add usernames to the usernames combo boxes
    public void updateUsernameComboBoxes() {
        //update usernameModifyComboBox and userRemoveComboBox
        UserModel userDAO = new UserModel();
        try {
            ObservableList usernames  = userDAO.getListOfAllUsers();
            usernameModifyComboBox.setItems(usernames);
            userRemoveComboBox.setItems(usernames);
        } catch (SQLException ex) {
            Logger.getLogger(UserSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//end uppdateUsernameComboBoxes(){}
    
    //add a new user to the database
    public void addUserToDatabase(){
        //get the input
        String username = addUsernameInput.getText();
        String password = addPasswordInput.getText();
        String role = addRoleInput.getValue();
            
        //create the user object
        User addUser = new User(username, password, role);
            
        //pass the user object to user data access object
        UserModel userDAO = new UserModel(addUser);
            
        try {
            //add the new user to the table
            userDAO.insertIntoTable();
        } catch (SQLException ex) {
             Logger.getLogger(UserSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        //update the table view and the username combo boxes
        updateUserTableView();
        updateUsernameComboBoxes();
            
        //reset the input fields
        addUsernameInput.setText("");
        addPasswordInput.setText("");
    }//end addUserToDatabase(){}
    
    //modify a user in the database
    public void modifyUserInDatabase(){
        //get the input
        String username = usernameModifyComboBox.getValue();
        String newPassword = modifyPasswordInput.getText();
        String newRole = roleModifyComboBox.getValue();
        
        //create a new user object
        User modifyUser =  new User(username, newPassword, newRole);
        
        //create a new data access object and update the database
        UserModel modifyUserDAO = new UserModel(modifyUser);
        
        try {
            modifyUserDAO.updateTable();
        } catch (SQLException ex) {
            Logger.getLogger(UserSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //update the table view
        updateUserTableView();
        
        //clear the selection field
        usernameModifyComboBox.getSelectionModel().clearSelection();
    }//end modifyUserInDatabase() {}
    
    //remove a user in the database
    public void removeUserInDatabase(){
        //create the user object
        User removeUser = new User(userRemoveComboBox.getValue());
        
        //pass the user object to the user data access object and update the database
        UserModel removeUserDAO = new UserModel(removeUser);
        try {
            removeUserDAO.deleteFromTable();
        } catch (SQLException ex) {
            Logger.getLogger(UserSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //update the tableview and the username comboboxes
        updateUserTableView();
        updateUsernameComboBoxes();
        
        //clear the selection
        userRemoveComboBox.getSelectionModel().clearSelection();
    }//end removeUserInDatabase(){}
    
}
