package usa.adapter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import usa.utils.Correo;

/**
 *
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres, Camila Fernández,
 * Andrés López, Natalia Montenegro, Santiago Pérez y Miguel Rippe
 */
public class CorreoClaveAdapter extends Email {

    private final CorreoClave correoClave;

    public CorreoClaveAdapter(CorreoClave correoClave) {
        this.correoClave = correoClave;
    }

    @Override
    public void enviarCorreo(String receptor) {

        String link = correoClave.generarLink();
        cuerpo = "<h1>Recuperación de contraseña</h1>\n"
                + "En este link podrá recuperar su contraseña:\n"
                + "link para la recuperación= <a href=\"" + link+"\"> Click aquí </a>";
        try {
            Correo.enviarCorreo(receptor, "Recuperación de contraseña", cuerpo);
        } catch (IOException | MessagingException ex) {
            Logger.getLogger(CorreoClaveAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
