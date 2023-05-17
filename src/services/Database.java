package services;

import helper.DBTablePrinter;
import models.User;
import org.sqlite.SQLiteDataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private final String dbName;
    private String tableName;
    private SQLiteDataSource ds;

    private String dbUrl;

    public Database(String dbName) {
        this.dbName = dbName;
        this.ds = null;
        this.dbUrl = "";
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setDs(SQLiteDataSource ds) {
        this.ds = ds;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbName() {
        return dbName;
    }

    public String getTableName() {
        return tableName;
    }

    public SQLiteDataSource getDs() {
        return ds;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void initialize(){

        try {

            if (dbName.isBlank()){
                throw new SQLException("DB name cant' be empty");
            }
            ds = new SQLiteDataSource();
            var url = this.getDbUrl();
            if (url.isBlank()){
                url = String.format("jdbc:sqlite:%s.db",this.getDbName());
            }
            ds.setUrl(url);

            System.out.println("Opened database successfully");

        } catch (SQLException e) {

            System.out.println("Error occurred: " + e.getMessage());
            System.exit(0);
        }

    }

    public boolean deleteDb(){
        File db = new File(this.getDbUrl());
        return db.delete();
    }

        public void setSchema(String tableName){

        try{

            if (tableName.isBlank()){
                throw new SQLException("Table name can't be empty");
            }
            this.setTableName(tableName);

        } catch (SQLException e) {
            System.out.println("Error occurred: " + e.getMessage());
            System.exit(0);

        }
    }
    public String createSchema(){

        String query = "CREATE TABLE IF NOT EXISTS " + this.getTableName() + " ( " +
                "USER_ID INTEGER NOT NULL UNIQUE, " +
                "USER_GUID TEXT PRIMARY KEY," +
                "USER_NAME TEXT NOT NULL )";

        return this.executeQuery(query, "update");

    }

    public String printAll(){

        System.out.println("\nAttempting to print all records...");
        var query = "SELECT * FROM " + this.getTableName();
        return this.executeQuery(query, "get");

    }

    public String add(User user){

        System.out.println("\nAttempting to insert records...");
        var values = "'" + user.getUserId() + "', '" + user.getUserGuid() + "', '" + user.getUserName() + "'";
        String query = "INSERT INTO " + this.getTableName() + " (USER_ID, USER_GUID, USER_NAME) VALUES ( " + values + " )";
        return this.executeQuery(query, "update");

    }

    public String deleteAll() {

        System.out.println("\nAttempting to delete records...");
        String query = "DELETE FROM " + this.getTableName();
        return this.executeQuery(query, "update");

    }

    public String executeQuery(String query, String action){

        try {
            Connection conn = ds.getConnection();
            Statement stmt = conn.createStatement();

            if (action.equals("update")){
                stmt.executeUpdate(query);

            } else if (action.equals("get")){
                ResultSet rs = stmt.executeQuery(query);
                DBTablePrinter.printResultSet(rs);
            }

            else {
                throw new SQLException("Query Error occurred");
            }

            System.out.println("Query '" + query + "' done successfully.");
            stmt.close();
            conn.close();
            return "Success";

        } catch (SQLException e) {
            System.out.println("Error occurred: " + e.getMessage());
            return "Error " + e.getMessage();
        }
    }

}