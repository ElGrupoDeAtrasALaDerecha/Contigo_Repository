/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usa.modelo.dto;

/**
 *
 * @author andre
 */
public class EstadisticasBtnPanico {
    public EstadisticasBtnPanico () {}
    
    public int clikcs;
    public String fecha;
    public Estudiante estudiante;

    public int getClikcs() {
        return clikcs;
    }

    public void setClikcs(int clikcs) {
        this.clikcs = clikcs;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
    
}
