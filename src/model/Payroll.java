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

  public void setWorker(Worker worker) { this.worker = worker; }
  public String getPayrollCode() { return payrollCode; }
  public Worker getWorker() { return worker;}
  public float getTotalIncome() { return annualGrossSalary;}



  public double paymentsPerMonth(float annualGrossSalary, int paymentsPerYear) {
    return (Math.round((annualGrossSalary / paymentsPerYear) * 100) / 100d);
  }

  public double monthlyGrossSalary(float annualGrossSalary, int paymentsPerYear, float extras) {
    return paymentsPerMonth(annualGrossSalary, paymentsPerYear) + (extras);
  }


  public double calculateSocialSecurity() {
    float percentage;

    if (worker.getCategory() >= 1 && worker.getCategory() <= 4) percentage = 6.35f;
    else if (worker.getCategory() >= 5 && worker.getCategory() <= 7) percentage = 6.40f;
    else if (worker.getCategory() >= 8 && worker.getCategory() <= 11) percentage = 6.45f;
    //else throw new IllegalArgumentException("Categoría fuera de rango");
    else percentage = 100; // keep income as it is without deductions
    sgs = Math.round(worker.getTotalIncome() * (percentage / 100));

    return sgs;
  }
}
