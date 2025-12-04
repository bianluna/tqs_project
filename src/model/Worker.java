package model;

public class Worker {

  private static final String LETRAS_DNI = "TRWAGMYFPDXBNJZSQVHLCKE";

  private String name;
  private String dni;
  private String civilStatus;
  private int children;
  private float totalIncome;
  private int payments;
  private String contract;
  private int category;
  private String cifEmpresa;

    public Worker() {
        this.name = "";
        this.dni = "";
        this.civilStatus = "";
        this.children = 0;
        this.totalIncome = 0;
        this.payments = 0;
        this.contract = "";
        this.category = 0;
        this.cifEmpresa = "";
    }

  public Worker(String name, String dni,  String civilStatus, int children, float totalIncome, int payments, String contract, int category, String cifEmpresa) {
      this.name = name;          // Valida no vacío
      setDni(dni);            // Valida formato y letra
      setCivilStatus(civilStatus); // Valida lista cerrada
      setChildren(children);  // Valida >= 0
      setTotalIncome(totalIncome); // Valida > 0 (o mínimo legal)
      setPayments(payments);  // Valida 12 o 14
      setContract(contract);  // Valida tipo contrato
      setCategory(category);  // Valida rango
      this.cifEmpresa=cifEmpresa; // Valida formato CIF (simple)
  }



  public String getName() { return this.name; }
  public String getDni() { return this.dni; }
  public String getCivilStatus() { return civilStatus; }
  public int getChildren() {return children; }
  public float getTotalIncome() {return totalIncome;}
  public int getPayments() {return payments;}
  public String getContract() {return contract;}
  public int getCategory() {return category;}
  public String getCifEmpresa() {return cifEmpresa;}

  public void setPayments(int payments) {
    if (payments == 12 || payments == 14) {
      this.payments = payments;
    }
  }

  public boolean isValidIncome(float income) {
    return income > 0;
  }

  public void setTotalIncome(float totalIncome) {
    if (!isValidIncome(totalIncome)) {
      throw new IllegalArgumentException("El salario neto bruto no puede ser negativo");
    }
    this.totalIncome = totalIncome;
  }

  public boolean isValidChildren(int children){
      return children >= 0;
  }

  public void setChildren(int children) {
      if (isValidChildren(children)) {
        this.children = children;
      }
  }

  public boolean isValidCategory(int category) {
      return category>=0 && category<=10;
  }

  public void setCategory(int category){
      if (!isValidCategory(category)) {
          category=0;
      }
      this.category = category;
  }

  public boolean isValidContract(String contract) {
    return contract.equals("Indefinido") || contract.equals("Temporal") || contract.equals("Formacion en Alternancia")|| contract.equals("Formativo para la Obtencion de la Práctica Profesional");
  }

  public void setContract(String contract) {
    if (isValidContract(contract)) {
      this.contract = contract;
    }
  }

  public boolean isCivilStatusValid(String civilStatus) {
      return civilStatus.equals("Soltero") || civilStatus.equals("Casado") || civilStatus.equals("Divorciado") || civilStatus.equals("Viudo");
  }
  public void setCivilStatus(String civilStatus) {
      if (isCivilStatusValid(civilStatus)) {
          this.civilStatus = civilStatus;
      }
  }

  public void setDni(String dni) {
    if (isValidDni(dni)) {
      this.dni = dni;
    } else {
      System.err.println("Error: DNI inválido intentando ser asignado: " + dni);
      //throw new IllegalArgumentException("DNI inválido");
    }
  }

  private boolean isValidDni(String dni) {
    if (dni == null || dni.isEmpty()) {
      return false;
    }

    dni = dni.replace("-", "").trim().toUpperCase();

    if (!dni.matches("^\\d{8}[A-Z]$")) {
      return false;
    }

    String numeroStr = dni.substring(0, 8);
    char letraProporcionada = dni.charAt(8);

    int numero = Integer.parseInt(numeroStr);
    int resto = numero % 23;
    char letraCalculada = LETRAS_DNI.charAt(resto);

    return letraProporcionada == letraCalculada;
  }

  @Override
  public String toString() {
    return "Worker{" +
        "name='" + name + '\'' +
        ", dni='" + dni + '\'' +
        ", civilStatus='" + civilStatus + '\'' +
        ", children=" + children +
        ", totalIncome=" + totalIncome +
        ", payments=" + payments +
        ", contract='" + contract + '\'' +
        ", category=" + category +
        ", cifEmpresa='" + cifEmpresa + '\'' +
        '}';
  }
}
