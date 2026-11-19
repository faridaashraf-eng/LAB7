/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package member2;

/**
 *
 * @author Jana
 */
import member1.Department;
import member1.Employee;
import member1.FullTimeEmployee;
import member1.PartTimeEmployee;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileHandler {

    private final String employeesFile = "employees.csv";
    private final String departmentsFile = "departments.csv";

    public ArrayList<Department> loadDepartments() throws IOException {
        ArrayList<Department> departments = new ArrayList<>();
        File file = new File(departmentsFile);
        if (!file.exists()) {
            return departments;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 2) {
                    continue;
                }
                try {
                    int id = Integer.parseInt(parts[0].trim());
                    departments.add(new Department(id, parts[1].trim()));
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid department line skipped: " + line);
                }
            }
        }
        return departments;
    }

    public void saveDepartments(ArrayList<Department> departments) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(departmentsFile))) {
            for (Department d : departments) {
                writer.println(d.getId() + "," + d.getName());
            }
        }
    }

    public ArrayList<Employee> loadEmployees(ArrayList<Department> departments) throws IOException {
        ArrayList<Employee> employees = new ArrayList<>();
        File file = new File(employeesFile);
        if (!file.exists()) {
            return employees;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 8) {
                    continue;
                }
                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String type = parts[1].trim();
                    String name = parts[2].trim();
                    int age = Integer.parseInt(parts[3].trim());
                    int departmentId = Integer.parseInt(parts[4].trim());
                    String position = parts[5].trim();
                    double value1 = Double.parseDouble(parts[6].trim());
                    double value2 = Double.parseDouble(parts[7].trim());

                    Department department = null;
                    for (Department d : departments) {
                        if (d.getId() == departmentId) {
                            department = d;
                        }
                    }
                    if (department == null) {
                        System.out.println("Employee with unknown department skipped: " + line);
                        continue;
                    }

                    if (type.equals("FullTime")) {
                        employees.add(new FullTimeEmployee(id, name, age, department, position, value1, value2));
                    } else if (type.equals("PartTime")) {
                        employees.add(new PartTimeEmployee(id, name, age, department, position, value1, value2));
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid employee line skipped: " + line);
                }
            }
        }
        return employees;
    }

    public void saveEmployees(ArrayList<Employee> employees) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(employeesFile))) {
            for (Employee e : employees) {
                String line = e.getId() + ",";
                if (e instanceof FullTimeEmployee) {
                    FullTimeEmployee f = (FullTimeEmployee) e;
                    line += "FullTime," + e.getName() + "," + e.getAge() + "," + e.getDepartment().getId() + ","
                            + e.getPosition() + "," + f.getBasicSalary() + "," + f.getBonus();
                } else if (e instanceof PartTimeEmployee) {
                    PartTimeEmployee p = (PartTimeEmployee) e;
                    line += "PartTime," + e.getName() + "," + e.getAge() + "," + e.getDepartment().getId() + ","
                            + e.getPosition() + "," + p.getHourlyRate() + "," + p.getHoursWorked();
                }
                writer.println(line);
            }
        }
    }
}
