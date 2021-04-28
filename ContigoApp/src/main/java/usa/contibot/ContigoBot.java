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
import usa.factory.AbstractFactory;
import usa.factory.Producer;
import usa.modelo.dao.EstudianteDao;
import usa.modelo.dao.IDao;
import usa.modelo.dao.IPersonalCalificadoDao;
import usa.modelo.dto.Estudiante;
import usa.modelo.dto.PersonalCalificado;
import usa.utils.Utils;

/**
 * Clase Websocket contigo Bot
 *
 * @author Valeria Bermúdez, Santiago Pérez y Camila Fernández
 * @since 2020-03-09
 * @version 0.0.2
 */
@ServerEndpoint("/contiBot")
public class ContigoBot {
    /**
     * Lista de personal calificado en sesión
     */
    private static final LinkedList<Session> PERSONALES = new LinkedList();
    /**
     * Lista de salas (estudiantes en sesión)
     */
    private static final LinkedList<Sala> SALAS = new LinkedList();
    
    private static final AbstractFactory factoryDao=Producer.getFabrica("DAO");
    private static final IDao personalDao = (IDao) factoryDao.obtener("PersonalCalificadoDao");
    private static final IDao dao = (IDao) factoryDao.obtener("EstudianteDao");
    /**
     * Método onOpen
     *
     * @param sesion que es la sesión que se abre
     */
    @OnOpen
    public void onOpen(Session sesion) {
        System.out.println("Abrió " + sesion.getId());
    }

    /**
     * Método onMessage. Los mensajes contienen un formato específico que
     * permite determinar qué hacer a partir de un atributo general en sus
     * objetos llamado "tipo"
     *
     * @param mensaje que es el mensaje que viene del cliente.
     * @param sesion que es la sesión que envía el mensaje
     */
    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Mensaje entrante " + mensaje);
        System.out.println("Mensaje de " + sesion.getId());
        JSONObject obj = new JSONObject(mensaje);
        String tipo = (String) obj.get("tipo");
        JSONObject objRespuesta = new JSONObject();
        Sala sala = null;
        int numeroSala;

