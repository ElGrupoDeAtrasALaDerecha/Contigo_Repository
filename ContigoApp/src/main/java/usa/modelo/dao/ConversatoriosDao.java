package usa.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import usa.modelo.dto.Clasificacion;
import usa.modelo.dto.Conversatorio;
import usa.modelo.dto.EstudianteConversatorio;

/**
 *
 * Clase de Conversatorios
 *
 * @author Miguel Angel Rippe y Natalia Montenegro
 * @since 2021-03-13
 */
public class ConversatoriosDao implements IDaoConversatorios {
// SQL

    private PreparedStatement pat;

    /**
     *
     * @param conver
     * @return
     */
    @Override
    public int crearConver(Conversatorio conver) {
        try {
            String sql = "insert into CONVERSATORIO (PERSONAL_PERSONA_documento,titulo,cronograma,imagen,descripcion,lugar,infografia)values(?,?,?,?,?,?,?);";
            pat = conn.prepareStatement(sql);
            pat.setString(1, conver.getOrador());
            pat.setString(2, conver.getTitulo());
            pat.setString(3, conver.getCronograma());
            pat.setString(4, conver.getImagen());
            pat.setString(5, conver.getDescripcion());
            pat.setString(6, conver.getLugar());
            pat.setString(7, conver.getInfografia());
            pat.execute();
            pat.close();
            String sql2 = "select id from CONVERSATORIO order by id desc limit 1;";
            pat = conn.prepareStatement(sql2);
            ResultSet rs = pat.executeQuery();
            if (rs.next()) {
                int idConver = rs.getInt("id");
                rs.close();
                pat.close();
                return idConver;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int crearClasi(String conver, int resultado) {
        try {
            String sql = "insert into CLASIFICACION_has_CONVERSATORIO (CLASIFICACION_id,CONVERSATORIO_id) values (?,?);";
            pat = conn.prepareStatement(sql);
            pat.setString(1, conver);
            pat.setString(2, String.valueOf(resultado));
            pat.execute();
            pat.close();
            return 0;

        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 1;
    }

    /**
     *
     * @param id
     * @return
     */
    public LinkedList<Clasificacion> consultar(int id) {
        LinkedList<Clasificacion> clasificaciones = new LinkedList();
        try {
            String sql = "select * from CLASIFICACION_has_CONVERSATORIO where CLASIFICACION_id =\"" + id + "\"";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();

            while (rs.next()) {
                Clasificacion clasi = new Clasificacion();
                clasi.setId(rs.getInt("CLASIFICACION_id"));
                clasi.setIdConversatorio(rs.getInt("CONVERSATORIO_id"));
                clasificaciones.add(clasi);
            }
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConversatoriosDao.class.getName()).log(Level.SEVERE, null, ex);

        }
        return clasificaciones;
    }

    public LinkedList<Clasificacion> consultarClasificacionConver(int id) {
        LinkedList<Clasificacion> clasificaciones = new LinkedList();
        try {
            String sql = "select c.* from CLASIFICACION as c\n"
                    + "inner join CLASIFICACION_has_CONVERSATORIO as cc on cc.CLASIFICACION_id=c.id\n"
                    + "inner join CONVERSATORIO as co on co.id=cc.CONVERSATORIO_id\n"
                    + "where co.id =\"" + id + "\";";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();

            while (rs.next()) {
                Clasificacion clasi = new Clasificacion();
                clasi.setId(rs.getInt("id"));
                clasi.setGrado(rs.getString("grado"));
                clasificaciones.add(clasi);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConversatoriosDao.class.getName()).log(Level.SEVERE, null, ex);

        }
        return clasificaciones;
    }

    @Override
    public LinkedList<Conversatorio> listarTodos() {
        LinkedList<Conversatorio> conversatorios = new LinkedList();
        try {
            String sql = "select * from CONVERSATORIO";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                Conversatorio conversatorio = new Conversatorio();
                conversatorio.setCronograma(rs.getString("cronograma"));
                conversatorio.setOrador(rs.getString("PERSONAL_PERSONA_documento"));
                conversatorio.setTitulo(rs.getString("titulo"));
                conversatorio.setImagen(rs.getString("imagen"));
                conversatorio.setDescripcion(rs.getString("descripcion"));
                conversatorio.setId(rs.getInt("id"));
                conversatorio.setInfografia(rs.getString("infografia"));
                conversatorio.setLugar(rs.getString("lugar"));
                conversatorios.add(conversatorio);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConversatoriosDao.class.getName()).log(Level.SEVERE, null, ex);

        }
        return conversatorios;
    }

    @Override
    public boolean registrarEstuConver(EstudianteConversatorio estuconver) {
        try {
            String sql = "insert into ESTUDIANTE_has_CONVERSATORIO (CONVERSATORIO_id,ESTUDIANTE_PERSONA_documento) values (?,?);";
            pat = conn.prepareStatement(sql);
            pat.setInt(1, estuconver.getIdConversatorio());
            pat.setString(2, estuconver.getIdEstudiante());
            pat.execute();
            pat.close();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean eliminar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizar(Conversatorio t) {
        try {
            String sql = "UPDATE CONVERSATORIO SET PERSONAL_PERSONA_documento = ?,titulo= ?,cronograma = ? ,imagen = ?,descripcion = ?,lugar=?,infografia=? WHERE id=?";
            pat = conn.prepareStatement(sql);
            pat.setString(1, t.getOrador());
            pat.setString(2, t.getTitulo());
            pat.setString(3, t.getCronograma());
            pat.setString(4, t.getImagen());
            pat.setString(5, t.getDescripcion());
            pat.setString(6, t.getLugar());
            pat.setString(7, t.getInfografia());
            pat.setInt(8, t.getId());
            pat.execute();
            pat.close();
            String sql2 = "delete from CLASIFICACION_has_CONVERSATORIO where conversatorio_id =" + t.getId() + ";";
            pat = conn.prepareStatement(sql2);
            pat.execute();
            pat.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(InstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Conversatorio consultar(String id) {
        Conversatorio conver = null;
        try {
            String sql = "select * from CONVERSATORIO where id = " + id;
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                conver = new Conversatorio();
                conver.setId(rs.getInt("id"));
                conver.setOrador(rs.getString("PERSONAL_PERSONA_documento"));
                conver.setTitulo(rs.getString("titulo"));
                conver.setCronograma(rs.getString("cronograma"));
                conver.setImagen(rs.getString("imagen"));
                conver.setDescripcion(rs.getString("descripcion"));
                conver.setLugar(rs.getString("lugar"));
                conver.setInfografia(rs.getString("infografia"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conver;
    }

    @Override
    public boolean crear(Conversatorio t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<JSONArray> consultarPorGrado(String grado) {
        LinkedList<JSONArray> datos = null;
        try {
            String sql = "select C.titulo, count(EC.ESTUDIANTE_PERSONA_documento) as Inscritos from  ESTUDIANTE_has_CONVERSATORIO as EC\n"
                    + "inner join ESTUDIANTE as E on EC.ESTUDIANTE_PERSONA_documento = E.PERSONA_documento\n"
                    + "inner join GRADO as G on E.GRADO_codigo = G.codigo\n"
                    + "right join CONVERSATORIO as C on EC.CONVERSATORIO_id = C.id \n"
                    + "where G.codigo = \'" + grado + "\' \n"
                    + "group by C.id\n"
                    + "order by Inscritos desc\n"
                    + "limit 5";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            datos = new LinkedList();
            datos.add(new JSONArray());
            datos.add(new JSONArray());
            while (rs.next()) {
                datos.get(0).put(rs.getString("titulo"));
                datos.get(1).put(rs.getString("Inscritos"));
            }
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConversatoriosDao.class.getName()).log(Level.SEVERE, null, ex);

        }
        return datos;
    }

    @Override
    public LinkedList<JSONArray> consultarPorInstitucion(String institucion) {
        LinkedList<JSONArray> datos = null;
        try {
            String sql = "select C.titulo, count(EC.ESTUDIANTE_PERSONA_documento) as Inscritos from  ESTUDIANTE_has_CONVERSATORIO as EC\n"
                    + "right join CONVERSATORIO as C on EC.CONVERSATORIO_id = C.id \n"
                    + "inner join ESTUDIANTE as E on EC.ESTUDIANTE_PERSONA_documento = E.PERSONA_documento\n"
                    + "inner join GRADO as G on E.GRADO_codigo = G.codigo\n"
                    + "inner join INSTITUCION as I on G.INSTITUCION_id = I.id\n"
                    + "where I.id = " + institucion + " \n"
                    + "group by C.id\n"
                    + "order by Inscritos desc\n"
                    + "limit 5";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            datos = new LinkedList();
            datos.add(new JSONArray());
            datos.add(new JSONArray());
            int i = 0;
            while (rs.next()) {
                datos.get(0).put(rs.getString("titulo"));
                datos.get(1).put(rs.getString("Inscritos"));
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConversatoriosDao.class.getName()).log(Level.SEVERE, null, ex);

        }
        return datos;

    }

    @Override
    public boolean eliminarRegistroEstu(String idConversatorio, String idEstudiante) {
        try {
            String sql = "delete from ESTUDIANTE_has_CONVERSATORIO where CONVERSATORIO_id= " + idConversatorio + " and ESTUDIANTE_PERSONA_documento=" + idEstudiante;
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
    public EstudianteConversatorio consultarEstConversatorio(String idConversatorio, String idEstudiante) {
        EstudianteConversatorio conver = null;
        try {
            String sql = "select * from ESTUDIANTE_has_CONVERSATORIO where CONVERSATORIO_id = " + idConversatorio + " and ESTUDIANTE_PERSONA_documento='" + idEstudiante + "';";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                conver = new EstudianteConversatorio();
                conver.setIdConversatorio(rs.getInt("CONVERSATORIO_id"));
                conver.setIdEstudiante(rs.getString("ESTUDIANTE_PERSONA_documento"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conver;
    }

}
