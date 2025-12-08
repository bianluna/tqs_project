import static org.junit.jupiter.api.Assertions.*;
import model.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests de Caja Blanca y Negra para Company
 * Objetivo: 100% Statement, Decision y Condition Coverage
 */
class CompanyTest {

  private Company company;

  @BeforeEach
  void setUp() {
    company = new Company(
        "Tech Solutions",
        "B12345678",
        "info@techsolutions.com",
        "+34 93 123 4567",
        "Carrer de Balmes, 123",
        "www.techsolutions.com",
        "J62.01"
    );
  }

  // ===== TESTS PARA CONSTRUCTOR =====

  /**
   * Test Caja Negra: Partición equivalente - Constructor válido
   * Test Caja Blanca: Statement coverage - inicialización de todos los campos
   */
  @Test
  void testConstructor_ValidCompany() {
    assertNotNull(company);
    assertEquals("Tech Solutions", company.getName());
    assertEquals("B12345678", company.getCif());
    assertEquals("info@techsolutions.com", company.getEmail());
    assertEquals("+34 93 123 4567", company.getPhone());
    assertEquals("Carrer de Balmes, 123", company.getAddress());
    assertEquals("www.techsolutions.com", company.getWebsite());
    assertEquals("J62.01", company.getCnae());
  }

  // ===== TESTS PARA GETTERS (Statement Coverage) =====

  @Test
  void testGetName() {
    assertEquals("Tech Solutions", company.getName());
  }

  @Test
  void testGetCif() {
    assertEquals("B12345678", company.getCif());
  }

  @Test
  void testGetEmail() {
    assertEquals("info@techsolutions.com", company.getEmail());
  }

  @Test
  void testGetPhone() {
    assertEquals("+34 93 123 4567", company.getPhone());
  }

  @Test
  void testGetAddress() {
    assertEquals("Carrer de Balmes, 123", company.getAddress());
  }

  @Test
  void testGetWebsite() {
    assertEquals("www.techsolutions.com", company.getWebsite());
  }

  @Test
  void testGetCnae() {
    assertEquals("J62.01", company.getCnae());
  }

  // ===== TESTS PARA SETTERS (Statement + Branch Coverage) =====

  /**
   * Test Caja Blanca: Statement coverage - setName
   */
  @Test
  void testSetName_ValidName() {
    company.setName("New Tech Company");
    assertEquals("New Tech Company", company.getName());
  }

  /**
   * Test Caja Negra: Partición equivalente - nombre vacío
   */
  @Test
  void testSetName_EmptyName() {
    company.setName("");
    assertEquals("", company.getName()); // Acepta vacío (no hay validación)
  }

  /**
   * Test Caja Negra: Valor límite - nombre null
   */
  @Test
  void testSetName_NullName() {
    company.setName(null);
    assertNull(company.getName()); // Acepta null (no hay validación)
  }

  // ===== TESTS PARA setCif (CON VALIDACIÓN - BRANCH COVERAGE) =====

  /**
   * Test Caja Negra: Partición equivalente - CIF válido
   * Test Caja Blanca: Branch coverage - cif != null && !isBlank() → true
   */
  @Test
  void testSetCif_ValidCif() {
    company.setCif("B99999999");
    assertEquals("B99999999", company.getCif());
  }

