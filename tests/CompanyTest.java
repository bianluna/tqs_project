import static org.junit.jupiter.api.Assertions.*;
import model.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.*;


public class CompanyTest {

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

  @Test
  void testDeleteCompany() {
    CompanyRepositoryMock mock = new CompanyRepositoryMock();
    // Eliminar una empresa existente
    boolean result = mock.delete("B12345678");
    assertTrue(result);
    assertTrue(mock.deleteWasCalled);
    // Verificar que ya no existe
    Company found = mock.findByCif("B12345678");
    assertNull(found);
  }


}