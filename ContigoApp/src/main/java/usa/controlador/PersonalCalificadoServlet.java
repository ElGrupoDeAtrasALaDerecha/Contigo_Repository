
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
import usa.modelo.dto.PersonalCalificado;
import usa.strategy.Contexto;
import usa.strategy.MailConfirmacionPersonal;
import usa.utils.Utils;

/**
 *
 * @author Santiago Pérez
 */
@WebServlet(name="PersonalCalificadoServlet", urlPatterns={"/PersonalCalificado"})
public class PersonalCalificadoServlet extends HttpServlet {
    AbstractFactory factoryDao=Producer.getFabrica("DAO");
    IDao dao = (IDao) factoryDao.obtener("PersonalCalificadoDao");
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        JSONObject respuesta = new JSONObject();
        respuesta.put("tipo", "ok");
        respuesta.put("personales",new JSONArray(Utils.toJson(dao.listarTodos())));
        PrintWriter out = response.getWriter();
        out.print(respuesta.toString());   
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String parametros = Utils.readParams(request);
        PersonalCalificado personal = (PersonalCalificado) Utils.fromJson(parametros, PersonalCalificado.class);
        JSONObject respuesta = new JSONObject();
        if (dao.consultar(personal.getDocumento())!=null){
            respuesta.put("tipo","error");
            respuesta.put("mensaje","Ya existe un usuario con el correo o número de documento ingresado");
        }else{
            if(dao.crear(personal)){
                respuesta.put("tipo","ok");
                respuesta.put("mensaje","Usuario registrado satisfactoriamente");
                //Aquí se envía la verificación
              Utils.enviarCorreoA("confirmacionPersonal", personal.getCorreo());
            }else{
                respuesta.put("tipo","error");
                respuesta.put("mensaje","Ya existe un usuario con el correo o número de documento ingresado");
            }
        }
        PrintWriter out = response.getWriter();
        out.print(respuesta.toString());
    }
//
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         
    }
    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String parametros = Utils.readParams(request);
        String documento=request.getParameter("document");
        PersonalCalificado personal = (PersonalCalificado) Utils.fromJson(parametros, PersonalCalificado.class);
        JSONObject respuesta = new JSONObject();
        if (dao.consultar(personal.getDocumento())==null){
            respuesta.put("tipo","error");
            respuesta.put("mensaje","No existe ningún usuario con ese documento");
        }else{
            if(dao.actualizar(personal)){
                respuesta.put("tipo","ok");
                respuesta.put("mensaje","Usuario actualizado satisfactoriamente");
                //Aquí se envía la verificación
            }else{
                respuesta.put("tipo","error");
                respuesta.put("mensaje","Ya existe un usuario con ese nombre documento");
            }
        }
    }

    
    
    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
