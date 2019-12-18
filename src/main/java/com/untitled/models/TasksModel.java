
package main.java.com.untitled.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static main.java.com.untitled.models.Model.JDBC_URL;

public class TasksModel implements Model{
    //variable declarations
    private IntegerProperty rowNum;
    private StringProperty customerID;
    private StringProperty partyType;
    private StringProperty identifier;
    private StringProperty taskOne;
    private IntegerProperty taskOneProgress;
    private StringProperty taskTwo;
    private IntegerProperty taskTwoProgress;
    private StringProperty taskThree;
    private IntegerProperty taskThreeProgress;
    private StringProperty taskFour;
    private IntegerProperty taskFourProgress;
    
    //getters and setters
    //row number
    public IntegerProperty getRowNum() {
        return rowNum;
        
    }
    public void setRowNum(int rowNum) {
        this.rowNum = new SimpleIntegerProperty(rowNum);
    }
    
    //customer id
    public StringProperty getCustomerID() {
        return customerID;
    }
    public void setCustomerID(String customerID) {
        this.customerID = new SimpleStringProperty(customerID);
    }
    
    //party type
    public StringProperty getPartyType() {
        return partyType;
    }
    public void setPartyType(String partyType) {
        this.partyType = new SimpleStringProperty(partyType);
    }
    
    //identifier
    public StringProperty getIdentifier() {
        return identifier;
    }
    public void setIdentifier(String identifier) {
        this.identifier = new SimpleStringProperty(identifier);
    }
    
    //task one
    public StringProperty getTaskOne() {
        return taskOne;
    }
    public void setTaskOne(String taskOne) {
        this.taskOne = new SimpleStringProperty(taskOne);
    }
    
    //task one progress
    public IntegerProperty getTaskOneProgress() {
        return taskOneProgress;
    }
    public void setTaskOneProgress(int taskOneProgress) {
        this.taskOneProgress = new SimpleIntegerProperty(taskOneProgress);
    }

    //task two
    public StringProperty getTaskTwo() {
        return taskTwo;
    }
    public void setTaskTwo(String taskTwo) {
        this.taskTwo = new SimpleStringProperty(taskTwo);
    }
    
    //task two progress
    public IntegerProperty getTaskTwoProgress() {
        return taskTwoProgress;
    }
    public void setTaskTwoProgress(int taskTwoProgress) {
        this.taskTwoProgress = new SimpleIntegerProperty(taskTwoProgress);
    }
    
    //task three
    public StringProperty getTaskThree() {
        return taskThree;
    }
    public void setTaskThree(String taskThree) {
        this.taskThree = new SimpleStringProperty(taskThree);
    }
    
    //task three progress
    public IntegerProperty getTaskThreeProgress() {
        return taskThreeProgress;
    }
    public void setTaskThreeProgress(int taskThreeProgress) {
        this.taskThreeProgress = new SimpleIntegerProperty(taskThreeProgress);
    }
    
    //task four
    public StringProperty getTaskFour() {
        return taskFour;
    }
    public void setTaskFour(String taskFour) {
        this.taskFour = new SimpleStringProperty(taskFour);
    }
    
    //task four progress
    public IntegerProperty getTaskFourProgress() {
        return taskFourProgress;
    }
    public void setTaskFourProgress(int taskFourProgress) {    
        this.taskFourProgress = new SimpleIntegerProperty(taskFourProgress);
    }

    //constructor
    public TasksModel() {
        //empty
    }

    public TasksModel(String customerID, String identifier, int taskOneProgress, int taskTwoProgress, int taskThreeProgress, int taskFourProgress) {
        this.customerID = new SimpleStringProperty(customerID);
        this.identifier = new SimpleStringProperty(identifier);
        this.taskOneProgress = new SimpleIntegerProperty(taskOneProgress);
        this.taskTwoProgress = new SimpleIntegerProperty(taskTwoProgress);
        this.taskThreeProgress = new SimpleIntegerProperty(taskThreeProgress);
        this.taskFourProgress = new SimpleIntegerProperty(taskFourProgress);
    }
    
    
    
    @Override
    public void insertIntoTable() throws SQLException {
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //1 - first get the tasks from the parties list
        ArrayList taskList = getTasksFromPartyBasedOnType();
        
        System.out.println(taskList.get(0) + " " + taskList.get(1));
        System.out.println(taskList.get(2) + " " + taskList.get(3));
        
        //2 - define the statement
        String addToTasks = "INSERT INTO tasks "
                          + "(TaskOne, TaskTwo, TaskThree, TaskFour , "
                          + "TaskOneProgress, TaskTwoProgress, TaskThreeProgress, TaskFourProgress) "
                          + "VALUES ("
                          + "'" + taskList.get(0) + "', "
                          + "'" + taskList.get(1) + "', "
                          + "'" + taskList.get(2) + "', "
                          + "'" + taskList.get(3) + "', "
                          + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ")"; //all progress is 0 at start
        //3 - prepare the statement
        PreparedStatement ps = connection.prepareStatement(addToTasks);
        
        ps.execute(); //exceute the statement
        
        //close the statement and the connection
        ps.close();
        connection.close();
    }//end insertIntoTable()
    
