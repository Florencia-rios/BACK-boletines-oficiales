package arg.boletinesoficiales.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Entities {

    @JsonProperty("entidades")
    private List<Entity> entities;
    @JsonProperty("parejas")
    private Map<String, String> relationShip;
}
