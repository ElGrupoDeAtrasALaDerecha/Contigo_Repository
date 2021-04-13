package usa.factory;

import usa.modelo.dao.DepartamentoDao;
import usa.modelo.dao.EstudianteDao;
import usa.modelo.dao.IDao;
import usa.modelo.dao.ConversatoriosDao;
import usa.modelo.dao.IDaoConversatorios;
import usa.modelo.dao.TipoDocumentoDao;



/**
 * Fábrica de DAO
 * @author Santiago Pérez
 */
public class FactoryDao {
    public static IDao obtenerDao(String nombreDao){
        IDao dao=null;
      
        if(nombreDao.equals("DepartamentoDao")){
            dao=new DepartamentoDao(); 
        }
        if(nombreDao.equals("EstudianteDao")){
            dao=new EstudianteDao(); 
        if(nombreDao.equals("ConversatoriosDao")){
            dao =  new ConversatoriosDao();
        }
        if(nombreDao.equals("TipoDocumento")){
            dao = new TipoDocumentoDao(); 
        }
        return dao;
    }
}
