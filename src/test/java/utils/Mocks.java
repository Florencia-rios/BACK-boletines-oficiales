package utils;

import arg.boletinesoficiales.models.*;

import java.util.ArrayList;
import java.util.List;

public class Mocks {

    public ResponseNLP altaSociedad(String fechaBoletin){
        ResponseNLP responseNLP = new ResponseNLP();
        List<Entities> entities = new ArrayList<>();
        Entities entity = new Entities();
        List<Persona> personas = new ArrayList<>();
        List<SociedadNLP> sociedades = new ArrayList<>();

        String nombrePersona1 = "Omar Humberto Camarda";
        String documento1 = "21771887";
        String sexo1 = "masculino";
        String fechaNacimiento1 = "11/1/1971";
        String nacionalidad = "argentino";
        String telefono = "11361564493";
        String estadoCivil = "casado";
        String cargo1 = "director suplente";
        String esBaja = "No";
        String nombrePersona2 = "Natalia Vanesa Montefusco";
        String documento2 = "25282290";
        String sexo2 = "femenino";
        String fechaNacimiento2 = "2/6/1976";
        String cargo2 = "presidente";

        String calle1 = "Roque Sáenz Peña";
        String altura1 = "616";
        String piso1 = "4";
        String depto1 = null;
        String codigoPostal1 = "1406";
        String localidad1 = "CABA";
        String provincia = "Buenos Aires";
        String calle2 = "Madariaga";
        String altura2 = "824";
        String piso2 = null;
        String depto2 = null;
        String codigoPostal2 = "1406";
        String localidad2 = "Luis Guillon";
        Direccion direccionPersona1 = getDireccion(calle1, altura1, piso1, depto1, codigoPostal1, localidad1, provincia);
        Direccion direccionPersona2 = getDireccion(calle2, altura2, piso2, depto2, codigoPostal2, localidad2, provincia);

        Persona persona1 = getPersona(fechaBoletin, nombrePersona1, sexo1, documento1, fechaNacimiento1, nacionalidad, telefono, estadoCivil, cargo1, direccionPersona1, nombrePersona2, esBaja);
        Persona persona2 = getPersona(fechaBoletin, nombrePersona2, sexo2, documento2, fechaNacimiento2, nacionalidad, telefono, estadoCivil, cargo2, direccionPersona2, nombrePersona1, esBaja);
        personas.add(persona1);
        personas.add(persona2);

        String nombreSociedad = "1806 S.A";
        String cuit = "";
        String fechaConstitucion = "19/10/2023";
        String alta = "Si";
        String disolucion = "No";
        String modificacion = "No";
        String causaModificacion = null;

        SociedadNLP sociedad = getSociedad(fechaBoletin, nombreSociedad, cuit, fechaConstitucion, direccionPersona1, alta, disolucion, modificacion, causaModificacion);
        sociedades.add(sociedad);

        entity.setSociedadNLP(sociedades);
        entity.setPersonas(personas);
        entities.add(entity);

        responseNLP.setEntities(entities);

        return responseNLP;
    }

    private static SociedadNLP getSociedad(String fechaCargo, String nombreSociedad, String cuit, String fechaConstitucion, Direccion direccionPersona1, String alta, String disolucion, String modificacion, String causaModificacion) {
        SociedadNLP sociedad = new SociedadNLP();
        sociedad.setNombre(nombreSociedad);
        sociedad.setCuit(cuit);
        sociedad.setFechaConstitucion(fechaConstitucion);
        sociedad.setDireccion(direccionPersona1);
        sociedad.setAlta(alta);
        sociedad.setDisolucion(disolucion);
        sociedad.setModificacion(modificacion);
        sociedad.setCausaModificacion(causaModificacion);
        sociedad.setFechaCargo(fechaCargo);
        return sociedad;
    }

    private static Direccion getDireccion(String calle, String altura, String piso, String depto, String codigoPostal, String localidad, String provincia) {
        Direccion direccion = new Direccion();
        direccion.setCalle(calle);
        direccion.setAltura(altura);
        direccion.setPiso(piso);
        direccion.setDepartamento(depto);
        direccion.setCodigoPostal(codigoPostal);
        direccion.setLocalidad(localidad);
        direccion.setProvincia(provincia);
        return direccion;
    }

    private static Persona getPersona(String fechaCargo, String nombrePersona, String sexo, String documento, String fechaNacimiento, String nacionalidad, String telefono, String estadoCivil, String cargo, Direccion direccionPersona, String nombreConyuge, String esBaja) {
        Persona persona = new Persona();
        persona.setNombre(nombrePersona);
        persona.setSexo(sexo);
        persona.setDocumento(documento);
        persona.setFechaNacimiento(fechaNacimiento);
        persona.setNacionalidad(nacionalidad);
        persona.setTelefono(telefono);
        persona.setEstadoCivil(estadoCivil);
        persona.setCargo(cargo);
        persona.setDireccion(direccionPersona);
        persona.setEsBaja(esBaja);
        persona.setCasadoConIntegrante(nombreConyuge);
        persona.setFechaCargo(fechaCargo);
        return persona;
    }


