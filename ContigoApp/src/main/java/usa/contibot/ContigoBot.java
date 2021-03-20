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
import org.json.JSONArray;
import org.json.JSONObject;
import usa.modelo.dao.EstudianteDao;
import usa.modelo.dao.PersonalCalificadoDao;
import usa.modelo.dto.Estudiante;
import usa.modelo.dto.PersonalCalificado;
import usa.utils.Utils;

/**
 * Clase Websocket contigo Bot
 *
 * @author Valeria Bermúdez y Santiago Pérez
 * @since 2020-03-09
 * @version 0.0.1
 */
@ServerEndpoint("/contiBot")
public class ContigoBot {

    private static final LinkedList<Session> PERSONALES = new LinkedList();
    private static final LinkedList<Sala> SALAS = new LinkedList();
    private final int numero;

    public ContigoBot() {
        super();
        numero = Utils.generarNumeroSala();
    }

    @OnOpen
    public void onOpen(Session sesion) {
        System.out.println("Abrió " + sesion.getId());
    }

    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Número aleatorio: " + numero);
        System.out.println("Mensaje entrante " + mensaje);
        System.out.println("Mensaje de " + sesion.getId());
        JSONObject obj = new JSONObject(mensaje);
        String tipo = (String) obj.get("tipo");
        JSONObject objRespuesta = new JSONObject();
        Sala sala = null;
        int numeroSala;
        PersonalCalificadoDao personalDao = new PersonalCalificadoDao();
        PersonalCalificado personalCalificado = null;
        try {
            switch (tipo) {
                case "ping":
                    //Ping - pong
                    sesion.getBasicRemote().sendText("pong");
                    break;
                case "ingreso estudiante":
                    //Un estudiante se conecta y se crea una sala
                    System.out.println("Creando sala");
                    EstudianteDao dao = new EstudianteDao();
                    Estudiante estudiante = dao.consultarPorToken((String) obj.get("token"));
                    sala = new Sala();
                    sala.setEstudiante(estudiante);
                    sala.setSesionEstudiante(sesion);
                    sala.setCodigo(Utils.generarNumeroSala());
                    sala.enviarPrimerMensaje(obj,objRespuesta);
                    
                    SALAS.add(sala);
                    System.out.println("Salas actuales: " + SALAS.toString());

                    //Se manda información al personal calificado acerca de la nueva sala
                    JSONObject respuestaPersonal = new JSONObject();
                    respuestaPersonal.put("tipo", "nuevoEstudiante");
                    JSONObject jsonEstudianteEnSala = new JSONObject(Utils.toJson(estudiante));
                    respuestaPersonal.put("estudiante", jsonEstudianteEnSala);
                    respuestaPersonal.put("mensajes", new JSONArray(Utils.toJson(sala.getMensajes())));
                    respuestaPersonal.put("numeroSala", sala.getCodigo());
                    respuestaPersonal.put("atendido", sala.getPersonaCalificada() != null);
                    System.out.println(respuestaPersonal);
                    notificarAlPersonal(respuestaPersonal);
                    break;
                case "mensaje":
                    //Mensaje de estudiante
                    numeroSala = obj.getInt("numeroSala");
                    sala = buscarSalas(numeroSala);
                    sala.recibirMensajeEstudiante(obj, objRespuesta);
                    break;
                case "ingreso personal":
                    //Personal calificado se conecta a su menú principal
                    personalCalificado = personalDao.consultarPorToken((String) obj.get("token"));
                    System.out.println(SALAS.toString());
                    PERSONALES.add(sesion);
                    objRespuesta.put("tipo", "salas");
                    objRespuesta.put("salas", listarSalas());
                    objRespuesta.put("data",new JSONObject(Utils.toJson(personalCalificado)));
                    

                    sesion.getBasicRemote().sendText(objRespuesta.toString());
                    break;
                case "mensaje personal":
                    //El mensaje de personal calificado a una sala
                    numeroSala = obj.getInt("numeroSala");
                    sala = buscarSalas(numeroSala);
                    sala.recibirMensajePersonal(obj, objRespuesta);
                    break;
                case "conexion personal":

                    //Se asigna un personal a una sala y se le manda la conversación
                    personalCalificado = personalDao.consultarPorToken((String) obj.get("token"));
                    sala = this.buscarSalas(obj.getInt("numeroSala"));
                    sala.setPersonaCalificada(personalCalificado);
                    sala.setSesionPersonal(sesion);
                    objRespuesta.put("tipo","conversacion");
                    objRespuesta.put("conversacion",new JSONArray(Utils.toJson(sala.getMensajes())));
                    objRespuesta.put("numeroSala",sala.getCodigo());
                    objRespuesta.put("estudiante",new JSONObject(Utils.toJson(sala.getEstudiante())));
                    sesion.getBasicRemote().sendText(objRespuesta.toString());
                    
                    //Aviso a todos los personales que el estudiante de una sala ya está siendo atendido
                    JSONObject avisoAPersonales = new JSONObject();
                    avisoAPersonales.put("tipo", "estudianteAtendido");
                    avisoAPersonales.put("numeroSala", obj.getInt("numeroSala"));
                    this.notificarAlPersonalExceptoA(avisoAPersonales, sesion);

                    break;

            }
        } catch (IOException ex) {
            Logger.getLogger(ContigoBot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param sesion
     * @throws IOException
     */
    @OnClose
    public void onClose(Session sesion) throws IOException {
        System.out.println("Conexión cerrada " + sesion.getId());
        for (int i = 0; i < PERSONALES.size(); i++) {
            if (PERSONALES.get(i).getId().equals(sesion.getId())) {//Si el personal es el que se desconecta y está con salas
                PERSONALES.remove(i);
                for (int j = 0; j < SALAS.size(); j++) {
                    if (SALAS.get(j).getSesionPersonal() != null) { 
                        if (sesion.getId().equals(SALAS.get(j).getSesionPersonal().getId())) {
                            SALAS.get(j).setSesionPersonal(null);
                            SALAS.get(j).setPersonaCalificada(null);
                            JSONObject mensaje = new JSONObject();
                            mensaje.put("tipo", "desconexionPersonal");
                            mensaje.put("mensaje", "<b>Conti: </b> mi amigo se desconectó, déjame busco otro para que te ayude en lo que estaban hablando");
                            SALAS.get(j).enviarAdvertenciaAEstudiante(mensaje);
                            JSONObject respuestaPersonal = new JSONObject();
                            respuestaPersonal.put("tipo", "nuevoEstudiante");
                            respuestaPersonal.put("estudiante", new JSONObject(Utils.toJson(SALAS.get(j).getEstudiante())));
                            respuestaPersonal.put("mensajes", new JSONArray(Utils.toJson(SALAS.get(j).getMensajes())));
                            respuestaPersonal.put("numeroSala", SALAS.get(j).getCodigo());
                            respuestaPersonal.put("atendido", SALAS.get(j).getPersonaCalificada() != null);
                            notificarAlPersonal(respuestaPersonal);
                        }

                    }

                }
                return;
            }
        }
        for (int i = 0; i < SALAS.size(); i++) {
            if (SALAS.get(i).getSesionEstudiante().equals(sesion)) {
                Sala sala = SALAS.remove(i);
                Session session = sala.getSesionPersonal();
                JSONObject respuestaAPersonal = new JSONObject();
                respuestaAPersonal.put("tipo", "desconexionEstudiante");
                respuestaAPersonal.put("mensaje", "El estudiante se ha desconectado");
                respuestaAPersonal.put("numeroSala", sala.getCodigo());
                if (session != null) {
                    session.getBasicRemote().sendText(respuestaAPersonal.toString());
                } else {
                    this.notificarAlPersonal(respuestaAPersonal);
                }
                return;
            }
        }
    }

    @OnError
    public void onError(Session sesion, Throwable t) {
        System.out.println(t);
    }

    public Sala buscarSalas(int numeroSala) {
        for (int i = 0; i < SALAS.size(); i++) {
            Sala sala = SALAS.get(i);
            if (sala.getCodigo() == numeroSala) {
                return sala;
            }
        }
        return null;
    }

    public JSONArray listarSalas() {
        JSONArray array = new JSONArray();
        for (Sala sala : SALAS) {
            JSONObject objSalas = new JSONObject();
            objSalas.put("numeroSala", sala.getCodigo());
            objSalas.put("estudiante", new JSONObject(Utils.toJson(sala.getEstudiante())));
            objSalas.put("atendido", sala.getPersonaCalificada() != null);
            objSalas.put("mensajes", new JSONArray(Utils.toJson(sala.getMensajes())));
            array.put(objSalas);
        }
        return array;
    }

    public void notificarAlPersonal(JSONObject object) throws IOException {
        for (int i = 0; i < PERSONALES.size(); i++) {
            PERSONALES.get(i).getBasicRemote().sendText(object.toString());
        }
    }

    public void notificarAlPersonalExceptoA(JSONObject object, Session sesion) throws IOException {
        for (int i = 0; i < PERSONALES.size(); i++) {
            if (!PERSONALES.get(i).equals(sesion)) {
                PERSONALES.get(i).getBasicRemote().sendText(object.toString());
            }
        }
    }
}
