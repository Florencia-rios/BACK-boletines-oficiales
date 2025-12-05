package arg.boletinesoficiales.dto.request;

import arg.boletinesoficiales.models.EntidadesWrapper;
import lombok.Data;

import java.util.List;

@Data
public class Temporal {

    private String fechaBoletin; // me pasan la fecha de la publicacion del boletin oficial
    private List<List<EntidadesWrapper>> sociedades;
}

