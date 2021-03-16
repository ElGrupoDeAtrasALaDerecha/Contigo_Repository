/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usa.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import usa.modelo.dto.Institucion;

/**
 *
 * @author santi
 */
public class InstitucionDao implements IDao<Institucion> {

    PreparedStatement pat;
    
    @Override
    public boolean crear(Institucion t) {
        /*
        try {
            String sql = "insert into  institucion (Direccion_idDireccion,nombre) values (?,?)";
            Connection conn = Conexion.tomarConexion();
            pat = conn.prepareStatement(sql);
            pat.setInt(1, proveedor.getDireccion().getIdDireccion());
            pat.setString(2, proveedor.getNombre());
            boolean insert = pat.execute();
            pat.close();
            return insert;
        } catch (SQLException ex) {
            Logger.getLogger(SedeDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
*/
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public Institucion consultar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizar(Institucion t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<Institucion> listarTodos() {
        LinkedList<Institucion> instituciones = new LinkedList();
        //pat sirve como el cur() de py 
        try {
            String sql = "select * from institucion";
            Connection conn = Conexion.tomarConexion();
            pat = conn.prepareStatement(sql);
            pat.execute();
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return instituciones;
    }

}
