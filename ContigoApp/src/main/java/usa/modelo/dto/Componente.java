package usa.modelo.dto;

import usa.modelo.dto.decorador.media.IComponente;

/**
 * Clase componente de historias de situaciones
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres, Camila Fernández,
 * Andrés López, Natalia Montenegro, Santiago Pérez y Miguel Rippe
 */
public abstract class Componente implements IComponente{
    private int id;
    private int idHistoria;
    private int predecesor;
    private String titulo;
    private String texto;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getIdHistoria() {
        return idHistoria;
    }

    @Override
    public void setIdHistoria(int idHistoria) {
        this.idHistoria = idHistoria;
    }

    @Override
    public int getPredecesor() {
        return predecesor;
    }

    @Override
    public void setPredecesor(int predecesor) {
        this.predecesor = predecesor;
    }

    @Override
    public String getTitulo() {
        return titulo;
    }

    @Override
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String getTexto() {
        return texto;
    }

    @Override
    public void setTexto(String texto) {
        this.texto = texto;
    }
    
}
