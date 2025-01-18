package arg.boletinesoficiales.service.nlp;

import arg.boletinesoficiales.dto.request.NLPExtraerEntidadesRequest;
import arg.boletinesoficiales.models.ResponseNLP;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NLPBoletinesOficiales {

    @Value("${application.rul.base}")
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

        ResponseEntity<ResponseNLP> response = restTemplate.postForEntity(urlBase+urlExtraerEntidadesBO, request, ResponseNLP.class);

       // String responseString = response.getBody();

      //  ObjectMapper objectMapper = new ObjectMapper();

     //   ResponseNLP responseNLP = objectMapper.readValue(responseString, ResponseNLP.class);

        return response.getBody();
    }

    public ResponseNLP extraerEntidadesSoc(String sociedad) throws JsonProcessingException {
        NLPExtraerEntidadesRequest request = new NLPExtraerEntidadesRequest();
        request.setDocumento(sociedad);

        ResponseEntity<ResponseNLP> response = restTemplate.postForEntity(urlBase+urlExtraerEntidadesSoc, request, ResponseNLP.class);

        //String responseString = response.getBody();

        //ObjectMapper objectMapper = new ObjectMapper();

        //ResponseNLP responseNLP = objectMapper.readValue(responseString, ResponseNLP.class);

        return response.getBody();
    }
}
