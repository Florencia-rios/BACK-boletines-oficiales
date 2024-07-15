package arg.boletinesoficiales.dto;

import lombok.Data;

import java.util.List;

@Data
public class BoletinesOficialesResponse {

    private List<SociedadDto> dataSociedades; // cada sociedad se corresponde a un boletin oficial
}
