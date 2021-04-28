package usa.modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import usa.modelo.dto.Clasificacion;

/**
 *fdsfsdfsdfsd
 * @author andre
 */
public class ClasificacionDao implements IDao<Clasificacion> {

    PreparedStatement pat;
    Statement stmt;
    ResultSet result;

    @Override
    public boolean crear(Clasificacion t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Clasificacion consultar(String txt) {
        Clasificacion clasf = new Clasificacion();
        String sql = "";
        try {
            try {
                int x = (Integer.parseInt(txt));
                sql = "select * from CLASIFICACION where id = " + x;
            } catch (NumberFormatException e) {
                sql = "select * from CLASIFICACION where grado = " + txt;
            }
            pat = conn.prepareStatement(sql);
            result = pat.executeQuery();
            while (result.next()) {
                clasf.setId(result.getInt("id"));
                clasf.setGrado(result.getString("grado"));
            }
            return clasf;
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clasf;
    }

    @Override
    public boolean actualizar(Clasificacion t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<Clasificacion> listarTodos() {
        LinkedList<Clasificacion> lista = new LinkedList();
        String sql = "";
        try {
            sql = "select clasificacion.id,  clasificacion.grado, grado.codigo from clasificacion, grado where grado.CLASIFICACION_id = clasificacion.id; ";
            pat = conn.prepareStatement(sql);
            result = pat.executeQuery();
            while (result.next()) {
                Clasificacion clasf = new Clasificacion();
                clasf.setId(result.getInt("id"));
                clasf.setGrado(result.getString("grado"));
                clasf.setCodigo(result.getString("codigo"));
                lista.add(clasf);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

}
