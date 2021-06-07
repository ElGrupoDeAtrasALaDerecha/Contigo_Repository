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
 * Clase de objeto de acceso a datos de las clasificaciones
 * @author andre
 */
public class ClasificacionDao implements IClasificacionDao{

    PreparedStatement pat;
    Statement stmt;
    ResultSet result;

    @Override
    public boolean crear(Clasificacion t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * Método que permite consultar una clasificación de un grado a partir de su id o su nombre de grado
     * @param txt que es el valor que permite realizar la consulta
     * @return un objeto con una clasificación o nulo si no lo encuentra
     */
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

    /**
     * Método que permite listar todas las clasificaciones de grados
     * @return una lista con las clasificaciones
     */
    @Override
    public LinkedList<Clasificacion> listarTodos() {
        LinkedList<Clasificacion> lista = new LinkedList();
        String sql = "";
        try {
            sql = "select c.*, g.codigo from CLASIFICACION c \n" +
            "inner join GRADO as g on g.CLASIFICACION_id=c.id; ";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                Clasificacion clasf = new Clasificacion();
                clasf.setId(rs.getInt("id"));
                clasf.setGrado(rs.getString("grado"));
                clasf.setCodigo(rs.getString("codigo"));
                lista.add(clasf);
                
            }
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

}
