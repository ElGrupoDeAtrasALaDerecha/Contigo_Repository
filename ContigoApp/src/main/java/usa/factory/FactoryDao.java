package usa.factory;

import usa.modelo.dao.IDao;
import usa.modelo.dao.PersonalCalificadoDao;


/**
 * Fábrica de DAO
 * @author Santiago Pérez
 */
public class FactoryDao {
    public static IDao obtenerDao(String nombreDao){
        IDao dao=null;
        if(nombreDao.equals("PersonalCalificadoDao")){
            dao=new PersonalCalificadoDao(); 
        }
        return dao;
    }
}
