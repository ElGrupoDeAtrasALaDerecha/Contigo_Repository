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

/**/
/**
 *
 * @author Andrés
 */
public class EstadisticasBtnPanicoDao implements IDao<EstadisticasBtnPanico> {

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
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaClicks;
    }
}
