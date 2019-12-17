/*
    This class defines SQL statements to create tables
 */
package main.java.com.untitled.db;

public class DatabaseTables {
    
    //DEFINES THE TABLES FOR THIS DATABASE
    
    /*
    CREATE USERS TABLE
    
    this table stores user names, passwords and roles
    */
    
    public static final String CREATE_USERS_TABLE_SQL = "create table users( "
                                                      + "UserID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                                                      + "Username VARCHAR(10) NOT NULL UNIQUE, "
                                                      + "Password VARCHAR(40) NOT NULL, "
                                                      + "Role VARCHAR(15) NOT NULL, "
                                                      + "PRIMARY KEY (UserID))";
    //insert admin details into the users table
    public static final String INSERT_ADMIN_TO_USERS_TABLE_SQL = "INSERT INTO users (Username, Password, Role) VALUES("
                                                                     + "'admin','admin123','Administrator')";
    /*
    CREATE USERS ACCESS HISTORY
    
    this table stores a record of the users that logged in and logged out
    */
    public static final String CREATE_USERS_ACCESS_HISTORY_TABLE_SQL = "CREATE TABLE access_history ("
                                                                     + "AccessID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                                                                     + "UserID INTEGER NOT NULL, "
                                                                     + "AccessTime TIMESTAMP, "
                                                                     + "LoggedOutTime TIMESTAMP, "
                                                                     + "PRIMARY KEY (AccessID), "
                                                                     + "FOREIGN KEY (UserID) REFERENCES users(UserID))";
    /*
    CREATE CUSTOMERS TABLE
    
    this table stores customer information
    */
    public static final String CREATE_CUSTOMERS_TABLE_SQL = "CREATE TABLE customers ( "
                                                          + "CustomerID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                                                          + "ID VARCHAR(60) NOT NULL UNIQUE,"
                                                          + "Name VARCHAR(60) NOT NULL,"
                                                          + "BankAccountNumber BIGINT NOT NULL,"
                                                          + "ContactNumber INTEGER,"
                                                          + "Email VARCHAR(60),"
                                                          + "PRIMARY KEY (CustomerID))";
    
    /*
    CREATE ADDONS TABLE
    
    this table stores addons information
    */
    public static final String CREATE_ADDONS_TABLE_SQL = "CREATE TABLE addons ( "
                                                       + "AddOnsID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                                                       + "Type VARCHAR(60) NOT NULL UNIQUE,"
                                                       + "Price INTEGER NOT NULL,"
                                                       + "PRIMARY KEY (AddOnsID))";
    /*
    CREATE CATERING TABLE
    
    this table stores caterer information
    */
    public static final String CREATE_CATERERS_TABLE_SQL = "CREATE TABLE caterers ( "
                                                         + "CatererID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                                                         + "Caterer VARCHAR(60) NOT NULL UNIQUE,"
                                                         + "Price INTEGER NOT NULL,"
                                                         + "PRIMARY KEY (CatererID))";
    
    /*
    CREATE VENUES TABLE
    
    This table stores venues information
    */
    public static final String CREATE_VENUES_TABLE_SQL = "CREATE TABLE venues ( "
                                                       + "VenueID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                                                       + "Venue VARCHAR(60) NOT NULL UNIQUE,"
                                                       + "Road VARCHAR(60) NOT NULL,"
                                                       + "Building VARCHAR(60) NOT NULL,"
                                                       + "Capacity INT NOT NULL,"
                                                       + "Price INT NOT NULL,"
                                                       + "PRIMARY KEY (VenueID))";
    /*
    CREATE PARTIES TABLE
    
    This table stores party information
    */
    public static final String CREATE_PARTIES_TABLE_SQL = "CREATE TABLE parties ( "
                                                        + "PartyID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                                                        + "Type VARCHAR(60) NOT NULL UNIQUE,"
                                                        + "Price INTEGER NOT NULL,"
                                                        + "TaskOne VARCHAR(60) NOT NULL,"
                                                        + "TaskTwo VARCHAR(60) NOT NULL,"
                                                        + "TaskThree VARCHAR(60) NOT NULL,"
                                                        + "TaskFour VARCHAR(60) NOT NULL,"
                                                        + "PRIMARY KEY (PartyID))";
    
    /*
    CREATE SOLD TABLE
    
    This table  contains information of parties that have been sold to customers
    Relevant information is taken from the other tables.
    Foregin Key is not used to prevent data loss in the even the referred row is deleted.
    */
    public static final String CREATE_SOLD_TABLE_SQL = "CREATE TABLE sold ( "
                                              + "SoldID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                                              + "Identifier VARCHAR(120) NOT NULL UNIQUE,"
                                              + "PartyType VARCHAR(60) NOT NULL,"
                                              + "Venue VARCHAR(60) NOT NULL,"
                                              + "Caterer VARCHAR(60) NOT NULL,"
                                              + "AddOnOne VARCHAR(60) NOT NULL,"
                                              + "AddOnTwo VARCHAR(60) NOT NULL,"
                                              + "AddOnThree VARCHAR(60) NOT NULL,"
                                              + "DueDate DATE NOT NULL,"
                                              + "DueTime TIME NOT NULL,"
                                              + "TotalPrice DECIMAL NOT NULL,"
                                              + "AmountPaid DECIMAL,"
                                              + "PRIMARY KEY (SoldID))";
    
    /*
    CREATE PAYMENTS TABLE
    
    This table contains information of all purchases made
    Here we are using foreign key relationships to refer to the customer and the sold table
    */
    public static final String CREATE_PURCHASES_TABLE_SQL = "CREATE TABLE purchases ( "
                                                         + "PurchaseID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                                                         + "CustomerID INTEGER NOT NULL,"
                                                         + "SoldID INTEGER NOT NULL,"
                                                         + "TaskID INTEGER NOT NULL,"
                                                         + "ToBePaid VARCHAR(3),"
                                                         + "PRIMARY KEY (PurchaseID),"
                                                         + "FOREIGN KEY (CustomerID) REFERENCES customers(CustomerID),"
                                                         + "FOREIGN KEY (SoldID) REFERENCES sold(SoldID),"
                                                         + "FOREIGN KEY (TaskID) REFERENCES tasks(TaskID))";
    
    /*
    CREATE TASKS TABLE
    
    this table is used for tasks management
    */
    public static final String CREATE_TASKS_TABLE_SQL = "CREATE TABLE tasks ( "
                                                      + "TaskID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                                                      + "TaskOne VARCHAR(60) NOT NULL,"
                                                      + "TaskTwo VARCHAR(60) NOT NULL,"
                                                      + "TaskThree VARCHAR(60) NOT NULL,"
                                                      + "TaskFour VARCHAR(60) NOT NULL,"
                                                      + "TaskOneProgress INTEGER,"
                                                      + "TaskTwoProgress INTEGER,"
                                                      + "TaskThreeProgress INTEGER,"
                                                      + "TaskFourProgress INTEGER,"
                                                      + "Status VARCHAR(10),"
                                                      + "PRIMARY KEY(TaskID))";
    }
