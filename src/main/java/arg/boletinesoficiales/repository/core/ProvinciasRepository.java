package arg.boletinesoficiales.repository.core;

import arg.boletinesoficiales.entity.core.Provincias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinciasRepository  extends JpaRepository<Provincias, Integer> {

    @Query("SELECT c FROM Provincias c WHERE c.nombre = :name")
    Provincias find_by_name(@Param("name") String name);
}