        IPersonalCalificadoDao personalDaoConcreto=(IPersonalCalificadoDao)personalDao;
        PersonalCalificado personalCalificado = null;
        try {
            switch (tipo) {
                case "ping":
                    //Ping - pong
                    sesion.getBasicRemote().sendText("pong");
                    break;
                case "escribiendoEstudiante":
                    numeroSala = obj.getInt("numeroSala");
                    sala = this.buscarSalas(numeroSala);
                    personalCalificado=sala.getPersonaCalificada();
                    if(personalCalificado!=null){
                        sala.notificarEscribiendoAPersonal(objRespuesta);
                    }
                    break;
                case "escribiendoPersonal":
                    numeroSala = obj.getInt("numeroSala");
                    sala = this.buscarSalas(numeroSala);
                    sala.notificarEscribiendoAEstudiante(objRespuesta);
                    break;
                case "ingreso estudiante":
                    //Un estudiante se conecta y se crea una sala
                    EstudianteDao daoEstudiante = (EstudianteDao)dao;
                    Estudiante estudiante = daoEstudiante.consultarPorToken((String) obj.get("token"));
                    sala = new Sala();
                    sala.setEstudiante(estudiante);
                    sala.setSesionEstudiante(sesion);
                    sala.setCodigo(Utils.generarNumeroSala());
                    sala.enviarPrimerMensaje(objRespuesta);

                    SALAS.add(sala);

                    //Se manda información al personal calificado acerca de la nueva sala
                    JSONObject respuestaPersonal = new JSONObject();
                    respuestaPersonal.put("tipo", "nuevoEstudiante");
                    JSONObject jsonEstudianteEnSala = new JSONObject(Utils.toJson(estudiante));
                    respuestaPersonal.put("estudiante", jsonEstudianteEnSala);
                    respuestaPersonal.put("mensajes", new JSONArray(Utils.toJson(sala.getMensajes())));
                    respuestaPersonal.put("numeroSala", sala.getCodigo());
                    respuestaPersonal.put("atendido", sala.getPersonaCalificada() != null);
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
                    personalCalificado = personalDaoConcreto.consultarPorToken((String) obj.get("token"));
                    System.out.println(SALAS.toString());
                    PERSONALES.add(sesion);
                    objRespuesta.put("tipo", "salas");
                    objRespuesta.put("salas", listarSalas());
                    objRespuesta.put("data", new JSONObject(Utils.toJson(personalCalificado)));

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
                    personalCalificado = personalDaoConcreto.consultarPorToken((String) obj.get("token"));
                    sala = this.buscarSalas(obj.getInt("numeroSala"));
                    sala.setPersonaCalificada(personalCalificado);
                    sala.setSesionPersonal(sesion);
                    objRespuesta.put("tipo", "conversacion");
                    objRespuesta.put("conversacion", new JSONArray(Utils.toJson(sala.getMensajes())));
                    objRespuesta.put("numeroSala", sala.getCodigo());
                    objRespuesta.put("estudiante", new JSONObject(Utils.toJson(sala.getEstudiante())));
                    sesion.getBasicRemote().sendText(objRespuesta.toString());
                    
                    //Aviso al estudiante que se conectó el personal calificado.
                    obj.put("mensaje","Hola. Soy "+personalCalificado.getPrimerNombre()+" "+personalCalificado.getPrimerApellido()+""
                            + " Dame un momento reviso tu pregunta");
                    sala.recibirMensajePersonal(obj, objRespuesta);
                    
                    //Aviso a todos los personales que el estudiante de una sala ya está siendo atendido
                    JSONObject avisoAPersonales = new JSONObject();
                    avisoAPersonales.put("tipo", "estudianteAtendido");
                    avisoAPersonales.put("numeroSala", obj.getInt("numeroSala"));
                    this.notificarAlPersonalExceptoA(avisoAPersonales, sesion);
                    
                    break;
                case "cerrar conexion":
                    sala=this.buscarSalas(obj.getInt("numeroSala"));
                    sala.cerrarConexionAEstudiante(objRespuesta);
                    break;

            }
        } catch (IOException ex) {
            Logger.getLogger(ContigoBot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método onClose.Indica qué hacer cuando se cierran las conexiones 
     *
     * @param sesion que es la sesión que se cierra
     * @throws IOException por posibles errores de entrada y salida de datos
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
                            SALAS.get(j).enviarAdvertenciaAEstudiante();
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

    /**
     * Método onError. Si ocurre una excepción, se dispara este método
     *
     * @param sesion que es la sesión que dispara el error.
     * @param t que es la excepción
     */
    @OnError
    public void onError(Session sesion, Throwable t) {
        System.out.println(t);
    }

    /**
     * Método que permite buscaruna sala a partir de su número
     *
     * @param numeroSala que es el número de la sala
     * @return un objeto de la clase sala si lo encuentra, si no, retorna nulo
     */
    public Sala buscarSalas(int numeroSala) {
        for (int i = 0; i < SALAS.size(); i++) {
            Sala sala = SALAS.get(i);
            if (sala.getCodigo() == numeroSala) {
                return sala;
            }
        }
        return null;
    }

    /**
     * Método que permite listar las salas en formato JSON
     *
     * @return una lista de salas en formato JSON en un objeto JSONArray
     */
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

    /**
     * Método que permite notificar a todo el personal calificado conectado en
     * la sesión un mensaje
     *
     * @param object que es un objeto en formato json para notificar a todo el
     * personal calificado
     * @throws IOException por posibles errores de entrada y salida de datos
     */
    public void notificarAlPersonal(JSONObject object) throws IOException {
        for (int i = 0; i < PERSONALES.size(); i++) {
            PERSONALES.get(i).getBasicRemote().sendText(object.toString());
        }
    }

    /**
     * Método que permite notificar a todo el personal, excepto a un personal
     * específico
     *
     * @param object que es el mensaje en formato json para notificar al
     * personal
     * @param sesion que es la sesión del personal al cual no se le notificará
     * el mensaje
     * @throws IOException por posibles errores de entrada y salida de datos
     */
    public void notificarAlPersonalExceptoA(JSONObject object, Session sesion) throws IOException {
        for (int i = 0; i < PERSONALES.size(); i++) {
            if (!PERSONALES.get(i).equals(sesion)) {
                PERSONALES.get(i).getBasicRemote().sendText(object.toString());
            }
        }
    }
}
