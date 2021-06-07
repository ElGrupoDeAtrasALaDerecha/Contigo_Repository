package usa.controlador;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import usa.modelo.dao.EstudianteDao;
import usa.modelo.dto.Estudiante;
import usa.utils.Utils;

/**
 * Clase Servlet Login Estudiante
 *
 * @author Valeria Bermúdez - Santiago Pérez
 */
@WebServlet(name = "LoginEstudianteServlet", urlPatterns = {"/LoginEstudiante"})
public class LoginEstudianteServlet extends HttpServlet {
    

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
            JSONObject estudianteJson = new JSONObject(Utils.toJson(estudiante));
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
