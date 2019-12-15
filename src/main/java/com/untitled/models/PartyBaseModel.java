
package main.java.com.untitled.models;

import java.sql.SQLException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PartyBaseModel implements Model{
    //variable declarations
    private StringProperty type;
    private IntegerProperty price;
    
    //getters and setters
    
    //string
    public StringProperty getType() {
        return type;
    }
    public void setType(String type) {
        this.type = new SimpleStringProperty(type);
    }
    
    //price
    public IntegerProperty getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = new SimpleIntegerProperty(price);
    }
    
    //constructor

    public PartyBaseModel() {
    }
    
    
    @Override
    public void insertIntoTable() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteFromTable() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateTable() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
