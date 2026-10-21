package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ProductController.java — REST API Controller
 * ------------------------------------------------------------------
 * Exposes 5 HTTP endpoints for CRUD operations on products.
 * Data is stored in an in-memory ArrayList — no database needed.
 * All data is lost when the application restarts.
 *
 * Base URL: http://localhost:8080/products
 *
 * Endpoints:
 *   GET    /products        → list all products
 *   GET    /products/{id}   → get one product by ID
 *   POST   /products        → create a new product
 *   PUT    /products/{id}   → update an existing product
 *   DELETE /products/{id}   → delete a product
 * ------------------------------------------------------------------
 */
@RestController    // Combines @Controller + @ResponseBody:
                   //   @Controller  → marks this as a Spring MVC controller
                   //   @ResponseBody → every return value is written as JSON
@RequestMapping("/products")  // All endpoints in this class start with /products
public class ProductController {

    // ── In-memory storage ────────────────────────────────────────────
    // ArrayList acts as our "database" while the app is running.
    // Spring creates ONE instance of this controller, so the list
    // persists across multiple HTTP requests during one session.
    private List<Product> products = new ArrayList<>();

    // ── Constructor ──────────────────────────────────────────────────
    // Spring calls this once when the app starts.
    // We seed two products so GET requests return data immediately.
    public ProductController() {
        products.add(new Product(1, "Keyboard", 1200.0));
        products.add(new Product(2, "Mouse",     600.0));
    }

    // ════════════════════════════════════════════════════════════════
    //  1. GET /products   →  Returns ALL products
    // ════════════════════════════════════════════════════════════════
    /**
     * Handles:  GET http://localhost:8080/products
     * Response: JSON array of all products + HTTP 200 OK
     *
     * Example response:
     *   [
     *     { "id": 1, "name": "Keyboard", "price": 1200.0 },
     *     { "id": 2, "name": "Mouse",    "price": 600.0  }
     *   ]
     */
    @GetMapping   // Maps HTTP GET on /products to this method
    public ResponseEntity<List<Product>> getAllProducts() {
        // ResponseEntity.ok(body) → sends HTTP 200 OK with body as JSON
        return ResponseEntity.ok(products);
    }

    // ════════════════════════════════════════════════════════════════
    //  2. GET /products/{id}   →  Returns ONE product by ID
    // ════════════════════════════════════════════════════════════════
    /**
     * Handles:  GET http://localhost:8080/products/1
     * Response: Single product JSON + HTTP 200 OK
     *           or HTTP 404 Not Found if ID doesn't exist
     *
     * @PathVariable int id  →  reads the {id} part from the URL
     */
    @GetMapping("/{id}")   // Maps GET /products/{id}
    public ResponseEntity<Product> getProductById(@PathVariable int id) {

        // Loop through every product in the list
        for (Product product : products) {
            if (product.getId() == id) {
                return ResponseEntity.ok(product);  // ✅ Found → 200 OK
            }
        }

        // No product matched the ID → 404 Not Found
        return ResponseEntity.notFound().build();
    }

    // ════════════════════════════════════════════════════════════════
    //  3. POST /products   →  Creates a NEW product
    // ════════════════════════════════════════════════════════════════
    /**
     * Handles:  POST http://localhost:8080/products
     *           Body: { "id": 3, "name": "Monitor", "price": 8500.0 }
     * Response: The created product + HTTP 201 Created
     *           or HTTP 409 Conflict if ID already exists
     *
     * @RequestBody Product product  →  Spring converts the incoming
     *                                  JSON body into a Product object
     */
    @PostMapping   // Maps HTTP POST on /products
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {

        // Check if any existing product already has this ID
        for (Product existing : products) {
            if (existing.getId() == product.getId()) {
                // Duplicate ID → reject with 409 Conflict
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }

        // Add the new product to the in-memory list
        products.add(product);

        // Return the created product with HTTP 201 Created
        // (201 is preferred over 200 for successful creation)
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    // ════════════════════════════════════════════════════════════════
    //  4. PUT /products/{id}   →  Updates an existing product
    // ════════════════════════════════════════════════════════════════
    /**
     * Handles:  PUT http://localhost:8080/products/1
     *           Body: { "name": "Gaming Keyboard", "price": 2500.0 }
     * Response: Updated product + HTTP 200 OK
     *           or HTTP 404 Not Found if ID doesn't exist
     *
     * Note: We use the ID from the URL (not from the JSON body)
     *       so clients cannot accidentally change a product's ID.
     */
    @PutMapping("/{id}")   // Maps HTTP PUT on /products/{id}
    public ResponseEntity<Product> updateProduct(
            @PathVariable int id,           // ID from the URL path
            @RequestBody Product updated) { // New data from request body

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {

                // Always use the URL's ID — ignore any ID in the JSON body
                updated.setId(id);

                // Replace old product at index i with the updated one
                products.set(i, updated);

                // Return updated product with HTTP 200 OK
                return ResponseEntity.ok(updated);
            }
        }

        // Product with this ID not found → 404 Not Found
        return ResponseEntity.notFound().build();
    }

    // ════════════════════════════════════════════════════════════════
    //  5. DELETE /products/{id}   →  Deletes a product
    // ════════════════════════════════════════════════════════════════
    /**
     * Handles:  DELETE http://localhost:8080/products/1
     * Response: HTTP 204 No Content (success, no body returned)
     *           or HTTP 404 Not Found if ID doesn't exist
     *
     * 204 No Content is the standard response for a successful DELETE —
     * the resource is gone so there is nothing to return in the body.
     */
    @DeleteMapping("/{id}")   // Maps HTTP DELETE on /products/{id}
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {

                // Remove the product from the in-memory list
                products.remove(i);

                // Return HTTP 204 No Content (success with no response body)
                return ResponseEntity.noContent().build();
            }
        }

        // Product with this ID doesn't exist → 404 Not Found
        return ResponseEntity.notFound().build();
    }
}
