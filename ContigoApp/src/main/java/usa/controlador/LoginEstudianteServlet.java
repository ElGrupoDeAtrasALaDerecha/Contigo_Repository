/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import usa.modelo.dao.EstudianteDao;
import usa.modelo.dao.PersonalCalificadoDao;
import usa.modelo.dto.Estudiante;
import usa.modelo.dto.PersonalCalificado;
import usa.utils.Utils;

/**
 * Clase Servlet Login Estudiante
 *
 * @author Valeria Bermúdez - Santiago Pérez
 */
@WebServlet(name = "LoginEstudianteServlet", urlPatterns = {"/LoginEstudiante"})
public class LoginEstudianteServlet extends HttpServlet {
/**/
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginEstudianteServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginEstudianteServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        PrintWriter out = response.getWriter();
        response.setContentType("application/json;charset=UTF-8");
        String parametros = Utils.readParams(request);
        JSONObject parametroJson = new JSONObject(parametros);
        EstudianteDao dao = new EstudianteDao();
        JSONObject respuesta = new JSONObject();
        Estudiante estudiante = dao.consultarPorCredenciales(parametroJson.getString("documento"), parametroJson.getString("contraseña"));
        if (estudiante != null) {
            Gson gson = new Gson();
            JSONObject estudianteJson = new JSONObject(gson.toJson(estudiante, Estudiante.class));
            estudianteJson.remove("contraseña");
            respuesta.put("tipo", "ok");
            respuesta.put("mensaje", "Bienvenido ");
            respuesta.put("estudiante", estudianteJson);
        } else {
            respuesta.put("tipo", "error");
            respuesta.put("mensaje", "documento o contraseña incorrecta ");
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
