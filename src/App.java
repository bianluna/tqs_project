import model.Worker;
import model.Company;
import model.Payroll;
import repository.WorkerRepositoryMock;
import repository.CompanyRepositoryMock;
import java.util.List;
import java.util.Scanner;

public class App {
  private static final Scanner scanner = new Scanner(System.in);

  // --- COLORES Y ESTILOS PARA CONSOLA ---
  private static final String RESET = "\u001B[0m";
  private static final String RED = "\u001B[31m";
  private static final String GREEN = "\u001B[32m";
  private static final String YELLOW = "\u001B[33m";
  private static final String BLUE = "\u001B[34m";
  private static final String CYAN = "\u001B[36m";
  private static final String BOLD = "\u001B[1m";


  private WorkerRepositoryMock workerRepository;
  private CompanyRepositoryMock companyRepository;
  private String userType;
  private String identifier;
  private String companyName;

  public App() {
    this.workerRepository = new WorkerRepositoryMock();
    this.companyRepository = new CompanyRepositoryMock();
  }

  public static void main(String[] args) {
    App app = new App();
    app.run();
  }

  private void run() {
    printHeader("SISTEMA DE GESTIÓN DE EMPLEADOS");
    askIdentity();

    boolean exit = false;
    while (!exit) {
      printMenu();
      String option = scanner.nextLine().trim();
      System.out.println(); // Salto de línea estético

      switch (option) {
        case "1":
          if (isEmpresa()) addWorkerFlowForCompany(); else viewWorkerByDni();
          break;
        case "2":
          if (isEmpresa()) listCompanyWorkers(); else listWorkersReadOnly();
          break;
        case "3":
          if (isEmpresa()) deleteWorkerFlow(); else calculateMyPayroll();
          break;
        case "4":
          if (isEmpresa()) calculateWorkerPayroll();
          else printError("Opción no disponible para perfil Trabajador.");
          break;
        case "0":
          exit = true;
          break;
        default:
          printError("Opción no válida. Intente nuevamente.");
      }

      if (!exit) waitForEnter();
    }
    printSuccess("¡Hasta luego! Cerrando sistema...");
  }

  // --- UI HELPERS ---

  private void printHeader(String title) {
    System.out.println(BLUE + "============================================================" + RESET);
    System.out.println(BOLD + "       " + title.toUpperCase() + RESET);
    System.out.println(BLUE + "============================================================" + RESET);
  }

  private void printSubHeader(String title) {
    System.out.println(CYAN + "\n--- " + title.toUpperCase() + " ---" + RESET);
  }

  private void printSuccess(String msg) {
    System.out.println(GREEN + "✔ " + msg + RESET);
  }

  private void printError(String msg) {
    System.out.println(RED + "✘ ERROR: " + msg + RESET);
  }

  private void waitForEnter() {
    System.out.println(YELLOW + "\nPresione [ENTER] para continuar..." + RESET);
    scanner.nextLine();
  }

  // --- FLUJO PRINCIPAL ---

  private void askIdentity() {
    System.out.println("Por favor, identifíquese para acceder al sistema.");
    System.out.println("1) " + CYAN + "Empresa" + RESET);
    System.out.println("2) " + CYAN + "Trabajador" + RESET);
    System.out.print(BOLD + "> Seleccione una opción: " + RESET);

    String choice = scanner.nextLine().trim();
    if ("1".equals(choice)) {
      userType = "empresa";
      System.out.print("Introduce tu " + BOLD + "CIF" + RESET + ": ");
      identifier = scanner.nextLine().trim();

      // Buscar el nombre de la empresa en el repositorio
      Company company = companyRepository.findByCif(identifier);
      if (company != null) {
        companyName = company.getName();
        printSuccess("Bienvenido/a, " + companyName + "!");
      } else {
        printError("CIF no encontrado en el sistema. Continuando con CIF: " + identifier);
        companyName = "Empresa desconocida";
      }
    } else {
      userType = "trabajador";
      System.out.print("Introduce tu " + BOLD + "DNI" + RESET + ": ");
      identifier = scanner.nextLine().trim();
      companyName = null;
    }
    printSuccess("Identidad confirmada: " + userType.toUpperCase());
  }

