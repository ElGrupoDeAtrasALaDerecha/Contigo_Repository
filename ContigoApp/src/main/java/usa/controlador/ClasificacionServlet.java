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
import usa.modelo.dao.ConversatoriosDao;
import usa.modelo.dao.IDao;
import usa.modelo.dto.Clasificacion;
import usa.utils.Utils;

/**
 *
 * @author migue
 */
@WebServlet(name = "ClasificacionServlet", urlPatterns = {"/ClasificacionServlet"})
public class ClasificacionServlet extends HttpServlet {

    //a
    
    AbstractFactory factoryDao=Producer.getFabrica("DAO");
    IDao dao = (IDao)factoryDao.obtener("ClasificacionDao");
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
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
        JSONArray arreglo = new JSONArray(Utils.toJson(dao.listarTodos()));
        json.put("clasificaciones", arreglo);//
        out.print(json.toString());
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
        String parametros = Utils.readParams(request);
        Gson gson = new Gson();
        Clasificacion clasi = (Clasificacion) gson.fromJson(parametros, Clasificacion.class);
        ConversatoriosDao dao = (ConversatoriosDao) factoryDao.obtener("ConversatoriosDao");
        JSONObject respuesta = new JSONObject();
        JSONArray arreglo = new JSONArray();
        LinkedList<Clasificacion> clasificaciones = dao.consultar(clasi.getId());
        if (clasificaciones != null) {
            respuesta.put("tipo", "ok");

            for (Clasificacion i : clasificaciones) {
                arreglo.put(new JSONObject(gson.toJson(i, Clasificacion.class)));
            }
            respuesta.put("clasificacion", arreglo);
        } else {
            respuesta.put("tipo", "error");
            respuesta.put("mensaje", "No se han podido traer los datos ");
        }

        PrintWriter out = response.getWriter();
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
