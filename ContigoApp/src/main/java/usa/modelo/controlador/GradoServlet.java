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
import usa.modelo.dao.ClasificacionDao;
import usa.modelo.dao.GradoDao;
import usa.modelo.dto.Grado;
import usa.modelo.dto.GradoClasf;
import usa.utils.GeneradorCodigos;
import usa.utils.Utils;

/**
 *
 * @author 
 */
@WebServlet(name = "GradoServlet", urlPatterns = {"/Grado"})
public class GradoServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    public ClasificacionDao clasificacion = new ClasificacionDao();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        GradoDao dao = new GradoDao();
        LinkedList <Grado> grados = dao.listarTodos();
        LinkedList<GradoClasf> gradosClasf = dao.listarGradosClasf();
        Gson gson = new Gson();
        JSONObject respuesta = new JSONObject();
        JSONArray arreglo = new JSONArray();
        respuesta.put("tipo", "ok");
        for (Grado grado:grados) {
            arreglo.put(new JSONObject(gson.toJson(grado,Grado.class)));
        }
        respuesta.put("Grados",arreglo);
        respuesta.put("GradosClasificados", gradosClasf);
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
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
        Gson gson = new Gson();
        //Se obtienen el grado del front
        String grado_slct = Utils.readParams(request);
        System.out.println("°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°");
        System.out.println("doPost. Value del grado seleccionado: " + grado_slct);
        //Se convierte de json a objeto Grado
        Grado grado = (Grado) gson.fromJson(grado_slct, Grado.class);
        grado.setInstitucion_id(4);
        String codigo = GeneradorCodigos.getCodigo(GeneradorCodigos.MAYUSCULAS+GeneradorCodigos.NUMEROS,6);
        grado.setCodigo(codigo);
        GradoDao dao = new GradoDao();
        if(dao.crear(grado)){
            json.put("tipo", "ok");
            json.put("mensaje","Grado creado con el código " + codigo);
            json.put("codigo", codigo);
        }else{
            json.put("tipo", "error");
            json.put("mensaje","Error al crear grado " + grado.getClasificacion_id());
            String existente = dao.consultar(String.valueOf(grado.getClasificacion_id())).getCodigo();
            json.put("codigo", existente);
        }
        System.out.println(json.toString());
        out.print(json.toString());
    } 
//wasaaaaaaa!
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short Description";
    }// </editor-fold>

}
