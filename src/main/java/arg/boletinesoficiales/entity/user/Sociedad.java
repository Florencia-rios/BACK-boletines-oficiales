package arg.boletinesoficiales.entity.user;

import arg.boletinesoficiales.entity.core.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name="sociedad")
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
    @ColumnDefault(value = "AA0029")
    private String matriz;
    @Column(name="mor_sucursal")
    @ColumnDefault(value = "9999")
    private String sucursal;
    @Column(name="mor_sector")
    private String sector; // PC, para altas, y MD para modificaciones
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
    @ColumnDefault(value = "")
    private String cedula;
    @Column(name="mor_prov_doc2")
    @ColumnDefault(value = "")
    private String provinciaEmisionCedula;
    @Column(name="mor_telefono")
    private String telefono;
    @Column(name="mor_marca_dire_1")
    @ColumnDefault(value = "S")
    private String marcaDireccion; // S o J, S el domicilio separado
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
    private String relacion; // si esta casado con el integrante anterior, va C

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
    @ColumnDefault(value = "")
    private String campo1;
    @Column(name="mor_campo_2")
    @ColumnDefault(value = "")
    private String campo2;
    @Column(name="mor_campo_3")
    @ColumnDefault(value = "")
    private String campo3;
    @Column(name="mor_campo_4")
    @ColumnDefault(value = "")
    private String campo4;
    @Column(name="mor_campo_5")
    @ColumnDefault(value = "")
    private String campo5;
    @Column(name="mor_campo_6")
    @ColumnDefault(value = "")
    private String campo6;
    @Column(name="mor_campo_7")
    @ColumnDefault(value = "")
    private String campo7;
    @Column(name="mor_campo_8")
    @ColumnDefault(value = "")
    private String campo8;
    @Column(name="mor_ant_fecha")
    @ColumnDefault(value = "")
    private String antFecha;  // (aaaammdd o dd/mm/aaaa)
    @Column(name="mor_archivo")
    @ColumnDefault(value = "")
    private String archivo;
    @Column(name="mor_soc_categoria")
    @ColumnDefault(value = "")
    private String sociedadCategoria; // Sólo es DOC si la sociedad se disolvió
    @Column(name = "fecha_insercion_boletin")
    private String fechaInsercionBoletin;
    @Column(name = "boletin_oficial") // Doc en base 64
    private String boletinOficial;
}
