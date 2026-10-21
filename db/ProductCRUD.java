import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.Scanner;

/**
 * ProductCRUD.java
 * ------------------------------------------------------------------
 * Menu-driven CRUD application for a MongoDB "Product" collection.
 *
 * Collection fields:
 *   ProductID   (int)    – unique identifier
 *   ProductName (String) – name of the product
 *   Price       (double) – unit price
 *   Quantity    (int)    – stock quantity
 *
 * Features:
 *   ✔ Create  – insert a new product with transaction support
 *   ✔ Read    – display all products or search by ProductID
 *   ✔ Update  – update Price and/or Quantity with transaction support
 *   ✔ Delete  – remove a product by ProductID with transaction support
 *   ✔ Transaction handling – each write operation uses a ClientSession
 *     so changes are atomic (requires MongoDB 4.0+ replica set or
 *     Atlas cluster; standalone Mongo skips session automatically).
 *
 * Prerequisites:
 *   1. MongoDB running locally on port 27017.
 *   2. MongoDB Java Driver JARs on classpath:
 *        mongodb-driver-sync-4.11.1.jar
 *        bson-4.11.1.jar
 *        mongodb-driver-core-4.11.1.jar
 *
 * Compile:
 *   javac -cp .:mongodb-driver-sync-4.11.1.jar:bson-4.11.1.jar:mongodb-driver-core-4.11.1.jar ProductCRUD.java
 *
 * Run:
 *   java -cp .:mongodb-driver-sync-4.11.1.jar:bson-4.11.1.jar:mongodb-driver-core-4.11.1.jar ProductCRUD
 * ------------------------------------------------------------------
 */
public class ProductCRUD {

    // ── Connection settings ─────────────────────────────────────────
    private static final String CONNECTION_URI = "mongodb://localhost:27017/?retryWrites=false";
    private static final String DATABASE_NAME  = "ShopDB";
    private static final String COLLECTION     = "Product";

    private static MongoClient       mongoClient;
    private static MongoDatabase     database;
    private static MongoCollection<Document> collection;
    private static Scanner           scanner = new Scanner(System.in);

