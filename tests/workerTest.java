import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class workerTest {

  @Test
  void test(){
    fail("Not yet implemented");
  }

  @Test
  void testConstructor() {
    Worker worker = new Worker("Pepito", "casado", 3, 35000, 12, "indefinido", "7" );

    assertEquals(worker.getName(),"Pepito");
    assertEquals(worker.getcivilStatus(), "casado");
    assertTrue(worker.getChildren()==3);
    assertTrue(worker.gettotalIncome()==35000);
    assertTrue(worker.getPayments()==12);
    assertEquals(worker.getContract(), "indefinido");
    assertEquals(worker.getCategory(), 7);
    assertNotSame("Julio", worker.getName());
  }



}
