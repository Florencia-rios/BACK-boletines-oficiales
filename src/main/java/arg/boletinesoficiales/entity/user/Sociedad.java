package arg.boletinesoficiales.entity.user;

import arg.boletinesoficiales.entity.core.*;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name="sociedad")
@Getter
public class Sociedad {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="mor_user")
    private String usuario = "";

    @Column(name="mor_nro_user")
    private String nroUsuario = "62";

    @Column(name="mor_lote")
    private String lote = "0";

    @Column(name="mor_codint")
    private int contador;

    @Column(name="mor_matriz")
    private String matriz = "AA0029";

    @Column(name="mor_sucursal")
    private String sucursal = "9999";

    @Column(name="mor_sector")
    private String sector;

    @Column(name="mor_cliente")
    private String cliente = "";

    @Column(name="mor_nombre_completo")
    private String nombreCompleto;

    @Column(name="mor_fecha_nac")
    private String fechaNacimiento = "";

    @OneToOne
    @JoinColumn(name="mor_sexo_id")
    private Sexo sexo;

    @Column(name="mor_documento1")
    private String documento = "";

    @Column(name="mor_documento2")
    private String cedula = "";

    @OneToOne
    @JoinColumn(name="mor_prov_doc2_id")
    private Provincias provinciaEmisionCedula;

    @Column(name="mor_telefono")
    private String telefono = "";

    @Column(name="mor_marca_dire_1")
    private String marcaDireccion = "S";

    @Column(name="mor_calle")
    private String calle = "";

    @Column(name="mor_numer")
    private String altura = "";

    @Column(name="mor_piso")
    private String pisoDepto = "";

    @Column(name="mor_loca")
    private String localidad = "";

    @OneToOne
    @JoinColumn(name="mor_prov_id")
    private Provincias provincia;

    @Column(name="mor_cp")
    private String codigoPostal = "";

    @OneToOne
    @JoinColumn(name="mor_est_civil_id")
    private EstadoCivil estadoCivil;

    @OneToOne
    @JoinColumn(name="mor_nacionalidad_id")
    private Nacionalidades nacionalidad;

    @Column(name="mor_relacion")
    private String relacion = "";

    @OneToOne
    @JoinColumn(name="mor_cargo_id")
    private Cargos cargo;

    @Column(name="mor_cargo_fecha")
    private String fechaCargo = "";

    @Column(name="mor_cargo_fuente")
    private String fuenteCargo = "BOL";

    @Column(name="mor_ant_codigo")
    private String antCodigo = "XXX";

    @Column(name="mor_campo_1")
    private String campo1 = "";

    @Column(name="mor_campo_2")
    private String campo2 = "";

    @Column(name="mor_campo_3")
    private String campo3 = "";

    @Column(name="mor_campo_4")
    private String campo4 = "";

    @Column(name="mor_campo_5")
    private String campo5 = "";

    @Column(name="mor_campo_6")
    private String campo6 = "";

    @Column(name="mor_campo_7")
    private String campo7 = "";

    @Column(name="mor_campo_8")
    private String campo8 = "";

    @Column(name="mor_ant_fecha")
    private String antFecha = "";

    @Column(name="mor_archivo")
    private String archivo = "";

    @Column(name="mor_soc_categoria")
    private String sociedadCategoria = "";

    @Column(name = "fecha_insercion_boletin")
    private String fechaInsercionBoletin;

    @Column(name = "boletin_oficial")
    private byte[] boletinOficial;

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setNroUsuario(String nroUsuario) {
        this.nroUsuario = nroUsuario;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public void setMatriz(String matriz) {
        this.matriz = matriz;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setNombreCompleto(String nombreCompleto) {
        String nombre = nombreCompleto != null? nombreCompleto.replaceAll("[.-]", "").replace(", ", ",") : "";
        this.nombreCompleto = nombre.length()>72? nombre.substring(0, 72) : nombre;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public void setCedula(String cedula) {
        String doc = cedula != null? cedula.replaceAll("[.-]", "") : "";
        this.cedula = doc.length()>11? doc.substring(0, 11) : doc;
    }

    public void setProvinciaEmisionCedula(Provincias provinciaEmisionCedula) {
        this.provinciaEmisionCedula = provinciaEmisionCedula;
    }

    public void setTelefono(String telefono) {
        String tel = telefono != null? telefono.replaceAll("[.-]", "") : "";
        this.telefono = tel.length()>14? tel.substring(0, 14) : tel;
    }

    public void setMarcaDireccion(String marcaDireccion) {
        this.marcaDireccion = marcaDireccion;
    }

    public void setCalle(String calle) {
        String c = calle != null? calle.replaceAll("[.-]", "") : "";
        this.calle = c.length()>40? c.substring(0, 40) : c;
    }

    public void setAltura(String altura) {
        String a = altura != null? altura.replaceAll("[.-]", "") : "";
        this.altura = a.length()>10? a.substring(0, 10) : a;
    }

    public void setPisoDepto(String pisoDepto) {
        String pD = pisoDepto != null? pisoDepto.replaceAll("[.-]", "") : "";
        this.pisoDepto = pD.length()>6? pD.substring(0, 6) : pD;
    }

    public void setLocalidad(String localidad) {
        String loca = localidad != null? localidad.replaceAll("[.-]", "") : "";
        this.localidad = loca.length()>36? loca.substring(0, 36) : loca;
    }

    public void setProvincia(Provincias provincia) {
        this.provincia = provincia;
    }

    public void setCodigoPostal(String codigoPostal) {
        String cP = codigoPostal != null? codigoPostal.replaceAll("[.-]", "") : "";
        this.codigoPostal = cP.length()>8? cP.substring(0, 8) : cP;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public void setNacionalidad(Nacionalidades nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public void setRelacion(String relacion) {
        this.relacion = relacion;
    }

    public void setCargo(Cargos cargo) {
        this.cargo = cargo;
    }

    public void setFechaCargo(String fechaCargo) {
        this.fechaCargo = fechaCargo;
    }

    public void setFuenteCargo(String fuenteCargo) {
        this.fuenteCargo = fuenteCargo;
    }

    public void setAntCodigo(String antCodigo) {
        this.antCodigo = antCodigo;
    }

    public void setCampo1(String campo1) {
        this.campo1 = campo1;
    }

    public void setCampo2(String campo2) {
        this.campo2 = campo2;
    }

    public void setCampo3(String campo3) {
        this.campo3 = campo3;
    }

    public void setCampo4(String campo4) {
        this.campo4 = campo4;
    }

    public void setCampo5(String campo5) {
        this.campo5 = campo5;
    }

    public void setCampo6(String campo6) {
        this.campo6 = campo6;
    }

    public void setCampo7(String campo7) {
        this.campo7 = campo7;
    }

    public void setCampo8(String campo8) {
        this.campo8 = campo8;
    }

    public void setAntFecha(String antFecha) {
        this.antFecha = antFecha;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public void setSociedadCategoria(String sociedadCategoria) {
        this.sociedadCategoria = sociedadCategoria;
    }

    public void setFechaInsercionBoletin(String fechaInsercionBoletin) {
        this.fechaInsercionBoletin = fechaInsercionBoletin;
    }

    public void setBoletinOficial(byte[] boletinOficial) {
        this.boletinOficial = boletinOficial;
    }
}
