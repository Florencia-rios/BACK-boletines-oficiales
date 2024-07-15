package arg.boletinesoficiales.repository.core;

import arg.boletinesoficiales.entity.core.Nacionalidades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NacionalidadesRepository  extends JpaRepository<Nacionalidades, Integer> {

    @Query("SELECT c FROM Nacionalidades c WHERE c.nombre = :name")
    Nacionalidades find_by_name(String name);
}
