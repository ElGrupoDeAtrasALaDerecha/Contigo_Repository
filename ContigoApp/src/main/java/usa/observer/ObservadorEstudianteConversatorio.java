package usa.observer;

import usa.adapter.CorreoConversatorio;
import usa.adapter.CorreoProxy;
import usa.factory.AbstractFactory;
import usa.factory.Producer;
import usa.modelo.dao.IDao;
import usa.modelo.dto.Estudiante;
import usa.modelo.dto.EstudianteConversatorio;

/**
 *
 * @author Santiago Pérez
 */
public class ObservadorEstudianteConversatorio extends Observer{

    public ObservadorEstudianteConversatorio(Subject sujeto) {
        super(sujeto);
    }

    @Override
    public void actualizar() {
        mostrar();
    }

    @Override
    public void mostrar() {
        AbstractFactory factoryDao = Producer.getFabrica("DAO");
        IDao dao =null;
        Estudiante e=null;
        EstudianteConversatorio estudianteTieneConversatorio = (EstudianteConversatorio) sujeto;
        dao= (IDao) factoryDao.obtener("EstudianteDao");
        e=(Estudiante) dao.consultar(estudianteTieneConversatorio.getIdEstudiante());
        CorreoProxy proxy = new CorreoProxy(new CorreoConversatorio(estudianteTieneConversatorio));
        proxy.enviarCorreo(e.getCorreo());        
    }
    
}
