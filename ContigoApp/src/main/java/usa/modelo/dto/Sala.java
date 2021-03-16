package usa.modelo.dto;

import java.io.IOException;
import java.util.LinkedList;
import javax.websocket.Session;
import org.json.JSONObject;

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

    public void setMensajes(LinkedList<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    public void recibirMensajeEstudiante(JSONObject objRecibido, JSONObject objRespuesta) throws IOException {
        Mensaje mensaje = new Mensaje();
        mensaje.setEmisor(estudiante.getPrimerNombre() + " " + estudiante.getPrimerApellido());
        mensaje.setMensaje(objRecibido.getString("mensaje"));
        mensajes.add(mensaje);
        if (sesionPersonal == null) {
            Mensaje mensaje2 = new Mensaje();
            mensaje.setEmisor("Conti");
            mensaje.setMensaje(objRecibido.getString("mensaje"));
            mensajes.add(mensaje);
            objRespuesta.put("tipo", "respuesta");
            objRespuesta.put("mensaje", "Hola estoy contigo , ¿Tienes alguna pregunta ?");
            sesionEstudiante.getBasicRemote().sendText(objRespuesta.toString());
        } else {
            objRespuesta.put("tipo", "mensajeEstudiante");
            objRespuesta.put("mensaje", mensaje);
            sesionPersonal.getBasicRemote().sendText(objRespuesta.toString());
        }
    }

    public void recibirMensajePersonal(JSONObject obj, JSONObject objRespuesta) throws IOException {
        Mensaje mensaje = new Mensaje();
        mensaje.setEmisor(personaCalificada.getPrimerNombre() + " " + personaCalificada.getPrimerApellido());
        mensaje.setMensaje(obj.getString("mensaje"));
        mensajes.add(mensaje);
        objRespuesta.put("tipo", "mensajeDePersonal");
        objRespuesta.put("mensaje", mensaje);
        sesionEstudiante.getBasicRemote().sendText(objRespuesta.toString());
    }

}
