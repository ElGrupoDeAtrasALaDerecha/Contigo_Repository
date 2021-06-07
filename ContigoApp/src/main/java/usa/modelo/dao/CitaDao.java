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
import org.json.JSONObject;
import usa.factory.AbstractFactory;
import usa.factory.Producer;
import static usa.modelo.dao.IDao.conn;
import usa.modelo.dto.Cita;
import usa.modelo.dto.PersonalCalificado;

/**
 * Clase de objeto de acceso a datos de las citas
 *
 * @author Valeria Bermúdez, Santiago Pérez
 * @version 1.1
 * @since 2021-06-03
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
            String sql = "select * from CITA where id = '" + idr + "';";
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
            String sql = "update CITA\n"
                    + "set ESTUDIANTE_PERSONA_documento = \"" + cita.getIdEstudiante() + "\"\n,"
                    + "estado=" + cita.getEstado() + ", "
                    + "motivo=\"" + cita.getMotivo() + "\" "
                    + "where id=" + cita.getId() + ";";
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
            String sql = "select *from CITA ";
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
                    + "inner join AGENDA as a on a.id=c.AGENDA_id\n"
                    + "inner join PERSONAL as pc on pc.PERSONA_documento=a.PERSONAL_PERSONA_documento\n"
                    + "inner join PERSONA as p on p.documento=pc.PERSONA_documento\n"
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
        AbstractFactory factoryDao = Producer.getFabrica("DAO");
        IPersonalCalificadoDao dao = (IPersonalCalificadoDao) factoryDao.obtener("PersonalCalificadoDao");
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
            for (Cita cita : perca) {
                PersonalCalificado p = dao.consultar(cita.getId_perca());
                cita.setPersonal(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CitaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return perca;
    }

    public LinkedList<Cita> listarCitasPersonal(String id) {
        LinkedList<Cita> citasPersonal = new LinkedList();
        try {
            String sql = "select p.primerNombre,p.segundoNombre, p.primerApellido ,P.segundoApellido, es.PERSONA_documento,ci.*\n"
                    + "       from PERSONA as p inner join ESTUDIANTE as es \n"
                    + "       on es.PERSONA_documento = p.documento\n"
                    + "	   inner join CITA as ci on ci.ESTUDIANTE_PERSONA_documento = es.PERSONA_documento \n"
                    + "       inner join AGENDA as a on a.id=ci.AGENDA_id\n"
                    + "		where PERSONAL_PERSONA_documento=\"" + id + "\";";
            pat = conn.prepareStatement(sql);
            result = pat.executeQuery();
            while (result.next()) {
                Cita cita = new Cita();
                cita.setNombre_estudiante(result.getString("primerNombre") + " " + result.getString("segundoNombre") + " " + result.getString("primerApellido") + " " + result.getString("segundoApellido"));
                cita.setId(result.getInt("id"));
                cita.setIdAgenda(result.getInt("AGENDA_id"));
                cita.setIdEstudiante(result.getString("ESTUDIANTE_PERSONA_documento"));
                cita.setHoraInicio(result.getInt("horaInicio"));
                cita.setFecha(result.getString("fecha"));
                cita.setEstado(result.getInt("estado"));
                cita.setLugar(result.getString("lugar"));
                cita.setMotivo(result.getString("motivo"));
                cita.setMotivo(result.getString("recomendaciones"));
                citasPersonal.add(cita);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CitaDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return citasPersonal;
    }

    @Override
    public JSONObject ultimasCitasEstudiante(String documento) {
        JSONObject datos = null;
        try {
            String sql = "select t2.mes,coalesce(t1.citas,0) as citas from (\n" +
                            "select month(fecha) as mes, count(e.PERSONA_documento) as citas\n" +
                            "from cita as c\n" +
                            "left join ESTUDIANTE as e on e.PERSONA_documento=c.ESTUDIANTE_PERSONA_documento\n" +
                            "inner join PERSONA as p on p.documento=e.PERSONA_documento\n" +
                            "where p.documento=\""+documento+"\" \n" +
                            "and \n" +
                            "c.fecha between LAST_DAY(DATE_SUB(NOW(), INTERVAL 6 MONTH)) and NOW()\n" +
                            "group by month(fecha)\n" +
                            "order by fecha asc) as t1\n" +
                            "right outer join  (select month(q.dia) as mes from (\n" +
                            "SELECT\n" +
                            "    DATE_ADD(LAST_DAY(DATE_SUB(NOW(), INTERVAL 5 MONTH)), INTERVAL t.n DAY) as dia\n" +
                            "FROM (\n" +
                            "    SELECT \n" +
                            "        a.N + b.N * 10 + c.N * 100 AS n\n" +
                            "    FROM\n" +
                            "        (SELECT 0 AS N UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) a\n" +
                            "       ,(SELECT 0 AS N UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) b\n" +
                            "       ,(SELECT 0 AS N UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7) c\n" +
                            "    ORDER BY n\n" +
                            ") t   \n" +
                            "WHERE\n" +
                            "    t.n <= TIMESTAMPDIFF(DAY, LAST_DAY(DATE_SUB(NOW(), INTERVAL 5 MONTH)) , NOW() )) as q\n" +
                            "group by month(q.dia)\n" +
                            "order by q.dia) as t2\n" +
                            "on t1.mes=t2.mes; ";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            datos = new JSONObject();
            JSONArray meses = new JSONArray();
            JSONArray citas = new JSONArray();
            while (rs.next()) {
                meses.put(rs.getInt("mes"));
                citas.put(rs.getInt("citas"));
            }
            datos.put("meses",meses);
            datos.put("citas",citas);
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(CitaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datos;
    }

}
