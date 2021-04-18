package usa.modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static usa.modelo.dao.IDao.conn;
import usa.modelo.dto.Historia;

/**
 *
 * @author Miguel Rippe, Santiago Cáceres, Laura Blanco y Santiago Pérez
 */
public class HistoriaDao implements IHistoriasDao{

    PreparedStatement pat;
    ResultSet result;
    
    @Override
    public boolean crear(Historia his) {
        try {
            String sql = "insert into  historia (PERSONAL_PERSONA_documento,titulo,descripcion,urlImagen) "
                    + "values (?,?,?,?)";
            pat = conn.prepareStatement(sql);
            pat.setString(1, his.getDocumentoCreador());
            pat.setString(2, his.getTitulo());
            pat.setString(3, his.getDescripcion());
            pat.setString(3, his.getUrlImagen());
            pat.execute();
            pat.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Historia consultar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizar(Historia t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<Historia> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
