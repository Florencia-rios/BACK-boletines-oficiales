package arg.boletinesoficiales.entity.user;

import arg.boletinesoficiales.entity.core.*;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name="SOCIEDAD")
@Getter
public class Sociedad {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="MOR_USER")
    private String usuario; // M o A
    @Column(name="MOR_NRO_USER")
    @ColumnDefault(value = "62")
    private String nroUsuario;
    @Column(name="MOR_LOTE")
    @ColumnDefault(value = "0")
    private String lote;
    @Column(name="MOR_CODINT")
    private int contador;
    @Column(name="MOR_MATRIZ")
    @ColumnDefault(value = "AA0029")
    private String matriz;
    @Column(name="MOR_SUCURSAL")
    @ColumnDefault(value = "9999")
    private String sucursal;
    @Column(name="MOR_SECTOR")
    private String sector; // PC, para altas, y MD para modificaciones
    @Column(name="MOR_CLIENTE")
    @ColumnDefault(value = "")
    private String cliente;
    @Column(name="MOR_NOMBRE_COMPLETO")
    private String nombreCompleto;
    @Column(name="MOR_FECHA_NAC")
    private String fechaNacimiento;  // (aaaammdd o dd/mm/aaaa)
    @OneToOne
    @JoinColumn(name="MOR_SEXO_ID")
    private Sexo sexo;
    @Column(name="MOR_DOCUMENTO1")
    private String documento;
    @Column(name="MOR_DOCUMENTO2")
    @ColumnDefault(value = "")
    private String cedula;

    @OneToOne
    @JoinColumn(name="MOR_PROV_DOC2_ID")
    private Provincias provinciaEmisionCedula;

    @Column(name="MOR_TELEFONO")
    private String telefono;
    @Column(name="MOR_MARCA_DIRE_1")
    @ColumnDefault(value = "S")
    private String marcaDireccion; // S o J, S el domicilio separado
    @Column(name="MOR_CALLE")
    private String calle;
    @Column(name="MOR_NUMER")
    private String altura;
    @Column(name="MOR_PISO")
    private String pisoDepto;
    @Column(name="MOR_LOCA")
    private String localidad;

    @OneToOne
    @JoinColumn(name="MOR_PROV_ID")
    private Provincias provincia;

    @Column(name="MOR_CP")
    private String codigoPostal;
    @OneToOne
    @JoinColumn(name="MOR_EST_CIVIL_ID")
    private EstadoCivil estadoCivil;
    @OneToOne
    @JoinColumn(name="MOR_NACIONALIDAD_ID")
    private Nacionalidades nacionalidad;
    @Column(name="MOR_RELACION")
    private String relacion; // si esta casado con el integrante anterior, va C
    @OneToOne
    @JoinColumn(name="MOR_CARGO_ID")
    private Cargos cargo;
    @Column(name="MOR_CARGO_FECHA")
    private String fechaCargo;  // (aaaammdd o dd/mm/aaaa)
    @Column(name="MOR_CARGO_FUENTE")
    @ColumnDefault(value = "BOL")
    private String fuenteCargo; // sólo si el integrante correspondiente se dió de baja en la sociedad, va BAJ
    @Column(name="MOR_ANT_CODIGO")
    @ColumnDefault(value = "XXX")
    private String antCodigo; // siempre es "XXX"
    @Column(name="MOR_CAMPO_1")
    @ColumnDefault(value = "")
    private String campo1;
    @Column(name="MOR_CAMPO_2")
    @ColumnDefault(value = "")
    private String campo2;
    @Column(name="MOR_CAMPO_3")
    @ColumnDefault(value = "")
    private String campo3;
    @Column(name="MOR_CAMPO_4")
    @ColumnDefault(value = "")
    private String campo4;
    @Column(name="MOR_CAMPO_5")
    @ColumnDefault(value = "")
    private String campo5;
    @Column(name="MOR_CAMPO_6")
    @ColumnDefault(value = "")
    private String campo6;
    @Column(name="MOR_CAMPO_7")
    @ColumnDefault(value = "")
    private String campo7;
    @Column(name="MOR_CAMPO_8")
    @ColumnDefault(value = "")
    private String campo8;
    @Column(name="MOR_ANT_FECHA")
    @ColumnDefault(value = "")
    private String antFecha;  // (aaaammdd o dd/mm/aaaa)
    @Column(name="MOR_ARCHIVO")
    @ColumnDefault(value = "")
    private String archivo;
    @Column(name="MOR_SOC_CATEGORIA")
    @ColumnDefault(value = "")
    private String sociedadCategoria; // Sólo es DOC si la sociedad se disolvió
    @Column(name = "FECHA_INSERCION_BOLETIN")
    private String fechaInsercionBoletin; // es la fecha actual, el dia en que pusieron a procesar cada boletin oficial
    @Column(name = "BOLETIN_OFICIAL") // Doc en base 64
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
        String nombre = nombreCompleto.replaceAll("[.-]", "");
        this.nombreCompleto = nombre.length()>36? nombre.substring(0, 73) : nombre;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public void setDocumento(String documento) {
        String doc = documento.replaceAll("[.-]", "");
        this.documento = doc.length()>11? doc.substring(0, 12) : doc;
    }

    public void setCedula(String cedula) {
        String doc = cedula.replaceAll("[.-]", "");
        this.cedula = doc.length()>11? doc.substring(0, 12) : doc;
    }

    public void setProvinciaEmisionCedula(Provincias provinciaEmisionCedula) {
        this.provinciaEmisionCedula = provinciaEmisionCedula;
    }

    public void setTelefono(String telefono) {
        String tel =  telefono.replaceAll("[.-]", "");
        this.telefono = tel.length()>14? tel.substring(0, 15) : tel;
    }

    public void setMarcaDireccion(String marcaDireccion) {
        this.marcaDireccion = marcaDireccion;
    }

    public void setCalle(String calle) {
        String c = calle.replaceAll("[.-]", "");
        this.calle = c.length()>40? c.substring(0, 41) : c;
    }

    public void setAltura(String altura) {
        String a = altura.replaceAll("[.-]", "");
        this.altura = a.length()>10? a.substring(0, 11) : a;
    }

    public void setPisoDepto(String pisoDepto) {
        String pD = pisoDepto.replaceAll("[.-]", "");
        this.pisoDepto = pD.length()>6? pD.substring(0, 7) : pD;
    }

    public void setLocalidad(String localidad) {
        String loca = localidad.replaceAll("[.-]", "");
        this.localidad = loca.length()>36? loca.substring(0, 37) : loca;
    }

    public void setProvincia(Provincias provincia) {
        this.provincia = provincia;
    }

    public void setCodigoPostal(String codigoPostal) {
        String cP = codigoPostal.replaceAll("[.-]", "");
        this.codigoPostal = cP.length()>8? cP.substring(0, 9) : cP;
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
