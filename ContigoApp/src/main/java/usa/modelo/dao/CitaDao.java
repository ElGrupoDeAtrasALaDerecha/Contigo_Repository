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
import org.json.JSONArray;
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

    @Override
    public Cita consultar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    @Override
    public LinkedList<Cita> listarHistorial(String documento) {
        LinkedList<Cita> historialDeCitas = new LinkedList();
        try {
            String sql = "select concat(p.primerNombre,\" \",p.segundoNombre,\" \",p.primerApellido,\" \",p.segundoApellido) as personal, pc.imagen , c.* from cita as c \n"
                    + "inner join agenda as a on a.id=c.AGENDA_id\n"
                    + "inner join personal as pc on pc.PERSONA_documento=a.PERSONAL_PERSONA_documento\n"
                    + "inner join persona as p on p.documento=pc.PERSONA_documento\n"
                    + "where ESTUDIANTE_PERSONA_documento=\""+documento+"\" and fecha<sysdate();";
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
                cita.setPersonal(rs.getString("personal"));
                cita.setImagen(rs.getString("imagen"));
                historialDeCitas.add(cita);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CitaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return historialDeCitas;
    }
}
