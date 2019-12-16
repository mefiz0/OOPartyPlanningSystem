/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.untitled.Services;

/**
 *
 * @author sath
 */
public class Addon {
    
    private String addonType;
    private int addonPrice;
    
    //getters and setters
    //addon type
    public String getAddonType() {
        return addonType;
    }
    public void setAddonType(String addonType) {
        this.addonType = addonType;
    }
    
    //add on price
    public int getAddonPrice() {
        return addonPrice;
    }
    public void setAddonPrice(int addonPrice) {
        this.addonPrice = addonPrice;
    }
    
    //end setters and getters
    
    //constructors
    //fully parametrized constructor
    public Addon(String type, int price){
        this.addonType = type;
        this.addonPrice = price;
    }
    
    //only the type
    public Addon(String type){
        this.addonType = type;
        this.addonPrice = 0;
    }
    
    //empty constructor
    public Addon(){
        //empty
    }
}
