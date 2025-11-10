package model;

import java.text.DecimalFormat;

public class Payroll {
  public String workerName;
  public float annualGrossSalary;
  public int paymentsPerYear;
  public float extras;


  public Payroll() {
    this.workerName = "";
    this.annualGrossSalary = 0;
    this.paymentsPerYear = 0;
    this.extras = 0;
  }

  public Payroll(float annualGrossSalary, String workerName, int paymentsPerYear, float extras) {
    this.annualGrossSalary = annualGrossSalary;
    this.workerName = workerName;
    this.paymentsPerYear = paymentsPerYear;
    this.extras = extras;
  }


  public double paymentsPerMonth(float annualGrossSalary, int paymentsPerYear) {
    return (Math.round((annualGrossSalary / paymentsPerYear) * 100) / 100d);
  }

  public double monthlyGrossSalary(float annualGrossSalary, int paymentsPerYear, float extras) {
    return paymentsPerMonth(annualGrossSalary, paymentsPerYear) + (extras);
  }


  public double calculateSocialSecurity(int totalIncome, int category) {
    float percentage;

    if (category >= 1 && category <= 4) percentage = 6.35f;
    else if (category >= 5 && category <= 7) percentage = 6.40f;
    else if (category >= 8 && category <= 10) percentage = 6.45f;
    //else throw new IllegalArgumentException("Categoría fuera de rango");
    else percentage = 100; // keep income as it is without deductions

    return totalIncome * (percentage / 100);
  }
}
