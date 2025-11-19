package model;

public class Payroll {
  public String payrollCode;
  Worker worker;
  public float annualGrossSalary; // worker.getTotalIncome()
  public double netSalary; // annualGrossSalary - deductions
  public double irpf;
  public double sgs;


  public Payroll() {
    this.payrollCode = "";
    this.worker = new Worker();
    this.annualGrossSalary = 0;
    this.netSalary = 0;
    this.irpf = 0;
    this.sgs = 0;
  }

  public Payroll(String payrollCode, Worker worker) {
    this.payrollCode = payrollCode;
    this.worker = worker;
    this.annualGrossSalary = worker.getTotalIncome();
  }

  public void setWorker(Worker worker) { this.worker = worker; this.annualGrossSalary = worker.getTotalIncome(); }
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
    else percentage = 100; // keep income as it is without deductions
    sgs = Math.round(worker.getTotalIncome() * (percentage / 100));

    return sgs;
  }

  public double calculateIrpf() {
    if (worker == null) {
      throw new IllegalStateException("Payroll: no worker assigned");
    }

    // Obtener salario bruto anual (Worker almacena totalIncome como float)
    double income = worker.getTotalIncome();

    // Determinar tasa base por tramos
    double baseRate;
    if (income <= 12_450.0) {
      baseRate = 0.19;
    } else if (income <= 20_200.0) {
      baseRate = 0.24;
    } else if (income <= 35_200.0) {
      baseRate = 0.30;
    } else if (income <= 60_000.0) {
      baseRate = 0.37;
    } else {
      baseRate = 0.45;
    }

    // Modificador por hijos: -1% por hijo, hasta -5%
    int children = worker.getChildren();
    double childrenReduction = Math.min(children * 0.01, 0.05);

    // Modificador por contrato temporal: +3% si es "Temporal"
    double contractIncrease = 0.0;
    String contract = worker.getContract();
    if (contract != null && contract.equalsIgnoreCase("temporal")) {
      contractIncrease = 0.03;
    }

    // Tasa ajustada
    double adjustedRate = baseRate - childrenReduction + contractIncrease;

    // Asegurar que la tasa no sea negativa (protección)
    if (adjustedRate < 0.0) {
      adjustedRate = 0.0;
    }

    // Importe IRPF anual
    double irpfAmount = income * adjustedRate;

    // Redondear a 2 decimales (céntimos)
    irpf = Math.round(irpfAmount * 100.0) / 100.0;
    return irpf;
  }

  public double calculateNetSalary() {
    irpf = calculateIrpf();
    sgs = calculateSocialSecurity();

    netSalary = annualGrossSalary - (irpf + sgs);
    return Math.round(netSalary * 100.0) / 100.0;
  }

  public double calculateMonthlyNetSalary() {
    double netAnnual = calculateNetSalary();
    int payments = worker.getPayments();

    double monthly = netAnnual / payments;
    return Math.round(monthly * 100.0) / 100.0;
  }


}
