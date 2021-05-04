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
import usa.modelo.dto.Final;
import usa.modelo.dto.Historia;
import usa.modelo.dto.Situacion;
import usa.utils.Utils;

/**
 *
 * Servlet de situaciones de decisión
 *
 * @author Miguel Rippe, Santiago Cáceres, Laura Blanco y Santiago Pérez
 */
@WebServlet(name = "SituacionServlet", urlPatterns = {"/Situacion"})
public class SituacionServlet extends HttpServlet {

    AbstractFactory factoryDao = Producer.getFabrica("DAO");
    IDao dao = (IDao) factoryDao.obtener("SituacionDao");
    IDao daoHistorias = (IDao) factoryDao.obtener("HistoriaDao");
    IDao daoFinal = (IDao) factoryDao.obtener("FinalDao");

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
        ISituacionDao daoSituacion = (ISituacionDao) dao;
        String id = request.getParameter("id");
        JSONObject respuesta = new JSONObject();
        Historia historia = (Historia) daoHistorias.consultar(id);
        if (historia != null) {
            JSONObject arreglo = new JSONObject(Utils.toJson(daoSituacion.consultarPorHistoria(Integer.parseInt(id))));
            respuesta.put("tipo", "ok");
            respuesta.put("situaciones", arreglo);
        } else {
            respuesta.put("tipo", "error");
            respuesta.put("mensaje", "No existe esa historia aa aa");
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
        String tipo = request.getHeader("tipo");
       
        JSONObject respuesta = new JSONObject();
        if ("situacion".equals(tipo)) {
             Situacion situacion = (Situacion) Utils.fromJson(parametros, Situacion.class);
            if (dao.crear(situacion)) {
                respuesta.put("tipo", "ok");
                respuesta.put("mensaje", "Situación creada con exito");
            } else {
                respuesta.put("tipo", "error");
                respuesta.put("mensaje", "Error al crear situación");
            }
        } else if("final".equals(tipo)){
             Final fin = (Final) Utils.fromJson(parametros, Final.class);
             if (daoFinal.crear(fin)) {
                respuesta.put("tipo", "ok");
                respuesta.put("mensaje", "Final creado con exito");
            } else {
                respuesta.put("tipo", "error");
                respuesta.put("mensaje", "Error al crear el final");
            }
        }
        PrintWriter out = response.getWriter();
        out.print(respuesta.toString());
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String parametros = Utils.readParams(request);
        Situacion situacion = (Situacion) Utils.fromJson(parametros, Situacion.class);
        String tipo = request.getHeader("tipo");
        JSONObject respuesta = new JSONObject();
        if("situacion".equals(tipo)){
        Situacion situacionAActualizar = (Situacion) dao.consultar(String.valueOf(situacion.getId()));
        if (situacionAActualizar != null) {
            situacionAActualizar.setTexto(situacion.getTexto());
            situacionAActualizar.setTitulo(situacion.getTitulo());
            situacionAActualizar.setIdHistoria(situacion.getIdHistoria());
            if (dao.actualizar(situacionAActualizar)) {
                respuesta.put("tipo", "ok");
                respuesta.put("mensaje", "Situación actualizada");
            } else {
                respuesta.put("tipo", "error");
                respuesta.put("mensaje", "Error al actualizar la situación");
            }
        } else {
            respuesta.put("tipo", "error");
            respuesta.put("mensaje", "No existe una situación con ese id");
        }
        }else if( "final".equals(tipo)){
            Final finalActualizar = (Final) daoFinal.consultar(String.valueOf(situacion.getId()));
        if (finalActualizar != null) {
            finalActualizar.setTexto(situacion.getTexto());
            finalActualizar.setTitulo(situacion.getTitulo());
            finalActualizar.setIdHistoria(situacion.getIdHistoria());
            if (daoFinal.actualizar(finalActualizar)) {
                respuesta.put("tipo", "ok");
                respuesta.put("mensaje", "Final actualizado");
            } else {
                respuesta.put("tipo", "error");
                respuesta.put("mensaje", "Error al actualizar el final");
            }
        } else {
            respuesta.put("tipo", "error");
            respuesta.put("mensaje", "No existe un final con el id");
        }  
        }

        PrintWriter out = response.getWriter();
        out.print(respuesta.toString());

    }
    
     protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String parametros = Utils.readParams(request);
        System.out.println(parametros);
        String tipo = request.getHeader("tipo");
        String id = request.getParameter("id");
        JSONObject respuesta = new JSONObject();
        if("situacion".equals(tipo)){
        Situacion situacionEliminar = (Situacion) dao.consultar(String.valueOf(id));
        if (situacionEliminar != null) {
            if (dao.eliminar(String.valueOf(id))) {
                respuesta.put("tipo", "ok");
                respuesta.put("mensaje", "Situación eliminada");
            } else {
                respuesta.put("tipo", "error");
                respuesta.put("mensaje", "Error al eliminar la situacion, debe eliminar sus situaciones primero");
            }
        } else {
            respuesta.put("tipo", "error");
            respuesta.put("mensaje", "No existe una situación con ese id");
        }
        }else if( "final".equals(tipo)){
            Final finalEliminar = (Final) daoFinal.consultar(id);
        if (finalEliminar != null) {
            if (daoFinal.eliminar(String.valueOf(String.valueOf(id)))) {
                respuesta.put("tipo", "ok");
                respuesta.put("mensaje", "Final Eliminado");
            } else {
                respuesta.put("tipo", "error");
                respuesta.put("mensaje", "Error al eliminar el final");
            }
        } else {
            respuesta.put("tipo", "error");
            respuesta.put("mensaje", "No existe un final con el id");
        }  
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
