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
import usa.modelo.dao.IDao;
import usa.modelo.dao.IDaoAgenda;
import usa.modelo.dao.IDaoCita;
import usa.modelo.dao.PersonalCalificadoDao;
import usa.modelo.dto.Agenda;
import usa.modelo.dto.Cita;
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
    IDao personalC = (IDao) factoryDao.obtener("PersonalCalificadoDao");
    IDao citaD = (IDao) factoryDao.obtener("CitaDao");

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
        PersonalCalificadoDao personal = (PersonalCalificadoDao) personalC;
        IDaoAgenda daoagenda = (IDaoAgenda) dao;
        IDaoCita daocita = (IDaoCita) citaD;
        Agenda agenda = new Agenda();
        if (personal.consultarPorToken(json.getString("personal")).getDocumento() != null) {
            agenda.setIdPersonal(personal.consultarPorToken(json.getString("personal")).getDocumento());
            agenda.setFechaInicio(json.getString("fechainicio"));
            agenda.setFechaFin(json.getString("fechafin"));
            agenda.setHoraInicio(json.getInt("horainicio"));
            agenda.setHoraFin(json.getInt("horafin"));
            int idAgenda = daoagenda.crearAgenda(agenda);
            if (idAgenda != 0) {
                int horaInicio = json.getInt("horainicio");
                String fechaInicio = json.getString("fechainicio");
                String fechaFin = json.getString("fechafin");
                int horaFin = json.getInt("horafin");
                json.put("tipo", "ok");
                json.put("mensaje", "Se ha creado la agenda del personal");
                fechaFin = daocita.asignarFecha(fechaFin);
                while (fechaInicio.compareTo(fechaFin) != 0) {
                    while (horaInicio < horaFin) {
                        Cita cita = daocita.crearObjetoCita(idAgenda, horaInicio, fechaInicio);
                        if (citaD.crear(cita)) {
                            horaInicio = daocita.asignarHoraDia(horaInicio);
                        } 
                    }
                    horaInicio = json.getInt("horainicio");
                    String fechaI = daocita.asignarFecha(fechaInicio);
                    fechaInicio = null;
                    fechaInicio = fechaI;
                }

            }
        } else {
            json.put("tipo", "error");
            json.put("mensaje", "Error al crear agenda");
        }

        out.print(json.toString());

    }

}
