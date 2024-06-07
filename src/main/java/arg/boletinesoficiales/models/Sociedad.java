package arg.boletinesoficiales.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Sociedad {

    @JsonProperty("nombre_sociedad")
    private String nombre;
    @JsonProperty("cuit_sociedad")
    private String cuit;
    @JsonProperty("fecha_constitucion")
    private String fechaConstitucion;
    @JsonProperty("direccion")
    private Direccion direccion;
}
