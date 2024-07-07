package arg.boletinesoficiales.service;

import arg.boletinesoficiales.entity.user.Sociedad;
import arg.boletinesoficiales.models.Direccion;
import arg.boletinesoficiales.models.Entities;
import arg.boletinesoficiales.models.Persona;
import arg.boletinesoficiales.models.ResponseNLP;
import arg.boletinesoficiales.service.mockNlp.MockNLPBoletinesOficiales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoletinesOficialesService {

    @Autowired
    private MockNLPBoletinesOficiales nlpBoletinesOficiales;

    // TODO acá puedo usar un BoletinesOficialesResponse, en donde guarde, por cada boletin oficial, las entidades que guardo en la base de datos
    public List<Sociedad> procesarBoletinOficial(List<String> boletinesOficiales) {


        for (String boletinOficial : boletinesOficiales) {
            LocalDate fechaActual = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaInsercionBoletin = fechaActual.format(formatter);

            ResponseNLP responseNLP = nlpBoletinesOficiales.extraerEntidades(boletinOficial);
            List<Sociedad> dataSociedades = obetenerDataFinal(responseNLP, boletinOficial, fechaInsercionBoletin);

        }

        return null;
    }

    // todo chequear valores con tablas maestras
    private List<Sociedad> obetenerDataFinal(ResponseNLP responseNLP, String boletinOficial, String fechaInsercionBoletin) {
        List<Sociedad> responseFinal = new ArrayList<>();

        List<Entities> sociedades = responseNLP.getEntities();
        for (int i = 0; i < sociedades.size(); i++) {
            arg.boletinesoficiales.models.Sociedad sociedad = sociedades.get(i).getSociedad();
            List<Persona> personas = sociedad.getPersonas();

            int contador = 0;
            Sociedad responseSociedad = new Sociedad();
            if (sociedad.getAlta().equals("Si")) {
                responseSociedad.setUsuario("A");
                responseSociedad.setSector("PC");
            } else if (sociedad.getAlta().equals("No") && (sociedad.getModificacion().equals("Si") || sociedad.getDisolucion().equals("Si"))) {
                responseSociedad.setUsuario("M");
                responseSociedad.setSector("MD");
                if (sociedad.getDisolucion().equals("Si")) {
                    responseSociedad.setSociedadCategoria("DOC");
                }
            }
            responseSociedad.setContador(contador);
            responseSociedad.setNombreCompleto(sociedad.getNombre());
            responseSociedad.setFechaNacimiento(sociedad.getFechaConstitucion());
            responseSociedad.setDocumento(sociedad.getCuit());
            Direccion direccionSoc = sociedad.getDireccion();
            responseSociedad.setCalle(direccionSoc.getCalle());
            responseSociedad.setAltura(direccionSoc.getAltura());
            responseSociedad.setPisoDepto(direccionSoc.getPiso() + " " + direccionSoc.getDepartamento());
            responseSociedad.setLocalidad(direccionSoc.getLocalidad());
            responseSociedad.setRelacion("");
            responseSociedad.setBoletinOficial(boletinOficial);
            responseSociedad.setFechaInsercionBoletin(fechaInsercionBoletin);
            // TODO faltan los demás campos: teléfono y código postal, fechaCargo. Tablas core
            responseFinal.add(responseSociedad);

            for (Persona persona : personas) {
                contador++;
                Sociedad responsePersona = new Sociedad();

                if (sociedad.getAlta().equals("Si")) {
                    responsePersona.setUsuario("A");
                    responsePersona.setSector("PC");
                } else if (sociedad.getAlta().equals("No") && (sociedad.getModificacion().equals("Si") || sociedad.getDisolucion().equals("Si"))) {
                    responsePersona.setUsuario("M");
                    responsePersona.setSector("MD");
                    if (sociedad.getDisolucion().equals("Si")) {
                        responsePersona.setSociedadCategoria("DOC");
                    }
                }
                responsePersona.setContador(contador);
                responsePersona.setNombreCompleto(persona.getNombre());
                responsePersona.setFechaNacimiento(persona.getFechaNacimiento());
                responsePersona.setDocumento(persona.getDocumento());
                Direccion direccionPer = sociedad.getDireccion();
                responsePersona.setCalle(direccionPer.getCalle());
                responsePersona.setAltura(direccionPer.getAltura());
                responsePersona.setPisoDepto(direccionPer.getPiso() + " " + direccionPer.getDepartamento());
                responsePersona.setLocalidad(direccionPer.getLocalidad());
                responsePersona.setRelacion("");
                responsePersona.setBoletinOficial(boletinOficial);
                responsePersona.setFechaInsercionBoletin(fechaInsercionBoletin);
                // TODO faltan los demás campos: teléfono y código postal, fechaCargo. Tablas core
                responseFinal.add(responsePersona);
            }
        }

        return responseFinal;
    }
}
