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


}
