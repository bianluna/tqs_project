import model.Worker;

import java.util.ArrayList;
import java.util.List;

public class WorkerRepositoryMock {

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
        "Luis Molina", "10293847J",  "Soltero", 1,
        22000f, 12, "Temporal", 4,
        "B34567890"
    ));

    workers.add(new Worker(
        "María Varela", "05672193W",  "Casada", 3,
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
    return workers.add(worker);
  }

  public List<Worker> findAll() {
    return new ArrayList<>(workers);
  }

}
