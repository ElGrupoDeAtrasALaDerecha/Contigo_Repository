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
import usa.modelo.dto.EstadisticasBtnPanico;

/**
 *
 * @author Andrés
 */
public class EstadisticasBtnPanicoDao implements IDao<EstadisticasBtnPanico>{
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
            String sql = "select count(*) as N_Clicks from ESTADISTICAS_BTNPANICO where ESTUDIANTE_PERSONA_documento = '" + id +"';";
            pat = conn.prepareStatement(sql);
            result = pat.executeQuery();
            est = new EstadisticasBtnPanico();
            while(result.next()){
                est.setClikcs(result.getInt("N_Clicks"));
                est.setEstudiante(id);
            }
            sql = "select fecha from ESTADISTICAS_BTNPANICO where ESTUDIANTE_PERSONA_documento = '" + id + "';" ;
            pat = conn.prepareStatement(sql);
            result = pat.executeQuery();
            while(result.next()){
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
        LinkedList<EstadisticasBtnPanico> estadisticas = new LinkedList<EstadisticasBtnPanico>();
        try {
            String sql = "select DISTINCT (ESTADISTICAS_BTNPANICO.ESTUDIANTE_PERSONA_documento) from ESTADISTICAS_BTNPANICO;";
            pat = conn.prepareStatement(sql);
            result = pat.executeQuery();
            while(result.next()){
                estadisticas.add(consultar(result.getString("ESTUDIANTE_PERSONA_documento")));
            }
            result.close();
            pat.close();
            return estadisticas;
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estadisticas;
    }
    
}
