package main.java.com.untitled.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import main.java.com.untitled.dao.UserDAO;

public class UserSettingsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView usersTable;

    @FXML
    private TableColumn<UserDAO, String> usernameColumn;

    @FXML
    private TableColumn<UserDAO, String> passwordColumn;

    @FXML
    private TableColumn<UserDAO, String> roleColumn;

    @FXML
    private JFXTextField addUsernameInput;

    @FXML
    private JFXPasswordField addPasswordInput;

    @FXML
    private JFXComboBox<?> addRoleInput;

    @FXML
    private JFXButton addUserButton;

    @FXML
    private JFXButton modifyUserButton;

    @FXML
    private JFXPasswordField modifyPasswordInput;

    @FXML
    private JFXComboBox<?> roleModifyComboBox;

    @FXML
    private JFXComboBox<?> usernameModifyComboBox;

    @FXML
    private JFXButton removeUserButton;

    @FXML
    private JFXComboBox<?> userRemoveComboBox;

    @FXML
    private TextField searchUserInput;

    @FXML
    private JFXButton searchUserButton;

    @FXML
    private TableView<?> accessHistoryTable;

    @FXML
    private TextField searchAccessInput;

    @FXML
    private JFXButton searchAccessButton;

    @FXML
    void initialize() throws SQLException {
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().getUsername());
        passwordColumn.setCellValueFactory(cellData -> cellData.getValue().getPassword());
        roleColumn.setCellValueFactory(cellData -> cellData.getValue().getRole());
        
        UserDAO test = new UserDAO();
        
        ObservableList<UserDAO> userList = test.getAllDatabaseRecords();
        
        usersTable.setItems(userList);
    }
    
}
