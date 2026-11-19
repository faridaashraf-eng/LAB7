/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package member4;

/**
 *
 * @author Jana
 */
import member1.Department;
import member1.Employee;
import member1.FullTimeEmployee;
import member1.PartTimeEmployee;
import member2.Validator;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EmployeeForm extends JPanel {

    private JTextField idField;
    private JTextField nameField;
    private JTextField ageField;
    private JComboBox<Department> departmentBox;
    private JTextField positionField;
    private JComboBox<String> typeBox;
    private JTextField basicSalaryField;
    private JTextField bonusField;
    private JTextField hourlyRateField;
    private JTextField hoursField;
    private JPanel salaryPanel;
    private CardLayout cardLayout;

    public EmployeeForm() {
        setLayout(new BorderLayout(0, 8));
        setOpaque(false);

        idField = new JTextField(15);
        nameField = new JTextField(15);
        ageField = new JTextField(15);
        departmentBox = new JComboBox<>();
        positionField = new JTextField(15);
        typeBox = new JComboBox<>(new String[]{"Full-Time", "Part-Time"});

        JPanel infoPanel = new JPanel(new GridLayout(6, 2, 8, 8));
        infoPanel.setOpaque(false);
        infoPanel.add(new JLabel("Employee ID:"));
        infoPanel.add(idField);
        infoPanel.add(new JLabel("Full Name:"));
        infoPanel.add(nameField);
        infoPanel.add(new JLabel("Age:"));
        infoPanel.add(ageField);
        infoPanel.add(new JLabel("Department:"));
        infoPanel.add(departmentBox);
        infoPanel.add(new JLabel("Position:"));
        infoPanel.add(positionField);
        infoPanel.add(new JLabel("Employee Type:"));
        infoPanel.add(typeBox);

        basicSalaryField = new JTextField(15);
        bonusField = new JTextField(15);
        JPanel fullTimePanel = new JPanel(new GridLayout(2, 2, 8, 8));
        fullTimePanel.setOpaque(false);
        fullTimePanel.add(new JLabel("Basic Salary:"));
        fullTimePanel.add(basicSalaryField);
        fullTimePanel.add(new JLabel("Bonus:"));
        fullTimePanel.add(bonusField);

        hourlyRateField = new JTextField(15);
        hoursField = new JTextField(15);
        JPanel partTimePanel = new JPanel(new GridLayout(2, 2, 8, 8));
        partTimePanel.setOpaque(false);
        partTimePanel.add(new JLabel("Hourly Rate:"));
        partTimePanel.add(hourlyRateField);
        partTimePanel.add(new JLabel("Hours Worked:"));
        partTimePanel.add(hoursField);

        cardLayout = new CardLayout();
        salaryPanel = new JPanel(cardLayout);
        salaryPanel.setOpaque(false);
        salaryPanel.add(fullTimePanel, "Full-Time");
        salaryPanel.add(partTimePanel, "Part-Time");

        typeBox.addActionListener(e -> cardLayout.show(salaryPanel, (String) typeBox.getSelectedItem()));

        add(infoPanel, BorderLayout.NORTH);
        add(salaryPanel, BorderLayout.CENTER);
    }

    public void setDepartments(ArrayList<Department> departments) {
        departmentBox.removeAllItems();
        for (Department d : departments) {
            departmentBox.addItem(d);
        }
    }

    public Employee getEmployee() {
        int id = Validator.checkPositiveInt(idField.getText(), "Employee ID");
        String name = Validator.checkText(nameField.getText(), "Full Name");
        int age = Validator.checkPositiveInt(ageField.getText(), "Age");
        if (age > 120) {
            throw new IllegalArgumentException("Age must be between 1 and 120.");
        }
        String position = Validator.checkText(positionField.getText(), "Position");

        Department department = (Department) departmentBox.getSelectedItem();
        if (department == null) {
            throw new IllegalArgumentException("Please select a valid department.");
        }

        String type = (String) typeBox.getSelectedItem();
        if ("Full-Time".equals(type)) {
            double basicSalary = Validator.checkNonNegativeDouble(basicSalaryField.getText(), "Basic Salary");
            double bonus = Validator.checkNonNegativeDouble(bonusField.getText(), "Bonus");
            return new FullTimeEmployee(id, name, age, department, position, basicSalary, bonus);
        } else if ("Part-Time".equals(type)) {
            double hourlyRate = Validator.checkNonNegativeDouble(hourlyRateField.getText(), "Hourly Rate");
            double hours = Validator.checkNonNegativeDouble(hoursField.getText(), "Hours Worked");
            if (hours > 744) {
                throw new IllegalArgumentException("Hours worked cannot be more than 744 hours per month.");
            }
            return new PartTimeEmployee(id, name, age, department, position, hourlyRate, hours);
        } else {
            throw new IllegalArgumentException("Please select a valid employee type.");
        }
    }

    public void setEmployee(Employee employee) {
        clearForm();
        idField.setText(String.valueOf(employee.getId()));
        nameField.setText(employee.getName());
        ageField.setText(String.valueOf(employee.getAge()));
        positionField.setText(employee.getPosition());

        for (int i = 0; i < departmentBox.getItemCount(); i++) {
            if (departmentBox.getItemAt(i).getId() == employee.getDepartment().getId()) {
                departmentBox.setSelectedIndex(i);
            }
        }

        if (employee instanceof FullTimeEmployee) {
            FullTimeEmployee f = (FullTimeEmployee) employee;
            typeBox.setSelectedItem("Full-Time");
            basicSalaryField.setText(String.valueOf(f.getBasicSalary()));
            bonusField.setText(String.valueOf(f.getBonus()));
        } else if (employee instanceof PartTimeEmployee) {
            PartTimeEmployee p = (PartTimeEmployee) employee;
            typeBox.setSelectedItem("Part-Time");
            hourlyRateField.setText(String.valueOf(p.getHourlyRate()));
            hoursField.setText(String.valueOf(p.getHoursWorked()));
        }
    }

    public void setId(int id) {
        idField.setText(String.valueOf(id));
    }

    public void clearForm() {
        idField.setText("");
        nameField.setText("");
        ageField.setText("");
        positionField.setText("");
        basicSalaryField.setText("");
        bonusField.setText("");
        hourlyRateField.setText("");
        hoursField.setText("");
        typeBox.setSelectedIndex(0);
        if (departmentBox.getItemCount() > 0) {
            departmentBox.setSelectedIndex(0);
        }
    }
}
