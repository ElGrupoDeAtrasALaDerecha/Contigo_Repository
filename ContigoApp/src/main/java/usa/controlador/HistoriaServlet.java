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
import usa.modelo.dao.IDao;
import usa.modelo.dao.IPersonalCalificadoDao;
import usa.modelo.dto.Historia;
import usa.modelo.dto.PersonalCalificado;
import usa.utils.Utils;

/**
 *
 * @author Miguel Rippe, Santiago Cáceres, Laura Blanco y Santiago Pérez
 */
@WebServlet(name = "HistoriaServlet", urlPatterns = {"/Historia"})
public class HistoriaServlet extends HttpServlet {

    AbstractFactory factoryDao = Producer.getFabrica("DAO");
    IDao dao = (IDao) factoryDao.obtener("HistoriaDao");
    IDao personalCalificadoDao = (IDao) factoryDao.obtener("PersonalCalificadoDao");

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        String mensaje = Utils.readParams(request);
        String token = request.getHeader("token");
        System.out.println(mensaje);
        Historia historia = (Historia) Utils.fromJson(mensaje, Historia.class);
        PersonalCalificado p = ((IPersonalCalificadoDao) personalCalificadoDao).consultarPorToken(token);
        historia.setDocumentoCreador(p.getDocumento());
        if (dao.crear(historia)) {
            json.put("tipo", "ok");
            json.put("mensaje", "Historia creada");
        } else {
            json.put("tipo", "error");
            json.put("mensaje", "Error al crear la historia");
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
