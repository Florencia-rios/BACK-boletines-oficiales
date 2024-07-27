package arg.boletinesoficiales.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Entities {

    @JsonProperty("sociedades")
    private List<SociedadNLP> sociedadNLP;
    @JsonProperty("integrantes")
    private List<Persona> personas;
}
