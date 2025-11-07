package arg.boletinesoficiales.validadores;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Validadores {

    public String validarSexo(String sexoIn) {
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

    public String validarCargo(String cargoIn) {
        /*
        //Cargos viejos
        String[] cargos = {
                "ABSORBIDA", "GERENTE", "Director Titular", "Presidente",
                "Representante Legal", "Socio Solidario", "Socio Comanditado",
                "Socio Comanditario", "Socio Gerente", "UNICAMENTE PARA SOCIEDADES DE HECHO Y COLECTIVA",
                "DENOMINACION ANTERIOR", "ESCINDIDA", "Vicepresidente", "Vicepresidente Primero", "Vicepresidente Segundo",
                "Vicepresidente Tercero", "Vicepresidente Cuarto", "FUSION", "UTE", "Directivo"
        };*/

        // Los cargos nuevos van a ser los siguientes:
        String[] cargos = {
                "ABSORBIDA",
                "GERENTE",
                "DIRECTOR TITULAR",
                "PRESIDENTE",
                "REP. LEGAL",
                "SOCIO SOLIDARIO",
                "SOCIO COMANDITADO",
                "SOCIO COMANDITARIO",
                "SOCIO GERENTE",
                "SOCIO",
                "ESCINDIDA/ESCINDENTE",
                "VICEPRESIDENTE",
                "VICEPRESIDENTE 1",
                "VICEPRESIDENTE 2",
                "VICEPRESIDENTE 3",
                "VICEPRESIDENTE 4",
                "VICEPRESIDENTE 5",
                "FUSIONADA",
                "UTE",
                "DIRECTIVO",
                "CONYUGE",
                "DUENO",
                "DIRECTOR",
                "SOCIO",
                "TITULAR",
                "LIQUIDADOR TITULAR",
                "RELACIONADO",
                "SOCIO CAPITALISTA",
                "FISCALIZADOR",
                "DENOM.ANTERIOR/ACTUAL",
                "SINDICO",
                "SINDICO SUPLENTE",
                "DIRECTOR SUPLENTE",
                "ADMINISTRADOR SUPLENTE"
        };

        String cargoOut = "";
        for (String cargo : cargos) {
            String patron = "\\b" + cargo.toUpperCase() + "\\b";
            Pattern pattern = Pattern.compile(patron);
            Matcher matcher = null;

            matcher = getMatcher(patron, cargo);

            if (matcher.find()) {
                cargoOut = cargo;
                break;
            }
            // TODO ver como hacer para verificar mejor si el cargo que se extrajo deberia estar en femenino
            if (cargoOut.isEmpty()) {
                switch (cargoIn) {
                    case "DIRECTORA TITULAR":
                        cargoOut = "Director Titular";
                        return cargoOut;
                       // break;
                    case "PRESIDENTA":
                        cargoOut = "Presidente";
                        break;
                    case "SOCIA SOLIDARIO":
                        cargoOut = "Socio Solidario";
                        break;
                    case "SOCIA COMANDITADO":
                        cargoOut = "Socio Comanditado";
                        break;
                    case "SOCIA COMANDITARIO":
                        cargoOut = "Socio Comanditario";
                        break;
                    case "SOCIA GERENTE":
                        cargoOut = "Socio Gerente";
                        break;
                    case "VICEPRESIDENTA":
                        cargoOut = "Vicepresidente";
                        break;
                    case "VICEPRESIDENTA 1":
                        cargoOut = "Vicepresidente 1";
                        break;
                    case "VICEPRESIDENTA 2":
                        cargoOut = "Vicepresidente 2";
                        break;
                    case "VICEPRESIDENTA 3":
                        cargoOut = "Vicepresidente 3";
                        break;
                    case "VICEPRESIDENTA 4":
                        cargoOut = "Vicepresidente 4";
                        break;
                    case "VICEPRESIDENTA 5":
                        cargoOut = "Vicepresidente 5";
                        break;
                    default:
                        cargoOut = "";
                }
            }
            // en el caso de Directivo, suele venir con las palabras claves que estanen el string padron
            if(cargoOut.isEmpty()){
                patron = "(ADMINISTRADOR TITULAR|SOCIO ADMINISTRADOR|REPRESENTANTE|REPRESENTANTE Y USO DE LA FIRMA)";
                Pattern pattern2 = Pattern.compile(patron);
                Matcher matcher2 = pattern2.matcher(cargoIn);
                if (matcher2.find()) {
                    cargoOut = "Directivo";
                    break;
                }
            }

        }

        return cargoOut;
    }

    private Matcher getMatcher(String patron, String secuenceToMatch){
        Pattern pattern = Pattern.compile(patron);

        return pattern.matcher(secuenceToMatch);
    }

    public String validarProvincia(String provIn) {
        String[] provincias = {
                "SALTA", "BUENOS AIRES", "CAPITAL FEDERAL", "SAN LUIS", "ENTRE RIOS",
                "LA RIOJA", "SANTIAGO DEL ESTERO", "CHACO", "SAN JUAN", "CATAMARCA",
                "LA PAMPA", "MENDOZA", "MISIONES", "FORMOSA", "NEUQUEN", "RIO NEGRO",
                "SANTA FE", "TUCUMAN", "CHUBUT", "TIERRA DEL FUEGO", "CORRIENTES",
                "CORDOBA", "JUJUY", "SANTA CRUZ"
        };

        String provOut = "";
        for (String provincia : provincias) {
            Pattern pattern = Pattern.compile(provincia);
            Matcher matcher = pattern.matcher(provIn);
            if (matcher.find()) {
                provOut = provincia;
            }
        }

        return provOut;
    }

    public String validarNacionalidad(String nacIn) {
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

    public String validarEstadoCivil(String estadoCivilIn) {
        String[] estadoCivil = {"Soltero", "Casado", "Divorciado"};

        String estadoCivilOut = "";
        for (String ec : estadoCivil) {
            if(ec.toUpperCase().contains(estadoCivilIn)){
                estadoCivilOut = ec;
                break;
            }
        }

        return estadoCivilOut;
    }

    public String validarFormatoFechas(String fecha) {
        if (fecha == null || fecha.isEmpty()) {
            return "";
        }

        // Primero, verificar si ya está en formato yyyyMMdd
        SimpleDateFormat formatoYYYYMMDD = new SimpleDateFormat("yyyyMMdd");
        formatoYYYYMMDD.setLenient(false); // Para evitar que acepte fechas inválidas

        try {
            formatoYYYYMMDD.parse(fecha);
            return fecha; // Ya está en el formato correcto, se retorna tal cual
        } catch (ParseException ignored) {
            // Si falla, significa que no estaba en yyyyMMdd, entonces probamos dd/MM/yyyy
        }

        // Intentar parsear en formato dd/MM/yyyy y convertirlo a yyyyMMdd
        SimpleDateFormat formatoDDMMYYYY = new SimpleDateFormat("dd/MM/yyyy");
        formatoDDMMYYYY.setLenient(false);

        try {
            Date date = formatoDDMMYYYY.parse(fecha);
            return formatoYYYYMMDD.format(date); // Convertir a yyyyMMdd
        } catch (ParseException e) {
            return ""; // Retorna vacío si no coincide con ningún formato válido
        }
    }

    public String validarTipoSocietario(String nombreSociedad) {
        String[] tiposSocietarios = {"SRL", "SA", "SH", "SCA", "SCS", "UTE", "SAS", "SAU"};

        String tipoSocOut = "";
        for (String tipoSoc : tiposSocietarios) {
            Pattern pattern = Pattern.compile("\\b" + tipoSoc.toUpperCase() + "\\b");
            Matcher matcher = pattern.matcher(nombreSociedad.replace(".", ""));
            if (matcher.find()) {
                tipoSocOut = tipoSoc;
            }
        }

        return tipoSocOut;
    }

    public String cuitValidoSociedades(String cuitIn) {
        cuitIn = cuitIn != null? cuitIn.replaceAll("[.-]", "") : "";

        String cuitOut = "";
        // Verifica si el CUIT comienza con "30"
        if (cuitIn != null && cuitIn.length() == 11 && cuitIn.startsWith("30") && cuitIn.matches("\\d+")) {
            cuitOut = cuitIn;
        }
        return cuitOut;
    }

    public String documentoValidoPersonas(String docIn) {
        docIn = docIn != null? docIn.replaceAll("[.-]", "") : "";

        String docOut = "";
        // Verificar que no sea null y que esté dentro de los límites de longitud
        if (docIn != null && (docIn.length() >= 6 || docIn.length() <= 11) && docIn.matches("\\d+")) {
            docOut = docIn;
        }

        // Verificar que solo contenga números
        return docOut;
    }
}
