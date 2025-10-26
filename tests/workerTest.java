import static org.junit.jupiter.api.Assertions.*;

import model.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class workerTest {
  Worker worker = new Worker();
  Worker invalidWorker = new Worker();
  @BeforeEach
  void setUp() {
    // This method can be used to set up common test data if needed
    worker = new Worker(
        "Pepito",
        "casado",
        3,
        35000,
        12,
        "indefinido",
        7 );

    invalidWorker = new Worker(
        "Pepito",
        "casado",
        3,
        35000,
        12,
        "indefinido",
        7 );

  }

  @Test
  void testConstructor() {
    Worker worker = new Worker("Pepito", "casado", 3, 35000, 12, "indefinido", 7 );

    assertEquals("Pepito", worker.getName());
    assertEquals(worker.getCivilStatus(), "casado");
    assertTrue(worker.getChildren()==3);
    assertTrue(worker.getTotalIncome()==35000);
    assertTrue(worker.getPayments()==12);
    assertEquals(worker.getContract(), "indefinido");
    assertEquals(worker.getCategory(), 7);
    assertNotSame("Julio", worker.getName());
  }

  @Test
  void testValidPayments() {
    // Valid payment 12
    assertTrue(worker.getPayments() == 12);

    // Valid payment 14
    worker.setPayments(14);
    assertTrue(worker.getPayments() == 14);

    // Invalid payment should be ignored (value stays unchanged, in this case it stays as default 0)
    invalidWorker.setPayments(15);
    assertFalse(invalidWorker.getPayments() == 15);
    assertTrue(invalidWorker.getPayments() == 0);
  }

  @Test
  void testNegativeTotalIncome() {
    assertThrows(IllegalArgumentException.class, () -> {
      invalidWorker.setTotalIncome(-20000);
    },"Debe lanzar una excepción si el salario neto bruto es negativo." );
  }

  @Test
  void testTotalIncomeNotZero() {
    assertThrows(IllegalArgumentException.class, () -> {
      invalidWorker.setTotalIncome(0);
    },"Debe lanzar una excepción si el salario neto bruto es cero." );
  }

  @Test
  void testChildren() {
    // Valid number of children
    assertEquals(3, worker.getChildren());

    // Attempt to set negative children should not change value
    worker.setChildren(-3);
    assertFalse(worker.getChildren() == -3);
    assertEquals(3, worker.getChildren());

    // Setting zero children
    worker.setChildren(0);
    assertEquals(0, worker.getChildren());
  }

  @Test
  void testValidCategory() {
    assertTrue(worker.getCategory()==7);
    worker.setCategory(20);
    assertFalse(worker.getCategory()==20);
  }

  @Test
  void testContract() {
    // Set contract type temporal
    worker.setContract("temporal");
    assertEquals("temporal", worker.getContract());

    // Set contract type indefinido
    worker.setContract("indefinido");
    assertEquals("indefinido", worker.getContract());

    // Set contract type prácticas
    worker.setContract("prácticas");
    assertEquals("prácticas", worker.getContract());

    // Attempt to set invalid contract type
    worker.setContract("indefinido-invalid");
    assertFalse(worker.getContract().equals("indefinido-invalid"));
    assertEquals("prácticas", worker.getContract());

  }

  @Test
  void testCivilStatus() {
    assertEquals("casado", worker.getCivilStatus());
    assertNotSame("hijo", worker.getCivilStatus());
  }




}

