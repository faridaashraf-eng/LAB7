/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package member3;

import member1.Employee;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Psyche
 */
public class HomePanel extends JPanel {

    private MainFrame mainFrame;
    private JLabel totalLabel;
    private JLabel fullTimeLabel;
    private JLabel partTimeLabel;
    private JLabel departmentsLabel;
    private JLabel salariesLabel;

    public HomePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(10, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel welcomeLabel = new JLabel("Welcome, Admin!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 26));
        JLabel infoLabel = new JLabel("Use the tabs or the buttons below to manage employees and departments.");

        JPanel welcomePanel = new JPanel(new GridLayout(2, 1));
        welcomePanel.add(welcomeLabel);
        welcomePanel.add(infoLabel);

        totalLabel = new JLabel();
        fullTimeLabel = new JLabel();
        partTimeLabel = new JLabel();
        departmentsLabel = new JLabel();
        salariesLabel = new JLabel();

        JPanel statsPanel = new JPanel(new GridLayout(5, 1, 5, 8));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Summary"));
        statsPanel.setBackground(new Color(225, 235, 245));
        Font statsFont = new Font("Arial", Font.PLAIN, 16);
        totalLabel.setFont(statsFont);
        fullTimeLabel.setFont(statsFont);
        partTimeLabel.setFont(statsFont);
        departmentsLabel.setFont(statsFont);
        salariesLabel.setFont(statsFont);
        statsPanel.add(totalLabel);
        statsPanel.add(fullTimeLabel);
        statsPanel.add(partTimeLabel);
        statsPanel.add(departmentsLabel);
        statsPanel.add(salariesLabel);

        JButton addButton = new JButton("Add Employee");
        JButton viewButton = new JButton("View Employees");
        JButton departmentsButton = new JButton("Departments");
        JButton logoutButton = new JButton("Logout");
        addButton.addActionListener(e -> mainFrame.openTab(1));
        viewButton.addActionListener(e -> mainFrame.openTab(2));
        departmentsButton.addActionListener(e -> mainFrame.openTab(3));
        logoutButton.addActionListener(e -> mainFrame.logout());

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonsPanel.add(addButton);
        buttonsPanel.add(viewButton);
        buttonsPanel.add(departmentsButton);
        buttonsPanel.add(logoutButton);

        add(welcomePanel, BorderLayout.NORTH);
        JPanel centerPanel = new JPanel(new BorderLayout(10, 15));
        centerPanel.add(statsPanel, BorderLayout.NORTH);
        centerPanel.add(buttonsPanel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
    }

    public void refresh() {
        int fullTime = 0;
        int partTime = 0;
        for (Employee e : mainFrame.getEmployeeManager().getEmployees()) {
            if (e.getType().equals("Full-Time")) {
                fullTime++;
            } else {
                partTime++;
            }
        }
        totalLabel.setText("Total Employees: " + mainFrame.getEmployeeManager().getEmployees().size());
        fullTimeLabel.setText("Full-Time Employees: " + fullTime);
        partTimeLabel.setText("Part-Time Employees: " + partTime);
        departmentsLabel.setText("Departments: " + mainFrame.getDepartmentManager().getDepartments().size());
        salariesLabel.setText("Total Monthly Salaries: " + String.format("%.2f", mainFrame.getEmployeeManager().getTotalSalaries()) + " EGP");
    }
}
