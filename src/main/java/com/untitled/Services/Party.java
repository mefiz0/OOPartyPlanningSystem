
package main.java.com.untitled.Services;

public class Party extends PartyBase{
    
    private String taskOne;
    private String taskTwo;
    private String taskThree;
    private String taskFour;
    
    //getters and setters
    //task one
    public String getTaskOne() {
        return taskOne;
    }
    public void setTaskOne(String taskOne) {
        this.taskOne = taskOne;
    }

    //task two
    public String getTaskTwo() {
        return taskTwo;
    }
    public void setTaskTwo(String taskTwo) {
        this.taskTwo = taskTwo;
    }

    //task three
    public String getTaskThree() {
        return taskThree;
    }
    public void setTaskThree(String taskThree) {
        this.taskThree = taskThree;
    }
    
    //task four
    public String getTaskFour() {
        return taskFour;
    }
    public void setTaskFour(String taskFour) {
        this.taskFour = taskFour;
    }
    
    //end getters and setters
    
    //constructors
    public Party(String type, int price, String taskOne, String taskTwo, String taskThree, String taskFour) {
        super(type, price);
        this.taskOne = taskOne;
        this.taskTwo = taskTwo;
        this.taskThree = taskThree;
        this.taskFour = taskFour;
    }

    public Party(String type) {
        super(type);
        this.taskOne = null;
        this.taskTwo = null;
        this.taskThree = null;
        this.taskFour = null;
    }

    public Party() {
        //empty
    }
    
    
    
}
