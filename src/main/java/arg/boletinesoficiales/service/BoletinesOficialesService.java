package arg.boletinesoficiales.service;

import arg.boletinesoficiales.entity.core.*;
import arg.boletinesoficiales.entity.user.Sociedad;
import arg.boletinesoficiales.models.*;
import arg.boletinesoficiales.repository.core.*;
import arg.boletinesoficiales.repository.user.SociedadRepository;
import arg.boletinesoficiales.service.nlp.NLPBoletinesOficiales;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private SociedadRepository sociedadRepository;

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

            List<Persona> personas = entitiesPorSociedad.getPersonas();
            List<Persona> personasOrdPorRel = validarRelacion(personas); // este metodo va a ver si hay relaciones, asi las ordeno de manera tal que se aplique bien la relacion
            SociedadNLP sociedadNLP = sociedades.get(0);
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

                    boolean fechaValida = validarFormatoFechas(sociedadNLP.getFechaCargo());
                    String fechaCargoSociedad = fechaValida? sociedadNLP.getFechaCargo(): "";
                    responseSociedad.setFechaCargo(!(fechaCargoSociedad.isEmpty() || fechaCargoSociedad.isBlank()) ? sociedadNLP.getFechaCargo() : fechaInsercionBoletin);
                } else {
                    responseSociedad.setFechaCargo(fechaBoletin);
                }
            }
            responseSociedad.setContador(contador);
            responseSociedad.setNombreCompleto(sociedadNLP.getNombre());

            boolean fechaValida = validarFormatoFechas(sociedadNLP.getFechaConstitucion());
            String fechaConstitucion = fechaValida? sociedadNLP.getFechaConstitucion(): "";
            responseSociedad.setFechaNacimiento(fechaConstitucion);
            responseSociedad.setDocumento(sociedadNLP.getCuit());
            Direccion direccionSoc = sociedadNLP.getDireccion();
            responseSociedad.setCalle(direccionSoc.getCalle());
            responseSociedad.setAltura(direccionSoc.getAltura());

            String piso = direccionSoc.getPiso() == null? "": direccionSoc.getPiso();
            String depto = direccionSoc.getDepartamento() == null? "": direccionSoc.getDepartamento();
            String pisoDepto = piso + " " + depto;
            responseSociedad.setPisoDepto(pisoDepto.strip());
            responseSociedad.setLocalidad(direccionSoc.getLocalidad());
            responseSociedad.setCodigoPostal(direccionSoc.getCodigoPostal());
            responseSociedad.setRelacion("");
            responseSociedad.setBoletinOficial(boBinario);
            responseSociedad.setFechaInsercionBoletin(fechaInsercionBoletin);

            Sexo sexo = sexoRepository.findByNombre("SOCIEDAD");
            responseSociedad.setSexo(sexo);

            String prov = direccionSoc.getProvincia();
            String provMayus = prov == null ? "" : prov.toUpperCase();
            if (provMayus.equals("CABA")) {
                provMayus = "CAPITAL FEDERAL";
            }
            provMayus = this.validarProvincia(provMayus);
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

            String cargoPersonaMayus = persona.getCargo() == null  ? "" : persona.getCargo().toUpperCase();
            String cargoOut = validarCargo(cargoPersonaMayus);
            if (cargoOut.isEmpty() && fuenteCargo.equals("BAJ")) {
                // TODO poner logica de baja sin cargo en el documento y sobre escribir la variable cargoOut
                //  primero obtener el tipo societario (vienen con puntos)
                //  en base a eso, setear el cargoOut en base a lo que existe en el maestro

                String[] tiposSocietarios = {"SRL", "SA", "SH", "SCA", "SCS", "UTE", "SAS", "SAU"};

            }

            if (!cargoOut.isEmpty()) {
                Sociedad responsePersona = new Sociedad();

                Cargos cargoPersona = cargosRepository.findByName(cargoOut);
                responsePersona.setCargo(cargoPersona);

                responsePersona.setFuenteCargo(fuenteCargo);

                if (sociedadNLP.getAlta().equals("Si")) {
                    responsePersona.setUsuario("A");
                    responsePersona.setSector("PC");
                    responsePersona.setFechaCargo(fechaBoletin);
                } else if (sociedadNLP.getAlta().equals("No") && (sociedadNLP.getModificacion().equals("Si") || sociedadNLP.getDisolucion().equals("Si"))) {
                    responsePersona.setUsuario("M");
                    responsePersona.setSector("MD");

                    boolean fechaValida = validarFormatoFechas(persona.getFechaCargo());
                    String fechaCargoSociedad = fechaValida? persona.getFechaCargo(): "";
                    responsePersona.setFechaCargo(fechaCargoSociedad);
                }
                responsePersona.setContador(contador);
                responsePersona.setNombreCompleto(persona.getNombre());

                boolean fechaValida = validarFormatoFechas(persona.getFechaNacimiento());
                String fechaNacimiento = fechaValida? persona.getFechaNacimiento(): "";
                responsePersona.setFechaNacimiento(fechaNacimiento);
                responsePersona.setDocumento(persona.getDocumento());
                responsePersona.setTelefono(persona.getTelefono());
                Direccion direccionPer = persona.getDireccion();
                responsePersona.setCalle(direccionPer.getCalle());
                responsePersona.setAltura(direccionPer.getAltura());

                String piso = direccionPer.getPiso() == null? "": direccionPer.getPiso();
                String depto = direccionPer.getDepartamento() == null? "": direccionPer.getDepartamento();
                String pisoDepto = piso + " " + depto;
                responsePersona.setPisoDepto(pisoDepto.strip());
                responsePersona.setLocalidad(direccionPer.getLocalidad());
                responsePersona.setCodigoPostal(direccionPer.getCodigoPostal());
                responsePersona.setBoletinOficial(boBinario);
                responsePersona.setFechaInsercionBoletin(fechaInsercionBoletin);

                String sex = persona.getSexo();
                String sexMayus = sex == null || sex.isBlank() || sex.isEmpty()? "NO APORTADO" : sex.toUpperCase();
                String sexValidado = validarSexo(sexMayus);
                Sexo sexoPersona = sexoRepository.findByNombre(sexValidado);
                responsePersona.setSexo(sexoPersona);

                String provPersona = direccionPer.getProvincia();
                String provPersonaMayus = provPersona == null ? "" : provPersona.toUpperCase();
                if (provPersonaMayus.equals("CIUDAD DE BUENOS AIRES") || provPersonaMayus.equals("CABA")) {
                    provPersonaMayus = "CAPITAL FEDERAL";
                }
                provPersonaMayus = this.validarProvincia(provPersonaMayus);
                Provincias provinciaPersona = provinciasRepository.find_by_name(provPersonaMayus);
                responsePersona.setProvincia(provinciaPersona);

                String nac = persona.getPais();
                String nacMayus = nac  == null ? "" : nac.toUpperCase();
                nacMayus = this.validarNacionalidad(nacMayus);
                Nacionalidades nacionalidadPersona = nacionalidadesRepository.find_by_name(nacMayus);
                responsePersona.setNacionalidad(nacionalidadPersona);

                String estadoCivilAValidar = persona.getEstadoCivil()  == null? "": persona.getEstadoCivil().substring(0, persona.getEstadoCivil().length()-1).toUpperCase();
                String estadoCivilValidado = validarEstadoCivil(estadoCivilAValidar);
                EstadoCivil estadoCivilPersona = estadoCivilRepository.find_by_name(estadoCivilValidado);
                responsePersona.setEstadoCivil(estadoCivilPersona);

                if (persona.getConyuge().equals("C")) {
                    responsePersona.setRelacion("C");
                }

                responseFinal.add(responsePersona);

                contador++;
            }
        }
    }

    private boolean validarFormatoFechas(String fecha){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        try {
            sdf.parse(fecha);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private String validarSexo(String sexoIn) {
        String[] sexo = {
                "MASCULINO", "FEMENINO", "NO APORTADO", "SOCIEDAD"
        };

        String sexoOut = "";
        for (String s : sexo) {
            Pattern pattern = Pattern.compile("\\b" + s + "\\b");
            Matcher matcher = pattern.matcher(sexoIn);
            if (matcher.find()) {
                sexoOut = s;
            }
        }

        return sexoOut;
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
            Pattern pattern = Pattern.compile("\\b" + cargo.toUpperCase() + "\\b");
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

    private String validarEstadoCivil(String estadoCivilIn) {
        String[] estadoCivil = {"Soltero", "Casado", "Divorciado"};

        String estadoCivilOut = "";
        for (String ec : estadoCivil) {
            Pattern pattern = Pattern.compile("\\b" + ec.toUpperCase() + "\\b");
            Matcher matcher = pattern.matcher(estadoCivilIn);
            if (matcher.find()) {
                estadoCivilOut = ec;
            }
        }

        return estadoCivilOut;
    }

    // Este metodo ordena a la lista de personas para que las personas casadas queden en secuencia
    // dentro de las parejas, la que aparece segunda es la que llevará seteado C de conyuge
    private List<Persona> validarRelacion(List<Persona> personas) {
        List<Persona> personasOrdPorRel = new ArrayList<>();

        Map<String, String> relaciones = new HashMap<>();
        Map<String, Persona> relacionesPersona = new HashMap<>(); // TODO cambiar el nombre a: nombresConSuPersona
        for (Persona p : personas) {
            // TODO agregar verificacion de null en ambos nombres
            relaciones.put(p.getNombre(), p.getCasadoConIntegrante() == null? "": p.getCasadoConIntegrante());
            relacionesPersona.put(p.getNombre(), p);
        }

        for (Persona p : personas) {
            String nombreInteranteCasado = relaciones.get(p.getNombre());
            if (!nombreInteranteCasado.isEmpty()) {
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
