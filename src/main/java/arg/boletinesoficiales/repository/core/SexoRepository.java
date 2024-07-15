package arg.boletinesoficiales.repository.core;

import arg.boletinesoficiales.entity.core.Sexo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SexoRepository  extends JpaRepository<Sexo, Integer> {

    @Query("SELECT c FROM Sexo c WHERE c.nombre = :name")
    Sexo find_by_name(String name);
}