    private ArrayList getTasksFromPartyBasedOnType() throws SQLException{
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //1 - first get the tasks from the parties list
        //define the statement
        String getTypesFromParties = "SELECT TaskOne, TaskTwo, TaskThree, TaskFour FROM parties"
                                   + " WHERE Type = '" + this.partyType.get() + "'";
        //prepare the statement and get the result set
        PreparedStatement ps = connection.prepareStatement(getTypesFromParties);
        ResultSet rs = ps.executeQuery();
        
        //fill the data from the result set
        String taskOne = null;
        String taskTwo = null;
        String taskThree = null;
        String taskFour = null;
        
        //loop through the result set and set the data
        while(rs.next()){
            taskOne = rs.getString("TaskOne");
            taskTwo = rs.getString("TaskTwo");
            taskThree = rs.getString("TaskThree");
            taskFour = rs.getString("TaskFour");
        }
        
        //add the items to an array list
        ArrayList<String> taskList = new ArrayList<String>();
        
        taskList.add(taskOne);
        taskList.add(taskTwo);
        taskList.add(taskThree);
        taskList.add(taskFour);
        
        //close the statement connection and the result set
        ps.close();
        rs.close();
        connection.close();
        
        return taskList;
    } //end getTasksFromPartyBasedOnType()
    
    @Override
    public void deleteFromTable() throws SQLException {
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //1 - get the task id
        int taskId = getTaskID();
        
        //2 - delete the record
        PreparedStatement ps = connection.prepareStatement("DELETE tasks WHERE TaskID = " + taskId);
        ps.execute();
        
        //close the statement and the connection
        ps.close();
        connection.close();
    }//end deleteFromTable()

    @Override
    public void updateTable() throws SQLException {
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //1 - get the task id
        int taskId = getTaskID();
        
        ///2 - update the table
        //check if the progress is 100%
        int progress = this.taskOneProgress.get() + this.taskTwoProgress.get() + this.taskThreeProgress.get() + this.taskFourProgress.get();
        
        if(progress != 400){
            String updateTasks = "UPDATE tasks SET "
                           + "TaskOneProgress = " + this.taskOneProgress.get() + ", "
                           + "TaskTwoProgress = " + this.taskTwoProgress.get() + ", "
                           + "TaskThreeProgress = " + this.taskThreeProgress.get() + ", "
                           + "TaskFourProgress = " + this.taskFourProgress.get() + ", "
                           + "Status = '-' "
                           + "WHERE TaskID = " + taskId;

            //prepare and execute the statement
            PreparedStatement ps = connection.prepareStatement(updateTasks);
            ps.execute();

            //close thee statement and the connection
            ps.close();
            connection.close();
            
        }else if(progress == 400){
            String updateTasks = "UPDATE tasks SET "
                           + "TaskOneProgress = 100, "
                           + "TaskTwoProgress = 100, "
                           + "TaskThreeProgress = 100, "
                           + "TaskFourProgress = 100, "
                           + "Status = 'Complete'"
                           + "WHERE TaskID = " + taskId;
        
        //prepare and execute the statement
        PreparedStatement ps = connection.prepareStatement(updateTasks);
        ps.execute();
        
        //close thee statement and the connection
        ps.close();
        connection.close();
        }
    }//end updateTable()
    
    //get the task id
    public int getTaskID() throws SQLException{
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //1 - get the task id
        //define the statement
        
        //insert an additional ' in the identifier to escape the ' character
        //get into a string
        String identifier = this.identifier.get();
        //get the substrings and inset a ' after the ' to escape it in sql
        identifier = identifier.substring(0, identifier.indexOf("'")) + "'" + identifier.substring(identifier.indexOf("'"));
        
        String getTaskID = "SELECT tasks.TaskID, "
                         + "sold.Identifier "
                         + "FROM purchases "
                         + "LEFT JOIN tasks "
                         + "ON purchases.TaskID = tasks.TaskID "
                         + "LEFT JOIN sold "
                         + "ON purchases.SoldID = sold.SoldID "
                         + "WHERE sold.Identifier = '" + identifier + "'";
        
        //prepare and execute the statement. get the task id from the result  set
        PreparedStatement ps = connection.prepareStatement(getTaskID);
        ResultSet rs = ps.executeQuery();
        
        int taskId = 0;
        
        //loop through the result set and the get the task id
        while(rs.next()){
            taskId = rs.getInt("TaskID");
            System.out.println(taskId);
        }
        
        //close the statement connection and the result set
        ps.close();
        rs.close();
        connection.close();
        
        return taskId;
    }//end getTaskID()
    
