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
import usa.modelo.dao.IDao;
import usa.utils.Utils;

/**
 *
 * @author Valeria
 */
@WebServlet(name = "CitaServlet", urlPatterns = {"/Cita"})
public class CitaServlet extends HttpServlet {

    AbstractFactory factoryDao = Producer.getFabrica("DAO");
    IDao dao = (IDao) factoryDao.obtener("CitaDao");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        System.out.println(request);
        JSONObject respuesta = new JSONObject();
        JSONArray arreglo = new JSONArray(Utils.toJson(dao.listarTodos()));
        respuesta.put("tipo", "ok");
        respuesta.put("citas", arreglo);
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
