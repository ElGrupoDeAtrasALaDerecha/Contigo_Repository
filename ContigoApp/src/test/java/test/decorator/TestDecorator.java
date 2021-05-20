package test.decorator;

import org.json.JSONObject;
import usa.modelo.dto.PersonalCalificado;
import usa.modelo.dto.decorador.Biografia;
import usa.modelo.dto.decorador.Especialidad;
import usa.modelo.dto.decorador.Experiencia;
import usa.modelo.dto.decorador.IInformacion;
import usa.utils.Utils;

public class TestDecorator {
    public static void main(String[] args) {
        PersonalCalificado decorado = new PersonalCalificado();
        IInformacion p=decorado;
        ((PersonalCalificado)p).setPrimerNombre("Pedro");
        ((PersonalCalificado)p).setPrimerApellido("Pataquiva");
        p = new Biografia(p);
        ((Biografia)p).setBiografia("Amigo de Pablo Pataquiva");
        
        p = new Especialidad(p);
        ((Especialidad)p).setEspecialidad("Amarillista");
        
        
        p = new Experiencia(p);
        ((Experiencia)p).setCargo("Monitor");
        ((Experiencia)p).setDetalles("Monitor de cálculo");
        
        p = new Experiencia(p);
        ((Experiencia)p).setCargo("Monitor");
        ((Experiencia)p).setDetalles("Monitor de cálculo 2");
        
        p.agregarInformacion();
        decorado.limpiar();
        System.out.println(new JSONObject(Utils.toJson(decorado)).toString());
    }
}
