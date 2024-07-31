package arg.boletinesoficiales.service.nlp;

import arg.boletinesoficiales.dto.request.NLPExtraerEntidadesRequest;
import arg.boletinesoficiales.models.ResponseNLP;
import org.springframework.stereotype.Service;

@Service
public class NLPBoletinesOficiales {

    public ResponseNLP extraerEntidadesBO(String boletinOficial) {
        NLPExtraerEntidadesRequest request = new NLPExtraerEntidadesRequest();
        request.setDocumento(boletinOficial);
        // agregar llamado a la api nlp
        return new ResponseNLP();
    }

    public ResponseNLP extraerEntidadesSoc(String sociedad) {
        NLPExtraerEntidadesRequest request = new NLPExtraerEntidadesRequest();
        request.setDocumento(sociedad);
        // agregar llamado a la api nlp
        return new ResponseNLP();
    }
}
