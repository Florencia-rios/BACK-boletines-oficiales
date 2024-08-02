package controller;

import arg.boletinesoficiales.BoletinesOficialesApplication;
import arg.boletinesoficiales.controller.BoletinesOficialesController;
import arg.boletinesoficiales.dto.request.BoletinesficialesRequest;
import arg.boletinesoficiales.dto.response.Response;
import arg.boletinesoficiales.models.ResponseNLP;
import arg.boletinesoficiales.service.nlp.NLPBoletinesOficiales;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import utils.Mocks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = BoletinesOficialesApplication.class)
@SpringJUnitConfig
@WebMvcTest(BoletinesOficialesController.class)
public class BoletinesOficialesControllerTests {

    private Mocks mocks = new Mocks();

    @MockBean
    private NLPBoletinesOficiales nlpBoletinesOficiales;

    @Autowired
    private BoletinesOficialesController boletinesOficialesController;

    @Test
    public void altaDeSociedadesObtenerLosCamposRequeridosq() throws JsonProcessingException {
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
        Mockito.when(nlpBoletinesOficiales.extraerEntidadesBO(boletinOficial)).thenReturn(responseNLP);

        // execution
        Response response = boletinesOficialesController.procesarBoletinOficial(request);

        // assertion
        assertEquals(2, response.getData().size());
    }
}
