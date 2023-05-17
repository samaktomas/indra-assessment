import services.Database;
import services.Queue;

public class Main {
    public static void main(String[] args) {

        Database db = new Database("testn");
        db.initialize();
        Queue queue = new Queue(db);
        queue.createSchema("SUSERS");

        queue.add(1, "", "Robert");
        queue.add(2, "a2", "Martin");
        queue.add(3, "a3", "Peter");

        queue.printAll();
        queue.deleteAll();
        queue.printAll();

        queue.close();
    }

}