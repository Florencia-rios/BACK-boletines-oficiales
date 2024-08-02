package arg.boletinesoficiales.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class Response {

    private List<SociedadDto> data; // cada sociedad se corresponde a un boletin oficial
}
