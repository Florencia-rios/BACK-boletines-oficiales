package arg.boletinesoficiales.entity.core;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="SEXO")
@Getter
@Setter
public class Sexo {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name="NOMBRE")
    private String nombre;
    @Column(name="CODIGO")
    private String codigo;
}
