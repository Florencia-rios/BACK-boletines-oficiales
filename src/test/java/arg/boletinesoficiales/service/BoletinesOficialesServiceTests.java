package arg.boletinesoficiales.service;

import arg.boletinesoficiales.entity.user.Sociedad;
import arg.boletinesoficiales.models.ResponseNLP;
import arg.boletinesoficiales.service.nlp.NLPBoletinesOficiales;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import utils.Mocks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.doReturn;

//@RunWith(SpringRunner.class)
//@ConfigurationProperties(prefix = "application-test")
//@SpringBootTest
//@ActiveProfiles("test")
//@Transactional
@SpringJUnitConfig
@WebMvcTest(BoletinesOficialesService.class)
public class BoletinesOficialesServiceTests {

    @Autowired
    private BoletinesOficialesService boletinesOficialesService;
    private Mocks mocks = new Mocks();

    @MockBean
    private NLPBoletinesOficiales nlpBoletinesOficiales;

    @Test
    void altaSociedad() throws JsonProcessingException {
        // set up
        List<String> boletinesOficiales = new ArrayList<>(); // parametro
        String boletinOficial = mocks.boletinOficial(); // parametro
        boletinesOficiales.add(boletinOficial);
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaBoletin = fechaActual.format(formatter); // parametro
        ResponseNLP responseNLP = mocks.altaSociedad();

        // mock
        doReturn(responseNLP).when(nlpBoletinesOficiales).extraerEntidadesBO(boletinOficial);

        // execution
        List<Sociedad> response = boletinesOficialesService.procesarBoletinOficial(boletinesOficiales, fechaBoletin);

        // assertion
        assertEquals(2, response.size());
    }

    @Test
    void disolucionSociedad() throws JsonProcessingException {
        // set up
        List<String> boletinesOficiales = new ArrayList<>(); // parametro
        String boletinOficial = mocks.boletinOficial(); // parametro
        boletinesOficiales.add(boletinOficial);
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaBoletin = fechaActual.format(formatter); // parametro
        ResponseNLP responseNLP = mocks.disolucionSociedad();

        // mock
        doReturn(responseNLP).when(nlpBoletinesOficiales).extraerEntidadesBO(boletinOficial);

        // execution
        List<Sociedad> response = boletinesOficialesService.procesarBoletinOficial(boletinesOficiales, fechaBoletin);

        // assertion
        assertEquals(1, response.size());
    }

    @Test
    void modificacionSociedadConBaja() throws JsonProcessingException {
        // set up
        List<String> boletinesOficiales = new ArrayList<>(); // parametro
        String boletinOficial = mocks.boletinOficial(); // parametro
        boletinesOficiales.add(boletinOficial);
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaBoletin = fechaActual.format(formatter); // parametro
        ResponseNLP responseNLP = mocks.modificacionSociedadConBaja();

        // mock
        doReturn(responseNLP).when(nlpBoletinesOficiales).extraerEntidadesBO(boletinOficial);

        // execution
        List<Sociedad> response = boletinesOficialesService.procesarBoletinOficial(boletinesOficiales, fechaBoletin);

        // assertion
        assertEquals(2, response.size());
    }

    @Test
    void modificacionSociedadConDA() throws JsonProcessingException {
        // set up
        List<String> boletinesOficiales = new ArrayList<>(); // parametro
        String boletinOficial = mocks.boletinOficial(); // parametro
        boletinesOficiales.add(boletinOficial);
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaBoletin = fechaActual.format(formatter); // parametro
        ResponseNLP responseNLP = mocks.modificacionSociedadConDA();

        // mock
        doReturn(responseNLP).when(nlpBoletinesOficiales).extraerEntidadesBO(boletinOficial);

        // execution
        List<Sociedad> response = boletinesOficialesService.procesarBoletinOficial(boletinesOficiales, fechaBoletin);

        // assertion
        assertEquals(2, response.size());
    }

    @Test
    void modificacionSociedadConAB() throws JsonProcessingException {
        // set up
        List<String> boletinesOficiales = new ArrayList<>(); // parametro
        String boletinOficial = mocks.boletinOficial(); // parametro
        boletinesOficiales.add(boletinOficial);
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaBoletin = fechaActual.format(formatter); // parametro
        ResponseNLP responseNLP = mocks.modificacionSociedadConAB();

        // mock
        doReturn(responseNLP).when(nlpBoletinesOficiales).extraerEntidadesBO(boletinOficial);

        // execution
        List<Sociedad> response = boletinesOficialesService.procesarBoletinOficial(boletinesOficiales, fechaBoletin);

        // assertion
        assertEquals(2, response.size());
    }
}
