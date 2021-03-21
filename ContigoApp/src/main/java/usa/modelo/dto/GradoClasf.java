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
public class GradoClasf {
    /**
     * Default constructor
     */
    public GradoClasf() {
    }

    /**
     * 
     */
    private String codigo;

    /**
     * 
     */
    private int institucion_id;

    /**
     * 
     */
    private String clasificacion;
    /**
     * 
     */
    private int clasificacion_id;


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getInstitucion_id() {
        return institucion_id;
    }

    public void setInstitucion_id(int institucion_id) {
        this.institucion_id = institucion_id;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public int getClasificacion_id() {
        return clasificacion_id;
    }

    public void setClasificacion_id(int clasificacion_id) {
        this.clasificacion_id = clasificacion_id;
    }

}