  /**
   * Test Caja Negra: Valor límite - CIF null
   * Test Caja Blanca: Branch coverage - cif == null → lanza excepción
   */
  @Test
  void testSetCif_NullCif_ThrowsException() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      company.setCif(null);
    });
    assertEquals("El CIF no puede ser vacío.", exception.getMessage());
  }

  /**
   * Test Caja Negra: Valor límite - CIF vacío
   * Test Caja Blanca: Branch coverage - cif.isBlank() → lanza excepción
   */
  @Test
  void testSetCif_EmptyCif_ThrowsException() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      company.setCif("");
    });
    assertEquals("El CIF no puede ser vacío.", exception.getMessage());
  }

  /**
   * Test Caja Negra: Valor límite - CIF con espacios en blanco
   * Test Caja Blanca: Branch coverage - cif.isBlank() → lanza excepción
   */
  @Test
  void testSetCif_BlankCif_ThrowsException() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      company.setCif("   ");
    });
    assertEquals("El CIF no puede ser vacío.", exception.getMessage());
  }

  // ===== TESTS PARA setEmail (Statement Coverage) =====

  /**
   * Test Caja Blanca: Statement coverage - email válido
   */
  @Test
  void testSetEmail_ValidEmail() {
    company.setEmail("newemail@company.com");
    assertEquals("newemail@company.com", company.getEmail());
  }

  /**
   * Test Caja Negra: Partición equivalente - email con subdominios
   */
  @Test
  void testSetEmail_EmailWithSubdomains() {
    company.setEmail("contact@sub.domain.company.com");
    assertEquals("contact@sub.domain.company.com", company.getEmail());
  }

  /**
   * Test Caja Negra: Valor límite - email null
   */
  @Test
  void testSetEmail_NullEmail() {
    company.setEmail(null);
    assertNull(company.getEmail());
  }

  /**
   * Test Caja Negra: Valor límite - email vacío
   */
  @Test
  void testSetEmail_EmptyEmail() {
    company.setEmail("");
    assertEquals("", company.getEmail());
  }

  // ===== TESTS PARA setPhone (Statement Coverage) =====

  @Test
  void testSetPhone_ValidPhone() {
    company.setPhone("+34 91 000 0000");
    assertEquals("+34 91 000 0000", company.getPhone());
  }

  @Test
  void testSetPhone_NullPhone() {
    company.setPhone(null);
    assertNull(company.getPhone());
  }

  // ===== TESTS PARA setAddress (Statement Coverage) =====

  @Test
  void testSetAddress_ValidAddress() {
    company.setAddress("Nueva dirección 456");
    assertEquals("Nueva dirección 456", company.getAddress());
  }

  @Test
  void testSetAddress_NullAddress() {
    company.setAddress(null);
    assertNull(company.getAddress());
  }

  // ===== TESTS PARA setWebsite (Statement Coverage) =====

  @Test
  void testSetWebsite_ValidWebsite() {
    company.setWebsite("www.newsite.com");
    assertEquals("www.newsite.com", company.getWebsite());
  }

  @Test
  void testSetWebsite_NullWebsite() {
    company.setWebsite(null);
    assertNull(company.getWebsite());
  }

  // ===== TESTS PARA setCnae (Statement Coverage) =====

  @Test
  void testSetCnae_ValidCnae() {
    company.setCnae("J62.02");
    assertEquals("J62.02", company.getCnae());
  }

  @Test
  void testSetCnae_NullCnae() {
    company.setCnae(null);
    assertNull(company.getCnae());
  }

  // ===== TEST DE INTEGRACIÓN =====

  /**
   * Test Caja Blanca: Path coverage - modificar todos los campos
   */
  @Test
  void testUpdateAllFields() {
    company.setName("Updated Company");
    company.setCif("B88888888");
    company.setEmail("updated@email.com");
    company.setPhone("+34 93 999 9999");
    company.setAddress("Updated Address 789");
    company.setWebsite("www.updated.com");
    company.setCnae("J63.11");

    assertEquals("Updated Company", company.getName());
    assertEquals("B88888888", company.getCif());
    assertEquals("updated@email.com", company.getEmail());
    assertEquals("+34 93 999 9999", company.getPhone());
    assertEquals("Updated Address 789", company.getAddress());
    assertEquals("www.updated.com", company.getWebsite());
    assertEquals("J63.11", company.getCnae());
  }

  /**
   * Test Caja Negra: Caso límite - múltiples modificaciones del CIF
   */
  @Test
  void testSetCif_MultipleChanges() {
    company.setCif("B11111111");
    assertEquals("B11111111", company.getCif());

    company.setCif("B22222222");
    assertEquals("B22222222", company.getCif());

    company.setCif("B33333333");
    assertEquals("B33333333", company.getCif());
  }

  /**
   * Test Caja Blanca: Condition coverage - validación de setCif con diferentes combinaciones
   */
  @Test
  void testSetCif_ConditionCoverage() {
    // Caso 1: cif != null && !isBlank() → true (válido)
    company.setCif("B12345678");
    assertEquals("B12345678", company.getCif());

    // Caso 2: cif == null → excepción
    assertThrows(IllegalArgumentException.class, () -> company.setCif(null));

    // Caso 3: cif.isBlank() → excepción (cadena vacía)
    assertThrows(IllegalArgumentException.class, () -> company.setCif(""));

    // Caso 4: cif.isBlank() → excepción (solo espacios)
    assertThrows(IllegalArgumentException.class, () -> company.setCif("    "));
  }
}