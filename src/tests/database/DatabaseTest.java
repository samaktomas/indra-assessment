package database;

import models.User;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.sqlite.SQLiteDataSource;
import services.Database;
import java.io.*;

public class DatabaseTest {

    private Database dummyDb = null;
    private static final String dbName = "myTestDb";

    @Before
    public void setUp() {
        dummyDb = new Database(dbName);
    }

    @AfterClass
    public static void cleanUp() {
        System.out.println("after");
        new File(dbName + ".db").delete();
    }

    @Test
    public void initializeTestSuccess() {
        dummyDb.initialize();
        dummyDb.setSchema("testSchema");
        var status = dummyDb.createSchema();
        Assertions.assertEquals("Success", status);;
    }

    @Test
    public void printAllTestSuccess() {
        dummyDb.initialize();
        dummyDb.setSchema("testSchema");
        dummyDb.createSchema();
        dummyDb.add(new User(1, "ax1", "Milan"));
        var res = dummyDb.printAll();
        Assertions.assertEquals("Success", res);;
    }

    @Test
    public void deleteAllErrorTest() {
        dummyDb.setDs(new SQLiteDataSource());
        var query = "DUMMY QUERY TEST";
        var action = "NOT_EXISTING_ACTION";
        var status = dummyDb.executeQuery(query, action);
        Assertions.assertEquals("Error Query Error occurred", status);;
    }

    @Test
    public void executeQueryErrorTest() {
        dummyDb.setDs(new SQLiteDataSource());
        var query = "DUMMY QUERY TEST";
        var action = "NOT_EXISTING_ACTION";
        var status = dummyDb.executeQuery(query, action);
        Assertions.assertEquals("Error Query Error occurred", status);;
    }

    @Test
    public void executeQueryDbErrorTest() {
        dummyDb = new Database("test");
        dummyDb.setDs(new SQLiteDataSource());
        var query = "DUMMY QUERY TEST";
        var action = "get";
        var status = dummyDb.executeQuery(query, action);
        Assertions.assertEquals("Error [SQLITE_ERROR] SQL error or missing " +
                "database (near \"DUMMY\": syntax error)", status);;
    }
}