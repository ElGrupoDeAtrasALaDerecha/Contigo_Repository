package usa.modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static usa.modelo.dao.IDao.conn;
import usa.modelo.dto.ClasificacionHasHistoria;
import usa.modelo.dto.GradoClasf;
import usa.modelo.dto.Historia;

/**
 * Clase de objeto de acceso a datos de las historias
 *
 * @author Miguel Rippe, Santiago Cáceres, Laura Blanco y Santiago Pérez
 * @version 1.1
 * @since 2021-06-03
 */
public class HistoriaDao implements IHistoriasDao {

    PreparedStatement pat;
    ResultSet result;

    @Override
    public boolean crear(Historia his) {
        try {
            String sql = "insert into  HISTORIA (PERSONAL_PERSONA_documento,titulo,descripcion,urlImagen) "
                    + "values (?,?,?,?)";
            pat = conn.prepareStatement(sql);
            pat.setString(1, his.getDocumentoCreador());
            pat.setString(2, his.getTitulo());
            pat.setString(3, his.getDescripcion());
            pat.setString(4, his.getUrlImagen());
            pat.execute();
            pat.close();
            sql = "select idHistoria from HISTORIA order by idHistoria desc limit 1;";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                his.setId(rs.getInt("idHistoria"));
            }
            rs.close();
            pat.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Historia consultar(String id) {
        Historia his = null;
        try {
            String sql = "select * from HISTORIA where idHistoria =\"" + id + "\"";
            pat = conn.prepareStatement(sql);
            result = pat.executeQuery();
            while (result.next()) {
                his = new Historia();
                his.setId(result.getInt("idHistoria"));
                his.setDocumentoCreador(result.getString("PERSONAL_PERSONA_documento"));
                his.setTitulo(result.getString("titulo"));
                his.setDescripcion(result.getString("descripcion"));
                his.setUrlImagen(result.getString("urlImagen"));
                return his;
            }
        } catch (SQLException ex) {
            Logger.getLogger(HistoriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return his;
    }

    @Override
    public boolean actualizar(Historia t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(String id) {
        try {
            String sql = "delete from CLASIFICACION_has_HISTORIA where Historia_idHistoria=" + id + ";";
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
    public LinkedList<Historia> listarTodos() {
        LinkedList<Historia> historias = new LinkedList();
        try {
            String sql = "select * from HISTORIA;";
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                Historia historia = new Historia();
                historia.setTitulo(rs.getString("titulo"));
                historia.setId(rs.getInt("idHistoria"));
                historia.setDocumentoCreador(rs.getString("PERSONAL_PERSONA_documento"));
                historia.setDescripcion(rs.getString("descripcion"));
                historia.setUrlImagen(rs.getString("urlImagen"));
                historias.add(historia);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return historias;
    }

    @Override
    public int crearClasi(String his, int resultado) {
        try {
            String sql = "insert into CLASIFICACION_has_HISTORIA (CLASIFICACION_id,HISTORIA_idHistoria) values (?,?);";
            pat = conn.prepareStatement(sql);
            pat.setString(1, String.valueOf(resultado));
            pat.setString(2, his);
            pat.execute();
            pat.close();
            return 0;

        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 1;
    }

    @Override
    public int crearhistoria(Historia his) {
        try {
            String sql = "insert into  HISTORIA (PERSONAL_PERSONA_documento,titulo,descripcion,urlImagen) "
                    + "values (?,?,?,?)";
            pat = conn.prepareStatement(sql);
            pat.setString(1, his.getDocumentoCreador());
            pat.setString(2, his.getTitulo());
            pat.setString(3, his.getDescripcion());
            pat.setString(4, his.getUrlImagen());
            pat.execute();
            pat.close();
            sql = "select idHistoria from HISTORIA order by idHistoria desc limit 1;";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                his.setId(rs.getInt("idHistoria"));
                pat.close();
                return 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public LinkedList<Historia> consultarHistoriasDeEstudiante(String documento) {
        LinkedList<Historia> historias = new LinkedList();
        try {
            
            String sql = "select h.* from ESTUDIANTE_has_HISTORIA as eh\n" +
                            "inner join ESTUDIANTE as e on e.PERSONA_documento=eh.ESTUDIANTE_PERSONA_documento\n" +
                            "inner join HISTORIA as h on h.idHistoria=eh.HISTORIA_idHistoria\n" +
                            "where e.PERSONA_documento=\""+documento+"\";";
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                Historia historia = new Historia();
                historia.setTitulo(rs.getString("titulo"));
                historia.setId(rs.getInt("idHistoria"));
                historia.setDocumentoCreador(rs.getString("PERSONAL_PERSONA_documento"));
                historia.setDescripcion(rs.getString("descripcion"));
                historia.setUrlImagen(rs.getString("urlImagen"));
                historias.add(historia);
            }
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(HistoriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return historias;
    }

    @Override
    public boolean tieneHistorias(String documento) {
        boolean tieneHistoria = false;
        try {
            String sql = "select count(*)>1 as tieneHistoria from HISTORIA \n"
                    + "where PERSONAL_PERSONA_documento =\"" + documento + "\"";
            pat = conn.prepareStatement(sql);
            result = pat.executeQuery();
            if (result.next()) {
                tieneHistoria = result.getBoolean("tieneHistoria");
            }
            result.close();
            pat.close();

        } catch (SQLException ex) {
            Logger.getLogger(HistoriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tieneHistoria;
    }

    public LinkedList<ClasificacionHasHistoria> consultarClasificacionHistoria(String id) {
        LinkedList<ClasificacionHasHistoria> clasificaciones = new LinkedList();
        try {
            String sql = "select c.* from CLASIFICACION as c\n"
                    + "inner join CLASIFICACION_has_HISTORIA as cc on cc.CLASIFICACION_id = c.id\n"
                    + "inner join HISTORIA as his on his.idHistoria = cc.HISTORIA_idHistoria\n"
                    + "where his.idHistoria =\"" + id + "\";";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();

            while (rs.next()) {
                ClasificacionHasHistoria clasi = new ClasificacionHasHistoria();
                clasi.setId(rs.getInt("id"));
                clasi.setCodigo(rs.getString("grado"));
                clasi.setIdHistoria(id);
                clasificaciones.add(clasi);
            }
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(HistoriaDao.class.getName()).log(Level.SEVERE, null, ex);

        }
        return clasificaciones;
    }

    @Override
    public boolean asignarGradoHistoria(String id) {
        return true;
    }

    @Override
    public LinkedList<GradoClasf> consultarGradosInstitucion(String id_ins) {
        LinkedList<GradoClasf> clasifi = new LinkedList();
        try {
            String sql = "select codigo from grado where INSTITUCION_id =\"" + id_ins + "\";";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while(rs.next()){
                GradoClasf clasificacioneGrados = new GradoClasf();
                clasificacioneGrados.setCodigo(rs.getString("codigo"));
                clasifi.add(clasificacioneGrados);
            }
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(HistoriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clasifi;
    }
}
