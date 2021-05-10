package usa.modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import static usa.modelo.dao.IDao.conn;
import usa.modelo.dto.Arbol;
import usa.modelo.dto.Final;
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
            String sql = "insert into SITUACION (SITUACION_id,HISTORIA_idHistoria,titulo,texto)\n" +
            "values(?,?,?,?);";
            pat = conn.prepareStatement(sql);
            if(situacion.getPredecesor()!=0){
                pat.setInt(1, situacion.getPredecesor());
            }else{
                pat.setString(1,null);
            }
            pat.setInt(2, situacion.getIdHistoria());
            pat.setString(3, situacion.getTitulo());
            pat.setString(4, situacion.getTexto());
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
            String sql = "UPDATE situacion SET HISTORIA_idHistoria=?, titulo=?, texto=? WHERE id=?";
            pat = conn.prepareStatement(sql);
            pat.setInt(1, situacion.getIdHistoria());
            pat.setString(2, situacion.getTitulo());
            pat.setString(3, situacion.getTexto());
            pat.setInt(4, situacion.getId());
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
            String sql = "delete from situacion where id="+id+";";
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
    public LinkedList<Situacion> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Arbol consultarPorHistoria(int idHistoria) {
        Arbol situaciones = new Arbol();
        try {
            String sql = "select * from situacion where HISTORIA_idHistoria = "+idHistoria+" order by id asc;";
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            int i = 0;
            while (rs.next()) {
                Situacion situacion = new Situacion();
                situacion.setId(rs.getInt("id"));
                situacion.setIdHistoria(rs.getInt("HISTORIA_idHistoria"));
                situacion.setPredecesor(rs.getInt("SITUACION_id"));
                situacion.setTitulo(rs.getString("titulo"));
                situacion.setTexto(rs.getString("texto"));
                situaciones.agregarNodo(situacion);
            }
            pat.close();
            rs.close();
            sql="select f.* from fin as f\n" +
            "inner join Situacion as s on s.id=f.SITUACION_id\n" +
            "where s.HISTORIA_idHistoria='"+idHistoria+"';";
            pat = conn.prepareStatement(sql);
            rs = pat.executeQuery();
            while(rs.next()){
                Final fin = new Final();
                fin.setId(rs.getInt("id"));
                fin.setIdHistoria(idHistoria);
                fin.setPredecesor(rs.getInt("SITUACION_id"));
                fin.setTexto(rs.getString("texto"));
                fin.setTitulo(rs.getString("titulo"));
                situaciones.agregarNodo(fin);
            }
            pat.close();
            rs.close();
                    
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return situaciones;
    }
    
}
