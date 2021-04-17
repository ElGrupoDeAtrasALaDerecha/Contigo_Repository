/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usa.modelo.dao;

import java.sql.Connection;
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
            String sql = "insert into ESTADISTICAS_BTNPANICO (CANTIDAD_CLICK, ESTUDIANTE_PERSONA_documento, FECHA) values (?,?,?)";
            pat = conn.prepareStatement(sql);
            pat.setInt(1, t.getClikcs());
            pat.setString(2, t.getEstudiante().getDocumento());
            pat.setString(3, t.getFecha());
            pat.execute();
            result.close();
            pat.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         return false;
    }

    @Override
    public EstadisticasBtnPanico consultar(String id) {
        EstudianteDao estudainte = new EstudianteDao();
        EstadisticasBtnPanico est = null;
        try {
            String sql = "select * from ESTADISTICAS_BTNPANICO where ESTUDIANTE_PERSONA_documento = '" + id + "';";
            pat = conn.prepareStatement(sql);
            result = pat.executeQuery();
            while(result.next()){
                est = new EstadisticasBtnPanico();
                est.setClikcs(result.getInt("CANTIDAD_CLICK"));
                est.setFecha(result.getString("fecha"));
                est.setEstudiante(estudainte.consultar(result.getString("ESTUDIANTE_PERSONA_documento")));
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
        try {
            String sql = "UPDATE `contigobd`.`ESTADISTICAS_BTNPANICO` SET `CANTIDAD_CLICK` = '" + (est.getClikcs()+1) +  "' WHERE (ESTUDIANTE_PERSONA_documento = '10008');";
            pat = conn.prepareStatement(sql);
            pat.executeQuery();
            result.close();
            pat.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean eliminar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<EstadisticasBtnPanico> listarTodos() {
        LinkedList<EstadisticasBtnPanico> estadisticas = new LinkedList<EstadisticasBtnPanico>();
        EstudianteDao estudainte = new EstudianteDao();
         try {
            String sql = "select * from ESTADISTICAS_BTNPANICO";
            pat = conn.prepareStatement(sql);
            result = pat.executeQuery();
            while(result.next()){
                EstadisticasBtnPanico est = new EstadisticasBtnPanico();
                est.setClikcs(result.getInt("CANTIDAD_CLICK"));
                est.setFecha(result.getString("fecha"));
                est.setEstudiante(estudainte.consultar(result.getString("ESTUDIANTE_PERSONA_documento")));
                estadisticas.add(est);
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
