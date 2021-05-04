package usa.modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            pat.setInt(1, fin.getId());
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
       Final situacion = null ;
        try {
            String sql = "select * from fin where id =\""+id+"\"";
            pat = conn.prepareStatement(sql);
            ResultSet result = pat.executeQuery();
            while(result.next()){
                situacion = new Final();
                situacion.setId(result.getInt("id"));
                situacion.setPredecesor(result.getInt("SITUACION_id"));
                situacion.setIdHistoria(result.getInt("HISTORIA_idHistoria"));
                situacion.setTexto(result.getString("texto"));
              
                return situacion;
            }
        } catch (SQLException ex) {
            Logger.getLogger(HistoriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return situacion;
    }

    @Override
    public boolean actualizar(Final fin) {
       try {
            String sql = "UPDATE fin SET titulo=?, texto=? WHERE id=?";
            pat = conn.prepareStatement(sql);
            pat.setString(1, fin.getTitulo());
            pat.setString(2, fin.getTexto());
            pat.setInt(3, fin.getId());
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
       try {
            String sql = "delete from fin where id="+id+";";
            pat = conn.prepareStatement(sql);
            pat.execute();
            pat.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public LinkedList<Final> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
