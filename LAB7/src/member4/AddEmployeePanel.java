/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package member4;

/**
 *
 * @author Jana
 */
import member2.DepartmentManager;
import member1.Employee;
import member2.EmployeeManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AddEmployeePanel extends JPanel {

    private EmployeeManager employeeManager;
    private DepartmentManager departmentManager;
    private EmployeeForm form;

    public AddEmployeePanel(EmployeeManager employeeManager, DepartmentManager departmentManager) {
        this.employeeManager = employeeManager;
        this.departmentManager = departmentManager;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel titleLabel = new JLabel("Add New Employee");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        form = new EmployeeForm();
        JPanel formHolder = new JPanel(new BorderLayout());
        formHolder.setBackground(new Color(225, 235, 245));
        formHolder.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Employee Information"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        formHolder.add(form, BorderLayout.NORTH);

        JButton addButton = new JButton("Add Employee");
        JButton clearButton = new JButton("Clear");
        addButton.addActionListener(e -> addEmployee());
        clearButton.addActionListener(e -> refresh());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(clearButton);
        buttonPanel.add(addButton);

        formHolder.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.setOpaque(false);
        JPanel center = new JPanel(new BorderLayout());
        JPanel left = new JPanel(new BorderLayout());
        left.add(formHolder, BorderLayout.NORTH);
        center.add(left, BorderLayout.WEST);

        add(titleLabel, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }

    private void addEmployee() {
        try {
            Employee employee = form.getEmployee();
            employeeManager.addEmployee(employee);
            JOptionPane.showMessageDialog(this, "Employee added successfully!");
            refresh();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refresh() {
        form.setDepartments(departmentManager.getDepartments());
        form.clearForm();
        form.setId(employeeManager.getNextId());
    }
}
