package services;

import models.User;
import java.sql.SQLException;

public class QueueAction implements Runnable {
    private final User user;
    private final String actionType;
    private final Database db;

    public QueueAction(User user, String actionType, Database db){
        this.user = user;
        this.actionType = actionType;
        this.db = db;
    }

    public void run() {
        try {
            if (user != null){
                if (actionType.equals("insert")){
                    db.add(user);
                }
            }
            else {
                switch (actionType) {
                    case "printAll" -> db.printAll();
                    case "deleteAll" -> db.deleteAll();
                    case "createSchema" -> db.createSchema();
                    default -> throw new SQLException("SQL Query Action type Error");
                }
            }

        } catch (Exception e) {
            System.out.println("Error Queue run occurred: " + e.getMessage());
        }
    }
}