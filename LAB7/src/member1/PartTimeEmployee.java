/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package member1;

/**
 *
 * @author Psyche
 */
public class PartTimeEmployee extends Employee {

    private double hourlyRate;
    private double hoursWorked;

    public PartTimeEmployee(int id, String name, int age, Department department, String position,
            double hourlyRate, double hoursWorked) {
        super(id, name, age, department, position);
        setHourlyRate(hourlyRate);
        setHoursWorked(hoursWorked);
    }

    @Override
    public double calculateSalary() {
        return hourlyRate * hoursWorked;
    }

    @Override
    public String getType() {
        return "Part-Time";
    }

    @Override
    public String getSalaryInfo() {
        return "Rate: " + hourlyRate + ", Hours: " + hoursWorked;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        if (hourlyRate < 0) {
            throw new IllegalArgumentException("Hourly rate cannot be negative.");
        }
        this.hourlyRate = hourlyRate;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        if (hoursWorked < 0 || hoursWorked > 744) {
            throw new IllegalArgumentException("Hours worked must be between 0 and 744 hours per month.");
        }
        this.hoursWorked = hoursWorked;
    }
}
