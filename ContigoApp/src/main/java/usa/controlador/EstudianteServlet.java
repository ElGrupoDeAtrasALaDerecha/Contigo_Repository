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
import usa.factory.FactoryDao;
import usa.modelo.dao.EstudianteDao;
import usa.modelo.dao.IDao;
import usa.modelo.dto.Estudiante;
import usa.utils.Utils;

/**
 *
 * @author Santiago Pérez
 */
@WebServlet(name = "Estudiante", urlPatterns = {"/Estudiante"})
public class EstudianteServlet extends HttpServlet {

// funciona     
    
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json;charset=UTF-8");
        IDao dao = FactoryDao.obtenerDao("EstudianteDao");
        JSONObject respuesta = new JSONObject();
<<<<<<< HEAD
        Estudiante estudiante = dao.consultarPorTokenGrado(request.getParameter("id"));

=======
        EstudianteDao daoestu = (EstudianteDao)dao;
        Estudiante estudiante = daoestu.consultarPorTokenGrado(request.getParameter("id"));
        
>>>>>>> V2.0
        if (estudiante != null) {
            Gson gson = new Gson();
            JSONObject estudianteJson = new JSONObject(gson.toJson(estudiante, Estudiante.class));

            respuesta.put("tipo", "ok");
            respuesta.put("estudiante", estudianteJson);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
        Gson gson = new Gson();
        String mensaje = Utils.readParams(request);
        System.out.println(mensaje);
        Estudiante estudiante = (Estudiante) gson.fromJson(mensaje, Estudiante.class);

<<<<<<< HEAD
        EstudianteDao dao = new EstudianteDao();
        System.out.println(estudiante.getDocumento());
        
        if (dao.consultar(estudiante.getDocumento())!= null) {
=======
        IDao dao = FactoryDao.obtenerDao("EstudianteDao");
        
        if (dao.crear(estudiante)) {
            json.put("tipo", "ok");
            json.put("mensaje", "Estudiante creado");
        } else {
>>>>>>> V2.0
            json.put("tipo", "error");
            json.put("mensaje", "Ya existe un estudiante con ese documento");
        } else {
            if (dao.crear(estudiante)) {
                json.put("tipo", "ok");
                json.put("mensaje", "Estudiante creado");
            } else {
                json.put("tipo", "error");
                json.put("mensaje", "Error al crear estudiante");
            }
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

    private String toString(String parametros) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
