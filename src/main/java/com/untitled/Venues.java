/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.untitled;

/**
 *
 * @author sath
 */
public class Venues {
    
    private String venueName;
    private String road;
    private String Building;
    private int capacity;
    private int price;
    
    //setters and getters
    //venue name
    public String getVenueName() {
        return venueName;
    }
    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }
    
    //road
    public String getRoad() {
        return road;
    }
    public void setRoad(String road) {
        this.road = road;
    }
    
    //building
    public String getBuilding() {
        return Building;
    }
    public void setBuilding(String Building) {
        this.Building = Building;
    }
    
    //capacity
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    //price
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    
    //end setters and getters
    
}
