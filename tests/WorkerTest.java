import static org.junit.jupiter.api.Assertions.*;

import model.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WorkerTest {

  //Creation of worker objects for testing
  Worker worker = new Worker();
  Worker emptyWorker = new Worker();

  private WorkerRepositoryMock repository;

  // Set up method to initialize common test data
  @BeforeEach
  void setUp() {
    // This method can be used to set up common test data if needed
    repository = new WorkerRepositoryMock();

    worker = new Worker(
        "Pepito",
        "Casado",
        3,
        35000,
        12,
        "Indefinido",
        7 ,
        "B12345678");

    emptyWorker = new Worker();

  }

  @Test
  void testConstructor() {
    Worker worker = new Worker("Pepito", "Casado", 3, 35000, 12, "Indefinido", 7, "B12345678");

    assertEquals("Pepito", worker.getName());
    assertEquals(worker.getCivilStatus(), "Casado");
    assertTrue(worker.getChildren()==3);
    assertTrue(worker.getTotalIncome()==35000);
    assertTrue(worker.getPayments()==12);
    assertEquals(worker.getContract(), "Indefinido");
    assertEquals(worker.getCategory(), 7);
    assertNotSame("Julio", worker.getName());
  }

  /*
   * Test cases for validating number of payments within a year, which can only be 12 or 14.
   *
   * Equivalence Partitions:
   * 1. Valid payments: 12
   * 2. Valid payments: 14
   * 3. Invalid payments: any other number (-1, 0, 15, 60) (numbers outside the valid range)
   *
   * */

  @Test
  void testValidPayments() {
    // Valid payment 12
    assertTrue(worker.getPayments() == 12);

    // Valid payment 14
    worker.setPayments(14);
    assertTrue(worker.getPayments() == 14);

    // Invalid payment should be ignored (value stays unchanged, in this case it stays as default 0)
    emptyWorker.setPayments(15);
    assertFalse(emptyWorker.getPayments() == 15);
    assertTrue(emptyWorker.getPayments() == 0);
  }


  /*
   * Test cases for validating total income, which must be a positive value and with minumum value of 16000 euros, which is the
   * interprofesional minimum salary within Spain.
   *
   * Equivalence Partitions:
   * 1. Valid total income: positive values greater than or equal to 16000 euros (e.g., 16000, 25000.60, 800000)
   * 2. Invalid total income: negative values and zero (e.g., -5000, -0.01, 0, 15999.99)
   *
   * */
  @Test
  void testTotalIncome() {
    // Valid total income
    assertTrue(worker.getTotalIncome() == 35000);
    worker.setTotalIncome(16000);
    assertTrue(worker.getTotalIncome() == 16000);

  }

  /*
   * Test cases for validating number of children, which must be a non-negative integer.
   *
   * Equivalence Partitions:
   * 1. Valid number of children: non-negative integers (e.g., 0, 1, 2, 3, ...)
   * 2. Invalid number of children: negative integers (e.g., -1, -2, -3, ...)
   *
   *
   * */
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

  /*
   * Test cases for validating worker category, which must be an integer between 1 and 10.
   * Equivalence Partitions:
   * 1. Valid category: integers between 1 and 10 (e.g.,1, 5, 11)
   * 2. Invalid category: integers less than 1 or greater than 10 (e.g., -1, 0, 12, 20)
   *
   *
   * */
  @Test
  void testValidCategory() {
    assertTrue(worker.getCategory()==7);
    worker.setCategory(20);
    assertFalse(worker.getCategory()==20);
  }


  /*
   * Test cases for validating contract type, which must be one of the following strings:
   * “Indefinido”,  “Temporal” “Formacion en Alternancia”, “Formativo para la Obtencion de la Práctica Profesional”
   * Equivalence Partitions:
   * 1. Valid contract types: “Indefinido”,  “Temporal” “Formacion en Alternancia”, “Formativo para la Obtencion de la Práctica Profesional”
   * 2. Invalid contract types: any other string (e.g., "indefinido-invalid", "permanent", "temporary")
   * */
  @Test
  void testContract() {
    // Set contract type temporal
    worker.setContract("Temporal");
    assertEquals("Temporal", worker.getContract());

    // Set contract type indefinido
    worker.setContract("Indefinido");
    assertEquals("Indefinido", worker.getContract());

    // Set contract type Formacion en Alternancia
    worker.setContract("Formacion en Alternancia");
    assertEquals("Formacion en Alternancia", worker.getContract());

    // Attempt to set invalid contract type
    worker.setContract("Permanente");
    assertFalse(worker.getContract().equals("Permanente"));
    assertEquals("Formacion en Alternancia", worker.getContract());

  }
  /*
   * Test cases for validating civil status, which must be one of the following strings:
   * “Soltero”, “Casado”, “Viudo”, “Divorciado”
   * Equivalence Partitions:
   * 1. Valid civil status: “Soltero”,  “Casado”, “Viudo”, “Divorciado”
   * 2. Invalid civil status: any other string (e.g., "Separado", "Solo", "Matrimonio")
   * */
  @Test
  void testCivilStatus() {
    assertEquals("Casado", worker.getCivilStatus());
    assertNotSame("Hijo", worker.getCivilStatus());
  }

  @Test
  void testSaveWorker(){
    boolean result = repository.save(worker);
    assertTrue(result, "Worker should be saved successfully");
  }

  @Test
  void testSaveAndPrintAllWorkers(){
    // Save the worker
    boolean result = repository.save(worker);
    assertTrue(result, "Worker should be saved successfully");

    // Print all workers to verify
    System.out.println("\n=== Lista de todos los Workers ===");
    repository.findAll().forEach(w -> {
      System.out.println("Nombre: " + w.getName());
      System.out.println("Estado Civil: " + w.getCivilStatus());
      System.out.println("Hijos: " + w.getChildren());
      System.out.println("Ingreso Total: " + w.getTotalIncome());
      System.out.println("Pagos: " + w.getPayments());
      System.out.println("Contrato: " + w.getContract());
      System.out.println("Categoría: " + w.getCategory());
      System.out.println("CIF Empresa: " + w.getCifEmpresa());
      System.out.println("----------------------------");
    });

    // Verify that we have at least 6 workers (5 initial + 1 new)
    assertEquals(6, repository.findAll().size(), "Should have 6 workers after saving");
  }

  @Test
  void testDeleteWorker() {
    repository.save(worker);

    boolean deleted = repository.delete(worker.getDni());
    assertTrue(deleted);

    assertNull(repository.findByDni("Pepito"), "Worker should be null after deletion");
  }



}

