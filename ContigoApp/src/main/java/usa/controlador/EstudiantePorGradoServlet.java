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
import org.json.JSONObject;
import usa.factory.AbstractFactory;
import usa.factory.Producer;
import usa.modelo.dao.EstudianteDao;
import usa.modelo.dao.IDao;
import usa.modelo.dto.Estudiante;

/**
 *
 * @author marif
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EstudiantePorGradoServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EstudiantePorGradoServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    AbstractFactory factoryDao=Producer.getFabrica("DAO");
    IDao dao = (IDao)factoryDao.obtener("EstudianteDao");
    EstudianteDao daoestu = (EstudianteDao)dao;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         PrintWriter out = response.getWriter();
        response.setContentType("application/json;charset=UTF-8");
        JSONObject respuesta = new JSONObject();
        JSONArray arreglo = new JSONArray();
        Gson gson =new Gson();
        LinkedList<Estudiante>  estudiantes = daoestu.listarGradosEstudiante(request.getParameter("grado"));
        if (estudiantes != null) {        
            respuesta.put("tipo", "ok");
            for (Estudiante i: estudiantes ) {
                arreglo.put(new JSONObject(gson.toJson(i,Estudiante.class)));
            }
            respuesta.put("estudiantes", arreglo);
        } else {
            respuesta.put("tipo", "error");
            respuesta.put("mensaje", "No se ha posido consultar el estudiant");
        }
       
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
        processRequest(request, response);
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
