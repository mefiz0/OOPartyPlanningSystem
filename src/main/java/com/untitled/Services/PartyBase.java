/*
    This class contains definitions for a generic party type
 */
package main.java.com.untitled.Services;

/**
 *
 * @author sath
 */
public class PartyBase {
    
    private String type;
    private int price;
    
    //getters and setters
    
    //type
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
    //price
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    
    //end setters and getters
    
    //constructors
    public PartyBase(String type, int price) {
        this.type = type;
        this.price = price;
    }

    public PartyBase(String type) {
        this.type = type;
        this.price = 0;
    }

    public PartyBase() {
        //empty
    }
    
    
}
