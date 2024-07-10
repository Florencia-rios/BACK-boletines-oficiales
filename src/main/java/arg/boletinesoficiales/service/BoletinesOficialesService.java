package arg.boletinesoficiales.service;

import arg.boletinesoficiales.entity.core.Provincias;
import arg.boletinesoficiales.entity.core.Sexo;
import arg.boletinesoficiales.entity.user.Sociedad;
import arg.boletinesoficiales.models.Direccion;
import arg.boletinesoficiales.models.Entities;
import arg.boletinesoficiales.models.Persona;
import arg.boletinesoficiales.models.ResponseNLP;
import arg.boletinesoficiales.repository.core.*;
import arg.boletinesoficiales.service.mockNlp.MockNLPBoletinesOficiales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BoletinesOficialesService {

    @Autowired
    private SexoRepository sexoRepository;
    @Autowired
    private ProvinciasRepository provinciasRepository;
    @Autowired
    private EstadoCivilRepository estadoCivilRepository;
    @Autowired
    private NacionalidadesRepository  nacionalidadesRepository;
    @Autowired
    private CargosRepository cargosRepository;

    @Autowired
    private MockNLPBoletinesOficiales nlpBoletinesOficiales;

    // TODO acá puedo usar un BoletinesOficialesResponse, en donde guarde, por cada boletin oficial, las entidades que guardo en la base de datos
    public List<Sociedad> procesarBoletinOficial(List<String> boletinesOficiales) {


        for (String boletinOficial : boletinesOficiales) {
            LocalDate fechaActual = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaInsercionBoletin = fechaActual.format(formatter);

            ResponseNLP responseNLP = nlpBoletinesOficiales.extraerEntidades(boletinOficial);
            byte[] boBinario = Base64.getDecoder().decode(boletinOficial);
            List<Sociedad> dataSociedades = obetenerDataFinal(responseNLP, boBinario, fechaInsercionBoletin);

        }

        return null;
    }

    private List<Sociedad> obetenerDataFinal(ResponseNLP responseNLP, byte[] boBinario, String fechaInsercionBoletin) {
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
            responseSociedad.setBoletinOficial(boBinario);
            responseSociedad.setFechaInsercionBoletin(fechaInsercionBoletin);

            Sexo sexo = sexoRepository.find_by_name("SOCIEDAD");
            responseSociedad.setSexo(sexo);

            String prov = direccionSoc.getProvincia();
            String provMayus = prov.isBlank() || prov.isEmpty()? "" : prov.toUpperCase();
            if(!(prov.isBlank() || prov.isEmpty()) && provMayus.equals("CABA")){
                provMayus = "CAPITAL FEDERAL";
            }
            provMayus = this.validarProvincia(provMayus);
            Provincias provincia = provinciasRepository.find_by_name(provMayus);
            responseSociedad.setProvincia(provincia);
            // TODO faltan los demás campos: teléfono y código postal, fechaCargo, Cargo

            // -alta: 'Socio Solidario' SA siempre
            // -mod:
            // Si se cambia el tipo societario: mod, el cargo es: DENOMINACION ANTERIOR, pasa a llamarse: bla=
            // como 0 en el contador va: <nuevo_nombre nuevo_tipo_soc> cargo: SA
            // como 1 en el contador va: <viejo_nombre viejo tipo_soc> cargo: DA

            // CARFO: ABSORBIDA, ES UNA MODIFICACION: UNA SOC ABS A OTRA
            // REGUNTAR * SI SIGUE EL PATRON DE CAMNIO DE TIPO SOC

            // -dic:
            // para una disolucion FUSION o SA?? * PREGUNTAR
            // REGUNTAR * SI SIGUE EL PATRON DE CAMNIO DE TIPO SOC

            // PREGUNTAR * : UTE - UNION TRANSITORIA DE EMPRESAS

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
                responsePersona.setBoletinOficial(boBinario);
                responsePersona.setFechaInsercionBoletin(fechaInsercionBoletin);
                // TODO faltan los demás campos: teléfono y código postal, fechaCargo. Tablas core

                String sex = persona.getSexo();
                String sexMayus = sex.isBlank() || sex.isEmpty()? "" : sex.toUpperCase();
                Sexo sexoPersona = sexoRepository.find_by_name(sexMayus);
                responseSociedad.setSexo(sexoPersona);

                responsePersona.setRelacion(""); /// TODO tiene una logica, hay que ver de poner en forma consecutiva a las personas casadas

                String provPersona = direccionSoc.getProvincia();
                String provPersonaMayus = provPersona.isBlank() || provPersona.isEmpty()? "" : provPersona.toUpperCase();
                if(!(provPersona.isBlank() || provPersona.isEmpty()) && provPersonaMayus.equals("CABA")){
                    provPersonaMayus = "CAPITAL FEDERAL";
                }
                provPersonaMayus = this.validarProvincia(provPersonaMayus);
                Provincias provinciaPersona = provinciasRepository.find_by_name(provPersonaMayus);
                responseSociedad.setProvincia(provinciaPersona);

                // EN CASO DE BAJA SE LA PUEDE REEMPLAZAR X OTRA PERSONA, EL CARGO ES 'Socio Gerente' (default) o GERENTE si es srl, DESPUES ME VA A PASAR X TIPO SOC
                // SA: PRESIDENTE, SH: SO, SOCIEDAD EN COMANDITA SC O SCA: SB (O SC * PREGUNTAR), SCS EN COMANDITA SIMPLE: SC (O SB * PREGUNTAR),
                // sociedades con representacion en el pais pero no instalados: Representante Legal (* PREGUNTAR SOC)


                // TODO en caso de baja podria ir Fallecido en cargo. NO PASAR A MAYUS NEC

                responseFinal.add(responsePersona);
            }
        }

        return responseFinal;
    }

    private String validarProvincia(String provIn){
        String[] provincias = {
                "SALTA", "BUENOS AIRES", "CAPITAL FEDERAL", "SAN LUIS", "ENTRE RIOS",
                "LA RIOJA", "SANTIAGO DEL ESTERO", "CHACO", "SAN JUAN", "CATAMARCA",
                "LA PAMPA", "MENDOZA", "MISIONES", "FORMOSA", "NEUQUEN", "RIO NEGRO",
                "SANTA FE", "TUCUMAN", "CHUBUT", "TIERRA DEL FUEGO", "CORRIENTES",
                "CORDOBA", "JUJUY", "SANTA CRUZ"
        };

        String provOut = "";
        for (String provincia : provincias) {
            Pattern pattern = Pattern.compile("\\b" + provincia + "\\b");
            Matcher matcher = pattern.matcher(provIn);
            if (matcher.find()) {
                provOut = provincia;
            }
        }

        return provOut;
    }
}
