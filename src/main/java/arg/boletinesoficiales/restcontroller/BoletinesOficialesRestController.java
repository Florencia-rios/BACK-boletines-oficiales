package arg.boletinesoficiales.restcontroller;

import arg.boletinesoficiales.controller.BoletinesOficialesController;
import arg.boletinesoficiales.dto.request.BoletinesficialesRequest;
import arg.boletinesoficiales.dto.response.Response;
import arg.boletinesoficiales.dto.request.SoloSociedadesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(name = "/api")
public class BoletinesOficialesRestController {

    @Autowired
    private BoletinesOficialesController controller;

    @GetMapping("/health")
    public ResponseEntity<String> health(){
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }

    @PostMapping("/boletines-oficiales")
    public ResponseEntity<Response> procesarBoletinOficial(@RequestBody BoletinesficialesRequest request){

        Response response = controller.procesarBoletinOficial(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/sociedades")
    public ResponseEntity<Response> procesarSociedades(@RequestBody SoloSociedadesRequest request){

        Response response = controller.procesarSociedad(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
