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
import usa.modelo.dto.EstadisticasBtnPanico;

/**
 *
 * @author Andrés
 */
public class EstadisticasBtnPanicoDao implements IEstadisticasBtnPanicoDao {

    PreparedStatement pat;
    Statement stmt;
    ResultSet result;

    @Override
    public boolean crear(EstadisticasBtnPanico t) {
        try {
            String sql = "insert into ESTADISTICAS_BTNPANICO (ESTUDIANTE_PERSONA_documento, FECHA) values (?,now())";
            pat = conn.prepareStatement(sql);
            pat.setString(1, t.getEstudiante());
            pat.execute();
            pat.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //Consulta del número de clics por estudiante
    @Override
    public EstadisticasBtnPanico consultar(String id) {
        EstadisticasBtnPanico est = null;
        try {
            String sql = "select count(*) as N_Clicks from ESTADISTICAS_BTNPANICO where ESTUDIANTE_PERSONA_documento = '" + id + "';";
            pat = conn.prepareStatement(sql);
            result = pat.executeQuery();
            est = new EstadisticasBtnPanico();
            while (result.next()) {
                est.setClikcs(result.getInt("N_Clicks"));
                est.setEstudiante(id);
            }
            sql = "select fecha from ESTADISTICAS_BTNPANICO where ESTUDIANTE_PERSONA_documento = '" + id + "';";
            pat = conn.prepareStatement(sql);
            result = pat.executeQuery();
            while (result.next()) {
                est.fechas.add(result.getString("fecha"));
            }
            result.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return est;
    }

    @Override
    public boolean actualizar(EstadisticasBtnPanico est) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<EstadisticasBtnPanico> listarTodos() {
        LinkedList<EstadisticasBtnPanico> estadisticas = new LinkedList<>();
        try {
            String sql = "select DISTINCT (ESTUDIANTE_PERSONA_documento) from ESTADISTICAS_BTNPANICO;";
            pat = conn.prepareStatement(sql);
            try (ResultSet result_x = pat.executeQuery()) {
                while (result_x.next()) {
                    estadisticas.add(consultar(result_x.getString("ESTUDIANTE_PERSONA_documento")));
                }
            }
            result.close();
            pat.cancel();
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estadisticas;
    }

    public LinkedList<JSONArray> listarClicksPorGrado() {
        LinkedList<JSONArray> listaClicks = new LinkedList<>();
        try {
            String sql = "select clasificacion.grado, count(ESTADISTICAS_BTNPANICO.ESTUDIANTE_PERSONA_documento) as Clicks\n"
                    + "from persona, estudiante, clasificacion, grado, ESTADISTICAS_BTNPANICO\n"
                    + "where persona.documento = estudiante.PERSONA_documento and clasificacion.id = grado.CLASIFICACION_id and estudiante.GRADO_codigo = grado.codigo\n"
                    + "      and ESTADISTICAS_BTNPANICO.ESTUDIANTE_PERSONA_documento = estudiante.PERSONA_documento\n"
                    + "group by clasificacion.grado ORDER BY clasificacion.id asc;";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            listaClicks.add(new JSONArray());
            listaClicks.add(new JSONArray());
            while (rs.next()) {
                listaClicks.get(0).put(rs.getString("grado"));
                listaClicks.get(1).put(rs.getString("Clicks"));
            }
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaClicks;
    }
    
    @Override
    public LinkedList<JSONArray> clicksPorestudiante(String documento) {
        LinkedList<JSONArray> listaClicks = new LinkedList<>();
        try {
            String sql = "select t2.mes, coalesce(t1.clicks,0) as clicks from \n" +
                        "(\n" +
                        "select month(FECHA) as mes, count(eb.ESTUDIANTE_PERSONA_documento) as clicks from estadisticas_btnpanico as eb\n" +
                        "inner join estudiante as e on eb.ESTUDIANTE_PERSONA_documento=e.PERSONA_documento\n" +
                        "where e.PERSONA_documento=\""+documento+"\"\n" +
                        "and \n" +
                        "FECHA between FIRST_DAY(DATE_SUB(NOW(), INTERVAL 5 MONTH)) and NOW()\n" +
                        "group by month(FECHA)\n" +
                        "order by month(FECHA) asc\n" +
                        ") as t1\n" +
                        "right outer join\n" +
                        " (select month(q.dia) as mes from (\n" +
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
                        "on t1.mes=t2.mes;";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            listaClicks.add(new JSONArray());
            listaClicks.add(new JSONArray());
            while (rs.next()) {
                listaClicks.get(0).put(rs.getInt("mes"));
                listaClicks.get(1).put(rs.getInt("clicks"));
            }
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaClicks;
    }
}
