/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usa.controlador;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import usa.modelo.dao.EstudianteDao;
import usa.modelo.dto.Estudiante;
import usa.utils.Utils;

/**
 *
 * @author Santiago Pérez
 */
@WebServlet(name = "Estudiante", urlPatterns = {"/Estudiante"})
public class EstudianteServlet extends HttpServlet {

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
            out.println("<title>Servlet Estudiante</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Estudiante at " + request.getContextPath() + "</h1>");
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /*
            Estudiante estudiante = new Estudiante();
            estudiante.setPrimerNombre("Pablo");
            estudiante.setPrimerApellido("Escobar");
            Gson gson = new Gson();
            String mensaje=gson.toJson(estudiante,Estudiante.class);
            System.out.println(mensaje);
            out.print(mensaje);*/
            
         
            
        }
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
        Gson gson = new Gson();
        String mensaje = Utils.readParams(request);
        System.out.println(mensaje);
        Estudiante estudiante= (Estudiante)gson.fromJson(mensaje, Estudiante.class);
        
        
        EstudianteDao dao = new EstudianteDao();
        if(dao.crear(estudiante)){
            json.put("tipo","ok");
            json.put("mensaje","Estudiante creado");
        }else{
            json.put("tipo","error");
            json.put("mensaje","Error al crear estudiante");
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
        return "Short description";
    }// </editor-fold>

}
