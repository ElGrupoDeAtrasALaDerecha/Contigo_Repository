/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package usa.modelo.controlador;

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
import usa.modelo.dao.PersonalCalificadoDao;
import usa.modelo.dto.PersonalCalificado;
import usa.utils.Utils;

/**
 *
 * @author Santiago Pérez
 */
@WebServlet(name="PersonalCalificadoServlet", urlPatterns={"/PersonalCalificado"})
public class PersonalCalificadoServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PersonalCalificadoDao dao = new PersonalCalificadoDao();
        LinkedList <PersonalCalificado>personales=dao.listarTodos();
        Gson gson = new Gson();
        JSONObject respuesta = new JSONObject();
        JSONArray arreglo = new JSONArray();
        respuesta.put("tipo", "ok");
        for (PersonalCalificado personal:personales) {
            arreglo.put(new JSONObject(gson.toJson(personal,PersonalCalificado.class)));
        }
        respuesta.put("personales",arreglo);
        PrintWriter out = response.getWriter();
        out.print(respuesta.toString());
        
        
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String parametros = Utils.readParams(request);
        Gson gson = new Gson();
        PersonalCalificado personal = (PersonalCalificado) gson.fromJson(parametros, PersonalCalificado.class);
        PersonalCalificadoDao dao = new PersonalCalificadoDao();
        JSONObject respuesta = new JSONObject();
        if (dao.consultar(personal.getDocumento())!=null){
            respuesta.put("tipo","error");
            respuesta.put("mensaje","Ya existe un usuario con ese nombre documento");
        }else{
            if(dao.crear(personal)){
                respuesta.put("tipo","ok");
                respuesta.put("mensaje","Usuario registrado satisfactoriamente");
                //Aquí se envía la verificación
            }else{
                respuesta.put("tipo","error");
                respuesta.put("mensaje","Ya existe un usuario con ese nombre documento");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String parametros = Utils.readParams(request);
        String documento=request.getParameter("document");
        Gson gson = new Gson();
        PersonalCalificado personal = (PersonalCalificado) gson.fromJson(parametros, PersonalCalificado.class);
        PersonalCalificadoDao dao = new PersonalCalificadoDao();
        JSONObject respuesta = new JSONObject();
        if (dao.consultar(personal.getDocumento())==null){
            respuesta.put("tipo","error");
            respuesta.put("mensaje","No existe ningún usuario con ese documento");
        }else{
            if(dao.actualizar(personal)){
                respuesta.put("tipo","ok");
                respuesta.put("mensaje","Usuario actualizado satisfactoriamente");
                //Aquí se envía la verificación
            }else{
                respuesta.put("tipo","error");
                respuesta.put("mensaje","Ya existe un usuario con ese nombre documento");
            }
        }
    }

    
    
    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
