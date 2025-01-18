package arg.boletinesoficiales.validadores;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        String[] cargos = {
                "ABSORBIDA", "GERENTE", "Director Titular", "Presidente",
                "Representante Legal", "Socio Solidario", "Socio Comanditado",
                "Socio Comanditario", "Socio Gerente", "UNICAMENTE PARA SOCIEDADES DE HECHO Y COLECTIVA",
                "DENOMINACION ANTERIOR", "ESCINDIDA", "Vicepresidente", "Vicepresidente Primero", "Vicepresidente Segundo",
                "Vicepresidente Tercero", "Vicepresidente Cuarto", "FUSION", "UTE", "Directivo"
        };

        String cargoOut = "";
        for (String cargo : cargos) {
            Pattern pattern = Pattern.compile("\\b" + cargo.toUpperCase() + "\\b");
            Matcher matcher = pattern.matcher(cargoIn);
            if (matcher.find()) {
                cargoOut = cargo;
                break;
            }
            if (cargoOut.isEmpty()) {
                switch (cargoIn) {
                    case "DIRECTORA TITULAR":
                        cargoOut = "Director Titular";
                        break;
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
                    case "VICEPRESIDENTA PRIMERO":
                        cargoOut = "Vicepresidente Primero";
                        break;
                    case "VICEPRESIDENTA SEGUNDO":
                        cargoOut = "Vicepresidente Segundo";
                        break;
                    case "VICEPRESIDENTA TERCERO":
                        cargoOut = "Vicepresidente Tercero";
                        break;
                    case "VICEPRESIDENTA CUARTO":
                        cargoOut = "Vicepresidente Cuarto";
                        break;
                    default:
                        cargoOut = "";
                }
            }
            if(cargoOut.isEmpty()){
                String patron = "(ADMINISTRADOR TITULAR|SOCIO ADMINISTRADOR|REPRESENTANTE|REPRESENTANTE Y USO DE LA FIRMA)";
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        try {
            if (fecha != null) {
                sdf.parse(fecha);
                return fecha;
            } else {
                return "";
            }
        } catch (ParseException e) {
            return "";
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
