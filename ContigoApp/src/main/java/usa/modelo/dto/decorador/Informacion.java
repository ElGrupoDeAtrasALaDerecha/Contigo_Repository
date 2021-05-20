package usa.modelo.dto.decorador;

/**
 *
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres, Camila Fernández,
 * Andrés López, Natalia Montenegro, Santiago Pérez y Miguel Rippe
 */
public abstract class Informacion implements IInformacion{

    
    
    private int id;
    private String documento;
    private boolean publico;
    IInformacion informacion;
    
    public Informacion(IInformacion informacion) {
        this.informacion = informacion;
    }

    public Informacion() {
        //this.informacion=null;
        this.id=0;
        this.documento="";
        this.publico=false;
    }

    
    @Override
    public void agregarInformacion(){
        informacion.agregarInformacion();
    }

    @Override
    public void agregarInformacion(IInformacion i){
        informacion.agregarInformacion(i);
    };

    public IInformacion getInformacion() {
        return informacion;
    }

    public void setInformacion(IInformacion informacion) {
        this.informacion = informacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public boolean isPublico() {
        return publico;
    }

    public void setPublico(boolean publico) {
        this.publico = publico;
    }
    
}
