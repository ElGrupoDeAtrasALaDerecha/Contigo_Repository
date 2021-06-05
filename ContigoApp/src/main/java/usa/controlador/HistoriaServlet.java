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
import usa.modelo.dao.IHistoriasDao;
import usa.modelo.dao.IPersonalCalificadoDao;
import usa.modelo.dto.Historia;
import usa.modelo.dto.PersonalCalificado;
import usa.modelo.dto.Situacion;
import usa.utils.Utils;

/**
 * Servlet de historias. En este servlet se crean, consultan y modifican historias
 * @author Miguel Rippe, Santiago Cáceres, Laura Blanco y Santiago Pérez
 * @version 1.0
 */
@WebServlet(name = "HistoriaServlet", urlPatterns = {"/Historia"})
public class HistoriaServlet extends HttpServlet {

    AbstractFactory factoryDao = Producer.getFabrica("DAO");
    IDao dao = (IDao) factoryDao.obtener("HistoriaDao");
    IDao dao2 = (IDao) factoryDao.obtener("SituacionDao");
    IDao personalCalificadoDao = (IDao) factoryDao.obtener("PersonalCalificadoDao");

    /**
     * Handles the HTTP <code>GET</code> method
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
        JSONObject respuesta = new JSONObject();
        String id = request.getParameter("id");
        if (id != null) {
            Historia historia = (Historia) dao.consultar(id);
            respuesta.put("historia", new JSONObject(Utils.toJson(historia)));
        } else {
            respuesta.put("tipo", "ok");
            respuesta.put("historias", new JSONArray(Utils.toJson(dao.listarTodos())));
        }

        PrintWriter out = response.getWriter();
        out.print(respuesta.toString());

    }

    /**
     * Handles the HTTP <code>POST</code> method. kbdsfkhbfshdfhd funciona
     * maldición quiero avanzar
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
        String mensaje = Utils.readParams(request);
        String token = request.getHeader("token");
        if (token == null) {
            json.put("tipo", "error");
            json.put("mensaje", "No autorizado");
            out.print(json.toString());
            return;
        }
        IPersonalCalificadoDao personalDao = (IPersonalCalificadoDao) personalCalificadoDao;
        PersonalCalificado personal = personalDao.consultarPorToken(token);
        System.out.println(mensaje);
        Historia historia = (Historia) Utils.fromJson(mensaje, Historia.class);
        historia.setDocumentoCreador(personal.getDocumento());
        IHistoriasDao daoHis = (IHistoriasDao) dao;
        if (dao.crear(historia)) {
            json.put("tipo", "ok");
            json.put("mensaje", "Historia creada");
            json.put("idHistoria", historia.getId());
            String arregloClasificaciones[] = historia.getClasificacion();
            for (int i = 0; i < arregloClasificaciones.length; i++) {
                daoHis.crearClasi(arregloClasificaciones[i], historia.getId());
            }
            Situacion situacion = new Situacion();
            situacion.setIdHistoria(historia.getId());
            situacion.setTitulo("");
            situacion.setTexto("");
            dao2.crear(situacion);
        } else {
            json.put("tipo", "error");
            json.put("mensaje", "Error al crear la historia");
        }
        out.print(json.toString());
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        JSONObject json = new JSONObject();
        String mensaje = Utils.readParams(request);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        JSONObject respuesta = new JSONObject();
        IHistoriasDao daoHis = (IHistoriasDao) dao;
        String id = request.getHeader("id");
        String clasificaciones  = request.getHeader("clasificacion");
        String [] vect = clasificaciones.split(",");
        if (id != null) {
            Historia historia = (Historia) dao.consultar(id);
            if (historia != null) {
                if (dao.eliminar(String.valueOf(String.valueOf(id)))) {
                    String arregloClasificaciones[] = vect;
                    for (int i = 0; i < arregloClasificaciones.length; i++) {
                        daoHis.crearClasi(arregloClasificaciones[i], historia.getId());
                    }
                    respuesta.put("tipo", "ok");
                    respuesta.put("mensaje", "Grados Actualizados");
                } else {
                    respuesta.put("tipo", "error");
                    respuesta.put("mensaje", "Error al eliminar el final");
                }
            }
        } else {
            respuesta.put("tipo", "ok");
            respuesta.put("historias", new JSONArray(Utils.toJson(dao.listarTodos())));
        }

        PrintWriter out = response.getWriter();
        out.print(respuesta.toString());

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description funcionaaaaaa
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
