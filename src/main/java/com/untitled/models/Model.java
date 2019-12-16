/*
a data access object template
*/
package main.java.com.untitled.models;

import java.sql.SQLException;
import javafx.collections.ObservableList;

public interface Model {
    public final String JDBC_URL = "jdbc:derby:Database;create=true"; //specifies the database URL
    
    
    public void insertIntoTable() throws SQLException; //add to the database
    public void deleteFromTable() throws SQLException; //remove from the database
    public void updateTable() throws SQLException; //update the database
    
}
