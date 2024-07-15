package arg.boletinesoficiales.restcontroller;

import arg.boletinesoficiales.controller.BoletinesOficialesController;
import arg.boletinesoficiales.dto.BoletinesOficialesResponse;
import arg.boletinesoficiales.dto.BoletinesficialesRequest;
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
    public ResponseEntity<BoletinesOficialesResponse> procesarBoletinOficial(@RequestBody BoletinesficialesRequest request){

        BoletinesOficialesResponse response = controller.procesarBoletinOficial(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
