package arg.boletinesoficiales.controller;

import arg.boletinesoficiales.dto.BoletinesOficialesResponse;
import arg.boletinesoficiales.dto.BoletinesficialesRequest;
import arg.boletinesoficiales.dto.SociedadDto;
import arg.boletinesoficiales.entity.user.Sociedad;
import arg.boletinesoficiales.service.BoletinesOficialesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BoletinesOficialesController {

    @Autowired
    private BoletinesOficialesService service;
    @Autowired
    private ModelMapper modelMapper;

    public BoletinesOficialesResponse procesarBoletinOficial(BoletinesficialesRequest request) {

        BoletinesOficialesResponse response = new BoletinesOficialesResponse();

        List<Sociedad> sociedades = service.procesarBoletinOficial(request.getBoletinesOficiales());

        response.setDataSociedades(
                sociedades.stream()
                        .map(s -> modelMapper.map(s, SociedadDto.class))
                        .collect(Collectors.toList())
        );

        return response;
    }
}
