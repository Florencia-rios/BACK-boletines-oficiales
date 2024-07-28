package controller;

import arg.boletinesoficiales.controller.BoletinesOficialesController;
import arg.boletinesoficiales.dto.BoletinesOficialesResponse;
import arg.boletinesoficiales.dto.BoletinesficialesRequest;
import arg.boletinesoficiales.models.ResponseNLP;
import arg.boletinesoficiales.service.BoletinesOficialesService;
import arg.boletinesoficiales.service.nlp.NLPBoletinesOficiales;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import utils.Mocks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class BoletinesOficialesControllerTests {

    @Autowired
    private BoletinesOficialesService boletinesOficialesService;
    private Mocks mocks = new Mocks();

    @MockBean
    private NLPBoletinesOficiales nlpBoletinesOficiales;

    @Autowired
    BoletinesOficialesController boletinesOficialesController;

    @Test
    void altaDeSociedadesObtenerLosCamposRequeridosq() {
        // set up
        List<String> boletinesOficiales = new ArrayList<>(); // parametro
        String boletinOficial = mocks.boletinOficial(); // parametro
        boletinesOficiales.add(boletinOficial);
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaBoletin = fechaActual.format(formatter); // parametro

        BoletinesficialesRequest request = new BoletinesficialesRequest();
        request.setBoletinesOficiales(boletinesOficiales);
        request.setFechaBoletin(fechaBoletin);

        ResponseNLP responseNLP = mocks.altaSociedad();

        // mock
        doReturn(responseNLP).when(nlpBoletinesOficiales).extraerEntidades(boletinOficial);

        // execution
        BoletinesOficialesResponse response = boletinesOficialesController.procesarBoletinOficial(request);

        // assertion
        assertEquals(2, response.getDataSociedades().size());
    }
}
