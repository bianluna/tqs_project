import model.Worker;
import java.util.List;

public interface WorkerRepository {
  boolean save(String dni, Worker worker, String companyCif);
  Worker findByDni(String dni);
  List<Worker> findByCompany(String companyCif);
  boolean delete(String dni);
}