  private boolean isEmpresa() { return "empresa".equalsIgnoreCase(userType); }

  private void printMenu() {
    System.out.println("\n" + BLUE + "┌──────────────────────────────────────────────┐" + RESET);
    if (isEmpresa()) {
      String welcome = (companyName != null && !companyName.isBlank()) ? companyName : "Empresa";
      System.out.printf(BLUE + "│ %-44s │%n" + RESET, "Usuario: " + BOLD + welcome + RESET + " (CIF: " + identifier + ")");
      System.out.println(BLUE + "├──────────────────────────────────────────────┤" + RESET);
      System.out.println("│ " + CYAN + "1." + RESET + " Agregar nuevo trabajador                   │");
      System.out.println("│ " + CYAN + "2." + RESET + " Listar plantilla (Ver Tabla)               │");
      System.out.println("│ " + CYAN + "3." + RESET + " Eliminar trabajador (Despido)              │");
      System.out.println("│ " + CYAN + "4." + RESET + " Calcular nómina de un trabajador          │");
    } else {
      System.out.printf(BLUE + "│ %-44s │%n" + RESET, "Usuario: Trabajador (DNI: " + identifier + ")");
      System.out.println(BLUE + "├──────────────────────────────────────────────┤" + RESET);
      System.out.println("│ " + CYAN + "1." + RESET + " Ver mis datos personales                   │");
      System.out.println("│ " + CYAN + "2." + RESET + " Ver listado general (Solo lectura)         │");
      System.out.println("│ " + CYAN + "3." + RESET + " Calcular mi nómina                         │");
    }
    System.out.println(BLUE + "├──────────────────────────────────────────────┤" + RESET);
    System.out.println("│ " + RED + "0. Salir" + RESET + "                                     │");
    System.out.println(BLUE + "└──────────────────────────────────────────────┘" + RESET);
    System.out.print(BOLD + "> Seleccione una opción: " + RESET);
  }

  // --- ACCIONES DE NEGOCIO ---

  private void addWorkerFlowForCompany() {
    printSubHeader("Alta de Nuevo Trabajador");
    try {
      System.out.print("Nombre completo: ");
      String name = scanner.nextLine().trim();

      System.out.print("DNI: ");
      String dni = scanner.nextLine().trim();

      System.out.print("Estado Civil (Soltero/Casado/Divorciado/Viudo): ");
      String civilStatus = scanner.nextLine().trim();

      System.out.print("Hijos: ");
      int children = Integer.parseInt(scanner.nextLine().trim());

      System.out.print("Salario Bruto Anual: ");
      float totalIncome = Float.parseFloat(scanner.nextLine().trim());

      System.out.print("Pagas (12/14): ");
      int payments = Integer.parseInt(scanner.nextLine().trim());

      System.out.print("Tipo de Contrato (Indefinido/Temporal/Formacion en Alternancia): ");
      String contract = scanner.nextLine().trim();

      System.out.print("Categoría (0-10): ");
      int category = Integer.parseInt(scanner.nextLine().trim());

      Worker worker = new Worker(name, dni, civilStatus, children, totalIncome, payments, contract, category, identifier);

      if (workerRepository.save(worker)) {
        printSuccess("Trabajador " + name + " dado de alta correctamente.");
      } else {
        printError("No se pudo guardar el registro.");
      }
    } catch (NumberFormatException e) {
      printError("Valor numérico inválido. Operación cancelada.");
    } catch (Exception e) {
      printError("Error inesperado: " + e.getMessage());
    }
  }

  private void listCompanyWorkers() {
    printSubHeader("Plantilla de " + (companyName != null ? companyName : identifier));
    List<Worker> list = workerRepository.findByCompany(identifier);
    printWorkerTable(list);
  }

