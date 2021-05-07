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
import usa.modelo.dto.PersonalCalificado;
import usa.observer.ObservadorCita;

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
                cita.setHoraInicio(result.getInt("horaInicio"));
                cita.setFecha(result.getString("fecha"));
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
    public boolean actualizar(Cita cita) {
        try {
            String sql = "update cita\n"
                    + "set ESTUDIANTE_PERSONA_documento = \""+cita.getIdEstudiante()+"\"\n,"
                    + "estado="+cita.getEstado()+", "
                    + "motivo=\""+cita.getMotivo()+"\" "
                    + "where id="+cita.getId()+";";
            pat = conn.prepareStatement(sql);
            pat.execute();
            pat.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AgendaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
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
        System.out.println("" + ci.getEstado());
        String sql = "update cita set estado=?, motivo=?,recomendaciones=? where id=?;";
        try {
            System.out.println("entro");
            pat = conn.prepareStatement(sql);
            pat.setInt(1, ci.getEstado());
            pat.setString(2, ci.getMotivo());
            pat.setString(3, ci.getRecomendaciones());
            pat.setInt(4, ci.getId());
            pat.execute();
            pat.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CitaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public LinkedList<Cita> listarHistorial(String documento) {
        LinkedList<Cita> historialDeCitas = new LinkedList();
        try {
            String sql = "select concat(p.primerNombre,\" \",p.segundoNombre,\" \",p.primerApellido,\" \",p.segundoApellido) as personal, pc.imagen , c.* from cita as c \n"
                    + "inner join agenda as a on a.id=c.AGENDA_id\n"
                    + "inner join personal as pc on pc.PERSONA_documento=a.PERSONAL_PERSONA_documento\n"
                    + "inner join persona as p on p.documento=pc.PERSONA_documento\n"
                    + "where ESTUDIANTE_PERSONA_documento=\"" + documento + "\";";
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
                cita.setNombre_perca(rs.getString("personal"));
                cita.setImagen(rs.getString("imagen"));
                historialDeCitas.add(cita);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CitaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return historialDeCitas;
    }

    @Override
    public LinkedList<Cita> percaCita(String fecha, String hora) {
        LinkedList<Cita> perca = new LinkedList<>();
        try {
            String sql = "select distinct AGENDA.PERSONAL_PERSONA_documento as ID_PERCA, PERSONA.primerNombre as Nombre, PERSONA.primerApellido as Apellido, PERSONAL.imagen, CITA.AGENDA_id, CITA.id"
                    + " from AGENDA, PERSONAL, CITA, PERSONA "
                    + " where  CITA.AGENDA_id = AGENDA.id and PERSONA.documento = AGENDA.PERSONAL_PERSONA_documento"
                    + " and CITA.fecha = '" + fecha + "' and CITA.horaInicio = '" + hora + "' "
                    + " and CITA.estado=1"
                    + " group by ID_PERCA;";
            pat = conn.prepareStatement(sql);
            result = pat.executeQuery();
            while (result.next()) {
                Cita cita = new Cita();
                cita.setId_perca(result.getString("ID_PERCA"));
                cita.setNombre_perca(result.getString("Nombre") + " " + result.getString("Apellido"));
                cita.setImagen(result.getString("imagen"));
                cita.setId(result.getInt("id"));
                cita.setIdAgenda(result.getInt("AGENDA_id"));
                System.out.println("###" + result.getInt("AGENDA_id") + result.getInt("id"));
                perca.add(cita);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CitaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return perca;
    }
}
