package usa.modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import usa.modelo.dto.Persona;

/**
 * 
 * @author Santiago Pérez
 */
public class PersonaDao implements IPersonaDao{


    @Override
    public boolean crear(Persona t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Persona consultar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizar(Persona t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<Persona> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Persona consultarPorCorreo(String correo) {
        PreparedStatement pat;
        ResultSet result;
        Persona p = null;
        try {
            String sql = "select * from PERSONA where correo=\"" + correo +"\";";
            pat = conn.prepareStatement(sql);
            result = pat.executeQuery();
            if(result.next()){
                p = new Persona();
                p.setDocumento(result.getString("documento"));
                p.setPrimerApellido(result.getString("primerNombre"));
                p.setDocumento(result.getString("documento"));
                p.setPrimerNombre(result.getString("primerNombre"));
                p.setSegundoNombre(result.getString("segundoNombre"));
                p.setPrimerApellido(result.getString("primerApellido"));
                p.setSegundoApellido(result.getString("segundoApellido"));
                p.setFechaDeNacimiento(result.getString("fechaNacimiento"));
                p.setGenero(result.getString("genero"));
                p.setCorreo(result.getString("correo"));
            }
            result.close();
            pat.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    
}
