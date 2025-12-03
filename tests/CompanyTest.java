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
    assertEquals("A03", company.getCnae());
    assertEquals("Tech Solutions", company.getName());
    assertEquals("123456789", company.getCif());
  }

  @Test
  void testSaveCompany() {
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
    assertEquals("TechCorp Solutions", foundCompany.getName(), "El nombre de la compañía debería coincidir");
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


  @Test
  void testSaveCompanyWithNullValues(){
    Company company = new Company(
        "Tech Solutions",
        null,
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
    assertFalse(result, "El método debería devolver false ya que el CIF es nulo");
  }

  @Test
  void testSaveCompanyWithInvalidEmail() {
    Company company = new Company(
        "Tech Solutions",
        "123456789",
        "invalid-email-format",
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
    assertFalse(result, "El método debería devolver false ya que el email es inválido");
  }


  @Test
  void testSaveCompanyWithInvalidCnae(){
    Company company = new Company(
        "Tech Solutions",
        "123456789",
        "techsolutions@email.com",
        "555-1234",
        "123 Tech St, Silicon Valley",
        "www.techsolutions.com",
        "A3"
    );
    CompanyRepositoryMock mock = new CompanyRepositoryMock();
    // Ejecutamos el método usando nuestro mock
    boolean result = mock.save(company);
    // Verificamos el resultado del retorno
    assertFalse(result, "El método debería devolver false ya que el cnae es inválido");

  }

  @Test
  void testSaveDuplicateCif() {
    Company company = new Company(
        "Tech Solutions",
        "123456789",
        "techsolutions@email.com",
        "555-1234",
        "123 Tech St, Silicon Valley",
        "www.techsolutions.com",
        "A03"
    );
    CompanyRepositoryMock mock = new CompanyRepositoryMock();
    // Guardar la primera vez
    boolean firstSaveResult = mock.save(company);
    // Intentar guardar de nuevo con el mismo CIF
    boolean secondSaveResult = mock.save(company);
    // Verificamos el resultado del segundo intento de guardado
    assertFalse(secondSaveResult, "El método debería devolver false al intentar guardar un CIF duplicado");
  }


  @Test
  void testFindbyCifNotFound(){
    CompanyRepositoryMock mock = new CompanyRepositoryMock();
    // Ejecutamos el método usando nuestro mock
    Company foundCompany = mock.findByCif("B56789038");
    // Verificamos el resultado del retorno
    assertNull(foundCompany, "La compañía no debería ser encontrada");
  }

  @Test
  void testUpdateCompanySuccess(){
    CompanyRepositoryMock mock = new CompanyRepositoryMock();
    Company company = new Company(
        "Tech Solutions Updated",
        "B12345678",
        "techsolutions@email.com",
        "555-1234",
        "123 Tech St, Silicon Valley",
        "www.techsolutions.com",
        "A03");
    boolean result = mock.update(company);
    assertTrue(result, "El método debería devolver true al actualizar la compañía existente");
    Company updatedCompany = mock.findByCif("B12345678");
    assertEquals("Tech Solutions Updated", updatedCompany.getName(), "El nombre de la compañía debería ser actualizado");
  }
}