package usa.modelo.dao;

import usa.modelo.dto.Estudiante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Clase de acceso a datos de estudiantes
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres y Santiago Pérez
 * @since 2021-03-13
 */
public class EstudianteDao implements IDao<Estudiante>{

    private PreparedStatement pat;
    @Override
    public boolean crear(Estudiante estudiante) {
        try {
            Connection con=Conexion.tomarConexion();
            String sql="insert into Estudiante (primerNombre,segundoNombre,....) values (?,?,?,?,?,?)";
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
    
}
