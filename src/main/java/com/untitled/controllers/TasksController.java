package main.java.com.untitled.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import main.java.com.untitled.models.TasksModel;

public class TasksController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane tasksPane;

    @FXML
    private TableView tasksTable;

    @FXML
    private TableColumn<TasksModel, Integer> rowNumColumn;

    @FXML
    private TableColumn<TasksModel, String> customerIDColumn;

    @FXML
    private TableColumn<TasksModel, String> partyTypeColumn;

    @FXML
    private TableColumn<TasksModel, String> partyIdentifierColumn;

    @FXML
    private TableColumn<TasksModel, String> taskOneColumn;

    @FXML
    private TableColumn<TasksModel, Integer> taskOneProgressColumn;

    @FXML
    private TableColumn<TasksModel, String> taskTwoColumn;

    @FXML
    private TableColumn<TasksModel, Integer> taskTwoProgressColumn;

    @FXML
    private TableColumn<TasksModel, String> taskThreeColumn;

    @FXML
    private TableColumn<TasksModel, Integer> taskThreeProgressColumn;

    @FXML
    private TableColumn<TasksModel, String> taskFourColumn;

    @FXML
    private TableColumn<TasksModel, Integer> taskFourProgressColumn;

    @FXML
    private JFXComboBox<String> customerIDSelect;

    @FXML
    private JFXComboBox<String> partySelect;

    @FXML
    private JFXTextField updateTaskOneProgress;

    @FXML
    private JFXTextField updateTaskTwoProgress;

    @FXML
    private JFXTextField updateTaskThreeProgress;

    @FXML
    private JFXTextField updateTaskFourProgress;

    @FXML
    private Label customerNameLabel;

    @FXML
    private JFXButton updateProgressButton;

    @FXML
    void initialize() {
        updateTableView();
        
        //add data to the combo boxes
        //add data to the customer id box
        customerIDSelect.setOnShowing((event) -> {
            //create a new tasks model
            TasksModel tasks = new TasksModel();
            
            //get the data into the combo box
            try {
                //get the data
                ObservableList customersList = tasks.getCustomers();
                customerIDSelect.setItems(customersList);
            } catch (SQLException ex) {
                Logger.getLogger(TasksController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        //when a customer id is selected, show the customer name and set the data in the identifiers box
        customerIDSelect.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            //create a new tasks model
            TasksModel tasks = new TasksModel();
            
            try {
                //set the items in the party select box
                ObservableList identifiersList = tasks.getIdentifiers(newValue);
                partySelect.setItems(identifiersList);
                
                //set the customer name
                customerNameLabel.setText(tasks.getCustomerNames(newValue));
                customerNameLabel.setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(TasksController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        /*
        Regex pattern matching to make the progress boxes numbers only
        */
        //task one progress
        updateTaskOneProgress.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try{
                    if(!newValue.matches("\\d{0,3}?") || (Integer.parseInt(newValue) > 100)) {
                        Platform.runLater(() -> {
                            updateTaskOneProgress.setText("");
                        });
                    }
                } catch(Exception e){
                    System.out.println("A string was entered that couldn't be parsed! moving on");
                }//end try catch
            }
            
        });//end updateTaskOneProgress.textProperty().addListener(new ChangeListener<String>()
        
        //task two
        updateTaskTwoProgress.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try{
                    if(!newValue.matches("\\d{0,3}?") || (Integer.parseInt(newValue) > 100)) {
                        Platform.runLater(() -> {
                            updateTaskOneProgress.setText("");
                        });
                    }
                } catch(Exception e){
                    System.out.println("A string was entered that couldn't be parsed! moving on");
                }//end try catch
            }
            
        });//end updateTaskTwoProgress.textProperty().addListener(new ChangeListener<String>()
        
        //task two
        updateTaskThreeProgress.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try{
                    if(!newValue.matches("\\d{0,3}?") || (Integer.parseInt(newValue) > 100)) {
                        Platform.runLater(() -> {
                            updateTaskOneProgress.setText("");
                        });
                    }
                } catch(Exception e){
                    System.out.println("A string was entered that couldn't be parsed! moving on");
                }//end try catch
            }
            
        });//end updateTaskThreeProgress.textProperty().addListener(new ChangeListener<String>()
        
        //task four
        //task two
        updateTaskFourProgress.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try{
                    if(!newValue.matches("\\d{0,3}?") || (Integer.parseInt(newValue) > 100)) {
                        Platform.runLater(() -> {
                            updateTaskOneProgress.setText("");
                        });
                    }
                } catch(Exception e){
                    System.out.println("A string was entered that couldn't be parsed! moving on");
                }//end try catch
            }
            
        });//end updateTaskFourProgress.textProperty().addListener(new ChangeListener<String>()
        
        //when the updateProgressButton is pressed
        updateProgressButton.setOnAction((event) -> {
            updateDatabase(); //update the database
        }); //end updateProgressButton.setOnAction
    }
    
    //update the table view
    public void updateTableView(){
        //createa a new tasksModel
        TasksModel tasksModel = new TasksModel();
        
        //set the rows
        rowNumColumn.setCellValueFactory(cellData -> cellData.getValue().getRowNum().asObject());
        customerIDColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomerID());
        partyTypeColumn.setCellValueFactory(cellData -> cellData.getValue().getPartyType());
        partyIdentifierColumn.setCellValueFactory(cellData -> cellData.getValue().getIdentifier());
        taskOneColumn.setCellValueFactory(cellData -> cellData.getValue().getTaskOne());
        taskOneProgressColumn.setCellValueFactory(cellData -> cellData.getValue().getTaskOneProgress().asObject());
        taskTwoColumn.setCellValueFactory(cellData -> cellData.getValue().getTaskTwo());
        taskTwoProgressColumn.setCellValueFactory(cellData -> cellData.getValue().getTaskTwoProgress().asObject());
        taskThreeColumn.setCellValueFactory(cellData -> cellData.getValue().getTaskThree());
        taskThreeProgressColumn.setCellValueFactory(cellData -> cellData.getValue().getTaskThreeProgress().asObject());
        taskFourColumn.setCellValueFactory(cellData -> cellData.getValue().getTaskFour());
        taskFourProgressColumn.setCellValueFactory(cellData -> cellData.getValue().getTaskFourProgress().asObject());
        
        try {
            tasksTable.setItems(tasksModel.getTasksRecords());
        } catch (SQLException ex) {
            Logger.getLogger(TasksController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//end updateTableView()
    
    //update users table
    public void updateDatabase(){
        //get the input
        String customerID = customerIDSelect.getValue();
        String party = partySelect.getValue();
        int progressTaskOne = Integer.parseInt(updateTaskOneProgress.getText());
        int progressTaskTwo = Integer.parseInt(updateTaskTwoProgress.getText());
        int progressTaskThree = Integer.parseInt(updateTaskThreeProgress.getText());
        int progressTaskFour = Integer.parseInt(updateTaskFourProgress.getText());
        
        //create a new tasks model object
        TasksModel tasks = new TasksModel(customerID, party, progressTaskOne, progressTaskTwo,
                                          progressTaskThree, progressTaskFour);
        
        try {
            //update the table
            tasks.updateTable();
        } catch (SQLException ex) {
            Logger.getLogger(TasksController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //update the table
        updateTableView();
        
        //reset the data inputs
        customerIDSelect.getSelectionModel().clearSelection();
        partySelect.getSelectionModel().clearSelection();
        updateTaskOneProgress.setText("");
        updateTaskTwoProgress.setText("");
        updateTaskThreeProgress.setText("");
        updateTaskFourProgress.setText("");
        customerNameLabel.setVisible(false);
    }
}
