package Exercise1.Creational;

// Singleton Database Connection Manager
class DatabaseConnection {
    private static DatabaseConnection instance;

    private DatabaseConnection() {
        System.out.println("Connection to the database established.");
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public void query(String sql) {
        System.out.println("Executing query: " + sql);
    }
}

// Main Application
public class SingletonPatternDemo {
    public static void main(String[] args) {
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();

        db1.query("SELECT * FROM users");
        db2.query("UPDATE users SET name = 'John' WHERE id = 1");

        // db1 and db2 are the same instance
        System.out.println(db1 == db2);  // true
    }
}

