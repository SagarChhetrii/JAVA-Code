class Employee {

    String name = "Employee";
    double salary;

    // Parent constructor
    Employee(double salary) {
        System.out.println("Employee constructor called");
        this.salary = salary;
    }

    // Parent method with parameters
    void display(String message) {
        System.out.println("Parent message: " + message);
        System.out.println("Employee salary: " + salary);
    }
}
class Manager extends Employee {

    String name = "Manager";

    // Child constructor
    Manager(double salary) {

        // Calling parent constructor
        super(salary);

        System.out.println("Manager constructor called");
    }

    // Overriding parent method
    @Override
    void display(String message) {

        // Accessing parent's variable
        System.out.println("Parent name: " + super.name);

        // Accessing child's variable
        System.out.println("Child name: " + this.name);

        // Calling parent's method with parameter
        super.display(message);

        // Child's own behavior
        System.out.println("Manager is managing the team");
    }
}


public class superr {

    public static void main(String[] args) {

        Manager m = new Manager(80000);

        m.display("Employee details");
    }
}
/*
parent class{
    string name;
    int id;
    smethod(string name, int id){
        this.name = name;
        this.id = id;
    }
}
*/
