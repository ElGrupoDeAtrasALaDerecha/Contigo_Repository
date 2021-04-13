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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EstadisticasBtnPanico consultar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizar(EstadisticasBtnPanico t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            Connection conn = Conexion.tomarConexion();
            pat = conn.prepareStatement(sql);
            result = pat.executeQuery();
            while(result.next()){
                EstadisticasBtnPanico est = new EstadisticasBtnPanico();
                est.setClikcs(result.getInt("CANTIDAD_CLICK"));
                est.setFecha(result.getString("fecha"));
                est.setEstudiante(estudainte.consultar(result.getString("ESTUDIANTE_PERSONA_documento")));
            }
            return estadisticas;
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estadisticas;
    }
    
}
