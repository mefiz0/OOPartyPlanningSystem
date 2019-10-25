
package database;

import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connection {
    public final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver"; //specifies the driver
    public final String JDBC_URL = "jdbc:derby:PartyDatabase;create=true;shutdown=true"; //specifies the database URL
    
    //Establishes a database connection
    public java.sql.Connection DatabaseConnection() throws ClassNotFoundException, SQLException{
        Class.forName(DRIVER);
        java.sql.Connection connection = DriverManager.getConnection(JDBC_URL);
        System.out.println("Connected to DB Successfully");

        return connection;
    }//end connection
    
    public void checkTable(String tableName, DatabaseMetaData dbmd, Statement stmt,
                              String createTable) throws SQLException{
        //gets the result set from the metadata
        boolean checkStatus;
        ResultSet rs = dbmd.getTables(null, null, tableName.toUpperCase() , null);
        //checks the metadata
        if(rs.next()){
            System.out.println("Table " + tableName + " already exists.");
        }else{

            System.out.println("Table " + tableName + " does not exist");
            stmt.execute(createTable); //create the users table
            System.out.println(tableName + " Table Created");
            if(tableName == "users"){
                stmt.executeUpdate(DatabaseTablesSQL.ADD_DEFAULT_VALUES_TO_USERS_TABLE_SQL);
            }
        }
    }//end checkTable

    //initializes the db with the tables
    public java.sql.Connection initDB() throws ClassNotFoundException, SQLException{
        //establish a connection, this will also create a db if there is none
        Class.forName(DRIVER);
        java.sql.Connection connection = DriverManager.getConnection(JDBC_URL);
        System.out.println("Connected to DB Successfully");

        Statement initTables = connection.createStatement(); //creates a statment object

        //check if the tables are created
        DatabaseMetaData dbmd = connection.getMetaData(); //gets the database metadata
        //gets the result set for the specific tables
        //check the users table
        checkTable("users", dbmd, initTables, DatabaseTablesSQL.CREATE_USERS_TABLE_SQL);

        //check user access history table
        checkTable("access_history", dbmd, initTables, DatabaseTablesSQL.CREATE_USERS_ACCESS_HISTORY_TABLE_SQL);

        //check customers table
        checkTable("customers", dbmd, initTables, DatabaseTablesSQL.CREATE_CUSTOMERS_TABLE_SQL);

        //check venues table
        checkTable("venues", dbmd, initTables, DatabaseTablesSQL.CREATE_VENUES_TABLE_SQL);

        //check add_ons table
        checkTable("add_ons", dbmd, initTables, DatabaseTablesSQL.CREATE_ADD_ONS_TABLE_SQL);

        //check party_details table
        checkTable("party_details", dbmd, initTables, DatabaseTablesSQL.CREATE_PARTY_DETAILS_TABLE_SQL);

        //check catering_options table
        checkTable("catering_options", dbmd, initTables, DatabaseTablesSQL.CREATE_CATERING_OPTIONS_TABLE_SQL);
        
        //check sales table
        checkTable("sales", dbmd, initTables, DatabaseTablesSQL.CREATE_SALES_TABLE_SQL);
        
        //check payments table
        checkTable("payments", dbmd, initTables, DatabaseTablesSQL.CREATE_PAYMENTS_TABLE);

        return connection;
    }//end initDB
}
