package usa.modelo.dao;

import usa.modelo.dto.Institucion;
import usa.modelo.dto.Persona;

/**
 * Interfaz de recuperación de contraseña
 * @author Santiago Pérez
 * @version 1.0
 * @since 2021-05-31
 */
public interface IRecuperacionDao extends IDao<String> {

    /**
     * Método que verifica si existe un código de recuperación activo en la base
     * de datos
     *
     * @param i que es la institución a la cual se le verificará el código
     * @return verdadero si existe y falso si no
     */
    boolean verificarCodigoRecuperacion(Institucion i);

    /**
     * Método que verifica si existe un código de recuperación activo en la base
     * de datos
     *
     * @param p que es la persona a la cual se le verificará el código
     * @return verdadero si existe y falso si no
     */
    boolean verificarCodigoRecuperacion(Persona p);

    /**
     * Método que permite validar un código (es decir, permite saber si es
     * válido para cambiar una contraseña)
     *
     * @param codigo que es el código que se quiere validar
     * @return verdadero si lo es y falso si no
     */
    public boolean validarCodigoRecuperacion(String codigo);

    /**
     * Método que permite "eliminar" un código de recuperación de una
     * institución.En realidad, solo deja inhabilitado el código
     *
     * @param institucion que es la institución de la cuál se eliminará el
     * código de recuperación
     * @return verdadero si elimina y falso si no
     */
    public boolean eliminarCodigoRecuperacion(Institucion institucion);
    /**
     * Método que permite "eliminar" un código de recuperación de una
     * institución.En realidad, solo deja inhabilitado el código
     *
     * @param persona que es la institución de la cuál se eliminará el
     * código de recuperación
     * @return verdadero si elimina y falso si no
     */
    public boolean eliminarCodigoRecuperacion(Persona persona);
    
    /**
     * Método que permite "eliminar" un código de recuperación de una
     * institución.En realidad, solo deja inhabilitado el código
     *
     * @param codigo que es la institución de la cuál se eliminará el
     * código de recuperación
     * @return verdadero si elimina y falso si no
     */
    public boolean eliminarCodigoRecuperacion(String codigo);

    /**
     * Método que cambia la contraseña con respecto al código a evaluar
     * @param codigo que es el código de recuperación
     * @param contraseña que es la contraseña nueva
     * @return verdadero si cambia o falso si no
     */
    public boolean cambiarContraseña(String codigo, String contraseña);

    /**
     * Método que almacena un código de recuperación en el objeto O que puede ser una institución o una persona
     * @param o que es el objeto que se va a crear
     * @param codigo que es el codigo que viene por parámetro
     * @return verdadero si crea y falso si no
     */
    public boolean crearCodigoRecuperacion(Object o, String codigo);

}
