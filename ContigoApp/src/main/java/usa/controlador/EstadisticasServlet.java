package usa.controlador;

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
import usa.modelo.dao.IDao;
import usa.modelo.dao.IDaoCita;
import usa.modelo.dao.IDaoConversatorios;
import usa.modelo.dao.IEstadisticasBtnPanicoDao;
import usa.modelo.dao.IGradoDao;
import usa.modelo.dao.IHistoriasDao;
import usa.modelo.dto.Estudiante;
import usa.utils.Utils;

/**
 * Servlet de estadísticas.
 *
 * @author varios
 * @version 1.1
 * @since 2021-06-03
 */
@WebServlet(name = "EstadisticasServlet", urlPatterns = {"/Estadisticas"})
public class EstadisticasServlet extends HttpServlet {

    AbstractFactory factoryDao = Producer.getFabrica("DAO");

    /**
     * Método doGet
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        JSONObject json = new JSONObject();
        String parametro = request.getParameter("tipoConsulta");
        if (parametro.equals("PorGrado")) { //Consulta de estadísticas por grado
            IDao dao = (IDao) factoryDao.obtener("ConversatoriosDao");
            String grado = request.getParameter("grado");
            IDaoConversatorios daoConversatorio = (IDaoConversatorios) dao;
            LinkedList<JSONArray> arreglos = daoConversatorio.consultarPorGrado(grado);
            JSONObject conversatorios = new JSONObject();
            conversatorios.put("titulos", arreglos.get(0));
            conversatorios.put("inscritos", arreglos.get(1));
            dao = (IDao) factoryDao.obtener("GradoDao");
            IGradoDao gradoDao = (IGradoDao) dao;
            JSONObject boton = new JSONObject();
            JSONArray arregloEP = gradoDao.consultarBtnPorGrado(grado);
            boton.put("datos", arregloEP);
            json.put("conversatorios", conversatorios);
            json.put("boton", boton);
        } else if (parametro.equals("porEstudiante")) { //Consulta de estadísticas por estudiante
            String documento = request.getParameter("id");
            if (documento != null) {
                JSONObject datosEstudiante = estadisticaEstudiante(documento);
                json.put("tipo", "ok");
                json.put("datos", datosEstudiante);
            }
        } else if (parametro.equals("ClicksPorGrado")) {
            IDao dao = (IDao) factoryDao.obtener("EstadisticasBtnPanicoDao");
            EstadisticasBtnPanicoDao estdao = (EstadisticasBtnPanicoDao) dao;
            LinkedList<JSONArray> estadisticas = estdao.listarClicksPorGrado();
            json.put("grado", estadisticas.get(0));
            json.put("Clicks", estadisticas.get(1));
        }
        PrintWriter out = response.getWriter();
        out.print(json.toString());
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private JSONObject estadisticaEstudiante(String documento) {
        JSONObject datos = null;
        IDao dao = (IDao) factoryDao.obtener("EstudianteDao");
        Estudiante e = (Estudiante) dao.consultar(documento);
        IDaoCita citaDao = (IDaoCita) factoryDao.obtener("CitaDao");
        IHistoriasDao historiaDao = (IHistoriasDao) factoryDao.obtener("HistoriaDao");
        IEstadisticasBtnPanicoDao btnPanicoDao = (IEstadisticasBtnPanicoDao) factoryDao.obtener("EstadisticasBtnPanicoDao");
        if (e != null) {
            datos = new JSONObject();
            e.setDocumento(null);
            e.setCorreo(null);
            datos.put("estudiante", new JSONObject(Utils.toJson(e)));
            datos.put("citas", citaDao.ultimasCitasEstudiante(documento));
            datos.put("historias", new JSONArray(Utils.toJson(historiaDao.consultarHistoriasDeEstudiante(documento))));
            datos.put("presion",btnPanicoDao.clicksPorestudiante(documento));
        }
        return datos;
    }

}
