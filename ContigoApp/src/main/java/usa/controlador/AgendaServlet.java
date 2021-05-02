/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usa.controlador;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import usa.factory.AbstractFactory;
import usa.factory.Producer;
import usa.modelo.dao.AgendaDao;
import usa.modelo.dao.EstadisticasBtnPanicoDao;
import usa.modelo.dao.EstudianteDao;
import usa.modelo.dao.IDao;
import usa.modelo.dao.InstitucionDao;
import usa.modelo.dao.PersonalCalificadoDao;
import usa.modelo.dto.Agenda;
import usa.modelo.dto.Conversatorio;
import usa.modelo.dto.EstadisticasBtnPanico;
import usa.modelo.dto.Institucion;
import usa.modelo.dto.PersonalCalificado;
import usa.utils.Utils;

/**/
/**
 *
 * @author Valeria
 */
@WebServlet(name = "AgendaServlet", urlPatterns = {"/Agenda"})
public class AgendaServlet extends HttpServlet {

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

    AbstractFactory factoryDao = Producer.getFabrica("DAO");
    IDao dao = (IDao) factoryDao.obtener("AgendaDao");
    AgendaDao agendaD = (AgendaDao) dao;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject(Utils.readParams(request));
        AgendaDao agendaN = new AgendaDao();
        IDao personalC = (IDao) factoryDao.obtener("PersonalCalificadoDao");
        PersonalCalificadoDao personal = (PersonalCalificadoDao) personalC;
        Agenda agenda = new Agenda();
        if (personal.consultarPorToken(json.getString("personal")).getDocumento() != null) {
            if(agendaD!=null){
                System.out.println("si sirvo");
            }
            System.out.println("" +json.getInt("horainicio"));
            System.out.println(""+json.getInt("horafin"));
            agenda.setIdPersonal(personal.consultarPorToken(json.getString("personal")).getDocumento());
            agenda.setFechaInicio(json.getString("fechainicio"));
            agenda.setFechaFin(json.getString("fechafin"));
            agenda.setHoraInicio(json.getInt("horainicio"));
            agenda.setHoraFin(json.getInt("horafin"));
            agendaN.crear(agenda);
            json.put("tipo", "ok");
            json.put("mensaje", "Se ha creado la agenda del personal");
        } else {
            json.put("tipo", "error");
            json.put("mensaje", "Error al crear agenda");
        }
        out.print(json.toString());

    }

}
