package usa.observer;

import usa.factory.AbstractFactory;
import usa.factory.Producer;
import usa.modelo.dao.IDao;
import usa.modelo.dto.Cita;
import usa.modelo.dto.Estudiante;
import usa.utils.Utils;

/**
 *
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres, Camila Fernández,
 * Andrés López, Natalia Montenegro, Santiago Pérez y Miguel Rippe
 */
public class ObservadorCita extends Observer{

    private String texto;
    public ObservadorCita(Subject sujeto) {
        super(sujeto);
    }

    @Override
    public void actualizar() {
        Cita cita = (Cita)sujeto;
        switch (cita.getEstado()) {
            case 2:
                texto="<h1>Confirmación de cita</h1>"
                        + "Ha confirmado su cita. \n"
                        + "Día: "+cita.getFecha()+"\n"
                        + "Hora: "+cita.getHoraInicio()+":00 \n"
                        + "Lugar: "+cita.getLugar()+"\n\n"
                        + "Se recomienda estar con 10 minutos de anticipación.";
                mostrar("confirmacionCita");
                break;
        //Aquí va la cancelación de la cita por parte del estudiante
            case 4:
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

    @Override
    public void mostrar(String params) {
        AbstractFactory factoryDao = Producer.getFabrica("DAO");
        IDao dao = (IDao) factoryDao.obtener("EstudianteDao");
        Cita cita = (Cita)sujeto;
        Estudiante e = (Estudiante) dao.consultar(cita.getIdEstudiante());
        Utils.enviarCorreoA(params,e.getCorreo(),texto);
    }
}
