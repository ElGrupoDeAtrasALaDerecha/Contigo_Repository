package usa.factory;

import usa.modelo.dao.ClasificacionDao;
import usa.modelo.dao.MunicipioDao;
import usa.modelo.dao.PersonalCalificadoDao;
import usa.modelo.dao.DepartamentoDao;
import usa.modelo.dao.EstudianteDao;
import usa.modelo.dao.GradoDao;
import usa.modelo.dao.IDao;
import usa.modelo.dao.ConversatoriosDao;
import usa.modelo.dao.HistoriaDao;
import usa.modelo.dao.SituacionDao;
import usa.modelo.dao.EstadisticasBtnPanicoDao;
import usa.modelo.dao.TipoDocumentoDao;

/**
 * Fábrica de DAO
 * @author ALL TEAM
 */
public class FactoryDao implements AbstractFactory<IDao>{
    @Override
    public IDao obtener(String nombreDao){
        IDao dao=null;    
        if(nombreDao.equals("ClasificacionDao")){
            dao = new ClasificacionDao(); 
        }
        else if(nombreDao.equals("MunicipioDao")){
            dao = new MunicipioDao();
        }
        else if(nombreDao.equals("DepartamentoDao")){
            dao=new DepartamentoDao(); 
        }
        else if(nombreDao.equals("EstudianteDao")){
            dao=new EstudianteDao(); 
        }
        else if(nombreDao.equals("ConversatoriosDao")){
            dao =  new ConversatoriosDao();
        }
        else if(nombreDao.equals("TipoDocumentoDao")){
            dao = new TipoDocumentoDao(); 
        } 
        else if(nombreDao.equals("PersonalCalificadoDao")){
            dao=new PersonalCalificadoDao(); 
        }
        else if (nombreDao.equals("GradoDao")){
            dao=new GradoDao();
<<<<<<< HEAD
        }else if (nombreDao.equals("HistoriaDao")){
            dao=new HistoriaDao();
        } 
        else if (nombreDao.equals("SituacionDao")){
            dao=new SituacionDao();
        } 
=======
        }
        else if (nombreDao.equals("EstadisticasBtnPanicoDao")){
            dao=new EstadisticasBtnPanicoDao();
        }
>>>>>>> estadisticas
        return dao;
    }
   
    
}
