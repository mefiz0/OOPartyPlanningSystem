/*
Data access object for payments
 */
package main.java.com.untitled.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public class PaymentsDAO implements DAO{
    //variable declarations
    private StringProperty customerName;
    private StringProperty partyType;
    private ObjectProperty<BigDecimal> amountToBePaid;
    
    //getters and setters
    //customer name
    public StringProperty getCustomerName() {
        return customerName;
    }
    public void setCustomerName(StringProperty customerName) {
        this.customerName = customerName;
    }
    
    //party type
    public StringProperty getPartyType() {
        return partyType;
    }
    public void setPartyType(StringProperty partyType) {
        this.partyType = partyType;
    }
    
    //amount to be paid
    public ObjectProperty<BigDecimal> getAmountToBePaid() {
        return amountToBePaid;
    }
    public void setAmountToBePaid(ObjectProperty<BigDecimal> amountToBePaid) {
        this.amountToBePaid = amountToBePaid;
    }
    
    //constructors
    public PaymentsDAO(StringProperty customerName, StringProperty partyType, ObjectProperty<BigDecimal> amountToBePaid) {
        this.customerName = customerName;
        this.partyType = partyType;
        this.amountToBePaid = amountToBePaid;
    }
    
    public PaymentsDAO(){
        //empty
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
