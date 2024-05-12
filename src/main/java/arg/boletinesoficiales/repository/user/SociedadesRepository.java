package arg.boletinesoficiales.repository.user;

import arg.boletinesoficiales.entity.user.Sociedades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SociedadesRepository extends JpaRepository<Sociedades, Integer> {
}
