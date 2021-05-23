package usa.modelo.dto.decorador;

import usa.modelo.dto.PersonalCalificado;

/**
 *
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres, Camila Fernández,
 * Andrés López, Natalia Montenegro, Santiago Pérez y Miguel Rippe
 */
public class Especialidad extends Informacion {

    private String especialidad;

    public Especialidad(IInformacion informacion) {
        super(informacion);
    }

    public Especialidad() {
        super();
        this.especialidad = "";
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
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
