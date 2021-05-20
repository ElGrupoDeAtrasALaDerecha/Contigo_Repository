package usa.modelo.dto.decorador;

/**
 *
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres, Camila Fernández,
 * Andrés López, Natalia Montenegro, Santiago Pérez y Miguel Rippe
 */
public class Biografia extends Informacion {

    private String biografia;

    public Biografia(IInformacion informacion) {
        super(informacion);
    }

    public Biografia() {
        super();
        this.biografia = "";
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
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
