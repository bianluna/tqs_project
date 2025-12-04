package controller;

import model.Worker;

import java.util.List;

public interface WorkerRepository {
  boolean save(Worker worker);
  Worker findByDni(String dni);
  List<Worker> findByCompany(String companyCif);
  boolean delete(String dni);
}
