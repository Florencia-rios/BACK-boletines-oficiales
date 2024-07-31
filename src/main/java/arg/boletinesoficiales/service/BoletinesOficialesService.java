package arg.boletinesoficiales.service;

import arg.boletinesoficiales.entity.core.*;
import arg.boletinesoficiales.entity.user.Sociedad;
import arg.boletinesoficiales.models.*;
import arg.boletinesoficiales.repository.core.*;
import arg.boletinesoficiales.repository.user.SociedadRepository;
import arg.boletinesoficiales.service.nlp.NLPBoletinesOficiales;
import arg.boletinesoficiales.validadores.Validadores;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class BoletinesOficialesService {

    @Autowired
    private SexoRepository sexoRepository;
    @Autowired
    private ProvinciasRepository provinciasRepository;
    @Autowired
    private EstadoCivilRepository estadoCivilRepository;
    @Autowired
    private NacionalidadesRepository nacionalidadesRepository;
    @Autowired
    private CargosRepository cargosRepository;
    @Autowired
    private SociedadRepository sociedadRepository;

    @Autowired
    private Validadores validadores;

    @Autowired
    private NLPBoletinesOficiales nlpBoletinesOficiales;

    @Transactional
    public List<Sociedad> procesarBoletinOficial(List<String> boletinesOficiales, String fechaBoletin) {
        List<Sociedad> responseTodosBoletines = new ArrayList<>();

        for (String boletinOficial : boletinesOficiales) {
            LocalDate fechaActual = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaInsercionBoletin = fechaActual.format(formatter);

            ResponseNLP responseNLP = nlpBoletinesOficiales.extraerEntidades(boletinOficial);
            byte[] boBinario = Base64.getDecoder().decode(boletinOficial);
            List<Sociedad> dataSociedades = obetenerDataFinal(responseNLP, boBinario, fechaInsercionBoletin, fechaBoletin);

            sociedadRepository.saveAll(dataSociedades);

            responseTodosBoletines.addAll(dataSociedades);
        }

        return responseTodosBoletines;
    }

    private List<Sociedad> obetenerDataFinal(ResponseNLP responseNLP, byte[] boBinario, String fechaInsercionBoletin, String fechaBoletin) {
        // Va a tener toda la informacion de los registros correspondiente a una sociedad principal, es decir la sociedad principal,
        // una o mas sociedades secundarias, y las personas o integrantes de la sociedad principal
        List<Sociedad> responseFinal = new ArrayList<>();

        List<Entities> entities = responseNLP.getEntities(); // lista de entities es porque cada objeto ENTITIES es una SOCIEDAD del boletin oficial que estoy procesando

        for (Entities entitiesPorSociedad : entities) {
            int contador = 0;

            List<SociedadNLP> sociedades = entitiesPorSociedad.getSociedadNLP();
            contador = obtenerDataSociedades(boBinario, fechaInsercionBoletin, fechaBoletin, sociedades, contador, responseFinal);

            SociedadNLP sociedadNLP = sociedades.get(0);
            List<Persona> personas = entitiesPorSociedad.getPersonas();
            List<Persona> personasOrdPorRel = validarRelacion(personas, sociedadNLP); // este metodo va a ver si hay relaciones, asi las ordeno de manera tal que se aplique bien la relacion
            if (!sociedadNLP.getDisolucion().equals("Si")) {
                obtenerDataPersonas(boBinario, fechaInsercionBoletin, fechaBoletin, personasOrdPorRel, sociedadNLP, contador, responseFinal);
            }
        }

        return responseFinal;
    }

    private int obtenerDataSociedades(byte[] boBinario, String fechaInsercionBoletin, String fechaBoletin, List<SociedadNLP> sociedades, int contador, List<Sociedad> responseFinal) {
        for (SociedadNLP sociedadNLP : sociedades) {
            Sociedad responseSociedad = new Sociedad();

            if (sociedadNLP.getAlta().equals("Si")) {
                responseSociedad.setUsuario("A");
                responseSociedad.setSector("PC");
                responseSociedad.setFechaCargo(fechaBoletin);
            } else if (sociedadNLP.getAlta().equals("No") && (sociedadNLP.getModificacion().equals("Si") || sociedadNLP.getDisolucion().equals("Si"))) {
                responseSociedad.setUsuario("M");
                responseSociedad.setSector("MD");
                if (sociedadNLP.getDisolucion().equals("Si")) {
                    responseSociedad.setSociedadCategoria("DOC");

                    String fechaCargoSociedad = validadores.validarFormatoFechas(sociedadNLP.getFechaCargo());
                    responseSociedad.setFechaCargo(!(fechaCargoSociedad == null || fechaCargoSociedad.isEmpty() || fechaCargoSociedad.isBlank()) ? sociedadNLP.getFechaCargo() : fechaBoletin);
                } else {
                    responseSociedad.setFechaCargo(fechaBoletin);
                }
            }
            responseSociedad.setContador(contador);
            responseSociedad.setNombreCompleto(sociedadNLP.getNombre().toUpperCase());

            String fechaConstitucion = validadores.validarFormatoFechas(sociedadNLP.getFechaConstitucion());
            responseSociedad.setFechaNacimiento(fechaConstitucion);
            responseSociedad.setDocumento(sociedadNLP.getCuit());
            Direccion direccionSoc = sociedadNLP.getDireccion();
            responseSociedad.setCalle(direccionSoc.getCalle());
            responseSociedad.setAltura(direccionSoc.getAltura());

            String piso = direccionSoc.getPiso() == null ? "" : direccionSoc.getPiso();
            String depto = direccionSoc.getDepartamento() == null ? "" : direccionSoc.getDepartamento();
            String pisoDepto = piso + " " + depto;
            responseSociedad.setPisoDepto(pisoDepto.strip());
            responseSociedad.setCodigoPostal(direccionSoc.getCodigoPostal());
            responseSociedad.setRelacion("");
            responseSociedad.setBoletinOficial(boBinario);
            responseSociedad.setFechaInsercionBoletin(fechaInsercionBoletin);

            Sexo sexo = sexoRepository.find_by_name("SOCIEDAD");
            responseSociedad.setSexo(sexo);

            String localidad = direccionSoc.getLocalidad();
            String localidadMayus = localidad == null ? "" : localidad.toUpperCase();
            responseSociedad.setLocalidad(localidadMayus);

            boolean localidadCABA = localidadMayus.equals("CAPITAL FEDERAL") || localidadMayus.equals("CIUDAD DE BUENOS AIRES") || localidadMayus.equals("CABA");
            String prov = direccionSoc.getProvincia();
            String provMayus = prov == null ? "" : prov.toUpperCase();
            if (localidadCABA || provMayus.equals("CIUDAD DE BUENOS AIRES") || provMayus.equals("CABA")) {
                provMayus = "CAPITAL FEDERAL";
            }
            provMayus = validadores.validarProvincia(provMayus);
            Provincias provincia = provinciasRepository.find_by_name(provMayus);
            responseSociedad.setProvincia(provincia);

            // CARGOS de sociedad:
            if (sociedadNLP.getCausaModificacion() != null && sociedadNLP.getModificacion().equals("Si") && sociedadNLP.getCausaModificacion().equals("denominacion anterior")) {
                Cargos cargoSocNLP = cargosRepository.find_by_code("DA");
                responseSociedad.setCargo(cargoSocNLP);
            } else if (sociedadNLP.getCausaModificacion() != null && sociedadNLP.getCausaModificacion().equals("absorbida")) {
                Cargos cargoSocNLP = cargosRepository.find_by_code("AB");
                responseSociedad.setCargo(cargoSocNLP);
            } else {
                Cargos cargoSocNLP = cargosRepository.find_by_code("SA");
                responseSociedad.setCargo(cargoSocNLP);
            }
            //////

            responseFinal.add(responseSociedad);

            contador++;
        }
        return contador;
    }

    private void obtenerDataPersonas(byte[] boBinario, String fechaInsercionBoletin, String fechaBoletin, List<Persona> personasOrdPorRel, SociedadNLP sociedadNLP, int contador, List<Sociedad> responseFinal) {
        for (Persona persona : personasOrdPorRel) {

            String fuenteCargo = persona.getEsBaja().equals("Si") ? "BAJ" : "BOL";
            String cargoOut = obtenerCargoValidoOVacio(persona, fuenteCargo, sociedadNLP);

            if (!cargoOut.isEmpty()) { // filtro por cargos validos
                Sociedad responsePersona = new Sociedad();

                Cargos cargoPersona = cargosRepository.find_by_name(cargoOut);
                responsePersona.setCargo(cargoPersona);

                responsePersona.setFuenteCargo(fuenteCargo);

                if (sociedadNLP.getAlta().equals("Si")) {
                    responsePersona.setUsuario("A");
                    responsePersona.setSector("PC");
                    responsePersona.setFechaCargo(fechaBoletin);
                } else if (sociedadNLP.getAlta().equals("No") && (sociedadNLP.getModificacion().equals("Si") || sociedadNLP.getDisolucion().equals("Si"))) {
                    responsePersona.setUsuario("M");
                    responsePersona.setSector("MD");

                    String fechaCargoSociedad = validadores.validarFormatoFechas(persona.getFechaCargo());
                    responsePersona.setFechaCargo(fechaCargoSociedad);
                }
                responsePersona.setContador(contador);
                responsePersona.setNombreCompleto(persona.getNombre().toUpperCase());

                String fechaNacimiento = validadores.validarFormatoFechas(persona.getFechaNacimiento());
                responsePersona.setFechaNacimiento(fechaNacimiento);
                responsePersona.setDocumento(persona.getDocumento());
                responsePersona.setTelefono(persona.getTelefono());
                Direccion direccionPer = persona.getDireccion();
                responsePersona.setCalle(direccionPer.getCalle());
                responsePersona.setAltura(direccionPer.getAltura());

                String piso = direccionPer.getPiso() == null ? "" : direccionPer.getPiso();
                String depto = direccionPer.getDepartamento() == null ? "" : direccionPer.getDepartamento();
                String pisoDepto = piso + " " + depto;
                responsePersona.setPisoDepto(pisoDepto.strip());
                responsePersona.setCodigoPostal(direccionPer.getCodigoPostal());
                responsePersona.setBoletinOficial(boBinario);
                responsePersona.setFechaInsercionBoletin(fechaInsercionBoletin);

                String sex = persona.getSexo();
                String sexMayus = sex == null || sex.isBlank() || sex.isEmpty() ? "NO APORTADO" : sex.toUpperCase();
                String sexValidado = validadores.validarSexo(sexMayus);
                Sexo sexoPersona = sexoRepository.find_by_name(sexValidado);
                responsePersona.setSexo(sexoPersona);

                String localidad = direccionPer.getLocalidad();
                String localidadMayus = localidad == null ? "" : localidad.toUpperCase();
                responsePersona.setLocalidad(localidadMayus);

                boolean localidadCABA = localidadMayus.equals("CAPITAL FEDERAL") || localidadMayus.equals("CIUDAD DE BUENOS AIRES") || localidadMayus.equals("CABA");
                String provPersona = direccionPer.getProvincia();
                String provPersonaMayus = provPersona == null ? "" : provPersona.toUpperCase();
                if (localidadCABA || provPersonaMayus.equals("CIUDAD DE BUENOS AIRES") || provPersonaMayus.equals("CABA")) {
                    provPersonaMayus = "CAPITAL FEDERAL";
                }
                provPersonaMayus = validadores.validarProvincia(provPersonaMayus);
                Provincias provinciaPersona = provinciasRepository.find_by_name(provPersonaMayus);
                responsePersona.setProvincia(provinciaPersona);

                String nac = persona.getPais();
                String nacMayus = nac == null ? "" : nac.toUpperCase();
                nacMayus = validadores.validarNacionalidad(nacMayus);
                Nacionalidades nacionalidadPersona = nacionalidadesRepository.find_by_name(nacMayus);
                responsePersona.setNacionalidad(nacionalidadPersona);

                String estadoCivilAValidar = persona.getEstadoCivil() == null || persona.getEstadoCivil().isEmpty() ? "" : persona.getEstadoCivil().substring(0, persona.getEstadoCivil().length() - 1).toUpperCase();
                String estadoCivilValidado = validadores.validarEstadoCivil(estadoCivilAValidar);
                EstadoCivil estadoCivilPersona = estadoCivilRepository.find_by_name(estadoCivilValidado);
                responsePersona.setEstadoCivil(estadoCivilPersona);

                if (persona.getConyuge() != null && persona.getConyuge().equals("C")) {
                    responsePersona.setRelacion("C");
                }

                responseFinal.add(responsePersona);

                contador++;
            }
        }
    }

    // Este metodo ordena a la lista de personas para que las personas casadas queden en secuencia
    // dentro de las parejas, la que aparece segunda es la que llevará seteado C de conyuge
    private List<Persona> validarRelacion(List<Persona> personas, SociedadNLP sociedadNLP) {
        List<Persona> personasOrdPorRel = new ArrayList<>();

        Map<String, String> relaciones = new HashMap<>();
        Map<String, Persona> nombresConSuPersona = new HashMap<>();
        for (Persona p : personas) {
            relaciones.put(p.getNombre() == null ? "" : p.getNombre(), p.getCasadoConIntegrante() == null ? "" : p.getCasadoConIntegrante());
            nombresConSuPersona.put(p.getNombre() == null ? "" : p.getNombre(), p);
        }

        for (Persona p : personas) {
            String nombreInteranteCasado = relaciones.get(p.getNombre());
            if (!nombreInteranteCasado.isEmpty()) {

                personasOrdPorRel.add(p);
                Persona conyuge = nombresConSuPersona.get(nombreInteranteCasado);

                if (conyuge != null) {
                    personasOrdPorRel.add(conyuge);

                    // chequeo si alguno de los dos tienen un cargo invalido, si es asi no tengo que setear el C en el conyuge
                    String fuenteCargo = conyuge.getEsBaja().equals("Si") ? "BAJ" : "BOL";
                    String cargoOutIntegranteCasado = obtenerCargoValidoOVacio(p, fuenteCargo, sociedadNLP);
                    String cargoOutConyuge = obtenerCargoValidoOVacio(conyuge, fuenteCargo, sociedadNLP);
                    if (!(cargoOutIntegranteCasado.isEmpty() || cargoOutConyuge.isEmpty())) {
                        conyuge.setConyuge("C");
                    }

                    relaciones.remove(nombreInteranteCasado);
                    nombresConSuPersona.remove(nombreInteranteCasado);
                    personas.remove(conyuge);
                }

            } else {
                personasOrdPorRel.add(p);
            }

        }

        return personasOrdPorRel;
    }

    private String obtenerCargoValidoOVacio(Persona persona, String fuenteCargo, SociedadNLP sociedadNLP) {

        String cargoPersonaMayus = persona.getCargo() == null ? "" : persona.getCargo().toUpperCase();
        String cargoOut = validadores.validarCargo(cargoPersonaMayus);
        if (cargoOut.isEmpty() && fuenteCargo.equals("BAJ")) {
            String tipoSocietario = validadores.validarTipoSocietario(sociedadNLP.getNombre());
            switch (tipoSocietario) {
                case "SRL":
                    cargoOut = "Socio Gerente";
                    break;
                case "SA":
                    cargoOut = "Presidente";
                    break;
                case "SH":
                    cargoOut = "UNICAMENTE PARA SOCIEDADES DE HECHO Y COLECTIVA";
                    break;
                case "SCA":
                    cargoOut = "Socio Comanditado";
                    break;
                case "SCS":
                    cargoOut = "Socio Comanditado";
                    break;
                case "UTE":
                    cargoOut = "Representante Legal";
                    break;
                case "SAS":
                    cargoOut = "Directivo";
                    break;
                case "SAU":
                    cargoOut = "Presidente";
                    break;
                default:
                    cargoOut = "";
            }

        }
        return cargoOut;
    }

}
