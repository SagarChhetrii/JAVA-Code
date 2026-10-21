import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

/**
 * EmployeeFetch.java
 * ------------------------------------------------------------------
 * Connects to a MongoDB database and retrieves all records from the
 * "Employee" collection, displaying EmpID, Name, and Salary fields.
 *
 * Prerequisites:
 *   1. MongoDB running locally on port 27017 (or update URI below).
 *   2. MongoDB Java Driver JAR on classpath:
 *      https://repo1.maven.org/maven2/org/mongodb/mongodb-driver-sync/4.11.1/
 *
 * Compile:
 *   javac -cp .:mongodb-driver-sync-4.11.1.jar:bson-4.11.1.jar:mongodb-driver-core-4.11.1.jar EmployeeFetch.java
 *
 * Run:
 *   java -cp .:mongodb-driver-sync-4.11.1.jar:bson-4.11.1.jar:mongodb-driver-core-4.11.1.jar EmployeeFetch
 *
 * Sample documents to insert via MongoDB Compass:
 *   { "EmpID": 1, "Name": "Alice Johnson",  "Salary": 75000 }
 *   { "EmpID": 2, "Name": "Bob Smith",      "Salary": 62000 }
 *   { "EmpID": 3, "Name": "Carol Williams", "Salary": 88000 }
 * ------------------------------------------------------------------
 */
public class EmployeeFetch {

    // ── Connection settings ─────────────────────────────────────────
    private static final String CONNECTION_URI = "mongodb://localhost:27017";
    private static final String DATABASE_NAME  = "CompanyDB";
    private static final String COLLECTION     = "Employee";

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║          EMPLOYEE RECORDS VIEWER             ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("⏳ Connecting to MongoDB...");

        // try-with-resources automatically closes the MongoClient
        try (MongoClient mongoClient = MongoClients.create(CONNECTION_URI)) {

            // 1. Get the database
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

            // 2. Get the collection
            MongoCollection<Document> collection = database.getCollection(COLLECTION);
            long totalRecords = collection.countDocuments();

            // ── Connection success banner ────────────────────────────
            System.out.println("╔══════════════════════════════════════════════╗");
            System.out.println("║   ✅  MongoDB Connected Successfully!        ║");
            System.out.println("╠══════════════════════════════════════════════╣");
            System.out.printf( "║   🗄  Database   : %-26s║%n", DATABASE_NAME);
            System.out.printf( "║   📁  Collection : %-26s║%n", COLLECTION);
            System.out.printf( "║   📊  Records    : %-26d║%n", totalRecords);
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.println();

            if (totalRecords == 0) {
                System.out.println("⚠  No records found. Please insert data using MongoDB Compass.");
                return;
            }

            // 3. Print table header
            printDivider();
            System.out.printf("║ %-8s │ %-20s │ %-12s ║%n", "EmpID", "Name", "Salary");
            printDivider();

            // 4. Iterate and display all documents
            try (MongoCursor<Document> cursor = collection.find().iterator()) {
                while (cursor.hasNext()) {
                    Document doc = cursor.next();

                    // Safely retrieve fields with fallback defaults
                    Object empIdObj = doc.get("EmpID");
                    String name     = doc.getString("Name")   != null ? doc.getString("Name")   : "N/A";
                    Object salObj   = doc.get("Salary");

                    String empId  = empIdObj != null ? empIdObj.toString() : "N/A";
                    String salary = salObj   != null ? formatSalary(salObj) : "N/A";

                    System.out.printf("║ %-8s │ %-20s │ %-12s ║%n", empId, name, salary);
                }
            }

            printDivider();
            System.out.println();
            System.out.println("✔  All records displayed successfully.");

        } catch (Exception e) {
            System.err.println("✘  Error connecting to MongoDB: " + e.getMessage());
            System.err.println("   Make sure MongoDB is running and the URI is correct.");
            e.printStackTrace();
        }
    }

    // ── Helpers ─────────────────────────────────────────────────────

    private static void printDivider() {
        System.out.println("╠══════════╪══════════════════════╪══════════════╣");
    }

    /**
     * Formats salary with a $ prefix regardless of whether it is stored
     * as Integer, Long, Double, or another Number type in MongoDB.
     */
    private static String formatSalary(Object salObj) {
        if (salObj instanceof Number) {
            return String.format("$%,.2f", ((Number) salObj).doubleValue());
        }
        return salObj.toString();
    }
}
