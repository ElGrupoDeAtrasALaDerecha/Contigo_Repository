package usa.adapter;

import usa.modelo.dto.Institucion;
import usa.modelo.dto.Persona;

/**
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres, Camila Fernández,
 * Andrés López, Natalia Montenegro, Santiago Pérez y Miguel Rippe
 */
public class CorreoClave {

    private final String codigo;
    private final Object o;

    public CorreoClave(String codigo, Object o) {
        this.codigo = codigo;
        this.o = o;
    }
    /**
     * Método que genera el link de recuperación de contraseña
     * @return un string con el link de recuperación de contraseña
     */
    public String generarLink() {
        return "http://184:8080/ContigoApp/recuperarContraseña.html?codigo=" + codigo;
    }
}
