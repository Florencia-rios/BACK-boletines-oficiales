package arg.boletinesoficiales.entity.core;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="CARGOS")
@Getter
@Setter
public class Cargos {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="NOMBRE")
    private String nombre;
    @Column(name="CODIGO")
    private String codigo;
}


