import static org.junit.jupiter.api.Assertions.*;

import model.Payroll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class payrollTest {
  Payroll payroll = new Payroll();

  @Test
  void testConstructor() {
    payroll = new Payroll(20000, "Pepito", 12);

    assertEquals("Pepito", payroll.workerName);
    assertTrue(payroll.annualGrossSalary == 20000);
    assertTrue(payroll.paymentsPerYear == 12);

  }



}
