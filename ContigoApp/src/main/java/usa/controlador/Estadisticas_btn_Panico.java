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
import usa.utils.Utils;

/**
 *
 * @author andre
 */
@WebServlet(name = "Estadisticas_btn_Panico", urlPatterns = {"/Estadisticas_btn_Panico"})
public class Estadisticas_btn_Panico extends HttpServlet {

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
    }

  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
        Gson gson = new Gson();
        //Se obtienen el grado del front
        String clicks = Utils.readParams(request);
        System.out.println("°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°");
        System.out.println("doPost. Value del grado seleccionado: " + grado_slct);
        //Se convierte de json a objeto Grado
        Grado grado = (Grado) gson.fromJson(grado_slct, Grado.class);
        String codigo = GeneradorCodigos.getCodigo(GeneradorCodigos.MAYUSCULAS+GeneradorCodigos.NUMEROS,6);
        System.out.println(codigo);
        grado.setCodigo(codigo);
        GradoDao dao = new GradoDao();
        System.out.println("Este es el grado "+grado.getCodigo());
        if(dao.crear(grado)){
            json.put("tipo", "ok");
            json.put("mensaje","Grado creado con el código " + codigo);
            json.put("codigo", codigo);
        }else{
            //
            json.put("tipo", "error");
            json.put("mensaje","Error al crear grado " + grado.getClasificacion_id());
            String existente = dao.consultar(String.valueOf(grado.getClasificacion_id())).getCodigo();
            json.put("codigo", existente);
        }
        System.out.println(json.toString());
        out.print(json.toString());
        
    }

}
