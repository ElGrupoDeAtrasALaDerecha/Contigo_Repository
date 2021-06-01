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

    public String generarLink() {
        if (o instanceof Persona) {
            return "http://localhost:8080/ContigoApp/recuperarContraseñaPersona.html?codigo=" + codigo;
        } else if (o instanceof Institucion) {
            return "http://localhost:8080/ContigoApp/recuperarContraseñaInstitucion.html?codigo=" + codigo;
        }
        return "";
    }
}
