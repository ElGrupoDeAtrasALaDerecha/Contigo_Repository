package usa.modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import usa.modelo.dto.Notificacion;

/**
 * Objeto de acceso a datos de las notificaciones
 *
 * @author Santiago Pérez
 * @version 1.0
 * @since 2021-06-07
 */
public class NotificacionDao implements INotificacionDao {

    @Override
    public boolean crear(Notificacion n) {
        try {
            String sql = "insert into NOTIFICACION (titulo,texto,vista,persona_documento,fecha,tipo) \n"
                    + "values(?,?,?,?,sysdate());";
            PreparedStatement pat = conn.prepareStatement(sql);
            pat.setString(1, n.getTitulo());
            pat.setString(2, n.getTexto());
            pat.setBoolean(3, n.isVista());
            pat.setString(4, n.getDocumento());
            pat.setString(5, n.getTipo());
            pat.execute();
            pat.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(NotificacionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Notificacion consultar(String id) {
        Notificacion n = null;
        try {
            String sql = "select * from NOTIFICACION where id=1;";
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            if(rs.next()){
                n=new Notificacion();
                n.setId(rs.getString("id"));
                n.setTitulo(rs.getString("titulo"));
                n.setTexto(rs.getString("texto"));
                n.setVista(rs.getBoolean("vista"));
                n.setDocumento(rs.getString("documento"));
                n.setFecha(rs.getString("fecha"));
                n.setTipo(rs.getString("tipo"));
            }
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(NotificacionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    @Override
    public boolean actualizar(Notificacion n) {
        try {
            String sql= "update NOTIFICACION \n" +
                    "set vista=?\n" +
                    "where id=?;";
            PreparedStatement pat = conn.prepareStatement(sql);
            pat.setBoolean(1,n.isVista() );
            pat.setString(2, n.getId());
            pat.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(NotificacionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean eliminar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<Notificacion> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Método que permite consultar las notificaciones de una persona específica
     * @param documento que es el documento de una persona
     * @return una lista con las notificaciones de una persona con el documento
     */
    @Override
    public LinkedList<Notificacion> consultarNotificacionDe(String documento) {
        LinkedList <Notificacion> notificaciones= null;
        try {
            String sql= "select * from NOTIFICACION where persona_documento=\""+documento+"\""
                    + "order by vista asc, fecha desc;";
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            notificaciones=new LinkedList();
            while(rs.next()){
                Notificacion n = new Notificacion();
                n.setId(rs.getString("id"));
                n.setTitulo(rs.getString("titulo"));
                n.setTexto(rs.getString("texto"));
                n.setVista(rs.getBoolean("vista"));
                n.setDocumento(rs.getString("persona_documento"));
                n.setFecha(rs.getString("fecha"));
                n.setTipo(rs.getString("tipo"));
                notificaciones.add(n);
            }
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(NotificacionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return notificaciones;
    }

}
