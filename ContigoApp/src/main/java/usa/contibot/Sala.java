package usa.contibot;

import java.io.IOException;
import java.util.LinkedList;
import javax.websocket.Session;
import org.json.JSONObject;
import usa.modelo.dto.Estudiante;
import usa.modelo.dto.Mensaje;
import usa.modelo.dto.PersonalCalificado;
import usa.observer.Subject;
import usa.utils.Utils;

/**
 * Clase sala
 *
 * @author Valeria Bermúdez y Santiago Pérez
 */
public class Sala extends Subject{

    /**
     * Código de la sala
     */
    private int codigo;

    /**
     * Datos del estudiante
     */
    private Estudiante estudiante;

    /**
     * Datos del personal calificado
     */
    private PersonalCalificado personaCalificada;

    /**
     * Sesiones de estudiante y personal calificado
     */
    private Session sesionEstudiante, sesionPersonal;

    /**
     * Mensajes de la sesión de chat
     */
    private final LinkedList<Mensaje> mensajes;

    /**
     * Constructor de la clase sala. Se inicializa la lista de mensajes
     */
    public Sala() {
        this.mensajes = new LinkedList();
    }

    /**
     * Mensaje recibido de estudiante al personal calificado
     *
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
            objRespuesta.put("numeroSala", this.codigo);
            sesionEstudiante.getBasicRemote().sendText(objRespuesta.toString());
        } else {
            objRespuesta.put("tipo", "mensajeEstudiante");
            objRespuesta.put("mensaje", new JSONObject(Utils.toJson(mensaje)));
            objRespuesta.put("numeroSala", this.codigo);
            sesionPersonal.getBasicRemote().sendText(objRespuesta.toString());
        }
    }

    /**
     * Mensaje recibido de personal al personal estudiante
     *
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

    /**
     * Método que envia una advertencia al estudiante de desconexión del
     * personal calificado en formato json
     *
     * @throws IOException por posibles errores de entrada y salida de datos
     */
    public void enviarAdvertenciaAEstudiante() throws IOException {
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

    /**
     * Método que envia el mensaje de saludo al estudiante en formato json
     *
     * @param objRespuesta que es una instancia de la respuesta al estudiante.
     * @throws IOException por posibles errores de entrada y salida de datos
     */
    public void enviarPrimerMensaje(JSONObject objRespuesta) throws IOException {
        Mensaje mensaje = new Mensaje();
        mensaje.setEmisor("Conti");
        mensaje.setMensaje("Hola, " + estudiante.getPrimerNombre() + ". Soy Conti y estoy contigo, ¿tienes alguna pregunta?");
        objRespuesta.put("tipo", "codigo sala");
        objRespuesta.put("numero", this.codigo);
        objRespuesta.put("mensaje", new JSONObject(Utils.toJson(mensaje)));
        this.sesionEstudiante.getBasicRemote().sendText(objRespuesta.toString());

    }
    
    /**
     * 
     * @param objRespuesta
     * @throws IOException 
     */
    public void notificarEscribiendoAPersonal(JSONObject objRespuesta) throws IOException{
        objRespuesta.put("tipo","escribiendoEstudiante");
        objRespuesta.put("numeroSala",this.codigo);
        this.sesionPersonal.getBasicRemote().sendText(objRespuesta.toString()); 
    }
    /**
     * Notificación de escritura hacia el estudiante
     * @param objRespuesta referencia en la memoria de la respuesta
     * @throws IOException 
     */
    public void notificarEscribiendoAEstudiante(JSONObject objRespuesta) throws IOException {
        objRespuesta.put("tipo","escribiendoPersonal");
        objRespuesta.put("numeroSala",this.codigo);
        this.sesionEstudiante.getBasicRemote().sendText(objRespuesta.toString());
    }

    void cerrarConexionAEstudiante(JSONObject objRespuesta) throws IOException {
        Mensaje mensaje = new Mensaje();
        mensaje.setEmisor("Conti");
        mensaje.setMensaje("Mi amigo terminó la conexión. Espero que te haya ayudado. Si tienes alguna otra pregunta, cuentas conmigo, ¿vale?");
        mensaje.setTipo(1);
        objRespuesta.put("tipo", "cerrar conexion");
        objRespuesta.put("mensaje", new JSONObject(Utils.toJson(mensaje)));
        this.sesionEstudiante.getBasicRemote().sendText(objRespuesta.toString());

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
}
