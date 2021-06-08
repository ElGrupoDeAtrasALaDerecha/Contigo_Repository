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
import usa.modelo.dao.IDaoConversatorios;
import usa.modelo.dto.EstudianteConversatorio;
import usa.observer.ObservadorEstudianteConversatorio;
import usa.utils.Utils;

/**
 *
 * @author migue
 */
@WebServlet(name = "REstudianteConversatorio", urlPatterns = {"/REstudianteConversatorio"})
public class REstudianteConversatorio extends HttpServlet {
   AbstractFactory factoryDao=Producer.getFabrica("DAO");
    IDao dao = (IDao)factoryDao.obtener("ConversatoriosDao");


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
        String id = request.getParameter("id");
        String idEstudiante = request.getParameter("idEstudiante");
        JSONObject respuesta = new JSONObject();
        IDaoConversatorios daoConver = (IDaoConversatorios) dao;
        System.out.println(id + "  " + idEstudiante);
        EstudianteConversatorio estuConsultar = (EstudianteConversatorio) daoConver.consultarEstConversatorio(id, idEstudiante);
        if (estuConsultar != null) {
            respuesta.put("tipo", "ok");
            respuesta.put("mensaje", "El estudiante esta registrado");
        } else {
            respuesta.put("tipo", "error");
            respuesta.put("mensaje", "El estudiante no esta registrado");
        }
        PrintWriter out = response.getWriter();
        out.print(respuesta.toString());
    }

    /**
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        String parametros = Utils.readParams(request);
        System.out.println(parametros);
        IDaoConversatorios daoConver = (IDaoConversatorios) dao;
        EstudianteConversatorio esco = (EstudianteConversatorio) Utils.fromJson(parametros, EstudianteConversatorio.class);
        ObservadorEstudianteConversatorio observador= new ObservadorEstudianteConversatorio(esco);
        
        JSONObject respuesta = new JSONObject();

        if (daoConver.registrarEstuConver(esco)) {
            respuesta.put("tipo", "ok");
            respuesta.put("mensaje", "El estudiante fue registrado en el conversatorio");
        } else {
            respuesta.put("tipo", "error");
            respuesta.put("mensaje", "Ya esta registrado en el conversatorio");
        }
        PrintWriter out = response.getWriter();
        out.print(respuesta.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String id = request.getParameter("id");
        String idEstudiante = request.getParameter("idEstudiante");
        JSONObject respuesta = new JSONObject();
        IDaoConversatorios daoConver=(IDaoConversatorios)dao;
        EstudianteConversatorio estuEliminar = (EstudianteConversatorio) daoConver.consultarEstConversatorio(id,idEstudiante);
        if (estuEliminar  != null) {
            if (daoConver.eliminarRegistroEstu(id,idEstudiante)) {
                respuesta.put("tipo", "ok");
                respuesta.put("mensaje", "Registro eliminado");                
            } else {
                respuesta.put("tipo", "error");
                respuesta.put("mensaje", "No se puede eliminar el registro");
            }
        } else {
            respuesta.put("tipo", "error");
            respuesta.put("mensaje", "No esta registrado en el conversatorio");
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
