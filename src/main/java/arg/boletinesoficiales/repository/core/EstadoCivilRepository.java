package arg.boletinesoficiales.repository.core;

import arg.boletinesoficiales.entity.core.EstadoCivil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoCivilRepository  extends JpaRepository<EstadoCivil, Integer> {

    @Query("SELECT c FROM EstadoCivil c WHERE c.nombre = :name")
    EstadoCivil find_by_name(@Param("name") String name);
}
