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
import usa.modelo.dao.ISituacionDao;
import usa.modelo.dto.Historia;
import usa.modelo.dto.Situacion;
import usa.utils.Utils;

/**
 *
 * Servlet de situaciones de decisión
 * @author Miguel Rippe, Santiago Cáceres, Laura Blanco y Santiago Pérez
 */
@WebServlet(name = "SituacionServlet", urlPatterns = {"/Situacion"})
public class SituacionServlet extends HttpServlet {
    AbstractFactory factoryDao=Producer.getFabrica("DAO");
    IDao dao = (IDao) factoryDao.obtener("SituacionDao");
    IDao daoHistorias=(IDao) factoryDao.obtener("HistoriaDao");
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
        ISituacionDao daoSituacion=(ISituacionDao) dao;
        String id = request.getParameter("id");
        JSONObject respuesta = new JSONObject();
        Historia historia = (Historia) daoHistorias.consultar(id);
        if(historia!=null){
            JSONObject arreglo=new JSONObject(Utils.toJson(daoSituacion.consultarPorHistoria(Integer.parseInt(id))));
            respuesta.put("tipo","ok");
            respuesta.put("situaciones", arreglo);
        }else{
            respuesta.put("tipo","error");
            respuesta.put("mensaje", "No existe esa historia");
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        String parametros = Utils.readParams(request);
        System.out.println(parametros);
        Situacion situacion = (Situacion) Utils.fromJson(parametros, Situacion.class);
        JSONObject respuesta = new JSONObject();
        if(dao.crear(situacion)){
            respuesta.put("tipo","ok");
            respuesta.put("mensaje", "Situación creada");
        }else{
            respuesta.put("tipo","error");
            respuesta.put("mensaje", "Error interno al crear situación");
        }
        PrintWriter out = response.getWriter();
        out.print(respuesta.toString());
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String parametros = Utils.readParams(request);
        Situacion situacion = (Situacion) Utils.fromJson(parametros, Situacion.class);
        JSONObject respuesta = new JSONObject();
        Situacion situacionAActualizar= (Situacion)dao.consultar(String.valueOf(situacion.getId()));
        if(situacionAActualizar!=null){
            situacionAActualizar.setTexto(situacion.getTexto());
            if(dao.actualizar(situacionAActualizar)){
                respuesta.put("tipo","ok");
                respuesta.put("mensaje", "Situación actualizada");
            }else{
                respuesta.put("tipo","error");
                respuesta.put("mensaje", "Error interno al crear situación");
            }
        }else{
            respuesta.put("tipo","error");
            respuesta.put("mensaje", "No existe una situación con ese id");
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
