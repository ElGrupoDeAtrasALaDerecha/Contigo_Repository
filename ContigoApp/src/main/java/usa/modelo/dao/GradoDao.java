package usa.modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import usa.modelo.dto.Grado;
import usa.modelo.dto.GradoClasf;
import usa.utils.GeneradorCodigos;
import usa.utils.Utils;

/**
 *
 * @author Andrés C. López R.
 */
public class GradoDao implements IGradoDao {

    private PreparedStatement pat;
    @Override
    public boolean crear(Grado grado) {
        if (consultar(String.valueOf(grado.getClasificacion_id())) == null) {
            try {
                String sql = "insert into GRADO (codigo,CLASIFICACION_id,INSTITUCION_id) values (?,?,?)";
                pat = conn.prepareStatement(sql);
                pat.setString(1, Utils.crearCodigoCurso());
                pat.setInt(2, grado.getClasificacion_id());
                pat.setInt(3, grado.getInstitucion_id());
                pat.execute();
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }          
        return false;
    }

    @Override
    public Grado consultar(String id) {
        Grado grado = null;
        try {
            String sql = "select * from GRADO where CLASIFICACION_id = " + id;
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                grado = new Grado();
                grado.setCodigo(rs.getString("codigo"));
                grado.setInstitucion_id(rs.getInt("INSTITUCION_id"));
                grado.setClasificacion_id(rs.getInt("CLASIFICACION_id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return grado;
    }

    @Override
    public boolean actualizar(Grado t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
       
    @Override
    public LinkedList<Grado> listarTodos() {
        LinkedList<Grado> grados = new LinkedList<Grado>();      
        Grado grado = null;
        try {
            String sql = "select * from GRADO";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                grado = new Grado();
                grado.setCodigo(rs.getString("codigo"));
                grado.setInstitucion_id(rs.getInt("INSTITUCION_id"));
                grado.setClasificacion_id(rs.getInt("CLASIFICACION_id"));
                grados.add(grado);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return grados;
    }
    
    public LinkedList<GradoClasf> listarGradosClasf() {
        LinkedList<GradoClasf> grados = new LinkedList<GradoClasf>();
        GradoClasf grado = null;
        try {
            String sql = "select CLASIFICACION.grado, GRADO.* from GRADO, CLASIFICACION where CLASIFICACION.id = GRADO.CLASIFICACION_id order by GRADO.CLASIFICACION_id asc;";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                grado = new GradoClasf();
                grado.setCodigo(rs.getString("codigo"));
                grado.setInstitucion_id(rs.getInt("INSTITUCION_id"));
                grado.setClasificacion_id(rs.getInt("CLASIFICACION_id"));
                grado.setClasificacion(rs.getString("grado"));
                grados.add(grado);
            }
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return grados;
    }

    @Override
    public JSONArray consultarBtnPorGrado(String grado) {
        JSONArray arregloBtnE=new JSONArray();
        try {
            String sql = "select q1.totalE as \"total\", q2.totalEP as \"si\", q1.totalE-q2.totalEP as \"no\" from \n" +
            "(select count(*) as totalE from estudiante\n" +
            "where GRADO_codigo=\'"+grado+"\') as q1, \n" +
            "(select count(p.documento) as totalEP from Persona as p\n" +
            "inner join Estudiante as e on e.PERSONA_documento=p.documento\n" +
            "inner join GRADO as g on g.codigo=e.GRADO_codigo\n" +
            "where g.codigo=\'"+grado+"\' and p.documento in (select distinct ESTUDIANTE_PERSONA_documento from estadisticas_btnpanico)\n" +
            ") as q2;";
            pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                arregloBtnE.put(rs.getInt("total"));
                arregloBtnE.put(rs.getInt("si"));
                arregloBtnE.put(rs.getInt("no"));
            }
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arregloBtnE;
    }
}
