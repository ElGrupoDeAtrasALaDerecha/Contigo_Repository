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
import usa.modelo.dao.EstadisticasBtnPanicoDao;
import usa.modelo.dao.EstudianteDao;
import usa.modelo.dao.IDao;
import usa.modelo.dto.EstadisticasBtnPanico;
import usa.utils.Utils;
/**/
/**
 *
 * @author andre
 */
@WebServlet(name = "Estadisticas_btn_Panico", urlPatterns = {"/EstadisticasBtnPanico"})
public class EstadisticasBtnPanicoServlet extends HttpServlet {

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

    AbstractFactory factoryDao=Producer.getFabrica("DAO");
    IDao dao = (IDao)factoryDao.obtener("EstadisticasBtnPanicoDao");
    EstadisticasBtnPanicoDao estdao = (EstadisticasBtnPanicoDao) dao;
    
    @Override
    //Consultar
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        response.setContentType("application/json;charset=UTF-8");
        
        //Instacia de EstadisticasBtnPanicoDao
        LinkedList<EstadisticasBtnPanico> estadisticas = estdao.listarTodos();
        
        //Instacias para el envio de los datosn en formato JSON
        Gson gson = new Gson();
        JSONObject respuesta = new JSONObject();
        JSONArray arreglo = new JSONArray();
        
        //Cada objeto de la clase EstadisticasBtnPanicoDao se agrega al arreglo JSON
        respuesta.put("tipo", "ok");
        for (EstadisticasBtnPanico estadistica:estadisticas) {
            arreglo.put(new JSONObject(gson.toJson(estadistica,EstadisticasBtnPanico.class)));
        }
        
        //Envio de los datos al clente en formato JSON 
        respuesta.put("Estadisticas",arreglo);
        PrintWriter out = response.getWriter();
        out.print(respuesta.toString());
    }

   
    @Override
    //Crear
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject(Utils.readParams(request));
                
        //Impersion de los datos 
        System.out.println("°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°");
        System.out.println(json.getString("estudiante"));
        IDao estudiante = (IDao)factoryDao.obtener("EstudianteDao");
        EstadisticasBtnPanico estadistica = new EstadisticasBtnPanico();
        EstudianteDao std = (EstudianteDao) estudiante;
        estadistica.setEstudiante(std.consultarPorToken(json.getString("estudiante")).getDocumento());
        if (estdao.crear(estadistica)) {
            json.put("tipo", "ok");
            json.put("mensaje", "Estadística creada");
        } else {
            json.put("tipo", "error");
            json.put("mensaje", "Error al crear estadística");
        }
        out.print(json.toString());
        
    }
   
}
