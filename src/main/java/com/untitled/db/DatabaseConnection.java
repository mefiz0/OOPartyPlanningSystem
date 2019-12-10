/*
    Initialises the database
 */
package main.java.com.untitled.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    
    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver"; //specifies the driver
    private static final String JDBC_URL = "jdbc:derby:Database;create=true"; //specifies the database URL

    /*
    This functions checks if a given table exists by looking at the database metadata,
    and creates if a table is missing. This allows the application to start from scratch
    in the event the database gets deleted.
    */
    private static void checkTable(String tableName, DatabaseMetaData dbmd, Statement stmt,
                              String createTable) throws SQLException{
        //gets the result set from the metadata
        ResultSet rs = dbmd.getTables(null, null, tableName.toUpperCase() , null);
        //checks the metadata
        if(rs.next()){
            System.out.println("Table " + tableName + " already exists.");
        }else{

            System.out.println("Table " + tableName + " does not exist");
            stmt.execute(createTable); //create the users table
            System.out.println(tableName + " Table Created");
            if(tableName == "users"){
                stmt.executeUpdate(DatabaseTables.INSERT_ADMIN_TO_USERS_TABLE_SQL);
                System.out.println("Success!");
            }
        }
    }//end checkTable

    //initializes the db with the tables
    public static void initDB() throws ClassNotFoundException, SQLException{
        //establish a connection, this will also create a db if there is none
        Class.forName(DRIVER);
        Connection connection = DriverManager.getConnection(JDBC_URL);
        System.out.println("Connected to DB Successfully");

        Statement initTables = connection.createStatement(); //creates a statment object

        //check if the tables are created
        DatabaseMetaData dbmd = connection.getMetaData(); //gets the database metadata
        //gets the result set for the specific tables
        //check the users table
        checkTable("users", dbmd, initTables, DatabaseTables.CREATE_USERS_TABLE_SQL);

        //check user access history table
        checkTable("access_history", dbmd, initTables, DatabaseTables.CREATE_USERS_ACCESS_HISTORY_TABLE_SQL);

        //check customers table
        checkTable("customers", dbmd, initTables, DatabaseTables.CREATE_CUSTOMERS_TABLE_SQL);

        //check addons table
        checkTable("addons", dbmd, initTables, DatabaseTables.CREATE_ADDONS_TABLE_SQL);

        //check caterers table
        checkTable("caterers", dbmd, initTables, DatabaseTables.CREATE_CATERERS_TABLE_SQL);

        //check venues table
        checkTable("venues", dbmd, initTables, DatabaseTables.CREATE_VENUES_TABLE_SQL);

        //check parties table
        checkTable("parties", dbmd, initTables, DatabaseTables.CREATE_PARTIES_TABLE_SQL);
        
        //check sold table
        checkTable("sold", dbmd, initTables, DatabaseTables.CREATE_SOLD_TABLE_SQL);

        //check purchases table
        checkTable("purchases", dbmd, initTables, DatabaseTables.CREATE_PURCHASES_TABLE_SQL);

        //check payments table
        checkTable("payments", dbmd, initTables, DatabaseTables.CREATE_PAYMENTS_TABLE_SQL);
        
        initTables.close();
        connection.close();
    }//end initDB
}
