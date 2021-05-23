package usa.modelo.dto.decorador;

import usa.modelo.dto.PersonalCalificado;

/**
 *
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres, Camila Fernández,
 * Andrés López, Natalia Montenegro, Santiago Pérez y Miguel Rippe
 */
public class RedSocial extends Informacion{
    private String alias;
    private String nombre;
    private String link;
    public RedSocial(IInformacion informacion) {
        super(informacion);
    }

    public RedSocial() {
        super();
        this.alias="";
        this.nombre="";
        this.link="";
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    @Override
    public void agregarInformacion(){
        super.agregarInformacion(this);
    }

    @Override
    public void agregarInformacion(IInformacion i){
        super.agregarInformacion(this);
        informacion.agregarInformacion(i);
    }
}
