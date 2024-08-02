package arg.boletinesoficiales.dto.request;

import arg.boletinesoficiales.dto.response.SociedadDto;
import lombok.Data;

import java.util.List;

@Data
public class NLPGenerarArchivosRequest {

    List<SociedadDto> data;
}
