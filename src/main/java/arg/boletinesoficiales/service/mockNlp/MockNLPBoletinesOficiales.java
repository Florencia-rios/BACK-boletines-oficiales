package arg.boletinesoficiales.service.mockNlp;

import arg.boletinesoficiales.dto.BoletinOficial;
import arg.boletinesoficiales.dto.Entity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MockNLPBoletinesOficiales {

    public List<Entity> extraerEntidades(BoletinOficial boletinOficial){
        List<Entity> response = new ArrayList<>();
        // todo luego hay que hacer el llamado al endpoint del servicio


        return response;
    }
}
