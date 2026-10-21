package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;   // ← for dependency injection
import org.springframework.boot.CommandLineRunner;                // ← lets us run code after startup
import org.springframework.boot.SpringApplication;               // ← starts the Spring Boot app
import org.springframework.boot.autoconfigure.SpringBootApplication; // ← main Spring Boot marker

/**
 * CollegeApp.java
 * ------------------------------------------------------------------
 * The main entry point of the Spring Boot application.
 *
 * Key annotations used:
 *
 *  @SpringBootApplication  – Combines three annotations in one:
 *      @Configuration      – This class can define Spring beans
 *      @EnableAutoConfiguration – Spring Boot auto-configures itself
 *      @ComponentScan      – Scans this package for @Service,
 *                            @Component, @Controller, etc.
 *
 *  CommandLineRunner       – A Spring Boot interface with a single
 *                            run() method. Spring calls run()
 *                            automatically after the app starts up.
 * ------------------------------------------------------------------
 */
@SpringBootApplication  // Makes this the root of the Spring Boot application
public class CollegeApp implements CommandLineRunner {

    /**
     * @Autowired tells Spring:
     *   "Find the StudentService bean you already created and
     *    inject (assign) it into this field automatically."
     *
     * We don't write  new StudentService()  ourselves —
     * Spring handles object creation and wiring for us.
     * This is called Dependency Injection (DI).
     */
    @Autowired
    private StudentService studentService;

    /**
     * main() — application entry point.
     *
     * SpringApplication.run() boots the entire Spring framework:
     *   1. Creates the application context
     *   2. Scans for and instantiates all @Service / @Component beans
     *   3. Performs @Autowired injections
     *   4. Calls run() on all CommandLineRunner beans
     */
    public static void main(String[] args) {
        SpringApplication.run(CollegeApp.class, args);  // ← start Spring Boot
    }

    /**
     * run() — executed automatically by Spring after startup.
     *
     * Here we use the injected StudentService to fetch the student
     * list and print each entry to the console.
     *
     * @param args command-line arguments (not used here)
     */
    @Override
    public void run(String... args) {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║        COLLEGE STUDENT DIRECTORY         ║");
        System.out.println("╠══════════════════════════════════════════╣");

        // studentService is injected by Spring — we just call its method
        for (String student : studentService.getStudents()) {
            System.out.println("║  ► " + student);  // print each student's details
        }

        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println();
    }
}
