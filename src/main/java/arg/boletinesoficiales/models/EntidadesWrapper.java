package arg.boletinesoficiales.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntidadesWrapper {

    @JsonProperty("entidades")
    private Entities entidades;
}
