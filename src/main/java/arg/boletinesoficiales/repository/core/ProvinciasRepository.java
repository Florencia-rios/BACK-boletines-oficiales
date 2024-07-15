package arg.boletinesoficiales.repository.core;

import arg.boletinesoficiales.entity.core.Provincias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProvinciasRepository  extends JpaRepository<Provincias, Integer> {

    @Query("SELECT c FROM Provincias c WHERE c.nombre = :name")
    Provincias find_by_name(String name);
}
