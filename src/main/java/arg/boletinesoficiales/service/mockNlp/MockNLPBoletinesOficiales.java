package arg.boletinesoficiales.service.mockNlp;

import arg.boletinesoficiales.models.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MockNLPBoletinesOficiales {

    public ResponseNLP extraerEntidades(String boletinOficial) {
        return mock_return_modelo_llm();
    }

    private ResponseNLP mock_return_modelo_llm() {

        ResponseNLP responseNLP = new ResponseNLP();
        List<Entities> entities = new ArrayList<>();
        Entities entity = new Entities();
        List<Persona> personas = new ArrayList<>();

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

        Persona persona2 = new Persona();
        String nombrePersona2 = "Natalia Vanesa Montefusco";
        String documento2 = "25282290";
        String sexo2 = "femenino";
        String fechaNacimiento2 = "2/6/1976";
        String nacionalidad2 = "argentino";
        String estadoCivil2 = "casado";
        String cargo2 = "presidente";
        Direccion direccionPersona2 = new Direccion();

        persona1.setNombre(nombrePersona1);
        persona1.setSexo(sexo1);
        persona1.setDocumento(documento1);
        persona1.setFechaNacimiento(fechaNacimiento1);
        persona1.setNacionalidad(nacionalidad1);
        persona1.setEstadoCivil(estadoCivil1);
        persona1.setCargo(cargo1);
        persona1.setDireccion(direccionPersona1);
        persona1.setEsBaja("No");
        persona1.setCasadoConIntegrante(nombrePersona2);

        direccionPersona2.setCalle("Madariaga");
        direccionPersona2.setAltura("824");
        direccionPersona2.setPiso(null);
        direccionPersona2.setDepartamento(null);
        direccionPersona2.setLocalidad("Luis Guillon");
        direccionPersona2.setProvincia("Buenos Aires");
        persona2.setNombre(nombrePersona2);
        persona2.setSexo(sexo2);
        persona2.setDocumento(documento2);
        persona2.setFechaNacimiento(fechaNacimiento2);
        persona2.setNacionalidad(nacionalidad2);
        persona2.setEstadoCivil(estadoCivil2);
        persona2.setCargo(cargo2);
        persona2.setDireccion(direccionPersona2);
        persona1.setEsBaja("No");
        persona1.setCasadoConIntegrante(nombrePersona1);

        personas.add(persona1);
        personas.add(persona2);

        List<SociedadNLP> sociedades = new ArrayList<>();
        SociedadNLP sociedad = new SociedadNLP();
        String nombreSociedad = "1806 S.A";
        String fechaConstitucion = "19/10/2023";
        Direccion direccionSociedad = new Direccion();
        sociedad.setNombre(nombreSociedad);
        sociedad.setCuit(null);
        sociedad.setFechaConstitucion(fechaConstitucion);
        sociedad.setDireccion(direccionSociedad);
        sociedad.setAlta("Si");
        sociedad.setDisolucion("No");
        sociedad.setModificacion("No");
        sociedades.add(sociedad);


        entity.setSociedadNLP(sociedades);
        entity.setPersonas(personas);
        entities.add(entity);

        responseNLP.setEntities(entities);

        return responseNLP;
    }
}