  private void listWorkersReadOnly() {
    printSubHeader("Directorio Global de Empleados");
    List<Worker> list = workerRepository.findAll();
    printWorkerTable(list);
  }

  private void viewWorkerByDni() {
    printSubHeader("Ficha del Empleado");
    Worker me = workerRepository.findByDni(identifier);
    if (me == null) {
      printError("No constan datos para el DNI " + identifier);
    } else {
      // Mostramos una ficha detallada vertical en lugar de tabla
      System.out.println(YELLOW + "┌──────────────────────────────────────────────┐" + RESET);
      System.out.printf("│ %-15s : %s%n", "Nombre", BOLD + me.getName() + RESET);
      System.out.printf("│ %-15s : %s%n", "DNI", me.getDni());
      System.out.printf("│ %-15s : %s%n", "Estado Civil", me.getCivilStatus());
      System.out.printf("│ %-15s : %d%n", "Hijos", me.getChildren());
      System.out.printf("│ %-15s : %s%n", "Contrato", me.getContract());
      System.out.printf("│ %-15s : %.2f €%n", "Salario Bruto", me.getTotalIncome());
      System.out.printf("│ %-15s : %d pagas%n", "Pagas", me.getPayments());
      System.out.printf("│ %-15s : %d%n", "Categoría", me.getCategory());
      System.out.println(YELLOW + "└──────────────────────────────────────────────┘" + RESET);
    }
  }

  private void deleteWorkerFlow() {
    printSubHeader("Baja de Trabajador");
    System.out.print("Ingrese el DNI del trabajador a eliminar: ");
    String dni = scanner.nextLine().trim();

    // Confirmación simple
    Worker w = workerRepository.findByDni(dni);
    if (w != null) {
      System.out.print(YELLOW + "¿Seguro que desea eliminar a " + w.getName() + "? (s/n): " + RESET);
      String confirm = scanner.nextLine();
      if ("s".equalsIgnoreCase(confirm)) {
        boolean deleted = workerRepository.delete(dni);
        if (deleted) printSuccess("Trabajador eliminado del sistema.");
      } else {
        System.out.println("Operación cancelada.");
      }
    } else {
      printError("No se encontró ningún trabajador con ese DNI.");
    }
  }

  // --- NUEVAS FUNCIONALIDADES DE NÓMINA ---

  /**
   * Calcula y muestra la nómina del trabajador actual (perfil trabajador)
   */
  private void calculateMyPayroll() {
    printSubHeader("Cálculo de Mi Nómina");
    Worker me = workerRepository.findByDni(identifier);

    if (me == null) {
      printError("No se encontraron datos para el DNI " + identifier);
      return;
    }

    displayPayrollDetails(me);
  }

  /**
   * Permite a la empresa calcular la nómina de cualquier trabajador
   */
  private void calculateWorkerPayroll() {
    printSubHeader("Cálculo de Nómina de Trabajador");
    System.out.print("Ingrese el DNI del trabajador: ");
    String dni = scanner.nextLine().trim();

    Worker worker = workerRepository.findByDni(dni);

    if (worker == null) {
      printError("No se encontró ningún trabajador con ese DNI.");
      return;
    }

    // Verificar que el trabajador pertenece a esta empresa
    if (!worker.getCifEmpresa().equals(identifier)) {
      printError("Este trabajador no pertenece a su empresa.");
      return;
    }

    displayPayrollDetails(worker);
  }

