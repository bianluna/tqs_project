import static org.junit.jupiter.api.Assertions.*;

import model.Payroll;
import model.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class payrollTest {

  Payroll payroll = new Payroll();

  @Test
  void testConstructor() {
    /*
    * public class Payroll {
        public String payrollCode;
        Worker worker;
        public float annualGrossSalary; // worker.getTotalIncome()
        public float netSalary; // annualGrossSalary - deductions
        public float irpf;
        public float sgs;
  * */

    // new Payroll(String payrollCode, Worker worker) // other attributes -->  annualGrossSalary, netSalary, irpf, sgs

    Worker basicWorker = new Worker("Pepito", "Casado", 3, 35000, 12, "Indefinido", 7 );
    payroll = new Payroll("112025",basicWorker);
    assertEquals("112025", payroll.getPayrollCode()); // to identify each payroll
    assertNotNull(payroll.getWorker()); // asegura que se ha asociado un trabajador a la nómina
    assertEquals(35000,payroll.getTotalIncome()); // obtiene el salario bruto anual del trabajador

    //assertEquals(0,payroll.getNetSalary()); // obtiene el salario neto anual (nulo de momento) -- imple
    //assertEquals(0,payroll.getDeductions()); //obtiene las deducciones a realizar del salario bruto -> irpf, sgs
  }

  @Test
  void testMonthlySalary() {
    //payroll = new Payroll(20000, "Pepito", 12, 2000);
    //assertTrue(payroll.paymentsPerMonth(payroll.annualGrossSalary, payroll.paymentsPerYear ) == 1666.67);
    //assertFalse(payroll.paymentsPerMonth(payroll.annualGrossSalary, payroll.paymentsPerYear ) == 1666);
  }

  @Test
  void testSalaryExtras(){
    //payroll = new Payroll(20000, "Pepito", 12, 2000);
    //assertTrue(payroll.monthlyGrossSalary(payroll.annualGrossSalary, payroll.paymentsPerYear, payroll.extras) == 3666.67);
    //assertFalse(payroll.monthlyGrossSalary(payroll.annualGrossSalary, payroll.paymentsPerYear, payroll.extras) == 1800);
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
