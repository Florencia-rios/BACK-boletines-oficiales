package arg.boletinesoficiales.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResponseNLP {

    @JsonProperty("entidadesSociedades")
    private List<EntidadesWrapper> entidadesSociedades;
}
