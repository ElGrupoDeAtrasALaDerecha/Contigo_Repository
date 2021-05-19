package usa.factory;

import java.util.logging.Level;
import java.util.logging.Logger;
import usa.modelo.dao.IDao;

/**
 * Fábrica de DAO
 * @author ALL TEAM
 */
public class FactoryDao implements AbstractFactory<IDao>{
    @Override
    public IDao obtener(String nombreDao){
        IDao dao=null;    
        try {
            dao = (IDao) Class.forName("usa.modelo.dao."+nombreDao).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(FactoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dao;
    }
   
    
}
