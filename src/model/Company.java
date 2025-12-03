package model;

public class Company {
  private String name;
  private String cif;
  private String email;
  private String phone;
  private String address;
  private String website;
  private String cnae;

  public Company(String name, String cif, String email, String phone, String address, String website, String cnae) {
    this.name = name;
    this.cif = cif;
    this.email = email;
    this.phone = phone;
    this.address = address;
    this.website = website;
    this.cnae = cnae;
  }


  public String getName() {
    return name;
  }

  public String getCif() {
    return cif;
  }

  public String getEmail() {
    return email;
  }

  public String getPhone() {
    return phone;
  }

  public String getAddress() {
    return address;
  }

  public String getWebsite() {
    return website;
  }

  public String getCnae() {
    return cnae;
  }


  public void setName(String name) {
    this.name = name;
  }

  public void setCif(String cif) {
    if (cif == null || cif.isBlank()) {
      throw new IllegalArgumentException("El CIF no puede ser vacío.");
    }
    this.cif = cif;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setWebsite(String website) {
    this.website = website;
  }

  public void setCnae(String cnae) {
    this.cnae = cnae;
  }

}
