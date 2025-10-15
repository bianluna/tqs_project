package model;

public class Worker {

  private String name;
  private String civilStatus;
  private int children;
  private float totalIncome;
  private int payments;
  private String contract;
  private String category;

    public Worker() {
        this.name = "";
        this.civilStatus = "";
        this.children = 0;
        this.totalIncome = 0;
        this.payments = 0;
        this.contract = "";
        this.category = "";
    }

  public Worker(String name, String civilStatus, int children, float totalIncome, int payments, String contract, String category) {
    this.name = name;
    this.civilStatus = civilStatus;
    this.children = children;
    this.totalIncome = totalIncome;
    this.payments = payments;
    this.contract = contract;
    this.category = category;
  }


  public String getName() { return this.name; }
  public String getCivilStatus() { return civilStatus; }
  public int getChildren() {return children; }
  public float getTotalIncome() {return totalIncome;}
  public int getPayments() {return payments;}
  public String getContract() {return contract;}
  public String getCategory() {return category;}

  public void setPayments(int payments) {
    if (payments != 12 && payments != 14) {
      throw new IllegalArgumentException("El número de pagos debe ser 12 o 14");
    }
    this.payments = payments;
  }

  public boolean isValidIncome(float income) {
    return income >= 0;
  }

  public void setTotalIncome(float totalIncome) {
    if (!isValidIncome(totalIncome)) {
      throw new IllegalArgumentException("El salario neto bruto no puede ser negativo");
    }
    this.totalIncome = totalIncome;
  }


}
