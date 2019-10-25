/*
This class defines the SQL to create and modify tables
 */
package database;

public class DatabaseTablesSQL {
    /*
    The users table contains the system users details.
    User ID is automatically generated and a username and password length is defined
    The role defines user permissions and level of access
    */
    /*
    The users table contains the system users details.
    User ID is automatically generated and a username and password length is defined
    The role defines user permissions and level of access
    */
    public static final String CREATE_USERS_TABLE_SQL = "create table users ( "
                                                      + "UserID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                                                      + "Username VARCHAR(10) NOT NULL UNIQUE,"
                                                      + "Password VARCHAR(15) NOT NULL, "
                                                      + "Role VARCHAR(20) NOT NULL, "
                                                      + "PRIMARY KEY (UserID))";
    
    public static final String ADD_DEFAULT_VALUES_TO_USERS_TABLE_SQL = "INSERT INTO users (Username, Password, Role) VALUES("
                                                                     + "'admin','admin123','Administrator')";
    
    //this table contains the access history for each user
    public static final String CREATE_USERS_ACCESS_HISTORY_TABLE_SQL = "CREATE TABLE access_history ("
                                                                     + "AccessID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                                                                     + "UserID INTEGER NOT NULL,"
                                                                     + "AccessTime TIMESTAMP,"
                                                                     + "LogOutTime TIMESTAMP,"
                                                                     + "PRIMARY KEY (AccessID),"
                                                                     + "FOREIGN KEY (UserID) REFERENCES users(UserID))";
    
    //this table contains customer information
    public static final String CREATE_CUSTOMERS_TABLE_SQL = "CREATE TABLE customers("
                                                          + "CustomerID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                                                          + "Name VARCHAR(50) NOT NULL,"
                                                          + "IDNumber VARCHAR(50),"
                                                          + "BankAccountNumber INTEGER,"
                                                          + "PRIMARY KEY (CustomerID))";
    
    //this table contains the avaliable venues
    public static final String CREATE_VENUES_TABLE_SQL = "CREATE TABLE venues ("
                                                       + "VenueID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                                                       + "Venue VARCHAR(50) NOT NULL,"
                                                       + "Address VARCHAR(50) NOT NULL UNIQUE,"
                                                       + "Capacity INTEGER NOT NULL,"
                                                       + "Price DECIMAL,"
                                                       + "PRMIARY KEY (VenueID))";
    
    //this table contains the Add-ons
    public static final String CREATE_ADD_ONS_TABLE_SQL = "CREATE TABLE add_ons ("
                                                        + "AddonID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                                                        + "Type VARCHAR(40) NOT NULL,"
                                                        + "Price DECIMAL NOT NULL,"
                                                        + "PRIMARY KEY (AddonID))";
    
    //this table contains the party details
    public static final String CREATE_PARTY_DETAILS_TABLE_SQL = "CREATE TABLE party_details ("
                                                        + "PartyID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                                                        + "Type VARCHAR(50) NOT NULL,"
                                                        + "PRIMARY KEY (PartyID))";
    
    //this table contains the catering options
    public static final String CREATE_CATERING_OPTIONS_TABLE_SQL = "CREATE TABLE catering_options ("
                                                                 + "CateringID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                                                                 + "Name VARCHAR(50) NOT NULL,"
                                                                 + "Price DECIMAL NOT NULL,"
                                                                 + "PRIMARY KEY (CateringID))";
    
    //this table tracks customer sales
    public static final String CREATE_SALES_TABLE_SQL = "CREATE TABLE sales("
                                                      + "SaleID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                                                      + "PartyType VARCHAR(50) NOT NULL,"
                                                      + "Venue VARCHAR(50) NOT NULL,"
                                                      + "Address VARCHAR(50) NOT NULL,"
                                                      + "CateringOption VARCHAR(50) NOT NULL,"
                                                      + "Addon1 VARCHAR(50) NOT NULL,"
                                                      + "Addon2 VARCHAR(50) NOT NULL,"
                                                      + "Addon3 VARCHAR(50) NOT NULL,"
                                                      + "TotalPrice DECIMAL NOT NULL,"
                                                      + "PRIMARY KEY (SaleID))";
    
    //this table tracks payments made
    public static final String CREATE_PAYMENTS_TABLE = "CREATE TABLE payments("
                                                     + "PaymentID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                                                     + "SaleID INTEGER NOT NULL,"
                                                     + "CustomerID INTEGER NOT NULL,"
                                                     + "AMOUNT DUE DECIMAL,"
                                                     + "DUE DATE TIMESTAMP,"
                                                     + "AMOUNT PAID DECIMAL,"
                                                     + "DATE PAID DECIMAL,"
                                                     + "PRIMARY KEY (PaymentID),"
                                                     + "FOREIGN KEY (SaleID) REFERENCES sales (SaleID),"
                                                     + "FOREIGN KEY (CustomerID) REFERENCES customers(CustomerID))";
}
