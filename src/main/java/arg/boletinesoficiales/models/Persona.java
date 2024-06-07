package arg.boletinesoficiales.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Persona {

    @JsonProperty("nombre_completo")
    private String nombrePersona;
    @JsonProperty("documento")
    private String documento;
    @JsonProperty("sexo")
    private String sexo;
    @JsonProperty("fecha_nacimiento")
    private String fechaNacimiento;
    @JsonProperty("nacionalidad")
    private String nacionalidad;
    @JsonProperty("estado_civil")
    private String estadoCivil;
    @JsonProperty("cargo")
    private String cargo;
    @JsonProperty("direccion")
    private Direccion direccion;
}
