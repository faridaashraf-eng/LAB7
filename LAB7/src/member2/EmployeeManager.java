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
import java.io.IOException;
import java.util.ArrayList;

public class EmployeeManager {

    private ArrayList<Employee> employees;
    private FileHandler fileHandler;

    public EmployeeManager(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        this.employees = new ArrayList<>();
    }

    public void loadEmployees(ArrayList<Department> departments) throws IOException {
        employees = fileHandler.loadEmployees(departments);
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public Employee searchById(int id) {
        for (Employee e : employees) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public ArrayList<Employee> searchByName(String name) {
        ArrayList<Employee> result = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(e);
            }
        }
        return result;
    }

    public ArrayList<Employee> searchByDepartment(String departmentName) {
        ArrayList<Employee> result = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getDepartment().getName().equalsIgnoreCase(departmentName.trim())) {
                result.add(e);
            }
        }
        return result;
    }

    public void addEmployee(Employee employee) throws IOException {
        if (searchById(employee.getId()) != null) {
            throw new IllegalArgumentException("Employee ID " + employee.getId() + " already exists.");
        }
        employees.add(employee);
        try {
            fileHandler.saveEmployees(employees);
        } catch (IOException e) {
            employees.remove(employee);
            throw e;
        }
    }

    public void updateEmployee(int oldId, Employee updatedEmployee) throws IOException {
        Employee oldEmployee = searchById(oldId);
        if (oldEmployee == null) {
            throw new IllegalArgumentException("Employee not found.");
        }
        if (updatedEmployee.getId() != oldId && searchById(updatedEmployee.getId()) != null) {
            throw new IllegalArgumentException("Employee ID " + updatedEmployee.getId() + " already exists.");
        }
        int index = employees.indexOf(oldEmployee);
        employees.set(index, updatedEmployee);
        try {
            fileHandler.saveEmployees(employees);
        } catch (IOException e) {
            employees.set(index, oldEmployee);
            throw e;
        }
    }

    public void deleteEmployee(int id) throws IOException {
        Employee employee = searchById(id);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found.");
        }
        int index = employees.indexOf(employee);
        employees.remove(employee);
        try {
            fileHandler.saveEmployees(employees);
        } catch (IOException e) {
            employees.add(index, employee);
            throw e;
        }
    }

    public int getNextId() {
        int max = 0;
        for (Employee e : employees) {
            if (e.getId() > max) {
                max = e.getId();
            }
        }
        return max + 1;
    }

    public double getTotalSalaries() {
        double total = 0;
        for (Employee e : employees) {
            total += e.calculateSalary();
        }
        return total;
    }
}
