package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ProductCatalogApplication.java — Main Entry Point
 * ------------------------------------------------------------------
 * @SpringBootApplication does three things in one annotation:
 *
 *   1. @Configuration        → This class can declare Spring beans
 *   2. @EnableAutoConfiguration → Spring Boot auto-configures itself
 *                                 (e.g., starts embedded Tomcat,
 *                                  configures Jackson for JSON, etc.)
 *   3. @ComponentScan        → Scans com.example.demo package for
 *                               @RestController, @Service, @Component
 *
 * After startup:
 *   • Embedded Tomcat server runs on http://localhost:8080
 *   • All 5 product endpoints are ready to receive HTTP requests
 * ------------------------------------------------------------------
 */
@SpringBootApplication
public class ProductCatalogApplication {

    public static void main(String[] args) {
        // Boots the entire Spring application:
        //   → Creates ApplicationContext
        //   → Detects and wires all beans (@RestController, etc.)
        //   → Starts embedded Tomcat on port 8080
        SpringApplication.run(ProductCatalogApplication.class, args);

        // After this line the server is RUNNING and listening for requests
        System.out.println();
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║      Product Catalog REST API — RUNNING           ║");
        System.out.println("╠═══════════════════════════════════════════════════╣");
        System.out.println("║  GET    http://localhost:8080/products             ║");
        System.out.println("║  GET    http://localhost:8080/products/{id}        ║");
        System.out.println("║  POST   http://localhost:8080/products             ║");
        System.out.println("║  PUT    http://localhost:8080/products/{id}        ║");
        System.out.println("║  DELETE http://localhost:8080/products/{id}        ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
        System.out.println();
    }
}
