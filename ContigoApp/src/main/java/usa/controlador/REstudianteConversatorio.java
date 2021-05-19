/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usa.controlador;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import static java.lang.System.out;
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
import usa.modelo.dto.Conversatorio;
import usa.modelo.dto.Estudiante;
import usa.modelo.dto.EstudianteConversatorio;
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
            out.println("<title>Servlet REstudianteConversatorio</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet REstudianteConversatorio at " + request.getContextPath() + "</h1>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
        IDaoConversatorios daoConver=(IDaoConversatorios)dao;
        EstudianteConversatorio esco = (EstudianteConversatorio) Utils.fromJson(parametros, EstudianteConversatorio.class);
        JSONObject respuesta = new JSONObject();
        
            if (daoConver.registrarEstuConver(esco)) {
                respuesta.put("tipo", "ok");
                respuesta.put("mensaje", "El estudiante fue registrado en el conversatorio");
            } else {
                respuesta.put("tipo", "error");
                respuesta.put("mensaje", "Error al registrar el estudiante");
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
