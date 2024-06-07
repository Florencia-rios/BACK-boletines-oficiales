package arg.boletinesoficiales.service.mockNlp;

import arg.boletinesoficiales.dto.BoletinOficial;
import arg.boletinesoficiales.dto.Entity;
import arg.boletinesoficiales.models.Direccion;
import arg.boletinesoficiales.models.Entities;
import arg.boletinesoficiales.models.Persona;
import arg.boletinesoficiales.models.Sociedad;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MockNLPBoletinesOficiales {

    public String clasificadorDeSociedades(BoletinOficial boletinOficial){
        String response = "";

        // todo hacer un for para recorer las sociedades del boletin

        // MOCK
        response = "alta"; // Los posibles valores son: alta, modificaciones, baja_sociedad

        return response;
    }

    public List<Entity> extraerEntidades(BoletinOficial boletinOficial){
        List<Entity> response = new ArrayList<>();

        // todo hacer un for para recorer las sociedades

        // MOCK
        Entities entities = mock_return_modelo_llm();



        return response;
    }

    private Entities mock_return_modelo_llm() {
        Entities entities = new Entities();

        arg.boletinesoficiales.models.Entity otherEntity1 = new arg.boletinesoficiales.models.Entity();
        arg.boletinesoficiales.models.Entity otherEntity2 = new arg.boletinesoficiales.models.Entity();
        List<arg.boletinesoficiales.models.Entity> otherEntities = new ArrayList<>();

        Sociedad sociedad = new Sociedad();

        String nombreSociedad = "1806 S.A";
        String cuitSociedad = null;
        String fechaConstitucion = "19/10/2023";
        Direccion direccionSociedad = new Direccion();

        sociedad.setNombre(nombreSociedad);
        sociedad.setCuit(cuitSociedad);
        sociedad.setFechaConstitucion(fechaConstitucion);
        sociedad.setDireccion(direccionSociedad);

        Persona persona1 = new Persona();

        String nombrePersona1 = "Omar Humberto Camarda";
        String documento1 = "21771887";
        String sexo1 = "masculino";
        String fechaNacimiento1 = "11/1/1971";
        String nacionalidad1 = "argentino";
        String estadoCivil1 = "casado";
        String cargo1 = "director suplente";
        Direccion direccionPersona1 = new Direccion();
        direccionPersona1.setCalle("Roque Sáenz Peña");
        direccionPersona1.setAltura("616");
        direccionPersona1.setPiso("4");
        direccionPersona1.setDepartamento(null);
        direccionPersona1.setLocalidad("CABA");
        direccionPersona1.setProvincia("Buenos Aires");

        persona1.setNombrePersona(nombrePersona1);
        persona1.setDocumento(documento1);
        persona1.setSexo(sexo1);
        persona1.setFechaNacimiento(fechaNacimiento1); // todo ver el formato cuando lo este por guardar y devolver
        persona1.setNacionalidad(nacionalidad1);
        persona1.setEstadoCivil(estadoCivil1);
        persona1.setCargo(cargo1);
        persona1.setDireccion(direccionPersona1);

        otherEntity1.setPersona(persona1);
        otherEntity1.setSociedad(sociedad);

        Persona persona2 = new Persona();

        String nombrePersona2 = "Natalia Vanesa Montefusco";
        String documento2 = "25282290";
        String sexo2 = "femenino";
        String fechaNacimiento2 = "2/6/1976";
        String nacionalidad2 = "argentino";
        String estadoCivil2 = "casado";
        String cargo2 = "presidente";
        Direccion direccionPersona2 = new Direccion();
        direccionPersona2.setCalle("Madariaga");
        direccionPersona2.setAltura("824");
        direccionPersona2.setPiso(null);
        direccionPersona2.setDepartamento(null);
        direccionPersona2.setLocalidad("Luis Guillon");
        direccionPersona2.setProvincia("Buenos Aires");

        persona2.setNombrePersona(nombrePersona2);
        persona2.setDocumento(documento2);
        persona2.setSexo(sexo2);
        persona2.setFechaNacimiento(fechaNacimiento2); // todo ver el formato cuando lo este por guardar y devolver
        persona2.setNacionalidad(nacionalidad2);
        persona2.setEstadoCivil(estadoCivil2);
        persona2.setCargo(cargo2);
        persona2.setDireccion(direccionPersona2);

        otherEntity2.setPersona(persona2);
        otherEntity2.setSociedad(sociedad);

        otherEntities.add(otherEntity1);
        otherEntities.add(otherEntity2);

        Map<String, String> relationShip = new HashMap<>();
        relationShip.put(nombrePersona1, nombrePersona2);
        relationShip.put(nombrePersona2, nombrePersona1);

        entities.setEntities(otherEntities);
        entities.setRelationShip(relationShip);

        return entities;
    }
}
