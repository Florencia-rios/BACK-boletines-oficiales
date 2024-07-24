package arg.boletinesoficiales.service;

import arg.boletinesoficiales.repository.core.*;
import arg.boletinesoficiales.repository.user.SociedadRepository;
import arg.boletinesoficiales.service.nlp.NLPBoletinesOficiales;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class BoletinesOficialesServiceTests {

    @Autowired
    BoletinesOficialesService boletinesOficialesService;

    @MockBean
    private SexoRepository sexoRepository;
    @MockBean
    private ProvinciasRepository provinciasRepository;
    @MockBean
    private EstadoCivilRepository estadoCivilRepository;
    @MockBean
    private NacionalidadesRepository nacionalidadesRepository;
    @MockBean
    private CargosRepository cargosRepository;
    @MockBean
    private SociedadRepository sociedadRepository;
    @MockBean
    private NLPBoletinesOficiales nlpBoletinesOficiales;

    @Test
    void altaSociedad(){



    }
}
