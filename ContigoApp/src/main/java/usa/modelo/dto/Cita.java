/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usa.modelo.dto;

import usa.observer.Subject;

/**
 *
 * @author Valeria
 */
public class Cita extends Subject{

    private int id;
    private int idAgenda;
    private String idEstudiante;
    private int horaInicio;
    private String fecha;
    private String lugar;
    private String motivo;
    private String recomendaciones;
    private String id_perca;
    private String nombre_perca;
    private String imagen;

    public Cita() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(int idAgenda) {
        this.idAgenda = idAgenda;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public int getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(int horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }
    
    public String getId_perca() {
        return id_perca;
    }

    public void setId_perca(String id_perca) {
        this.id_perca = id_perca;
    }

    public String getNombre_perca() {
        return nombre_perca;
    }

    public void setNombre_perca(String nombre_perca) {
        this.nombre_perca = nombre_perca;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
    
}
