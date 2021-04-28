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
import usa.modelo.dto.Institucion;
import usa.utils.Utils;

/**
 *
 * @author santi
 */
@WebServlet(name = "InstitucionServlet", urlPatterns = {"/Institucion"})
public class InstitucionServlet extends HttpServlet {

    AbstractFactory factoryDao=Producer.getFabrica("DAO");
    IDao dao = (IDao)factoryDao.obtener("InstitucionDao");
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
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
        JSONArray arreglo = new JSONArray(Utils.toJson(dao.listarTodos()));
        json.put("Instituciones", arreglo);
        System.out.println(json.toString());
        out.print(json.toString());
    }

    /**
     * Handles the HTTP <code>POST</code> method.a
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
        String nom = Utils.readParams(request);
        Institucion ins = (Institucion) Utils.fromJson(nom, Institucion.class); //forma de leer datos cast
        System.out.println(nom);
        if (dao.consultar(ins.getNombre()) != null) {
            json.put("tipo", "error");
            json.put("mensaje", "Ya existe una institucion con este nombre");
        } else {
            if (dao.crear(ins)) {
                json.put("tipo", "ok");
                json.put("mensaje", "Institucion registrada correctamente");
                Utils.enviarCorreoA("confirmacionInstitucion", ins.getCorreo());
            } else {
                json.put("tipo", "error");
                json.put("mensaje", "Error al registrar institucion");
            }
        }
        out.print(json.toString());
        /*
        
         */
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
