package model;

public class Payroll {
  public String payrollCode;
  Worker worker;
  public float annualGrossSalary; // worker.getTotalIncome()
  public float netSalary; // annualGrossSalary - deductions
  public float irpf;
  public float sgs;


  public Payroll() {
    this.payrollCode = "";
    this.worker = new Worker();
    this.annualGrossSalary = 0;
  }

  public Payroll(String payrollCode, Worker worker) {
    this.payrollCode = payrollCode;
    this.worker = worker;
    this.annualGrossSalary = worker.getTotalIncome();
  }

  public String getPayrollCode() { return payrollCode; }
  public Worker getWorker() { return worker;}
  public float getTotalIncome() { return annualGrossSalary;}



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
