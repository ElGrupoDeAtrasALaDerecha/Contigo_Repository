package usa.modelo.dto.decorador.media;
public class Imagen extends Multimedia{
    private String linkImagen;

    public Imagen(IComponente componente) {
        super(componente);
    }

    public String getLinkImagen() {
        return linkImagen;
    }

    public void setLinkImagen(String linkImagen) {
        this.linkImagen = linkImagen;
    }
    
    
}
