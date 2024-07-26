package arg.boletinesoficiales.entity.core;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="PROVINCIAS")
@Getter
@Setter
public class Provincias {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="NOMBRE")
    private String nombre;
    @Column(name="CODIGO")
    private String codigo;
}
