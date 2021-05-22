package usa.modelo.dto.decorador.media;

public class Multimedia implements IComponente{
    private IComponente componente;

    public Multimedia(IComponente componente) {
        this.componente = componente;
    }
    
    @Override
    public int getId() {
        return componente.getId();
    }

    @Override
    public void setId(int id) {
        componente.setId(id);
    }

    @Override
    public int getIdHistoria() {
        return componente.getIdHistoria();
    }

    @Override
    public void setIdHistoria(int idHistoria) {
        componente.setIdHistoria(idHistoria);
    }

    @Override
    public int getPredecesor() {
        return componente.getPredecesor();
}

    @Override
    public void setPredecesor(int predecesor) {
        componente.setPredecesor(predecesor);
    }

    @Override
    public String getTitulo() {
        return componente.getTitulo();
    }

    @Override
    public void setTitulo(String titulo) {
        componente.setTitulo(titulo);
    }

    @Override
    public String getTexto() {
        return componente.getTexto();
    }

    @Override
    public void setTexto(String texto) {
        componente.setTexto(texto);
    }
    
}
