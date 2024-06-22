package arg.boletinesoficiales.service;

import arg.boletinesoficiales.dto.BoletinOficial;
import arg.boletinesoficiales.entity.user.Sociedad;
import arg.boletinesoficiales.models.Entities;
import arg.boletinesoficiales.service.mockNlp.MockNLPBoletinesOficiales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoletinesOficialesService {

    @Autowired
    private MockNLPBoletinesOficiales nlpBoletinesOficiales;

    public List<Sociedad> procesarBoletinOficial(List<BoletinOficial> boletinesOficiales) {

        List<Sociedad> dataSociedades = new ArrayList<>();

        for(BoletinOficial boletinOficial: boletinesOficiales) {
            Entities entities = nlpBoletinesOficiales.extraerEntidades(boletinOficial);
            Sociedad dataSociedad = obetenerDataFinal(entities);

            dataSociedades.add(dataSociedad);
        }

        return dataSociedades;
    }

    // todo chequear valores con tablas maestras
    private Sociedad obetenerDataFinal(Entities entities){
        Sociedad response = new Sociedad();

        return response;
    }
}
