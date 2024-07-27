package arg.boletinesoficiales.repository.core;

import arg.boletinesoficiales.entity.core.Cargos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CargosRepository extends JpaRepository<Cargos, Integer> {

    @Query("SELECT c FROM Cargos c WHERE c.nombre = :name")
    Cargos find_by_name(@Param("name") String name);

    @Query("SELECT c FROM Cargos c WHERE c.codigo = :code")
    Cargos find_by_code(@Param("code") String code);
}
