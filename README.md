# Lab 7 - Employee Management System

## Group Members

* Farida Ashraf - 9442
* Jana Mohamed - 9440
* Toka Wessam - 9483
* Mohamed Tarek - 9607

Group: 1

## Description

This is a desktop application made with Java Swing to manage the employees and departments of a company.

After logging in, the admin can:

* Add Full-Time and Part-Time employees
* View all employees in a table with their monthly salary
* Search employees by ID, name or department
* Filter employees by department
* Update and delete employees
* Add and delete departments
* Logout

Salary calculation:

* Full-Time: Monthly Salary = Basic Salary + Bonus
* Part-Time: Monthly Salary = Hourly Rate x Hours Worked

The project is divided into 4 packages (one for each team member):

* `member1` (Model): Employee (abstract), FullTimeEmployee, PartTimeEmployee, Department
* `member2` (Backend): EmployeeManager, DepartmentManager, Validator, FileHandler
* `member3` (GUI): EmployeeManagementSystem (main class), LoginFrame, MainFrame, HomePanel, DepartmentsPanel
* `member4` (GUI): EmployeeForm, AddEmployeePanel, EmployeesPanel

## How to Run

Using NetBeans:

1. File > Open Project and choose the `EmployeeManagementSystem` folder.
2. Right click the project > Run (or press F6).

Using the command line (from inside the `EmployeeManagementSystem` folder):

```
javac -d out src/main/java/member1/\\\*.java src/main/java/member2/\\\*.java src/main/java/member3/\\\*.java src/main/java/member4/\\\*.java
java -cp out member3.EmployeeManagementSystem
```

## Login

* Username: admin
* Password: admin123

## Data Files

The data is saved in two CSV files in the project folder (the folder the program runs from):

* `departments.csv`: each line is `id,name`
* `employees.csv`: each line is `id,type,name,age,departmentId,position,value1,value2`

  * For FullTime: value1 = basic salary, value2 = bonus
  * For PartTime: value1 = hourly rate, value2 = hours worked

The files are loaded when the program starts and saved after every add, update or delete.
If the files do not exist (first run), the program starts with no employees and creates 5 default departments (IT, Human Resources, Finance, Marketing, Sales).

