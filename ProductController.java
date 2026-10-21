import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ProductController.java
 * ------------------------------------------------------------------
 * Full CRUD controller for the "products" table in MySQL.
 * Pairs with the Product model defined in product.java.
 *
 * Table schema (run once in MySQL):
 *   CREATE DATABASE IF NOT EXISTS ShopDB;
 *   USE ShopDB;
 *   CREATE TABLE IF NOT EXISTS products (
 *       id    INT AUTO_INCREMENT PRIMARY KEY,
 *       name  VARCHAR(100) NOT NULL,
 *       price DOUBLE       NOT NULL
 *   );
 *
 * Prerequisites:
 *   1. MySQL running locally on port 3306 (or update DB_URL below).
 *   2. MySQL Connector/J JAR on classpath:
 *      https://dev.mysql.com/downloads/connector/j/
 *
 * Compile:
 *   javac -cp .:mysql-connector-j-8.x.x.jar ProductController.java product.java
 *
 * Run:
 *   java -cp .:mysql-connector-j-8.x.x.jar ProductController
 * ------------------------------------------------------------------
 */
public class ProductController {

    // ── Database connection settings ─────────────────────────────────
    private static final String DB_URL  = "jdbc:mysql://localhost:3306/ShopDB";
    private static final String DB_USER = "root";          // ← change as needed
    private static final String DB_PASS = "your_password"; // ← change as needed

    // ── SQL Statements ───────────────────────────────────────────────
    private static final String SQL_INSERT       = "INSERT INTO products (name, price) VALUES (?, ?)";
    private static final String SQL_SELECT_ALL   = "SELECT id, name, price FROM products";
    private static final String SQL_SELECT_BY_ID = "SELECT id, name, price FROM products WHERE id = ?";
    private static final String SQL_UPDATE       = "UPDATE products SET name = ?, price = ? WHERE id = ?";
    private static final String SQL_DELETE       = "DELETE FROM products WHERE id = ?";

    // ── Connection helper ────────────────────────────────────────────

