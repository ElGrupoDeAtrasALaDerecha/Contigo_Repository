/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.json.JSONException;
import org.json.JSONObject;
import usa.factory.AbstractFactory;
import usa.factory.Producer;
import usa.modelo.dao.EstudianteDao;
import usa.modelo.dao.IDao;
import usa.modelo.dto.Estudiante;
import usa.utils.Utils;
/**/
/**
 *
 * @author Maria Camila Fernandez ,Andres López
 */
@WebServlet(name = "EstudiantePorGradoServlet", urlPatterns = {"/EstudiantePorGradoServlet"})
public class EstudiantePorGradoServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    AbstractFactory factoryDao = Producer.getFabrica("DAO");
    IDao dao = (IDao) factoryDao.obtener("EstudianteDao");
    EstudianteDao daoestu = (EstudianteDao) dao;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        JSONObject json = new JSONObject(Utils.readParams(request));
        PrintWriter out = response.getWriter();
        JSONObject respuesta = new JSONObject();
        JSONArray arreglo = new JSONArray();
        Gson gson = new Gson();

        LinkedList<Estudiante> estudiantes = daoestu.listarGradosEstudiante(json.getString("grado"));
        if (estudiantes != null) {
            respuesta.put("tipo", "ok");
            for (Estudiante i : estudiantes) {
                System.out.println("---> " + i.getPrimerApellido());
                arreglo.put(new JSONObject(gson.toJson(i, Estudiante.class)));
            }
            respuesta.put("estudiantes", arreglo);
        } /**
         * String grado = request.getParameter("grado"); if(grado != null){
         * respuesta.put("estudiantes", new
         * JSONObject(Utils.toJson(daoestu.listarGradosEstudiante(grado)))); }*
         */
        else {
            respuesta.put("tipo", "error");
            respuesta.put("mensaje", "No se ha posido consultar el estudiant");
        }

        //respuesta.put("tipo", "ok");
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
