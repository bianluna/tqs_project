import static org.junit.jupiter.api.Assertions.*;

import model.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class companyTest {

  @Test
  void testCompanyConstructor() {
    Company company = new Company("Tech Solutions", "123456789", "techsolutions@email.com", "555-1234", "123 Tech St, Silicon Valley", "www.techsolutions.com", "A03");
    assertNotNull(company);
    assertEquals(company.cnae, "A03");
    assertEquals(company.name, "Tech Solutions");
    assertEquals(company.cif, "123456789");
  }

  @Test
  void testCompanyDatabase() {
    Company company = new Company("Tech Solutions", "123456789", "techsolutions@email.com", "555-1234", "123 Tech St, Silicon Valley", "www.techsolutions.com", "A03");

    // Instanciamos nuestro mock
    CompanyRepositoryMock mock = new CompanyRepositoryMock();

    // Ejecutamos el método usando nuestro mock
    boolean result = mock.save(company);

    // Verificamos el resultado del retorno
    assertTrue(result, "El método debería devolver true");

    // Verificamos el comportamiento manualmente consultando las variables del mock
    assertTrue(mock.saveWasCalled, "El método save() debería haber sido invocado");
    assertEquals(company, mock.companySaved, "La compañía guardada debería ser la misma que enviamos");

  }
}
