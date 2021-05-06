/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usa.modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static usa.modelo.dao.IDao.conn;
import usa.modelo.dto.Agenda;
import usa.modelo.dto.PersonalCalificado;

/**/
/**
 *
 * @author Valeria
 */
public class AgendaDao implements IDaoAgenda {

    PreparedStatement pat;
    Statement stmt;
    ResultSet result;

    /**
     *
     * @param ag
     * @return
     */
    public int crearAgenda(Agenda ag) {
        try {
            String sql = "insert into AGENDA (PERSONAL_PERSONA_documento, fechaInicio, fechaFin,horaInicio ,horaFin ) values (?,?,?,?,?)";
            pat = conn.prepareStatement(sql);
            pat.setString(1, ag.getIdPersonal());
            pat.setString(2, ag.getFechaInicio());
            pat.setString(3, ag.getFechaFin());
            pat.setInt(4, ag.getHoraInicio());
            pat.setInt(5, ag.getHoraFin());
            pat.execute();
            String sql2 = "select id from AGENDA order by id desc limit 1;";
            pat = conn.prepareStatement(sql2);
            ResultSet rs = pat.executeQuery();
            if (rs.next()) {
                int idAgenda = rs.getInt("id");
                rs.close();
                pat.close();
                return idAgenda;
            }
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
//Consulta agenda por personal calificado
    @Override
    public Agenda consultar(String id) {
        Agenda agenda = null;
        try {
            String sql = "select * from agenda where personal_persona_documento = '" + id + "';";
            pat = conn.prepareStatement(sql);
            result = pat.executeQuery();
            agenda = new Agenda();
            while (result.next()) {
                agenda.setId(result.getInt("id"));
                agenda.setIdPersonal(result.getString("personal_persona_documento"));
                agenda.setFechaInicio(result.getString("fechaInicio"));
                agenda.setFechaInicio(result.getString("fechaFin"));
                agenda.setHoraInicio(result.getInt("horaInicio"));
                agenda.setHoraFin(result.getInt("horaFin"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AgendaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return agenda;

    }

    @Override
    public boolean actualizar(Agenda t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<Agenda> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean crear(Agenda t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
  

}
