package usa.modelo.dao;

import java.sql.CallableStatement;
import usa.modelo.dto.Estudiante;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import usa.utils.Utils;
/**
 * Clase de acceso a datos de estudiantes
 *
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres y Santiago Pérez
 * @since 2021-03-13
 */
public class EstudianteDao implements IDaoEstudiante {

    private PreparedStatement pat;
    private ResultSet rs;

    @Override
    public boolean crear(Estudiante estudiante) {
        try {
            String sql = "call insertarEstudiante(?,?,?,?,?,?,?,?,?,?,?,?)";
            CallableStatement call = conn.prepareCall(sql);
            call.setString("_documento", estudiante.getDocumento());
            call.setInt("_TIPO_DOCUMENTO_ID", estudiante.getTipoDocumento());
            call.setString("_primerNombre", estudiante.getPrimerNombre());
            call.setString("_segundoNombre", estudiante.getSegundoNombre());
            call.setString("_primerApellido", estudiante.getPrimerApellido());
            call.setString("_segundoApellido", estudiante.getSegundoApellido());
            call.setString("_token", Utils.generateNewToken());
            call.setString("_fechaNacimiento", estudiante.getFechaDeNacimiento());
            call.setString("_genero", estudiante.getGenero());
            call.setString("_contraseña", estudiante.getContraseña());
            call.setString("_GRADO_codigo", estudiante.getGrado());
            call.setString("_correo",estudiante.getCorreo());
            call.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Estudiante consultar(String id) {
        Estudiante estudiante = null;
        try {
            String sql = "select p.* , e.GRADO_codigo from persona as p  \n"
                    + "inner join estudiante as e on e.PERSONA_documento =p.documento\n where PERSONA_documento =\"" + id + "\"";
            pat = conn.prepareStatement(sql);
            rs = pat.executeQuery();
            while (rs.next()) {
                estudiante = new Estudiante();
                estudiante.setPrimerApellido("p.primerNombre");
                estudiante.setDocumento(rs.getString("p.documento"));
                estudiante.setPrimerNombre(rs.getString("p.primerNombre"));
                estudiante.setSegundoNombre(rs.getString("p.segundoNombre"));
                estudiante.setPrimerApellido(rs.getString("p.primerApellido"));
                estudiante.setSegundoApellido(rs.getString("p.segundoApellido"));
                estudiante.setFechaDeNacimiento(rs.getDate("p.fechaNacimiento").toString());
                estudiante.setGenero(rs.getString("p.genero"));
                estudiante.setGrado(rs.getString("GRADO_codigo"));
                estudiante.setCorreo(rs.getString("correo"));
            }
            rs.close();
            pat.close();
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
        LinkedList<Estudiante> estudiantes = new LinkedList();
        try {
            String sql = "select p.* , e.GRADO_codigo from persona as p  \n"
                    + "inner join estudiante as e on e.PERSONA_documento =p.documento\n";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                Estudiante estudiante = new Estudiante();
                estudiante.setPrimerApellido("p.primerNombre");
                estudiante.setDocumento(rs.getString("p.documento"));
                estudiante.setPrimerNombre(rs.getString("p.primerNombre"));
                estudiante.setSegundoNombre(rs.getString("p.segundoNombre"));
                estudiante.setPrimerApellido(rs.getString("p.primerApellido"));
                estudiante.setSegundoApellido(rs.getString("p.segundoApellido"));
                estudiante.setFechaDeNacimiento(rs.getDate("p.fechaNacimiento").toString());
                estudiante.setGenero(rs.getString("p.genero"));
                estudiante.setCorreo(rs.getString("correo"));
                estudiantes.add(estudiante);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return estudiantes;
    }
    @Override
    public Estudiante consultarPorToken(String token) {
        Estudiante estudiante = null;
        try {

            String sql = "select p.* , e.GRADO_codigo from persona as p  \n"
                    + "inner join estudiante as e on e.PERSONA_documento =p.documento\n"
                    + "where p.token = '" + token + "';";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                estudiante = new Estudiante();
                estudiante.setPrimerApellido("p.primerNombre");
                estudiante.setDocumento(rs.getString("p.documento"));
                estudiante.setPrimerNombre(rs.getString("p.primerNombre"));
                estudiante.setSegundoNombre(rs.getString("p.segundoNombre"));
                estudiante.setPrimerApellido(rs.getString("p.primerApellido"));
                estudiante.setSegundoApellido(rs.getString("p.segundoApellido"));
                estudiante.setFechaDeNacimiento(rs.getDate("p.fechaNacimiento").toString());
                estudiante.setGenero(rs.getString("p.genero"));
                estudiante.setCorreo(rs.getString("correo"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);

        }
        return estudiante;
    }
    @Override
    public Estudiante consultarPorTokenGrado(String id) {
        Estudiante estudiante = null;
        try {
            String sql = "select * from Estudiante where PERSONA_documento =\"" + id + "\"";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            estudiante = new Estudiante();
            while (rs.next()) {
                estudiante.setGrado(rs.getString("GRADO_codigo"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);

        }
        return estudiante;
    }

    public Estudiante consultarPorCredenciales(String documento, String contraseña) {
        Estudiante estudiante = null;
        try {

            String sql = "select p.* , e.GRADO_codigo from persona as p  \n"
                    + "inner join estudiante as e on e.PERSONA_documento =p.documento\n"
                    + "where p.documento = \"" + documento + "\"  and p.contraseña = sha(\"" + contraseña + "\");";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                estudiante = new Estudiante();
                estudiante.setPrimerApellido("p.primerNombre");
                estudiante.setDocumento(rs.getString("p.documento"));
                estudiante.setPrimerNombre(rs.getString("p.primerNombre"));
                estudiante.setSegundoNombre(rs.getString("p.segundoNombre"));
                estudiante.setPrimerApellido(rs.getString("p.primerApellido"));
                estudiante.setSegundoApellido(rs.getString("p.segundoApellido"));
                estudiante.setFechaDeNacimiento(rs.getDate("p.fechaNacimiento").toString());
                estudiante.setGenero(rs.getString("p.genero"));
                estudiante.setToken(rs.getString("p.token"));
                estudiante.setCorreo(rs.getString("correo"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);

        }
        return estudiante;
    }

    @Override
    public LinkedList<Estudiante> listarGradosEstudiante(String id) {
        LinkedList<Estudiante> estudiantes = new LinkedList<>();
        String sql = "select p.documento, p.primerNombre, p.segundoNombre, p.primerApellido, p.segundoApellido, c.grado \n" +
        "from persona as p \n" +
        "inner join Estudiante as e on e.PERSONA_documento= p.documento\n" +
        "inner join Grado as g  on e.Grado_codigo=g.codigo\n" +
        "inner join clasificacion as c on c.id=g.CLASIFICACION_id\n" +
        "where c.id = '"+id+"';";
        try {
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();

            while (rs.next()) {
                Estudiante estudi = new Estudiante();
                estudi.setDocumento(rs.getString("documento"));
                estudi.setPrimerNombre(rs.getString("primerNombre"));
                estudi.setSegundoNombre(rs.getString("segundoNombre"));
                estudi.setPrimerApellido(rs.getString("primerApellido"));
                estudi.setSegundoApellido(rs.getString("segundoApellido"));
                estudi.setGrado(rs.getString("grado"));
                estudi.setCorreo(rs.getString("correo"));
                estudiantes.add(estudi);
            }
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(PersonalCalificadoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estudiantes; //To change body of generated methods, choose Tools | Templates.
    }
}
