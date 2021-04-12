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
import usa.modelo.dao.InstitucionDao;
import usa.modelo.dto.Institucion;
import usa.utils.Utils;

/**
 *
 * @author andre
 */
@WebServlet(name = "LoginInstitucionServlet", urlPatterns = {"/LoginInstitucion"})
public class LoginInstitucionServlet extends HttpServlet {
/**/

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
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.a
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    /**/
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Se definen los objetos a trabajar:
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
        Gson gson = new Gson();
        InstitucionDao dao = new InstitucionDao();
        //Se obtienen las credenciales de la institucion del front:
        String usr_inst = Utils.readParams(request);
        System.out.println("°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°");
        System.out.println("doPost. Credenciales a validar: " + usr_inst);
        //Se convierte de json a objeto Institucion
        Institucion institucion = (Institucion) gson.fromJson(usr_inst, Institucion.class);
        Institucion instBD = dao.loginInstitucion(institucion.getCorreo(), institucion.getContraseña());
        if(instBD != null){
            json.put("tipo", "ok");
            json.put("mensaje","Ingreso satisfactorio");
            json.put("ID",  + instBD.getId());
        }else{
            json.put("tipo", "error");
            json.put("mensaje","Error al ingresar");
        }
        System.out.println(json.toString());
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
