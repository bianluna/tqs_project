import model.Company;

import java.util.*;
import java.util.regex.Pattern;

public class CompanyRepositoryMock implements CompanyRepository {

  private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
  private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
  public Map<String, Company> database;
  public boolean deleteWasCalled;
  public List<String> methodCallLog;
  public boolean shouldFailOnSave;


  private static final Set<String> VALID_CNAE_CODES = new HashSet<>(Arrays.asList(
      "6201", // Actividades de programación informática
      "6202", // Consultoría de informática
      "4110", // Promoción inmobiliaria
      "4121", // Construcción de edificios residenciales
      "5610", // Restaurantes y puestos de comidas
      "6920",  // Actividades de contabilidad
      "J62.01",
      "J62.02",
      "J63.11",
      "F41.20",
      "F43.32",
      "A03"
      // ... many more codes can be added here
  ));


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

    if (database.containsKey(company.getCif())) {
      return false; // Indicates failure because it already exists
    }

    if (company != null && company.getCif() != null && !company.getCif().isEmpty() && isValidEmail(company.getEmail()) && isValidCnae(company.getCnae())) {
      database.put(company.getCif(), company);
      return true;
    }
    return false;
  }

  @Override
  public boolean update(Company company) {
    methodCallLog.add("update");

    if (shouldFailOnSave) {
      return false;
    }

    if (findByCif(company.getCif()) == null) {
      return false; // Indicates failure because it doesn't exist
    }

    if (company != null && company.getCif() != null && !company.getCif().isEmpty() && isValidEmail(company.getEmail()) && isValidCnae(company.getCnae())) {
      database.put(company.getCif(), company);
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

  private boolean isValidEmail(String email) {
    if (email == null) {
      return false;
    }
    return EMAIL_PATTERN.matcher(email).matches();
  }

  private boolean isValidCnae(String cnae) {
    if (cnae == null) {
      return false;
    }
    return VALID_CNAE_CODES.contains(cnae);
  }
}