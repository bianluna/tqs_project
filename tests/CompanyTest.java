import static org.junit.jupiter.api.Assertions.*;
import model.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.*;


class CompanyTest {

  private CompanyRepositoryMock mock;
  private Company validCompany;

  // Constants for the "Pre-existing" company defined inside your Mock
  private static final String EXISTING_CIF = "B12345678";

  @BeforeEach
  void setUp() {
    // 1. Initialize the mock before every test
    mock = new CompanyRepositoryMock();

    // 2. Create a standard valid company object to reuse
    validCompany = createCompany("123456789", "techsolutions@email.com", "A03");
  }

  @Test
  void testCompanyConstructor() {
    assertNotNull(validCompany);
    assertEquals("A03", validCompany.getCnae());
    assertEquals("Tech Solutions", validCompany.getName());
    assertEquals("123456789", validCompany.getCif());
  }

  @Test
  void testSaveCompany() {
    assertTrue(mock.save(validCompany), "Valid company should be saved");
  }

  @Test
  void testFindByCif() {
    // Uses the CIF that is hardcoded inside your Mock
    Company foundCompany = mock.findByCif(EXISTING_CIF);

    assertNotNull(foundCompany, "Company should be found");
    assertEquals("TechCorp Solutions", foundCompany.getName());
  }

  @Test
  void testDeleteCompany() {
    boolean result = mock.delete(EXISTING_CIF);

    assertTrue(result);
    assertTrue(mock.deleteWasCalled);
    assertNull(mock.findByCif(EXISTING_CIF), "Company should not exist after deletion");
  }

  @Test
  void testSaveCompanyWithNullValues() {
    Company nullCifCompany = createCompany(null, "email@test.com", "A03");
    assertFalse(mock.save(nullCifCompany), "Should return false for null CIF");
  }

  @Test
  void testSaveCompanyWithInvalidEmail() {
    Company badEmailCompany = createCompany("99999999", "invalid-email-format", "A03");
    assertFalse(mock.save(badEmailCompany), "Should return false for invalid email");
  }

  @Test
  void testSaveCompanyWithInvalidCnae() {
    Company badCnaeCompany = createCompany("99999999", "email@test.com", "A3");
    assertFalse(mock.save(badCnaeCompany), "Should return false for invalid CNAE");
  }

  @Test
  void testSaveDuplicateCif() {
    mock.save(validCompany); // First save
    boolean secondSaveResult = mock.save(validCompany); // Second save

    assertFalse(secondSaveResult, "Should return false on duplicate CIF");
  }

  @Test
  void testFindbyCifNotFound() {
    assertNull(mock.findByCif("B56789038"), "Non-existent CIF should return null");
  }

  @Test
  void testUpdateCompanySuccess() {
    // We must use the CIF that already exists in the mock to update it
    Company companyToUpdate = createCompany(EXISTING_CIF, "tech@email.com", "A03");

    assertTrue(mock.update(companyToUpdate), "Should return true when updating existing company");

    Company updated = mock.findByCif(EXISTING_CIF);
    assertEquals("Tech Solutions", updated.getName());
  }

  @Test
  void testUpdateCompanyNotExisting() {
    Company nonExisting = createCompany("B99999999", "tech@email.com", "A03");
    assertFalse(mock.update(nonExisting), "Should fail if company does not exist");
  }

  @Test
  void testUpdateCompanyInvalidValues() {
    // Case 1: Invalid CNAE
    Company badCnae = createCompany(EXISTING_CIF, "valid@email.com", "A3");
    assertFalse(mock.update(badCnae), "Should fail update with invalid CNAE");

    // Case 2: Invalid Email
    Company badEmail = createCompany(EXISTING_CIF, "bad-email", "A03");
    assertFalse(mock.update(badEmail), "Should fail update with invalid Email");
  }

  // --- Helper Method ---
  // This allows us to create a company with one line, customizing only what we need
  private Company createCompany(String cif, String email, String cnae) {
    return new Company(
        "Tech Solutions", // Default Name
        cif,
        email,
        "555-1234",       // Default Phone
        "123 Tech St",    // Default Address
        "www.tech.com",   // Default Web
        cnae
    );
  }
}