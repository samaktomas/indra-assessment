import services.Database;
import services.Queue;

public class Main {
    public static void main(String[] args) {

        Database db = new Database("myUsersDb");
        db.initialize();
        Queue queue = new Queue(db);
        queue.createSchema("SUSERS");

        queue.add(1, "a1", "Robert");
        queue.add(2, "a2", "Martin");
        queue.add(3, "a3", "Peter");

        for (int i = 1; i < 100; i++) {
            queue.add(i, "a" + i, "Robert"+i);
        }
        queue.printAll();
        queue.deleteAll();
        queue.printAll();

        queue.close();
    }
}