    //get the database records and store them in an observable list for tableview generation
    public ObservableList<TasksModel> getTasksRecords() throws SQLException{
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //define the statement
        String selectStatement = "SELECT purchases.CustomerID, "
                               + "purchases.SoldID, "
                               + "purchases.TaskID, "
                               + "customers.ID, "
                               + "sold.Identifier, "
                               + "sold.PartyType, "
                               + "tasks.TaskOne, "
                               + "tasks.TaskOneProgress, "
                               + "tasks.TaskTwo, "
                               + "tasks.TaskTwoProgress, "
                               + "tasks.TaskThree, "
                               + "tasks.TaskThreeProgress, "
                               + "tasks.TaskFour, "
                               + "tasks.TaskFourProgress "
                               + "FROM purchases "
                               + "LEFT JOIN customers "
                               + "ON purchases.CustomerID = customers.CustomerID "
                               + "LEFT JOIN sold "
                               + "ON purchases.SoldID = sold.SoldID "
                               + "LEFT JOIN tasks "
                               + "ON purchases.TaskID = tasks.TaskID";
        
        //prepare the statment
        PreparedStatement ps = connection.prepareStatement(selectStatement);
        
        ResultSet rs = ps.executeQuery(); //get the result set
        
        ObservableList<TasksModel> tasksList = getTasksObjects(rs);  //get the user objects
        
        return tasksList;
    }
    
    //generate a observable list which is used to generate a table view
    private ObservableList<TasksModel> getTasksObjects(ResultSet rs) throws SQLException{
        ObservableList<TasksModel> tasksList = FXCollections.observableArrayList(); //create an observable array list
        
        int rowCounter = 1; //counts the row for table view
        
        while(rs.next()){
            TasksModel tasks = new TasksModel();
            tasks.setRowNum(rowCounter);
            tasks.setCustomerID(rs.getString("ID"));
            tasks.setIdentifier(rs.getString("Identifier"));
            tasks.setPartyType(rs.getString("PartyType"));
            tasks.setTaskOne(rs.getString("TaskOne"));
            tasks.setTaskOneProgress(rs.getShort("TaskOneProgress"));
            tasks.setTaskTwo(rs.getString("TaskTwo"));
            tasks.setTaskTwoProgress(rs.getShort("TaskTwoProgress"));
            tasks.setTaskThree(rs.getString("TaskThree"));
            tasks.setTaskThreeProgress(rs.getShort("TaskThreeProgress"));
            tasks.setTaskFour(rs.getString("TaskFour"));
            tasks.setTaskFourProgress(rs.getShort("TaskFourProgress"));
                
            tasksList.add(tasks);
        }
        
        return tasksList;
    }
    
    //get a list of customers
    public ObservableList<String> getCustomers() throws SQLException{
        ObservableList<String> customersList = FXCollections.observableArrayList(); //create an observable array list
        
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //prepare the select statement
        String customerSelect = "SELECT customers.CustomerID, "
                              + "customers.ID, "
                              + "tasks.TaskID, "
                              + "tasks.Status "
                              + "FROM purchases "
                              + "LEFT JOIN customers "
                              + "ON purchases.CustomerID = customers.CustomerID "
                              + "LEFT JOIN tasks "
                              + "ON purchases.TaskID = tasks.TaskID "
                              + "WHERE tasks.Status IS NULL "
                              + "OR tasks.Status = '-'";
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(customerSelect);
        
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()){
            System.out.println(rs.getString("ID"));
            System.out.println(rs.getString("Status"));
            System.out.println("THIS WORKS!");
            
            customersList.add(rs.getString("ID"));
        }
        
        return customersList;
    }
    
    //get a list of identifiers
    public ObservableList<String> getIdentifiers(String customerID) throws SQLException{
        ObservableList<String> identifiersList = FXCollections.observableArrayList(); //create an observable array list
        
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //prepare the select statement
        String getIdentifier = "SELECT customers.ID, "
                              + "tasks.Status, "
                              + "sold.Identifier "
                              + "FROM purchases "
                              + "LEFT JOIN customers "
                              + "ON purchases.CustomerID = customers.CustomerID "
                              + "LEFT JOIN tasks "
                              + "ON purchases.TaskID = tasks.TaskID "
                              + "LEFT JOIN sold "
                              + "ON purchases.SoldID = sold.SoldID "
                              + "WHERE customers.ID = '" + customerID + "'";
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(getIdentifier);
        
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()){
            identifiersList.add(rs.getString("Identifier"));
        }
        
        return identifiersList;
    }
    
     //get customer names at id
    public String getCustomerNames(String customerID) throws SQLException{
        String customerName = null;
        
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //prepare the select statement
        String getIdentifier = "SELECT customers.ID, "
                              + "customers.Name "
                              + "FROM purchases "
                              + "LEFT JOIN customers "
                              + "ON purchases.CustomerID = customers.CustomerID "
                              + "WHERE customers.ID = '" + customerID + "'";
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(getIdentifier);
        
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()){
            customerName = rs.getString("Name");
        }
        
        return customerName;
    }
}
