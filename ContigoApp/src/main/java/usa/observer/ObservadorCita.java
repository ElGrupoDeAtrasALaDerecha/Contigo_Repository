package usa.observer;

import usa.adapter.CorreoCita;
import usa.adapter.CorreoProxy;
import usa.factory.AbstractFactory;
import usa.factory.Producer;
import usa.modelo.dao.IDao;
import usa.modelo.dto.Agenda;
import usa.modelo.dto.Cita;
import usa.modelo.dto.Estudiante;
import usa.modelo.dto.PersonalCalificado;

/**
 *
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres, Camila Fernández,
 * Andrés López, Natalia Montenegro, Santiago Pérez y Miguel Rippe
 */
public class ObservadorCita extends Observer {

    public ObservadorCita(Subject sujeto) {
        super(sujeto);
    }

    @Override
    public void actualizar() {
        mostrar();
    }

    @Override
    public void mostrar() {
        Cita cita = (Cita) sujeto;
        AbstractFactory factoryDao = Producer.getFabrica("DAO");
        IDao dao =null;
        Estudiante e=null;
        PersonalCalificado pc=null;
        CorreoProxy proxy = new CorreoProxy(new CorreoCita(cita));;
        switch (cita.getEstado()) {
            case 2:
                dao= (IDao) factoryDao.obtener("EstudianteDao");
                e = (Estudiante) dao.consultar(cita.getIdEstudiante());
                proxy= new CorreoProxy(new CorreoCita(cita));
                if (e.getCorreo() != null) {
                    proxy.enviarCorreo(e.getCorreo());
                }
                break;
            //Aquí va la cancelación de la cita por parte del estudiante
            case 4:
                dao = (IDao) factoryDao.obtener("AgendaDao");
                Agenda a=(Agenda) dao.consultar(String.valueOf(cita.getIdAgenda()));
                dao = (IDao) factoryDao.obtener("PersonalCalificadoDao");
                pc=(PersonalCalificado) dao.consultar(String.valueOf(a.getIdPersonal()));
                if (pc.getCorreo() != null) {
                    proxy.enviarCorreo(pc.getCorreo());
                }
                break;
            //Aquí va la cancelación de la cita por parte del personal calificado
            case 5:
                break;
            //Aquí va el estado de la cita perdida
            case 6:
                break;
            //Aquí va el estado de inasistencia del estudiante indicada por el personal calificado.
            case 7:
                break;
            //Aquí va el estado de inasistencia del personal calificado indicada por el estudiante.
            case 8:
                break;
            default:
                break;
        }


    }
}
