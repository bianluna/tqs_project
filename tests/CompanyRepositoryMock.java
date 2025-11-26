import model.Company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanyRepositoryMock implements CompanyRepository {

  public Map<String, Company> database;
  public boolean deleteWasCalled;
  public List<String> methodCallLog;
  public boolean shouldFailOnSave;

  /**
   * Constructor que inicializa la BD con datos ficticios
   */
  public CompanyRepositoryMock() {
    this.database = new HashMap<>();
    this.methodCallLog = new ArrayList<>();
    this.deleteWasCalled = false;
    this.shouldFailOnSave = false;
    loadFakeData();
  }

  /**
   * Carga empresas ficticias en la base de datos simulada
   */
  private void loadFakeData() {

    database.put("B12345678", new Company(
        "TechCorp Solutions",
        "B12345678",
        "info@techcorp.com",
        "+34 93 123 4567",
        "Carrer de Balmes, 123, Barcelona",
        "www.techcorp.com",
        "J62.01"
    ));

    database.put("B23456789", new Company(
        "DataAnalytics Barcelona",
        "B23456789",
        "contact@dataanalytics.es",
        "+34 93 234 5678",
        "Avinguda Diagonal, 456, Barcelona",
        "www.dataanalytics.es",
        "J62.02"
    ));

    database.put("B34567890", new Company(
        "CloudServers SA",
        "B34567890",
        "hello@cloudservers.com",
        "+34 93 345 6789",
        "Carrer de Provença, 789, Barcelona",
        "www.cloudservers.com",
        "J63.11"
    ));

    // Empresas de construcción
    database.put("B45678901", new Company(
        "Construcciones García SL",
        "B45678901",
        "garcia@construcciones.com",
        "+34 93 456 7890",
        "Carrer de Sants, 321, Barcelona",
        "www.construccionesgarcia.com",
        "F41.20"
    ));

    database.put("B56789012", new Company(
        "Reformas Martínez",
        "B56789012",
        "info@reformasmartinez.es",
        "+34 93 567 8901",
        "Carrer de Girona, 654, Barcelona",
        "www.reformasmartinez.es",
        "F43.32"
    ));
  }

  @Override
  public boolean save(Company company) {
    methodCallLog.add("save");

    if (shouldFailOnSave) {
      return false;
    }

    if (company != null && company.cif != null && !company.cif.isEmpty()) {
      database.put(company.cif, company);
      return true;
    }
    return false;
  }

  @Override
  public Company findByCif(String cif) {
    methodCallLog.add("findByCif");
    return database.get(cif);
  }

  @Override
  public boolean delete(String cif) {
    methodCallLog.add("delete");
    deleteWasCalled = true;

    if (database.containsKey(cif)) {
      database.remove(cif);
      return true;
    }
    return false;
  }
}