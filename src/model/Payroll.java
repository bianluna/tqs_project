package model;

public class Payroll {
  public String workerName;
  public float annualGrossSalary;
  public int paymentsPerYear;

  public Payroll() {
    this.workerName = "";
    this.annualGrossSalary = 0;
    this.paymentsPerYear = 0;
  }

  public Payroll(float annualGrossSalary, String workerName, int paymentsPerYear) {
    this.annualGrossSalary = annualGrossSalary;
    this.workerName = workerName;
    this.paymentsPerYear = paymentsPerYear;
  }






}
