package com.example.demo;

/**
 * Product.java — Model Class
 * ------------------------------------------------------------------
 * Represents a single product in the catalog.
 *
 * Spring uses Jackson (included automatically) to convert between
 * Java Product objects and JSON — this is called serialization.
 *
 *  Java Object  ──►  JSON  :  serialization   (response body)
 *  JSON         ──►  Java  :  deserialization (request body)
 *
 * Jackson needs:
 *   ✔ A no-arg constructor  (to create the object first)
 *   ✔ Getters               (to read fields for JSON output)
 *   ✔ Setters               (to write fields from JSON input)
 * ------------------------------------------------------------------
 */
public class Product {

    // Unique ID used to identify each product (e.g., 1, 2, 3)
    private int id;

    // Product name, for example "Keyboard" or "Monitor"
    private String name;

    // Price of the product in rupees (or any currency)
    private double price;

    // ── No-arg constructor ───────────────────────────────────────────
    // Required by Jackson when it deserializes JSON into a Product object.
    // Jackson calls new Product() first, then uses setters to fill fields.
    public Product() {
    }

    // ── All-arg constructor ──────────────────────────────────────────
    // Convenient way to create a Product with all fields in one line.
    // Used in ProductController to seed the initial sample data.
    public Product(int id, String name, double price) {
        this.id    = id;
        this.name  = name;
        this.price = price;
    }

    // ── Getters & Setters ────────────────────────────────────────────
    // Jackson reads getters to build the JSON output.
    // Jackson calls setters to populate fields from incoming JSON.

    public int getId() {
        return id;      // Jackson maps this to "id" in JSON
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;    // Jackson maps this to "name" in JSON
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;   // Jackson maps this to "price" in JSON
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // ── toString ─────────────────────────────────────────────────────
    // Useful for logging / debugging — not used by Jackson
    @Override
    public String toString() {
        return "Product{id=" + id + ", name='" + name + "', price=" + price + "}";
    }
}
