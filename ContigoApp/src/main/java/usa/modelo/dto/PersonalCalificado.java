package usa.modelo.dto;

import java.util.LinkedList;
import usa.modelo.dto.decorador.IInformacion;
import usa.modelo.dto.decorador.Informacion;

/**
 *
 */
public class PersonalCalificado extends Persona implements IInformacion {

    private String imagen;

    private LinkedList<IInformacion> info;

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public void agregarInformacion() {
        System.out.println("No hay información adicional");
    }

    @Override
    public void agregarInformacion(IInformacion i) {
        if (info == null) {
            info = new LinkedList();
        }
        if (info.indexOf(i) == -1) {
            this.info.add(i);
        }
    }

    public void limpiar() {
        if (info != null) {
            for (IInformacion i : info) {
                Informacion informacion = (Informacion) i;
                informacion.setInformacion(null);
            }
        }
    }

    public LinkedList<IInformacion> getInfo() {
        return info;
    }

    public void setInfo(LinkedList<IInformacion> info) {
        this.info = info;
    }
    
}
