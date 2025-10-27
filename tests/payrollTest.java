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




}
