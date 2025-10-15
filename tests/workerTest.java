import static org.junit.jupiter.api.Assertions.*;

import model.Worker;
import org.junit.jupiter.api.Test;

public class workerTest {

  @Test
  void testConstructor() {
    Worker worker = new Worker("Pepito", "casado", 3, 35000, 12, "indefinido", "7" );

    assertEquals("Pepito", worker.getName());
    assertEquals(worker.getCivilStatus(), "casado");
    assertTrue(worker.getChildren()==3);
    assertTrue(worker.getTotalIncome()==35000);
    assertTrue(worker.getPayments()==12);
    assertEquals(worker.getContract(), "indefinido");
    assertEquals(worker.getCategory(), "7");
    assertNotSame("Julio", worker.getName());
  }

  @Test
  void testValidPayments12(){
      Worker worker12 = new Worker();
      worker12.setPayments(12);
      assertEquals(12, worker12.getPayments());
  }

  @Test
  void testValidPayments14() {
    Worker worker = new Worker();
    worker.setPayments(14);
    assertEquals(14, worker.getPayments());
  }

  @Test
  void testInvalidPaymentsBelow12() {
    Worker worker = new Worker();
    assertThrows(IllegalArgumentException.class, () -> {
      worker.setPayments(11);
    }, "Debe lanzar excepción si el número de pagos es menor que 12");
  }

  @Test
  void testInvalidPaymentsThrowsException() {
    Worker worker = new Worker();
    assertThrows(IllegalArgumentException.class, () -> {
      worker.setPayments(15);
    }, "Debe lanzar una excepción si el número de pagos no es 12 o 14");
  }




}
