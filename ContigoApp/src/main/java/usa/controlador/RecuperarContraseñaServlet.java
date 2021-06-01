package usa.controlador;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import usa.adapter.CorreoClave;
import usa.adapter.CorreoClaveAdapter;
import usa.adapter.CorreoProxy;
import usa.factory.AbstractFactory;
import usa.factory.Producer;
import usa.modelo.dao.IDao;
import usa.modelo.dao.IInstitucionDao;
import usa.modelo.dao.IPersonaDao;
import usa.modelo.dao.IRecuperacionDao;
import usa.modelo.dto.Institucion;
import usa.modelo.dto.Persona;
import usa.utils.Utils;

/**
 * Servlet para recuperación de contraseña para instituciones
 *
 * @author Santiago Pérez
 * @version 1.0
 * @since 2021-05-31
 */
@WebServlet(name = "RecuperarContraseña", urlPatterns = {"/Password"})
public class RecuperarContraseñaServlet extends HttpServlet {

    AbstractFactory factoryDao = Producer.getFabrica("DAO");
    IDao institucionDao = (IDao) factoryDao.obtener("InstitucionDao");
    IPersonaDao personaDao = (IPersonaDao) factoryDao.obtener("PersonaDao");
    IRecuperacionDao recuperacionDao = (IRecuperacionDao) factoryDao.obtener("RecuperacionDao");

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
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String codigo = request.getParameter("codigo");
        JSONObject json = new JSONObject();
        if (recuperacionDao.validarCodigoRecuperacion(codigo)) {
            recuperacionDao.eliminarCodigoRecuperacion(codigo);
            json.put("tipo", "ok");
            json.put("allowed", true);
        } else {
            json.put("tipo", "error");
            json.put("allowed", false);
            response.sendError(403);
        }
        out.print(json.toString());
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
        JSONObject json = new JSONObject(Utils.readParams(request));
        String correo = json.getString("correo");

        IInstitucionDao daoInstitucion = (IInstitucionDao) institucionDao;
        Institucion institucion = daoInstitucion.consultarPorCorreo(correo);
        CorreoProxy proxy=null;
        if (institucion != null) {
            if (recuperacionDao.verificarCodigoRecuperacion(institucion)) {
                recuperacionDao.eliminarCodigoRecuperacion(institucion);
            }
            String codigo = Utils.crearCodigoRecuperacion();
            recuperacionDao.crearCodigoRecuperacion(institucion, codigo);
            proxy = new CorreoProxy(new CorreoClaveAdapter(new CorreoClave(codigo, institucion)));
            json.put("tipo", "ok");
            json.put("mensaje", "Se ha enviado un correo con los datos para recuperar su contraseña");
        } else {
            Persona p = personaDao.consultarPorCorreo(correo);
            if (p != null) {
                if (recuperacionDao.verificarCodigoRecuperacion(p)) {
                    recuperacionDao.eliminarCodigoRecuperacion(p);
                }
                String codigo = Utils.crearCodigoRecuperacion();
                recuperacionDao.crearCodigoRecuperacion(p, codigo);
                proxy = new CorreoProxy(new CorreoClaveAdapter(new CorreoClave(codigo, p)));
                json.put("tipo", "ok");
                json.put("mensaje", "Se ha enviado un correo con los datos para recuperar su contraseña");
            }else{
                json.put("tipo", "error");
                json.put("mensaje", "Ese correo no está registrado");
                response.sendError(404);
            }

        }
        out.print(json.toString());
        if(proxy!=null){
            proxy.enviarCorreo(correo);
        }
                
        
       
    }

    /**
     * Handles the HTTP <code>PUT</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String codigo = request.getParameter("codigo");
        JSONObject obj = new JSONObject(Utils.readParams(request));
        String contraseña = obj.getString("contraseña");
        if (recuperacionDao.cambiarContraseña(codigo, contraseña)) {
            obj.remove("contraseña");
            obj.put("tipo", "ok");
            obj.put("mensaje", "¡Contraseña modificada satisfactoriamente!");
        } else {
            obj.remove("contraseña");
            obj.put("tipo", "error");
            obj.put("mensaje", "Error al cambiar contraseña");
        }

        out.print(obj.toString());
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet para recuperar contraseña";
    }

}
