package arg.boletinesoficiales.dto;

import lombok.Data;

import java.util.List;

@Data
public class BoletinesficialesRequest {

    private String fechaBoletin;
    private List<String> boletinesOficiales;
}
