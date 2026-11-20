/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package member1;

/**
 *
 * @author Psyche
 */
public class FullTimeEmployee extends Employee {

    private double basicSalary;
    private double bonus;

    public FullTimeEmployee(int id, String name, int age, Department department, String position,
            double basicSalary, double bonus) {
        super(id, name, age, department, position);
        setBasicSalary(basicSalary);
        setBonus(bonus);
    }

    @Override
    public double calculateSalary() {
        return basicSalary + bonus;
    }

    @Override
    public String getType() {
        return "Full-Time";
    }

    @Override
    public String getSalaryInfo() {
        return "Basic: " + basicSalary + ", Bonus: " + bonus;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        if (basicSalary < 0) {
            throw new IllegalArgumentException("Basic salary cannot be negative.");
        }
        this.basicSalary = basicSalary;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        if (bonus < 0) {
            throw new IllegalArgumentException("Bonus cannot be negative.");
        }
        this.bonus = bonus;
    }
}
