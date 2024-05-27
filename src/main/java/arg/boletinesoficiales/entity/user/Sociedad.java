package arg.boletinesoficiales.entity.user;

import arg.boletinesoficiales.entity.core.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name="sociedad", schema = "[user]")
@Getter
@Setter
public class Sociedad {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="mor_user")
    private String usuario; // M o A
    @Column(name="mor_nro_user")
    @ColumnDefault(value = "62")
    private String nroUsuario;
    @Column(name="mor_lote")
    @ColumnDefault(value = "0")
    private String lote;
    @Column(name="mor_codint")
    private int contador;
    @Column(name="mor_matriz")
    private String matriz;
    @Column(name="mor_sucursal")
    private String sucursal;
    @Column(name="mor_sector")
    private String sector;
    @Column(name="mor_cliente")
    @ColumnDefault(value = "")
    private String cliente;
    @Column(name="mor_nombre_completo")
    private String nombreCompleto;
    @Column(name="mor_fecha_nac")
    private String fechaNacimiento;  // (aaaammdd o dd/mm/aaaa)
    @OneToOne
    @JoinColumn(name="mor_sexo_id")
    private Sexo sexo;
    @Column(name="mor_documento1")
    private String documento;
    @Column(name="mor_documento2")
    private String cedula;
    @Column(name="mor_prov_doc2")
    private String provinciaEmisionCedula;
    @Column(name="mor_telefono")
    private String telefono;
    @Column(name="mor_marca_dire_1")
    private String marcaDireccion; // S o J, J el domicilio separado
    @Column(name="mor_calle")
    private String calle;
    @Column(name="mor_numer")
    private String altura;
    @Column(name="mor_piso")
    private String pisoDepto;
    @Column(name="mor_loca")
    private String localidad;
    @OneToOne
    @JoinColumn(name="mor_prov_id")
    private Provincias provincia;
    @Column(name="mor_cp")
    private String codigoPostal;
    @OneToOne
    @JoinColumn(name="mor_est_civil_id")
    private EstadoCivil estadoCivil;
    @OneToOne
    @JoinColumn(name="mor_nacionalidad_id")
    private Nacionalidades nacionalidad;
    @Column(name="mor_relacion")
    private String relacion;
    @OneToOne
    @JoinColumn(name="mor_cargo_id")
    private Cargos cargo;
    @Column(name="mor_cargo_fecha")
    private String fechaCargo;  // (aaaammdd o dd/mm/aaaa)
    @OneToOne
    @JoinColumn(name="mor_cargo_fuente_id")
    private FuenteInformacion fuenteCargo;
    @Column(name="mor_ant_codigo")
    @ColumnDefault(value = "XXX")
    private String antCodigo; // siempre es "XXX"
    @Column(name="mor_campo_1")
    private String campo1;
    @Column(name="mor_campo_2")
    private String campo2;
    @Column(name="mor_campo_3")
    private String campo3;
    @Column(name="mor_campo_4")
    private String campo4;
    @Column(name="mor_campo_5")
    private String campo5;
    @Column(name="mor_campo_6")
    private String campo6;
    @Column(name="mor_campo_7")
    private String campo7;
    @Column(name="mor_campo_8")
    private String campo8;
    @Column(name="mor_ant_fecha")
    private String antFecha;  // (aaaammdd o dd/mm/aaaa)
    @Column(name="mor_archivo")
    private String archivo;
    @Column(name="mor_soc_categoria")
    private String sociedadCategoria;
}
