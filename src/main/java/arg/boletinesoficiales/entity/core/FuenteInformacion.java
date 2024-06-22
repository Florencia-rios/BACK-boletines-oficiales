package arg.boletinesoficiales.entity.core;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name="fuente_informacion")
@Getter
@Setter
public class FuenteInformacion {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="nombre")
    @ColumnDefault(value = "BOL")
    private String nombre; // Sólo si el integrante correspondiente se dió de baja en la sociedad, va BAJ
    @Column(name="codigo")
    private String codigo;
}
