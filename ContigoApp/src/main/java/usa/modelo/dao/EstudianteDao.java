package usa.modelo.dao;

import usa.modelo.dto.Estudiante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Clase de acceso a datos de estudiantes
 *
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres y Santiago Pérez
 * @since 2021-03-13
 */
public class EstudianteDao implements IDao<Estudiante> {

    private PreparedStatement pat;

    @Override
    public boolean crear(Estudiante estudiante) {
        try {
            Connection con = Conexion.tomarConexion();
            String sql = "insert into Estudiante (primerNombre,segundoNombre,....) values (?,?,?,?,?,?)";
            pat = con.prepareStatement(sql);
            pat.setString(1, estudiante.getPrimerNombre());
            pat.setString(2, estudiante.getSegundoNombre());
            pat.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Estudiante consultar(String id) {
        Estudiante estudiante = null;
        Connection conn = Conexion.tomarConexion();
        try {

            String sql = "select * from Estudiante where documento =\"" + id + "\"";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            estudiante = new Estudiante();
            while (rs.next()) {
                estudiante.setPrimerApellido("primerNombre");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);

        }
        return estudiante;
    }

    @Override
    public boolean actualizar(Estudiante t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<Estudiante> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Estudiante consultarPorToken(String token) {
        return new Estudiante();
    }

    public Estudiante consultarPorCredenciales(String documento, String contraseña) {
        Estudiante estudiante = null;
        Connection conn = Conexion.tomarConexion();
        try {

            String sql = "select p.* , e.GRADO_codigo from persona as p  \n"
                    + "inner join estudiante as e on e.PERSONA_documento =p.documento\n"
                    + "where p.documento = \"" + documento + "\"  and p.contraseña = sha(\"" + contraseña + "\");";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                estudiante = new Estudiante();
                estudiante.setPrimerApellido("primerNombre");
                estudiante.setDocumento(rs.getString("documento"));
                estudiante.setPrimerNombre(rs.getString("primerNombre"));
                estudiante.setSegundoNombre(rs.getString("segundoNombre"));
                estudiante.setPrimerApellido(rs.getString("primerApellido"));
                estudiante.setSegundoApellido(rs.getString("segundoApellido"));
                estudiante.setFechaDeNacimiento(rs.getDate("fechaNacimiento").toString());
                estudiante.setGenero(rs.getString("genero"));
                estudiante.setToken(rs.getString("token"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);

        }
        return estudiante;
    }

}
