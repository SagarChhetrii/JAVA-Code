abstract class Person {
    String name;
    int age;

    // Constructor
    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Abstract method
    abstract void displayDetails();
}

// Derived class Student
class Student extends Person {
    int rollNumber;

    Student(String name, int age, int rollNumber) {
        super(name, age);
        this.rollNumber = rollNumber;
    }

    // Method overriding
    @Override
    void displayDetails() {
        System.out.println("Student Details:");
        System.out.println("Name        : " + name);
        System.out.println("Age         : " + age);
        System.out.println("Roll Number : " + rollNumber);
    }
}

// Derived class Teacher
class Teacher extends Person {
    String subject;

    Teacher(String name, int age, String subject) {
        super(name, age);
        this.subject = subject;
    }

    // Method overriding
    @Override
    void displayDetails() {
        System.out.println("Teacher Details:");
        System.out.println("Name    : " + name);
        System.out.println("Age     : " + age);
        System.out.println("Subject : " + subject);
    }
}

public class studentinfo {
    public static void main(String[] args) {
        Student student = new Student("Rahul", 20, 101);
        Teacher teacher = new Teacher("Mr. Sharma", 40, "Computer Science");
        student.displayDetails();
        System.out.println();
        teacher.displayDetails();
    }
}