  /**
   * Muestra los detalles completos de la nómina de un trabajador
   */
  private void displayPayrollDetails(Worker worker) {
    // Generar código de nómina (puede ser más sofisticado)
    String payrollCode = "NOM-" + worker.getDni() + "-" + System.currentTimeMillis();

    // Crear objeto Payroll y calcular
    Payroll payroll = new Payroll(payrollCode, worker);

    double irpf = payroll.calculateIrpf();
    double sgs = payroll.calculateSocialSecurity();
    double netSalary = payroll.calculateNetSalary();
    double monthlyNet = payroll.calculateMonthlyNetSalary();
    double monthlyGross = payroll.paymentsPerMonth();

    // Mostrar información detallada
    System.out.println(GREEN + "┌────────────────────────────────────────────────────────┐" + RESET);
    System.out.println(GREEN + "│" + BOLD + "              DETALLE DE NÓMINA                       " + RESET + GREEN + "│" + RESET);
    System.out.println(GREEN + "├────────────────────────────────────────────────────────┤" + RESET);
    System.out.printf("│ %-30s : %-20s │%n", "Código Nómina", payrollCode);
    System.out.printf("│ %-30s : %-20s │%n", "Trabajador", worker.getName());
    System.out.printf("│ %-30s : %-20s │%n", "DNI", worker.getDni());
    System.out.println(GREEN + "├────────────────────────────────────────────────────────┤" + RESET);
    System.out.printf("│ %-30s : %19.2f € │%n", "Salario Bruto Anual", worker.getTotalIncome());
    System.out.printf("│ %-30s : %19.2f € │%n", "Salario Bruto Mensual", monthlyGross);
    System.out.printf("│ %-30s : %20d │%n", "Número de Pagas", worker.getPayments());
    System.out.println(GREEN + "├────────────────────────────────────────────────────────┤" + RESET);
    System.out.println(GREEN + "│" + BOLD + "              DEDUCCIONES                             " + RESET + GREEN + "│" + RESET);
    System.out.printf("│ %-30s : %19.2f € │%n", "IRPF (anual)", irpf);
    System.out.printf("│ %-30s : %19.2f € │%n", "Seguridad Social (anual)", sgs);
    System.out.printf("│ %-30s : %19.2f € │%n", "Total Deducciones", (irpf + sgs));
    System.out.println(GREEN + "├────────────────────────────────────────────────────────┤" + RESET);
    System.out.println(GREEN + "│" + BOLD + "              SALARIO NETO                            " + RESET + GREEN + "│" + RESET);
    System.out.printf("│ %-30s : " + BOLD + "%19.2f € " + RESET + "│%n", "Salario Neto Anual", netSalary);
    System.out.printf("│ %-30s : " + BOLD + "%19.2f € " + RESET + "│%n", "Salario Neto Mensual", monthlyNet);
    System.out.println(GREEN + "└────────────────────────────────────────────────────────┘" + RESET);

    // Información adicional del trabajador
    System.out.println("\n" + CYAN + "Información adicional:" + RESET);
    System.out.println("  • Estado Civil: " + worker.getCivilStatus());
    System.out.println("  • Número de Hijos: " + worker.getChildren());
    System.out.println("  • Tipo de Contrato: " + worker.getContract());
    System.out.println("  • Categoría: " + worker.getCategory());
  }

  // --- MÉTODO PARA IMPRIMIR TABLA BONITA ---
  private void printWorkerTable(List<Worker> workers) {
    if (workers.isEmpty()) {
      System.out.println(YELLOW + "   (La lista está vacía)" + RESET);
      return;
    }

    // Encabezado de la tabla
    String format = "| %-20s | %-10s | %-12s | %-10s | %-10s |%n";
    String line = "+----------------------+------------+--------------+------------+------------+";

    System.out.println(line);
    System.out.printf(format, "NOMBRE", "DNI", "CONTRATO", "SALARIO", "CIF EMP.");
    System.out.println(line);

    // Filas
    for (Worker w : workers) {
      System.out.printf(format,
          truncate(w.getName(), 20),
          w.getDni(),
          truncate(w.getContract(), 12),
          String.format("%.0f€", w.getTotalIncome()),
          w.getCifEmpresa()
      );
    }
    System.out.println(line);
    System.out.println("Total registros: " + workers.size());
  }

  // Ayuda a que las columnas no se rompan si el texto es muy largo
  private String truncate(String value, int width) {
    if (value == null) return "";
    if (value.length() > width) {
      return value.substring(0, width - 3) + "...";
    }
    return value;
  }
}