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
import usa.modelo.dao.ClasificacionDao;
import usa.modelo.dao.IDao;
import usa.modelo.dao.IGradoDao;
import usa.modelo.dto.Grado;
import usa.utils.Utils;
/**/
/**
 *
 * @author 
 */
@WebServlet(name = "GradoServlet", urlPatterns = {"/Grado"})
public class GradoServlet extends HttpServlet {
    
    /**/
    
    AbstractFactory factoryDao=Producer.getFabrica("DAO");
    IDao dao = (IDao)factoryDao.obtener("GradoDao");
    IDao clasificacion = (IDao) factoryDao.obtener("ClasificacionDao");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        IGradoDao daoGrado=(IGradoDao)dao;
        JSONObject respuesta = new JSONObject();
        respuesta.put("tipo", "ok");
        respuesta.put("Grados",new JSONArray((Utils.toJson(dao.listarTodos()))));
        respuesta.put("GradosClasificados", new JSONArray((Utils.toJson(daoGrado.listarGradosClasf()))));
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
        //Se convierte de json a objeto Grado
        Grado grado = (Grado) Utils.fromJson(Utils.readParams(request), Grado.class);
        if(dao.crear(grado)){
            json.put("tipo", "ok");
            json.put("mensaje","Grado creado con el código " + grado.getCodigo());
            json.put("codigo", grado.getCodigo());
        }else{
            //
            json.put("tipo", "error");
            json.put("mensaje","Error al crear grado " + grado.getClasificacion_id());
            String existente = ((Grado)(dao.consultar(String.valueOf(grado.getClasificacion_id())))).getCodigo();
            json.put("codigo", existente);
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
        return "Short Description";
    }// </editor-fold>

}
