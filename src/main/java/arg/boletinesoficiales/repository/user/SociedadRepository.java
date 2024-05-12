package arg.boletinesoficiales.repository.user;

import arg.boletinesoficiales.entity.user.Sociedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SociedadRepository extends JpaRepository<Sociedad, Integer> {
}
