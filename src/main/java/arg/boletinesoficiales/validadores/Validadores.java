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
        SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/YYYY");
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
}
