package usa.modelo.dto.decorador;

/**
 *
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres, Camila Fernández,
 * Andrés López, Natalia Montenegro, Santiago Pérez y Miguel Rippe
 */
public class Experiencia extends Informacion {

    private String cargo;
    private String detalles;

    public Experiencia(IInformacion informacion) {
        super(informacion);
    }

    public Experiencia() {
        super();
        this.cargo = "";
        this.detalles = "";
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
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
