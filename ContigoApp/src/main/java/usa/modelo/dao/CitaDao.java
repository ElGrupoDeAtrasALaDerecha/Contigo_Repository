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
import org.json.JSONObject;
import static usa.modelo.dao.IDao.conn;
import usa.modelo.dto.Cita;

/**
 *
 * @author Valeria
 */
public class CitaDao implements IDaoCita {

    PreparedStatement pat;
    Statement stmt;
    ResultSet result;

    @Override
    public boolean crear(Cita cita) {
        try {
            String sql = "insert into CITA (AGENDA_id ,horaInicio,fecha,estado,lugar ) values (?,?,?,?,?);";
            pat = conn.prepareStatement(sql);
            pat.setInt(1, cita.getIdAgenda());
            pat.setInt(2, cita.getHoraInicio());
            pat.setString(3, cita.getFecha());
            pat.setInt(4, cita.getEstado());
            pat.setString(5, cita.getLugar());
            pat.execute();
            pat.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CitaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Cita consultar(String id) {
        int idr = Integer.valueOf(id);
        Cita cita = null;
        try {
            String sql = "select * from cita where id = '" + idr + "';";
            pat = conn.prepareStatement(sql);
            result = pat.executeQuery();
            cita = new Cita();
            while (result.next()) {
                cita.setId(result.getInt("id"));
                cita.setIdAgenda(result.getInt("AGENDA_id"));
                cita.setIdEstudiante(result.getString("ESTUDIANTE_PERSONA_documento"));
                cita.setHoraInicio(result.getInt("fechaInicio"));
                cita.setEstado(result.getInt("estado"));
                cita.setLugar(result.getString("lugar"));
                cita.setMotivo(result.getString("motivo"));
                cita.setRecomendaciones(result.getString("recomendaciones"));
            }
            result.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(AgendaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cita;
    }

    @Override
    public boolean actualizar(Cita t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<Cita> listarTodos() {
        LinkedList<Cita> citas = new LinkedList();
        try {
            String sql = "select *from cita ";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                Cita cita = new Cita();
                cita.setId(rs.getInt("id"));
                cita.setIdAgenda(rs.getInt("AGENDA_id"));
                cita.setIdEstudiante(rs.getString("ESTUDIANTE_PERSONA_documento"));
                cita.setHoraInicio(rs.getInt("horaInicio"));
                cita.setFecha(rs.getString("fecha"));
                cita.setEstado(rs.getInt("estado"));
                cita.setLugar(rs.getString("lugar"));
                citas.add(cita);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CitaDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return citas;
    }

    public int asignarHoraDia(int horainicio) {
        horainicio++;
        return horainicio;
    }

    public Cita crearObjetoCita(int idAgenda, int horaInicio, String fechaInicio) {
        Cita cita = new Cita();
        cita.setIdAgenda(idAgenda);
        cita.setHoraInicio(horaInicio);
        cita.setFecha(fechaInicio);
        cita.setEstado(1);
        cita.setLugar("https://meet.google.com/snf-yxio-tdp");
        return cita;
    }

    /**
     *
     * @param fechaInicio
     * @return
     */
    @Override
    public String asignarFecha(String fechaInicio) {
        try {
            String sql = "select DATE_ADD(\"" + fechaInicio + "\", INTERVAL 1 DAY) as fecha;";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            if (rs.next()) {
                String fechaI = rs.getString("fecha");
                fechaInicio = null;
                fechaInicio = fechaI;
                rs.close();
                pat.close();
                return fechaInicio;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CitaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean registroSucedidoEstudiante(Cita ci) {
        String sql = "update cita set estado=\"" + ci.getEstado() + "\", motivo=\"" + ci.getMotivo() + "\",recomendaciones=\"" + ci.getRecomendaciones() + "\" where id=\"" + ci.getId() + "\";";
        try {
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            pat.setInt(1, ci.getEstado());
            pat.setString(2, ci.getMotivo());
            pat.setString(2, ci.getRecomendaciones());
            pat.execute();
            pat.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CitaDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

}
