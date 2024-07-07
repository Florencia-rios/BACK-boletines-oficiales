package arg.boletinesoficiales.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Sociedad {

    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("cuit")
    private String cuit;

    @JsonProperty("fechaConstitucion")
    private String fechaConstitucion;

    @JsonProperty("direccion")
    private Direccion direccion;

    @JsonProperty("alta")
    private String alta;

    @JsonProperty("disolucion")
    private String disolucion;

    @JsonProperty("modificacion")
    private String modificacion;

    @JsonProperty("personas")
    private List<Persona> personas;
}