    // ── Entry point ─────────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║         PRODUCT MANAGEMENT SYSTEM (MongoDB)      ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        System.out.println("⏳ Connecting to MongoDB...");
        try {
            mongoClient = MongoClients.create(CONNECTION_URI);
            database    = mongoClient.getDatabase(DATABASE_NAME);
            collection  = database.getCollection(COLLECTION);
            long count  = collection.countDocuments();
            System.out.println("╔══════════════════════════════════════════════════╗");
            System.out.println("║   ✅  MongoDB Connected Successfully!          ║");
            System.out.println("╠══════════════════════════════════════════════════╣");
            System.out.printf( "║   🗄  Database   : %-28s║%n", DATABASE_NAME);
            System.out.printf( "║   📁  Collection : %-28s║%n", COLLECTION);
            System.out.printf( "║   📊  Records    : %-28d║%n", count);
            System.out.println("╚══════════════════════════════════════════════════╝");
            System.out.println();
        } catch (Exception e) {
            System.err.println("✘  Cannot connect to MongoDB: " + e.getMessage());
            return;
        }

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");
            System.out.println();
            switch (choice) {
                case 1 -> createProduct();
                case 2 -> readAllProducts();
                case 3 -> searchProduct();
                case 4 -> updateProduct();
                case 5 -> deleteProduct();
                case 6 -> { running = false; System.out.println("Goodbye! 👋"); }
                default -> System.out.println("⚠  Invalid option. Please choose 1–6.\n");
            }
        }

        mongoClient.close();
        scanner.close();
    }

    // ── MENU ────────────────────────────────────────────────────────
    private static void printMenu() {
        System.out.println("┌──────────────────────────────────┐");
        System.out.println("│          MAIN MENU               │");
        System.out.println("├──────────────────────────────────┤");
        System.out.println("│  1. Create  – Add new product    │");
        System.out.println("│  2. Read    – View all products  │");
        System.out.println("│  3. Search  – Find by ProductID  │");
        System.out.println("│  4. Update  – Edit product       │");
        System.out.println("│  5. Delete  – Remove product     │");
        System.out.println("│  6. Exit                         │");
        System.out.println("└──────────────────────────────────┘");
    }

    // ── CREATE ──────────────────────────────────────────────────────
    private static void createProduct() {
        System.out.println("── ADD NEW PRODUCT ─────────────────────────────");

        int    productId   = readInt("Enter ProductID   : ");
        String productName = readString("Enter ProductName : ");
        double price       = readDouble("Enter Price       : ");
        int    quantity    = readInt("Enter Quantity    : ");

        // Check for duplicate ProductID
        if (collection.find(Filters.eq("ProductID", productId)).first() != null) {
            System.out.println("✘  Product with ID " + productId + " already exists.\n");
            return;
        }

        Document doc = new Document("ProductID",   productId)
                           .append("ProductName", productName)
                           .append("Price",       price)
                           .append("Quantity",    quantity);

        // ── Transaction block ────────────────────────────────────────
        try (ClientSession session = mongoClient.startSession()) {
            session.startTransaction();
            try {
                collection.insertOne(session, doc);
                session.commitTransaction();
                System.out.println("✔  Product inserted successfully (transaction committed).\n");
            } catch (Exception e) {
                try { session.abortTransaction(); } catch (Exception ignored) {}
                // Standalone MongoDB doesn't support transactions — fall back to direct insert
                try {
                    collection.insertOne(doc);
                    System.out.println("✔  Product inserted successfully (standalone mode).\n");
                } catch (Exception ex) {
                    System.out.println("✘  Insert failed: " + ex.getMessage() + "\n");
                }
            }
        } catch (Exception e) {
            // Fallback for standalone MongoDB (no replica set) – skip session
            try {
                collection.insertOne(doc);
                System.out.println("✔  Product inserted successfully (no-session mode).\n");
            } catch (Exception ex) {
                System.out.println("✘  Insert failed: " + ex.getMessage() + "\n");
            }
        }
    }

    // ── READ ALL ────────────────────────────────────────────────────
    private static void readAllProducts() {
        System.out.println("── ALL PRODUCTS ────────────────────────────────");

        long count = collection.countDocuments();
        if (count == 0) {
            System.out.println("⚠  No products found in the database.\n");
            return;
        }

        printTableHeader();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                printProduct(cursor.next());
            }
        }
        printTableFooter();
        System.out.println("   Total records: " + count + "\n");
    }

    // ── SEARCH ──────────────────────────────────────────────────────
    private static void searchProduct() {
        System.out.println("── SEARCH PRODUCT ──────────────────────────────");
        int id = readInt("Enter ProductID to search: ");

        Document doc = collection.find(Filters.eq("ProductID", id)).first();
        if (doc == null) {
            System.out.println("⚠  No product found with ID " + id + ".\n");
        } else {
            printTableHeader();
            printProduct(doc);
            printTableFooter();
            System.out.println();
        }
    }

    // ── UPDATE ──────────────────────────────────────────────────────
    private static void updateProduct() {
        System.out.println("── UPDATE PRODUCT ──────────────────────────────");
        int id = readInt("Enter ProductID to update: ");

        Document existing = collection.find(Filters.eq("ProductID", id)).first();
        if (existing == null) {
            System.out.println("⚠  No product found with ID " + id + ".\n");
            return;
        }

        System.out.println("Current record:");
        printTableHeader();
        printProduct(existing);
        printTableFooter();

        System.out.println("\nWhat would you like to update?");
        System.out.println("  1. Price only");
        System.out.println("  2. Quantity only");
        System.out.println("  3. Both Price and Quantity");
        int option = readInt("Choice: ");

        double newPrice    = existing.getDouble("Price");
        int    newQuantity = existing.getInteger("Quantity");

        if (option == 1 || option == 3) newPrice    = readDouble("New Price    : ");
        if (option == 2 || option == 3) newQuantity = readInt("New Quantity : ");

        // ── Transaction block ────────────────────────────────────────
        try (ClientSession session = mongoClient.startSession()) {
            session.startTransaction();
            try {
                UpdateResult result = collection.updateOne(
                    session,
                    Filters.eq("ProductID", id),
                    Updates.combine(
                        Updates.set("Price",    newPrice),
                        Updates.set("Quantity", newQuantity)
                    )
                );
                session.commitTransaction();
                System.out.printf("✔  Updated %d document(s) (transaction committed).%n%n",
                                  result.getModifiedCount());
            } catch (Exception e) {
                try { session.abortTransaction(); } catch (Exception ignored) {}
                // Standalone MongoDB doesn't support transactions — fall back to direct update
                try {
                    UpdateResult result = collection.updateOne(
                        Filters.eq("ProductID", id),
                        Updates.combine(
                            Updates.set("Price",    newPrice),
                            Updates.set("Quantity", newQuantity)
                        )
                    );
                    System.out.printf("✔  Updated %d document(s) (standalone mode).%n%n",
                                      result.getModifiedCount());
                } catch (Exception ex) {
                    System.out.println("✘  Update failed: " + ex.getMessage() + "\n");
                }
            }
        } catch (Exception e) {
            // Fallback for standalone MongoDB
            try {
                UpdateResult result = collection.updateOne(
                    Filters.eq("ProductID", id),
                    Updates.combine(
                        Updates.set("Price",    newPrice),
                        Updates.set("Quantity", newQuantity)
                    )
                );
                System.out.printf("✔  Updated %d document(s) (no-session mode).%n%n",
                                  result.getModifiedCount());
            } catch (Exception ex) {
                System.out.println("✘  Update failed: " + ex.getMessage() + "\n");
            }
        }
    }

    // ── DELETE ──────────────────────────────────────────────────────
    private static void deleteProduct() {
        System.out.println("── DELETE PRODUCT ──────────────────────────────");
        int id = readInt("Enter ProductID to delete: ");

        Document existing = collection.find(Filters.eq("ProductID", id)).first();
        if (existing == null) {
            System.out.println("⚠  No product found with ID " + id + ".\n");
            return;
        }

        System.out.println("Record to delete:");
        printTableHeader();
        printProduct(existing);
        printTableFooter();

        String confirm = readString("\nAre you sure? (yes/no): ");
        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("ℹ  Deletion cancelled.\n");
            return;
        }

        // ── Transaction block ────────────────────────────────────────
        try (ClientSession session = mongoClient.startSession()) {
            session.startTransaction();
            try {
                DeleteResult result = collection.deleteOne(
                    session,
                    Filters.eq("ProductID", id)
                );
                session.commitTransaction();
                System.out.printf("✔  Deleted %d document(s) (transaction committed).%n%n",
                                  result.getDeletedCount());
            } catch (Exception e) {
                try { session.abortTransaction(); } catch (Exception ignored) {}
                // Standalone MongoDB doesn't support transactions — fall back to direct delete
                try {
                    DeleteResult result = collection.deleteOne(Filters.eq("ProductID", id));
                    System.out.printf("✔  Deleted %d document(s) (standalone mode).%n%n",
                                      result.getDeletedCount());
                } catch (Exception ex) {
                    System.out.println("✘  Delete failed: " + ex.getMessage() + "\n");
                }
            }
        } catch (Exception e) {
            // Fallback for standalone MongoDB
            try {
                DeleteResult result = collection.deleteOne(Filters.eq("ProductID", id));
                System.out.printf("✔  Deleted %d document(s) (no-session mode).%n%n",
                                  result.getDeletedCount());
            } catch (Exception ex) {
                System.out.println("✘  Delete failed: " + ex.getMessage() + "\n");
            }
        }
    }

    // ── TABLE PRINTING HELPERS ───────────────────────────────────────
    private static void printTableHeader() {
        System.out.println("┌───────────┬───────────────────────┬────────────┬──────────┐");
        System.out.printf( "│ %-9s │ %-21s │ %-10s │ %-8s │%n",
                           "ProductID", "ProductName", "Price", "Quantity");
        System.out.println("├───────────┼───────────────────────┼────────────┼──────────┤");
    }

    private static void printTableFooter() {
        System.out.println("└───────────┴───────────────────────┴────────────┴──────────┘");
    }

    private static void printProduct(Document doc) {
        int    pid  = doc.getInteger("ProductID",  0);
        String name = doc.getString("ProductName") != null ? doc.getString("ProductName") : "N/A";
        double price = doc.get("Price") instanceof Number
                       ? ((Number) doc.get("Price")).doubleValue() : 0.0;
        int    qty  = doc.getInteger("Quantity", 0);

        System.out.printf("│ %-9d │ %-21s │ $%-9.2f │ %-8d │%n", pid, name, price, qty);
    }

    // ── INPUT HELPERS ────────────────────────────────────────────────
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  ⚠  Please enter a valid integer.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  ⚠  Please enter a valid number.");
            }
        }
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
