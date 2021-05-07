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
public class HistoriaDao implements IHistoriasDao {

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
            pat.setString(4, his.getUrlImagen());
            pat.execute();
            pat.close();
            sql = "select idHistoria from HISTORIA order by idHistoria desc limit 1;";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                his.setId(rs.getInt("idHistoria"));
            }
            pat.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Historia consultar(String id) {
        Historia his = null ;
        try {
            String sql = "select * from HISTORIA where idHistoria =\""+id+"\"";
            pat = conn.prepareStatement(sql);
            result = pat.executeQuery();
            while(result.next()){
                his = new Historia();
                his.setId(result.getInt("idHistoria"));
                his.setDocumentoCreador(result.getString("PERSONAL_PERSONA_documento"));
                his.setTitulo(result.getString("titulo"));
                his.setDescripcion(result.getString("descripcion"));
                his.setUrlImagen(result.getString("urlImagen"));
                return his;
            }
        } catch (SQLException ex) {
            Logger.getLogger(HistoriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return his;
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
        LinkedList<Historia> historias = new LinkedList();
        try {
            String sql = "select * from HISTORIA;";
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                Historia historia = new Historia();
                historia.setTitulo(rs.getString("titulo"));
                historia.setId(rs.getInt("idHistoria"));
                historia.setDocumentoCreador(rs.getString("PERSONAL_PERSONA_documento"));
                historia.setDescripcion(rs.getString("descripcion"));
                historia.setUrlImagen(rs.getString("urlImagen"));
                historias.add(historia);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return historias;
    }

}
