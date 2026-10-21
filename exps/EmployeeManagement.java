import java.util.ArrayList;
import java.util.Scanner;

class Employee {
    int id;
    String name;
    double salary;

    Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    void display() {
        System.out.println("ID: " + id +
                ", Name: " + name +
                ", Salary: " + salary);
    }
}

public class EmployeeManagement {

    public static void main(String[] args) {

        ArrayList<Employee> employees = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n--- Employee Management ---");
            System.out.println("1. Add Employee");
            System.out.println("2. Update Employee");
            System.out.println("3. Remove Employee");
            System.out.println("4. Search Employee");
            System.out.println("5. Display All Employees");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter Employee ID: ");
                    int id = sc.nextInt();

                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Salary: ");
                    double salary = sc.nextDouble();

                    employees.add(new Employee(id, name, salary));

                    System.out.println("Employee added successfully!");
                    break;

                case 2:
                    System.out.print("Enter Employee ID to update: ");
                    int updateId = sc.nextInt();

                    boolean updated = false;

                    for (Employee emp : employees) {
                        if (emp.id == updateId) {

                            sc.nextLine();

                            System.out.print("Enter new name: ");
                            emp.name = sc.nextLine();

                            System.out.print("Enter new salary: ");
                            emp.salary = sc.nextDouble();

                            updated = true;
                            System.out.println("Employee updated!");
                            break;
                        }
                    }

                    if (!updated) {
                        System.out.println("Employee not found!");
                    }
                    break;

                case 3:
                    System.out.print("Enter Employee ID to remove: ");
                    int removeId = sc.nextInt();

                    boolean removed = false;

                    for (int i = 0; i < employees.size(); i++) {
                        if (employees.get(i).id == removeId) {
                            employees.remove(i);
                            removed = true;
                            System.out.println("Employee removed!");
                            break;
                        }
                    }

                    if (!removed) {
                        System.out.println("Employee not found!");
                    }
                    break;

                case 4:
                    System.out.print("Enter Employee ID to search: ");
                    int searchId = sc.nextInt();

                    boolean found = false;

                    for (Employee emp : employees) {
                        if (emp.id == searchId) {
                            emp.display();
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("Employee not found!");
                    }
                    break;

                case 5:
                    if (employees.isEmpty()) {
                        System.out.println("No employees available.");
                    } else {
                        for (Employee emp : employees) {
                            emp.display();
                        }
                    }
                    break;

                case 6:
                    System.out.println("Program ended.");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}