package arg.boletinesoficiales.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Persona {

    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("sexo")
    private String sexo;

    @JsonProperty("documento")
    private String documento;

    @JsonProperty("fechaNacimiento")
    private String fechaNacimiento;

    @JsonProperty("telefono")
    private String telefono;

    @JsonProperty("pais")
    private String pais;

    @JsonProperty("estadoCivil")
    private String estadoCivil;

    @JsonProperty("cargo")
    private String cargo;

    @JsonProperty("direccion")
    private Direccion direccion;

    @JsonProperty("esBaja")
    private String esBaja;

    @JsonProperty("casadoConIntegrante")
    private String casadoConIntegrante;

    @JsonProperty("fechaCargo")
    private String fechaCargo;

    private String conyuge;
}
