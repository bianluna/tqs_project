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
        "11768496V",
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
    Worker worker = new Worker("Pepito", "11768496V", "Casado", 3,
        35000, 12, "Indefinido", 7,
        "B12345678");

    assertEquals("Pepito", worker.getName());
    assertEquals("Casado", worker.getCivilStatus());
    assertEquals(3, worker.getChildren());
    assertEquals(35000, worker.getTotalIncome());
    assertEquals(12, worker.getPayments());
    assertEquals("Indefinido", worker.getContract());
    assertEquals(7, worker.getCategory());
    assertNotSame("Julio", worker.getName());
  }

  /*
   * Test cases for validating number of payments within a year, which can only be 12 or 14.
   *
   * Equivalence Partitions:
   * 1. Valid payments: 12, 14
   * 2. Invalid payments: any other number (-1, 0, 15, 60) (numbers outside the valid range)
   * 3. Boundary cases: 11, 12, 13, 14, 15
   * */

  @Test
  void testValidPayments() {
    // Valid payment 12
    assertEquals(12, worker.getPayments());

    // Valid payment 14
    worker.setPayments(14);
    assertEquals(14, worker.getPayments());

    // Invalid payment should throw an exception
    Exception exceptionInvalidPayments11 = assertThrows(IllegalArgumentException.class, () -> {
      emptyWorker.setPayments(11);
    });
    assertEquals("Número de pagas inválido. Debe ser 12 o 14.", exceptionInvalidPayments11.getMessage());

    // Invalid payment should throw an exception
    Exception exceptionInvalidPayments13 = assertThrows(IllegalArgumentException.class, () -> {
      emptyWorker.setPayments(13);
    });
    assertEquals("Número de pagas inválido. Debe ser 12 o 14.", exceptionInvalidPayments13.getMessage());

    // Invalid payment should throw an exception
    Exception exceptionInvalidPayments = assertThrows(IllegalArgumentException.class, () -> {
      emptyWorker.setPayments(15);
    });
    assertEquals("Número de pagas inválido. Debe ser 12 o 14.", exceptionInvalidPayments.getMessage());
  }


  /*
   * Test cases for validating total income, which must be a positive value and with
   * minimum value of 16000 euros, which is the
   * interprofessional minimum salary within Spain.
   *
   * Equivalence Partitions:
   * 1. Valid total income: positive values greater than or equal to 16000 euros (e.g., 16000, 25000.60, 800000)
   * 2. Invalid total income: negative values and zero (e.g., -5000, -0.01, 0, 15999.99)
   * 3. Boundary case: exactly 16000.00 euros, -0.01, 0, 1, 15 999.99, 16 000.01
   * */
  @Test
  void testTotalIncome() {
    // Valid total income
    assertEquals(35000, worker.getTotalIncome());
    worker.setTotalIncome(16000);
    assertEquals(16000, worker.getTotalIncome());

    // Attempt to set negative total income should throw exception
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      worker.setTotalIncome(-5000);
    });
    assertEquals("El salario debe ser positivo.",
        exception.getMessage());

    // Attempt to set zero total income should throw exception
    Exception exceptionZero = assertThrows(IllegalArgumentException.class, () -> {
      worker.setTotalIncome(0);
    });
    assertEquals("El salario debe ser positivo.",
        exceptionZero.getMessage());

    // Boundary cases
    Exception exceptionBoundaryCloseZeroNegative = assertThrows(IllegalArgumentException.class, () -> {
      worker.setTotalIncome(-0.01f);
    });
    assertEquals("El salario debe ser positivo.",
        exceptionBoundaryCloseZeroNegative.getMessage());

    worker.setTotalIncome(0.01f);
    assertEquals(0.01f, worker.getTotalIncome());

    worker.setTotalIncome(15999.99f);
    assertEquals(15999.99f, worker.getTotalIncome());

    worker.setTotalIncome(16000.01f);
    assertEquals(16000.01f, worker.getTotalIncome());
  }

  /*
   * Test cases for validating number of children, which must be a non-negative integer.
   *
   * Equivalence Partitions:
   * 1. Valid number of children: non-negative integers (e.g., 0, 1, 2, 3, ...)
   * 2. Invalid number of children: negative integers (e.g., -1, -2, -3, ...)
   * 3. Boundary case: -1, 0, 1
   * */
  @Test
  void testChildren() {
    // Valid number of children
    assertEquals(3, worker.getChildren());

    // Attempt to set negative children should throw an exception
    Exception exceptionNegativeChildren = assertThrows(IllegalArgumentException.class, () -> {
      worker.setChildren(-3);
    });
    assertEquals("Número de hijos inválido. No puede ser negativo.",
        exceptionNegativeChildren.getMessage());

    // Boundary case: setting -1 child
    // Setting negative children should throw an exception
    Exception exceptionBoundaryNegative = assertThrows(IllegalArgumentException.class, () -> {
      worker.setChildren(-1);
    });
    assertEquals("Número de hijos inválido. No puede ser negativo.",
        exceptionBoundaryNegative.getMessage());

    // Setting zero children should be valid
    worker.setChildren(0);
    assertEquals(0, worker.getChildren());

    // Boundary case: setting 1 child
    // Setting one child should be valid
    worker.setChildren(1);
    assertEquals(1, worker.getChildren());

    // Setting a high number of children should be valid
    worker.setChildren(6);
    assertEquals(6, worker.getChildren());
  }

  /*
   * Test cases for validating worker category, which must be an integer between 1 and 10.
   * Equivalence Partitions:
   * 1. Valid category: integers between 1 and 10 (e.g.,1, 5, 11)
   * 2. Invalid category: integers less than 1 or greater than 10 (e.g., -1, 0, 12, 20)
   * 3. Boundary cases: -1, 0, 1, 10, 11
   * */
  @Test
  void testValidCategory() {
    assertEquals(7, worker.getCategory());

    // Boundary case: setting category to -1
    // Attempt to set invalid category less than 0 should throw an exception
    Exception exceptionInvalidCategoryLow = assertThrows(IllegalArgumentException.class, () -> {
      worker.setCategory(-1);
    });
    assertEquals("Categoría inválida. Debe estar entre 0 y 10.",
        exceptionInvalidCategoryLow.getMessage());

    // Boundary case: setting category to 0
    worker.setCategory(0);
    assertEquals(0, worker.getCategory());

    // Valid category: setting category to 1
    worker.setCategory(1);
    assertEquals(1, worker.getCategory());

    // Valid category: setting category to 10
    worker.setCategory(10);
    assertEquals(10, worker.getCategory());

    // Boundary case: setting category to 11
    Exception exceptionInvalidCategoryHigh = assertThrows(IllegalArgumentException.class, () -> {
      worker.setCategory(11);
    });
    assertEquals("Categoría inválida. Debe estar entre 0 y 10.",
        exceptionInvalidCategoryHigh.getMessage());
  }


  /*
   * Test cases for validating contract type, which must be one of the following strings:
   * “Indefinido”,  “Temporal” “Formacion en Alternancia”, “Formativo para la Obtencion de la Práctica Profesional”
   * Equivalence Partitions:
   * 1. Valid contract types: “Indefinido”,  “Temporal” “Formacion en Alternancia”,
   * “Formativo para la Obtencion de la Práctica Profesional”
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

    // Attempt to set invalid contract type should throw an exception
    Exception exceptionInvalidContract = assertThrows(IllegalArgumentException.class, () -> {
      worker.setContract("permanent");
    });
    assertEquals("Tipo de contrato inválido. Debe ser 'Indefinido', 'Temporal', 'Formacion en Alternancia' o " +
        "'Formativo para la Obtencion de la Práctica Profesional'.", exceptionInvalidContract.getMessage());
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

    // Set civil status to Hijo (invalid) should throw an exception
    Exception exceptionInvalidCivilStatus = assertThrows(IllegalArgumentException.class, () -> {
      worker.setCivilStatus("Hijo");
    });
    assertEquals("Estado civil inválido. Debe ser 'Soltero', 'Casado', 'Divorciado' o 'Viudo'.",
        exceptionInvalidCivilStatus.getMessage());
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
      System.out.println("DNI: " + w.getDni());
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

    assertNull(repository.findByDni("11768496V"), "Worker should be null after deletion");
  }

  @Test
  void testUpdateEmployeeData(){
    repository.save(worker);

    // Update worker data
    Worker existingWorker = (Worker) repository.findByDni("11768496V");
    assertNotNull(existingWorker, "Worker should exist before update");

    repository.update("Pepito",
        "11768496V",
        "Casado",
        3,
        40000,
        14,
        "Temporal",
        7 ,
        "B12345678");

    // Retrieve updated worker
    Worker updatedWorker = (Worker) repository.findByDni("11768496V");
    assertNotNull(updatedWorker, "Updated worker should exist");
    // Print all workers to verify
    System.out.println("\n=== Lista de todos los Workers ===");
    repository.findAll().forEach(w -> {
      System.out.println("Nombre: " + w.getName());
      System.out.println("DNI: " + w.getDni());
      System.out.println("Estado Civil: " + w.getCivilStatus());
      System.out.println("Hijos: " + w.getChildren());
      System.out.println("Ingreso Total: " + w.getTotalIncome());
      System.out.println("Pagos: " + w.getPayments());
      System.out.println("Contrato: " + w.getContract());
      System.out.println("Categoría: " + w.getCategory());
      System.out.println("CIF Empresa: " + w.getCifEmpresa());
      System.out.println("----------------------------");
    });
    // Verify updates
    assertEquals(40000, updatedWorker.getTotalIncome(), "Total income should be updated to 40000");
    assertEquals(14, updatedWorker.getPayments(), "Number of payments should be updated to 14");
    assertEquals("Indefinido", updatedWorker.getContract(), "Contract should not be updated to Temporal");
  }

  @Test
  void testValidDni(){
    Worker worker = new Worker();

    // 1. Asignar un DNI válido inicial
    String validDni = "11768496V";
    worker.setDni(validDni);
    assertEquals(validDni, worker.getDni());

    // 2. Intentar asignar uno con letra incorrecta
    worker.setDni("48392015F"); // Letra falsa

    // 3. Verificar que el DNI sigue siendo el antiguo (el cambio fue rechazado)
    assertEquals(validDni, worker.getDni(), "El worker no debería aceptar DNIs matemáticamente incorrectos");

    // 4. Intentar asignar un DNI con formato inválido
    worker.setDni("1234ABC"); // Formato inválido
    assertEquals(validDni, worker.getDni(), "El worker no debería aceptar DNIs con formato inválido");

    // 5. Asignar un DNI nulo
    worker.setDni(null);
    assertEquals(validDni, worker.getDni(),
        "El worker no debería aceptar DNIs nulos");

    // 6. Asignar un DNI no nulo pero vacío
    worker.setDni("");
    assertEquals(validDni, worker.getDni(),
        "El worker no debería aceptar DNIs vacíos");
  }

  // Additional tests for loop testing coverage
  // Test 1: Omitir el bucle (n = 0) - String vacío
  @Test
  public void testLoopCountDigitsZeroIterations() {
    Worker worker = new Worker();
    int result = worker.countDigits("");
    assertEquals(0, result);
    // El bucle no se ejecuta ninguna vez
  }

  // Test 2: Una iteración (n = 1) - Un solo carácter
  @Test
  public void testLoopCountDigitsOneIteration() {
    Worker worker = new Worker();
    int result = worker.countDigits("5");
    assertEquals(1, result);
    // El bucle se ejecuta exactamente una vez
  }

  // Test 3: Dos iteraciones (n = 2)
  @Test
  public void testLoopCountDigitsTwoIterations() {
    Worker worker = new Worker();
    int result = worker.countDigits("5A");
    assertEquals(1, result);
    // El bucle se ejecuta dos veces, solo uno es dígito
  }

  // Test 4: Valor típico (n = 8) - DNI normal
  @Test
  public void testLoopCountDigitsTypicalValue() {
    Worker worker = new Worker();
    int result = worker.countDigits("12345678");
    assertEquals(8, result);
    // El bucle se ejecuta 8 veces (valor típico para DNI)
  }

  // Test 5: Valor típico con letras mezcladas
  @Test
  public void testLoopCountDigitsTypicalMixed() {
    Worker worker = new Worker();
    int result = worker.countDigits("12345678Z");
    assertEquals(8, result);
    // El bucle se ejecuta 9 veces, 8 son dígitos
  }

  // Test 6: Valor grande (n = max - 1)
  @Test
  public void testLoopCountDigitsNearMaximum() {
    Worker worker = new Worker();
    String longString = "123456789012345"; // 15 caracteres
    int result = worker.countDigits(longString);
    assertEquals(15, result);
  }

  // Test 7: Valor muy grande (n = max)
  @Test
  public void testLoopCountDigitsMaximum() {
    Worker worker = new Worker();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 100; i++) {
      sb.append(i % 10);
    }
    int result = worker.countDigits(sb.toString());
    assertEquals(100, result);
    // El bucle se ejecuta 100 veces
  }

  // Test 8: String null (caso especial)
  @Test
  public void testLoopCountDigitsNull() {
    Worker worker = new Worker();
    int result = worker.countDigits(null);
    assertEquals(0, result);
    // El bucle no se ejecuta (condición de guarda)
  }

}

