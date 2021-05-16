package usa.observer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import usa.contibot.Sala;
import usa.utils.Utils;

/**
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres, Camila Fernández,
 * Andrés López, Natalia Montenegro, Santiago Pérez y Miguel Rippez
 */
public class ObservadorSala extends Observer{

    public ObservadorSala(Subject sujeto) {
        super(sujeto);
    }

    @Override
    public void actualizar() {
        Sala sala = (Sala)sujeto;
        if(sala.getEstado()==2){
            mostrar();
        }
    }

    @Override
    public void mostrar() {
        Sala sala = (Sala)sujeto;
        try {
                //Se manda la conversación al personal califiado
                JSONObject objRespuesta=new JSONObject();
                objRespuesta.put("tipo", "conversacion");
                objRespuesta.put("conversacion", new JSONArray(Utils.toJson(sala.getMensajes())));
                objRespuesta.put("numeroSala", sala.getCodigo());
                objRespuesta.put("estudiante", new JSONObject(Utils.toJson(sala.getEstudiante())));
                sala.getSesionPersonal().getBasicRemote().sendText(objRespuesta.toString());
                
                JSONObject obj=new JSONObject();
                //Aviso al estudiante que se conectó el personal calificado.
                obj.put("mensaje","Hola. Soy "+sala.getPersonaCalificada().getPrimerNombre()+" "+sala.getPersonaCalificada().getPrimerApellido()+""
                        + " Dame un momento reviso tu pregunta");

                //Se mandan datos del personal calificado al estudiante
                JSONObject personal=new JSONObject(Utils.toJson(sala.getPersonaCalificada()));
                personal.remove("token");
                personal.remove("contraseña");
                personal.remove("correo");
                personal.remove("documento");
                objRespuesta.put("personal",personal);
                sala.recibirMensajePersonal(obj, objRespuesta);

            } catch (IOException ex) {
                Logger.getLogger(ObservadorSala.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
}
