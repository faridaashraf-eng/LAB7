/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package member1;
package com.mycompany.employeemanagementsystem.model;

/**
 *
 * @author Psyche
 */
public abstract class Employee {

    private int id;
    private String name;
    private int age;
    private Department department;
    private String position;

    public Employee(int id, String name, int age, Department department, String position) {
        setId(id);
        setName(name);
        setAge(age);
        setDepartment(department);
        setPosition(position);
    }

    public abstract double calculateSalary();

    public abstract String getType();

    public abstract String getSalaryInfo();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Employee ID must be a positive number.");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name.trim();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age <= 0 || age > 120) {
            throw new IllegalArgumentException("Age must be between 1 and 120.");
        }
        this.age = age;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        if (department == null) {
            throw new IllegalArgumentException("Please select a valid department.");
        }
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        if (position == null || position.trim().isEmpty()) {
            throw new IllegalArgumentException("Position cannot be empty.");
        }
        this.position = position.trim();
    }
}

    
}
