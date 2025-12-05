package arg.boletinesoficiales.restcontroller;

import arg.boletinesoficiales.controller.BoletinesOficialesController;
import arg.boletinesoficiales.dto.request.BoletinesficialesRequest;
import arg.boletinesoficiales.dto.request.SoloSociedadesRequest;
import arg.boletinesoficiales.dto.request.Temporal;
import arg.boletinesoficiales.dto.response.Response;
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

    /***
     * Éste método es por si se cortó el procesamiento de extracción nlp y no se llegó a guardar el proceso en la base
     *
     * @param request
     * @return la lista de los crudos que extrajo la AI pero no se insertaron en la base
     * @throws JsonProcessingException
     */
    @PostMapping("/api/sociedades-temporal")
    public ResponseEntity<Response> procesarSociedadesTemporal(@RequestBody Temporal request) throws JsonProcessingException {

        Response response = controller.procesarSociedadesTemporal(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /***
     * Éste método es uno de los principales, para poder procesar una lista de boletines oficiales
     *
     * @param request lista de boletines oficiales
     * @return lista de sociedades que se terminaron de procesar y se guardaron en la base
     * @throws JsonProcessingException
     */
    @PostMapping("/api/boletines-oficiales")
    public ResponseEntity<Response> procesarBoletinOficial(@RequestBody BoletinesficialesRequest request) throws JsonProcessingException {

        Response response = controller.procesarBoletinOficial(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /***
     * Es para generar manualmente los archivos con todas las altas y modificaciones (todos los cargos disponibles)
     * @param fechaInsercionBoletin
     * @return OK si se pueden generar con éxito
     */
    @PostMapping("/api/csv")
    public ResponseEntity<String> getArchivos(@RequestParam String fechaInsercionBoletin) {

        controller.extractedCSV(fechaInsercionBoletin);

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    /***
     * Éste método es uno de los principales, para poder procesar una lista de sociedades (es el que se usa actualmente)
     *
     * @param request lista de sociedades
     * @return lista de sociedades que se terminaron de procesar y se guardaron en la base
     * @throws JsonProcessingException
     */
    @PostMapping("/api/sociedades")
    public ResponseEntity<Response> procesarSociedades(@RequestBody SoloSociedadesRequest request) throws JsonProcessingException {

        Response response = controller.procesarSociedad(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/nuevos-cargos")
    public void obtenerCSVSoloNuevosCargos(@RequestParam String fechaInsercionBoletin){
        controller.findSociedadByFechaInsercionBoletinNuevosCargos(fechaInsercionBoletin);
    }
}
