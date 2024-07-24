package arg.boletinesoficiales.dto;

import lombok.Data;

import java.util.List;

@Data
public class BoletinesficialesRequest {

    private String fechaBoletin; // me pasan la fecha de la publicacion del boletin oficial
    private List<String> boletinesOficiales;
}
