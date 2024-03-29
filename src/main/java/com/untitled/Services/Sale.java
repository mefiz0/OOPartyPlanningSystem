/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.untitled.Services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import main.java.com.untitled.models.AddonsModel;
import main.java.com.untitled.models.CatererModel;
import main.java.com.untitled.models.PartyModel;
import main.java.com.untitled.models.VenuesModel;

public class Sale extends PartyBase{
    
    //define the value of gst as 6% = 0.06
    private final BigDecimal GST = BigDecimal.valueOf(0.06).setScale(2, RoundingMode.HALF_UP);
    
    //define variable
    private String venue;
    private Date dueDate;
    private Time dueTime;
    private String addonOne;
    private String addonTwo;
    private String addonThree;
    private String caterer;
    private BigDecimal totalPrice;
    private BigDecimal amountPaid;
    
    //getters and setters
    //venue
    public String getVenue() {
        return venue;
    }
    public void setVenue(String venue) {
        this.venue = venue;
    }
    
    //date
    public Date getDueDate() {
        return dueDate;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    
    //time
    public Time getDueTime() {
        return dueTime;
    }
    public void setDueTime(Time dueTime) {
        this.dueTime = dueTime;
    }
    
    //addonOne
    public String getAddonOne() {
        return addonOne;
    }
    public void setAddonOne(String addonOne) {
        this.addonOne = addonOne;
    }
    
    //addonTwo
    public String getAddonTwo() {
        return addonTwo;
    }
    public void setAddonTwo(String addonTwo) {
        this.addonTwo = addonTwo;
    }
    
    //addonThree
    public String getAddonThree() {
        return addonThree;
    }
    public void setAddonThree(String addonThree) {
        this.addonThree = addonThree;
    }
    
    //caterer
    public String getCaterer() {
        return caterer;
    }
    public void setCaterer(String caterer) {
        this.caterer = caterer;
    }
    
    //total price
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    //amount paid
    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }
    
    //constructors
    public Sale(String venue, Date dueDate, Time dueTime, String addonOne, String addonTwo, String addonThree, String caterer, BigDecimal totalPrice, BigDecimal amountPaid, String type, int price) {
        super(type, price);
        this.venue = venue;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.addonOne = addonOne;
        this.addonTwo = addonTwo;
        this.addonThree = addonThree;
        this.caterer = caterer;
        this.totalPrice = totalPrice;
        this.amountPaid = amountPaid;
    }

    public Sale(String venue, Date dueDate, Time dueTime, String addonOne, String addonTwo, String addonThree, String caterer, BigDecimal amountPaid, BigDecimal totalPrice, String type) {
        super(type);
        this.venue = venue;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.addonOne = addonOne;
        this.addonTwo = addonTwo;
        this.addonThree = addonThree;
        this.caterer = caterer;
        this.amountPaid = amountPaid;
        this.totalPrice = totalPrice;
    }
    
    public Sale(String venue, Date dueDate, Time dueTime, String addonOne, String addonTwo, String addonThree, String caterer, BigDecimal amountPaid, String type) {
        super(type);
        this.venue = venue;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.addonOne = addonOne;
        this.addonTwo = addonTwo;
        this.addonThree = addonThree;
        this.caterer = caterer;
    }

    public Sale(String venue, Date dueDate, Time dueTime, String addonOne, String addonTwo, String addonThree, String caterer, BigDecimal totalPrice, BigDecimal amountPaid) {
        this.venue = venue;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.addonOne = addonOne;
        this.addonTwo = addonTwo;
        this.addonThree = addonThree;
        this.caterer = caterer;
        this.totalPrice = totalPrice;
        this.amountPaid = amountPaid;
    }
    
    public Sale(){
        
    }
    
    
    //calculate the subtotal
    public int calculateSubtotal() throws SQLException{
        //create the data access objects
        PartyModel partyDAO = new PartyModel();
        VenuesModel venuesDAO = new VenuesModel();
        AddonsModel addonsDAO = new AddonsModel();
        CatererModel catererDAO = new CatererModel();
        
        //get the prices
        int partyPrice = partyDAO.getPriceOfPartyBasedOnType(this.getType());
        System.out.println("party price = " + partyPrice);
        int venuePrice = venuesDAO.getPriceOfVenue(this.venue);
        System.out.println("venue price = " + venuePrice);
        int addonsOnePrice = addonsDAO.getPriceBasedOnAddon(this.addonOne);
        System.out.println("addonsOnePrice = " + addonsOnePrice);
        int addonsTwoPrice = addonsDAO.getPriceBasedOnAddon(this.addonTwo);
        System.out.println("addonsTwoPrice = " + addonsTwoPrice);
        int addonsThreePrice = addonsDAO.getPriceBasedOnAddon(this.addonThree);
        System.out.println("addonsThreePrice = " + addonsThreePrice);
        int catererPrice = catererDAO.getPriceBasedOnCaterer(this.caterer);
        System.out.println("catererPrice = " + catererPrice);
        
        //return the sub total
        return (partyPrice + venuePrice + addonsOnePrice + addonsTwoPrice + addonsThreePrice + catererPrice);
    }//end calculateSubtotal()
    
    //calculate the total price with gst
    public void calculateTotalPrice(int subTotal){
        //convert subtotal to a big decimal
        BigDecimal subTotalBigDecimal = BigDecimal.valueOf(subTotal);
        
        //get the gst value
        BigDecimal gstOfSubTotal = subTotalBigDecimal.multiply(GST).setScale(2, RoundingMode.HALF_UP);
        
        //calculate the total price, which is total price = subtotal + (subtotal*gst), rounded to 2 decimal places
        this.setTotalPrice((gstOfSubTotal.add(subTotalBigDecimal)).setScale(2, RoundingMode.HALF_UP));
    }
}
