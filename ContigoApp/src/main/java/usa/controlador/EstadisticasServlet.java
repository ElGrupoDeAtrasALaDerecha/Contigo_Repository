package usa.controlador;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
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
import usa.modelo.dao.IDaoConversatorios;
import usa.modelo.dao.IGradoDao;
import usa.modelo.dto.EstadisticasBtnPanico;
import usa.utils.Utils;
/**/
/**
 *
 * @author
 */
@WebServlet(name = "EstadisticasServlet", urlPatterns = {"/Estadisticas"})
public class EstadisticasServlet extends HttpServlet {

    AbstractFactory factoryDao = Producer.getFabrica("DAO");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("application/json;charset=UTF-8");
            JSONObject json = new JSONObject();
            String parametro = request.getParameter("tipoConsulta");
            if(parametro.equals("PorGrado")){
                IDao dao = (IDao)factoryDao.obtener("ConversatoriosDao");
                String grado = request.getParameter("grado");
                IDaoConversatorios daoConversatorio= (IDaoConversatorios)dao;
                LinkedList<JSONArray> arreglos = daoConversatorio.consultarPorGrado(grado);
                JSONObject conversatorios = new JSONObject();
                conversatorios.put("titulos",arreglos.get(0));
                conversatorios.put("inscritos",arreglos.get(1));
                dao = (IDao)factoryDao.obtener("GradoDao");
                IGradoDao gradoDao= (IGradoDao)dao;
                JSONObject boton = new JSONObject();
                JSONArray arregloEP = gradoDao.consultarBtnPorGrado(grado);
                boton.put("datos",arregloEP);
                json.put("conversatorios", conversatorios);
                json.put("boton", boton);
                PrintWriter out = response.getWriter();
                out.print(json.toString());
             }else if(parametro.equals("PorEstudiante")){
                 
                PrintWriter out = response.getWriter();
                out.print(json.toString());
             }                       
            else if (parametro.equals("ClicksPorGrado")) {
            IDao dao = (IDao) factoryDao.obtener("EstadisticasBtnPanicoDao");
            EstadisticasBtnPanicoDao estdao = (EstadisticasBtnPanicoDao) dao;
            LinkedList<JSONArray> estadisticas = estdao.listarClicksPorGrado();
            json.put("grado", estadisticas.get(0));
            json.put("Clicks", estadisticas.get(1));
            PrintWriter out = response.getWriter();
            out.print(json.toString());

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
