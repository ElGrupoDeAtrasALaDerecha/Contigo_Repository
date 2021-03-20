package usa.contibot;

import java.io.IOException;
import java.util.LinkedList;
import javax.websocket.Session;
import org.json.JSONObject;
import usa.modelo.dto.Estudiante;
import usa.modelo.dto.Mensaje;
import usa.modelo.dto.PersonalCalificado;
import usa.utils.Utils;

/**
 *
 */
public class Sala extends Thread {

    /**
     * Constructor
     */
    private int codigo;

    /**
     *
     */
    private Estudiante estudiante;

    /**
     *
     */
    private PersonalCalificado personaCalificada;

    private Session sesionEstudiante, sesionPersonal;

    private LinkedList<Mensaje> mensajes;

    public Sala() {
        this.mensajes = new LinkedList();
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public PersonalCalificado getPersonaCalificada() {
        return personaCalificada;
    }

    public void setPersonaCalificada(PersonalCalificado personaCalificada) {
        this.personaCalificada = personaCalificada;
    }

    public Session getSesionEstudiante() {
        return sesionEstudiante;
    }

    public void setSesionEstudiante(Session sesionEstudiante) {
        this.sesionEstudiante = sesionEstudiante;
    }

    public Session getSesionPersonal() {
        return sesionPersonal;
    }

    public void setSesionPersonal(Session sesionPersonal) {
        this.sesionPersonal = sesionPersonal;
    }

    public LinkedList<Mensaje> getMensajes() {
        return mensajes;
    }
    /**
     * Mensaje recibido de estudiante al personal calificado
     * @param objRecibido que es una instancia de lo recibido
     * @param objRespuesta que es una instancia de la respuesta
     * @throws IOException 
     */
    public void recibirMensajeEstudiante(JSONObject objRecibido, JSONObject objRespuesta) throws IOException {
        Mensaje mensaje = new Mensaje();
        mensaje.setEmisor(estudiante.getPrimerNombre() + " " + estudiante.getPrimerApellido());
        mensaje.setMensaje(objRecibido.getString("mensaje"));
        mensaje.setTipo(1);
        mensajes.add(mensaje);
        if (sesionPersonal == null) {
            Mensaje mensaje2 = new Mensaje();
            mensaje2.setEmisor("Conti");
            mensaje2.setMensaje("Déjame hablo con uno de mis amigos para que venga a ayudarte, ¿vale?");
            mensaje2.setTipo(2);
            mensajes.add(mensaje2);
            objRespuesta.put("tipo", "respuesta");
            objRespuesta.put("mensaje", new JSONObject(Utils.toJson(mensaje2))); 
            objRespuesta.put("numeroSala",this.codigo);
            sesionEstudiante.getBasicRemote().sendText(objRespuesta.toString());
        } else {
            objRespuesta.put("tipo", "mensajeEstudiante");
            objRespuesta.put("mensaje", new JSONObject(Utils.toJson(mensaje)));
            objRespuesta.put("numeroSala",this.codigo);
            sesionPersonal.getBasicRemote().sendText(objRespuesta.toString());
        }
    }

    /**
     * Mensaje recibido de personal al personal estudiante
     * @param obj que es una instancia de lo recibido
     * @param objRespuesta que es una instancia de la respuesta
     * @throws IOException 
     */
    public void recibirMensajePersonal(JSONObject obj, JSONObject objRespuesta) throws IOException {
        Mensaje mensaje = new Mensaje();
        mensaje.setEmisor(personaCalificada.getPrimerNombre() + " " + personaCalificada.getPrimerApellido());
        mensaje.setMensaje(obj.getString("mensaje"));
        mensaje.setTipo(2);
        mensajes.add(mensaje);
        objRespuesta.put("tipo", "mensajeDePersonal");
        objRespuesta.put("mensaje", new JSONObject(Utils.toJson(mensaje)));
        sesionEstudiante.getBasicRemote().sendText(objRespuesta.toString());
    }

    public void enviarAdvertenciaAEstudiante(JSONObject obj) throws IOException {
        JSONObject mensaje = new JSONObject();
        Mensaje advertencia = new Mensaje();
        advertencia.setEmisor("Conti");
        advertencia.setMensaje("Lo siento , el personal se ha desconectado espera busco otro amigo");
        advertencia.setTipo(2);
        mensajes.add(advertencia);
        mensaje.put("tipo", "perdidaConexion");
        mensaje.put("mensaje", new JSONObject(Utils.toJson((advertencia))));
        sesionEstudiante.getBasicRemote().sendText(mensaje.toString());
    }

    public void enviarPrimerMensaje(JSONObject obj, JSONObject objRespuesta) throws IOException {
        Mensaje mensaje = new Mensaje();
        mensaje.setEmisor("Conti");
        mensaje.setMensaje("Hola, " + estudiante.getPrimerNombre() + ". Soy Conti y estoy contigo, ¿tienes alguna pregunta?");
        objRespuesta.put("tipo", "codigo sala");
        objRespuesta.put("numero", this.codigo);
        objRespuesta.put("mensaje", new JSONObject(Utils.toJson(mensaje)));
        this.sesionEstudiante.getBasicRemote().sendText(objRespuesta.toString());
        
    }

}
