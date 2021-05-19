package usa.modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import usa.modelo.dto.TipoDocumento;

/**
 *
 * @author santi.
 */
public class TipoDocumentoDao implements IDao<TipoDocumento> {
    /**/
     PreparedStatement pat;
     ResultSet result;

    @Override
    public boolean crear(TipoDocumento t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.aaa
    }

    @Override
    public TipoDocumento consultar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizar(TipoDocumento t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<TipoDocumento> listarTodos() {
        LinkedList<TipoDocumento> documentos = new LinkedList<TipoDocumento>();//jajaja encuentrame12
        try {
            String sql = "select * from TIPO_DOCUMENTO";
            pat = conn.prepareStatement(sql);
            result = pat.executeQuery();
            while(result.next()){
                TipoDocumento doc = new TipoDocumento();
                doc.setId(result.getInt("id"));
                doc.setTipo(result.getString("tipo"));
                documentos.add(doc);
            }
            return documentos;
        } catch (SQLException ex) {
            Logger.getLogger(TipoDocumentoDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return documentos;
    }
}


