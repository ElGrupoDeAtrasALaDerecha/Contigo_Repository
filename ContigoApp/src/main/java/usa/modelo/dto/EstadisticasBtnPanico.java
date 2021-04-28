/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usa.modelo.dto;

import java.util.LinkedList;

/**
 *
 * @author andre
 */
public class EstadisticasBtnPanico {
    public EstadisticasBtnPanico () {}
    
    public int clikcs;
    public LinkedList<String> fechas = new LinkedList<String>();
    public String estudiante;

    public int getClikcs() {
        return clikcs;
    }

    public void setClikcs(int clikcs) {
        this.clikcs = clikcs;
    }

    public LinkedList<String> getFechas() {
        return fechas;
    }

    public void setFechas(LinkedList<String> fechas) {
        this.fechas = fechas;
    }
    
    public String getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(String estudiante) {
        this.estudiante = estudiante;
    }
    
}
