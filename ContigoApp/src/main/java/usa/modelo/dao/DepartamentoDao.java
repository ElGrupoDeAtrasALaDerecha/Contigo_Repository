package usa.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import usa.bd.IConexionBD;
import usa.factory.AbstractFactory;
import usa.factory.Producer;
import usa.modelo.dto.Departamento;


/**
 *
 * @author santi l
 */
public class DepartamentoDao implements IDao<Departamento>{
    
    
    PreparedStatement pat;
    Statement stmt; 	            
    ResultSet result;

    @Override
    public boolean crear(Departamento t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Departamento consultar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizar(Departamento t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<Departamento> listarTodos() {
         LinkedList<Departamento> departamentos = new LinkedList<Departamento>();
        //pat sirve como el cur() de py 
        try {
            String sql = "select * from DEPARTAMENTO";
            pat = conn.prepareStatement(sql);
            result = pat.executeQuery();
            while(result.next()){
                Departamento dep = new Departamento();
                dep.setId(result.getInt("id"));
                dep.setNombre(result.getString("nombre"));
                departamentos.add(dep);
            }
            return departamentos;
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return departamentos;
    }
    
}
