package com.example.demo;

import org.springframework.stereotype.Service;  // ← marks this as a Spring service bean
import java.util.Arrays;
import java.util.List;

/**
 * StudentService.java
 * ------------------------------------------------------------------
 * A Spring-managed service class that holds student information.
 *
 * @Service tells Spring Boot:
 *   "Create one instance of this class and keep it in the
 *    application context so other classes can use it."
 * ------------------------------------------------------------------
 */
@Service  // Spring will automatically detect and register this class
public class StudentService {

    /**
     * Hard-coded list of four students.
     * Each entry follows the format: "Name - Branch"
     *
     * In a real application this would come from a database or API,
     * but we keep it simple here to focus on Spring concepts.
     */
    private final List<String> students = Arrays.asList(
            "Rahul - Computer Science",
            "Aman  - Mechanical",
            "Priya - Electronics",
            "Neha  - Information Technology"
    );

    /**
     * Returns the complete list of student details.
     *
     * Any class that is injected with StudentService can call this
     * method to get the student data.
     * run cd /Users/sagarchhetri/Desktop/java/SpringBootDemo 
     * mvn spring-boot:run

     * @return an unmodifiable list of student name-branch strings
     */
    public List<String> getStudents() {
        return students;  // ← hand the list back to the caller
    }
}
