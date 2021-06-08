package usa.controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import usa.factory.AbstractFactory;
import usa.factory.Producer;
import usa.modelo.dao.EstudianteDao;
import usa.modelo.dao.INotificacionDao;
import usa.modelo.dao.IPersonalCalificadoDao;
import usa.modelo.dto.Notificacion;
import usa.modelo.dto.Persona;
import usa.utils.Utils;

/**
 * Servlet de notificaciones
 *
 * @author Santiago Pérez
 * @version 1.0
 * @since 2021-06-07
 */
@WebServlet(name = "NotificacionServlet", urlPatterns = {"/Notificacion"})
public class NotificacionServlet extends HttpServlet {

    AbstractFactory factoryDao = Producer.getFabrica("DAO");
    EstudianteDao estudianteDao = (EstudianteDao) factoryDao.obtener("EstudianteDao");
    IPersonalCalificadoDao personalDao = (IPersonalCalificadoDao) factoryDao.obtener("PersonalCalificadoDao");
    INotificacionDao notificacionDao = (INotificacionDao) factoryDao.obtener("NotificacionDao");

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String token = request.getHeader("token");
        Persona p = estudianteDao.consultarPorToken(token);
        JSONObject respuesta = null;
        if (p==null){
            p=personalDao.consultarPorToken(token);
            if(p!=null){
                respuesta = obtenerNotificaciones(p);
                respuesta.put("tipo","ok");
                out.print(respuesta.toString());
            }else{
                response.sendError(403);
            }
        }else{
            respuesta=obtenerNotificaciones(p);
            respuesta.put("tipo","ok");
            out.print(respuesta.toString());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String params = Utils.readParams(request);
        Notificacion n = (Notificacion) Utils.fromJson(params, Notificacion.class);
        n.setVista(true);
        JSONObject respuesta = new JSONObject();
        if(notificacionDao.actualizar(n)){
            respuesta.put("tipo","ok");
            respuesta.put("mensaje","notificación actualizada");
        }else{
            respuesta.put("tipo","error");
            respuesta.put("mensaje","error al actualizar la notificación");
        }
        out.print(respuesta.toString());
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet de notificaciones";
    }

    private JSONObject obtenerNotificaciones(Persona p) {
        LinkedList <Notificacion> notificaciones = notificacionDao.consultarNotificacionDe(p.getDocumento());
        JSONObject json = new JSONObject();
        json.put("notificaciones",new JSONArray(Utils.toJson(notificaciones)));
        return json;
    }


}
