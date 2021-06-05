package usa.controlador;

import com.google.gson.Gson;
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
import usa.modelo.dao.IDao;
import usa.modelo.dto.Estudiante;
import usa.utils.Utils;

/**
 * Servlet de consulta de estadísticas de estudiante por grado
 *
 * @author Maria Camila Fernandez ,Andres López, Santiago Pérez
 * @version 1.1
 * @since 2021-06-03
 */
@WebServlet(name = "EstudiantePorGradoServlet", urlPatterns = {"/Estudiante/Grado"})
public class EstudiantePorGradoServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    AbstractFactory factoryDao = Producer.getFabrica("DAO");
    IDao dao = (IDao) factoryDao.obtener("EstudianteDao");
    EstudianteDao daoestu = (EstudianteDao) dao;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String codigo = request.getParameter("codigo");
        String token = request.getHeader("token");
        JSONObject respuesta = new JSONObject();
        if (codigo != null) {
            if (token != null) {
                LinkedList<Estudiante> estudiantes = daoestu.listarEstudiantesPorGrado(codigo);
                if (estudiantes != null) {
                    respuesta.put("tipo", "ok");
                    respuesta.put("estudiantes", new JSONArray(Utils.toJson(estudiantes)));
                } else {
                    respuesta.put("tipo", "error");
                    respuesta.put("mensaje", "No se ha posido consultar el estudiant");
                }
            }else{
                respuesta.put("tipo","error");
                respuesta.put("mensaje","No autorizado");
                response.sendError(403);
            }
        }else{
            response.sendError(400);
        }
        out.print(respuesta.toString());
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        JSONObject json = new JSONObject(Utils.readParams(request));
        PrintWriter out = response.getWriter();
        JSONObject respuesta = new JSONObject();
        LinkedList<Estudiante> estudiantes = daoestu.listarEstudiantesPorGrado(json.getString("grado"));
        if (estudiantes != null) {
            respuesta.put("tipo", "ok");
            respuesta.put("estudiantes", new JSONArray(Utils.toJson(estudiantes)));
        } else {
            respuesta.put("tipo", "error");
            respuesta.put("mensaje", "No se ha posido consultar el estudiant");
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
        return "Short description";
    }// </editor-fold>

}
