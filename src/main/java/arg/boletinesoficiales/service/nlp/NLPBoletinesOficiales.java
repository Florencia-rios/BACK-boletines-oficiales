package arg.boletinesoficiales.service.nlp;

import arg.boletinesoficiales.dto.request.NLPExtraerEntidadesRequest;
import arg.boletinesoficiales.models.ResponseNLP;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NLPBoletinesOficiales {

    @Value("${application.url.base}")
    private String urlBase;

    @Value("${application.url.extraer_entidades_bo}")
    private String urlExtraerEntidadesBO;

    @Value("${application.url.extraer_entidades_soc}")
    private String urlExtraerEntidadesSoc;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseNLP extraerEntidadesBO(String boletinOficial) throws JsonProcessingException {
        NLPExtraerEntidadesRequest request = new NLPExtraerEntidadesRequest();
        request.setDocumento(boletinOficial);

        ResponseEntity<String> response = restTemplate.postForEntity(urlBase+urlExtraerEntidadesBO, request, String.class);

        String responseString = response.getBody();

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseNLP responseNLP = objectMapper.readValue(responseString, ResponseNLP.class);

        return responseNLP;
    }

    public ResponseNLP extraerEntidadesSoc(String sociedad) throws JsonProcessingException {
        NLPExtraerEntidadesRequest request = new NLPExtraerEntidadesRequest();
        request.setDocumento(sociedad);

        ResponseEntity<String> response = restTemplate.postForEntity(urlBase+urlExtraerEntidadesSoc, request, String.class);

        String responseString = response.getBody();

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseNLP responseNLP = objectMapper.readValue(responseString, ResponseNLP.class);

        return responseNLP;
    }
}
