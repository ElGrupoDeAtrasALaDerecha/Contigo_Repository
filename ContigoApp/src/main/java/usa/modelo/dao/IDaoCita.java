/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usa.modelo.dao;

import java.util.LinkedList;
import usa.modelo.dto.Cita;

/**
 *
 * @author Valeria
 */
public interface IDaoCita extends IDao<Cita> {

    Cita crearObjetoCita(int idAgenda, int horaInicio, String fechaInicio);

    int asignarHoraDia(int horainicio);

    String asignarFecha(String fechaInicio);

    boolean registroSucedidoEstudiante(Cita ci);

    LinkedList<Cita> listarHistorial(String documento);

    LinkedList<Cita> percaCita(String fecha, String hora);

    LinkedList<Cita> listarCitasPersonal(String id);

}
