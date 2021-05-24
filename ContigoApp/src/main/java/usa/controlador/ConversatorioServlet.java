/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import usa.modelo.dao.ConversatoriosDao;
import usa.modelo.dao.IDao;
import usa.modelo.dao.IDaoConversatorios;
import usa.modelo.dto.Clasificacion;
import usa.modelo.dto.Conversatorio;
import usa.utils.Utils;

/**
 * Clase de Conversatorios
 *
 * @author Miguel Angel Rippe y Natalia Montenegro
 * @since 2021-03-13
 */
@WebServlet(name = "ConversatorioServlet", urlPatterns = {"/Conversatorio"})
public class ConversatorioServlet extends HttpServlet {
    
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        System.out.println(request);
        JSONObject respuesta = new JSONObject();
        JSONArray arreglo = new JSONArray(Utils.toJson(dao.listarTodos()));
        String id = request.getParameter("id");
        ConversatoriosDao daoConver = (ConversatoriosDao) factoryDao.obtener("ConversatoriosDao");
         Gson gson = new Gson();
        respuesta.put("tipo", "ok");
        respuesta.put("conversatorios", arreglo);
  
        if(id != null){
            LinkedList<Clasificacion> clasificaciones = daoConver.consultarClasificacionConver(Integer.parseInt(id));
             JSONArray arreglo2 = new JSONArray();
             for (Clasificacion i : clasificaciones) {
                arreglo2.put(new JSONObject(gson.toJson(i, Clasificacion.class)));
            }
            respuesta.put("clasificacion", arreglo2);
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
        String parametros = Utils.readParams(request);
        System.out.println(parametros);
        Conversatorio conver = (Conversatorio) Utils.fromJson(parametros, Conversatorio.class);
        IDaoConversatorios daoConver=(IDaoConversatorios)dao;
        JSONObject respuesta = new JSONObject();
        int resultado = daoConver.crearConver(conver);
       
        if (resultado != 0) {
            respuesta.put("tipo", "ok");
            respuesta.put("mensaje", "El conversatorio fue agendado correctamente");
            respuesta.put("conversatorio", resultado);
            String arregloClasificaciones[]=conver.getClasificacion();
            for (int i = 0; i < arregloClasificaciones.length; i++) {
                daoConver.crearClasi(arregloClasificaciones[i],resultado);
            }
    
        } else {
            respuesta.put("tipo", "error");
            respuesta.put("mensaje", "No se ha podido crear el conversatorio");
        }
        

        PrintWriter out = response.getWriter();
        out.print(respuesta.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String parametros = Utils.readParams(req);
        Conversatorio conver = (Conversatorio) Utils.fromJson(parametros, Conversatorio.class);
        IDaoConversatorios daoConver=(IDaoConversatorios)dao;
        JSONObject respuesta = new JSONObject();
        Conversatorio conversatorioAActualizar = (Conversatorio) daoConver.consultar(String.valueOf(conver.getId()));
        if (conversatorioAActualizar != null) {
            conversatorioAActualizar.setOrador(conver.getOrador());
            conversatorioAActualizar.setTitulo(conver.getTitulo());
            conversatorioAActualizar.setDescripcion(conver.getCronograma());
            conversatorioAActualizar.setImagen(conver.getImagen());
            conversatorioAActualizar.setDescripcion(conver.getDescripcion());
            conversatorioAActualizar.setLugar(conver.getLugar());
            conversatorioAActualizar.setInfografia(conver.getInfografia());
   
            if (daoConver.actualizar(conversatorioAActualizar)) {
                String arregloClasificaciones[]=conver.getClasificacion();
                for (int i = 0; i < arregloClasificaciones.length; i++) {
                daoConver.crearClasi(arregloClasificaciones[i],conver.getId());
                }
                respuesta.put("tipo", "ok");
                respuesta.put("mensaje", "Conversatorio Actualizado");
             
            } else {
                respuesta.put("tipo", "error");
                respuesta.put("mensaje", "Error al actualizar el conversatorio");
            }
        } else {
            respuesta.put("tipo", "error");
            respuesta.put("mensaje", "No existe el conversatorio");
        }
       

        PrintWriter out = resp.getWriter();
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
