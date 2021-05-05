package usa.controlador;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import usa.factory.AbstractFactory;
import usa.factory.Producer;
import usa.modelo.dao.IDao;
import usa.modelo.dto.Historia;
import usa.modelo.dto.Situacion;
import usa.utils.Utils;

/**
 *
 * @author Miguel Rippe, Santiago Cáceres, Laura Blanco y Santiago Pérez
 */
@WebServlet(name = "HistoriaServlet", urlPatterns = {"/Historia"})
public class HistoriaServlet extends HttpServlet {

    AbstractFactory factoryDao = Producer.getFabrica("DAO");
    IDao dao = (IDao) factoryDao.obtener("HistoriaDao");
    IDao dao2 = (IDao) factoryDao.obtener("SituacionDao");
    IDao personalCalificadoDao = (IDao) factoryDao.obtener("PersonalCalificadoDao");

    /**
     * Handles the HTTP <code>GET</code> method.holi
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
        JSONObject respuesta = new JSONObject();
        String id = request.getParameter("id");
        if (id != null) {
            Historia historia = (Historia) dao.consultar(id);
            respuesta.put("historia",new JSONObject(Utils.toJson(historia)));
        } else {
            respuesta.put("tipo", "ok");
            respuesta.put("historias", new JSONArray(Utils.toJson(dao.listarTodos())));
        }

        PrintWriter out = response.getWriter();
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
        String mensaje = Utils.readParams(request);
        String token = request.getHeader("token");
        System.out.println(mensaje);
        Historia historia = (Historia) Utils.fromJson(mensaje, Historia.class);
        if (dao.crear(historia)) {
            json.put("tipo", "ok");
            json.put("mensaje", "Historia creada");
            json.put("idHistoria", historia.getId());
            Situacion situacion = new Situacion();
            situacion.setIdHistoria(historia.getId());
            situacion.setTitulo("");
            situacion.setTexto("");
            dao2.crear(situacion);
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
     * funcionaaaaaa
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
