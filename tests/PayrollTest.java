import static org.junit.jupiter.api.Assertions.*;

import model.Payroll;
import model.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PayrollTest {

  Payroll payroll;

  @BeforeEach
  void setup() {
    payroll = new Payroll();
  }

  @Test
  void testConstructor() {
    Worker basicWorker = new Worker("Juan", "48392015S", "Casado", 2,
        35000, 12, "Indefinido", 5, "J12345678");
    payroll = new Payroll("112025", basicWorker);

    assertEquals("112025", payroll.getPayrollCode());
    assertNotNull(payroll.getWorker());
    assertEquals(35000, payroll.getTotalIncome());
  }

  @Test
  void testMonthlySalary() {
    Worker basicWorker = new Worker("Juan", "48392015S", "Casado", 2,
        20000, 12, "Indefinido", 5, "J12345678");
    payroll = new Payroll("112025", basicWorker);

    assertEquals(1666.67, payroll.paymentsPerMonth());
    assertNotEquals(1666, payroll.paymentsPerMonth(), 0.0);
  }

  @Test
  void testSocialSecurityDeduction() {
    Worker cat1Worker = new Worker("Juan", "48392015S", "Soltero", 0,
        10000, 12, "Temporal", 1, "J12345678");
    Worker cat2Worker = new Worker("Juan", "48392015S", "Soltero", 0,
        10000, 12, "Temporal", 2, "J12345678");
    Worker cat4Worker = new Worker("Juan", "48392015S", "Soltero", 0,
        10000, 12, "Temporal", 4, "J12345678");
    Worker cat5Worker = new Worker("Juan", "48392015S", "Soltero", 0,
        10000, 12, "Temporal", 5, "J12345678");
    Worker cat6Worker = new Worker("Juan", "48392015S", "Soltero", 0,
        10000, 12, "Temporal", 6, "J12345678");
    Worker cat7Worker = new Worker("Juan", "48392015S", "Soltero", 0,
        10000, 12, "Temporal", 7, "J12345678");
    Worker cat8Worker = new Worker("Juan", "48392015S", "Soltero", 0,
        10000, 12, "Temporal", 8, "J12345678");
    Worker cat9Worker = new Worker("Juan", "48392015S", "Soltero", 0,
        10000, 12, "Temporal", 9, "J12345678");
    Worker cat10Worker = new Worker("Juan", "48392015S", "Soltero", 0,
        10000, 12, "Temporal", 10, "J12345678");

    // Equivalence Partition 1: grup 1–4
    payroll.setWorker(cat1Worker);
    assertEquals(635.0, payroll.calculateSocialSecurity());
    payroll.setWorker(cat2Worker);
    assertEquals(635.0, payroll.calculateSocialSecurity());
    payroll.setWorker(cat4Worker);
    assertEquals(635.0, payroll.calculateSocialSecurity());

    // Equivalence Partition 2: grup 5–7
    payroll.setWorker(cat5Worker);
    assertEquals(640.0, payroll.calculateSocialSecurity());
    payroll.setWorker(cat6Worker);
    assertEquals(640.0, payroll.calculateSocialSecurity());
    payroll.setWorker(cat7Worker);
    assertEquals(640.0, payroll.calculateSocialSecurity());

    // Equivalence Partition 3: grup 8–10
    payroll.setWorker(cat8Worker);
    assertEquals(645.0, payroll.calculateSocialSecurity());
    payroll.setWorker(cat9Worker);
    assertEquals(645.0, payroll.calculateSocialSecurity());
    payroll.setWorker(cat10Worker);
    assertEquals(645.0, payroll.calculateSocialSecurity());
  }

  @Test
  void testIrpfDeduction() {

    // === BASE CASES (no children, indefinite contract) ===
    Worker base1 = new Worker("A", "71239485K", "Soltero", 0,
        12000, 12, "Indefinido", 3, "J12345678");
    Worker base1b = new Worker("A", "71239485K", "Soltero", 0,
        12449, 12, "Indefinido", 3, "J12345678");
    Worker base1c = new Worker("A", "71239485K", "Soltero", 0,
        12451, 12, "Indefinido", 3, "J12345678");

    Worker base2 = new Worker("B", "71239485K", "Soltero", 0,
        18000, 12, "Indefinido", 3, "J12345678");
    Worker base2b = new Worker("B", "71239485K", "Soltero", 0,
        20201, 12, "Indefinido", 3, "J12345678");
    Worker base2c = new Worker("B", "71239485K", "Soltero", 0,
        20199, 12, "Indefinido", 3, "J12345678");

    Worker base3 = new Worker("C", "71239485K", "Soltero", 0,
        30000, 12, "Indefinido", 3, "J12345678");
    Worker base3b = new Worker("C", "71239485K", "Soltero", 0,
        35199, 12, "Indefinido", 3, "J12345678");
    Worker base3c = new Worker("C", "71239485K", "Soltero", 0,
        35201, 12, "Indefinido", 3, "J12345678");

    Worker base4 = new Worker("D", "71239485K", "Soltero", 0,
        50000, 12, "Indefinido", 3, "J12345678");
    Worker base4b = new Worker("D", "71239485K", "Soltero", 0,
        59999, 12, "Indefinido", 3, "J12345678");
    Worker base4c = new Worker("D", "71239485K", "Soltero", 0,
        60001, 12, "Indefinido", 3, "J12345678");

    Worker base5 = new Worker("E", "71239485K", "Soltero", 0,
        80000, 12, "Indefinido", 3, "J12345678");

    payroll.setWorker(base1);
    assertEquals(2280.0, payroll.calculateIrpf(), 0.01);
    payroll.setWorker(base1b);
    assertEquals(2365.31, payroll.calculateIrpf(), 0.01);

    payroll.setWorker(base1c);
    assertEquals(2988.24, payroll.calculateIrpf(), 0.01);
    payroll.setWorker(base2);
    assertEquals(4320.0, payroll.calculateIrpf(), 0.01);
    payroll.setWorker(base2c);
    assertEquals(4847.76, payroll.calculateIrpf(), 0.01);

    payroll.setWorker(base2b);
    assertEquals(6060.3, payroll.calculateIrpf(), 0.01);
    payroll.setWorker(base3);
    assertEquals(9000.0, payroll.calculateIrpf(), 0.01);
    payroll.setWorker(base3b);
    assertEquals(10559.7, payroll.calculateIrpf(), 0.01);

    payroll.setWorker(base3c);
    assertEquals(13024.37, payroll.calculateIrpf(), 0.01);
    payroll.setWorker(base4);
    assertEquals(18500.0, payroll.calculateIrpf(), 0.01);
    payroll.setWorker(base4b);
    assertEquals(22199.63, payroll.calculateIrpf(), 0.01);

    payroll.setWorker(base4c);
    assertEquals(27000.45, payroll.calculateIrpf(), 0.01);
    payroll.setWorker(base5);
    assertEquals(36000.0, payroll.calculateIrpf(), 0.01);

    // === CHILDREN CASES (1, 2, 3, 5 children) ===
    Worker child1 = new Worker("A1", "71239485K", "Soltero", 1,
        12000, 12, "Indefinido", 3, "J12345678");
    payroll.setWorker(child1);
    assertEquals(2160.0, payroll.calculateIrpf(), 0.01);

    Worker child2 = new Worker("A2", "71239485K", "Soltero", 2,
        18000, 12, "Indefinido", 3, "J12345678");
    payroll.setWorker(child2);
    assertEquals(3960.0, payroll.calculateIrpf(), 0.01);

    Worker child3 = new Worker("A3", "71239485K", "Soltero", 3,
        30000, 12, "Indefinido", 3, "J12345678");
    payroll.setWorker(child3);
    assertEquals(8100.0, payroll.calculateIrpf(), 0.01);

    Worker child5 = new Worker("A5", "71239485K", "Soltero", 5,
        80000, 12, "Indefinido", 3, "J12345678");
    payroll.setWorker(child5);
    assertEquals(32000.0, payroll.calculateIrpf(), 0.01);

    // === TEMPORARY CONTRACT CASES (+3%) ===
    Worker temp1 = new Worker("T1", "71239485K", "Soltero", 0,
        12000, 12, "Temporal", 3, "J12345678");
    payroll.setWorker(temp1);
    assertEquals(2640.0, payroll.calculateIrpf(), 0.01);

    Worker temp2 = new Worker("T2", "71239485K", "Soltero", 0,
        18000, 12, "Temporal", 3, "J12345678");
    payroll.setWorker(temp2);
    assertEquals(4860.0, payroll.calculateIrpf(), 0.01);

    Worker temp3 = new Worker("T3", "71239485K", "Soltero", 0,
        30000, 12, "Temporal", 3, "J12345678");
    payroll.setWorker(temp3);
    assertEquals(9900.0, payroll.calculateIrpf(), 0.01);

    Worker temp4 = new Worker("T4", "71239485K", "Soltero", 0,
        50000, 12, "Temporal", 3, "J12345678");
    payroll.setWorker(temp4);
    assertEquals(20000.0, payroll.calculateIrpf(), 0.01);

    Worker temp5 = new Worker("T5", "71239485K", "Soltero", 0,
        80000, 12, "Temporal", 3, "J12345678");
    payroll.setWorker(temp5);
    assertEquals(38400.0, payroll.calculateIrpf(), 0.01);
  }

  @Test
  void testCalculateNetSalary() {
    // Caso 1: Trabajador base
    Worker w1 = new Worker("Ana", "71239485K", "Soltero", 0,
        30000, 12, "Indefinido", 5, "J12345678");
    payroll.setWorker(w1);
    assertEquals(19080.0, payroll.calculateNetSalary(), 0.01);

    // Caso 2: Trabajador con hijos y contrato temporal
    Worker w2 = new Worker("Luis", "71239485K", "Casado", 3,
        40000, 14, "Temporal", 7, "J12345678");
    payroll.setWorker(w2);
    assertEquals(22640.0, payroll.calculateNetSalary(), 0.01);

    // Caso 3: Trabajador de categoría alta con muchos hijos
    Worker w3 = new Worker("Lucia", "71239485K", "Casado", 5,
        80000, 12, "Indefinido", 10, "J12345678");
    payroll.setWorker(w3);
    assertEquals(42840.0, payroll.calculateNetSalary(), 0.01);
  }

  @Test
  void testCalculateMonthlyNetSalary() {
    // Caso 1: Trabajador con 12 pagas
    Worker w1 = new Worker("Marta", "63314529Z", "Soltero", 0,
        36000, 12, "Indefinido", 5, "B34567890");
    payroll.setWorker(w1);
    assertEquals(1698.0, payroll.calculateMonthlyNetSalary(), 0.01);

    // Caso 1A: Trabajador con 12 pagas
    Worker w1a = new Worker("Luis Molina", "10293847J", "Soltero", 1,
        22000f, 12, "Temporal", 4, "B34567890");
    payroll.setWorker(w1a);
    assertEquals(1130.25, payroll.calculateMonthlyNetSalary(), 0.01);

    // Caso 2: Trabajador con 14 pagas
    Worker w2 = new Worker("Luis", "71239485K", "Casado", 2,
        42000, 14, "Temporal", 7, "J12345678");
    payroll.setWorker(w2);
    assertEquals(1668.0, payroll.calculateMonthlyNetSalary(), 0.01);

    // Caso 2A: Trabajador con 14 pagas
    Worker w2a = new Worker("Ana Torres", "71239485K", "Casado", 2,
        28000f, 14, "Indefinido", 2, "B12345678");
    payroll.setWorker(w2a);
    assertEquals(1312.99, payroll.calculateMonthlyNetSalary(), 0.01);
  }

  @Test
  void testEmptyConstructor() {
    Payroll empty = new Payroll();
    assertEquals("", empty.payrollCode);
    assertNull(empty.getWorker());
    assertEquals(0f, empty.annualGrossSalary, 0.01);
  }

  @Test
  void testSetWorker() {
    Payroll p = new Payroll();
    Worker w = new Worker("Test", "48392015S", "Soltero", 0,
        25000, 12, "Indefinido", 5, "J12345678");
    p.setWorker(w);
    assertEquals(w, p.getWorker());
    assertEquals(25000f, p.annualGrossSalary, 0.01);
  }
}