package services;

import models.User;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Queue {
    private final Database db;
    private final ExecutorService executor;

    public Queue(Database db) {
        this.db = db;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void createSchema(String tableName){
        this.db.setSchemaName(tableName);
        this.executor.submit(new QueueAction(null, "createSchema", this.db));
    }

    public void printAll(){
        this.executor.submit(new QueueAction(null, "printAll", this.db));
    }

    public void add(Integer id, String uid, String name){
        this.executor.submit(new QueueAction(new User(id, uid, name), "insert", this.db));
    }

    public void deleteAll(){
        this.executor.submit(new QueueAction(null, "deleteAll", this.db));
    }

    public void close(){
        this.executor.shutdown();
    }
}
