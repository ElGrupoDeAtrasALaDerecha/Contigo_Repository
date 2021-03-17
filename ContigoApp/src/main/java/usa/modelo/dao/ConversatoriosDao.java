/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usa.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import usa.modelo.dto.Conversatorio;

/**
 *
 * Clase de Conversatorios
 *
 * @author Miguel Angel Rippe y Natalia Montenegro
 * @since 2021-03-13
 */
public class ConversatoriosDao implements IDao<Conversatorio> {
// SQL

    private PreparedStatement pat;

    @Override
    public boolean crear(Conversatorio conver) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Conversatorio consultar(String id) {
        Conversatorio conver = null;
        Connection conn = Conexion.tomarConexion();
        try {
            String sql = "select * from Conversatorio where id =\"" + id + "\"";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            conver = new Conversatorio();
            while (rs.next()) {

            }
        } catch (SQLException ex) {
            Logger.getLogger(ConversatoriosDao.class.getName()).log(Level.SEVERE, null, ex);

        }
        return conver;
    }

    @Override
    public boolean actualizar(Conversatorio t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<Conversatorio> listarTodos() {
        LinkedList<Conversatorio> conversatorios = new LinkedList();
        Connection conn = Conexion.tomarConexion();
        try {
            String sql = "select * from Conversatorio";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                Conversatorio conversatorio = new Conversatorio();
                conversatorio.setCronograma(rs.getString("cronograma"));
                conversatorio.setOrador(rs.getInt("PERSONAL_PERSONA_documento"));
                conversatorio.setTitulo(rs.getString("titulo"));
               
                conversatorios.add(conversatorio);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConversatoriosDao.class.getName()).log(Level.SEVERE, null, ex);

        }
        return conversatorios;
    }
}
