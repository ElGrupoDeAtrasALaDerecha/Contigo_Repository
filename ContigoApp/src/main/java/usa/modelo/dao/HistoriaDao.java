package usa.modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import usa.modelo.dto.Historia;

/**
 *
 * @author Miguel Rippe, Santiago Cáceres, Laura Blanco y Santiago Pérez
 */
public class HistoriaDao implements IHistoriasDao{

    @Override
    public boolean crear(Historia t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        LinkedList<Historia> historias= new LinkedList();
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
