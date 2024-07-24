package arg.boletinesoficiales.service;

import arg.boletinesoficiales.entity.core.*;
import arg.boletinesoficiales.entity.user.Sociedad;
import arg.boletinesoficiales.models.*;
import arg.boletinesoficiales.repository.core.*;
import arg.boletinesoficiales.service.mockNlp.MockNLPBoletinesOficiales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
    private NacionalidadesRepository nacionalidadesRepository;
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
            // todo tengo que hacer un método que vaya recorriendo las sociedades y las vaya insertando en la tabla Sociedad
        }

        return null; // TODO qué devuelvo?????
    }

    private List<Sociedad> obetenerDataFinal(ResponseNLP responseNLP, byte[] boBinario, String fechaInsercionBoletin) {
        // Va a tener toda la informacion de los registros correspondiente a una sociedad principal, es decir la sociedad principal,
        // una o mas sociedades secundarias, y las personas o integrantes de la sociedad principal
        List<Sociedad> responseFinal = new ArrayList<>();

        List<Entities> entities = responseNLP.getEntities(); // lista de entities es porque cada objeto ENTITIES es una SOCIEDAD del boletin oficial que estoy procesando

        for (Entities entitiesPorSociedad : entities) {
            int contador = 0;

            List<SociedadNLP> sociedades = entitiesPorSociedad.getSociedadNLP();
            contador = obtenerDataSociedades(boBinario, fechaInsercionBoletin, sociedades, contador, responseFinal);

            List<Persona> personas = entitiesPorSociedad.getPersonas();
            List<Persona> personasOrdPorRel = validarRelacion(personas); // este metodo va a ver si hay relaciones, asi las ordeno de manera tal que se aplique bien la relacion
            SociedadNLP sociedadNLP = sociedades.get(0);
            if (!sociedadNLP.getDisolucion().equals("Si")) {
                obtenerDataPersonas(boBinario, fechaInsercionBoletin, personasOrdPorRel, sociedadNLP, contador, responseFinal);
            }
        }

        return responseFinal;
    }

    private int obtenerDataSociedades(byte[] boBinario, String fechaInsercionBoletin, List<SociedadNLP> sociedades, int contador, List<Sociedad> responseFinal) {
        for (SociedadNLP sociedadNLP : sociedades) {
            Sociedad responseSociedad = new Sociedad();

            if (sociedadNLP.getAlta().equals("Si")) {
                responseSociedad.setUsuario("A");
                responseSociedad.setSector("PC");
                responseSociedad.setFechaCargo(fechaInsercionBoletin);
            } else if (sociedadNLP.getAlta().equals("No") && (sociedadNLP.getModificacion().equals("Si") || sociedadNLP.getDisolucion().equals("Si"))) {
                responseSociedad.setUsuario("M");
                responseSociedad.setSector("MD");
                if (sociedadNLP.getDisolucion().equals("Si")) {
                    responseSociedad.setSociedadCategoria("DOC");
                    String fechaCargoSociedad = sociedadNLP.getFechaCargo();
                    responseSociedad.setFechaCargo(!(fechaCargoSociedad.isEmpty() || fechaCargoSociedad.isBlank()) ? sociedadNLP.getFechaCargo() : fechaInsercionBoletin);
                } else {
                    responseSociedad.setFechaCargo(fechaInsercionBoletin);
                }
            }
            responseSociedad.setContador(contador);
            responseSociedad.setNombreCompleto(sociedadNLP.getNombre());
            responseSociedad.setFechaNacimiento(sociedadNLP.getFechaConstitucion());
            responseSociedad.setDocumento(sociedadNLP.getCuit());
            Direccion direccionSoc = sociedadNLP.getDireccion();
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
            String provMayus = prov.isBlank() || prov.isEmpty() ? "" : prov.toUpperCase();
            if (!(prov.isBlank() || prov.isEmpty()) && provMayus.equals("CABA")) {
                provMayus = "CAPITAL FEDERAL";
            }
            provMayus = this.validarProvincia(provMayus);
            Provincias provincia = provinciasRepository.find_by_name(provMayus);
            responseSociedad.setProvincia(provincia);

            // CARGOS de sociedad:
            if (sociedadNLP.getCausaModificacion().equals("denominacion anterior")) {
                Cargos cargoSocNLP = cargosRepository.find_by_code("DA");
                responseSociedad.setCargo(cargoSocNLP);
            } else if (sociedadNLP.getCausaModificacion().equals("absorbida")) {
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

    private void obtenerDataPersonas(byte[] boBinario, String fechaInsercionBoletin, List<Persona> personasOrdPorRel, SociedadNLP sociedadNLP, int contador, List<Sociedad> responseFinal) {
        for (Persona persona : personasOrdPorRel) {

            String fuenteCargo = persona.getEsBaja().equals("Si") ? "BAJ" : "BOL";

            String cargoPersonaMayus = persona.getCargo().isBlank() || persona.getCargo().isEmpty() ? "" : persona.getCargo().toUpperCase();
            String cargoOut = validarCargo(cargoPersonaMayus);
            if (cargoOut.isEmpty() && fuenteCargo.equals("BAJ")) {
                // TODO poner logica de baja sin cargo en el documento y sobre escribir la variable cargoOut
                //  primero obtener el tipo societario (vienen con puntos)
                //  en base a eso, setear el cargoOut en base a lo que existe en el maestro

                String[] tiposSocietarios = {"SRL", "SA", "SH", "SCA", "SCS", "UTE", "SAS", "SAU"};

            }

            if (!cargoOut.isEmpty()) {
                Sociedad responsePersona = new Sociedad();

                Cargos cargoPersona = cargosRepository.find_by_name(cargoOut);
                responsePersona.setCargo(cargoPersona);

                responsePersona.setFuenteCargo(fuenteCargo);

                if (sociedadNLP.getAlta().equals("Si")) {
                    responsePersona.setUsuario("A");
                    responsePersona.setSector("PC");
                    responsePersona.setFechaCargo(fechaInsercionBoletin);
                } else if (sociedadNLP.getAlta().equals("No") && (sociedadNLP.getModificacion().equals("Si") || sociedadNLP.getDisolucion().equals("Si"))) {
                    responsePersona.setUsuario("M");
                    responsePersona.setSector("MD");
                    responsePersona.setFechaCargo(persona.getFechaCargo());
                }
                responsePersona.setContador(contador);
                responsePersona.setNombreCompleto(persona.getNombre());
                responsePersona.setFechaNacimiento(persona.getFechaNacimiento());
                responsePersona.setDocumento(persona.getDocumento());
                Direccion direccionPer = persona.getDireccion();
                responsePersona.setCalle(direccionPer.getCalle());
                responsePersona.setAltura(direccionPer.getAltura());
                responsePersona.setPisoDepto(direccionPer.getPiso() + " " + direccionPer.getDepartamento());
                responsePersona.setLocalidad(direccionPer.getLocalidad());
                responsePersona.setBoletinOficial(boBinario);
                responsePersona.setFechaInsercionBoletin(fechaInsercionBoletin);

                String sex = persona.getSexo();
                String sexMayus = sex.isBlank() || sex.isEmpty() ? "" : sex.toUpperCase();
                Sexo sexoPersona = sexoRepository.find_by_name(sexMayus);
                responsePersona.setSexo(sexoPersona);

                String provPersona = direccionPer.getProvincia();
                String provPersonaMayus = provPersona.isBlank() || provPersona.isEmpty() ? "" : provPersona.toUpperCase();
                if (!(provPersona.isBlank() || provPersona.isEmpty()) && provPersonaMayus.equals("CABA")) {
                    provPersonaMayus = "CAPITAL FEDERAL";
                }
                provPersonaMayus = this.validarProvincia(provPersonaMayus);
                Provincias provinciaPersona = provinciasRepository.find_by_name(provPersonaMayus);
                responsePersona.setProvincia(provinciaPersona);

                String nac = persona.getNacionalidad();
                String nacMayus = nac.isBlank() || nac.isEmpty() ? "" : nac.toUpperCase();
                nacMayus = this.validarNacionalidad(nacMayus);
                Nacionalidades nacionalidadPersona = nacionalidadesRepository.find_by_name(nacMayus);
                responsePersona.setNacionalidad(nacionalidadPersona);

                EstadoCivil estadoCivilPersona = estadoCivilRepository.find_by_name(persona.getEstadoCivil());
                responsePersona.setEstadoCivil(estadoCivilPersona);

                if (persona.getConyuge().equals("C")) {
                    responsePersona.setRelacion("C");
                }

                responseFinal.add(responsePersona);

                contador++;
            }
        }
    }

    private String validarCargo(String cargoIn) {
        String[] cargos = {
                "ABSORBIDA", "GERENTE", "Director Titular", "Presidente",
                "Representante Legal", "Socio Solidario", "Socio Comanditado",
                "Socio Comanditario", "Socio Gerente", "UNICAMENTE PARA SOCIEDADES DE HECHO Y COLECTIVA",
                "DENOMINACION ANTERIOR", "ESCINDIDA", "Vicepresidente", "Vicepresidente Primero", "Vicepresidente Segundo",
                "Vicepresidente Tercero", "Vicepresidente Cuarto", "FUSION", "UTE"
        };

        String cargoOut = "";
        for (String cargo : cargos) {
            String cargoMayus = cargo.toUpperCase();
            Pattern pattern = Pattern.compile("\\b" + cargoMayus + "\\b");
            Matcher matcher = pattern.matcher(cargoIn);
            if (matcher.find()) {
                cargoOut = cargo;
            }
        }

        return cargoOut;
    }

    private String validarProvincia(String provIn) {
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

    private String validarNacionalidad(String nacIn) {
        String[] nacionalidades = {"ARGENTINA", "ALEMANIA", "AUSTRALIA", "BOLIVIA", "BRASIL", "COLOMBIA", "CUBA", "CANADA",
                "CHECOSLOVAQUIA", "CHILE", "CHINA", "ESPAÑA", "ECUADOR", "FRANCIA", "GRAN BRETAÑA", "HOLANDA", "ITALIA",
                "IRLANDA", "JAPON", "KOREA", "MEXICO", "EXTRANJERO", "PERU", "PORTUGAL", "PARAGUAY", "SUECIA", "SUIZA",
                "TAIWAN", "URUGUAY", "ESTADOS UNIDOS", "EXTRANJERO", "YUGOSLAVIA"};

        String nacOut = "";
        for (String nacionalidad : nacionalidades) {
            Pattern pattern = Pattern.compile("\\b" + nacionalidad + "\\b");
            Matcher matcher = pattern.matcher(nacIn);
            if (matcher.find()) {
                nacOut = nacionalidad;
            }
        }

        return nacOut;
    }

    // Este metodo ordena a la lista de personas para que las personas casadas queden en secuencia
    // dentro de las parejas, la que aparece segunda es la que llevará seteado C de conyuge
    private List<Persona> validarRelacion(List<Persona> personas) {
        List<Persona> personasOrdPorRel = new ArrayList<>();

        Map<String, String> relaciones = new HashMap<>();
        Map<String, Persona> relacionesPersona = new HashMap<>();
        for (Persona p : personas) {
            relaciones.put(p.getNombre(), p.getCasadoConIntegrante());
            relacionesPersona.put(p.getNombre(), p);
        }

        for (Persona p : personas) {
            String nombreInteranteCasado = relaciones.get(p.getNombre());
            if (!nombreInteranteCasado.isEmpty() || !nombreInteranteCasado.isBlank()) {
                personasOrdPorRel.add(p);
                Persona integranteCasado = relacionesPersona.get(nombreInteranteCasado);
                personasOrdPorRel.add(integranteCasado);

                integranteCasado.setConyuge("C");

                relaciones.remove(nombreInteranteCasado);
                relacionesPersona.remove(nombreInteranteCasado);
                personas.remove(integranteCasado);
            }
        }

        return personasOrdPorRel;
    }
}
