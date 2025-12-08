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
    // public Worker(String name, String dni,  String civilStatus, int children, float totalIncome, int payments, String contract, int category, String cifEmpresa)
    Worker basicWorker = new Worker("Juan", "48392015S", "Casado", 2, 35000, 12, "Indefinido", 5 , "J12345678");
    payroll = new Payroll("112025",basicWorker);
    assertEquals("112025", payroll.getPayrollCode()); // to identify each payroll
    assertNotNull(payroll.getWorker()); // asegura que se ha asociado un trabajador a la nómina
    assertEquals(35000,payroll.getTotalIncome()); // obtiene el salario bruto anual del trabajador

    // Assigned null worker should throw exception
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      payroll.setWorker(null);
    });
    assertEquals("Payroll: worker cannot be null", exception.getMessage());
  }

  @Test
  void testMonthlySalary() {
    Worker basicWorker = new Worker("Juan", "48392015S", "Casado", 2, 20000, 12, "Indefinido", 5 , "J12345678");
    payroll = new Payroll("112025", basicWorker);
    assertEquals(1666.67, payroll.paymentsPerMonth());
    assertNotEquals(1666, payroll.paymentsPerMonth(), 0.0);
  }

  /*
   * Test cases for calculating social security deduction based on contribution group.
   *
   * Equivalence Partitions:
   * 1. Group 1–4 → 6.35%
   * 2. Group 5–7 → 6.40%
   * 3. Group 8–10 → 6.45%
   * 4. Invalid group (<1 or >10) → IllegalArgumentException
   * 5. Boundary cases: 1, 0, 2, 3, 6, 9, 10, 11
   */
  @Test
  void testSocialSecurityDeduction() {
    Worker cat1Worker = new Worker("Juan", "48392015S", "Soltero", 0, 10000, 12, "Temporal", 1 , "J12345678");
    Worker cat2Worker = new Worker("Juan", "48392015S", "Soltero", 0, 10000, 12, "Temporal", 2, "J12345678");
    Worker cat4Worker = new Worker("Juan", "48392015S", "Soltero", 0, 10000, 12, "Temporal", 4 , "J12345678");
    Worker cat5Worker = new Worker("Juan", "48392015S", "Soltero", 0, 10000, 12, "Temporal", 5, "J12345678");
    Worker cat6Worker = new Worker("Juan", "48392015S", "Soltero", 0, 10000, 12, "Temporal", 6 , "J12345678");
    Worker cat7Worker = new Worker("Juan", "48392015S", "Soltero", 0, 10000, 12, "Temporal", 7 , "J12345678");
    Worker cat8Worker = new Worker("Juan", "48392015S", "Soltero", 0, 10000, 12, "Temporal", 8 , "J12345678");
    Worker cat9Worker = new Worker("Juan", "48392015S", "Soltero", 0, 10000, 12, "Temporal", 9 , "J12345678");
    Worker cat10Worker = new Worker("Juan", "48392015S", "Soltero", 0, 10000, 12, "Temporal", 10 , "J12345678");


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

  /* Equivalence Partitions:
    * 1. Base cases (no children, indefinite contract):
    *    - Income ≤ 12,450 → 19%
    *    - 12,451 – 20,200 → 24%
    *    - 20,201 – 35,200 → 30%
    *    - 35,201 – 60,000 → 37%
    *    - > 60,000 → 45%
    *
    * 2. Children cases (1 to 5 children):
    *    - Each child reduces IRPF by 1% up to a maximum of 5%
    *
    * 3. Temporary contract cases:
    *    - Add 3% to the base IRPF rate
    * 4. Boundary cases:
    *   income: negative income, 0 income, 12449, 12451, 20199, 20201, 35199, 35201, 59999, 60001
    *   children: 0, 4, 6
    *   contract type: "Indefinido", "Temporal", invalid types
    *  */

  @Test
  void testIrpfDeduction() {

    // === BASE CASES (no children, indefinite contract) ===
    //Worker(String name, String dni,  String civilStatus, int children, float totalIncome, int payments, String contract, int category, String cifEmpresa)
    Worker base1 = new Worker("A", "71239485K", "Soltero", 0, 12000, 12, "Indefinido", 3, "J12345678");
    // Frontier value test: 12449
    Worker base1b = new Worker("A", "71239485K", "Soltero", 0, 12449, 12, "Indefinido", 3, "J12345678");
    // Frontier value test: 12451
    Worker base1c = new Worker("A", "71239485K", "Soltero", 0, 12451, 12, "Indefinido", 3, "J12345678");


    Worker base2 = new Worker("B", "71239485K", "Soltero", 0, 18000, 12, "Indefinido", 3, "J12345678");
    // Frontier value test: 20201
    Worker base2b = new Worker("B", "71239485K", "Soltero", 0, 20201, 12, "Indefinido", 3, "J12345678");
    // Frontier value test: 20199
    Worker base2c = new Worker("B", "71239485K", "Soltero", 0, 20199, 12, "Indefinido", 3, "J12345678");

    Worker base3 = new Worker("C", "71239485K", "Soltero", 0, 30000, 12, "Indefinido", 3, "J12345678");
    // Frontier value test: 35199
    Worker base3b = new Worker("C", "71239485K", "Soltero", 0, 35199, 12, "Indefinido", 3, "J12345678");
    // Frontier value test: 35201
    Worker base3c = new Worker("C", "71239485K", "Soltero", 0, 35201, 12, "Indefinido", 3, "J12345678");

    Worker base4 = new Worker("D", "71239485K", "Soltero", 0, 50000, 12, "Indefinido", 3, "J12345678");
    // Frontier value test: 59999
    Worker base4b = new Worker("D", "71239485K", "Soltero", 0, 59999, 12, "Indefinido", 3, "J12345678");
    // Frontier value test: 60001
    Worker base4c = new Worker("D", "71239485K", "Soltero", 0, 60001, 12, "Indefinido", 3, "J12345678");

    Worker base5 = new Worker("E", "71239485K", "Soltero", 0, 80000, 12, "Indefinido", 3, "J12345678");

    payroll.setWorker(base1);
    assertEquals(2280.0, payroll.calculateIrpf(), 0.01); // 19% of 12000
    payroll.setWorker(base1b);
    assertEquals(2365.31, payroll.calculateIrpf(), 0.01); // 19% of 12449

    payroll.setWorker(base1c);
    assertEquals(2988.24, payroll.calculateIrpf(), 0.01); // 24% of 12451
    payroll.setWorker(base2);
    assertEquals(4320.0, payroll.calculateIrpf(), 0.01); // 24% of 18000
    payroll.setWorker(base2c);
    assertEquals(4847.76, payroll.calculateIrpf(), 0.01); // 24% of 20199

    payroll.setWorker(base2b);
    assertEquals(6060.3, payroll.calculateIrpf(), 0.01); // 30% of 20201
    payroll.setWorker(base3);
    assertEquals(9000.0, payroll.calculateIrpf(), 0.01); // 30% of 30000
    payroll.setWorker(base3b);
    assertEquals(10559.7, payroll.calculateIrpf(), 0.01); // 30% of 35199

    payroll.setWorker(base3c);
    assertEquals(13024.37, payroll.calculateIrpf(), 0.01); // 37% of 35201
    payroll.setWorker(base4);
    assertEquals(18500.0, payroll.calculateIrpf(), 0.01); // 37% of 50000
    payroll.setWorker(base4b);
    assertEquals(22199.63, payroll.calculateIrpf(), 0.01); // 37% of 59999

    payroll.setWorker(base4c);
    assertEquals(27000.45, payroll.calculateIrpf(), 0.01); // 45% of 60001
    payroll.setWorker(base5);
    assertEquals(36000.0, payroll.calculateIrpf(), 0.01); // 45% of 80000

    // === CHILDREN CASES (1, 2, 3, 5 children) ===
    // Each child reduces IRPF 1% up to 5%

    // Partition 1: 1 child → -1%
    Worker child1 =  new Worker("A1", "71239485K", "Soltero", 1, 12000, 12, "Indefinido", 3, "J12345678");
    payroll.setWorker(child1);
    assertEquals(2160.0, payroll.calculateIrpf(), 0.01); // 18%

    // Partition 2: 2 children → -2%
    Worker child2 = new Worker("A2", "71239485K", "Soltero", 2, 18000, 12, "Indefinido", 3, "J12345678");
    payroll.setWorker(child2);
    assertEquals(3960.0, payroll.calculateIrpf(), 0.01); // 22%

    // Partition 3: 3 children → -3%
    Worker child3 = new Worker("A3", "71239485K", "Soltero", 3, 30000, 12, "Indefinido", 3, "J12345678");
    payroll.setWorker(child3);
    assertEquals(8100.0, payroll.calculateIrpf(), 0.01); // 27%

    // Partition 4: 5 children → -5% (maximum)
    Worker child5 = new Worker("A5", "71239485K", "Soltero", 5, 80000, 12, "Indefinido", 3, "J12345678");
    payroll.setWorker(child5);
    assertEquals(32000.0, payroll.calculateIrpf(), 0.01); // 40%

    // Partition 5: 6 children → -5% (still maximum)
    Worker child6 = new Worker("Test", "71239485K", "Soltero", 6, 30000, 12, "Indefinido", 3, "J12345678");
    payroll.setWorker(child6);
    // Base 30% - 5% = 25% → 30000 * 0.25 = 7500
    assertEquals(7500.0, payroll.calculateIrpf(), 0.01);

    // === TEMPORARY CONTRACT CASES (+3%) ===
    Worker temp1 = new Worker("T1", "71239485K", "Soltero", 0, 12000, 12, "Temporal", 3, "J12345678");
    payroll.setWorker(temp1);
    assertEquals(2640.0, payroll.calculateIrpf(), 0.01); // 22%

    Worker temp2 = new Worker("T2", "71239485K", "Soltero", 0, 18000, 12, "Temporal", 3, "J12345678");
    payroll.setWorker(temp2);
    assertEquals(4860.0, payroll.calculateIrpf(), 0.01); // 27%

    Worker temp3 = new Worker("T3", "71239485K", "Soltero", 0, 30000, 12, "Temporal", 3, "J12345678");
    payroll.setWorker(temp3);
    assertEquals(9900.0, payroll.calculateIrpf(), 0.01); // 33%

    Worker temp4 = new Worker("T4", "71239485K", "Soltero", 0, 50000, 12, "Temporal", 3, "J12345678");
    payroll.setWorker(temp4);
    assertEquals(20000.0, payroll.calculateIrpf(), 0.01); // 40%

    Worker temp5 = new Worker("T5", "71239485K", "Soltero", 0, 80000, 12, "Temporal", 3, "J12345678");
    payroll.setWorker(temp5);
    assertEquals(38400.0, payroll.calculateIrpf(), 0.01); // 48%
  }

  @Test
  void testCalculateNetSalary() {
    // === Caso 1: Trabajador base, sin hijos, contrato indefinido, categoría 5 ===
    Worker w1 = new Worker("Ana", "71239485K", "Soltero", 0, 30000, 12, "Indefinido", 5, "J12345678");
    payroll.setWorker(w1);

    // IRPF base 30% → 9 000 €
    // Seguridad Social (cat 5 → 6.40%) → 1 920 €
    // Neto esperado = 30 000 - (9 000 + 1 920) = 19 080 €
    assertEquals(19080.0, payroll.calculateNetSalary(), 0.01);

    // === Caso 2: Trabajador con hijos y contrato temporal ===
    Worker w2 = new Worker("Luis", "71239485K", "Casado", 3, 40000, 14, "Temporal", 7, "J12345678");
    payroll.setWorker(w2);

    // IRPF base (tramo 4 → 37%) -3% por hijos +3% temporal = 37%
    // IRPF = 40000 * 0.37 = 14 800 €
    // SS (cat 7 → 6.40%) = 2 560 €
    // Neto esperado = 40000 - (14800 + 2560) = 22 640 €
    assertEquals(22640.0, payroll.calculateNetSalary(), 0.01);

    // === Caso 3: Trabajador de categoría alta con muchos hijos ===
    Worker w3 = new Worker("Lucia", "71239485K", "Casado", 5, 80000, 12, "Indefinido", 10, "J12345678");
    payroll.setWorker(w3);

    // IRPF base 45% -5% por hijos = 40%
    // IRPF = 80000 * 0.40 = 32 000 €
    // SS (cat 10 → 6.45%) = 5 160 €
    // Neto esperado = 80000 - (32 000 + 5 160) = 42 840 €
    assertEquals(42840.0, payroll.calculateNetSalary(), 0.01);
  }

  @Test
  void testCalculateMonthlyNetSalary() {
    // === Caso 1: Trabajador con 12 pagas ===
    Worker w1 = new Worker("Marta", "63314529Z", "Soltero",
        0, 36000, 12, "Indefinido", 5,
        "B34567890");
    payroll.setWorker(w1);

    // IRPF base 37% = 13 320 €
    // Seguridad Social 6.40% = 2 304 €
    // Neto anual = 36 000 - (13 320 + 2 304) = 20 376 €
    // Neto mensual esperado = 20 376 / 12 = 1 698 €
    assertEquals(1698.0, payroll.calculateMonthlyNetSalary(), 0.01);

    // === Caso 1A: Trabajador con 12 pagas ===
    Worker w1a = new Worker("Luis Molina", "10293847J", "Soltero", 1,
        22000f, 12, "Temporal", 4,
        "B34567890");
    payroll.setWorker(w1a);

    // IRPF base 30% -1% hijo +3% temporal = 32%
    // IRPF = 7 040 €
    // SS (cat 4 → 6.35%) = 1 397.0 €
    // Neto anual = 22 000 - (7 040 + 1 397) = 13 563 €
    // Neto mensual esperado = 13 563 / 12 = 1 130.25 €
    assertEquals(1130.25, payroll.calculateMonthlyNetSalary(), 0.01);

    // === Caso 2: Trabajador con 14 pagas ===
    Worker w2 = new Worker("Luis", "71239485K", "Casado", 2, 42000, 14, "Temporal", 7, "J12345678");
    payroll.setWorker(w2);

    // IRPF base 37% -2% hijos +3% temporal = 38%
    // IRPF = 15 960 €
    // SS (cat 7 → 6.40%) = 2 688 €
    // Neto anual = 42 000 - (15 960 + 2 688) = 23 352 €
    // Neto mensual esperado = 23 352 / 14 = 1 668 €
    assertEquals(1668.0, payroll.calculateMonthlyNetSalary(), 0.01);

    // === Caso 2A: Trabajador con 14 pagas ===
    Worker w2a = new Worker(
        "Ana Torres", "71239485K", "Casado", 2,
        28000f, 14, "Indefinido", 2,
        "B12345678");
    payroll.setWorker(w2a);

    // IRPF base 30% -2% hijos = 28%
    // IRPF = 7 840 €
    // SS (cat 2 → 6.35%) = 1 778 €
    // Neto anual = 28 000 - (7 840 + 1 778) = 18 382 €
    // Neto mensual esperado = 18 382 / 14 = 1 312.99 €
    assertEquals(1312.99, payroll.calculateMonthlyNetSalary(), 0.01);

  }
}
