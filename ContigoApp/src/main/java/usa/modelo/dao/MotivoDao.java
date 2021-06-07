/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usa.modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import usa.modelo.dto.Motivo;
/**/
/**
 *
 * @author Valeria
 */
public class MotivoDao implements IDao<Motivo> {

    PreparedStatement pat;
    Statement stmt;
    ResultSet result;

    @Override
    public boolean crear(Motivo t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Motivo consultar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizar(Motivo t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<Motivo> listarTodos() {
        LinkedList<Motivo> motivos =new LinkedList<Motivo>();
        String sql ="select * from MOTIVO;";
        try {
            pat =conn.prepareStatement(sql);
            result = pat.executeQuery();
            while (result.next()){
                Motivo motivo=new Motivo();
                motivo.setMotivo(result.getString("id"));
                motivos.add(motivo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MotivoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return motivos;
    }

}
