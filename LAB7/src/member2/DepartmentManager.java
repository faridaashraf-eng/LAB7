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

public class DepartmentManager {

    private ArrayList<Department> departments;
    private FileHandler fileHandler;

    public DepartmentManager(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        this.departments = new ArrayList<>();
    }

    public void loadDepartments() throws IOException {
        departments = fileHandler.loadDepartments();
        if (departments.isEmpty()) {
            departments.add(new Department(1, "IT"));
            departments.add(new Department(2, "Human Resources"));
            departments.add(new Department(3, "Finance"));
            departments.add(new Department(4, "Marketing"));
            departments.add(new Department(5, "Sales"));
            fileHandler.saveDepartments(departments);
        }
    }

    public ArrayList<Department> getDepartments() {
        return departments;
    }

    public Department findById(int id) {
        for (Department d : departments) {
            if (d.getId() == id) {
                return d;
            }
        }
        return null;
    }

    public boolean nameExists(String name) {
        for (Department d : departments) {
            if (d.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public void addDepartment(int id, String name) throws IOException {
        if (findById(id) != null) {
            throw new IllegalArgumentException("Department ID " + id + " already exists.");
        }
        if (nameExists(name)) {
            throw new IllegalArgumentException("Department \"" + name + "\" already exists.");
        }
        Department department = new Department(id, name);
        departments.add(department);
        try {
            fileHandler.saveDepartments(departments);
        } catch (IOException e) {
            departments.remove(department);
            throw e;
        }
    }

    public void deleteDepartment(int id, ArrayList<Employee> employees) throws IOException {
        Department department = findById(id);
        if (department == null) {
            throw new IllegalArgumentException("Department not found.");
        }
        for (Employee e : employees) {
            if (e.getDepartment().getId() == id) {
                throw new IllegalArgumentException("Cannot delete this department because it has employees.");
            }
        }
        departments.remove(department);
        try {
            fileHandler.saveDepartments(departments);
        } catch (IOException e) {
            departments.add(department);
            throw e;
        }
    }

    public int getNextId() {
        int max = 0;
        for (Department d : departments) {
            if (d.getId() > max) {
                max = d.getId();
            }
        }
        return max + 1;
    }
}
