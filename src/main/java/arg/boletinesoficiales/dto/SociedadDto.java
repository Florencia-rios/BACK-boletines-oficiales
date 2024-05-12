package arg.boletinesoficiales.dto;

import lombok.Data;

@Data
public class SociedadDto {

    private String usuario;
    private String nroUsuario;
    private String lote;
    private int contador;
    private String matriz;
    private String sucursal;
    private String sector;
    private String cliente;
    private String nombreCompleto;
    private String fechaNacimiento;  // (aaaammdd o dd/mm/aaaa)
    private String sexo;
    private String documento;
    private String cedula;
    private String provinciaEmisionCedula;
    private String telefono;
    private String marcaDireccion; // S o J, J el domicilio separado
    private String calle;
    private String altura;
    private String pisoDepto;
    private String localidad;
    private String provincia;
    private String codigoPostal;
    private String estadoCivil;
    private String nacionalidad;
    private String relacion;
    private String cargo;
    private String fechaCargo;  // (aaaammdd o dd/mm/aaaa)
    private String fuenteCargo;
    private String antCodigo; // siempre es "XXX"
    private String campo1;
    private String campo2;
    private String campo3;
    private String campo4;
    private String campo5;
    private String campo6;
    private String campo7;
    private String campo8;
    private String antFecha;  // (aaaammdd o dd/mm/aaaa)
    private String archivo;
    private String sociedadCategoria;
}