    public String boletinOficial(){
        return "MTgwNiBTLkEuCgoxKSAxOS8xMC8yMyAyKSBPbWFyIEh1bWJlcnRvIENBTUFSREEsKDk1MC4wMDAgYWNjaW9uZXMpIEROSSAyMTc3MTg4NywgMTEvMS83MSwgUm9xdWUgU8OhZW56IFBlw7FhIDYxNiwgUGlzbwo0LCBDQUJBOyB5IE5hdGFsaWEgVmFuZXNhIE1PTlRFRlVTQ08sICg1MC4wMDAgYWNjaW9uZXMpIEROSSAyNTI4MjI5MCwgMi82Lzc2LCBNYWRhcmlhZ2EgODI0LCBMdWlzIEd1aWxsb24sClByb3YuIEJzLkFzLiBBbWJvcyBhcmdlbnRpbm9zLCBjYXNhZG9zLCBlbXByZXNhcmlvcy4gMykgUm9xdWUgU8OhZW56IFBlw7FhIDYxNiwgUGlzbyA0LCBDQUJBLiA0KSBFdmVudG9zLApmaWVzdGFzLCBjYXRlcmluZywgdmVudGEgeSBhbHF1aWxlciBkZSBlc3RydWN0dXJhcyBtw7N2aWxlczogT3JnYW5pemFjacOzbiBkZSBldmVudG9zIHkgZmllc3RhcyBzb2NpYWxlczogYWxxdWlsZXIgeQp2ZW50YSBkZSB2YWppbGxhIHkgbWFudGVsZXLDrWEgbWVzYXMgeSBzaWxsYXMsIGNhcnBhcywgcGFudGFsbGFzIGdpZ2FudGVzIHkgdG9kbyB0aXBvIGRlIGluc3Vtb3MgYSB1dGlsaXphciBlbiBldmVudG9zCnkgZmllc3RhczsgcHJlc3RhY2nDs24gZGUgc2VydmljaW9zIGRlIGRpc2stam9ja2V5LCBmb3RvZ3JhZsOtYSwgdmlkZW8gZmlsbWFjaW9uZXMsIGNhdGVyaW5nIHkgc2VydmljaW8gZGUgY29taWRhLgpQcm9kdWNjacOzbiwgZGVzYXJyb2xsbyB5IG9yZ2FuaXphY2nDs24gZGUgZXhwb3NpY2lvbmVzLCBpbmF1Z3VyYWNpb25lcywgbGFuemFtaWVudG9zIGRlIHByb2R1Y3RvcywgcmVhbGl6YWNpw7NuIGRlCmVzcGVjdMOhY3Vsb3MsIGNvbmdyZXNvcywgZXZlbnRvcyB5IGRlbcOhcyBhY3RvcyB5IHByb2R1Y3RvcyBvIHNlcnZpY2lvcyBlbXByZXNhcmlvcyBlIGluc3RpdHVjaW9uYWxlcy4gQWxxdWlsZXIgeQp2ZW50YSBkZSBlc3RydWN0dXJhcyBtw7N2aWxlcywgeWEgc2VhIGNhcnBhcywgZXNjZW5hcmlvcywgYW5kYW1pb3MsIHBhcmEgdG9kbyB0aXBvIGRlIGV2ZW50b3Mgc29jaWFsZXMuIDYpIDMwIGHDsW9zLgo3KSAkIDEuMDAuMDAwIGVuIGFjY2lvbmVzIG9yZGluYXJpYXMgbm9taW5hdGl2YXMgZGUgJCAxIHkgMSB2b3RvIGMvdS4gOCkgRGlyZWN0b3JpbyAxIGEgNiB0aXR1bGFyZXMgcG9yIDMgZWplcmNpY2lvcy4KUFJFU0lERU5URTogTmF0YWxpYSBWYW5lc2EgTU9OVEVGVVNDTzsgeSBESVJFQ1RPUiBTVVBMRU5URTogT21hciBIdW1iZXJ0byBDQU1BUkRBIGFtYm9zCmRvbWljaWxpbyBlc3BlY2lhbCBzZWRlIHNvY2lhbC4gOSkgMzEvMTIgQXV0b3JpemFkbyBzZWfDum4gaW5zdHJ1bWVudG8gcMO6YmxpY28gRXNjLiBObyAxNzMgZGUgZmVjaGEgMTkvMTAvMjAyMwpSZWcuIE5vIDk2NApDcmlzdGlhbiBKYXZpZXIgTG9wZXogLSBIYWJpbGl0YWRvIEQuTi5SLk8uIE7CsCAzNjQ5CgplLiAyNC8xMC8yMDIzIE7CsCA4NTU3NS8yMyB2LiAyNC8xMC8yMDIz";
    }
}
