package usa.modelo.dto.decorador.media;

/**
 *
 * @author Santiago Pérez
 */
public class Video extends Multimedia{
    private String linkVideo;
    public Video(IComponente componente) {
        super(componente);
    }

    public String getLinkVideo() {
        return linkVideo;
    }

    public void setLinkVideo(String linkVideo) {
        this.linkVideo = linkVideo;
    }
    
    
}
