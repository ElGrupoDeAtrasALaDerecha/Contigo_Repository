package usa.modelo.dao;

import java.util.LinkedList;
import org.json.JSONObject;
import usa.modelo.dto.Cita;

/**
 * Interfaz de objeto de acceso a datos de las citas
 * @author Valeria Bermúdez y Santiago Pérez
 * @version 1.1
 * @since 2021-06-03
 */
public interface IDaoCita extends IDao<Cita> {

    Cita crearObjetoCita(int idAgenda, int horaInicio, String fechaInicio);

    int asignarHoraDia(int horainicio);

    String asignarFecha(String fechaInicio);

    boolean registroSucedidoEstudiante(Cita ci);

    LinkedList<Cita> listarHistorial(String documento);

    LinkedList<Cita> percaCita(String fecha, String hora);

    LinkedList<Cita> listarCitasPersonal(String id);
    
    JSONObject ultimasCitasEstudiante(String documento);
}
