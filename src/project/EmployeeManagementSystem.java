package project;

import java.io.*;
import java.util.*;

public class EmployeeManagementSystem {
    private static final String FILE_PATH = "employee_data.txt";
    private static final List<Employee> employees = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    private static class Employee {
        private String id;
        private String name;
        private double salary;

        public Employee(String id, String name, double salary) {
            this.id = id;
            this.name = name;
            this.salary = salary;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getSalary() {
            return salary;
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", salary=" + salary +
                    '}';
        }
    }

    public static void main(String[] args) {
        loadEmployeeData();

        while (true) {
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Search Employee");
            System.out.println("5. Exit");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    viewEmployees();
                    break;
                case 3:
                    updateEmployee();
                    break;
                case 4:
                    searchEmployee();
                    break;
                case 5:
                    saveEmployeeData();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addEmployee() {
        System.out.println("Enter employee details:");

        System.out.print("ID: ");
        String id = scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        double salary = getValidSalary();

        Employee newEmployee = new Employee(id, name, salary);
        employees.add(newEmployee);

        System.out.println("Employee added successfully!");
    }

    private static void viewEmployees() {
        System.out.println("Employee List:");
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    private static void updateEmployee() {
        System.out.print("Enter employee ID to update: ");
        String idToUpdate = scanner.nextLine();

        boolean found = false;
        for (Employee employee : employees) {
            if (employee.getId().equals(idToUpdate)) {
                System.out.print("Enter new salary for " + employee.getName() + ": ");
                double newSalary = getValidSalary();
                employee = new Employee(employee.getId(), employee.getName(), newSalary);

                System.out.println("Employee updated successfully!");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Employee not found with ID: " + idToUpdate);
        }
    }

    private static void searchEmployee() {
        System.out.print("Enter employee ID to search: ");
        String idToSearch = scanner.nextLine();

        boolean found = false;
        for (Employee employee : employees) {
            if (employee.getId().equals(idToSearch)) {
                System.out.println("Employee found: " + employee);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Employee not found with ID: " + idToSearch);
        }
    }

    private static double getValidSalary() {
        double salary = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Salary: ");
            try {
                salary = Double.parseDouble(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid salary.");
            }
        }

        return salary;
    }

    private static int getIntInput() {
        int choice = -1;
        boolean validInput = false;

        while (!validInput) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }

        return choice;
    }

    private static void loadEmployeeData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Employee employee = new Employee(data[0], data[1], Double.parseDouble(data[2]));
                employees.add(employee);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading employee data: " + e.getMessage());
        }
    }

    private static void saveEmployeeData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Employee employee : employees) {
                writer.write(employee.getId() + "," + employee.getName() + "," + employee.getSalary());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving employee data: " + e.getMessage());
        }
    }
}