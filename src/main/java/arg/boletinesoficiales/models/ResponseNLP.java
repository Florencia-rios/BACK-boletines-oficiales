package arg.boletinesoficiales.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResponseNLP {

    @JsonProperty("entidades")
    private List<Entities> entities; // todo recordar que el nlp-boletines-oficiales, tiene que devolver este formato de respuesta
}
