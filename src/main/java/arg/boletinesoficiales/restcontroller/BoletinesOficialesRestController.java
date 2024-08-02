package arg.boletinesoficiales.restcontroller;

import arg.boletinesoficiales.controller.BoletinesOficialesController;
import arg.boletinesoficiales.dto.request.BoletinesficialesRequest;
import arg.boletinesoficiales.dto.response.Response;
import arg.boletinesoficiales.dto.request.SoloSociedadesRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BoletinesOficialesRestController {

    @Autowired
    private BoletinesOficialesController controller;

    @GetMapping("/api/health")
    public ResponseEntity<String> health(){
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }

    @PostMapping("/api/boletines-oficiales")
    public ResponseEntity<Response> procesarBoletinOficial(@RequestBody BoletinesficialesRequest request) throws JsonProcessingException {

        Response response = controller.procesarBoletinOficial(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/sociedades")
    public ResponseEntity<Response> procesarSociedades(@RequestBody SoloSociedadesRequest request) throws JsonProcessingException {

        Response response = controller.procesarSociedad(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
