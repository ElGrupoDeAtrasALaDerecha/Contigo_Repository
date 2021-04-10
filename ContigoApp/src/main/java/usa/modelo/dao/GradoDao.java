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
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import usa.modelo.dto.Clasificacion;
import usa.modelo.dto.Grado;
import usa.modelo.dto.GradoClasf;
import usa.utils.GeneradorCodigos;

/**
 *
 * @author andre
 */
public class GradoDao implements IDao<Grado> {

    private PreparedStatement pat;

    @Override
    public boolean crear(Grado grado) {
        if (consultar(String.valueOf(grado.getClasificacion_id())) == null) {
            try {
                Connection con = Conexion.tomarConexion();
                String sql = "insert into GRADO (codigo,CLASIFICACION_id,INSTITUCION_id) values (?,?,?)";
                pat = con.prepareStatement(sql);
                pat.setString(1, grado.getCodigo());
                pat.setInt(2, grado.getClasificacion_id());
                pat.setInt(3, grado.getInstitucion_id());
                pat.execute();
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }          
        return false;
    }

    @Override
    public Grado consultar(String id) {
        Grado grado = null;
        Connection conn = Conexion.tomarConexion();
        try {
            String sql = "select * from GRADO where CLASIFICACION_id = " + id;
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                grado = new Grado();
                grado.setCodigo(rs.getString("codigo"));
                grado.setInstitucion_id(rs.getInt("INSTITUCION_id"));
                grado.setClasificacion_id(rs.getInt("CLASIFICACION_id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return grado;
    }

    @Override
    public boolean actualizar(Grado t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
       
    @Override
    public LinkedList<Grado> listarTodos() {
        LinkedList<Grado> grados = new LinkedList<Grado>();      
        Grado grado = null;
        Connection conn = Conexion.tomarConexion();
        try {
            String sql = "select * from GRADO";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                grado = new Grado();
                grado.setCodigo(rs.getString("codigo"));
                grado.setInstitucion_id(rs.getInt("INSTITUCION_id"));
                grado.setClasificacion_id(rs.getInt("CLASIFICACION_id"));
                grados.add(grado);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return grados;
    }
    
    public LinkedList<GradoClasf> listarGradosClasf() {
        LinkedList<GradoClasf> grados = new LinkedList<GradoClasf>();
        GradoClasf grado = null;
        Connection conn = Conexion.tomarConexion();
        try {
            String sql = "select CLASIFICACION.grado, GRADO.* from GRADO, CLASIFICACION where CLASIFICACION.id = GRADO.CLASIFICACION_id order by GRADO.CLASIFICACION_id asc;";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                grado = new GradoClasf();
                grado.setCodigo(rs.getString("codigo"));
                grado.setInstitucion_id(rs.getInt("INSTITUCION_id"));
                grado.setClasificacion_id(rs.getInt("CLASIFICACION_id"));
                grado.setClasificacion(rs.getString("grado"));
                grados.add(grado);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return grados;
    }
}
