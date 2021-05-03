package usa.modelo.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import usa.modelo.dto.Final;


/**
 * Clase de objeto de acceso a datos de los finales de historias de situaciones
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres, Camila Fernández,
 * Andrés López, Natalia Montenegro, Santiago Pérez y Miguel Rippe
 */

public class FinalDao implements IFinalDao{
 PreparedStatement pat;
    @Override
    public boolean crear(Final fin) {
        try {
            String sql = "delete from situacion where id = "+ fin.getId()+";";
            pat = conn.prepareStatement(sql);
            pat.execute();
            pat.close();
           
            sql = "insert into fin (id,SITUACION_id,titulo,texto)\n" +
            "values(?,?,?,?);";
            pat = conn.prepareStatement(sql);
            pat.setInt(1, fin.getPredecesor());
            if(fin.getPredecesor()!=0){
                pat.setInt(2, fin.getPredecesor());
            }else{
                pat.setString(2,null);
            }
            pat.setString(3, fin.getTitulo());
            pat.setString(4, fin.getTexto());
            pat.execute();
            pat.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    
    }

    @Override
    public Final consultar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizar(Final fin) {
       try {
            String sql = "UPDATE fin SET HISTORIA_idHistoria=?, titulo=?, texto=? WHERE id=?";
            pat = conn.prepareStatement(sql);
            pat.setInt(1, fin.getIdHistoria());
            pat.setString(2, fin.getTitulo());
            pat.setString(3, fin.getTexto());
            pat.setInt(4, fin.getId());
            pat.execute();
            pat.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean eliminar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<Final> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
