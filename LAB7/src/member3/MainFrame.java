package member3;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import member3.LoginFrame;
import member3.HomePanel;
import member3.DepartmentsPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Psyche
 */
public class MainFrame extends JFrame {

    private EmployeeManager employeeManager;
    private DepartmentManager departmentManager;
    private JTabbedPane tabs;
    private HomePanel homePanel;
    private AddEmployeePanel addEmployeePanel;
    private EmployeesPanel employeesPanel;
    private DepartmentsPanel departmentsPanel;

    public MainFrame(EmployeeManager employeeManager, DepartmentManager departmentManager) {
        this.employeeManager = employeeManager;
        this.departmentManager = departmentManager;

        setTitle("Employee Management System");
        setSize(1150, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(40, 70, 120));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        JLabel titleLabel = new JLabel("Employee Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(logoutButton, BorderLayout.EAST);

        homePanel = new HomePanel(this);
        addEmployeePanel = new AddEmployeePanel(employeeManager, departmentManager);
        employeesPanel = new EmployeesPanel(employeeManager, departmentManager);
        departmentsPanel = new DepartmentsPanel(employeeManager, departmentManager);

        tabs = new JTabbedPane();
        tabs.addTab("Home", homePanel);
        tabs.addTab("Add Employee", addEmployeePanel);
        tabs.addTab("View / Search / Update / Delete", employeesPanel);
        tabs.addTab("Departments", departmentsPanel);
        tabs.addChangeListener(e -> refreshCurrentTab());

        add(topPanel, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);

        refreshCurrentTab();
    }

    private void refreshCurrentTab() {
        int index = tabs.getSelectedIndex();
        if (index == 0) {
            homePanel.refresh();
        } else if (index == 1) {
            addEmployeePanel.refresh();
        } else if (index == 2) {
            employeesPanel.refresh();
        } else if (index == 3) {
            departmentsPanel.refresh();
        }
    }

    public void openTab(int index) {
        tabs.setSelectedIndex(index);
    }

    public EmployeeManager getEmployeeManager() {
        return employeeManager;
    }

    public DepartmentManager getDepartmentManager() {
        return departmentManager;
    }

    public void logout() {
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            new LoginFrame(employeeManager, departmentManager).setVisible(true);
            dispose();
        }
    }
}
