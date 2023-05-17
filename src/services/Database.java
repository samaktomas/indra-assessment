package services;

import models.User;
import org.sqlite.SQLiteDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private final String dbName;
    private String tableName;
    private SQLiteDataSource ds;

    public Database(String dbName) {
        this.dbName = dbName;
        this.ds = null;
    }

    public void initialize(){

        try {

            if (dbName.isBlank()){
                throw new SQLException("DB name cant' be empty");
            }
            ds = new SQLiteDataSource();
            ds.setUrl(String.format("jdbc:sqlite:%s.db",this.dbName));

            System.out.println( "Opened database successfully");

        } catch ( SQLException e ) {

            System.out.println("Error occurred: " + e.getMessage());
            System.exit( 0 );
        }
    }

    public void setSchemaName(String tableName){

        try{
            if (tableName.isBlank()){
                throw new SQLException("Table name can't be empty");
            }
            this.tableName = tableName;

        } catch (SQLException e) {
            System.out.println("Error occurred: " + e.getMessage());
            System.exit(0);
        }
    }
    public void createSchema(){

        String query = "CREATE TABLE IF NOT EXISTS " + this.tableName + " ( " +
                "USER_ID INTEGER NOT NULL UNIQUE, " +
                "USER_GUID TEXT PRIMARY KEY," +
                "USER_NAME TEXT NOT NULL )";

        this.query(query, "update");

    }

    public void printAll(){

        System.out.println("\nAttempting to print all records...");
        var query = "SELECT * FROM " + this.tableName;
        this.query(query, "get");

    }

    public void add(User user){

        System.out.println( "\nAttempting to insert records..." );
        var values = "'" + user.getUserId() + "', '" + user.getUserGuid() + "', '" + user.getUserName() + "'";
        String query = "INSERT INTO " + this.tableName + " (USER_ID, USER_GUID, USER_NAME) VALUES ( " + values + " )";
        this.query(query, "update");

    }

    public void deleteAll() {

        System.out.println("\nAttempting to delete records...");
        String query = "DELETE FROM " + this.tableName;
        this.query(query, "update");

    }

    public void query(String query, String action){

        try {

            Connection conn = ds.getConnection();
            Statement stmt = conn.createStatement();
            if (action.equals("update")){

                stmt.executeUpdate( query );

            } else if (action.equals("get")){

                ResultSet rs = stmt.executeQuery(query);
                if (!rs.isBeforeFirst() ) {
                    System.out.println("No records found");
                }
                while ( rs.next() ) {
                    int id = rs.getInt( "USER_ID" );
                    String guid = rs.getString( "USER_GUID" );
                    String name = rs.getString( "USER_NAME" );

                    System.out.println( "USER_ID = " + id );
                    System.out.println( "USER_GUID = " + guid );
                    System.out.println( "USER_NAME = " + name + "\n" );
                }
            }
            else {
                throw new SQLException("Query Error occurred");
            }

            System.out.println( "Query '" + query + "' done successfully.");
            stmt.close();
            conn.close();

        } catch ( SQLException e ) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

}