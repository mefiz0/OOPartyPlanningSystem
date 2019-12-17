package main.java.com.untitled.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class TasksController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane tasksPane;

    @FXML
    private TableView<?> tasksTable;

    @FXML
    private TableColumn<?, ?> rowNumColumn;

    @FXML
    private TableColumn<?, ?> customerNameColumn;

    @FXML
    private TableColumn<?, ?> partyTypeColumn;

    @FXML
    private TableColumn<?, ?> partyIdentifierColumn;

    @FXML
    private TableColumn<?, ?> taskOneColumn;

    @FXML
    private TableColumn<?, ?> taskOneProgressColumn;

    @FXML
    private TableColumn<?, ?> taskTwoColumn;

    @FXML
    private TableColumn<?, ?> taskTwoProgressColumn;

    @FXML
    private TableColumn<?, ?> taskThreeColumn;

    @FXML
    private TableColumn<?, ?> taskThreeProgressColumn;

    @FXML
    private TableColumn<?, ?> taskFourColumn;

    @FXML
    private TableColumn<?, ?> taskFourProgressColumn;

    @FXML
    private JFXComboBox<?> partySelect1;

    @FXML
    private JFXComboBox<?> partySelect;

    @FXML
    private JFXTextField updateTaskOneProgress;

    @FXML
    private JFXTextField updateTaskTwoProgress;

    @FXML
    private JFXTextField updateTaskThreeProgress;

    @FXML
    private JFXTextField updateTaskFourProgress;

    @FXML
    private JFXButton updateProgressButton;

    @FXML
    void initialize() {
       
    }
}
