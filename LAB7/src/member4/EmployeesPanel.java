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
import member2.DepartmentManager;
import member1.Employee;
import member2.EmployeeManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class EmployeesPanel extends JPanel {

    private EmployeeManager employeeManager;
    private DepartmentManager departmentManager;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> searchTypeBox;
    private JTextField searchField;
    private JComboBox<String> filterBox;
    private EmployeeForm form;
    private ArrayList<Employee> shownEmployees;
    private int selectedId = -1;

    public EmployeesPanel(EmployeeManager employeeManager, DepartmentManager departmentManager) {
        this.employeeManager = employeeManager;
        this.departmentManager = departmentManager;
        this.shownEmployees = new ArrayList<>();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        searchTypeBox = new JComboBox<>(new String[]{"ID", "Name", "Department"});
        searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");
        JButton showAllButton = new JButton("Show All");
        filterBox = new JComboBox<>();
        JButton filterButton = new JButton("Filter");

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search by:"));
        searchPanel.add(searchTypeBox);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);
        searchPanel.add(new JLabel("    Filter by department:"));
        searchPanel.add(filterBox);
        searchPanel.add(filterButton);

        String[] columns = {"ID", "Name", "Age", "Department", "Position", "Type", "Salary Info", "Monthly Salary"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(24);
        table.getTableHeader().setReorderingAllowed(false);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedEmployee();
            }
        });

        form = new EmployeeForm();
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton clearButton = new JButton("Clear");
        JPanel formButtons = new JPanel(new FlowLayout());
        formButtons.setOpaque(false);
        formButtons.add(updateButton);
        formButtons.add(deleteButton);
        formButtons.add(clearButton);

        JPanel editPanel = new JPanel(new BorderLayout(5, 5));
        editPanel.setBackground(new Color(225, 235, 245));
        editPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Selected Employee"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        editPanel.setPreferredSize(new Dimension(330, 0));
        JPanel formTop = new JPanel(new BorderLayout(5, 10));
        formTop.setOpaque(false);
        formTop.add(new JLabel("Select an employee from the table to edit it."), BorderLayout.NORTH);
        formTop.add(form, BorderLayout.CENTER);
        formTop.add(formButtons, BorderLayout.SOUTH);
        editPanel.add(formTop, BorderLayout.NORTH);

        searchButton.addActionListener(e -> search());
        searchField.addActionListener(e -> search());
        showAllButton.addActionListener(e -> showAll());
        filterButton.addActionListener(e -> filter());
        updateButton.addActionListener(e -> updateEmployee());
        deleteButton.addActionListener(e -> deleteEmployee());
        clearButton.addActionListener(e -> clearSelection());

        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(editPanel, BorderLayout.EAST);
    }

    private void fillTable(ArrayList<Employee> list) {
        shownEmployees = list;
        tableModel.setRowCount(0);
        for (Employee e : list) {
            tableModel.addRow(new Object[]{
                e.getId(),
                e.getName(),
                e.getAge(),
                e.getDepartment().getName(),
                e.getPosition(),
                e.getType(),
                e.getSalaryInfo(),
                String.format("%.2f", e.calculateSalary())
            });
        }
    }

    private void search() {
        String text = searchField.getText().trim();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a value to search for.");
            return;
        }

        ArrayList<Employee> result = new ArrayList<>();
        String searchType = (String) searchTypeBox.getSelectedItem();

        if (searchType.equals("ID")) {
            try {
                int id = Integer.parseInt(text);
                Employee e = employeeManager.searchById(id);
                if (e != null) {
                    result.add(e);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else if (searchType.equals("Name")) {
            result = employeeManager.searchByName(text);
        } else {
            result = employeeManager.searchByDepartment(text);
        }

        clearSelection();
        fillTable(result);
        if (result.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No matching employee found.");
        }
    }

    private void filter() {
        String department = (String) filterBox.getSelectedItem();
        clearSelection();
        if (department == null || department.equals("All")) {
            fillTable(employeeManager.getEmployees());
        } else {
            fillTable(employeeManager.searchByDepartment(department));
        }
    }

    private void showAll() {
        searchField.setText("");
        filterBox.setSelectedIndex(0);
        clearSelection();
        fillTable(employeeManager.getEmployees());
    }

    private void loadSelectedEmployee() {
        int row = table.getSelectedRow();
        if (row == -1) {
            return;
        }
        Employee employee = shownEmployees.get(row);
        selectedId = employee.getId();
        form.setEmployee(employee);
    }

    private void updateEmployee() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee first.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Employee updated = form.getEmployee();
            employeeManager.updateEmployee(selectedId, updated);
            JOptionPane.showMessageDialog(this, "Employee updated successfully!");
            showAll();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteEmployee() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee first.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete employee " + selectedId + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (choice != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            employeeManager.deleteEmployee(selectedId);
            JOptionPane.showMessageDialog(this, "Employee deleted successfully!");
            showAll();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearSelection() {
        selectedId = -1;
        table.clearSelection();
        form.clearForm();
    }

    public void refresh() {
        form.setDepartments(departmentManager.getDepartments());
        filterBox.removeAllItems();
        filterBox.addItem("All");
        for (Department d : departmentManager.getDepartments()) {
            filterBox.addItem(d.getName());
        }
        showAll();
    }
}
