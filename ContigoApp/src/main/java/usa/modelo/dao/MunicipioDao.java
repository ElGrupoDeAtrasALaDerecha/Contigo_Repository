package usa.modelo.dao;

import java.util.LinkedList;
import usa.modelo.dto.Municipio;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author santi
 */
public class MunicipioDao implements IDao<Municipio> {

    PreparedStatement pat;
    Statement stmt;
    ResultSet result;

    @Override
    public boolean crear(Municipio t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Municipio consultar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizar(Municipio t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<Municipio> listarTodos() {
        LinkedList<Municipio> municipios = new LinkedList<Municipio>();
        try {
            String sql = "select * from MUNICIPIO";
            pat = conn.prepareStatement(sql);
            result = pat.executeQuery();
            while (result.next()) {
                Municipio mun = new Municipio();
                mun.setId(result.getInt("id"));
                mun.setDepartamentoId(result.getInt("DEPARTAMENTO_id"));
                mun.setNombre(result.getString("nombre"));
                municipios.add(mun);
            }
            return municipios;
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return municipios;
    }

}
