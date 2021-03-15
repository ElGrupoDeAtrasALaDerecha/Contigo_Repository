package usa.contibot;

import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.json.JSONObject;
import usa.modelo.dao.EstudianteDao;
import usa.modelo.dao.PersonalCalificadoDao;
import usa.modelo.dto.Estudiante;
import usa.modelo.dto.PersonalCalificado;
import usa.modelo.dto.Sala;
import static usa.utils.Utils.generarNumeroSala;

/**
 * Clase Websocket contigo Bot
 *
 * @author Valeria Bermúdez y Santiago Pérez
 * @since 2020-03-09
 * @version 0.0.1
 */
@ServerEndpoint("/contiBot")
public class ContigoBot {

    private LinkedList<Session> sesiones;
    private LinkedList<Session> personal;
    private LinkedList<Sala> salas;

    public ContigoBot() {
        super();
        this.sesiones = new LinkedList();
        this.salas=new LinkedList();
        this.personal = new LinkedList();
    }

    @OnOpen
    public void onOpen(Session sesion) {
        System.out.println("Abrió " + sesion.getId());
        sesiones.add(sesion);
        //ws.send(objeto);
    }

    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Mensaje entrante " + mensaje);
        JSONObject obj = new JSONObject(mensaje);
        String tipo = (String) obj.get("tipo");
        JSONObject objRespuesta = new JSONObject();
        Sala sala = null;
        String objeto = "";
        try {
            switch (tipo) {
                case "ping":
                    sesion.getBasicRemote().sendText("pong");
                    break;
                case "primer ingreso":
                    System.out.println("Creando sala");
                    EstudianteDao dao=new EstudianteDao();
                    Estudiante estudiante = dao.consultarPorToken((String) obj.get("token"));
                    System.out.println("Sesion  :" +sesion.getId());
                    int numerosala = generarNumeroSala();
                    sala = new Sala();
                    sala.setEstudiante(estudiante);
                    sala.setSesionEstudiante(sesion);
                    sala.setCodigo(numerosala);
                    objRespuesta.put("tipo", "codigo sala");
                    objRespuesta.put("numero", numerosala);
                    sesion.getBasicRemote().sendText(objRespuesta.toString());
                    break;
                case "mensaje":
                    objRespuesta.put("tipo", "respuesta");
                    objRespuesta.put("mensaje", "Hola estoy contigo , ¿Tienes alguna pregunta ?");
                    sesion.getBasicRemote().sendText(objRespuesta.toString());
                    break;
                case "ingreso personal":
                    PersonalCalificadoDao personalDao=new PersonalCalificadoDao();
                    PersonalCalificado personal = personalDao.consultarPorToken((String) obj.get("token"));
                    int numeroSala = obj.getInt("numeroSala");
                    
                    sesion.getBasicRemote().sendText(objRespuesta.toString());
                    break;

            }
            System.out.println(mensaje);
            System.out.println("ajá");
        } catch (IOException ex) {
            Logger.getLogger(ContigoBot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @OnClose
    public void onClose(Session sesion) {
        System.out.println("Conexión cerrada " + sesion.getId());
        for (int i = 0; i < sesiones.size(); i++) {
            this.sesiones.remove();
        }

        
    }
    
    @OnError
    public void onError(Session sesion, Throwable t){
        
    }
    
    
}
