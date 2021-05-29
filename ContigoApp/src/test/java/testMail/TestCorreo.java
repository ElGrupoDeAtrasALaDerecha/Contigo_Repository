package testMail;

import usa.adapter.CorreoConversatorio;
import usa.adapter.CorreoInscripcion;
import usa.adapter.CorreoProxy;
import usa.factory.AbstractFactory;
import usa.factory.Producer;
import usa.modelo.dao.IDao;
import usa.modelo.dto.Estudiante;
import usa.modelo.dto.EstudianteConversatorio;
import usa.observer.ObservadorEstudianteConversatorio;

public class TestCorreo {

    public static void main(String[] args) {
        AbstractFactory factoryDao = Producer.getFabrica("DAO");
        IDao dao =null;
        Estudiante e=null;
        EstudianteConversatorio estudianteTieneConversatorio = new EstudianteConversatorio();
        estudianteTieneConversatorio.setIdConversatorio(1);
        estudianteTieneConversatorio.setIdEstudiante("123456789");
        dao= (IDao) factoryDao.obtener("EstudianteDao");
        e=(Estudiante) dao.consultar(estudianteTieneConversatorio.getIdEstudiante());
        ObservadorEstudianteConversatorio observador = new ObservadorEstudianteConversatorio(estudianteTieneConversatorio);
        estudianteTieneConversatorio.setEstado(2);
        
    }
}
