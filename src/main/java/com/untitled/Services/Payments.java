
package main.java.com.untitled.Services;

import java.math.BigDecimal;

public class Payments {
    //variable declarations
    private String customerName;
    private String partyType;
    private BigDecimal amountToBePaid;
    
    //getters and setters
    //customer name
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    //party type
    public String getPartyType() {
        return partyType;
    }
    public void setPartyType(String partyType) {
        this.partyType = partyType;
    }
    
    //amount to be paid
    public BigDecimal getAmountToBePaid() {
        return amountToBePaid;
    }
    public void setAmountToBePaid(BigDecimal amountToBePaid) {
        this.amountToBePaid = amountToBePaid;
    }
    
    //constructor
    public Payments(String customerName, String partyType, String amountToBePaid) {
        this.customerName = customerName;
        this.partyType = partyType;
        this.amountToBePaid = new BigDecimal(amountToBePaid);
    }
    
    public Payments(){
        
    }
    
}
