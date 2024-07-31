package arg.boletinesoficiales.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class SoloSociedadesRequest {

    private String fechaBoletin; // me pasan la fecha de la publicacion del boletin oficial
    private List<String> sociedades;
}
