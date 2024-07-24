package arg.boletinesoficiales.service.nlp;

import arg.boletinesoficiales.models.ResponseNLP;
import org.springframework.stereotype.Service;

@Service
public class NLPBoletinesOficiales {

    public ResponseNLP extraerEntidades(String boletinOficial) {
        return new ResponseNLP();
    }
}
