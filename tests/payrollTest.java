import static org.junit.jupiter.api.Assertions.*;

import model.Payroll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class payrollTest {

  Payroll payroll = new Payroll();

  @Test
  void testConstructor() {
    payroll = new Payroll(20000, "Pepito", 12, 2000);
    assertEquals("Pepito", payroll.workerName);
    assertTrue(payroll.annualGrossSalary == 20000);
    assertTrue(payroll.paymentsPerYear == 12);
  }

  @Test
  void testMonthlySalary() {
    payroll = new Payroll(20000, "Pepito", 12, 2000);
    assertTrue(payroll.paymentsPerMonth(payroll.annualGrossSalary, payroll.paymentsPerYear ) == 1666.67);
    assertFalse(payroll.paymentsPerMonth(payroll.annualGrossSalary, payroll.paymentsPerYear ) == 1666);
  }

  @Test
  void testSalaryExtras(){
    payroll = new Payroll(20000, "Pepito", 12, 2000);
    assertTrue(payroll.monthlyGrossSalary(payroll.annualGrossSalary, payroll.paymentsPerYear, payroll.extras) == 3666.67);
    assertFalse(payroll.monthlyGrossSalary(payroll.annualGrossSalary, payroll.paymentsPerYear, payroll.extras) == 1800);
  }

  /*
   * Test cases for calculating social security deduction based on contribution group.
   *
   * Equivalence Partitions:
   * 1. Group 1–4 → 6.35%
   * 2. Group 5–7 → 6.40%
   * 3. Group 8–11 → 6.45%
   * 4. Invalid group (<1 or >11) → IllegalArgumentException
   *    Frontier values: -1, 0, 12, 13
   */
  @Test
  void testSocialSecurityDeduction() {
    Payroll p = new Payroll();

    // Equivalence Partition 1: grup 1–4
    assertEquals(635.0, p.calculateSocialSecurity(10000, 2));

    // Equivalence Partition 2: grup 5–7
    assertEquals(640.0, p.calculateSocialSecurity(10000, 6));

    // Equivalence Partition 3: grup 8–11
    assertEquals(645.0, p.calculateSocialSecurity(10000, 9));

    // Invalid case -> out of range group
    assertEquals(0.0, p.calculateSocialSecurity(10000, -1));
    assertEquals(0.0, p.calculateSocialSecurity(10000, 0));
    assertEquals(0.0, p.calculateSocialSecurity(10000, 12));
    assertEquals(0.0, p.calculateSocialSecurity(10000, 13));
  }

}
