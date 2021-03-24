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
import java.sql.*;

/**
 *
 * @author santi
 */
public class InstitucionDao implements IDao<Institucion> {

    PreparedStatement pat;
    Statement stmt; 	            
    ResultSet result;
    
    @Override
    public boolean crear(Institucion ins) {
        
        try {
            String sql = "insert into  institucion (MUNICIPIO_id, METODO_PAGO_id, nombre, correo, direccion, tipoInstitucion, calendario, barrio, telefono, contraseña, web ) "
                    + "values (?,?,?,?,?,?,?,?,?,sha(?),?)";
            Connection conn = Conexion.tomarConexion();
            pat = conn.prepareStatement(sql);
            pat.setInt(1, ins.getIdMunicipio());
            pat.setInt(2, ins.getMETODO_PAGO_id());
            pat.setString(3, ins.getNombre());
            pat.setString(4, ins.getCorreo());
            pat.setString(5, ins.getDireccion());
            pat.setBoolean(6, ins.isTipoInstitucion());
            pat.setBoolean(7, ins.isCalendario());
            pat.setString(8, ins.getBarrio());
            pat.setString(9, ins.getTelefono());
            pat.setString(10, ins.getContraseña());
            pat.setString(11, ins.getPagina());
            pat.execute();
            pat.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }


    @Override
    public Institucion consultar(String nom) {
        Institucion ins = null;
        try {
            String sql = "select * from institucion where nombre =\""+ nom+"\"";
            Connection conn = Conexion.tomarConexion();
            pat = conn.prepareStatement(sql);
            result = pat.executeQuery();    
            while(result.next()){
                ins = new Institucion();
                ins.setIdMunicipio(result.getInt("MUNICIPIO_id"));
                //int meto_pago = result.getInt("METODO_PAGO_id");//falta en el fornt 
                ins.setNombre(result.getString("nombre"));
                ins.setCorreo(result.getString("correo"));
                ins.setDireccion(result.getString("direccion"));
                ins.setTipoInstitucion(result.getBoolean("tipoInstitucion"));
                //boolean calen = result.getBoolean("calendario"); //falta en front
                ins.setBarrio(result.getString("barrio"));
                ins.setTelefono(result.getString("telefono"));
                ins.setContraseña(result.getString("contraseña"));
                ins.setPagina(result.getString("web")); 
            }
            result.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ins;
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
        LinkedList<Institucion> instituciones = new LinkedList<Institucion>();
        //pat sirve como el cur() de py 
        try {
            String sql = "select * from institucion";
            Connection conn = Conexion.tomarConexion();
            pat = conn.prepareStatement(sql);
            result = pat.executeQuery();
            while(result.next()){
                Institucion ins = new Institucion();
                ins.setIdMunicipio(result.getInt("MUNICIPIO_id"));
                //int meto_pago = result.getInt("METODO_PAGO_id");//falta en el fornt 
                ins.setNombre(result.getString("nombre"));
                ins.setCorreo(result.getString("correo"));
                ins.setDireccion(result.getString("direccion"));
                ins.setTipoInstitucion(result.getBoolean("tipoInstitucion"));
                //boolean calen = result.getBoolean("calendario"); //falta en front
                ins.setBarrio(result.getString("barrio"));
                ins.setTelefono(result.getString("telefono"));
                ins.setContraseña(result.getString("contraseña"));
                ins.setPagina(result.getString("web"));
                instituciones.add(ins);
            }
            return instituciones;
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return instituciones;
    }
    
    public Institucion loginInstitucion(String correo, String contraseña){
        int id = 666;
        Institucion inst = null;
        try {
            String sql = "select * from institucion where correo = \"" + correo + "\" and contraseña = \"" + contraseña + "\";";
            Connection conn = Conexion.tomarConexion();
            pat = conn.prepareStatement(sql);
            result = pat.executeQuery();
            while(result.next()){
                id = result.getInt("id");
            }
            inst = consultar(String.valueOf(id));
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return inst;
    } 
}
