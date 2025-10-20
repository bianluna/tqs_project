import static org.junit.jupiter.api.Assertions.*;

import model.Worker;
import org.junit.jupiter.api.Test;

public class workerTest {

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
    Worker worker = new Worker();

    // Valid payment 12
    worker.setPayments(12);
    assertTrue(worker.getPayments() == 12);

    // Valid payment 14
    worker.setPayments(14);
    assertTrue(worker.getPayments() == 14);

    // Invalid payment should be ignored (value stays unchanged, in this case it stays as default 0)
    Worker invalidWorker = new Worker();
    invalidWorker.setPayments(15);
    assertFalse(invalidWorker.getPayments() == 15);
    assertTrue(invalidWorker.getPayments() == 0);
  }

  @Test
  void testNegativeTotalIncome() {
    Worker worker = new Worker();
    assertThrows(IllegalArgumentException.class, () -> {
      worker.setTotalIncome(-20000);
    },"Debe lanzar una excepción si el salario neto bruto es negativo." );
  }

  @Test
  void testTotalIncomeNotZero() {
    Worker worker = new Worker();
    assertThrows(IllegalArgumentException.class, () -> {
      worker.setTotalIncome(0);
    },"Debe lanzar una excepción si el salario neto bruto es cero." );
  }

  @Test
  void testValidChildren() {
    Worker worker = new Worker();
    assertThrows(IllegalArgumentException.class, () -> {
      worker.setChildren(-3);
    },"Debe lanzar una excepción si el numero de hijos es negativo." );
  }

  @Test
  void testValidCategory() {
    Worker worker = new Worker();
    worker = new Worker("Ana", "soltera", 0, 28000, 14, "temporal", 3);
    assertTrue(worker.getCategory()==3);
    worker.setCategory(20);
    assertFalse(worker.getCategory()==20);
  }


}

