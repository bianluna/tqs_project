package repository;

import controller.WorkerRepository;
import model.Worker;

import java.util.ArrayList;
import java.util.List;

public class WorkerRepositoryMock implements WorkerRepository {

  private List<Worker> workers = new ArrayList<>();

  public boolean deleteWasCalled = false;

  public WorkerRepositoryMock() {
    workers.add(new Worker(
        "Juan Pérez", "48392015S", "Soltero", 0,
        24000f, 14, "Indefinido", 3,
        "B12345678"
    ));

    workers.add(new Worker(
        "Ana Torres", "71239485K", "Casada", 2,
        28000f, 14, "Indefinido", 2,
        "B12345678"
    ));

    workers.add(new Worker(
        "Luis Molina", "10293847J", "Soltero", 1,
        22000f, 12, "Temporal", 4,
        "B34567890"
    ));

    workers.add(new Worker(
        "María Varela", "05672193W", "Casada", 3,
        35000f, 14, "Indefinido", 1,
        "B34567890"
    ));

    workers.add(new Worker(
        "Pedro Gómez", "99221188Q", "Soltero", 0,
        18000f, 12, "Parcial", 5,
        "B34567890"
    ));
  }

  public boolean save(Worker worker) {
    // Evitar duplicados por DNI
    if (worker == null || findByDni(worker.getDni()) != null) {
      return false;
    }
    return workers.add(worker);
  }

  public List<Worker> findAll() {
    return new ArrayList<>(workers);
  }

  public boolean delete(String dni) {
    deleteWasCalled = true;
    return workers.removeIf(worker -> worker.getDni().equals(dni));
  }

  public Worker findByDni(String dni) {
    for (Worker worker : workers) {
      if (worker.getDni().equals(dni)) {
        return worker;
      }
    }
    return null;
  }

  public List<Worker> findByCompany(String companyCif) {
    List<Worker> result = new ArrayList<>();
    for (Worker worker : workers) {
      if (worker.getCifEmpresa().equals(companyCif)) {
        result.add(worker);
      }
    }
    return result;
  }

  public void update(String name, String dni, String civilStatus, int children, float totalIncome,
                     int payments, String contract, int category, String cifEmpresa) {
    Worker worker = findByDni(dni);
    if (worker != null) {
      if ("Temporary".equals(worker.getContract()) && "Indefinido".equals(contract)) {
        worker.setContract(contract);
      }
      worker.setTotalIncome(totalIncome);
      worker.setPayments(payments);
      worker.setCategory(category);
      worker.setCivilStatus(civilStatus);
      worker.setChildren(children);
    }
  }
}

