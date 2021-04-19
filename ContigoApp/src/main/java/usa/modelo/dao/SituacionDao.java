package usa.modelo.dao;

import java.util.LinkedList;
import usa.modelo.dto.Situacion;

/**
 * Clase de objetos de acceso a datos de las situaciones
 * @author Miguel Rippe, Santiago Cáceres, Laura Blanco y Santiago Pérez
 */
public class SituacionDao implements ISituacionDao{

    @Override
    public boolean crear(Situacion situacion) {
        
        return false;
    }

    @Override
    public Situacion consultar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizar(Situacion t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<Situacion> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<Situacion> consultarPorHistoria(int idHistoria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
