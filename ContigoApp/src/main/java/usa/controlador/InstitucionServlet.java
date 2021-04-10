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
import usa.modelo.dao.InstitucionDao;
import usa.modelo.dto.Institucion;
import usa.utils.Utils;

/**
 *
 * @author santi
 */
@WebServlet(name = "InstitucionServlet", urlPatterns = {"/Institucion"})
public class InstitucionServlet extends HttpServlet {

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
            out.println("<title>Servlet InstitucionServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InstitucionServlet at " + request.getContextPath() + "</h1>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        InstitucionDao dao = new InstitucionDao();
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
        Gson gson = new Gson();
        String nom = Utils.readParams(request);
        Institucion ins = (Institucion) gson.fromJson(nom, Institucion.class); //forma de leer datos cast
        System.out.println(nom);
        InstitucionDao instuti = new InstitucionDao();

        if (instuti.consultar(ins.getNombre()) != null) {
            json.put("tipo", "error");
            json.put("mensaje", "Ya existe una institucion con este nombre");
        } else {
            if (instuti.crear(ins)) {
                json.put("tipo", "ok");
                json.put("mensaje", "Institucion registrada correctamente");
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
