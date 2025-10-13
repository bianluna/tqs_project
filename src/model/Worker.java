package model;

public class Worker {

  private String name;
  private String civilStatus;
  private int children;
  private float totalIncome;
  private int payments;
  private String contract;
  private String category;

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

}
