package arg.boletinesoficiales.controller;

import arg.boletinesoficiales.dto.response.Response;
import arg.boletinesoficiales.dto.request.BoletinesficialesRequest;
import arg.boletinesoficiales.dto.response.SociedadDto;
import arg.boletinesoficiales.dto.request.SoloSociedadesRequest;
import arg.boletinesoficiales.repository.user.SociedadRepository;
import arg.boletinesoficiales.service.BoletinesOficialesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BoletinesOficialesController {

    @Autowired
    private BoletinesOficialesService service;
    @Autowired
    private SociedadRepository sociedadRepository;
    @Autowired
    private ModelMapper modelMapper;

    public Response procesarBoletinOficial(BoletinesficialesRequest request) {

        Response response = new Response();

        service.procesarBoletinOficial(request.getBoletinesOficiales(), request.getFechaBoletin());

        List<SociedadDto> sociedadDtos = getSociedadByFechaInsercionBoletin(request.getFechaBoletin());
        response.setDataSociedades(sociedadDtos);

        return response;
    }

    public Response procesarSociedad(SoloSociedadesRequest request) {

        Response response = new Response();

        service.procesarSoloSociedades(request.getSociedades(), request.getFechaBoletin());

        List<SociedadDto> sociedadDtos = getSociedadByFechaInsercionBoletin(request.getFechaBoletin());
        response.setDataSociedades(sociedadDtos);

        return response;
    }

    private List<SociedadDto> getSociedadByFechaInsercionBoletin(String fechaInsercionBoletin) {
        List<Object[]> results = sociedadRepository.findSociedadByFechaInsercionBoletin(fechaInsercionBoletin);
        return mapToSociedadDto(results);
    }

    private List<SociedadDto> mapToSociedadDto(List<Object[]> results) {
        List<SociedadDto> sociedadDtos = new ArrayList<>();

        for (Object[] row : results) {
            SociedadDto dto = new SociedadDto();
            dto.setMor_user((String) row[0]);
            dto.setMor_nro_user((String) row[1]);
            dto.setMor_lote((String) row[2]);
            dto.setMor_codint((Integer) row[3]);
            dto.setMor_matriz((String) row[4]);
            dto.setMor_sucursal((String) row[5]);
            dto.setMor_sector((String) row[6]);
            dto.setMor_cliente((String) row[7]);
            dto.setMor_nombre_completo((String) row[8]);
            dto.setMor_fecha_nac((String) row[9]);
            dto.setMor_sexo((String) row[10]);
            dto.setMor_documento1((String) row[11]);
            dto.setMor_documento2((String) row[12]);
            dto.setMor_prov_doc2((String) row[13]);
            dto.setMor_telefono((String) row[14]);
            dto.setMor_marca_dire_1((String) row[15]);
            dto.setMor_calle((String) row[16]);
            dto.setMor_numer((String) row[17]);
            dto.setMor_piso((String) row[18]);
            dto.setMor_loca((String) row[19]);
            dto.setMor_prov((String) row[20]);
            dto.setMor_cp((String) row[21]);
            dto.setMor_est_civil((String) row[22]);
            dto.setMor_nacionalidad((String) row[23]);
            dto.setMor_relacion((String) row[24]);
            dto.setMor_cargo((String) row[25]);
            dto.setMor_cargo_fecha((String) row[26]);
            dto.setMor_cargo_fuente((String) row[27]);
            dto.setMor_ant_codigo((String) row[28]);
            dto.setMor_campo_1((String) row[29]);
            dto.setMor_campo_2((String) row[30]);
            dto.setMor_campo_3((String) row[31]);
            dto.setMor_campo_4((String) row[32]);
            dto.setMor_campo_5((String) row[33]);
            dto.setMor_campo_6((String) row[34]);
            dto.setMor_campo_7((String) row[35]);
            dto.setMor_campo_8((String) row[36]);
            dto.setMor_ant_fecha((String) row[37]);
            dto.setMor_archivo((String) row[38]);
            dto.setMor_soc_categoria((String) row[39]);

            sociedadDtos.add(dto);
        }

        return sociedadDtos;
    }
}
