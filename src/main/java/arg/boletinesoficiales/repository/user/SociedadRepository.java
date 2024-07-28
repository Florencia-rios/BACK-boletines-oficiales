package arg.boletinesoficiales.repository.user;

import arg.boletinesoficiales.entity.user.Sociedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SociedadRepository extends JpaRepository<Sociedad, Integer> {

    @Query("SELECT s.usuario, s.nroUsuario, s.lote, s.contador, s.matriz, s.sucursal, s.sector, s.cliente, " +
            "s.nombreCompleto, s.fechaNacimiento, sx.codigo, s.documento, s.cedula, p1.codigo, s.telefono, " +
            "s.marcaDireccion, s.calle, s.altura, s.pisoDepto, s.localidad, p2.codigo, s.codigoPostal, " +
            "ec.codigo, n.codigo, s.relacion, c.codigo, s.fechaCargo, s.fuenteCargo, s.antCodigo, s.campo1, " +
            "s.campo2, s.campo3, s.campo4, s.campo5, s.campo6, s.campo7, s.campo8, s.antFecha, s.archivo, " +
            "s.sociedadCategoria " +
            "FROM Sociedad s " +
            "LEFT JOIN s.sexo sx " +
            "LEFT JOIN s.provinciaEmisionCedula p1 " +
            "LEFT JOIN s.provincia p2 " +
            "LEFT JOIN s.estadoCivil ec " +
            "LEFT JOIN s.nacionalidad n " +
            "LEFT JOIN s.cargo c " +
            "WHERE s.fechaInsercionBoletin = :fechaInsercionBoletin")
    List<Object[]> findSociedadByFechaInsercionBoletin(@Param("fechaInsercionBoletin") String fechaInsercionBoletin);
}
