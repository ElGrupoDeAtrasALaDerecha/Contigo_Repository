
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
import usa.modelo.dao.IDao;
import usa.modelo.dao.IDaoConversatorios;
import usa.modelo.dao.IGradoDao;
import usa.utils.Utils;

/**
 *
 * @author 
 */
@WebServlet(name = "EstadisticasServlet", urlPatterns = {"/Estadisticas"})
public class EstadisticasServlet extends HttpServlet {

   AbstractFactory factoryDao=Producer.getFabrica("DAO");
    
   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("application/json;charset=UTF-8");
            JSONObject json = new JSONObject();
            String parametro = request.getParameter("tipoConsulta");
            if(parametro.equals("ConversatorioPorGrado")){
                IDao dao = (IDao)factoryDao.obtener("ConversatoriosDao");
                String grado = request.getParameter("grado");
                IDaoConversatorios daoConversatorio= (IDaoConversatorios)dao;
                LinkedList<JSONArray> arreglos = daoConversatorio.consultarPorGrado(grado);
                json.put("titulos",arreglos.get(0));
                json.put("inscritos",arreglos.get(1));
                PrintWriter out = response.getWriter();
                out.print(json.toString());
             }else if(parametro.equals("BtnPorGrado")){
                 IDao dao = (IDao)factoryDao.obtener("GradoDao");
                String grado = request.getParameter("grado");
                IGradoDao gradoDao= (IGradoDao)dao;
                JSONArray arregloEP = gradoDao.consultarBtnPorGrado(grado);
                json.put("datos",arregloEP);
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
