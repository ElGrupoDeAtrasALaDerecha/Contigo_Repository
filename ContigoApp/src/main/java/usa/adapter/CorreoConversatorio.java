package usa.adapter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import usa.modelo.dto.EstudianteConversatorio;
import usa.utils.Correo;

/**
 * @author Valeria Bermúdez, Laura Blanco, Santiago Cáceres, Camila Fernández,
 * Andrés López, Natalia Montenegro, Santiago Pérez y Miguel Rippe
 */
public class CorreoConversatorio extends Email {

    EstudianteConversatorio estudianteTieneConversatorio;

    public CorreoConversatorio(EstudianteConversatorio estudianteTieneConversatorio) {
        this.estudianteTieneConversatorio = estudianteTieneConversatorio;
    }

    @Override
    public void enviarCorreo(String receptor) {
        try {
            String motivo = "";
            if (estudianteTieneConversatorio.getEstado() == 1) {
                cuerpo = "<!DOCTYPE html>\n"
                        + "<html lang=\"es\">\n"
                        + "\n"
                        + "<head>\n"
                        + "    <meta charset=\"utf-8\">\n"
                        + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                        + "    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.11/semantic.min.css\">\n"
                        + "    \n"
                        + "    <link href=\"https://fonts.googleapis.com/css2?family=Kaushan+Script&display=swap\" rel=\"stylesheet\">\n"
                        + "    <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\">\n"
                        + "    <link href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\">\n"
                        + "    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js\"></script>\n"
                        + "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.js\"></script>\n"
                        + "</head>\n"
                        + "\n"
                        + "<body>\n"
                        + "\n"
                        + "    <div class=\"ui grid\">\n"
                        + "        <div class=\"ui row\">\n"
                        + "            <div class=\"ui sixteen grey inverted wide column\">\n"
                        + "                <h1>¡Muchas gracias por registrarte!</h1>\n"
                        + "\n"
                        + "                Esperamos que el conversatorio te sea de amplio aprendizaje\n"
                        + "            </div>\n"
                        + "        </div>\n"
                        + "        <div class=\"ui row\">\n"
                        + "            <div class=\"ui sixteen grey inverted wide column\">\n"
                        + "                <div class=\"ui segment\">\n"
                        + "                    <div class=\"ui items\">\n"
                        + "                        <div class=\"item\">\n"
                        + "                            <div class=\"image\">\n"
                        + "                                <img src=\"https://www.definicionabc.com/wp-content/uploads/2015/03/orador.jpg\">\n"
                        + "                            </div>\n"
                        + "                            <div class=\"content\">\n"
                        + "                                <a class=\"header\">Inteligencia Emocional</a>\n"
                        + "                                <div class=\"meta\">\n"
                        + "                                    <span>Por <a>Pedro Pataquiva</a></span>\n"
                        + "                                </div>\n"
                        + "                                <div class=\"description\">\n"
                        + "                                    <p>Especialista en dar consejos</p>\n"
                        + "                                    <p>PhD en ser buen amigo</p>\n"
                        + "                                </div>\n"
                        + "                                <div class=\"extra\">\n"
                        + "                                    <b>Lugar: </b> \n"
                        + "                                    Reunión de zoom:\n"
                        + "                                    \n"
                        + "                                    <a href=\"https://zoom.us/j/2743259095?pwd=Ny9oTzVBb1I2R0k3OFE1N3BrVVJmZz09\n"
                        + "                                    \">Click aquí</a> <br>                                     \n"
                        + "                                    ID de reunión: 274 325 9095 <br>\n"
                        + "                                    \n"
                        + "                                    Contraseña: 2xzJwe\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                        </div>\n"
                        + "                        \n"
                        + "                    </div>\n"
                        + "                </div>\n"
                        + "            </div>\n"
                        + "        </div>\n"
                        + "        <div class=\"ui row\">\n"
                        + "            <div class=\"ui sixteen grey inverted wide column\">\n"
                        + "                <h3>¡No te pierdas el conversatorio!</h3>\n"
                        + "            </div>\n"
                        + "        </div>\n"
                        + "        <div class=\"ui row\">\n"
                        + "            <div class=\"ui sixteen wide column\">\n"
                        + "                <h6>Recuerda que puedes cancelar la inscripción en cualquier momento en la página del conversatorio</h6> \n"
                        + "            </div>\n"
                        + "        </div>\n"
                        + "\n"
                        + "    </div>\n"
                        + "\n"
                        + "</body>\n"
                        + "<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js\"></script>\n"
                        + "\n"
                        + "\n"
                        + "</html>";
                motivo = "Confirmación de inscripción";
            }
            Correo.enviarCorreo(receptor, motivo, cuerpo);
        } catch (IOException | MessagingException ex) {
            Logger.getLogger(CorreoConversatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
