package usa.modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static usa.modelo.dao.IDao.conn;
import usa.modelo.dto.Situacion;

/**
 * Clase de objetos de acceso a datos de las situaciones
 * @author Miguel Rippe, Santiago Cáceres, Laura Blanco y Santiago Pérez
 */
public class SituacionDao implements ISituacionDao{
    PreparedStatement pat;
    @Override
    public boolean crear(Situacion situacion) {
        try {
            String sql = "insert into SITUACION (SITUACION_id,HISTORIA_idHistoria,titulo,texto,opcion)\n" +
            "values(?,?,?,?,?);";
            pat = conn.prepareStatement(sql);
            if(situacion.getPredecesor()!=0){
                pat.setInt(1, situacion.getPredecesor());
            }else{
                pat.setString(1,null);
            }
            pat.setInt(2, situacion.getIdHistoria());
            pat.setString(3, situacion.getTitulo());
            pat.setString(4, situacion.getTexto());
            pat.setString(5, situacion.getTextoOpcion());
            pat.execute();
            pat.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Situacion consultar(String id) {
        Situacion situacion = null ;
        try {
            String sql = "select * from situacion where id =\""+id+"\"";
            pat = conn.prepareStatement(sql);
            ResultSet result = pat.executeQuery();
            while(result.next()){
                situacion = new Situacion();
                situacion.setId(result.getInt("id"));
                situacion.setPredecesor(result.getInt("SITUACION_id"));
                situacion.setIdHistoria(result.getInt("HISTORIA_idHistoria"));
                situacion.setTexto(result.getString("texto"));
                situacion.setTextoOpcion(result.getString("opcion"));
              
                return situacion;
            }
        } catch (SQLException ex) {
            Logger.getLogger(HistoriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return situacion;
    }

    @Override
    public boolean actualizar(Situacion situacion) {
       try {
     
            String sql = "UPDATE situacion SET SITUACION_id=?, HISTORIA_idHistoria=?, titulo=?, texto=?, opcion=? WHERE id=?";
            pat = conn.prepareStatement(sql);
            pat.setInt(1, situacion.getIdHistoria());
            pat.setString(2, situacion.getTitulo());
            pat.setString(3, situacion.getTexto());
            pat.setString(4, situacion.getTextoOpcion());
            pat.setInt(5, situacion.getId());
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
    public LinkedList<Situacion> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<Situacion> consultarPorHistoria(int idHistoria) {
        LinkedList<Situacion> situaciones = new LinkedList();
        try {
            String sql = "select * from HISTORIA;";
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                Situacion situacion = new Situacion();
                situacion.setId(rs.getInt("id"));
                situacion.setPredecesor(rs.getInt("SITUACION_id"));
                situacion.setTitulo(rs.getString("titulo"));
                situacion.setTexto(rs.getString("texto"));
                situacion.setTextoOpcion(rs.getString("opcion"));
                situaciones.add(situacion);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return situaciones;
    }
    
}
