package arg.boletinesoficiales.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseNLP {

    @JsonProperty("entidades")
    private Entities entities;
}
