package usa.controlador;

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
import usa.modelo.dao.IDao;
import usa.modelo.dao.IPersonalCalificadoDao;
import usa.modelo.dto.PersonalCalificado;
import usa.utils.Utils;

/**/
/**
 *
 * @author Valeria Bermúdez & Santiago Pérez
 */
@WebServlet(name = "LoginPersonalCalificadoServlet", urlPatterns = {"/LoginPersonalCalificado"})
public class LoginPersonalCalificadoServlet extends HttpServlet {
    AbstractFactory factoryDao=Producer.getFabrica("DAO");
    IDao dao = (IDao)factoryDao.obtener("PersonalCalificadoDao");
    /**
     * Handles the HTTP <code>POST</code> method. En este caso, se habla del
     * ingreso de un personal calificado
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
        JSONObject respuesta = new JSONObject();
        IPersonalCalificadoDao daoPersonal=(IPersonalCalificadoDao)dao;
        PersonalCalificado personalcalificado = daoPersonal.consultarPorCredenciales(parametroJson.getString("correo"), parametroJson.getString("contraseña"));
        if (personalcalificado != null) {
            JSONObject personalJson = new JSONObject(Utils.toJson(personalcalificado));
            personalJson.remove("contraseña");
            respuesta.put("tipo", "ok");
            respuesta.put("mensaje", "Bienvenido ");
            respuesta.put("personal", personalJson);
        } else {
            respuesta.put("tipo", "error");
            respuesta.put("mensaje", "Correo o Contraseña incorrecta ");
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
