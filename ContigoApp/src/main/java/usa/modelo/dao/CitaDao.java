/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usa.modelo.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
           String sql = "select DATE_ADD(\""+fechaInicio+"\", INTERVAL 1 DAY) as fecha;";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            if (rs.next()) {
                String fechaI = rs.getString("fecha");
                fechaInicio=null;
                fechaInicio=fechaI;
                rs.close();
                pat.close();
                return fechaInicio;
            }
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}