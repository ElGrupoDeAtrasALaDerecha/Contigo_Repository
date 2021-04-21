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
public class Clasificacion {
    
    public Clasificacion (){
    }
    
    private int id;
    private String codigo; 

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getIdConversatorio() {
        return idConversatorio;
    }

    public void setIdConversatorio(int idConversatorio) {
        this.idConversatorio = idConversatorio;
    }
    private int idConversatorio;
    
    private String grado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }
    
    
    
}
