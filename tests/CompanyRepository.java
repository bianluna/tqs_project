

import model.Company;

public interface CompanyRepository {
  /**
   * Guarda una compañía en el repositorio.
   * @param company La compañía a guardar
   * @return true si se guardó correctamente, false en caso contrario
   */
  boolean save(Company company);
  boolean update(Company company);
  Company findByCif(String cif);
  boolean delete(String cif);
}
