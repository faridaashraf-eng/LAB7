/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package member3;

import member2.DepartmentManager;
import member2.EmployeeManager;
import member2.FileHandler;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Psyche
 */
public class EmployeeManagementSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Could not set Nimbus look and feel.");
        }

        FileHandler fileHandler = new FileHandler();
        DepartmentManager departmentManager = new DepartmentManager(fileHandler);
        EmployeeManager employeeManager = new EmployeeManager(fileHandler);

        try {
            departmentManager.loadDepartments();
            employeeManager.loadEmployees(departmentManager.getDepartments());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage()
                    + "\nThe program will start with empty data.", "File Error", JOptionPane.ERROR_MESSAGE);
        }

        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame(employeeManager, departmentManager);
            loginFrame.setVisible(true);
        });

    }
    
}
