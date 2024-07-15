package arg.boletinesoficiales.repository.core;

import arg.boletinesoficiales.entity.core.Cargos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CargosRepository extends JpaRepository<Cargos, Integer> {

    @Query("SELECT c FROM Cargos c WHERE c.nombre = :name")
    Cargos find_by_name(String name);
}
