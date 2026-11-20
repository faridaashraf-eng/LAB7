/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package member3;

import member1.Department;
import member2.DepartmentManager;
import member1.Employee;
import member2.EmployeeManager;
import member2.Validator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Psyche
 */
public class DepartmentsPanel extends JPanel {

    private EmployeeManager employeeManager;
    private DepartmentManager departmentManager;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField idField;
    private JTextField nameField;

    public DepartmentsPanel(EmployeeManager employeeManager, DepartmentManager departmentManager) {
        this.employeeManager = employeeManager;
        this.departmentManager = departmentManager;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tableModel = new DefaultTableModel(new String[]{"Department ID", "Department Name", "Number of Employees"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                idField.setText(tableModel.getValueAt(row, 0).toString());
                nameField.setText(tableModel.getValueAt(row, 1).toString());
            }
        });

        idField = new JTextField(12);
        nameField = new JTextField(12);
        JPanel fieldsPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        fieldsPanel.setOpaque(false);
        fieldsPanel.add(new JLabel("Department ID:"));
        fieldsPanel.add(idField);
        fieldsPanel.add(new JLabel("Department Name:"));
        fieldsPanel.add(nameField);

        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton clearButton = new JButton("Clear");
        addButton.addActionListener(e -> addDepartment());
        deleteButton.addActionListener(e -> deleteDepartment());
        clearButton.addActionListener(e -> clearForm());
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(addButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(clearButton);

        JPanel rightPanel = new JPanel(new BorderLayout(5, 10));
        rightPanel.setBackground(new Color(225, 235, 245));
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Department Details"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        rightPanel.setPreferredSize(new Dimension(330, 0));
        rightPanel.add(fieldsPanel, BorderLayout.NORTH);
        rightPanel.add(buttonsPanel, BorderLayout.CENTER);

        JPanel rightHolder = new JPanel(new BorderLayout());
        rightHolder.add(rightPanel, BorderLayout.NORTH);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(rightHolder, BorderLayout.EAST);
    }

    private void addDepartment() {
        try {
            int id = Validator.checkPositiveInt(idField.getText(), "Department ID");
            String name = Validator.checkText(nameField.getText(), "Department Name");
            departmentManager.addDepartment(id, name);
            JOptionPane.showMessageDialog(this, "Department added successfully!");
            refresh();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteDepartment() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a department first.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this department?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (choice != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            departmentManager.deleteDepartment(id, employeeManager.getEmployees());
            JOptionPane.showMessageDialog(this, "Department deleted successfully!");
            refresh();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        table.clearSelection();
        idField.setText(String.valueOf(departmentManager.getNextId()));
        nameField.setText("");
    }

    public void refresh() {
        tableModel.setRowCount(0);
        for (Department d : departmentManager.getDepartments()) {
            int count = 0;
            for (Employee e : employeeManager.getEmployees()) {
                if (e.getDepartment().getId() == d.getId()) {
                    count++;
                }
            }
            tableModel.addRow(new Object[]{d.getId(), d.getName(), count});
        }
        clearForm();
    }
}

