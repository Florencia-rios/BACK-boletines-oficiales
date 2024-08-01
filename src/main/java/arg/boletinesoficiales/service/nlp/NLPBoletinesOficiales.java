package arg.boletinesoficiales.service.nlp;

import arg.boletinesoficiales.dto.request.NLPExtraerEntidadesRequest;
import arg.boletinesoficiales.models.ResponseNLP;
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

    @Value("${application.url.extraer_entidades_soc")
    private String urlExtraerEntidadesSoc;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseNLP extraerEntidadesBO(String boletinOficial) {
        NLPExtraerEntidadesRequest request = new NLPExtraerEntidadesRequest();
        request.setDocumento(boletinOficial);

        ResponseEntity<ResponseNLP> responseNLP = restTemplate.postForEntity(urlBase+urlExtraerEntidadesBO, request, ResponseNLP.class);

        return responseNLP.getBody();
    }

    public ResponseNLP extraerEntidadesSoc(String sociedad) {
        NLPExtraerEntidadesRequest request = new NLPExtraerEntidadesRequest();
        request.setDocumento(sociedad);

        ResponseEntity<ResponseNLP> responseNLP = restTemplate.postForEntity(urlBase+urlExtraerEntidadesSoc, request, ResponseNLP.class);

        return responseNLP.getBody();
    }
}