    /**
     * Opens and returns a new JDBC connection.
     * Caller is responsible for closing it (use try-with-resources).
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    // ────────────────────────────────────────────────────────────────
    //  CREATE
    // ────────────────────────────────────────────────────────────────

    /**
     * Inserts a new product into the database.
     *
     * @param product  Product object (id is ignored; auto-assigned by DB)
     * @return         The auto-generated id, or -1 on failure
     */
    public int addProduct(Product product) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT,
                                         Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());

            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        int generatedId = keys.getInt(1);
                        product.setId(generatedId);
                        System.out.println("✅  Product added — ID: " + generatedId
                                + ", Name: " + product.getName()
                                + ", Price: $" + product.getPrice());
                        return generatedId;
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("✘  Error adding product: " + e.getMessage());
        }
        return -1;
    }

    // ────────────────────────────────────────────────────────────────
    //  READ ALL
    // ────────────────────────────────────────────────────────────────

    /**
     * Retrieves all products from the database.
     *
     * @return List of Product objects (empty list if none found)
     */
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price")
                ));
            }

        } catch (SQLException e) {
            System.err.println("✘  Error fetching products: " + e.getMessage());
        }
        return products;
    }

    // ────────────────────────────────────────────────────────────────
    //  READ BY ID
    // ────────────────────────────────────────────────────────────────

    /**
     * Retrieves a single product by its primary key.
     *
     * @param id  The product ID to look up
     * @return    The matching Product, or null if not found
     */
    public Product getProductById(int id) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDouble("price")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("✘  Error fetching product by ID: " + e.getMessage());
        }
        return null;
    }

    // ────────────────────────────────────────────────────────────────
    //  UPDATE
    // ────────────────────────────────────────────────────────────────

    /**
     * Updates an existing product's name and price.
     *
     * @param product  Product with updated fields (id must match existing record)
     * @return         true if update was successful, false otherwise
     */
    public boolean updateProduct(Product product) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {

            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getId());

            int affected = ps.executeUpdate();
            if (affected > 0) {
                System.out.println("✅  Product updated — ID: " + product.getId()
                        + ", Name: " + product.getName()
                        + ", Price: $" + product.getPrice());
                return true;
            } else {
                System.out.println("⚠  No product found with ID: " + product.getId());
            }

        } catch (SQLException e) {
            System.err.println("✘  Error updating product: " + e.getMessage());
        }
        return false;
    }

    // ────────────────────────────────────────────────────────────────
    //  DELETE
    // ────────────────────────────────────────────────────────────────

    /**
     * Deletes a product by its primary key.
     *
     * @param id  The product ID to delete
     * @return    true if deletion was successful, false otherwise
     */
    public boolean deleteProduct(int id) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setInt(1, id);
            int affected = ps.executeUpdate();
            if (affected > 0) {
                System.out.println("✅  Product deleted — ID: " + id);
                return true;
            } else {
                System.out.println("⚠  No product found with ID: " + id);
            }

        } catch (SQLException e) {
            System.err.println("✘  Error deleting product: " + e.getMessage());
        }
        return false;
    }

    // ────────────────────────────────────────────────────────────────
    //  DISPLAY HELPER
    // ────────────────────────────────────────────────────────────────

    /**
     * Prints all products in a formatted table to stdout.
     */
    public void displayAllProducts() {
        List<Product> products = getAllProducts();

        System.out.println();
        System.out.println("╔══════╦══════════════════════════╦══════════════╗");
        System.out.println("║  ID  ║  Name                    ║  Price       ║");
        System.out.println("╠══════╬══════════════════════════╬══════════════╣");

        if (products.isEmpty()) {
            System.out.println("║              No products found.              ║");
        } else {
            for (Product p : products) {
                System.out.printf("║ %-4d ║ %-24s ║ $%-11.2f ║%n",
                        p.getId(), p.getName(), p.getPrice());
            }
        }

        System.out.println("╚══════╩══════════════════════════╩══════════════╝");
        System.out.println("  Total records: " + products.size());
        System.out.println();
    }

    // ────────────────────────────────────────────────────────────────
    //  MAIN — demo / smoke test
    // ────────────────────────────────────────────────────────────────

    public static void main(String[] args) {
        ProductController controller = new ProductController();

        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║        PRODUCT CRUD CONTROLLER DEMO          ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        // ── CREATE ──────────────────────────────────────────────────
        System.out.println("\n── CREATE ──────────────────────────────────────");
        Product p1 = new Product(0, "Laptop",              1299.99);
        Product p2 = new Product(0, "Wireless Mouse",         29.99);
        Product p3 = new Product(0, "Mechanical Keyboard",    89.99);

        controller.addProduct(p1);
        controller.addProduct(p2);
        controller.addProduct(p3);

        // ── READ ALL ────────────────────────────────────────────────
        System.out.println("\n── READ ALL ────────────────────────────────────");
        controller.displayAllProducts();

        // ── READ BY ID ──────────────────────────────────────────────
        System.out.println("── READ BY ID ──────────────────────────────────");
        Product found = controller.getProductById(p1.getId());
        if (found != null) {
            System.out.printf("   Found → ID: %d | Name: %s | Price: $%.2f%n",
                    found.getId(), found.getName(), found.getPrice());
        }

        // ── UPDATE ──────────────────────────────────────────────────
        System.out.println("\n── UPDATE ──────────────────────────────────────");
        p1.setName("Gaming Laptop");
        p1.setPrice(1499.99);
        controller.updateProduct(p1);

        // ── DISPLAY AFTER UPDATE ────────────────────────────────────
        System.out.println("\n── AFTER UPDATE ────────────────────────────────");
        controller.displayAllProducts();

        // ── DELETE ──────────────────────────────────────────────────
        System.out.println("── DELETE ──────────────────────────────────────");
        controller.deleteProduct(p3.getId());

        // ── FINAL STATE ─────────────────────────────────────────────
        System.out.println("\n── FINAL STATE ─────────────────────────────────");
        controller.displayAllProducts();
    }
}
