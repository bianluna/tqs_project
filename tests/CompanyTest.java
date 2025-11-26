import static org.junit.jupiter.api.Assertions.*;

import model.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanyTest {

  static class CompanyRepositoryMock implements CompanyRepository {

    private Map<String, Company> database;

    // Variables de espionaje
    private boolean saveWasCalled;
    private boolean findByCifWasCalled;
    private boolean deleteWasCalled;
    private Company lastSavedCompany;
    private String lastSearchedCif;
    private List<String> methodCallLog;

    // Variables de stubbing
    private boolean shouldFailOnSave;
    private Company stubReturnValue;

    /**
     * Constructor que inicializa la BD con datos ficticios
     */
    public CompanyRepositoryMock() {
      this.database = new HashMap<>();
      this.methodCallLog = new ArrayList<>();
      this.saveWasCalled = false;
      this.findByCifWasCalled = false;
      this.deleteWasCalled = false;
      this.shouldFailOnSave = false;
      loadFakeData();
    }

    /**
     * Carga empresas ficticias en la base de datos simulada
     */
    private void loadFakeData() {
      // Empresas tecnológicas
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

      // Empresas de hostelería
      database.put("B67890123", new Company(
          "Restaurante El Buen Gusto",
          "B67890123",
          "reservas@elbuengusto.com",
          "+34 93 678 9012",
          "Rambla de Catalunya, 147, Barcelona",
          "www.elbuengusto.com",
          "I56.10"
      ));

      database.put("B78901234", new Company(
          "Café Central Barcelona",
          "B78901234",
          "info@cafecentral.es",
          "+34 93 789 0123",
          "Plaça Catalunya, 21, Barcelona",
          "www.cafecentral.es",
          "I56.30"
      ));

      database.put("B89012345", new Company(
          "Hotel Mediterráneo",
          "B89012345",
          "reservas@hotelmediterraneo.com",
          "+34 93 890 1234",
          "Passeig de Gràcia, 88, Barcelona",
          "www.hotelmediterraneo.com",
          "I55.10"
      ));

      // Empresas de servicios profesionales
      database.put("B90123456", new Company(
          "Asesoría Legal López",
          "B90123456",
          "info@asesorialopez.com",
          "+34 93 901 2345",
          "Carrer de Muntaner, 234, Barcelona",
          "www.asesorialopez.com",
          "M69.10"
      ));

      database.put("B01234567", new Company(
          "Consultoría Financiera Global",
          "B01234567",
          "contacto@cfglobal.es",
          "+34 93 012 3456",
          "Avinguda Meridiana, 567, Barcelona",
          "www.cfglobal.es",
          "K64.99"
      ));

      // Empresas de comercio
      database.put("B11111111", new Company(
          "Supermercado FreshMart",
          "B11111111",
          "info@freshmart.com",
          "+34 93 111 1111",
          "Carrer de Gran Via, 999, Barcelona",
          "www.freshmart.com",
          "G47.11"
      ));

      database.put("B22222222", new Company(
          "Librería Cervantes",
          "B22222222",
          "libros@cervantes.es",
          "+34 93 222 2222",
          "Carrer de Pelai, 52, Barcelona",
          "www.libreriacervantes.es",
          "G47.61"
      ));

      // Empresas de salud
      database.put("B33333333", new Company(
          "Farmacia Central",
          "B33333333",
          "farmacia@central.com",
          "+34 93 333 3333",
          "Carrer de Aragó, 123, Barcelona",
          "www.farmaciacentral.com",
          "G47.73"
      ));
    }

    @Override
    public boolean save(Company company) {
      methodCallLog.add("save");
      saveWasCalled = true;
      lastSavedCompany = company;

      if (shouldFailOnSave) {
        return false;
      }

      if (company != null && company.cif != null && !company.cif.isEmpty()) {
        database.put(company.cif, company);
        return true;
      }
      return false;
    }

  }

  @Test
  void testCompanyConstructor() {
    Company company = new Company(
        "Tech Solutions",
        "123456789",
        "techsolutions@email.com",
        "555-1234",
        "123 Tech St, Silicon Valley",
        "www.techsolutions.com",
        "A03"
    );

    assertNotNull(company);
    assertEquals("A03", company.cnae);
    assertEquals("Tech Solutions", company.name);
    assertEquals("123456789", company.cif);
  }

  @Test
  void testCompanyDatabase() {
    Company company = new Company(
        "Tech Solutions",
        "123456789",
        "techsolutions@email.com",
        "555-1234",
        "123 Tech St, Silicon Valley",
        "www.techsolutions.com",
        "A03"
    );

    // Instanciamos nuestro mock
    CompanyRepositoryMock mock = new CompanyRepositoryMock();
    // Ejecutamos el método usando nuestro mock
    boolean result = mock.save(company);
    // Verificamos el resultado del retorno
    assertTrue(result, "El método debería devolver true");
  }

  @Test
  void testFindByCif() {
    // Instanciamos nuestro mock
    CompanyRepositoryMock mock = new CompanyRepositoryMock();
    // Ejecutamos el método usando nuestro mock
    Company foundCompany = mock.findByCif("B12345678");
    // Verificamos el resultado del retorno
    assertNotNull(foundCompany, "La compañía debería ser encontrada");
    assertEquals("TechCorp Solutions", foundCompany.name, "El nombre de la compañía debería coincidir");
  }


}