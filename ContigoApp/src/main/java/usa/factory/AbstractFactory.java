package usa.factory;

/**
 * Clase abstract Factory
 *
 * @author Camila fernandez, Miguel Angel Rippe, Laura Blanco, Andres McQueen
 * López, Valeria Bermúdez, Natalia Montenegro Quevedo, Santiago Cáceres.
 * Santiago Pérez
 * @version 1.0.0
 * @since 2021-04-14
 */
public interface AbstractFactory <T>{

    public T obtener(String param);
}
