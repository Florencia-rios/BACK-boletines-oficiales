package arg.boletinesoficiales.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Direccion {

    @JsonProperty("calle")
    private String calle;

    @JsonProperty("altura")
    private String altura;

    @JsonProperty("piso")
    private String piso;

    @JsonProperty("departamento")
    private String departamento;

    @JsonProperty("codigoPostal")
    private String codigoPostal;

    @JsonProperty("localidad")
    private String localidad;

    @JsonProperty("provincia")
    private String provincia;
}
