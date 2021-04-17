package usa.modelo.dao;

import java.util.LinkedList;
import usa.modelo.dto.Historia;

/**
 *
 * @author Miguel Rippe, Santiago Cáceres, Laura Blanco y Santiago Pérez
 */
public class HistoriaDao implements IHistoriasDao{

    @Override
    public boolean crear(Historia t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Historia consultar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizar(Historia t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<Historia> listarTodos() {
        LinkedList<Historia> historia= new LinkedList();
        try {
            String sql = "select * from HISTORIA;";
            PreparedStatement pat = conn.prepareStatement(sql);
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
                estudiantes.add(estudiante);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return estudiantes;
    }
    
}
