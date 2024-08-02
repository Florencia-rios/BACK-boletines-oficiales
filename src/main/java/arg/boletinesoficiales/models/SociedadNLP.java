package arg.boletinesoficiales.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SociedadNLP {

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

    @JsonProperty("causaModificacion")
    private String causaModificacion;

    @JsonProperty("fechaCargo")
    private String fechaCargo;
}
