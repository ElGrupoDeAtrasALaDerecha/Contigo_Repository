package usa.factory;

import usa.modelo.dao.ClasificacionDao;
import usa.modelo.dao.IDao;
import usa.modelo.dao.MunicipioDao;
import usa.modelo.dao.PersonalCalificadoDao;


/**
 * Fábrica de DAO
 * @author VAleria Bermúdez
 */
public class FactoryDao {
    public static IDao obtenerDao(String nombreDao){
        IDao dao=null;
                
        if(nombreDao.equals("ClasificacionDao")){
            dao = new ClasificacionDao(); 
        }
        
        else if(nombreDao.equals("MunicipioDao")){
            dao = new MunicipioDao();
        }

        return dao;
    }
    
    
}
