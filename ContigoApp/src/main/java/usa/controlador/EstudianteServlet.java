package usa.controlador;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import usa.factory.AbstractFactory;
import usa.factory.Producer;
import usa.modelo.dao.EstudianteDao;
import usa.modelo.dao.IDao;
import usa.modelo.dto.Estudiante;
import usa.utils.Utils;

/**
 *
 * @author Santiago Pérez
 */
@WebServlet(name = "Estudiante", urlPatterns = {"/Estudiante"})
public class EstudianteServlet extends HttpServlet {

    AbstractFactory factoryDao = Producer.getFabrica("DAO");
    IDao dao = (IDao) factoryDao.obtener("EstudianteDao");

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json;charset=UTF-8");
        JSONObject respuesta = new JSONObject();
        EstudianteDao daoestu = (EstudianteDao) dao;
        Estudiante estudiante = daoestu.consultar(request.getParameter("id"));
        System.out.println(request.getParameter("id"));
        if (estudiante != null) {
            JSONObject estudianteJson = new JSONObject(Utils.toJson(estudiante));
            respuesta.put("tipo", "ok");
            respuesta.put("estudiante", estudianteJson);
        } else {
            respuesta.put("tipo", "error");
            respuesta.put("mensaje", "No se ha posido consultar el estudiante");
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
        Gson gson = new Gson();
        String mensaje = Utils.readParams(request);
        System.out.println(mensaje);
        Estudiante estudiante = (Estudiante) gson.fromJson(mensaje, Estudiante.class);
        EstudianteDao daoestu = (EstudianteDao) dao;

        if (daoestu.consultar(estudiante.getDocumento()) != null) {
            json.put("tipo", "error");
            json.put("mensaje", "Error el estudiante ya esta registrado");
        } else {
            if (dao.crear(estudiante)) {
                json.put("tipo", "ok");
                json.put("mensaje", "Estudiante creado");
                Utils.enviarCorreoA("confirmacionEstudiante", estudiante.getCorreo());
            } else {
                json.put("tipo", "error");
                json.put("mensaje", "Error al crear estudiante");
            }
        }
        out.print(json.toString());

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
