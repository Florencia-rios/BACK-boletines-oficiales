package arg.boletinesoficiales.repository.core;

import arg.boletinesoficiales.entity.core.EstadoCivil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EstadoCivilRepository  extends JpaRepository<EstadoCivil, Integer> {

    @Query("SELECT c FROM EstadoCivil c WHERE c.nombre = :name")
    EstadoCivil find_by_name(String name);
}
